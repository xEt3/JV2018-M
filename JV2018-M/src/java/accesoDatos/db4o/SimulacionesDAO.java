/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento de Simulaciones en base de datos db4o.
 * Aplica el patron Singleton.
 * Colabora en el patrón Façade.
 * @since: prototipo2.0
 * @source: SimulacionesDAO.java 
 * @version: 2.1 - 2019/05/20
 * @author: Nicolas Fernando Rodriguez Bon
 * @author: Adrian Martinez Martinez
 * @author: Emilio Muñoz Navarro
 * @author: Francisco Mendoza Ruiz
 * @author: Sergio Franco Gonzalez
 * @author: Jose Miguel Hernandez Rodriguez
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
import config.Configuracion;
import modelo.ModeloException;
import modelo.SesionUsuario;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario;
import util.Fecha;

public class SimulacionesDAO implements OperacionesDAO {

private static SimulacionesDAO instance;
	
	private ObjectContainer db;
	
	private SimulacionesDAO() {
		db = Conexion.getDB();
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

	
	/**
	 * Obtiene de todas las simulaciones por IdUsr de usuario.
	 * @param idUsr - el idUsr a buscar.
	 * @return - las simulaciones encontradas.
	 */
	public List<Simulacion> obtenerTodasMismoUsr(String idUsr) {
		Query consulta = db.query();
		consulta.constrain(Simulacion.class);
		consulta.descend("usr").descend("id").constrain(idUsr).equal();
		return consulta.execute();	
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
		StringBuffer result = new StringBuffer();
		Query query = db.query();
		query.constrain(SesionUsuario.class);
		query.descend("simulacion").descend("id");
		ObjectSet <Simulacion> listaIdSes = query.execute();
		while (listaIdSes.hasNext()) {
			result.append(listaIdSes.next().toString()+("\n")) ;
		}
		return result.toString();
	}

	//Se usa
    @Override
    public void borrarTodo() {
        ObjectSet<Simulacion> result = db.queryByExample(Simulacion.class);
        while(result.hasNext()) {
            db.delete(result.next());
        }
    }
} //class
