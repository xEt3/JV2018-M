/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Mundo.
 * Utilizando base de datos db4o.
 * Colabora en el patron Fachada.
 * @since: prototipo2.0
 * @source: MundosDAO.java 
 * @version: 2.1 - 2019.05.03
 * @author: ajp
 */

package accesoDatos.db4o;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import config.Configuracion;
import modelo.ModeloException;
/** 
 * Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos del almacenamiento del
 *  DTO Mundo utilizando base de datos db4o.
 *  Colabora en el patron Fachada.
 *  @since: prototipo2.2
 *  @source: MundosDAO.java 
 *  @version: 1.1 - 2016/06/02 
 *  @author: ajp
 */
import modelo.Mundo;

public class MundosDAO implements OperacionesDAO {

	// Singleton.
	private static MundosDAO instance;

	// Elemento de almacenamiento, base datos db4o.
	private ObjectContainer db;

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instance
	 */
	public static MundosDAO getInstance() {
		if (instance == null) {
			instance = new MundosDAO();
		}
		return instance;
	}

	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private MundosDAO() {
		db = Conexion.getDB();
		if (obtener(Configuracion.get().getProperty("mundo.nombrePredeterminado")) == null) {
			cargarPredeterminados();
		}
	}

	/**
	 *  Método para generar de datos predeterminados.
	 */
	private void cargarPredeterminados() {
		try {	
			Mundo mundoDemo = new Mundo(); // En este array los 0 indican celdas con célula muerta y los 1 vivas
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

	/**
	 *  Cierra conexión.
	 */
	@Override
	public void cerrar() {
		db.close();
	}

	//OPERACIONES DAO
	/**
	 * Obtiene objeto Mundo dado su nombre.
	 * @param nombreMundo - el nombre de Mundo a buscar.
	 * @return - el Mundo encontrado o null si no existe.
	 */	
	@Override
	public Mundo obtener(String nombreMundo) {
		Query consulta = db.query();
		consulta.constrain(Mundo.class);
		consulta.descend("nombre").constrain(nombreMundo).equal();
		ObjectSet<Mundo> result = consulta.execute();
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * Búsqueda de Usuario dado un objeto, reenvía al método que utiliza nombre.
	 * @param obj - el Mundo a buscar.
	 * @return - el Mundo encontrado o null si no existe.
	 */
	public Mundo obtener(Object obj) throws DatosException  {
		return this.obtener(((Mundo) obj).getNombre());
	}

	/**
	 * Obtiene todos los objetos Mundo almacenados.
	 * @return - la List con todos los mundos.
	 */
	@Override
	public List<Mundo> obtenerTodos() {
		Query consulta = db.query();
		consulta.constrain(Mundo.class);
		return consulta.execute();
	}

	/**
	 *  Alta de un nuevo Mundo sin repeticiones según el campo nombre. 
	 *  @param obj - Mundo a buscar y obtener.
	 *  @throws DatosException - si ya existe.
	 */	
	@Override
	public void alta(Object obj) throws DatosException {
		assert obj != null;
		Mundo mundo = (Mundo) obj;
		if (obtener(mundo.getNombre()) == null) {
			db.store(mundo);
			return;
		}
		throw new DatosException("Alta: " + mundo.getNombre() + " ya existe.");
	}

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param nombreMundo - el nombre del Mundo a eliminar.
	 * @return - el Mundo eliminado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Mundo baja(String nombreMundo) throws DatosException {
		assert nombreMundo != null;
		assert nombreMundo != "";
		assert nombreMundo != " ";
		Mundo mundo = obtener(nombreMundo);
		if (mundo != null) {
			db.delete(mundo);
			return mundo;
		}
		throw new DatosException("Baja: " + nombreMundo + " no existe...");
	}

	/**
	 *  Actualiza datos de un Mundo reemplazando el almacenado por el recibido.
	 *	@param obj - Mundo con las modificaciones.
	 *  @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		assert obj != null;
		Mundo mundo = (Mundo) obj;
		Mundo mundoActualizado = obtener(mundo.getNombre());
		if (mundoActualizado != null) {	
			mundoActualizado.setEspacio(mundo.getEspacio());
			mundoActualizado.setDistribucion(mundo.getDistribucion());
			mundoActualizado.setConstantes(mundo.getConstantes());
			mundoActualizado.setTipoMundo(mundo.getTipoMundo());
			db.store(mundoActualizado);
		}  	
		throw new DatosException("(Actualizar: " + mundo.getNombre() + " no existe.");
	}

	/**
	 * Obtiene el listado de todos los objetos Mundo almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder listado = new StringBuilder();
		Query consulta = db.query();
		consulta.constrain(Mundo.class);
		ObjectSet<Mundo> result = consulta.execute();
		for (Mundo mundo: result) {
			listado.append("\n" + mundo);
		}
		return listado.toString();
	}

	/**
	 * Obtiene el listado de todos los identificadores de mundos almacenados.
	 * @return el texto con el volcado de datos.
	 */
	public String listarId() {
		StringBuilder listado = new StringBuilder();
		Query consulta = db.query();
		consulta.constrain(Mundo.class);
		ObjectSet<Mundo> result = consulta.execute();
		for (Mundo mundo: result) {
			if (mundo != null) {
				listado.append(mundo.getNombre() + "\n");
			}
		}
		return listado.toString();
	}

	/**
	 * Quita todos los objetos Mundo de la base de datos.
	 */
	@Override
	public void borrarTodo() {
		// Elimina cada uno de los objetos obtenidos.
		for (Mundo mundo: obtenerTodos()) {
			db.delete(mundo);
		}
		cargarPredeterminados();
	}

} //class
