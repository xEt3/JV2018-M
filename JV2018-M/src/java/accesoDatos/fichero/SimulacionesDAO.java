/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Simulacion utilizando un ArrayList.
 * Aplica el patron Singleton.
 * Participa del patron Template Method heredando el método indexSort().
 * Colabora en el patrón Façade.
 * @since: prototipo2.0
 * @source: SimulacionesDAO.java 
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
import modelo.SesionUsuario;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario;
import util.Fecha;

public class SimulacionesDAO extends DAOIndexSort implements OperacionesDAO, Persistente {

	// Singleton. 
	private static SimulacionesDAO instance;

	// Elemento de almacenamiento.
	private ArrayList <Identificable> datosSimulaciones;
	private File fSimulaciones;

	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private SimulacionesDAO() {
		datosSimulaciones = new ArrayList <Identificable>();
		new File(Configuracion.get().getProperty("datos.nombreDirectorio")).mkdirs();
		fSimulaciones = new File("." + File.separator 
							+ Configuracion.get().getProperty("datos.nombreDirectorio")
							+ File.separator
							+ Configuracion.get().getProperty("simulaciones.nombreFichero"));
		recuperarDatos();
	}

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 * @throws DatosException 
	 */
	public static SimulacionesDAO getInstance() throws DatosException {
		if (instance == null) {
			instance = new SimulacionesDAO();
		}
		return instance;
	}

	/**
	 *  Método para generar datos predeterminados. 
	 */
	private void cargarPredeterminados() {
		try {
			alta(new Simulacion(UsuariosDAO.getInstance().obtener(new Usuario().getId()),
								new Fecha(Configuracion.get().getProperty("fecha.predeterminadaFija")), 
								MundosDAO.getInstance().obtener(Configuracion.get().getProperty("mundo.nombrePredeterminado")),
								Integer.parseInt(Configuracion.get().getProperty("simulacion.ciclosPredeterminados")),
								EstadoSimulacion.PREPARADA));
			guardarDatos();
		} 
		catch (DatosException | ModeloException e) {
			e.printStackTrace();
		}
	}

	// OPERACIONES DE PERSISTENCIA
	
	/**
	 *  Recupera el Arraylist datosSimulaciones almacenados en fichero. 
	 * @throws DatosException 
	 */
	@Override
	public void recuperarDatos() {
		if (fSimulaciones.exists()) {
			try {
				FileInputStream fisSimulaciones = new FileInputStream(fSimulaciones);
				ObjectInputStream oisSimulaciones = new ObjectInputStream(fisSimulaciones);
				datosSimulaciones = (ArrayList<Identificable>) oisSimulaciones.readObject();
				oisSimulaciones.close();
				return;
			}
			catch (ClassNotFoundException | IOException e) {	
				e.printStackTrace();
			}
		} 
		cargarPredeterminados();
	}
	
