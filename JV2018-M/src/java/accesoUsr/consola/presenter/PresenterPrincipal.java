/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el estado, 
 *  sincronización y lógica de presentación principal del 
 *  programa con un menú. 
 *  Colabora en el patrón MVP.
 *  @since: prototipo2.1
 *  @source: PresenterPrincipal.java 
 *  @version: 2.1 - 2019.05.06
 *  @author: ajp
 */

package accesoUsr.consola.presenter;

import accesoDatos.Datos;
import accesoUsr.consola.vista.VistaPrincipal;
import config.Configuracion;
import modelo.SesionUsuario;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import util.Fecha;

public class PresenterPrincipal {

	private VistaPrincipal vistaPrincipal;
	private SesionUsuario sesionUsr;
	private Datos datos;

	public PresenterPrincipal(String idUsr) {
		initMenuPrincipal(idUsr);	
	}

	public PresenterPrincipal() {
		this(null);
	}

	private void initMenuPrincipal(String idUsr) {
		datos = new Datos();
		vistaPrincipal = new VistaPrincipal();
		sesionUsr = new PresenterlInicioSesion(idUsr).getSesion();
		secuenciaPrincipal();
	}

	private void secuenciaPrincipal() {
		do {
			vistaPrincipal.mostrar();
			vistaPrincipal.pedirOpcion();
			procesarOpcion();	
		} while (true);

	}

	private void procesarOpcion() {
		switch (vistaPrincipal.getOpcionActiva()) {
		case 0:
			salir();

			// Simulaciones
		case 1:
			crearNuevaSimulacion();
			break;
		case 2:
			modificarSimulacion();
			break;
		case 3:
			eliminarSimulacion();
			break;
		case 4:
			mostrarSimulaciones();
			break;
		case 5:
			mostrarIdSimulaciones();
			break;
		case 6:
			ejecutarDemoSimulacion();
			break;

			// Mundos
		case 7:
			crearNuevoMundo();
			break;
		case 8:
			modificarMundo();
			break;
		case 9:
			eliminarMundo();
			break;
		case 10:
			mostrarMundos();
			break;

			// Usuarios
		case 11:
			crearNuevoUsuario();
			break;
		case 12:
			modificarUsuario();
			break;
		case 13:
			eliminarUsuario();
			break;
		case 14:
			mostrarUsuarios();
			break;

			// Sesiones
		case 15:
			modificarSesion();
			break;
		case 16:
			eliminarSesion();
			break;
		case 17:
			mostrarSesiones();
			break;
		case 18:
			mostrarIdSesiones();
			break;
		default:
			vistaPrincipal.mostrarMensaje("Opción incorrecta...");
			break;
		}	
	}

	private void salir() {
		datos.cerrar();
		vistaPrincipal.mostrarMensaje("\nFin de programa...");	
		System.exit(1); 
	}	

	// Simulaciones.
	private void crearNuevaSimulacion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void modificarSimulacion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void eliminarSimulacion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void mostrarSimulaciones() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void mostrarIdSimulaciones() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void ejecutarDemoSimulacion() {
		Simulacion demo = new Simulacion(sesionUsr.getUsr(),
				new Fecha(), 
				datos.obtenerMundo(Configuracion.get().getProperty("mundo.nombrePredeterminado")),
				Integer.parseInt(Configuracion.get().getProperty("simulacion.ciclosPredeterminados")),
				EstadoSimulacion.PREPARADA);
		new PresenterEjecucionSimulacion(demo);
	}

	// Mundos.
	private void crearNuevoMundo() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void modificarMundo() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void eliminarMundo() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void mostrarMundos() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	// Usuarios.	
	private void crearNuevoUsuario() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void modificarUsuario() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void eliminarUsuario() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void mostrarUsuarios() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	// Sesiones.	
	private void modificarSesion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void eliminarSesion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void mostrarSesiones() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	private void mostrarIdSesiones() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

} // class
