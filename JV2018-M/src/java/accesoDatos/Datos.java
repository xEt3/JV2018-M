/** 
 * Proyecto: Juego de la vida.
 * Almacén de datos del programa. Utiliza patron Façade.
 * @since: prototipo2.0
 * @source: Datos.java 
 * @version: 2.0 - 2019.03.23
 * @author: ajp
 */

package accesoDatos;

import java.util.List;

import accesoDatos.fichero.*;
import modelo.Identificable;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.SesionUsuario;
import modelo.Simulacion;
import modelo.Usuario;

public class Datos {

	private UsuariosDAO usuariosDAO; 
	private SesionesDAO sesionesDAO;
	private SimulacionesDAO simulacionesDAO;
	private MundosDAO mundosDAO;

	/**
	 * Constructor por defecto.
	 * @throws DatosException 
	 */
	public Datos() throws DatosException {
		usuariosDAO = UsuariosDAO.getInstance();
		sesionesDAO = SesionesDAO.getInstance();
		mundosDAO = MundosDAO.getInstance();
		simulacionesDAO = SimulacionesDAO.getInstance();
	}

	/**
	 *  Cierra almacen de datos.
	 */
	public void cerrar() {
		usuariosDAO.cerrar();
		sesionesDAO.cerrar();
		simulacionesDAO.cerrar();
		mundosDAO.cerrar();
	}
	
	// FACHADA usuariosDAO
	
	/**
	 * Método fachada que obtiene un Usuario dado el id. 
	 * Reenvia petición al método DAO específico.
	 * @param id - el id de Usuario a obtener.
	 * @return - el Usuario encontrado; null si no encuentra.
	 */	
	public Usuario obtenerUsuario(String id) {
		return usuariosDAO.obtener(id);
	}

	/**
	 * Método fachada que obtiene un Usuario dado un objeto. 
	 * Reenvia petición al método DAO específico.
	 * @param usr - el objeto Usuario a obtener.
	 * @return - el Usuario encontrado; null si no encuentra. 
	 */	
	public Usuario obtenerUsuario(Usuario usr)  {
		assert usr != null;
		return usuariosDAO.obtener(usr.getId());
	}
	
	/**
	 * Método fachada para alta de un Usuario. 
	 * Reenvia petición al método DAO específico.
	 * @param usuario - el objeto Usuario a dar de alta.
	 * @throws DatosException - si ya existe.
	 */
	public void altaUsuario(Usuario usuario) throws DatosException  {
		usuariosDAO.alta(usuario);
	}

	/**
	 * Método fachada para baja de un Usuario y sus dependencias. 
	 * Reenvia petición al método DAO específico.
	 * @param idUsr - el id de Usuario a dar de baja.
	 * @throws DatosException - si no existe.
	 * @throws ModeloException 
	 */
	public Usuario bajaUsuario(String idUsr) throws DatosException, ModeloException  {
		Usuario usrBaja = usuariosDAO.baja(idUsr);
		// Baja de sesiones y simulaciones dependientes.
		for (Identificable sesionBaja : sesionesDAO.obtenerTodosMismoUsr(idUsr)) {
			sesionesDAO.baja(sesionBaja.getId());
		}
		for (Identificable simulBaja : simulacionesDAO.obtenerTodosMismoUsr(idUsr)) {
			simulacionesDAO.baja(simulBaja.getId());
		}	
		return usrBaja;
	}

	/**
	 * Método fachada para baja de un Usuario y sus dependencias. 
	 * @param usr - el objeto Usuario a dar de baja.
	 * @throws DatosException - si no existe.
	 * @throws ModeloException 
	 */
	public Usuario bajaUsuario(Usuario usr) throws DatosException, ModeloException  {
		assert usr != null;
		return bajaUsuario(usr.getId());
	}

	/**
	 * Método fachada para modicar un Usuario. 
	 * Reenvia petición al método DAO específico.
	 * @param u - el objeto Usuario con los cambios.
	 * @throws DatosException - si no existe.
	 */
	public void actualizarUsuario(Usuario usr) throws DatosException  {
		usuariosDAO.actualizar(usr);
	}

	/**
	 * Método fachada para obtener listado de todos
	 * los objetos en forma de texto.  
	 * Reenvia petición al método DAO específico.
	 * @return - el texto.
	 */
	public String toStringDatosUsuarios() {
		return usuariosDAO.listarDatos();
	}

	/**
	 * Método fachada para eliminar todos los usuarios y sus dependencias.  
	 * Reenvia petición al método DAO específico.
	 */
	public void borrarTodosUsuarios() {
		usuariosDAO.borrarTodo();
		// Dependencias.
		sesionesDAO.borrarTodo();
		simulacionesDAO.borrarTodo();
	}

