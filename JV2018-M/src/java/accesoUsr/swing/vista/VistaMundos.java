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
	private JPanel pedirDatos;
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
	
}
