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
import accesoDatos.mySql.MundosDAO;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Mundo.FormaEspacio;


public class MundosDAOTest {

	private static MundosDAO mundoDAO1;
	
	@BeforeAll
	public static void inicializarDatosFijos() {
		mundoDAO1 = MundosDAO.getInstance();
	}
	
	@AfterAll
	public static void limpiarDatosFijos() {
		mundoDAO1.cerrar();
	}
	
	@BeforeEach
	public void borrarDatosFijos() {
		mundoDAO1.borrarTodo();
	}
	
	@Test
	public void testMundosDAODefecto() {
		assertNotNull(mundoDAO1);	
	}
	
	@Test
	public void testMundosDAOAlta() {
		try {
			Mundo mundo = new Mundo("Demo1", new byte[20][20], new LinkedList<>(), new HashMap<>(), FormaEspacio.ESFERICO);
			mundoDAO1.alta(mundo);
			assertEquals(mundoDAO1.obtener(mundo.getId()), mundo);
		} 
		catch (DatosException | ModeloException e) {
		}
		
	}
	
	@Test
	public void testMundosDAOBaja() {
		try {
			Mundo mundo = new Mundo("Demo2", new byte[20][20], new LinkedList<>(), new HashMap<>(), FormaEspacio.ESFERICO);
			mundoDAO1.alta(mundo);
			mundoDAO1.baja(mundo.getId());
			assertNull(mundoDAO1.obtener(mundo.getId()));
		} 
		catch (DatosException | ModeloException e) {
		}
		
	}
	
	@Test
	public void testObtener() {
		try {
			byte[][] espacio  = new byte[20][20];
			Mundo mundoObtener = new Mundo("MundoObtener",
											espacio, 
											new LinkedList<>(), 
											new HashMap<>(),
											FormaEspacio.ESFERICO);

			mundoDAO1.alta(mundoObtener);
			assertSame(mundoDAO1.obtener("MundoObtener"), mundoObtener);
		} 
		catch (DatosException | ModeloException e) {
			
		}
	}
	
	@Test
	public void testBorrarTodos() {
		StringBuilder result = new StringBuilder("[]");
		mundoDAO1.borrarTodo();
		assertEquals(mundoDAO1.obtenerTodos().toString(), result.toString());
	}
	
	@Test
	public void testListarId() {
		try {
			Mundo mundo1 = new Mundo("Demo123", new byte[20][20], new LinkedList<>(), new HashMap<>(), FormaEspacio.ESFERICO);
			mundoDAO1.alta(mundo1);
			Mundo mundo2 = new Mundo("Demo321", new byte[20][20], new LinkedList<>(), new HashMap<>(), FormaEspacio.ESFERICO);
			mundoDAO1.alta(mundo2);
			assertEquals(mundoDAO1.listarId(),"Demo123\nDemo321\n");
		} catch (DatosException | ModeloException e) {
			fail("No debe llegar aquí...");
		}
	}
	
	@Test
	public void testListarDatos() {
		try {
			Mundo mundo1 = new Mundo("Demo123", new byte[20][20], new LinkedList<>(), new HashMap<>(), FormaEspacio.ESFERICO);
			mundoDAO1.alta(mundo1);
			Mundo mundo2 = new Mundo("Demo321", new byte[20][20], new LinkedList<>(), new HashMap<>(), FormaEspacio.ESFERICO);
			mundoDAO1.alta(mundo2);
			List mundos = mundoDAO1.obtenerTodos();
			StringBuilder mundosAMostrar = new StringBuilder();
			for (int i = 0; i < mundos.size(); i++) {
				mundosAMostrar.append(mundos.get(i)).append("\n");
			}
			assertEquals(mundoDAO1.listarDatos(),mundosAMostrar.toString());
		} catch (DatosException | ModeloException e) {
			fail("No debe llegar aquí...");
		}
	}
	
	@Test
	public void testObtenerTodos(){
		try {
			mundoDAO1.alta(new Mundo("Demo9",new byte[20][20], new LinkedList<>(), new HashMap<>(), FormaEspacio.ESFERICO));
		} 
		catch (DatosException | ModeloException e) {
		}
		assertNotNull(mundoDAO1.obtenerTodos());
	}
	
	
}
