/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Mundo utilizando un ArrayList.
 * Aplica el patron Singleton.
 * Participa del patron Template Method heredando el método indexSort().
 * Colabora en el patrón Façade.
 * @since: prototipo2.0
 * @source: MundosDAO.java 
 * @version: 2.0 - 2019/03/25
 * @author: ajp
 */

package accesoDatos.fichero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import accesoDatos.memoria.DAOIndexSort;
import config.Configuracion;
import modelo.Identificable;
import modelo.ModeloException;
import modelo.Mundo;

public class MundosDAO extends DAOIndexSort implements OperacionesDAO, Persistente {

	// Singleton.
	private static MundosDAO instance;
	
	// Elementos de almacenamiento.
	private ArrayList<Identificable> datosMundos;
	private File fMundos;

	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private MundosDAO() {
		datosMundos = new ArrayList<Identificable>();
		new File(Configuracion.get().getProperty("datos.nombreDirectorio")).mkdirs();
		fMundos = new File("." + File.separator 
							+ Configuracion.get().getProperty("datos.nombreDirectorio")
							+ File.separator
							+ Configuracion.get().getProperty("mundos.nombreFichero"));

		recuperarDatos();
	}

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
			guardarDatos();
		} 
		catch (DatosException | ModeloException e) {
			e.printStackTrace();
		}
	}

	// OPERACIONES DE PERSISTENCIA
	
	/**
	 *  Recupera el Arraylist datosMundos almacenado en fichero. 
	 * @throws DatosException 
	 */
	@Override
	public void recuperarDatos() {
		if (fMundos.exists()) {
			try {
				FileInputStream fisMundos = new FileInputStream(fMundos);
				ObjectInputStream oisMundos = new ObjectInputStream(fisMundos);
				datosMundos = (ArrayList<Identificable>) oisMundos.readObject();
				oisMundos.close();
				return;
			}
			catch (ClassNotFoundException | IOException e) {	
				e.printStackTrace();
			}
		} 
		cargarPredeterminados();
	}
	
	/**
	 *  Guarda el Arraylist de Mundos en fichero.
	 */
	@Override
	public void guardarDatos() {
		try {
			FileOutputStream fosMundos = new FileOutputStream(fMundos);
			ObjectOutputStream oosSesiones = new ObjectOutputStream(fosMundos);
			oosSesiones.writeObject(datosMundos);		
			oosSesiones.flush();
			oosSesiones.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	// OPERACIONES DAO
	
	/**
	 * Obtiene el objeto dado el id utilizado para el almacenamiento.
	 * @param id - id del mundo a obtener.
	 * @return - el Mundo encontrado; null si no encuentra.
	 */	
	@Override
	public Mundo obtener(String id) {
		assert id != null;
		int posicion = indexSort(id, datosMundos);			// En base 1
		if (posicion >= 0) {
			return (Mundo) datosMundos.get(posicion - 1);   // En base 0
		}
		return null;
	}

	/**
	 * obtiene todos los mundos en una lista.
	 * @return - la lista.
	 */
	@Override
	public List obtenerTodos() {
		return datosMundos;
	}

	/**
	 *  Alta de un objeto en el almacén de datos, 
	 *  sin repeticiones, según el campo id previsto. 
	 *	@param obj - Objeto a almacenar.
	 * @throws DatosException - si ya existe.
	 */
	@Override
	public void alta(Object obj) throws DatosException  {
		assert obj != null;
		Mundo mundoNuevo = (Mundo) obj;										// Para conversión cast
		int posInsercion = indexSort(mundoNuevo.getId(), datosMundos); 
		if (posInsercion < 0) {
			datosMundos.add(Math.abs(posInsercion)-1, mundoNuevo); 			// Inserta la sesión en orden.
		}
		else {
			throw new DatosException("MundosDAO.alta: "+ mundoNuevo.getId() + " ya existe");
		}
	}

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param nombre - el nombre del Mundo a eliminar.
	 * @return - el Mundo eliminado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Mundo baja(String nombre) throws DatosException  {
		assert (nombre != null);
		int posicion = indexSort(nombre, datosMundos); 						// En base 1
		if (posicion > 0) {
			return (Mundo) datosMundos.remove(posicion - 1); 				// En base 0
		}
		else {
			throw new DatosException("MundosDAO.baja: "+ nombre + " no existe");
		}
	}

	/**
	 *  Actualiza datos de un Mundo reemplazando el almacenado por el recibido.
	 *	@param obj - Mundo con las modificaciones.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException  {
		assert obj != null;
		Mundo mundoActualizado = (Mundo) obj;								// Para conversión cast
		int posicion = indexSort(mundoActualizado.getId(), datosMundos); 	// En base 1
		if (posicion > 0) {
			// Reemplaza elemento
			datosMundos.set(posicion - 1, mundoActualizado);  				// En base 0		
		}
		else {
			throw new DatosException("MundosDAO.actualizar : "+ mundoActualizado.getId() + " no existe");
		}
	}

	/**
	 * Obtiene el listado de todos los objetos Mundo almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder result = new StringBuilder();
		for (Identificable mundo: datosMundos) {
			result.append("\n" + mundo);
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
		for (Identificable mundo: datosMundos) {
			result.append("\n" + mundo.getId());
		}
		return result.toString();
	}
	
	/**
	 * Elimina todos los mundos almacenados y regenera el demo predeterminado.
	 */
	@Override
	public void borrarTodo() {
		datosMundos.clear();
		cargarPredeterminados();	
	}

	/**
	 *  Cierra almacenes de datos.
	 */
	@Override
	public void cerrar() {
		guardarDatos();
	}
	
} // class