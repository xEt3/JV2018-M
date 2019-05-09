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

package accesoDatos.fichero;

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

	private SimulacionesDAO() {
	}

	public static SimulacionesDAO getInstance() {
	}

	private void cargarPredeterminados() {
	}

	@Override
	public void recuperarDatos() {
	}
	
	@Override
	public void guardarDatos() {
	}
	
	@Override
	public Simulacion obtener(String id) {
	}

	@Override
	public List obtenerTodos() {
	}

	public List<Identificable> obtenerTodasMismoUsr(String idUsr) {
	}

	private List<Identificable> separarSimulacionesUsr(int ultima) {
	}

	public void alta(Object obj) throws DatosException  {
	}

	@Override
	public Simulacion baja(String idSimulacion) throws DatosException  {
	}
	
	@Override
	public void actualizar(Object obj) throws DatosException  {
	}

	@Override
	public String listarDatos() {
	}

	@Override
	public String listarId() {
	}

	@Override
	public void borrarTodo() {
	}

	@Override
	public void cerrar() {
	}
	
} //class
