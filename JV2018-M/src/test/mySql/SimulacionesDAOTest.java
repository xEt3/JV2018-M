/** Proyecto: Juego de la vida.
 *  Prueba Junit5 de la clase MundosDAO.
 *  @since: prototipo2.1
 *  @source: MundosDAO.java 
 *  @version: 2.1 - 2019/05/2
 *  @author: Grupo 3
 *  @author: Ramon Moreno
 *  @author: Ramon Moñino
 *  @author: Ignacio Belmonte
 *  @author: Roberto Bastida
 *  @author: Antonio Ruiz
 *  @author: Atanas Genvech
 */

package mySql;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import accesoDatos.DatosException;
import accesoDatos.mySql.SimulacionesDAO;
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Nif;
import modelo.Mundo.FormaEspacio;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario.RolUsuario;
import modelo.Usuario;
import util.Fecha;

public class SimulacionesDAOTest {

	private static SimulacionesDAO simulacionesDAO1;

	@BeforeAll
	public static void inicializarDatosFijos() {
		simulacionesDAO1 = SimulacionesDAO.getInstance();
	}

	@AfterAll
	public static void limpiarDatosFijos() {
		simulacionesDAO1.cerrar();
	}

	@BeforeEach
	public void borrarDatosFijos() {
		//simulacionesDAO1.borrarTodo();
	}

	@Test
	public void testMundosDAODefecto() {
		//assertNotNull(simulacionesDAO1);
	}

	@Test
	public void testSimulacionesDAOAlta() {
		try {
			Simulacion simulacion = new Simulacion(new Usuario(), new Fecha(), new Mundo(), 5,
					EstadoSimulacion.INICIADA);
			simulacionesDAO1.alta(simulacion);
			assertEquals(simulacionesDAO1.obtener(simulacion.getId()).getId(), simulacion.getId());
		} catch (DatosException | ModeloException e) {
		}

	}

	@Test
	public void testSimulacionesDAOBaja() {
//		try {
//			Usuario usuario1 = new Usuario(new Nif("00000001R"), "Luis", "Roca Mora",
//					new DireccionPostal("Roncal", "10", "30130", "Murcia"), new Correo("luis@gmail.com"),
//					new Fecha(2000, 03, 21), new Fecha(2018, 10, 17), new ClaveAcceso("Miau#12"), RolUsuario.NORMAL);
//			Simulacion simulacion = new Simulacion(usuario1,new Fecha(),new Mundo(),5,EstadoSimulacion.INICIADA);
//			simulacionesDAO1.alta(simulacion);
//			simulacionesDAO1.baja(simulacion.getId());
//			assertNull(simulacionesDAO1.obtener(simulacion.getId()));
//		} catch (DatosException | ModeloException e) {
//		}

	}

	@Test
	public void testSimulacinesDAOObtener() {
//		try {
//			Usuario usuario1 = new Usuario(new Nif("00000001R"), "Luis", "Roca Mora",
//					new DireccionPostal("Roncal", "10", "30130", "Murcia"), new Correo("luis@gmail.com"),
//					new Fecha(2000, 03, 21), new Fecha(2018, 10, 17), new ClaveAcceso("Miau#12"), RolUsuario.NORMAL);
//			
//			Simulacion simulacionObtener = new Simulacion(usuario1,new Fecha(),new Mundo(),5,EstadoSimulacion.INICIADA);
//			
//			simulacionesDAO1.alta(simulacionObtener);
//			assertEquals(simulacionesDAO1.obtener(simulacionObtener.getId()).getId(), simulacionObtener.getId());
//		} catch (DatosException | ModeloException e) {
//
//		}
	}

	@Test
	public void testSimulacionesDAOBorrarTodos() {
//		StringBuilder result = new StringBuilder("[]");
//		simulacionesDAO1.borrarTodo();
//		assertEquals(simulacionesDAO1.obtenerTodos().toString(), result.toString());
	}

	@Test
	public void testListarId() {
//		try {
//			Usuario usuario1 = new Usuario(new Nif("00000001R"), "Luis", "Roca Mora",
//					new DireccionPostal("Roncal", "10", "30130", "Murcia"), new Correo("luis@gmail.com"),
//					new Fecha(2000, 03, 21), new Fecha(2018, 10, 17), new ClaveAcceso("Miau#12"), RolUsuario.NORMAL);
//			Simulacion simulacion1 = new Simulacion(usuario1,new Fecha(),new Mundo(),5,EstadoSimulacion.INICIADA);
//			simulacionesDAO1.alta(simulacion1);
//			
//			Usuario usuario2 = new Usuario(new Nif("00606383B"), "Manuel", "Martin Mora",
//					new DireccionPostal("Roncal", "10", "30130", "Murcia"), new Correo("martin@gmail.com"),
//					new Fecha(2000, 03, 21), new Fecha(2018, 10, 17), new ClaveAcceso("Miau#12"), RolUsuario.NORMAL);
//			Simulacion simulacion2 = new Simulacion(usuario2,new Fecha(),new Mundo(),5,EstadoSimulacion.INICIADA);
//			simulacionesDAO1.alta(simulacion2);
//			
//			assertEquals(simulacionesDAO1.listarId(), simulacion1.getId()+"\n"+simulacion2.getId()+"\n");
//		} catch (DatosException | ModeloException e) {
//			fail("No debe llegar aquí...");
//		}
	}

	@Test
	public void testListarDatos() {
//		try {
//			Mundo mundo1 = new Mundo("Demo123", new byte[20][20], new LinkedList<>(), new HashMap<>(),
//					FormaEspacio.ESFERICO);
//			simulacionesDAO1.alta(mundo1);
//			Mundo mundo2 = new Mundo("Demo321", new byte[20][20], new LinkedList<>(), new HashMap<>(),
//					FormaEspacio.ESFERICO);
//			simulacionesDAO1.alta(mundo2);
//			List<Simulacion> simulaciones = simulacionesDAO1.obtenerTodos();
//			StringBuilder simulacionesAMostrar = new StringBuilder();
//			for (int i = 0; i < simulaciones.size(); i++) {
//				simulacionesAMostrar.append(simulaciones.get(i).getId()).append("\n");
//			}
//			assertEquals(simulacionesDAO1.listarDatos(), simulacionesAMostrar.toString());
//		} catch (DatosException | ModeloException e) {
//			fail("No debe llegar aquí...");
//		}
	}

	@Test
	public void testObtenerTodos() {
//		try {
//			Usuario usuario1 = new Usuario(new Nif("00000001R"), "Luis", "Roca Mora",
//					new DireccionPostal("Roncal", "10", "30130", "Murcia"), new Correo("luis@gmail.com"),
//					new Fecha(2000, 03, 21), new Fecha(2018, 10, 17), new ClaveAcceso("Miau#12"), RolUsuario.NORMAL);
//			Simulacion simulacion1 = new Simulacion(usuario1,new Fecha(),new Mundo(),5,EstadoSimulacion.INICIADA);
//			simulacionesDAO1.alta(simulacion1);
//		} catch (DatosException | ModeloException e) {
//		}
//		assertNotNull(simulacionesDAO1.obtenerTodos());
	}

}