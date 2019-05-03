/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Mundo utilizando una base de datos.
 * Aplica el patron Singleton.
 * Colabora en el patrón Façade.
 * @since: prototipo2.0
 * @source: MundosDAO.java 
 * @version: 2.1 - 2019/03/25
 *  @author: Grupo 3
 *  @author Antonio Ruiz
 *  @author Atanas Genchev
 *  @author Roberto Bastida
 *  @author Ignacio Belmonte
 *  @author Ramon Moreno
 *  @author Ramon Moñino
 */

package accesoDatos.db4o;


import java.util.List;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.Mundo;

public class MundosDAO implements OperacionesDAO {

	// Singleton.
	private static MundosDAO instance;

	// Base de datos
	private ObjectContainer db;

	/**
	 * Constructor por defecto de uso interno Solo se ejecutara una vez.
	 */
	private MundosDAO() {
		db = Conexion.getInstance();
	}

	/**
	 * Método estático de acceso a la instancia única. Si no existe la crea
	 * invocando al constructor interno. Utiliza inicialización diferida. Sólo se
	 * crea una vez; instancia única -patrón singleton-
	 * 
	 * @return instance
	 */
	public static MundosDAO getInstance() {
		if (instance == null) {
			instance = new MundosDAO();
		}
		return instance;
	}

	@Override
	public Object obtener(String id) throws DatosException {
		Query query = db.query();
		query.constrain(Mundo.class);
		query.descend("nombre").constrain(id);
		ObjectSet<Mundo> result = query.execute();
		return result.next();
	}

	@Override
	public List obtenerTodos() {
		Query query = db.query();
		query.constrain(Mundo.class);
		ObjectSet<Mundo> result = query.execute();
		return result;
	}

	@Override
	public void alta(Object obj) throws DatosException {
		assert obj != null;
		Object mundoId = obtener(((Mundo)obj).getId());
		
		if (mundoId == null) {
			this.db.store(obj);
		}
		else {
			throw new DatosException("MundosDAO: mundo repetido ");
		}
	}

	@Override
	public Object baja(String id) throws DatosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(Object obj) throws DatosException {
		// TODO Auto-generated method stub

	}

	@Override
	public String listarDatos() {
		StringBuilder resultado = new StringBuilder();
		ObjectSet<Mundo> res = db.queryByExample(Mundo.class);
		while (res.hasNext()) {
		resultado.append(res.next());
		}
	return resultado.toString();
	}

	@Override
	public String listarId() {
		StringBuilder resultado = new StringBuilder();
		ObjectSet<Mundo> res = db.queryByExample(Mundo.class);
		while (res.hasNext()) {
			resultado.append(res.next().getId());
		}
	return resultado.toString();
	}

	@Override
	public void borrarTodo() {
		// TODO Auto-generated method stub

	}

} // class