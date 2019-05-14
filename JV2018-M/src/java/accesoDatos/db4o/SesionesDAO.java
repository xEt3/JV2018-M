/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Patron utilizando un ArrayList.
 * Aplica el patron Singleton.
 * Participa del patron Template Method heredando el método indexSort().
 * Colabora en el patrón Façade.
 * @since: prototipo2.1
 * @source: SesionesDAO.java
 * @version: 2.1 - 2019.05.03
 * @author: Grupo 1
 * @author: Miguel Fernández Piñero (MiguelFerPi)
 * @author: Jesús Pérez Robles (jebles)
 */

package accesoDatos.db4o;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.SesionUsuario;

public class SesionesDAO implements OperacionesDAO {

	// Singleton.
	private static SesionesDAO instance;

	// Base de datos
	private ObjectContainer db;

	// Constructor
	private SesionesDAO() {
		db = Conexion.getInstance();
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
	 * Obtención de la Sesion
	 * 
	 * @param idSesion - string con la id de la sesión
	 * @return SesionUsuario si la encuentra
	 */
	@Override
	public SesionUsuario obtener(String idSesion) {
		assert idSesion != null;

		List<SesionUsuario> sesiones = db.query(new Predicate<SesionUsuario>() {
			public boolean match(SesionUsuario sesion) {
				return sesion.getId().equals(idSesion);
			}
		});

		if (sesiones.size() > 0) {
			return sesiones.get(0);
		}
		return null;
	}

	/**
	 * Obtención de la lista de Sesiones almacenadas
	 * 
	 * @return lista de Sesiones de Usuario
	 */
	@Override
	public List obtenerTodos() {
		Query query = db.query();
		query.constrain(SesionUsuario.class);
		ObjectSet<SesionUsuario> result = query.execute();
		return result;
	}

	/**
	 * Búsqueda de todas la sesiones de un mismo usuario.
	 * 
	 * @param idUsr - el identificador de usuario a buscar.
	 * @return - Sublista con las sesiones encontrada.
	 * @throws DatosException - si no existe ninguna.
	 */
	public List<SesionUsuario> obtenerTodosMismoUsr(String idUsr) throws DatosException {
		assert idUsr != null;

		Query query = db.query();
		query.constrain(SesionUsuario.class);
		query.descend("usr").descend("id").constrain(idUsr);
		ObjectSet<SesionUsuario> result = query.execute();

		if (result.size() > 0) {
			return result;
		} else {
			throw new DatosException("No existe ninguna sesión de " + idUsr + ".");
		}
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
		SesionUsuario sesionNueva = (SesionUsuario) obj;
		if (obtener(sesionNueva.getId()) == null) {
			db.store(sesionNueva);
		} else {
			throw new DatosException("SesionesDAO.alta: " + sesionNueva.getId() + " ya existe");
		}
	}

	/**
	 * Baja de una sesion existente
	 * 
	 * @param id de la sesion
	 * @throws DatosException - si no la encuentra
	 */
	@Override
	public Object baja(String id) throws DatosException {
		assert id != null;
		SesionUsuario sesionBD = obtener(id);
		if (sesionBD != null) {
			db.delete(sesionBD);
			return sesionBD;
		} else {
			throw new DatosException("SesionesDAO.baja: " + id + " no existe");
		}
	}

	@Override
	public void actualizar(Object obj) throws DatosException {
		assert obj != null;
		SesionUsuario sesionBD = obtener(((SesionUsuario) obj).getId());
		SesionUsuario sesionRef = (SesionUsuario) obj;
		if (sesionBD != null) {
			sesionBD.setEstado(sesionRef.getEstado());
			sesionBD.setFecha(sesionRef.getFecha());
			sesionBD.setUsr(sesionRef.getUsr());
			db.store(sesionBD);
		} else {
			throw new DatosException("SesionesDAO.actualizar: sesion no encontrada");
		}
	}

	@Override
	public String listarDatos() {
		// TODO OperacionesDAO.listarDatos
		return null;
	}

	@Override
	public String listarId() {
		// TODO OperacionesDAO.listarId
		return null;
	}

	/**
	 * Elimina todas los objetos SesionUsuario en la base de datos.
	 */
	@Override
	public void borrarTodo() {
		Query query = db.query();
		query.constrain(SesionUsuario.class);
		ObjectSet<SesionUsuario> result = query.execute();

		while (result.hasNext()) {
			db.delete(result.next());
		}
	}

}
