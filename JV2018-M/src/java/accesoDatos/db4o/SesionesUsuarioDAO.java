/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento de objetos SesionUsuario en base de datos db4o.
 * Aplica el patron Singleton.
 * Colabora en el patrón Façade.
 * @since: prototipo2.0
 * @source: SesionesDAO.java
 * @version: 2.1 - 2019.05.20
 * @author: Grupo 1
 * @author: Miguel Fernández Piñero (MiguelFerPi)
 * @author: Jesús Pérez Robles (jebles)
 * @author: ajp
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

public class SesionesUsuarioDAO implements OperacionesDAO {

	// Singleton.
	private static SesionesUsuarioDAO instance;

	// Base de datos
	private ObjectContainer db;

	// Constructor
	private SesionesUsuarioDAO() {
		db = Conexion.getDB();
	}

	/**
	 * Método estático de acceso a la instancia única. Si no existe la crea
	 * invocando al constructor interno. Utiliza inicialización diferida. Sólo se
	 * crea una vez; instancia única -patrón singleton-
	 * 
	 * @return instance
	 */
	public static SesionesUsuarioDAO getInstance() {
		if (instance == null) {
			instance = new SesionesUsuarioDAO();
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
	 * Obtiene todas las sesiones por IdUsr de usuario.
	 * @param idUsr - el idUsr a buscar.
	 * @return - las sesiones encontradas.
	 */
	public List<SesionUsuario> obtenerTodasMismoUsr(String idUsr) {
		assert idUsr != null;
		Query consulta = db.query();
		consulta.constrain(SesionUsuario.class);
		consulta.descend("usr").descend("idUsr").constrain(idUsr);
		return consulta.execute();
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
	public SesionUsuario baja(String id) throws DatosException {
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
	/**
	 * @return un String con todos los usuarios 
	 */
	@Override
	public String listarDatos() {
		StringBuffer result = new StringBuffer();
		Query query = db.query();
		query.constrain(SesionUsuario.class);		
		ObjectSet <SesionUsuario> listaSsn = query.execute();
		while (listaSsn.hasNext()) {
			result.append(listaSsn.next().toString()+("\n")) ;
		}
		return result.toString();
	}

	@Override
	public String listarId() {
		StringBuffer result = new StringBuffer();
		Query query = db.query();
		query.constrain(SesionUsuario.class);
		query.descend("usr").descend("id");
		ObjectSet <SesionUsuario> listaIdSes = query.execute();
		while (listaIdSes.hasNext()) {
			result.append(listaIdSes.next().toString()+("\n")) ;
		}
		return result.toString();		
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
