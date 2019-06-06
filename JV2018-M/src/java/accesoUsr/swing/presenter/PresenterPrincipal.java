/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el estado, 
 *  sincronización y lógica de presentación principal del programa.
 *  Colabora en el patrón MVP.
 *  @since: prototipo2.1
 *  @source: PresenterPrincipal.java 
 *  @version: 2.2 - 2019.05.17
 *  @author: ajp
 */

package accesoUsr.swing.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import accesoDatos.Datos;
import accesoUsr.swing.presenter.PresenterEjecucionSimulacion;
import accesoUsr.swing.vista.VistaPrincipal;
import config.Configuracion;
import modelo.SesionUsuario;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import util.Fecha;

public class PresenterPrincipal implements ActionListener, WindowListener {

	private VistaPrincipal vistaPrincipal;
	private SesionUsuario sesion;
	private Datos datos;

	public PresenterPrincipal() {
		initControlPrincipal();	
	}

	private void initControlPrincipal() {
		this.datos = new Datos();
		this.vistaPrincipal = new VistaPrincipal();
		this.configListener();
		this.vistaPrincipal.pack();
		this.vistaPrincipal.setVisible(true);
		this.vistaPrincipal.setEnabled(false);
		new PresenterInicioSesion(this);	
	}
	public void setSesion(SesionUsuario sesion) {
		assert sesion != null;
		this.sesion = sesion;
	}
	
	public VistaPrincipal getVistaPrincipal() {
		return vistaPrincipal;
	}
	
	private void configListener() {
		// Hay que escuchar todos los componentes que tengan interacción de la vista
		// registrándoles la clase control que los escucha.
		this.vistaPrincipal.addWindowListener(this);
		this.vistaPrincipal.getMntnGuardar().addActionListener(this);
		this.vistaPrincipal.getMntnSalir().addActionListener(this);

		this.vistaPrincipal.getMntnCrearNuevaSimulacion().addActionListener(this);
		this.vistaPrincipal.getMntnEliminarSimulacion().addActionListener(this);
		this.vistaPrincipal.getMntnModificarSimulacion().addActionListener(this);
		this.vistaPrincipal.getMntnMostrarDatosSimulacion().addActionListener(this);
		this.vistaPrincipal.getMntnDemoSimulacion().addActionListener(this);

		this.vistaPrincipal.getMntnCrearNuevoMundo().addActionListener(this);
		this.vistaPrincipal.getMntnEliminarMundo().addActionListener(this);
		this.vistaPrincipal.getMntnModificarMundo().addActionListener(this);
		this.vistaPrincipal.getMntnMostrarDatosMundo().addActionListener(this);

		this.vistaPrincipal.getMntnCrearNuevoUsuario().addActionListener(this);
		this.vistaPrincipal.getMntnEliminarUsuario().addActionListener(this);
		this.vistaPrincipal.getMntnModificarUsuario().addActionListener(this);
		this.vistaPrincipal.getMntnMostrarDatosUsuario().addActionListener(this);

		this.vistaPrincipal.getMntnEliminarSesion().addActionListener(this);
		this.vistaPrincipal.getMntnModificarSesion().addActionListener(this);
		this.vistaPrincipal.getMntnMostrarDatosSesion().addActionListener(this);

		this.vistaPrincipal.getMntnAcercaDe().addActionListener(this);

		//...
	}

	//Manejador de eventos de componentes... ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.vistaPrincipal.getMntnGuardar()) {
			// Ejecutar método asociado
		}

		if(e.getSource() == this.vistaPrincipal.getMntnSalir()) {
			salir();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnCrearNuevaSimulacion()) {
			crearNuevaSimulacion();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnEliminarSimulacion()) {
			eliminarSimulacion();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnModificarSimulacion()) {
			modificarSimulacion();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnMostrarDatosSimulacion()) {
			mostrarDatosSimulacion();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnDemoSimulacion()) {
			ejecutarDemoSimulacion();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnCrearNuevoMundo()) {
			crearNuevoMundo();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnEliminarMundo()) {
			eliminarMundo();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnModificarMundo()) {
			modificarMundo();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnMostrarDatosMundo()) {
			mostrarDatosMundo();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnCrearNuevoUsuario()) {
			crearNuevoUsuario();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnEliminarUsuario()) {
			eliminarUsuario();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnModificarUsuario()) {
			modificarUsuario();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnMostrarDatosUsuario()) {
			mostrarDatosUsuario();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnEliminarSesion()) {
			eliminarSesion();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnModificarSesion()) {
			modificarSesion();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnMostrarDatosSesion()) {
			mostrarDatosSesion();
		}

		if(e.getSource() == this.vistaPrincipal.getMntnAcercaDe()) {
			this.vistaPrincipal.muestraDialogoAcercaDe();
		}
	}

	// Salida segura única de la aplicación
	private void salir() {
		// Confirmar cierre
		if (this.vistaPrincipal.mensajeConfirmacion("Confirma que quieres salir...")) {
			this.datos.cerrar();
			// Cierra la aplicación
			System.exit(0);
		}
	}

	// Simulaciones
	private void crearNuevaSimulacion() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	private void modificarSimulacion() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	private void eliminarSimulacion() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	private void mostrarDatosSimulacion() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	private void ejecutarDemoSimulacion() {
		Simulacion demo;
		demo = new Simulacion(this.sesion.getUsr(),
				new Fecha(), 
				this.datos.obtenerMundo(Configuracion.get().getProperty("mundo.nombrePredeterminado")),
				Integer.parseInt(Configuracion.get().getProperty("simulacion.ciclosPredeterminados")),
				EstadoSimulacion.PREPARADA);
		new PresenterEjecucionSimulacion(demo);
	}

	// Mundos
	private void crearNuevoMundo() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
		// ControlUsuarios controlUsuarios = new ControlUsuarios();
	}

	private void modificarMundo() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	private void eliminarMundo() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	private void mostrarDatosMundo() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	// Usuarios	
	private void crearNuevoUsuario() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	private void modificarUsuario() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	private void eliminarUsuario() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	private void mostrarDatosUsuario() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	// Sesiones	
	private void modificarSesion() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	private void eliminarSesion() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");
	}

	private void mostrarDatosSesion() {
		this.vistaPrincipal.mostrarMensaje("Opción no disponible...");

	}

	// Manejadores de eventos de ventana... Wnidowslistener
	@Override
	public void windowClosing(WindowEvent arg0) {
		salir();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// No usado
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// No usado
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// No usado
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// No usado
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// No usado
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// No usado
	}

} // class
