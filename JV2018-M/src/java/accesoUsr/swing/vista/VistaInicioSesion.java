/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con la presentación
 *  del inicio de sesión de usuario. 
 *  Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: VistaInicioSesion.java 
 *  @version: 2.2 - 2019.05.16
 *  @author: ajp
 */

package accesoUsr.swing.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import accesoUsr.OperacionesVista;
import config.Configuracion;
import java.awt.Dialog.ModalityType;

@SuppressWarnings("serial")
public class VistaInicioSesion extends JDialog implements OperacionesVista {
    
	private static String OK = "ok";
    private static String CANCEL = "Cancelar";
    
    private JPanel panelTexto;
    private JPanel panelCampos;
    private JPanel panelEtiquetas;
    private JPanel panelBotones;

    private JTextField campoUsuario;
    private JPasswordField campoClaveAcceso;
    
    private JButton botonOk;
    private JButton botonCancelar;
    private JPanel panelTitulo;
    private JPanel panelTrabajo;
    private JLabel lblTitulo;
    private JLabel lblAyuda;
    private JLabel lblSubTitulo;

	public VistaInicioSesion() {
	  	initVistaSesion();
    }
    
	private void initVistaSesion() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		//setType(Type.POPUP);
		setResizable(false);
		
     // Centra la ventana en la pantalla
		Dimension sizePantalla = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((sizePantalla.width - getSize().width) / 2,
				(sizePantalla.height - getSize().height)/ 2);
	    
		
		getContentPane().setLayout(new BorderLayout(0, 0));	
		panelTitulo = new JPanel();
		getContentPane().add(panelTitulo, BorderLayout.NORTH);
		panelTitulo.setLayout(new BorderLayout(0, 0));
		
		lblSubTitulo = new JLabel("                        ");
		panelTitulo.add(lblSubTitulo, BorderLayout.WEST);
		lblTitulo = new JLabel(Configuracion.get().getProperty("aplicacion.titulo")+  " Control de acceso");
		panelTitulo.add(lblTitulo, BorderLayout.CENTER);
		
		lblAyuda = new JLabel("Ayuda ");
		lblAyuda.setToolTipText("");
		lblAyuda.setFont(new Font("Dialog", Font.PLAIN, 11));
		panelTitulo.add(lblAyuda, BorderLayout.EAST);
		panelTrabajo = new JPanel();
	    getContentPane().add(panelTrabajo);
		
	    crearPanelTexto();
        crearPanelBotones();  
	}

	private void crearPanelTexto() {
        panelTexto = new JPanel();
        panelTrabajo.add(panelTexto);
        panelTexto.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        panelEtiquetas = new JPanel();
        JLabel labelUsr = new JLabel("Usuario:");
        JLabel labelClave = new JLabel("Clave de acceso: ");
        labelUsr.setLabelFor(campoUsuario);
        labelClave.setLabelFor(campoClaveAcceso);
        panelEtiquetas.setLayout(new BorderLayout(0, 0));
        panelEtiquetas.add(labelUsr, BorderLayout.NORTH);
        panelEtiquetas.add(labelClave);
        panelTexto.add(panelEtiquetas);
        
		panelCampos = new JPanel();
		campoUsuario = new JTextField(10);
		campoClaveAcceso = new JPasswordField(10);
		campoUsuario.setActionCommand(OK);
		campoClaveAcceso.setActionCommand(OK);
		panelCampos.setLayout(new BorderLayout(0, 0));
		panelCampos.add(campoUsuario, BorderLayout.NORTH);
		panelCampos.add(campoClaveAcceso);
		panelTexto.add(panelCampos);
	}

	private void crearPanelBotones() {      
		panelBotones = new JPanel(new GridLayout(0,1));
		panelTrabajo.add(panelBotones);
        botonOk = new JButton("OK");
        botonOk.setToolTipText("Confirmar credenciales.");
        botonCancelar = new JButton("Cancelar");
        botonCancelar.setToolTipText("Abandonar y salir.");
        botonOk.setActionCommand(OK);
        botonCancelar.setActionCommand(CANCEL);
        panelBotones.setLayout(new BorderLayout(0,0));
        panelBotones.add(botonOk, BorderLayout.NORTH);
        panelBotones.add(botonCancelar, BorderLayout.SOUTH);	
    }

	   public JButton getBotonOk() {
			return botonOk;
		}

		public JLabel getLblAyuda() {
			return lblAyuda;
		}
		
		public JButton getBotonCancelar() {
			return botonCancelar;
		}

		public JTextField getCampoUsuario() {
			return campoUsuario;
		}

		public JPasswordField getCampoClaveAcceso() {
			return campoClaveAcceso;
		}

	    public void resetFocus() {
	    	campoUsuario.requestFocusInWindow();
	    }
		
		@Override
		public void mostrarMensaje(String mensaje) {
			JOptionPane.showMessageDialog(null, mensaje, Configuracion.get().getProperty("aplicacion.titulo"), JOptionPane.INFORMATION_MESSAGE);
		}
		
} //class
