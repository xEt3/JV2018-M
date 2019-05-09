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
import modelo.ModeloException;
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
		cargarPredeterminados();
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
		if (!result.isEmpty()) {
			return result.next();
		}
		return null;
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
		Object mundoId = obtener(((Mundo) obj).getId());

		if (mundoId == null) {
			this.db.store(obj);
		} else {
			throw new DatosException("MundosDAO: mundo repetido ");
		}
	}

	@Override
	public Object baja(String id) throws DatosException {
		ObjectSet<Mundo> res = db.queryByExample(obtener(id));
		db.delete(res);
		return res;
	}

	@Override
	public void actualizar(Object obj) throws DatosException {
		Mundo mundoBD = (Mundo) obtener(((Mundo) obj).getId());
		Mundo mundoConCambios = (Mundo) obj;
		mundoBD.setTipoMundo(mundoConCambios.getTipoMundo());
		mundoBD.setEspacio(mundoConCambios.getEspacio());
		mundoBD.setDistribucion(mundoConCambios.getDistribucion());
		db.store(mundoBD);
	}

	@Override
	public String listarDatos() {
		StringBuilder resultado = new StringBuilder();
		ObjectSet<Mundo> res = db.queryByExample(Mundo.class);
		while (res.hasNext()) {
			resultado.append(res.next()).append("\n");
		}
		return resultado.toString();
	}

	@Override
	public String listarId() {
		StringBuilder resultado = new StringBuilder();
		ObjectSet<Mundo> res = db.queryByExample(Mundo.class);
		while (res.hasNext()) {
			resultado.append(res.next().getId()).append("\n");
		}
		return resultado.toString();
	}

	@Override
	public void borrarTodo() {
		ObjectSet<Mundo> res = db.queryByExample(Mundo.class);
		while (res.hasNext()) {
			db.delete(res.next());
		}
	}

	/**
	 *  Método para generar de datos predeterminados.
	 */
	private void cargarPredeterminados() {
		try {	
			Mundo mundoDemo = new Mundo();
			
			// En este array los 0 indican celdas con célula muerta y los 1 vivas
			byte[][] espacioDemo =  new byte[][]{ 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 }, // 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 }, // 
				{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Planeador
				{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Flip-Flop
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Still Life
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }  //
			};
			mundoDemo.setEspacio(espacioDemo);
			mundoDemo.setTipoMundo(Mundo.FormaEspacio.ESFERICO);
			alta(mundoDemo);
		} 
		catch (DatosException | ModeloException e) {
			e.printStackTrace();
		}
	}
} // class