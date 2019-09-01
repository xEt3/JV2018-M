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
import accesoDatos.mySql.SesionesUsuarioDAO;
import accesoDatos.mySql.SimulacionesDAO;
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Nif;
import modelo.SesionUsuario;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario.RolUsuario;
import modelo.Usuario;
import modelo.Mundo.FormaEspacio;
import modelo.SesionUsuario.EstadoSesion;
import util.Fecha;

public class SesionesUsuarioDAOTest {

	private static SesionesUsuarioDAO sesionesDAO1;
	private static Usuario usuario1;
	private static Usuario usuario2;

	@BeforeAll
	public static void inicializarDatosFijos() {
		sesionesDAO1 = SesionesUsuarioDAO.getInstance();
		 try {
				usuario1 = new Usuario(new Nif("17197760Q"), "Antonio",
						"Belmonte Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
						new Correo("antonio@gmail.com"), new Fecha(1990, 11, 12), 
						new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
				usuario2= new Usuario(new Nif("52149567B"), "Fernando",
						"Belmonte Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
						new Correo("fernando@gmail.com"), new Fecha(1990, 11, 12), 
						new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	@AfterAll
	public static void limpiarDatosFijos() {
		sesionesDAO1.cerrar();
	}

	@BeforeEach
	public void borrarDatosFijos() {
		sesionesDAO1.borrarTodo();
	}

	@Test
	public void testSesionesUsuarioDAODefecto() {
		assertNotNull(sesionesDAO1);
	}
	
	@Test
	public void testSesionesUsuarioDAOAlta() {
		try {
			SesionUsuario sesionUsuario = new SesionUsuario(usuario1, new Fecha(),
					EstadoSesion.ACTIVA);
			sesionesDAO1.alta(sesionUsuario);
			assertEquals(sesionesDAO1.obtener(sesionUsuario.getId()).getId(), sesionUsuario.getId());
		} catch (DatosException e) {
		}
	}
	
	@Test
	public void testSesionesUsuarioDAOBaja() {
		try {
			SesionUsuario sesionUsuario = new SesionUsuario(usuario1,new Fecha(),EstadoSesion.ACTIVA);
			sesionesDAO1.alta(sesionUsuario);
			sesionesDAO1.baja(sesionUsuario.getId());
			assertNull(sesionesDAO1.obtener(sesionUsuario.getId()));
		} catch (DatosException e) {
		}
	}
	
	@Test
	public void testSimulacinesDAOObtener() {
		try {
			SesionUsuario sesionObtener = new SesionUsuario(usuario1,new Fecha(),EstadoSesion.ACTIVA);
			sesionesDAO1.alta(sesionObtener);
			assertEquals(sesionesDAO1.obtener(sesionObtener.getId()).getId(), sesionObtener.getId());
		} catch (DatosException e) {
		}
	}
	
	@Test
	public void testSesionesUsuarioDAOBorrarTodos() {
		StringBuilder result = new StringBuilder("[]");
		sesionesDAO1.borrarTodo();
		assertEquals(sesionesDAO1.obtenerTodos().toString(), result.toString());
	}
	
	@Test
	public void testListarId() {
		try {
			SesionUsuario sesion1 = new SesionUsuario(usuario1,new Fecha(),EstadoSesion.ACTIVA);
			sesionesDAO1.alta(sesion1);
			SesionUsuario sesion2 = new SesionUsuario(usuario2,new Fecha(),EstadoSesion.ACTIVA);
			sesionesDAO1.alta(sesion2);
			assertEquals(sesionesDAO1.listarId(), sesion1.getId()+"\n"+sesion2.getId()+"\n");
		} catch (DatosException e) {
			fail("No debe llegar aquí...");
		}
	}
	
}