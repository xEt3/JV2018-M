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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.SesionUsuario;

public class SesionesDAO implements OperacionesDAO {

	// Singleton.
	private static SesionesDAO instance;

	// Base de datos
	private Connection db;

	// Atributos de procesamiento de la base de datos.
	private Statement stSesiones;
	private ArrayList<Object> bufferSesiones;
	private DefaultTableModel tmSesiones;
	private ResultSet rsSesiones;

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

		tmSesiones = new DefaultTableModel();
		bufferSesiones = new ArrayList<>();
	}

	/**
	 * Crea la tabla "sesiones" en la base de datos si no existiera previamente.
	 */
	private void crearTablaSesiones() {
		try {
			stSesiones.executeQuery("CREATE TABLE IF NOT EXISTS `sesiones` (" + 
					"`id_usuario` VARCHAR(45) NOT NULL," + 
					"`fecha` TIMESTAMP NOT NULL," + 
					"`estado` ENUM('EN_PREPARACION', 'ACTIVA', 'CERRADA') NOT NULL," + 
					"PRIMARY KEY (`id_usuario`, `fecha`))"
					);
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
	public SesionUsuario obtener(String idSesion) {
		// TODO SesionUsuario.obtener
		return null;
	}

	/**
	 * Obtención de la lista de Sesiones almacenadas.
	 * 
	 * @return lista de Sesiones de Usuario
	 */
	@Override
	public List obtenerTodos() {
		// TODO SesionUsuario.obtenerTodos
		return null;
	}

	/**
	 * Obtiene todas las sesiones por IdUsr de usuario.
	 * 
	 * @param idUsr - el idUsr a buscar.
	 * @return - las sesiones encontradas.
	 */
	public List<SesionUsuario> obtenerTodasMismoUsr(String idUsr) {
		// TODO SesionUsuario.obtenerTodasMismoUsr
		return null;
	}

	/**
	 * Alta de una nueva SesionUsuario.
	 * 
	 * @param obj - la SesionUsuario a almacenar.
	 * @throws DatosException - si ya existe.
	 */
	@Override
	public void alta(Object obj) throws DatosException {
		// TODO SesionUsuario.alta
	}

	/**
	 * Baja de una sesion existente.
	 * 
	 * @param id de la sesion
	 * @throws DatosException - si no la encuentra
	 */
	@Override
	public SesionUsuario baja(String id) throws DatosException {
		// TODO SesionUsuario.baja
		return null;
	}

	/**
	 * Actualiza los datos de una sesion.
	 * 
	 * @param objeto sesion con los nuevos datos.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		// TODO SesionUsuario.actualizar
	}

	/**
	 * @return un String con todos los usuarios.
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
	 * @return un String con todas las IDs de los usuarios.
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
		// TODO SesionUsuario.borrarTodo
	}

}