	// FACHADA sesionesDAO
	/**
	 * Método fachada que obtiene un Usuario dado el idSesion. 
	 * Reenvia petición al método DAO específico.
	 * @param idSesion - el idUsr + fecha de la SesionUsuario a obtener.
	 * @return - la SesionUsuario encontrada.
	 * @throws DatosException - si no existe.
	 */	
	public SesionUsuario obtenerSesion(String idSesion) throws DatosException {
		return sesionesDAO.obtener(idSesion);
	}

	/**
	 * Método fachada que obtiene un Usuario dado un objeto.
	 * Reenvia petición al método DAO específico.
	 * @param sesion - la SesionUsuario a obtener.
	 * @return - la SesionUsuario encontrada.
	 * @throws DatosException - si no existe.
	 */	
	public SesionUsuario obtenerSesion(SesionUsuario sesion) throws DatosException {
		return sesionesDAO.obtener(sesion.getId());
	}

	/**
	 * Método fachada que obtiene todas las sesiones de un usuario. 
	 * Reenvia petición al método DAO específico.
	 * @param simulacion - el objeto Simulacion a obtener.
	 * @return - lista de simulaciones encontradas.
	 * @throws ModeloException 
	 */	
	public List<Identificable> obtenerSesionesUsuario(String idUsr) throws ModeloException {
		return sesionesDAO.obtenerTodosMismoUsr(idUsr);
	}
			
	/**
	 * Método fachada para alta de una SesionUsuario. 
	 * Reenvia petición al método DAO específico.
	 * @param sesion - el objeto SesionUsuario a dar de alta.
	 * @throws DatosException - si ya existe.
	 */
	public void altaSesion(SesionUsuario sesion) throws DatosException  {
		sesionesDAO.alta(sesion);
	}

	/**
	 * Método fachada para baja de una SesionUsuario. 
	 * Reenvia petición al método DAO específico.
	 * @param idSesion - el id de la SesionUsuario a dar de baja.
	 * @throws DatosException - si no existe.
	 */
	public SesionUsuario bajaSesion(String idSesion) throws DatosException  {
		return sesionesDAO.baja(idSesion);
	}

	/**
	 * Método fachada para baja de una SesionUsuario. 
	 * Reenvia petición al método DAO específico.
	 * @param idSesion - el id de la SesionUsuario a dar de baja.
	 * @throws DatosException - si no existe.
	 */
	public SesionUsuario bajaSesion(SesionUsuario sesion) throws DatosException  {
		assert sesion != null;
		return sesionesDAO.baja(sesion.getId());
	}

	/**
	 * Método fachada para modicar una Sesión. 
	 * Reenvia petición al método DAO específico.
	 * @param sesion - el objeto SesionUsuario a modificar.
	 * @throws DatosException - si no existe.
	 */
	public void actualizarSesion(SesionUsuario sesion) throws DatosException  {
		sesionesDAO.actualizar(sesion);
	}

	/**
	 * Método fachada para obtener listado de todos los objetos en forma de texto.  
	 * Reenvia petición al método DAO específico.
	 * @return - el texto.
	 */
	public String toStringDatosSesiones() {
		return sesionesDAO.listarDatos();
	}

	/**
	 * Método fachada para eliminar todas las sesiones.  
	 * Reenvia petición al método DAO específico.
	 */
	public void borrarTodasSesiones() {
		sesionesDAO.borrarTodo();
	}

	/**
	 * Método fachada para obtener total sesiones registradas.  
	 * Reenvia petición al método DAO específico.
	 */
	public int getSesionesRegistradas() {
		return sesionesDAO.obtenerTodos().size();
	}

	// FACHADA simulacionesDAO
	/**
	 * Método fachada que obtiene una Simulacion dado el idSimulacion. 
	 * Reenvia petición al método DAO específico.
	 * @param idSimulacion - el id de la Simulacion a obtener.
	 * @return - la Simulacion encontrada.
	 * @throws DatosException - si no existe.
	 */	
	public Simulacion obtenerSimulacion(String idSimulacion) {
		return simulacionesDAO.obtener(idSimulacion);
	}

	/**
	 * Método fachada que obtiene una Simulacion dado un objeto.
	 * Reenvia petición al método DAO específico. 
	 * @param simulacion - el objeto Simulacion a obtener.
	 * @return - la Simulacion encontrada.
	 * @throws DatosException - si no existe.
	 */	
	public Simulacion obtenerSimulacion(Simulacion simulacion) {
		assert simulacion != null;
		return simulacionesDAO.obtener(simulacion.getId());
	}

	/**
	 * Método fachada que obtiene todas las simulaciones de un usuario. 
	 * Reenvia petición al método DAO específico.
	 * @param simulacion - el objeto Simulacion a obtener.
	 * @return - lista de simulaciones encontradas.
	 * @throws ModeloException 
	 */	
	public List<Identificable> obtenerSimulacionesUsuario(String idUsr) throws ModeloException {
		return simulacionesDAO.obtenerTodosMismoUsr(idUsr);
	}

