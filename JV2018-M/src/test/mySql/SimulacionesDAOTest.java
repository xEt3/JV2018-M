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
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario.RolUsuario;
import modelo.Usuario;
import modelo.Mundo.FormaEspacio;
import util.Fecha;

public class SimulacionesDAOTest {

	private static SimulacionesDAO simulacionesDAO1;
	private static Usuario usuario1;
	private static Usuario usuario2;
	private static Mundo mundo;

	@BeforeAll
	public static void inicializarDatosFijos() {
		simulacionesDAO1 = SimulacionesDAO.getInstance();
		 try {
				usuario1 = new Usuario(new Nif("17197760Q"), "Antonio",
						"Belmonte Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
						new Correo("antonio@gmail.com"), new Fecha(1990, 11, 12), 
						new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
				usuario2= new Usuario(new Nif("52149567B"), "Fernando",
						"Belmonte Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
						new Correo("fernando@gmail.com"), new Fecha(1990, 11, 12), 
						new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
				mundo = new Mundo("Demo123", new byte[20][20], new LinkedList<>(), new HashMap<>(), FormaEspacio.ESFERICO);
		} catch (ModeloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

	@AfterAll
	public static void limpiarDatosFijos() {
		simulacionesDAO1.cerrar();
	}

	@BeforeEach
	public void borrarDatosFijos() {
		simulacionesDAO1.borrarTodo();
	}

	@Test
	public void testSimulacionDAODefecto() {
		assertNotNull(simulacionesDAO1);
	}

	@Test
	public void testSimulacionesDAOAlta() {
		try {
			Simulacion simulacion = new Simulacion(usuario1, new Fecha(),mundo , 5,
					EstadoSimulacion.INICIADA);
			simulacionesDAO1.alta(simulacion);
			assertEquals(simulacionesDAO1.obtener(simulacion.getId()).getId(), simulacion.getId());
		} catch (DatosException e) {
		}

	}

	@Test
	public void testSimulacionesDAOBaja() {
		try {
			Simulacion simulacion = new Simulacion(usuario1,new Fecha(),mundo,5,EstadoSimulacion.INICIADA);
			simulacionesDAO1.alta(simulacion);
			simulacionesDAO1.baja(simulacion.getId());
			assertNull(simulacionesDAO1.obtener(simulacion.getId()));
		} catch (DatosException e) {
		}

	}

	@Test
	public void testSimulacinesDAOObtener() {
		try {
			Simulacion simulacionObtener = new Simulacion(usuario1,new Fecha(),mundo,5,EstadoSimulacion.INICIADA);
			simulacionesDAO1.alta(simulacionObtener);
			assertEquals(simulacionesDAO1.obtener(simulacionObtener.getId()).getId(), simulacionObtener.getId());
		} catch (DatosException e) {

		}
	}

	@Test
	public void testSimulacionesDAOBorrarTodos() {
		StringBuilder result = new StringBuilder("[]");
		simulacionesDAO1.borrarTodo();
		assertEquals(simulacionesDAO1.obtenerTodos().toString(), result.toString());
	}

	@Test
	public void testListarId() {
		try {
			Simulacion simulacion1 = new Simulacion(usuario1,new Fecha(),mundo,5,EstadoSimulacion.INICIADA);
			simulacionesDAO1.alta(simulacion1);
			Simulacion simulacion2 = new Simulacion(usuario2,new Fecha(),mundo,5,EstadoSimulacion.INICIADA);
			simulacionesDAO1.alta(simulacion2);
			
			assertEquals(simulacionesDAO1.listarId(), simulacion1.getId()+"\n"+simulacion2.getId()+"\n");
		} catch (DatosException e) {
			fail("No debe llegar aquí...");
		}
	}

	@Test
	public void testListarDatos() {
		try {
			Simulacion simulacion1 = new Simulacion(usuario1,new Fecha(),mundo,5,EstadoSimulacion.INICIADA);
			simulacionesDAO1.alta(simulacion1);
			Simulacion simulacion2 = new Simulacion(usuario2,new Fecha(),mundo,5,EstadoSimulacion.INICIADA);
			simulacionesDAO1.alta(simulacion2);
			assertEquals(simulacionesDAO1.listarDatos(),simulacion1+"\n"+simulacion2+"\n");
		} catch (DatosException  e) {
			fail("No debe llegar aquí...");
		}
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