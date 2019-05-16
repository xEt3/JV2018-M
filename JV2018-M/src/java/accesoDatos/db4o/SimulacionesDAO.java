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
		assert idUsr != null;
		
		Query query = db.query();
		query.constrain(Simulacion.class);
		query.descend("usr").descend("id").constrain(idUsr);
		ObjectSet<Simulacion> result = query.execute();
		
		if(result.size() > 0) {
			return result;
		}else {
			throw new DatosException("No existe ninguna simulacion de " + idUsr + ".");
		}
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
		Query query = db.query();
		query.constrain(Simulacion.class);
		ObjectSet <Simulacion> lista = query.execute();
		String idUsr = lista.get(ultima).getUsr().getId();
		int primera = ultima;
		for (int i = ultima; i >= 0 && lista.get(i).getUsr().getId().equals(idUsr); i--) {
			primera = i;
		}
		return lista.subList(primera, ultima+1);
	}
	
	
	//Se usa
	public void alta(Object obj) throws DatosException  {
		assert obj != null;
		Simulacion simulacionNueva = (Simulacion) obj;
		if(obtener(simulacionNueva.getId()) == null) {
			db.store(simulacionNueva);
		}else {
			throw new DatosException("SimulacionesDAO.alta: " + simulacionNueva.getId() + " ya existe");
		}
	}

	@Override
	public Simulacion baja(String idSimulacion) throws DatosException  {
		assert idSimulacion != null;
		Simulacion simulacionBD = obtener(idSimulacion);
		if(simulacionBD != null) {
			db.delete(simulacionBD);
			return simulacionBD;
		} else {
			throw new DatosException("SimulacionesDAO.baja: " + idSimulacion + " no existe");
		}
	}
	
	//Se usa
	@Override
	public void actualizar(Object obj) throws DatosException  {
		assert obj != null;
		Simulacion simulacionBD = obtener(((Simulacion) obj).getId());
		Simulacion simulacionRef = (Simulacion) obj;
		if(simulacionBD != null) {
			simulacionBD.setEstado(simulacionRef.getEstado());
			simulacionBD.setFecha(simulacionRef.getFecha());
			simulacionBD.setUsr(simulacionRef.getUsr());
			db.store(simulacionBD);
		}else {
			throw new DatosException("SimulacionesDAO.actualizar: simulacion no encontrara");
		}
	}

	//Se usa
	@Override
	public String listarDatos() {
		StringBuffer result = new StringBuffer();
		Query query = db.query();
		query.constrain(Simulacion.class);		
		ObjectSet <Simulacion> listaS = query.execute();
		while (listaS.hasNext()) {
			result.append(listaS.next().toString()+("\n")) ;
		}
		return result.toString();	
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
} //class
