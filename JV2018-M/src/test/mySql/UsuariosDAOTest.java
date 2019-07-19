/** 
 *  Proyecto: Juego de la vida.
 *  Clase JUnit 5 para pruebas del DAO de usuarios y la parte de la fachada de Datos correspondiente.
 *  @since: prototipo2.1
 *  @source: UsuariosDAO.java 
 *  @version: 2.1 - 2019/05/09 
 *  @author: ajp
 */

package mySql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import accesoDatos.mySql.MundosDAO;
import accesoDatos.mySql.UsuariosDAO;
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Nif;
import modelo.Usuario;
import modelo.Mundo.FormaEspacio;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class UsuariosDAOTest {

	private static UsuariosDAO usuariosDAO1;


	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 * @throws DatosException 
	 */
	@BeforeAll
	public static void crearFachadaDatos() {
		usuariosDAO1 = UsuariosDAO.getInstance();
	}
	
	@AfterAll
	public static void limpiarDatosFijos() {
		usuariosDAO1.cerrar();
	}
	
	@BeforeEach
	public void borrarDatosFijos() {
		usuariosDAO1.borrarTodo();
	}
	
	@Test
	public void testObtenerUsuarioId() {
		assertEquals(usuariosDAO1.obtener("III1R").getId(), "III1R");
	}

	@Test
	public void testObtenerUsuario() {
		try {
			// Usuario con idUsr "PMA8P"
			Usuario usrPrueba =  new Usuario(new Nif("00000008P"), "Pepe",
					"Márquez Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("pepe@gmail.com"), new Fecha(1990, 11, 12), 
					new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
			
			usuariosDAO1.alta(usrPrueba);
			// Busca el mismo Usuario almacenado.
			assertEquals(usrPrueba, usuariosDAO1.obtener(usrPrueba));
		} catch (ModeloException | DatosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAltaUsuario() {
		try {
			Usuario usrPrueba =  new Usuario(new Nif("54353056P"), "Jose",
					"Perez Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("jose@gmail.com"), new Fecha(1990, 11, 12), 
					new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
			usuariosDAO1.alta(usrPrueba);
			// Busca el mismo Usuario almacenado.
			assertEquals(usrPrueba, usuariosDAO1.obtener(usrPrueba));
		} 
		catch (DatosException | ModeloException e) { 
		}
	}

	@Test
	public void testBajaUsuario() {
		try {
			Usuario usrPrueba =  new Usuario(new Nif("52149567B"), "Fernando",
					"Belmonte Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("fernando@gmail.com"), new Fecha(1990, 11, 12), 
					new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
			usuariosDAO1.alta(usrPrueba);
			usuariosDAO1.baja(usrPrueba.getId());
			assertNull(usuariosDAO1.obtener(usrPrueba.getId()));

		} 
		catch (DatosException | ModeloException e) { 
		}
	}

	@Test
	public void testActualizarUsuario() {
		try {

			Usuario usrPrueba =  new Usuario(new Nif("17197760Q"), "Antonio",
					"Belmonte Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("antonio@gmail.com"), new Fecha(1990, 11, 12), 
					new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
			// Usuario nuevo, que no existe.
			usuariosDAO1.alta(usrPrueba);
			usrPrueba.setApellidos("Ramírez Pinto");
			usuariosDAO1.actualizar(usrPrueba);
			assertEquals(usuariosDAO1.obtener(usrPrueba).getApellidos(), "Ramírez Pinto");
		} 
		catch (DatosException | ModeloException e) {
		}
	}

	@Test
	public void testListarDatos() {
		try {
			Usuario usuario1 = new Usuario(new Nif("17197760Q"), "Antonio",
					"Belmonte Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("antonio@gmail.com"), new Fecha(1990, 11, 12), 
					new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
			Usuario usuario2= new Usuario(new Nif("52149567B"), "Fernando",
					"Belmonte Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("fernando@gmail.com"), new Fecha(1990, 11, 12), 
					new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
			usuariosDAO1.alta(usuario1);
			usuariosDAO1.alta(usuario2);
			List<Usuario> usuarios = usuariosDAO1.obtenerTodos();
			assertEquals(usuariosDAO1.listarDatos(),usuarios.toString());
		} catch (ModeloException | DatosException e) {
			fail("No debe llegar aquí...");
		}
	}
	@Test
	public void testListarId() {
		try {
			Usuario usuario1 = new Usuario(new Nif("17197760Q"), "Antonio",
					"Belmonte Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("antonio@gmail.com"), new Fecha(1990, 11, 12), 
					new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
			Usuario usuario2= new Usuario(new Nif("52149567B"), "Fernando",
					"Belmonte Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("fernando@gmail.com"), new Fecha(1990, 11, 12), 
					new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
			usuariosDAO1.alta(usuario1);
			usuariosDAO1.alta(usuario2);
			StringBuilder idUsuarios = new StringBuilder();
			List<Usuario> usuarios = usuariosDAO1.obtenerTodos();
			for (int i = 0; i < usuarios.size(); i++) {
				idUsuarios.append(usuarios.get(i).getId()+"\n");
			}
			assertEquals(usuariosDAO1.listarId(),idUsuarios.toString());
		} catch (ModeloException | DatosException e) {
			fail("No debe llegar aquí...");
		}
	}

} //class
