/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el estado, 
 *  sincronización y lógica de presentación del inicio de sesión de usuario. 
 *  Colabora en el patrón MVP.
 *  @since: prototipo2.1
 *  @source: PresenterInicioSesion.java 
 *  @version: 2.2 - 2019.05.17
 *  @author: ajp
 */

package accesoUsr.swing.presenter;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import accesoUsr.swing.vista.VistaInicioSesion;
import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.SesionUsuario;
import modelo.SesionUsuario.EstadoSesion;
import modelo.Usuario;
import util.Fecha;

public class PresenterInicioSesion implements ActionListener, MouseListener {
	private int intentosPermitidos;
	private PresenterPrincipal controlPrincipal;
	private VistaInicioSesion vistaInicioSesion;
	private Usuario usrEnSesion;
	private Datos datos;

	public PresenterInicioSesion(PresenterPrincipal controlPrincipal) {
		this.controlPrincipal = controlPrincipal;
		this.initControlSesion();
	}

	private void initControlSesion() {
		this.intentosPermitidos = Integer.parseInt(Configuracion.get().getProperty("sesion.intentosPermitidos"));	
		this.datos = new Datos();
		this.vistaInicioSesion = new VistaInicioSesion();
		this.vistaInicioSesion.setModalityType(ModalityType.MODELESS);
		this.configListener();
		this.vistaInicioSesion.pack();
		this.vistaInicioSesion.setVisible(true);
	}

	/**
	 * Configura los componentes con interacción de la vista asociándoles un ActionListener
	 */
	private void configListener() {
		// Registra la clase que controla eventos de cada componente.
		this.vistaInicioSesion.getBotonOk().addActionListener(this);
		this.vistaInicioSesion.getBotonCancelar().addActionListener(this);
		this.vistaInicioSesion.getCampoUsuario().addActionListener(this);
		this.vistaInicioSesion.getCampoClaveAcceso().addActionListener(this);
		this.vistaInicioSesion.getLblAyuda().addMouseListener(this);
	}

	//Manejador de eventos de componentes... ActionListener
	@Override
	public void actionPerformed(ActionEvent evento) {
		if(evento.getSource() == this.vistaInicioSesion.getBotonOk()) {
			// Procesa campos.
			this.iniciarSesionUsuario();
		}

		if(evento.getSource() == this.vistaInicioSesion.getBotonCancelar()) {
			this.datos.cerrar();
			System.exit(0);
		}

		if(evento.getSource() == this.vistaInicioSesion.getCampoUsuario()) {
			// Intro en campo usuario pasa foco a campo claveAcceso.
			this.vistaInicioSesion.getCampoClaveAcceso().requestFocus();
		}

		if(evento.getSource() == this.vistaInicioSesion.getCampoClaveAcceso()) {
			// Procesa campos.
			this.iniciarSesionUsuario();
		}
	}

	//Manejador de evento de raton...
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == this.vistaInicioSesion.getLblAyuda()) {
			this.vistaInicioSesion.mostrarMensaje("Sólo pueden iniciar sesión los usuarios registrados...");
		}
	}

	/**
	 * Controla el acceso de usuario y registro de la sesión correspondiente.
	 */
	private void iniciarSesionUsuario() {	
		String credencialUsr = this.vistaInicioSesion.getCampoUsuario().getText().toUpperCase();
		String clave = new String(this.vistaInicioSesion.getCampoClaveAcceso().getPassword());
		// Busca usuario coincidente con las credenciales.
		this.usrEnSesion = this.datos.obtenerUsuario(credencialUsr);
		try {			
			if (this.usrEnSesion != null 
					&& this.usrEnSesion.getClaveAcceso().equals(new ClaveAcceso(clave))) {
				this.vistaInicioSesion.dispose();
				this.controlPrincipal.getVistaPrincipal().setEnabled(true);
				this.controlPrincipal.getVistaPrincipal().requestFocus();
				this.vistaInicioSesion.mostrarMensaje("Sesión: " + this.registrarSesion().getId()
						+ '\n' + "Iniciada por: " + this.usrEnSesion.getNombre());
				return;
			} 
			throw new Exception();
		} 
		catch (Exception e) {
			this.intentosPermitidos--;
			this.vistaInicioSesion.mostrarMensaje("Credenciales incorrectas...\n"
					+ "Quedan " + intentosPermitidos + " intentos. ");
			this.vistaInicioSesion.getCampoUsuario().setText("");
			this.vistaInicioSesion.getCampoUsuario().requestFocus();
			this.vistaInicioSesion.getCampoClaveAcceso().setText("");
		}

		if (intentosPermitidos <= 0){
			this.vistaInicioSesion.mostrarMensaje("Fin del programa.");
			this.datos.cerrar();
			System.exit(0);	
		}
	}

	/**
	 * Crea la sesion de usuario 
	 */
	private SesionUsuario registrarSesion() {
		// Registra sesión.
		// Crea la sesión de usuario en el sistema.
		SesionUsuario nuevaSesion = null;
		try {
			nuevaSesion = new SesionUsuario(this.usrEnSesion, new Fecha(), EstadoSesion.ACTIVA);
			this.controlPrincipal.setSesion(nuevaSesion);
			this.datos.altaSesion(nuevaSesion);
		} 
		catch (DatosException e) {
			e.printStackTrace();
		}
		return nuevaSesion;		
	}

	// Manejadores de eventos de ratón no usados.
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

} //class
