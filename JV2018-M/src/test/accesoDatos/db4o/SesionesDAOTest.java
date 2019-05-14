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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import accesoDatos.db4o.SesionesDAO;
import modelo.*;
import modelo.SesionUsuario.EstadoSesion;
import modelo.Usuario.RolUsuario;
import util.*;

public class SesionesDAOTest {

	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;
	private SesionUsuario sesion1;
	private SesionUsuario sesion1_2;
	private SesionUsuario sesion2;
	private SesionUsuario sesion3;
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
			usuario1 = new Usuario();
			usuario2 = new Usuario(new Nif("00000002W"), "Perico", "Pérez López", new DireccionPostal(), new Correo(),
					new Fecha(1980, 10, 1), new Fecha(), new ClaveAcceso("Miau#2"), RolUsuario.NORMAL);
			usuario3 = new Usuario(new Nif("00000003A"), "Andrés", "Armando Carreras", new DireccionPostal(),
					new Correo(), new Fecha(1980, 11, 1), new Fecha(), new ClaveAcceso("Miau#3"), RolUsuario.NORMAL);
			sesion1 = new SesionUsuario(usuario1, new Fecha(2019, 3, 1), EstadoSesion.ACTIVA);
			sesion1_2 = new SesionUsuario(usuario1, new Fecha(2019, 3, 2), EstadoSesion.ACTIVA);
			sesion2 = new SesionUsuario(usuario2, new Fecha(2019, 3, 2), EstadoSesion.ACTIVA);
			sesion3 = new SesionUsuario(usuario3, new Fecha(2019, 3, 3), EstadoSesion.ACTIVA);
		} catch (Exception e) {
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
			fail(e.toString());
		}
	}

	@Test
	public void testObtenerTodos() {
		try {
			sesionesDAO.alta(sesion1);
			sesionesDAO.alta(sesion2);
			sesionesDAO.alta(sesion3);
			List<SesionUsuario> todasSesiones = sesionesDAO.obtenerTodos();

			assertEquals(sesion1, todasSesiones.get(0));
			assertEquals(sesion2, todasSesiones.get(1));
			assertEquals(sesion3, todasSesiones.get(2));
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	public void testObtenerTodosMismoUsuario() {
		try {
			sesionesDAO.alta(sesion1);
			sesionesDAO.alta(sesion1_2);
			sesionesDAO.alta(sesion2);
			sesionesDAO.alta(sesion3);
			List<SesionUsuario> todasSesiones = sesionesDAO.obtenerTodosMismoUsr(usuario1.getId());

			if (todasSesiones.size() == 2) {
				for (SesionUsuario sesion : todasSesiones) {
					assertEquals(usuario1, sesion.getUsr());
				}
			} else {
				fail("SesionesDAOTest.testObtenerTodosMismoUsuario: Se encontraron " + todasSesiones.size()
						+ " sesiones cuando debían de ser 2.");
			}

		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	public void testActualizar() {
		try {
			sesionesDAO.alta(sesion1);
			sesion1.setFecha(sesion2.getFecha());
			sesionesDAO.actualizar(sesion1);

			assertEquals(sesionesDAO.obtener(sesion1.getId()).getFecha(), sesion2.getFecha());
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	public void testBaja() {
		try {
			sesionesDAO.alta(sesion1);
			sesionesDAO.alta(sesion2);
			String id = sesion2.getId();
			sesionesDAO.baja(id);

			assertNull(sesionesDAO.obtener(sesion2.getId()));
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testBorrarTodos() {
		try {
			sesionesDAO.alta(sesion1);
			sesionesDAO.alta(sesion2);
			sesionesDAO.alta(sesion3);
			sesionesDAO.borrarTodo();
			List<SesionUsuario> sesiones = sesionesDAO.obtenerTodos();
			
			for (SesionUsuario sesionUsuario : sesiones) {
				fail("No debe llegar aquí");
			}
		} catch (Exception e) {
			fail(e.toString());
		}
	}
}
