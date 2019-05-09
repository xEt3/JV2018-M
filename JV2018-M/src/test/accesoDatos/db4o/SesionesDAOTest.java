/** 
 * Proyecto: Juego de la vida.
 * Clase JUnit5 de prueba automatizada de las características
 * de la clase SesionesDAO según el modelo2.1
 * @since: prototipo2.1
 * @source: SesionesDAOTest.java
 * @version: 2.1 - 2019.05.03
 * @author: Grupo 1
 * @author: Miguel Fernández Piñero (MiguelFerPi)
 * @author: Jesús Pérez Robles (jebles)
 */

package accesoDatos.db4o;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import accesoDatos.db4o.SesionesDAO;
import modelo.SesionUsuario;
import modelo.SesionUsuario.EstadoSesion;
import modelo.Usuario;
import util.Fecha;

public class SesionesDAOTest {

	private static SesionUsuario sesion1;
	private static SesionUsuario sesion2;
	private SesionesDAO sesionesDAO;

	/**
	 * Método que se ejecuta antes de cada @Test
	 */
	@BeforeEach
	public void reiniciarBaseDeDatos() {
		// Objetos no modicados en las pruebas.
		try {
			sesionesDAO = SesionesDAO.getInstance();
			sesionesDAO.borrarTodo();
			sesion1 = new SesionUsuario(new Usuario(), new Fecha(2019, 3, 1), EstadoSesion.ACTIVA);
			sesion2 = new SesionUsuario(new Usuario(), new Fecha(2019, 3, 2), EstadoSesion.ACTIVA);
		} catch (Exception e) {
			System.out.println(e.toString());
			fail(e.toString());
		}
	}

	@Test
	public void testAltaYObtener() {
		try {
			sesionesDAO.alta(sesion1);
			SesionUsuario sesion = sesionesDAO.obtener(sesion1.getId());
			assertSame(sesion1, sesion);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testObtenerTodos() {
		try {
			sesionesDAO.alta(sesion1);
			sesionesDAO.alta(sesion2);
			List<SesionUsuario> todasSesiones = sesionesDAO.obtenerTodos();

			assertEquals(sesion1, todasSesiones.get(0));
			assertEquals(sesion2, todasSesiones.get(1));
		} catch (Exception e) {
			System.out.println(e.toString());
			fail(e.toString());
		}
	}
}
