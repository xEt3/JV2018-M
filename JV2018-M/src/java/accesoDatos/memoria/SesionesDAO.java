/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Patron utilizando un ArrayList.
 * Aplica el patron Singleton.
 * Participa del patron Template Method heredando el método indexSort().
 * Colabora en el patrón Façade.
 * @since: prototipo2.0
 * @source: SesionesDAO.java 
 * @version: 2.0 - 2019/03/25 
 * @author: ajp
 */

package accesoDatos.memoria;

import java.util.ArrayList;
import java.util.List;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.Identificable;
import modelo.ModeloException;
import modelo.SesionUsuario;

public class SesionesDAO extends DAOIndexSort implements OperacionesDAO {

	// Singleton.
	private static SesionesDAO instance;

	// Elemento de almacenamiento. 
	private ArrayList<Identificable> datosSesiones;

	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private SesionesDAO() {
		datosSesiones = new ArrayList<Identificable>();
	}

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instance
	 */
	public static SesionesDAO getInstance() {
		if (instance == null) {
			instance = new SesionesDAO();
		}
		return instance;
	}

	//OPERACIONES DAO
	/**
	 * Búsqueda de sesión por idSesion.
	 * @param id - el idUsr+fecha a buscar.
	 * @return - la sesión encontrada; sin encuentra. 
	 */
	@Override
	public SesionUsuario obtener(String id) {
		assert id != null;
		int posicion = indexSort(id, datosSesiones);					// En base 1
		if (posicion >= 0) {
			return (SesionUsuario) datosSesiones.get(posicion - 1);     // En base 0
		}
		return null;
	}
	
	/**
	 * obtiene todas las sesiones en una lista.
	 * @return - la lista.
	 */
	@Override
	public List obtenerTodos() {
		return datosSesiones;
	}

	/**
	 * Búsqueda de todas la sesiones de un mismo usuario.
	 * @param idUsr - el identificador de usuario a buscar.
	 * @return - Sublista con las sesiones encontrada.
	 * @throws ModeloException 
	 * @throws DatosException - si no existe ninguna.
	 */
	public List<Identificable> obtenerTodasMismoUsr(String idUsr) throws ModeloException, DatosException  {
		assert idUsr != null;
		SesionUsuario aux = new SesionUsuario();
		aux.setUsr(UsuariosDAO.getInstance().obtener(idUsr));
		//Busca posición inserción ordenada por idUsr + fecha. La última para el mismo usuario.
		return separarSesionesUsr(indexSort(aux.getId(), datosSesiones) - 1);
	}

	/**
	 * Separa en una lista independiente de todas las sesiones de un mismo usuario.
	 * @param ultima - el indice de una sesion almacenada.
	 * @return - Sublista con las sesiones encontrada; null si no existe ninguna.
	 */
	private List<Identificable> separarSesionesUsr(int ultima) {
		String idUsr = ((SesionUsuario) datosSesiones.get(ultima)).getUsr().getId();
		int primera = ultima;
		// Localiza primera sesión del mismo usuario.
		for (int i = ultima; i >= 0 && ((SesionUsuario) datosSesiones.get(i)).getUsr().getId().equals(idUsr); i--) {
			primera = i;
		}
		// devuelve la sublista de sesiones buscadas.
		return datosSesiones.subList(primera, ultima+1);
	}

	/**
	 * Alta de una nueva SesionUsuario en orden y sin repeticiones según IdUsr + fecha. 
	 * Busca previamente la posición que le corresponde por búsqueda binaria.
	 * @param obj - la SesionUsuario a almacenar.
	 * @throws DatosException - si ya existe.
	 */
	@Override
	public void alta(Object obj) throws DatosException  {
		assert obj != null;
		SesionUsuario sesionNueva = (SesionUsuario) obj;							// Para conversión cast
		int posInsercion = indexSort(sesionNueva.getId(), datosSesiones); 
		if (posInsercion < 0) {
			datosSesiones.add(Math.abs(posInsercion)-1, sesionNueva); 				// Inserta la sesión en orden.
		}
		else {
			throw new DatosException("SesionesDAO.alta: "+ sesionNueva.getId() + " ya existe");
		}
	}

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param idSesion - identificador de la SesionUsuario a eliminar.
	 * @return - la SesionUsuario eliminada.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public SesionUsuario baja(String idSesion) throws DatosException  {
		assert idSesion != null;
		int posicion = indexSort(idSesion, datosSesiones); 							// En base 1
		if (posicion > 0) {
			return (SesionUsuario) datosSesiones.remove(posicion - 1); 				// En base 0
		}
		else {
			throw new DatosException("SesionesDAO.baja: "+ idSesion + " no existe");
		}
	}
	
	/**
	 *  Actualiza datos de una SesionUsuario reemplazando el almacenado por el recibido.
	 *	@param obj - SesionUsuario con las modificaciones.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		assert obj != null;
		SesionUsuario sesionActualizada = (SesionUsuario) obj;				// Para conversión cast
		int posicion = indexSort(sesionActualizada.getId(), datosSesiones); // En base 1
		if (posicion > 0) {
			// Reemplaza elemento
			datosSesiones.set(posicion - 1, sesionActualizada);  			// En base 0		
		}
		else {
			throw new DatosException("SesionesDAO.actualizar: "+ sesionActualizada.getId() + " no existe");
		}
	}

	/**
	 * Obtiene el listado de todos las sesiones almacenadas.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder result = new StringBuilder();
		for (Identificable sesiones: datosSesiones) {
			result.append("\n" + sesiones);
		}
		return result.toString();
	}

	/**
	 * Obtiene el listado de todos id de los objetos almacenados.
	 * @return el texto con el volcado de id.
	 */
	@Override
	public String listarId() {
		StringBuilder result = new StringBuilder();
		for (Identificable sesiones: datosSesiones) {
			result.append("\n" + sesiones.getId()); 
		}
		return result.toString();
	}
	
	/**
	 * Elimina todos las sesiones almacenadas.
	 */
	@Override
	public void borrarTodo() {
		datosSesiones.clear();	
	}

	/**
	 * Total de sesiones almacenadas.
	 */
	public int totalRegistrado() {
		return datosSesiones.size();
	}

}//class
