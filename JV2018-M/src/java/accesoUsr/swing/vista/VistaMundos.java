/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con la presentación. 
 *  Colabora en el patron MVC
 *  @since: prototipo 2.2
 *  @source: VistaMundos.java 
 *  @version: 0.0.0001-Alpha - 2019/06/15
 *  @author: Antonio Ruiz
 *  @author: Ramon Moñino
 *  @author: Ignacio Belmonte
 *  @author: Atanas Genchev
 *  @author: Ramon Moreno
 *  @author: Roberto Bastida
 */

package accesoUsr.swing.vista;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class VistaMundos {

	private JFrame ventanaMundo;
	private JTextField textIdentificador;
	private JTextField textNombre;
	private JTextField textEspacioX;
	private JTextField testEspacioY;
	private JTextField textDistribucion;
	private JTextField textValoresSobrevivir;
	private JTextField textValoresRenacer;
	private JTextField textTipoMundo;
	private JPanel mostrarMundos;
	private JPanel panelBotones;
	private JPanel pedirDatos;
	private JButton btnAlta;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JLabel labelIdentificador;
	private JLabel labelValoresRenacer;
	private JLabel labelValoresSobrevivir;
	private JLabel labelDistribucion;
	private JLabel labelEspacioY;
	private JLabel labelEspacioX;
	private JLabel labelNombre;
	private JLabel labelTipoMundo;

	public VistaMundos() {
		initVistaMundo();
	}

	private void initVistaMundo() {
		inicializarVentanaMundo();
		crearPanelesVista();

	}

	/**
	 * Configuracion del JFrame Principal de la vista de Mundo
	 */
	private void inicializarVentanaMundo() {
		ventanaMundo = new JFrame();
		ventanaMundo.setTitle("Gestion de Mundo");
		ventanaMundo.getContentPane().setLayout(new BorderLayout(0, 0));
		ventanaMundo.getContentPane().setBackground(new Color(176, 224, 230));
		ventanaMundo.setSize(958, 552);
		ventanaMundo.setLocationRelativeTo(null);
		ventanaMundo.setVisible(true);
	}

	/**
	 * Inicializa todos los Paneles de la vista de Mundo
	 */
	private void crearPanelesVista() {
		inicializarPedirDatos();
		inicializarMostrarMundos();
		inicializarPanelBotones();
	}

	/**
	 * Inicializa el panel que recibe los datos de entrada a la vista de Mundo
	 */
	private void inicializarPedirDatos() {
		pedirDatos = new JPanel();
		pedirDatos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pedirDatos.setBackground(new Color(176, 224, 230));
		pedirDatos.setPreferredSize(new Dimension(265, 0));
		pedirDatos.setLayout(null);
		ventanaMundo.getContentPane().add(pedirDatos, BorderLayout.EAST);
		crearEtiquetasPedirlDatos();
		crearTextFieldPedirDatos();
	}

	/**
	 * Crea las etiquetas del panel PedirDatos para pedir la informacion
	 * correspondiente de los campos a los que pertenecen.
	 */
	private void crearEtiquetasPedirlDatos() {
		labelIdentificador = new JLabel("Identificador: ");
		labelIdentificador.setBounds(16, 49, 78, 21);
		pedirDatos.add(labelIdentificador);

		labelNombre = new JLabel("Nombre:");
		labelNombre.setBounds(16, 82, 55, 16);
		pedirDatos.add(labelNombre);

		labelEspacioX = new JLabel("Espacio X: ");
		labelEspacioX.setBounds(16, 110, 67, 16);
		pedirDatos.add(labelEspacioX);

		labelEspacioY = new JLabel("Espacio Y:");
		labelEspacioY.setBounds(16, 138, 67, 16);
		pedirDatos.add(labelEspacioY);

		labelDistribucion = new JLabel("Distribucion:");
		labelDistribucion.setBounds(16, 166, 78, 16);
		pedirDatos.add(labelDistribucion);

		labelValoresSobrevivir = new JLabel("ValoresSobrevivir: ");
		labelValoresSobrevivir.setBounds(16, 194, 106, 16);
		pedirDatos.add(labelValoresSobrevivir);

		labelValoresRenacer = new JLabel("Valores Renacer: ");
		labelValoresRenacer.setBounds(16, 222, 98, 16);
		pedirDatos.add(labelValoresRenacer);

		labelTipoMundo = new JLabel("Tipo Mundo: ");
		labelTipoMundo.setBounds(16, 249, 98, 16);
		pedirDatos.add(labelTipoMundo);
	}
	

	/**
	 * Crea los campos de texto, para añadir los datos y luego
	 * tratarlos para dar de alta, modificar o eliminar
	 */
	private void crearTextFieldPedirDatos() {
		textIdentificador = new JTextField();
		textIdentificador.setFont(new Font("SansSerif", Font.PLAIN, 10));
		textIdentificador.setBounds(130, 49, 122, 21);
		pedirDatos.add(textIdentificador);

		textNombre = new JTextField();
		textNombre.setFont(new Font("SansSerif", Font.PLAIN, 10));
		textNombre.setBounds(130, 79, 122, 21);
		pedirDatos.add(textNombre);
		textNombre.setColumns(10);

		textEspacioX = new JTextField();
		textEspacioX.setFont(new Font("SansSerif", Font.PLAIN, 10));
		textEspacioX.setBounds(130, 107, 122, 21);
		pedirDatos.add(textEspacioX);
		textEspacioX.setColumns(10);

		testEspacioY = new JTextField();
		testEspacioY.setFont(new Font("SansSerif", Font.PLAIN, 10));
		testEspacioY.setBounds(130, 135, 122, 21);
		pedirDatos.add(testEspacioY);
		testEspacioY.setColumns(10);

		textDistribucion = new JTextField();
		textDistribucion.setFont(new Font("SansSerif", Font.PLAIN, 10));
		textDistribucion.setBounds(130, 160, 122, 22);
		pedirDatos.add(textDistribucion);
		textDistribucion.setColumns(10);

		textValoresSobrevivir = new JTextField();
		textValoresSobrevivir.setFont(new Font("SansSerif", Font.PLAIN, 10));
		textValoresSobrevivir.setBounds(130, 191, 122, 21);
		pedirDatos.add(textValoresSobrevivir);
		textValoresSobrevivir.setColumns(10);

		textValoresRenacer = new JTextField();
		textValoresRenacer.setFont(new Font("SansSerif", Font.PLAIN, 10));
		textValoresRenacer.setBounds(130, 219, 122, 22);
		pedirDatos.add(textValoresRenacer);
		textValoresRenacer.setColumns(10);

		textTipoMundo = new JTextField();
		textTipoMundo.setFont(new Font("SansSerif", Font.PLAIN, 10));
		textTipoMundo.setBounds(130, 247, 122, 21);
		pedirDatos.add(textTipoMundo);
		textTipoMundo.setColumns(10);
	}

	
	/**
	 * Inicializa el panel que muestra los datos de mundo
	 */
	private void inicializarMostrarMundos() {
		mostrarMundos = new JPanel();
		mostrarMundos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		mostrarMundos.setBackground(new Color(176, 224, 230));
		mostrarMundos.setPreferredSize(new Dimension(265, 0));
		mostrarMundos.setLayout(null);
		ventanaMundo.getContentPane().add(mostrarMundos, BorderLayout.WEST);
	}

	/**
	 * Inicializar el panel que contiene los botones con las operaciones 
	 * que se puedan realizar en la vista de mundo.
	 */
	private void inicializarPanelBotones() {
		panelBotones = new JPanel();
		panelBotones.setBackground(new Color(176, 224, 230));
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		ventanaMundo.getContentPane().add(panelBotones, BorderLayout.SOUTH);
		añadirBotonesPanelBotones();
	}

	/**
	 * Añade los botones al panel de botones.
	 */
	private void añadirBotonesPanelBotones() {
		btnAlta = new JButton("Alta");
		panelBotones.add(btnAlta);

		btnModificar = new JButton("Modificar");
		panelBotones.add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		panelBotones.add(btnEliminar);
	}
	
}
