/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con la visualización
 *  de la ejecución de una simulación. 
 *  Colabora en el patrón MVP.
 *  @since: prototipo2.1
 *  @source: VistaSimulacionTexto.java 
 *  @version: 2.2 - 2019.05.17
 *  @author: ajp
 */

package accesoUsr.swing.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import accesoUsr.OperacionesVista;
import accesoUsr.swing.presenter.PresenterEjecucionSimulacion;
import config.Configuracion;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;

public class VistaEjecucionSimulacion extends JFrame implements OperacionesVista {

	private static final long serialVersionUID = 1L;

	private JPanel panelControles;
	private JPanel panelSimulacion;
	private JTextArea textAreaVisualizacion;
	private JToolBar toolBar;
	private JPanel panelEstado;
	private JScrollPane panelVisualizacion;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	
	public VistaEjecucionSimulacion() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Ejecución Simulación JV");
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panelControles = new JPanel();
		getContentPane().add(panelControles, BorderLayout.NORTH);
		
		
		panelSimulacion = new JPanel();
		getContentPane().add(panelSimulacion, BorderLayout.CENTER);
		panelSimulacion.setLayout(new BorderLayout(0, 0));	
		
		textAreaVisualizacion = new JTextArea();
		textAreaVisualizacion.setEditable(false);
		textAreaVisualizacion.setRows(50);
		textAreaVisualizacion.setColumns(50);
		textAreaVisualizacion.setTabSize(4);
		textAreaVisualizacion.setFont(new Font("Courier New", Font.PLAIN, 16));
		textAreaVisualizacion.setBackground(Color.WHITE);
		
		panelVisualizacion = new JScrollPane(textAreaVisualizacion);
		panelVisualizacion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		panelSimulacion.add(panelVisualizacion, BorderLayout.NORTH);
		
		toolBar = new JToolBar();
		panelSimulacion.add(toolBar, BorderLayout.SOUTH);
		
		panelEstado = new JPanel();
		getContentPane().add(panelEstado, BorderLayout.SOUTH);
	}

	public JTextArea getTextAreaVisualizacion() {
		return textAreaVisualizacion;
	}
	
	/**
	 * Despliega en un panel de texto el estado almacenado correspondiente
	 * a una generación del Juego de la vida.
	 */
	public void mostrarSimulacion(PresenterEjecucionSimulacion control) {
		byte[][] espacio = control.getMundo().getEspacio();
		for (int i = 0; i < espacio.length; i++) {
			for (int j = 0; j < espacio.length; j++) {
				mostrarSimple((espacio[i][j] == 1) ? "|o" : "| ");
			}
			textAreaVisualizacion.append("|\n");
		}
	}
	
	private void mostrarSimple(String mensaje) {
		textAreaVisualizacion.append(mensaje);
	}
	
	@Override
	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, Configuracion.get().getProperty("aplicacion.titulo"), JOptionPane.INFORMATION_MESSAGE);
	}

} // class
