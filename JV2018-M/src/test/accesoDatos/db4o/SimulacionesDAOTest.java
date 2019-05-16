package accesoDatos.db4o;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.Mundo;
import modelo.Nif;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario;
import modelo.Mundo.FormaEspacio;
import modelo.Usuario.RolUsuario;
import util.Fecha;

class SimulacionesDAOTest {

	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;
	private Mundo mundo1;
	private Mundo mundo2;
	private Mundo mundo3;
	private Simulacion simulacion1;
	private Simulacion simulacion1_2;
	private Simulacion simulacion2;
	private Simulacion simulacion3;
	private Simulacion simulacionPre;
	private SimulacionesDAO simulacionesDAO;
	
	@BeforeEach
	public void reiniciarBaseDeDatos() {
		// Objetos no modicados en las pruebas.
		try {
			simulacionesDAO = SimulacionesDAO.getInstance();
			simulacionesDAO.borrarTodo();
			usuario1 = new Usuario();
			usuario2 = new Usuario(new Nif("00000002W"), "Perico", "Pérez López", new DireccionPostal(), new Correo(),
					new Fecha(1980, 10, 1), new Fecha(), new ClaveAcceso("Miau#2"), RolUsuario.NORMAL);
			usuario3 = new Usuario(new Nif("00000003A"), "Andrés", "Armando Carreras", new DireccionPostal(),
					new Correo(), new Fecha(1980, 11, 1), new Fecha(), new ClaveAcceso("Miau#3"), RolUsuario.NORMAL);
			mundo1 = new Mundo();
			mundo2 = new Mundo("Demo2",new byte[20][20], new LinkedList<>(), new HashMap<>(), FormaEspacio.ESFERICO);
			mundo3 = new Mundo("Demo3",new byte[20][20], new LinkedList<>(), new HashMap<>(), FormaEspacio.ESFERICO);
			simulacion1 = new Simulacion(usuario1, new Fecha(2019, 3, 1),mundo1,Integer.parseInt(Configuracion.get().getProperty("simulacion.ciclosPredeterminados")), EstadoSimulacion.PREPARADA);
			simulacion1_2 = new Simulacion(usuario1, new Fecha(2019, 3, 2),mundo1,Integer.parseInt(Configuracion.get().getProperty("simulacion.ciclosPredeterminados")), EstadoSimulacion.PREPARADA);
			simulacion2 = new Simulacion(usuario2, new Fecha(2019, 3, 2),mundo2,Integer.parseInt(Configuracion.get().getProperty("simulacion.ciclosPredeterminados")), EstadoSimulacion.PREPARADA);
			simulacion3 = new Simulacion(usuario3, new Fecha(2019, 3, 3),mundo3,Integer.parseInt(Configuracion.get().getProperty("simulacion.ciclosPredeterminados")), EstadoSimulacion.PREPARADA);
			simulacionPre = new Simulacion((Usuario)UsuariosDAO.getInstance().obtener(new Usuario().getId()),
					new Fecha(Configuracion.get().getProperty("fecha.predeterminadaFija")), 
					MundosDAO.getInstance().obtener(Configuracion.get().getProperty("mundo.nombrePredeterminado")),
					Integer.parseInt(Configuracion.get().getProperty("simulacion.ciclosPredeterminados")),
					EstadoSimulacion.PREPARADA);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testAltaYObtener() {
		try {
			simulacionesDAO.alta(simulacion1);
			Simulacion simulacion = simulacionesDAO.obtener(simulacion1.getId());

			assertSame(simulacion1, simulacion);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testObtenerTodos() {
		try {
			simulacionesDAO.alta(simulacion1);
			simulacionesDAO.alta(simulacion2);
			simulacionesDAO.alta(simulacion3);
			List<Simulacion> todasSimulaciones = simulacionesDAO.obtenerTodos();

			assertEquals(simulacion1, todasSimulaciones.get(0));
			assertEquals(simulacion2, todasSimulaciones.get(1));
			assertEquals(simulacion3, todasSimulaciones.get(2));
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testObtenerTodosMismoUsuario() {
		try {
			simulacionesDAO.alta(simulacion1);
			simulacionesDAO.alta(simulacion1_2);
			simulacionesDAO.alta(simulacion2);
			simulacionesDAO.alta(simulacion3);
			List<Simulacion> todasSimulaciones = simulacionesDAO.obtenerTodasMismoUsr(simulacion1.getId());

			if (todasSimulaciones.size() == 2) {
				for (Simulacion simulacion : todasSimulaciones) {
					assertEquals(usuario1, simulacion.getUsr());
				}
			} else {
				fail("SimulacionesDAOTest.testObtenerTodasMismoUsuario: Se encontraron " + todasSimulaciones.size()
						+ " simulaciones cuando debían de ser 2.");
			}

		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testAlta() {
		try {
			simulacionesDAO.alta(simulacion1);
			simulacionesDAO.alta(simulacion2);
			simulacionesDAO.alta(simulacion3);
			List<Simulacion> todasSimulaciones = simulacionesDAO.obtenerTodos();
			
			int tamInicial = todasSimulaciones.size();
			simulacionesDAO.baja(simulacion3.getId());
			todasSimulaciones = simulacionesDAO.obtenerTodos();
			
			assertNotEquals(tamInicial,todasSimulaciones.size());
			
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testBorrarTodo() {
		try {
			simulacionesDAO.alta(simulacion1);
			simulacionesDAO.alta(simulacion2);
			simulacionesDAO.alta(simulacion3);
			int tamInicial = simulacionesDAO.obtenerTodos().size();
			
			simulacionesDAO.borrarTodo();
			
			assertNotEquals(tamInicial,simulacionesDAO.obtenerTodos().size());
			
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testActualizar() {
		try {
			simulacionesDAO.alta(simulacion1);
			simulacion1.setFecha(simulacion2.getFecha());
			simulacionesDAO.actualizar(simulacion1);

			assertEquals(simulacionesDAO.obtener(simulacion1.getId()).getFecha(), simulacion2.getFecha());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testListarDatos() {
		try {
			simulacionesDAO.alta(simulacion1);
			simulacionesDAO.alta(simulacion2);
			simulacionesDAO.alta(simulacion3);
			String simulaciones = simulacionesDAO.listarDatos();
			assertNotNull(simulaciones);
			
		} catch (Exception e){
			fail(e.toString());
		}
	}
	
	@Test
	public void testListarId() {
		try {
			simulacionesDAO.alta(simulacion1);
			simulacionesDAO.alta(simulacion2);
			simulacionesDAO.alta(simulacion3);
			String simulaciones = simulacionesDAO.listarId();
			assertNotNull(simulaciones);
			
		} catch (Exception e) {
			fail(e.toString());
		}
		
	}

}