	/**
	 *  Guarda el Arraylist de simulaciones de usuarios en fichero.
	 */
	@Override
	public void guardarDatos() {
		try {
			FileOutputStream fosSimulaciones = new FileOutputStream(fSimulaciones);
			ObjectOutputStream oosSesiones = new ObjectOutputStream(fosSimulaciones);
			oosSesiones.writeObject(datosSimulaciones);		
			oosSesiones.flush();
			oosSesiones.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	// OPERACIONES DAO

	/**
	 * Búsqueda de Simulacion dado idUsr y fecha.
	 * @param id - el idUsr+fecha de la Simulacion a buscar. 
	 * @return - la Simulacion encontrada; null si no encuentra. 
	 */	
	@Override
	public Simulacion obtener(String id) {
		assert id != null;
		int posicion = indexSort(id, datosSimulaciones);				// En base 1
		if (posicion >= 0) {
			return (Simulacion) datosSimulaciones.get(posicion - 1);     // En base 0
		}
		return null;
	}

	/**
	 * obtiene todas las simulaciones en una lista.
	 * @return - la lista.
	 */
	@Override
	public List obtenerTodos() {
		return datosSimulaciones;
	}

	/**
	 * Búsqueda de todas la simulaciones de un usuario.
	 * @param idUsr - el identificador de usuario a buscar.
	 * @return - Sublista con las simulaciones encontrada; null si no existe ninguna.
	 * @throws ModeloException 
	 */
	public List<Identificable> obtenerTodosMismoUsr(String idUsr) throws ModeloException {
		Simulacion aux = null;
		aux = new Simulacion();
		aux.setUsr(UsuariosDAO.getInstance().obtener(idUsr));
		//Busca posición inserción ordenada por idUsr + fecha. La última para el mismo usuario.
		return separarSimulacionesUsr(indexSort(aux.getId(), datosSimulaciones) - 1);
	}

	/**
	 * Separa en una lista independiente todas la simulaciones de un mismo usuario.
	 * @param ultima - el indice de la última simulación ya encontrada.
	 * @return - Sublista con las simulaciones encontrada; null si no existe ninguna.
	 */
	private List<Identificable> separarSimulacionesUsr(int ultima) {
		// Localiza primera simulación del mismo usuario.
		String idUsr = ((SesionUsuario) datosSimulaciones.get(ultima)).getUsr().getId();
		int primera = ultima;
		for (int i = ultima; i >= 0 && ((SesionUsuario) datosSimulaciones.get(i)).getUsr().getId().equals(idUsr); i--) {
			primera = i;
		}
		// devuelve la sublista de simulaciones buscadas.
		return datosSimulaciones.subList(primera, ultima+1);
	}

	/**
	 *  Alta de una nueva Simulacion en orden y sin repeticiones según los idUsr más fecha. 
	 *  Busca previamente la posición que le corresponde por búsqueda binaria.
	 *  @param obj - Simulación a almacenar.
	 * @throws DatosException - si ya existe.
	 */	
	public void alta(Object obj) throws DatosException  {
		assert obj != null;
		Simulacion simulacion = (Simulacion) obj;								// Para conversión cast
		int posInsercion = indexSort(simulacion.getId(), datosSimulaciones); 
		if (posInsercion < 0) {
			datosSimulaciones.add(Math.abs(posInsercion)-1, simulacion); 		// Inserta la simulación en orden.
		}
		else {
			throw new DatosException("SimulacionesDAO.alta: "+ simulacion.getId() + " ya existe");
		}
	}

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param idSimulacion - identificador de la Simulacion a eliminar.
	 * @return - la Simulacion eliminada. 
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Simulacion baja(String idSimulacion) throws DatosException  {
		assert (idSimulacion != null);
		int posicion = indexSort(idSimulacion, datosSimulaciones); 								// En base 1
		if (posicion > 0) {
			return (Simulacion) datosSimulaciones.remove(posicion - 1); 						// En base 0
		}
		else {
			throw new DatosException("SimulacionesDAO.baja: "+ idSimulacion + " no existe");
		}
	}
	
	/**
	 *  Actualiza datos de una Simulacion reemplazando el almacenado por el recibido.
	 *  No admitirá cambios en usr ni en la fecha.
	 *	@param obj - Patron con las modificaciones.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException  {
		assert obj != null;
		Simulacion simulActualizada = (Simulacion) obj;							// Para conversión cast
		int posicion = indexSort(simulActualizada.getId(), datosSimulaciones); 	// En base 1
		if (posicion > 0) {
			// Reemplaza elemento
			datosSimulaciones.set(posicion - 1, simulActualizada);  			// En base 0		
		}
		else {
			throw new DatosException("SimulacionesDAO.actualizar: "+ simulActualizada.getId() + "no existe");
		}
	}

	/**
	 * Obtiene el listado de todos las simulaciones almacenadas.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder result = new StringBuilder();
		for (Identificable simulacion: datosSimulaciones) {
			result.append("\n" + simulacion);
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
		for (Identificable simulacion: datosSimulaciones) {
			result.append("\n" + simulacion.getId()); 
		}
		return result.toString();
	}

	/**
	 * Elimina todos las simulaciones almacenadas y regenera la demo predeterminada.
	 */
	@Override
	public void borrarTodo() {
		datosSimulaciones.clear();
		cargarPredeterminados();
	}

	/**
	 *  Cierra almacenes de datos.
	 */
	@Override
	public void cerrar() {
		guardarDatos();
	}
	
} //class
