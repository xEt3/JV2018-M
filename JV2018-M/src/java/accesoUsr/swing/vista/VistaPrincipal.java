/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con la visualización 
 *  principal del programa con interfaz gráfico. 
 *  Colabora en el patrón MVP.
 *  @since: prototipo2.1
 *  @source: VistaPrincipal.java 
 *  @version: 2.2 - 2019.05.17
 *  @author: ajp
 */

package accesoUsr.swing.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import accesoUsr.OperacionesVista;
import config.Configuracion;

public class VistaPrincipal extends JFrame implements OperacionesVista {
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	
	private JMenu mnFicheros;
	private JMenuItem mntnGuardar;
	private JMenuItem mntnSalir;
	
	private JMenu mnSimulaciones;
	private JMenuItem mntnCrearNuevaSimulacion;
	private JMenuItem mntnModificarSimulacion;
	private JMenuItem mntnEliminarSimulacion;
	private JMenuItem mntnMostrarDatosSimulacion;
	private JMenuItem mntnDemoSimulacion;
	
	private JMenu mnMundos;
	private JMenuItem mntnCrearNuevoMundo;
	private JMenuItem mntnModificarMundo;
	private JMenuItem mntnEliminarMundo;
	private JMenuItem mntnMostrarDatosMundo;
	
	private JMenu mnUsuarios;
	private JMenuItem mntnCrearNuevoUsuario;
	private JMenuItem mntnModificarUsuario;
	private JMenuItem mntnEliminarUsuario;
	private JMenuItem mntnMostrarDatosUsuario;
	
	private JMenu mnSesiones;
	private JMenuItem mntnModificarSesion;
	private JMenuItem mntnEliminarSesion;
	private JMenuItem mntnMostrarDatosSesion;
	
	private JMenu mnAyuda;
	private JMenuItem mntnAcercaDe;
	private JDialog dialogAcercaDe;
	
	/**
	 * Create the frame.
	 */
	public VistaPrincipal() {
		initVistaPrincipal();
	}

	/**
	 * Initializa contenido de la ventana principal de la sesión.
	 */
	private void initVistaPrincipal() {
		setTitle(Configuracion.get().getProperty("aplicacion.titulo") + " GESTIÓN PRINCIPAL");
		//Permite controlar manualmente el cierre de la ventana
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		//Adapta tamaño preferido de ventana al 25% de la pantalla
		Dimension sizePantalla = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(new Dimension(sizePantalla.width / 2, sizePantalla.height / 2));
		setPreferredSize(this.getSize());

		// Centra la ventana en la pantalla
		setLocation((sizePantalla.width - getSize().width) / 2,
				(sizePantalla.height - getSize().height) / 2);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFicheros = new JMenu();
		mnFicheros.setHorizontalAlignment(SwingConstants.CENTER);
		mnFicheros.setToolTipText("Menú fichero.");
		mnFicheros.setText("Fichero");
		menuBar.add(mnFicheros);

		mntnGuardar = new JMenuItem();
		mntnGuardar.setText("Guardar");
		mnFicheros.add(mntnGuardar);

		mntnSalir = new JMenuItem();

		mntnSalir.setText("Salir");
		mnFicheros.add(mntnSalir);

		mnSimulaciones = new JMenu();
		mnSimulaciones.setToolTipText("Menú simulaciones.");
		mnSimulaciones.setText("Simulaciones");
		menuBar.add(mnSimulaciones);

		mntnCrearNuevaSimulacion = new JMenuItem();
		mntnCrearNuevaSimulacion.setText("Crear nueva simulación");
		mnSimulaciones.add(mntnCrearNuevaSimulacion);

		mntnModificarSimulacion = new JMenuItem();
		mntnModificarSimulacion.setText("Modificar simulación");
		mnSimulaciones.add(mntnModificarSimulacion);

		mntnEliminarSimulacion = new JMenuItem();
		mntnEliminarSimulacion.setText("Eliminar simulación");
		mnSimulaciones.add(mntnEliminarSimulacion);

		mntnMostrarDatosSimulacion = new JMenuItem();
		mntnMostrarDatosSimulacion.setText("Mostrar datos");
		mnSimulaciones.add(mntnMostrarDatosSimulacion);

		mntnDemoSimulacion = new JMenuItem();
		mntnDemoSimulacion.setText("Demo JV");
		mnSimulaciones.add(mntnDemoSimulacion);

		mnMundos = new JMenu();
		mnMundos.setToolTipText("Menú mundos.");
		mnMundos.setText("Mundos");
		menuBar.add(mnMundos);

		mntnCrearNuevoMundo = new JMenuItem();
		mntnCrearNuevoMundo.setText("Crear nuevo mundo");
		mnMundos.add(mntnCrearNuevoMundo);

		mntnModificarMundo = new JMenuItem();
		mntnModificarMundo.setText("Modificar mundo");
		mnMundos.add(mntnModificarMundo);

		mntnEliminarMundo = new JMenuItem();
		mntnEliminarMundo.setText("Eliminar mundo");
		mnMundos.add(mntnEliminarMundo);

		mntnMostrarDatosMundo = new JMenuItem();
		mntnMostrarDatosMundo.setText("Mostrar datos");
		mnMundos.add(mntnMostrarDatosMundo);

		mnUsuarios = new JMenu();
		mnUsuarios.setToolTipText("Menú usuarios.");
		mnUsuarios.setText("Usuarios");
		menuBar.add(mnUsuarios);

		mntnCrearNuevoUsuario = new JMenuItem();
		mntnCrearNuevoUsuario.setText("Crear nuevo usuario");
		mnUsuarios.add(mntnCrearNuevoUsuario);

		mntnModificarUsuario = new JMenuItem();
		mntnModificarUsuario.setText("Modificar usuario");
		mnUsuarios.add(mntnModificarUsuario);

		mntnEliminarUsuario = new JMenuItem();
		mntnEliminarUsuario.setText("Eliminar usuario");
		mnUsuarios.add(mntnEliminarUsuario);

		mntnMostrarDatosUsuario = new JMenuItem();
		mntnMostrarDatosUsuario.setText("Mostrar usuario");
		mnUsuarios.add(mntnMostrarDatosUsuario);

		mnSesiones = new JMenu();
		mnSesiones.setToolTipText("Menú sesiones.");
		mnSesiones.setText("Sesiones");
		menuBar.add(mnSesiones);

		mntnModificarSesion = new JMenuItem();
		mntnModificarSesion.setText("Modificar sesión");
		mnSesiones.add(mntnModificarSesion);

		mntnEliminarSesion = new JMenuItem();
		mntnEliminarSesion.setText("Eliminar sesión");
		mnSesiones.add(mntnEliminarSesion);

		mntnMostrarDatosSesion = new JMenuItem();
		mntnMostrarDatosSesion.setText("Mostrar datos");
		mnSesiones.add(mntnMostrarDatosSesion);

		mnAyuda = new JMenu();
		mnAyuda.setActionCommand("ayuda");
		mnAyuda.setToolTipText("Menú ayuda.");
		mnAyuda.setText("Ayuda");
		menuBar.add(mnAyuda);

		mntnAcercaDe = new JMenuItem();
		mntnAcercaDe.setText("Acerca de...");
		mnAyuda.add(mntnAcercaDe);
	}

