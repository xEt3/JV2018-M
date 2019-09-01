/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento de objetos SesionUsuario en base de datos mysql.
 * Aplica el patron Singleton.
 * Colabora en el patrón Façade.
 * @since: prototipo2.0
 * @source: SesionesDAO.java
 * @version: 2.2 - 2019.05.30
 * @author: Grupo 1
 * @author: Miguel Fernández Piñero (MiguelFerPi)
 * @author: Jesús Pérez Robles (jebles)
 * @author: ajp
 */

package accesoDatos.mySql;

import java.util.ArrayList;
import java.util.List;

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
import modelo.SesionUsuario.EstadoSesion;
import modelo.Simulacion.EstadoSimulacion;
import util.*;

public class SesionesDAO implements OperacionesDAO {

	// Singleton.
	private static SesionesDAO instance;

	// Base de datos
	private Connection db;

	// Atributos de procesamiento de la base de datos.
	private Statement stSesiones;
	private ResultSet rsSesiones;
	private DefaultTableModel tmSesiones;
	private ArrayList<SesionUsuario> bufferSesiones;

	// Constructor
	private SesionesDAO() {
		db = Conexion.getDB();
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
	public static SesionesDAO getInstance() {
		if (instance == null) {
			instance = new SesionesDAO();
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
			stSesiones = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			crearTablaSesiones();
		} catch (SQLException e) {
		}
	}

	/**
	 * Crea la tabla "sesiones" en la base de datos si no existiera previamente.
	 */
	private void crearTablaSesiones() {
		try {
			stSesiones.executeUpdate("CREATE TABLE IF NOT EXISTS SESIONES("
								+ "id_sesion VARCHAR(255) NOT NULL PRIMARY KEY,"
								+ "id_usuario VARCHAR(45) NOT NULL,"
								+ "fecha DATETIME NOT NULL,"
								+ "estado VARCHAR(20) NOT NULL,"
								+ ")");
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
	public SesionUsuario obtener(String id) {
		assert id != null;
		ejecutarConsulta(id);
		etiquetarColumnasModelo();
		borrarFilasModelo();
		rellenarFilasModelo();
		sincronizarBufferSimulaciones();

		if (bufferSesiones.size() > 0) {
			return (SesionUsuario) bufferSesiones.get(0);
		}
		return null;
	}

	private void ejecutarConsulta(String id) {
		try {
			rsSesiones = stSesiones.executeQuery("select * from SESIONES where id_sesion = '" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void etiquetarColumnasModelo() {
		try {
			ResultSetMetaData metaDatos = this.rsSesiones.getMetaData();
			// numero total de columnas
			int numCol = metaDatos.getColumnCount();
			// etiqueta de cada columna
			Object[] etiquetas = new Object[numCol];
			for (int i = 0; i < numCol; i++) {
				etiquetas[i] = metaDatos.getColumnLabel(i + 1);
			}
			// Incorpora array de etiquetas en el TableModel.
			this.tmSesiones.setColumnIdentifiers(etiquetas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void borrarFilasModelo() {
		while (this.tmSesiones.getRowCount() > 0) {
			this.tmSesiones.removeRow(0);
		}
	}
	
	private void rellenarFilasModelo() {
		Object[] datosFila = new Object[this.tmSesiones.getColumnCount()];
		try {
			while(rsSesiones.next()) {
				for (int i = 0; i < this.tmSesiones.getColumnCount(); i++) {
					datosFila[i] = this.rsSesiones.getObject(i+1);
				}
				this.tmSesiones.addRow(datosFila);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private void sincronizarBufferSimulaciones() {
		bufferSesiones.clear();
		for (int i = 0; i < tmSesiones.getRowCount(); i++) {
			try {
				String id_sesion = (String) tmSesiones.getValueAt(i, 0);
				String id_usuario = (String) tmSesiones.getValueAt(i, 1);
				Fecha fecha  = obtenerFechaFromDateTime(tmSesiones.getValueAt(i, 2).toString());
				EstadoSesion estado = obtenerEstadoSesion((String)tmSesiones.getValueAt(i, 5));
				UsuariosDAO usrDAO = UsuariosDAO.getInstance();
				bufferSesiones.add(new SesionUsuario(id_sesion,usrDAO.obtener(id_usuario),fecha,estado));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Metodo que combierte el resultado que se obtiene de una consulta de un datetime a un objeto Fecha
	 * @param dateTime
	 * @return
	 */
	private Fecha obtenerFechaFromDateTime(String dateTime) {
		String[] fechaYHora= dateTime.split(" ");
		String[] fecha = fechaYHora[0].split("-");
		String[] hora = fechaYHora[1].split(":");
		return new Fecha(Integer.parseInt(fecha[0]),Integer.parseInt(fecha[1]),Integer.parseInt(fecha[2]),
				Integer.parseInt(hora[0]),Integer.parseInt(hora[1]),(int)Double.parseDouble(hora[2]));
	}
	
	/**
	 * Metodo para convertir un varchar en un enum
	 * @param valorSinFormatear
	 * @return
	 */
	private EstadoSesion obtenerEstadoSesion(String valorSinFormatear) {
		if(valorSinFormatear.equals("ACTIVA")) {
			return EstadoSesion.ACTIVA;
		}
		if(valorSinFormatear.equals("CERRADA")) {
			return EstadoSesion.CERRADA;
		}
		if(valorSinFormatear.equals("EN_PREPARACION")) {
			return EstadoSesion.EN_PREPARACION;
		}
		return null;
	}
	
	/**
	 * Obtención de la lista de Sesiones almacenadas.
	 * 
	 * @return lista de Sesiones de Usuario
	 */
	@Override
	public ArrayList<SesionUsuario> obtenerTodos() {
		try {
			rsSesiones = stSesiones.executeQuery("select * from SESIONES");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		etiquetarColumnasModelo();
		borrarFilasModelo();
		rellenarFilasModelo();
		sincronizarBufferSimulaciones();;
		return this.bufferSesiones;
	}
	
	/**
	 * Obtiene todas las sesiones por IdUsr de usuario.
	 * 
	 * @param idUsr - el idUsr a buscar.
	 * @return - las sesiones encontradas.
	 */
	public ArrayList<SesionUsuario> obtenerTodasMismoUsr(String idUsr) {
		try {
			rsSesiones = stSesiones.executeQuery("select * from SESIONES  where id_usuario = '" + idUsr + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		etiquetarColumnasModelo();
		borrarFilasModelo();
		rellenarFilasModelo();
		sincronizarBufferSimulaciones();
		return this.bufferSesiones;
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
		SesionUsuario sesion= (SesionUsuario)obj;
		String sqlQuery = obtenerConsultaAlta(sesion);
		if (obtener(sesion.getId()) == null) {
			try {
				Statement statement = db.createStatement();
				statement.executeUpdate(sqlQuery);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			throw new DatosException("Sesion dada de Alta ya existe con el nombre: " + sesion.getId());
		}
	}
	
	/**
	 * Consulta que se lanza para dar de alta el mundo en la base de datos.
	 * @param mundo
	 * @return - Devuelve la QuerySQL para dar de alta el mundo.
	 */
	private String obtenerConsultaAlta(SesionUsuario sesion) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("INSERT INTO SESIONES (id_sesion,id_usuario,fecha,estado) VALUES"
				+ "('" + sesion.getId()+ "'," 
				+"'"+ sesion.getUsr().getId()+ "'," 
				+"'"+ sesion.getFecha()+ "',"  
				+ "'"+ formatearEstadoSesion(sesion.getEstado()) +"');");
		return sqlQuery.toString();
	}
	
	private String formatearEstadoSesion(EstadoSesion estadoSesion) {
		if(estadoSesion.equals(EstadoSesion.ACTIVA)) {
			return "ACTIVA";
		}
		if(estadoSesion.equals(EstadoSesion.CERRADA)) {
			return "CERRADA";
		}
		if(estadoSesion.equals(EstadoSesion.EN_PREPARACION)) {
			return "EN_PREPARACION";
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
	public SesionUsuario baja(String id) throws DatosException {
		assert id != null;
		assert !id.matches("");
		assert !id.matches("[ ]+");
		SesionUsuario sesion = (SesionUsuario) obtener(id);
		if (sesion != null) {
			try {
				bufferSesiones.remove(sesion);
				String sql = "DELETE FROM SESIONES WHERE id_sesion ='" + id + "'";
				stSesiones.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return sesion;
		}
		throw new DatosException("Baja: " + id + " no existe...");
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
		SesionUsuario sesionActualizada = (SesionUsuario) obj;
		SesionUsuario sesionPrevia = (SesionUsuario) obtener(sesionActualizada.getId());
		String sqlQuery = obtenerConsultaActualizar(sesionActualizada);
		if (sesionPrevia != null) {
			try {
				this.bufferSesiones.remove(sesionPrevia);
				this.bufferSesiones.add(sesionActualizada);
				this.stSesiones.executeUpdate(sqlQuery);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		throw new DatosException("Actualizar: "+ sesionActualizada.getId() + " no existe.");
	}
	/**
	 * Consulta que se lanza para actualizar la sesion de la base de datos.
	 * @param sesionActualizada
	 * @return - Devuelve la QuerySQL para actualizar la sesion.
	 */
	private String obtenerConsultaActualizar(SesionUsuario sesionActualizada) {
		String id_usr = sesionActualizada.getUsr().getId();
		Fecha fecha= sesionActualizada.getFecha();
		EstadoSesion estado = sesionActualizada.getEstado();
		StringBuilder query = new StringBuilder();
		query.append("UPDATE SESIONES SET id_usuario = '" + id_usr + "', fecha  = '" + fecha.getGregorian() +  "', estado = '" + formatearEstadoSesion(estado)
				+ "' WHERE id_sesion LIKE'"+ sesionActualizada.getId()+"'");
		return query.toString();
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
		for (SesionUsuario sesion: listaSsn) {
			result.append(sesion.getId() + ("\n"));
		}

		return result.toString();
	}
	
	/**
	 * Elimina todas los objetos SesionUsuario en la base de datos.
	 */
	@Override
	public void borrarTodo() {
		try {
			stSesiones.executeUpdate("DELETE FROM SESIONES");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
