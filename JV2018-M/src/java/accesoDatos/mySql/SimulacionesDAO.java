/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento de objetos SesionUsuario en base de datos mysql.
 * Aplica el patron Singleton.
 * Colabora en el patrón Façade.
 * @since: prototipo2.0
 * @source: SesionesDAO.java
 * @version: 2.2 - 2019.05.30
 * @author: Ignacio Belmonte
 */

package accesoDatos.mySql;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.*;
import modelo.Mundo.FormaEspacio;
import modelo.SesionUsuario.EstadoSesion;
import modelo.Simulacion.EstadoSimulacion;
import util.*;

public class SimulacionesDAO implements OperacionesDAO {

	// Singleton.
	private static SimulacionesDAO instance;

	// Base de datos
	private Connection db;

	// Fachada de datos
	private Datos datos;

	// Atributos de procesamiento de la base de datos.
	private Statement stSimulaciones;
	private ResultSet rsSimulaciones;
	private DefaultTableModel tmSimulaciones;
	private ArrayList<Object> bufferSimulaciones;

	// Constructor
	private SimulacionesDAO() {
		db = Conexion.getDB();
		datos = new Datos();

		try {
			inicializar();
		} catch (SQLException e) {
		}

	}

	/**
	 * Método estático de acceso a la instancia única. Si no existe la crea
	 * invocando al constructor interno. Utiliza inicialización diferida. Sólo se
	 * crea una vez; instancia única -patrón singleton-
	 * 
	 * @return instance
	 */
	public static SimulacionesDAO getInstance() {
		if (instance == null) {
			instance = new SimulacionesDAO();
		}
		return instance;
	}

	/**
	 * Inicializa el DAO, detecta si existen la tabla de datos capturando la
	 * excepcion SQLException
	 * 
	 * @throws SQLException
	 */
	private void inicializar() throws SQLException {
		db = Conexion.getDB();
		try {
			stSimulaciones = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			crearTablaSimulaciones();
		} catch (SQLException e) {
		}
	}

