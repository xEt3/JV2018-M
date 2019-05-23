/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento de objetos Usuario 
 * utilizando acceso a base de datos db4o.
 * Colabora en el patron Fachada.
 * @since: prototipo2.0
 * @source: UsuariosDAO.java 
 * @version: 2.1 - 2019/05/03 
 * @author: ajp
 */

package accesoDatos.db4o;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Nif;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class UsuariosDAO  implements OperacionesDAO {

	// Singleton. 
	private static UsuariosDAO instance;

	// Base datos db4o
	private ObjectContainer db;

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instance
	 */
	public static UsuariosDAO getInstance() {
		if (instance == null) {
			instance = new UsuariosDAO();
		}
		return instance;
	}
	
	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private UsuariosDAO() {
		db = Conexion.getDB();
		db.store(new Hashtable <String,String>());
		if (obtener("AAA0T") == null && obtener("III1R") == null) {
			cargarPredeterminados();
		}
	}

	/**
	 *  Método para generar de datos predeterminados.
	 */
	private void cargarPredeterminados() {
		try {
			alta(new Usuario());	//Invitado.

			String nombre = Configuracion.get().getProperty("usuario.admin");
			alta(new Usuario(new Nif(Configuracion.get().getProperty("usuario.nifAdmin")), 
					nombre, 
					nombre + " " + nombre, 
					new DireccionPostal(), 
					new Correo(nombre.toLowerCase() + Configuracion.get().getProperty("correo.dominioPredeterminado")), 
					new Fecha(Configuracion.get().getProperty("usuario.fechaNacimientoPredeterminada")), 
					new Fecha(), 
					new ClaveAcceso(), 
					RolUsuario.ADMINISTRADOR)
					);
		} 
		catch (ModeloException | DatosException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  Cierra conexión.
	 */
	@Override
	public void cerrar() {
		db.close();
	}

	//OPERACIONES DAO
	
	/**
	 * Búsqueda de Usuario dado un objeto, reenvía al método que utiliza idUsr.
	 * @param obj - el Usuario a buscar.
	 * @return - el Usuario encontrado o null si no encuentra.
	 */
	public Usuario obtener(Object obj) {
		return this.obtener(((Usuario) obj).getId());
	}
	
	/**
	 * Obtiene un usuario dado su idUsr, el correo o su nif.
	 * @param id - el id de Usuario a buscar.
	 * @return - el Usuario encontrado. 
	 */
	@Override
	public Usuario obtener(String id) {
		id = obtenerEquivalencia(id);
		ObjectSet <Usuario> result;
		Query consulta = db.query();
		consulta.constrain(Usuario.class);
		consulta.descend("id").constrain(id);
		result = consulta.execute();
		if (result.size() > 0) {
			return result.get(0);
		}	
		return null;			
	}	
	
	/**
	 * Obtiene todos los usuarios almacenados.
	 * @return - la List con todos los usuarios.
	 */
	@Override
	public List <Usuario> obtenerTodos() {
		Query consulta = db.query();
		consulta.constrain(Usuario.class);
		return consulta.execute();
	}

	/**
	 * Registro de nuevo usuario.
	 * @param usr 
	 * @throws DatosException 
	 */
	@Override
	public void alta(Object obj) throws DatosException {
		assert obj != null;
		Usuario usrNuevo = (Usuario) obj;
		if (obtener(usrNuevo.getId()) == null) {
			db.store(usrNuevo);  		
			registrarEquivalenciaId(usrNuevo);
			return;
		}
		else {
			if (obj.equals(usrNuevo)) {
				producirVariantesIdUsr(usrNuevo);
			}
			else {
				throw new DatosException("UsuariosDAO.alta:" + usrNuevo.getId() +" Ya existe.");
			}
		}
	}

	/**
	 *  Si hay coincidencia de identificador hace 23 intentos de variar la última letra
	 *  procedente del NIF. Llama al generarVarianteIdUsr() de la clase Usuario.
	 * @param usrNuevo
	 * @throws DatosException
	 */
	private void producirVariantesIdUsr(Usuario usr) throws DatosException {
		// Coincidencia de id. Hay que generar variante
		int intentos = "ABCDEFGHJKLMNPQRSTUVWXYZ".length();
		do {
			// Generar variante y comprueba de nuevo.
			usr = new Usuario(usr);	
			if (obtener(usr.getId()) == null) {
				db.store(usr);  		
				registrarEquivalenciaId(usr);
				return;
			}
			intentos--;
		} while (intentos >= 0);
		throw new DatosException("UsuariosDAO.alta: imposible generar variante del " + usr.getId());
	}

	/**
	 * Registra las equivalencias de nif y correo para un idUsr.
	 * @param usuario
	 */
	private void registrarEquivalenciaId(Usuario usuario) {
		//Obtiene mapa de equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//Registra equivalencias 
		mapaEquivalencias.put(usuario.getId(), usuario.getId());
		mapaEquivalencias.put(usuario.getNif().getTexto(), usuario.getId());
		mapaEquivalencias.put(usuario.getCorreo().getTexto(), usuario.getId());
		//actualiza datos
		db.store(mapaEquivalencias);	
	}

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param id - el identificador del objeto a eliminar.
	 * @return - el Objeto eliminado. 
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Usuario baja(String id) throws DatosException {
		assert id != null;
		assert id != "";
		assert id != " ";
		Usuario usr = obtener(id);
		if (usr != null) {
			borrarEquivalenciaId(usr);
			db.delete(usr);
			return usr;
		}
		throw new DatosException("Baja: "+ id + " no existe.");
	} 

	/**
	 *  Actualiza datos de un Usuario reemplazando el almacenado por el recibido. 
	 *  No admitirá cambios en el idUsr.
	 *	@param obj - Usuario con los cambios.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException  {
		assert obj != null;
		Usuario usrActualizado = (Usuario) obj;
		Usuario usrPrevio = (Usuario) obtener(usrActualizado.getId());
		if (usrPrevio != null) {
			try {
				cambiarEquivalenciaId(usrPrevio, usrActualizado);
				usrPrevio.setNif(usrActualizado.getNif());
				usrPrevio.setNombre(usrActualizado.getNombre());
				usrPrevio.setApellidos(usrActualizado.getApellidos());
				usrPrevio.setDomicilio(usrActualizado.getDomicilio());
				usrPrevio.setCorreo(usrActualizado.getCorreo());
				usrPrevio.setFechaNacimiento(usrActualizado.getFechaNacimiento());
				usrPrevio.setFechaAlta(usrActualizado.getFechaAlta());
				usrPrevio.setRol(usrActualizado.getRol());
				db.store(usrPrevio);
				return;
			}
			catch (ModeloException e) {
				e.printStackTrace();
			}
		}
		throw new DatosException("Actualizar: "+ usrActualizado.getId() + " no existe.");
	} 

	/**
	 * Actualiza las equivalencias de nif y correo para un id de usuario.
	 * @param usrAntiguo - usuario con datos antiguos
	 * @param usrNuevo - usuario con datos nuevos
	 */
	private void cambiarEquivalenciaId(Usuario usrAntiguo, Usuario usrNuevo) {
		//Obtiene mapa de equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//Cambia equivalencias 
		mapaEquivalencias.replace(usrAntiguo.getId(), usrNuevo.getId().toUpperCase());
		mapaEquivalencias.replace(usrAntiguo.getNif().getTexto(), usrNuevo.getId().toUpperCase());
		mapaEquivalencias.replace(usrAntiguo.getCorreo().getTexto(), usrNuevo.getId().toUpperCase());
		//actualiza datos
		db.store(mapaEquivalencias);
	}
	
	/**
	 * Obtiene el listado de todos los usuarios almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder listado = new StringBuilder();
		for (Usuario usr: obtenerTodos()) {
			listado.append("\n" + usr);
		}
		return listado.toString();
	}

	/**
	 * Obtiene el listado de todos los identificadores de usuario almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarId() {
		StringBuilder listado = new StringBuilder();
		for (Usuario usr: obtenerTodos()) {
			if (usr != null) {
				listado.append("\n" + usr.getId());
			}
		}
		return listado.toString();
	}

	/**
	 * Elimina todos los usuarios almacenados y regenera los predeterminados.
	 */
	@Override
	public void borrarTodo() {
		// Elimina cada uno de los obtenidos
		for (Usuario usr: obtenerTodos()) {
			db.delete(usr);
		}
		// Quita todas las equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		mapaEquivalencias.clear();
		db.store(mapaEquivalencias);
		cargarPredeterminados();
	}

	//GESTION equivalencias id
	/**
	 * Obtiene el idUsr usado internamente a partir de otro equivalente.
	 * @param id - la clave alternativa. 
	 * @return - El idUsr equivalente.
	 */
	public String obtenerEquivalencia(String id) {
		return obtenerMapaEquivalencias().get(id);
	}

	/**
	 * Obtiene el mapa de equivalencias de id para idUsr.
	 * @return el Hashtable almacenado.
	 */
	private Map <String, String> obtenerMapaEquivalencias() {
		//Obtiene mapa de equivalencias de id de acceso
		Query consulta = db.query();
		consulta.constrain(Hashtable.class);
		ObjectSet <Hashtable <String,String>> result = consulta.execute();
		return result.get(0);	
	}

	/**
	 * Elimina las equivalencias de nif y correo para un id de usuario.
	 * @param usuario - el usuario para eliminar sus equivalencias de id.
	 */
	private void borrarEquivalenciaId(Usuario usuario) {
		//Obtiene mapa de equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//Borra equivalencias 
		mapaEquivalencias.remove(usuario.getId());
		mapaEquivalencias.remove(usuario.getNif().getTexto());
		mapaEquivalencias.remove(usuario.getCorreo().getTexto());
		//actualiza datos
		db.store(mapaEquivalencias);	
	}

} //class