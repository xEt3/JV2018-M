/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Mundo utilizando una base de datos.
 * Aplica el patron Singleton.
 * Colabora en el patrón Façade.
 * @since: prototipo2.1
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

package accesoDatos.mySql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import config.Configuracion;
import modelo.ModeloException;
import modelo.Mundo;

public class MundosDAO implements OperacionesDAO {

	// Singleton.
	private static MundosDAO instance;

	// Elemento de almacenamiento, base datos mySql.
	private Connection db;
	private Statement sentenciaMundo;
	private ArrayList<Object> bufferObjetos;
	private DefaultTableModel tmMundo;
	private ResultSet rsMundo;

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

	/**
	 * Constructor por defecto de uso interno. Sólo se ejecutará una vez.
	 */
	private MundosDAO() {
		db = Conexion.getDB();
		try {
			inicializar();
		} catch (SQLException e) {
		}
		
		if (obtener(Configuracion.get().getProperty("mundo.nombrePredeterminado")) == null) {
			cargarPredeterminados();
		}
	}

	/**
	 * Inicializa el DAO, detecta si existen las tablas de datos capturando la excepcion SQLException
	 * @throws SQLException
	 */
	private void inicializar() throws SQLException {
		db = Conexion.getDB();
		try {
			
			sentenciaMundo = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			crearTablaMundo();
		} catch (SQLException e) {
		}	
		
		
		tmMundo = new DefaultTableModel();
		bufferObjetos = new ArrayList<>();
	}

	private void crearTablaMundo() {
		
	}
	
	/**
	 * Método para generar de datos predeterminados.
	 */
	private void cargarPredeterminados() {
		try {
			Mundo mundoDemo = new Mundo(); // En este array los 0 indican celdas con célula muerta y los 1 vivas
			byte[][] espacioDemo = new byte[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
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
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } //
			};
			mundoDemo.setEspacio(espacioDemo);
			mundoDemo.setTipoMundo(Mundo.FormaEspacio.ESFERICO);
			alta(mundoDemo);
		} catch (DatosException | ModeloException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cierra conexión.
	 */
	@Override
	public void cerrar() {
		
	}

	@Override
	public Object obtener(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Búsqueda de Usuario dado un objeto, reenvía al método que utiliza nombre.
	 * @param obj - el Mundo a buscar.
	 * @return - el Mundo encontrado o null si no existe.
	 */
	public Mundo obtener(Mundo obj) throws DatosException {
		return (Mundo) this.obtener(obj.getId());
	}
	
	@Override
	public List obtenerTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alta(Object obj) throws DatosException {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listarId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void borrarTodo() {
		// TODO Auto-generated method stub
		
	}


} // class
