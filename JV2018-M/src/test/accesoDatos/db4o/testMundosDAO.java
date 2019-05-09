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

package accesoDatos.db4o;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import accesoDatos.DatosException;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Mundo.FormaEspacio;


public class testMundosDAO {

	private static MundosDAO mundoDAO1;
	
	@BeforeAll
	public static void inicializarDatosFijos() {
		mundoDAO1 = MundosDAO.getInstance();
	}
	
	@AfterAll
	public static void limpiarDatosFijos() {
		Conexion.cerrarConexiones();
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
			Mundo mundoObtener = new Mundo("MundoObtener",
											new byte[20][20], 
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
	public void testObtenerTodos(){
		try {
			mundoDAO1.alta(new Mundo("Demo9",new byte[20][20], new LinkedList<>(), new HashMap<>(), FormaEspacio.ESFERICO));
		} 
		catch (DatosException | ModeloException e) {
		}
		assertNotNull(mundoDAO1.obtenerTodos());
	}
	
	
}
