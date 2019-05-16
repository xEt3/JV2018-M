/** 
 * Proyecto: Juego de la vida.
 * Version de SimulacionesDAO orientada a utilizar db4o
 * @since: prototipo2.1
 * @source: SimulacionesDAO.java 
 * @version: 2.1 - 2019/05/09
 * @author: Nicolas Fernando Rodriguez Bon
 * @author: Adrian Martinez Martinez
 * @author: Emilio Muñoz Navarro
 * @author: Francisco Mendoza Ruiz
 * @author: Sergio Franco Gonzalez
 * @author: Jose Miguel Hernandez Rodriguez
 */

package accesoDatos.db4o;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import config.Configuracion;
import modelo.ModeloException;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario;
import util.Fecha;

public class SimulacionesDAO implements OperacionesDAO {

private static SimulacionesDAO instance;
	
	private ObjectContainer db;
	
	private SimulacionesDAO() {
		db = Conexion.getInstance();
	}

	public static SimulacionesDAO getInstance() {
		if (instance == null) {
			instance = new SimulacionesDAO();
		}
		return instance;
	}
	
	//Se usa 
	@Override
	public Simulacion obtener(String id) {
		assert id != null;
		
		List<Simulacion> simulaciones = db.query(
			new Predicate<Simulacion>() {
				public boolean match(Simulacion simulacion) {
					return simulacion.getId().equals(id);
				}
			}
		);
		
		if(simulaciones.size() > 0) {
			return simulaciones.get(0);
		}
		return null;
	}

	//Se usa
    @Override
    public List obtenerTodos() {
        Query query = db.query();
        query.constrain(Simulacion.class);
        ObjectSet<Simulacion> result = query.execute();
        return result;
    }

	
	public List<Simulacion> obtenerTodasMismoUsr(String idUsr) throws DatosException {
		return null;
	}
	
	
	 
	private void cargarPredeterminados() {
		try {
			Simulacion simulacionDemo;
			simulacionDemo=new Simulacion((Usuario)UsuariosDAO.getInstance().obtener(new Usuario().getId()),
								new Fecha(Configuracion.get().getProperty("fecha.predeterminadaFija")), 
								MundosDAO.getInstance().obtener(Configuracion.get().getProperty("mundo.nombrePredeterminado")),
								Integer.parseInt(Configuracion.get().getProperty("simulacion.ciclosPredeterminados")),
								EstadoSimulacion.PREPARADA);
			if(db.queryByExample(simulacionDemo).isEmpty()) {
				alta(simulacionDemo);
			}
		} 
		catch (DatosException | ModeloException e) {
			e.printStackTrace();
		}
	}
	
	
	private List<Simulacion> separarSimulacionesUsr(int ultima) {
		return null;
	}
	
	
	//Se usa
	public void alta(Object obj) throws DatosException  {
	}

	//Se usa
	@Override
	public Simulacion baja(String idSimulacion) throws DatosException  {
		return null;
	}
	
	//Se usa
	@Override
	public void actualizar(Object obj) throws DatosException  {
	}

	//Se usa
	@Override
	public String listarDatos() {
		return null;
	}

	//Se usa
	@Override
	public String listarId() {
		return null;
	}

	//Se usa
    @Override
    public void borrarTodo() {
    }

	@Override
	public void cerrar() {
	}
} //class