	public JMenuItem getMntnGuardar() {
		return mntnGuardar;
	}

	public JMenuItem getMntnSalir() {
		return mntnSalir;
	}

	public JMenuItem getMntnCrearNuevaSimulacion() {
		return mntnCrearNuevaSimulacion;
	}

	public JMenuItem getMntnModificarSimulacion() {
		return mntnModificarSimulacion;
	}

	public JMenuItem getMntnEliminarSimulacion() {
		return mntnEliminarSimulacion;
	}

	public JMenuItem getMntnMostrarDatosSimulacion() {
		return mntnMostrarDatosSimulacion;
	}
	
	public JMenuItem getMntnDemoSimulacion() {
		return mntnDemoSimulacion;
	}

	public JMenuItem getMntnCrearNuevoMundo() {
		return mntnCrearNuevoMundo;
	}

	public JMenuItem getMntnModificarMundo() {
		return mntnModificarMundo;
	}

	public JMenuItem getMntnEliminarMundo() {
		return mntnEliminarMundo;
	}

	public JMenuItem getMntnMostrarDatosMundo() {
		return mntnMostrarDatosMundo;
	}

	public JMenuItem getMntnCrearNuevoUsuario() {
		return mntnCrearNuevoUsuario;
	}

	public JMenuItem getMntnModificarUsuario() {
		return mntnModificarUsuario;
	}

	public JMenuItem getMntnEliminarUsuario() {
		return mntnEliminarUsuario;
	}

	public JMenuItem getMntnMostrarDatosUsuario() {
		return mntnMostrarDatosUsuario;
	}

	public JMenuItem getMntnModificarSesion() {
		return mntnModificarSesion;
	}

	public JMenuItem getMntnEliminarSesion() {
		return mntnEliminarSesion;
	}

	public JMenuItem getMntnMostrarDatosSesion() {
		return mntnMostrarDatosSesion;
	}

	public JMenuItem getMntnAcercaDe() {
		return mntnAcercaDe;
	}

	public void muestraDialogoAcercaDe() {
		dialogAcercaDe = new JDialog(this) {
			private static final long serialVersionUID = 1L;
			public void paint(Graphics g) {
				Graphics2D g2D = (Graphics2D) g;
				g2D.setColor(Color.CYAN);
				g2D.fillRect(0, 0, 300, 200);
				g2D.setColor(Color.BLACK);
				g2D.drawString("JV-2018 ", 20, 70);
				g2D.drawString("(c) 2018", 70, 90);
			}
		};
		dialogAcercaDe.setLocation(getX() + 100, getY() + 70);
		dialogAcercaDe.setTitle("Acerca De...");
		dialogAcercaDe.setBounds(460, 460, 200, 150);
		dialogAcercaDe.setResizable(false);
		dialogAcercaDe.setVisible(true);
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, "JV-2018", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public boolean mensajeConfirmacion(String mensaje) {
		return JOptionPane.showConfirmDialog(null, mensaje,
				"JV-2018", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
	}

} //class
