/** Proyecto: Juego de la vida.
 *  Prueba Junit5 de la clase SesionUsuario según el modelo1.1
 *  @since: prototipo1.0
 *  @source: SesionUsuarioTest.java 
 *  @version: 1.1 - 2019/01/22
 *  @author: ajp
 */

package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.SesionUsuario.EstadoSesion;
import util.Fecha;

class SesionUsuarioTest {

	private SesionUsuario sesion1;
	private static SesionUsuario sesion2;
	private static Usuario usr;
	private static Fecha fecha;

	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 */
	@BeforeAll
	public static void iniciarlizarDatosFijos() {
		// Objetos no modicados en las pruebas.
		try {
			usr = new Usuario(new Nif("00000001R"), 
					"Luis", "Roca Mora",
					new DireccionPostal("Roncal", "10", "30130", "Murcia"), 
					new Correo("luis@gmail.com"), 
					new Fecha(2000, 03, 21),
					new Fecha(2018, 10, 17), 
					new ClaveAcceso("Miau#12"), 
					Usuario.RolUsuario.NORMAL);
		} 
		catch (ModeloException e) {
		}
		fecha = new Fecha(2018, 10, 20, 10, 35, 2);
		sesion2 = new SesionUsuario(usr, fecha, EstadoSesion.EN_PREPARACION); 
	}

	/**
	 * Método que se ejecuta una sola vez al final del conjunto pruebas.
	 * No es necesario en este caso.
	 */
	@AfterAll
	public static void limpiarDatosFijos() {
		usr = null;
		fecha = null;
		sesion2 = null;
	}

	/**
	 * Método que se ejecuta antes de cada pruebas.
	 */
	@BeforeEach
	public void iniciarlizarDatosVariables() {	
			try {
				sesion1 = new SesionUsuario();
			} 
			catch (ModeloException e) {
			}
	}

	/**
	 * Método que se ejecuta después de cada pruebas.
	 */
	@AfterEach
	public void borrarDatosPrueba() {	
		sesion1 = null;
	}

	// Test's con DATOS VALIDOS
	@Test
	public void testSesionUsuarioConvencional() {	
		assertEquals(sesion2.getUsr(), usr);
		assertEquals(sesion2.getFecha(), fecha);
	}

	@Test
	public void testSesionUsuarioDefecto() {
		assertNotNull(sesion1.getUsr());
		assertNotNull(sesion1.getFecha());
	}

	@Test
	public void testSesionUsuarioCopia() {
		assertNotSame(sesion2, new SesionUsuario(sesion2));
	}

	@Test
	public void testSetUsr() {
		sesion1.setUsr(usr);
		assertEquals(sesion1.getUsr(), usr);
	}

	@Test
	public void testSetFecha() {
		sesion1.setFecha(fecha);
		assertEquals(sesion1.getFecha(), fecha);
	}

	@Test
	public void testToString() {
		assertNotNull(sesion1.toString());
	}

	// Test's CON DATOS NO VALIDOS

	@Test
	public void testSetUsrNull() {
		try {
			sesion2.setUsr(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) { 
			assertTrue(sesion2.getUsr() != null);
		}
	}
	
	@Test
	public void testSetFechaNull() {
		try {
			sesion2.setFecha(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) { 
			assertTrue(sesion2.getFecha() != null);
		}
	}
	
} // class