	/**
	 * Crea la tabla "sesiones" en la base de datos si no existiera previamente.
	 */
	private void crearTablaSimulaciones() {
		try {
			stSimulaciones.executeUpdate(
					"CREATE TABLE IF NOT EXISTS `SIMULACIONES` (" + "`id_simulacion VARCHAR(45) NOT NULL PRIMARY KEY`,"
							+ "`id_usuario` VARCHAR(45) NOT NULL," + "`fecha` DATETIME NOT NULL,"
							+"`id_mundo VARCHAR(255) NOT NULL`," + "`ciclos int(8) NOT NULL`," + "`estado` VARCHAR(20) NOT NULL,)" );
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtención de la Sesion.
	 * 
	 * @param idSesion - string con la id de la sesión
	 * @return SesionUsuario si la encuentra
	 */
	@Override
	public Simulacion obtener(String id) {
		assert id != null;
		ejecutarConsulta(id);
		etiquetarColumnasModelo();
		borrarFilasModelo();
		rellenarFilasModelo();
		sincronizarBufferSimulaciones();

		if (bufferSimulaciones.size() > 0) {
			return (Simulacion) bufferSimulaciones.get(0);
		}
		return null;
	}

	private void ejecutarConsulta(String id) {
		try {
			rsSimulaciones = stSimulaciones.executeQuery("select * from SIMULACIONES where nombre = '" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void etiquetarColumnasModelo() {
		try {
			ResultSetMetaData metaDatos = this.rsSimulaciones.getMetaData();

			// numero total de columnas
			int numCol = metaDatos.getColumnCount();

			// etiqueta de cada columna
			Object[] etiquetas = new Object[numCol];
			for (int i = 0; i < numCol; i++) {
				etiquetas[i] = metaDatos.getColumnLabel(i + 1);
			}

			// Incorpora array de etiquetas en el TableModel.
			this.tmSimulaciones.setColumnIdentifiers(etiquetas);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void borrarFilasModelo() {
		while (this.tmSimulaciones.getRowCount() > 0) {
			this.tmSimulaciones.removeRow(0);
		}

	}
	
	private void rellenarFilasModelo() {
		Object[] datosFila = new Object[this.tmSimulaciones.getColumnCount()];
		
		try {
			while(rsSimulaciones.next()) {
				for (int i = 0; i < this.tmSimulaciones.getColumnCount(); i++) {
					datosFila[i] = this.rsSimulaciones.getObject(i+1);
				}
				this.tmSimulaciones.addRow(datosFila);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void sincronizarBufferSimulaciones() {
		bufferSimulaciones.clear();
		for (int i = 0; i < tmSimulaciones.getRowCount(); i++) {
			try {
				String id_usr = (String) tmSimulaciones.getValueAt(i, 1);
				Fecha fecha  = new Fecha((GregorianCalendar) tmSimulaciones.getValueAt(i, 2));
				String id_mundo = (String) tmSimulaciones.getValueAt(i, 3);
				int ciclos= (Integer) tmSimulaciones.getValueAt(i, 4); 
				EstadoSimulacion estado = obtenerEstadoSimulacion((String)tmSimulaciones.getValueAt(i, 5));
				UsuariosDAO usrDAO = UsuariosDAO.getInstance();
				MundosDAO mundosDAO= MundosDAO.getInstance();
				bufferSimulaciones.add(new Simulacion(usrDAO.obtener(id_usr),fecha,mundosDAO.obtener(id_mundo),ciclos,estado));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}


	private EstadoSimulacion obtenerEstadoSimulacion(String valorSinFormatear) {
		if(valorSinFormatear.equals("PREPARADA")) {
			return EstadoSimulacion.PREPARADA;
		}
		if(valorSinFormatear.equals("INICIADA")) {
			return EstadoSimulacion.INICIADA;
		}
		if(valorSinFormatear.equals("COMPLETADA")) {
			return EstadoSimulacion.COMPLETADA;
		}
		return null;
	}

	/**
	 * Obtención de la lista de Sesiones almacenadas.
	 * 
	 * @return lista de Sesiones de Usuario
	 */
	@Override
	public List<SesionUsuario> obtenerTodos() {
		List<SesionUsuario> lista = new ArrayList<SesionUsuario>();
		try {
			rsSimulaciones = stSimulaciones.executeQuery("SELECT * FROM sesiones");
			lista = obtener(rsSimulaciones);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Obtiene todas las sesiones por IdUsr de usuario.
	 * 
	 * @param idUsr - el idUsr a buscar.
	 * @return - las sesiones encontradas.
	 */
	public List<SesionUsuario> obtenerTodasMismoUsr(String idUsr) {
		List<SesionUsuario> lista = new ArrayList<SesionUsuario>();
		try {
			rsSimulaciones = stSimulaciones.executeQuery("SELECT * FROM sesiones WHERE id_usuario=" + idUsr);
			lista = obtener(rsSimulaciones);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Método obtener que se encarga de buscar en la base de datos según el tipo de
	 * identificador que le es introducido. Creado para evitar repetición de código.
	 * 
	 * @param id - identificador a buscar, si contiene ":" lo detectará como id de
	 *           sesión, si no, como id de usuario, y si es nulo, devolverá todas
	 *           las sesiones que existan.
	 * @return Lista de Sesiones de usuario encontradas
	 */
	private List<SesionUsuario> obtener(ResultSet resultadoQuery) {
		ArrayList<SesionUsuario> listaSesiones = new ArrayList<SesionUsuario>();
		Usuario usr = null;

		try {
			while (resultadoQuery.next()) {
				usr = datos.obtenerUsuario((resultadoQuery.getString("id_usuario")));
				String estadoString = resultadoQuery.getString("estado");
				EstadoSesion estado = null;
				switch (estadoString) {
				case "EN_PREPARACION":
					estado = SesionUsuario.EstadoSesion.EN_PREPARACION;
					break;
				case "ACTIVA":
					estado = SesionUsuario.EstadoSesion.ACTIVA;
					break;
				case "CERRADA":
					estado = SesionUsuario.EstadoSesion.CERRADA;
					break;
				}

				listaSesiones.add(new SesionUsuario(usr, new Fecha(resultadoQuery.getString("fecha")), estado));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaSesiones;
	}

	/**
	 * Alta de una nueva SesionUsuario.
	 * 
	 * @param obj - la SesionUsuario a almacenar.
	 * @throws DatosException - si ya existe.
	 */
	@Override
	public void alta(Object obj) throws DatosException {
		assert obj != null;
		Simulacion simulacion = (Simulacion)obj;
		
		String sqlQuery = obtenerConsultaAlta(simulacion);
		
		if (obtener(simulacion.getId()) == null) {
			try {
				Statement statement = db.createStatement();
				statement.executeUpdate(sqlQuery);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			throw new DatosException("Simulacion dado de Alta ya existe con el nombre: " + simulacion.getId());
		}
		
	}
	
	/**
	 * Consulta que se lanza para dar de alta el mundo en la base de datos.
	 * @param mundo
	 * @return - Devuelve la QuerySQL para dar de alta el mundo.
	 */
	private String obtenerConsultaAlta(Simulacion simulacion) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("INSERT INTO MUNDO (id_simulacion, id_usuario, fecha, id_mundo,ciclos, estado) VALUES "
				+ "('" + simulacion.getId()
				+ "'," + simulacion.getUsr().getId()
				+ "," + simulacion.getFecha().getGregorian()
				+ ",'" + simulacion.getMundo().getId()
				+"','" + simulacion.getCiclos()
				+ "','"+ formatearEstadoSimulacion(simulacion.getEstado()) +"')");
		
		return sqlQuery.toString();
	}

	private String formatearEstadoSimulacion(EstadoSimulacion estadoSimulacion) {
		if(estadoSimulacion.equals(EstadoSimulacion.PREPARADA)) {
			return "PREPARADA";
		}
		if(estadoSimulacion.equals(EstadoSimulacion.INICIADA)) {
			return "INICIADA";
		}
		if(estadoSimulacion.equals(EstadoSimulacion.COMPLETADA)) {
			return "COMPLETADA";
		}
		return null;
	}
	/**
	 * Baja de una sesion existente.
	 * 
	 * @param id de la sesion
	 * @throws DatosException - si no la encuentra
	 * @returns La sesion dada de baja
	 */
	@Override
	public Simulacion baja(String id) throws DatosException {
		assert id != null;
		Simulacion simulacionAborrar = (Simulacion) obtener(id);

		if (simulacionAborrar == null) {
			throw new DatosException("SesionesDAO.baja: id " + id + "no encontrada");
		} else {
			String query = "DELETE FROM sesiones where CONCAT(id_usuario,':',DATE_FORMAT(fecha,'%Y%m%d%H%i%s')) = ?";

			try {
				// preparar borrado
				PreparedStatement prepStm = db.prepareStatement(query);
				prepStm.setString(1, simulacionAborrar.getId());

				// borrar tupla
				prepStm.execute();

				db.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return simulacionAborrar;
	}

	/**
	 * Actualiza los datos de una sesion.
	 * 
	 * @param objeto sesion con los nuevos datos.
	 * @throws DatosException si no encuentra la sesion
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		assert obj != null;
		SesionUsuario sesNuevoEstado = (SesionUsuario) obj;

		if (obtener(sesNuevoEstado.getId()) == null) {
			throw new DatosException("SesionesDAO.baja: id " + sesNuevoEstado.getId() + "no encontrada");
		} else {
			String query = "UPDATE sesiones SET estado = ? WHERE CONCAT(id_usuario,':',DATE_FORMAT(fecha,'%Y%m%d%H%i%s')) = ?";
			try {
				// preparar actualización
				PreparedStatement prepStm = db.prepareStatement(query);
				prepStm.setString(1, sesNuevoEstado.getEstado().name());
				prepStm.setString(2, sesNuevoEstado.getId());

				// actualizar tupla
				prepStm.execute();

				db.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Devuelve los datos de todas las Sesiones de usuario como String
	 * 
	 * @return un String con los datos de todas las sesiones.
	 */
	@Override
	public String listarDatos() {
		StringBuffer result = new StringBuffer();
		List<SesionUsuario> listaSsn = obtenerTodos();
		for (SesionUsuario sesionUsuario : listaSsn) {
			result.append(sesionUsuario.toString() + ("\n"));
		}

		return result.toString();
	}

	/**
	 * Devuelve las IDs de todas las Sesiones de usuario como String
	 * 
	 * @return un String con todas las IDs de las sesiones.
	 */
	@Override
	public String listarId() {
		StringBuffer result = new StringBuffer();
		List<SesionUsuario> listaSsn = obtenerTodos();
		for (SesionUsuario sesionUsuario : listaSsn) {
			result.append(sesionUsuario.getId() + ("\n"));
		}

		return result.toString();
	}

	/**
	 * Elimina todas los objetos SesionUsuario en la base de datos.
	 */
	@Override
	public void borrarTodo() {
		List<SesionUsuario> listaSesiones = obtenerTodos();
		for (SesionUsuario sesion : listaSesiones) {
			try {
				baja(sesion.getId());
			} catch (DatosException e) {
				e.printStackTrace();
			}
		}
	}

}