	/**
	 * Método fachada para alta de una Simulacion. 
	 * Reenvia petición al método DAO específico.
	 * @param simulacion - el objeto Simulacion a dar de alta.
	 * @throws DatosException - si ya existe.
	 */
	public void altaSimulacion(Simulacion simulacion) throws DatosException  {
		simulacionesDAO.alta(simulacion);
	}

	/**
	 * Método fachada para baja de una Simulacion dado su idSimulacion. 
	 * Reenvia petición al método DAO específico.
	 * @param idSimulacion - el id de la Simulacion a dar de baja.
	 * @throws DatosException - si no existe.
	 */
	public Simulacion bajaSimulacion(String idSimulacion) throws DatosException  {
		return simulacionesDAO.baja(idSimulacion);
	}

	/**
	 * Método fachada para baja de una Simulacion dado su idSimulacion.
	 * Reenvia petición al método DAO específico.
	 * @param simulacion - el objeto Simulacion a dar de baja.
	 * @throws DatosException - si no existe.
	 */
	public Simulacion bajaSimulacion(Simulacion simulacion) throws DatosException  {
		assert simulacion != null;
		return simulacionesDAO.baja(simulacion.getId());
	}

	/**
	 * Método fachada para modicar una Simulacion. 
	 * Reenvia petición al método DAO específico.
	 * @param simulacion - el objeto Simulacion a modificar.
	 * @throws DatosException - si no existe.
	 */
	public void actualizarSimulacion(Simulacion simulacion) throws DatosException  {
		simulacionesDAO.actualizar(simulacion);
	}

	/**
	 * Método fachada para obtener listado de todos
	 * los objetos en forma de texto.  
	 * Reenvia petición al método DAO específico.
	 * @return - el texto.
	 */
	public String toStringDatosSimulaciones() {
		return simulacionesDAO.listarDatos();
	}

	/**
	 * Método fachada para eliminar todos las simulaciones.  
	 * Reenvia petición al método DAO específico.
	 */
	public void borrarTodasSimulaciones() {
		simulacionesDAO.borrarTodo();
	}

	// FACHADA mundosDAO
	/**
	 * Método fachada para obtener un dado su nombre. 
	 * Reenvia petición al método DAO específico.
	 * @param nombre - el nombre de un Mundo a buscar.
	 * @return - el Mundo encontrado.
	 * @throws DatosException - si no existe.
	 */
	public Mundo obtenerMundo(String nombre) throws DatosException {
		return mundosDAO.obtener(nombre);
	}

	/**
	 * Método fachada para obtener un dado un objeto.
	 * Reenvia petición al método DAO específico. 
	 * @param mundo - el objeto Mundo a buscar.
	 * @return - el Mundo encontrado.
	 * @throws DatosException - si no existe.
	 */
	public Mundo obtenerMundo(Mundo mundo) throws DatosException {
		assert mundo != null;
		return mundosDAO.obtener(mundo.getId());
	}

	/**
	 * Método fachada para alta de un Mundo. 
	 * Reenvia petición al método DAO específico.
	 * @param mundo - el objeto Mundo a dar de alta.
	 * @throws DatosException - si ya existe.
	 */
	public void altaMundo(Mundo mundo) throws DatosException  {
		mundosDAO.alta(mundo);
	}

	/**
	 * Método fachada para baja de un Mundo. 
	 * Reenvia petición al método DAO específico.
	 * @param nombre - el nombre de un Mundo a dar de baja.
	 * @throws DatosException - si no existe.
	 */
	public Mundo bajaMundo(String id) throws DatosException  {
		return mundosDAO.baja(id);
	}

	/**
	 * Método fachada para baja de un Mundo.
	 * Reenvia petición al método DAO específico.
	 * @param nombre - el nombre de un Mundo a dar de baja.
	 * @throws DatosException - si no existe.
	 */
	public Mundo bajaMundo(Mundo mundo) throws DatosException  {
		assert mundo != null;
		return mundosDAO.baja(mundo.getId());
	}

	/**
	 * Método fachada para modicar un Mundo. 
	 * Reenvia petición al método DAO específico.
	 * @param mundo - el objeto Mundo a modificar.
	 * @throws DatosException - si no existe.
	 */
	public void actualizarMundo(Mundo mundo) throws DatosException   {
		mundosDAO.actualizar(mundo);
	}

	/**
	 * Método fachada para obtener listado de todos
	 * los objetos en forma de texto.  
	 * Reenvia petición al método DAO específico.
	 * @return - el texto.
	 */
	public String toStringDatosMundos() {
		return mundosDAO.listarDatos();
	}

	/**
	 * Método fachada para eliminar todos
	 * los mundos.  
	 * Reenvia petición al método DAO específico.
	 */
	public void borrarTodosMundos() {
		mundosDAO.borrarTodo();
	}

} //class