/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con la presentación. 
 *  Colabora en el patron MVC
 *  @since: prototipo2.2
 *  @source: VentanaUsuarios.java 
 *  @version: 2.2 - 2019.05.17
 *  @author: ajp
 */

package accesoUsr.swing.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Insets;
import javax.swing.JTextArea;

public class VistaUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelControl = null;
	private JPanel jPanelTrabajo = null;
	private JPanel jPanelEstado = null;
	private JButton jButtonSalir = null;
	private JLabel jLabelUsuario = null;
	private JPanel jPanelGeneral = null;
	private JScrollPane jScrollPaneUsuarioes = null;
	private JTable jTable = null;
	private JPanel jPanelDetalle = null;
	private JTextField jTextFieldNombre = null;
	private JLabel jLabelNombre = null;
	private JLabel jLabelCIF = null;
	private JLabel jLabelNIF;
	private JTextField jTextFieldCIF = null;
	private JLabel jLabelTelefono = null;
	private JTextField jTextFieldTelefono = null;
	private JLabel jLabelFax = null;
	private JLabel jLabelApellidos;
	private JTextField jTextFieldFax = null;
	private JLabel jLabelEmail = null;
	private JTextField jTextFieldEmail = null;
	private JLabel jLabelDireccion = null;
	private JTextField jTextFieldDireccion = null;
	private JLabel jLabelFechaAlta = null;
	private JTextField jTextFieldFechaAlta = null;
	private JButton jButtonAlta = null;
	private JButton jButtonModificar = null;
	private JButton jButtonEliminar = null;
	private JPanel jPanelBotonera = null;
	private JTextArea textArea;
	
	/**
	 * This is the default constructor
	 */
	public VistaUsuarios() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * @return void
	 */
	private void initialize() {
		this.setResizable(false);
		this.setBounds(new Rectangle(0, 0, 786, 438));
		this.setBackground(new Color(199, 220, 233));
		this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setName("Usuarios");
		this.setContentPane(getJContentPane());
		this.setTitle("Gestión de Simulaciones");
		this.setSize(this.getPreferredSize());
		jLabelUsuario.setText(" Elija opción, por favor.");

		// Adapta tamaño de ventana al 25% de pantalla.
		Dimension tamañoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(tamañoPantalla.width / 2,
				tamañoPantalla.height / 2));
		this.setPreferredSize(new Dimension(600, 400));

		// Calcula y posiciona cerca de la esquina superior izquierda.
		this.setLocation((tamañoPantalla.width - this.getSize().width) / 4,
				(tamañoPantalla.height - this.getSize().height) / 4);
		this.setVisible(false);
	}

	/**
	 * This method initializes jContentPane
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setPreferredSize(new Dimension(350, 550));
			jContentPane.setBackground(new Color(199, 220, 233));
			jContentPane.add(getJPanelControl(), BorderLayout.NORTH);
			jContentPane.add(getJPanelTrabajo(), BorderLayout.CENTER);
			jContentPane.add(getJPanelEstado(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelControl
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelControl() {
		if (jPanelControl == null) {
			jPanelControl = new JPanel();
			jPanelControl.setLayout(new FlowLayout());
			jPanelControl.setBackground(new Color(199, 220, 233));
			jPanelControl.add(getJButtonSalir(), null);
		}
		return jPanelControl;
	}

	/**
	 * This method initializes jPanelTrabajo
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTrabajo() {
		if (jPanelTrabajo == null) {
			jPanelTrabajo = new JPanel();
			jPanelTrabajo.setLayout(new BorderLayout());
			jPanelTrabajo.setPreferredSize(new Dimension(600, 400));
			jPanelTrabajo.setBackground(new Color(199, 220, 233));
			jPanelTrabajo.add(getJPanelGeneral(), BorderLayout.WEST);
			jPanelTrabajo.add(getJPanelBotonera(), BorderLayout.SOUTH);
			jPanelTrabajo.add(getJPanelDetalle(), BorderLayout.EAST);
		}
		return jPanelTrabajo;
	}

	/**
	 * This method initializes jPanelEstado
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelEstado() {
		if (jPanelEstado == null) {
			jLabelUsuario = new JLabel();
			jLabelUsuario.setText("");
			jLabelUsuario.setName("Estado");
			jLabelUsuario.setForeground(Color.red);
			jLabelUsuario.setBackground(Color.white);
			jPanelEstado = new JPanel();
			jPanelEstado.setLayout(new BorderLayout());
			jPanelEstado.setPreferredSize(new Dimension(409, 20));
			jPanelEstado.setBackground(new Color(251, 251, 251));
			jPanelEstado.setForeground(Color.red);
			jPanelEstado.add(jLabelUsuario, BorderLayout.NORTH);
		}
		return jPanelEstado;
	}

	/**
	 * This method initializes jButtonSalir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonSalir() {
		if (jButtonSalir == null) {
			jButtonSalir = new JButton();
			jButtonSalir.setText("Salir");
			jButtonSalir.setName("Salir");
			jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// Grupo 4
					salirSeguro();
					// Grupo 4
				}
			});
		}
		return jButtonSalir;
	}

	/**
	 * This method initializes jPanelGeneral
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelGeneral() {
		if (jPanelGeneral == null) {
			jPanelGeneral = new JPanel();
			jPanelGeneral.setLayout(new BorderLayout());
			jPanelGeneral.setPreferredSize(new Dimension(450, 400));
			jPanelGeneral.setBackground(new Color(199, 220, 233));
			jPanelGeneral.add(getJScrollPaneUsuarioes(), BorderLayout.NORTH);
		}
		return jPanelGeneral;
	}

	/**
	 * This method initializes jScrollPaneUsuarioes
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneUsuarioes() {
		if (jScrollPaneUsuarioes == null) {
			jScrollPaneUsuarioes = new JScrollPane();
			jScrollPaneUsuarioes.setPreferredSize(new Dimension(200, 300));
			jScrollPaneUsuarioes.setBackground(new Color(199, 220, 233));
			jScrollPaneUsuarioes
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPaneUsuarioes
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPaneUsuarioes.setViewportView(getJTable());
			jScrollPaneUsuarioes.setColumnHeaderView(getTextArea());
		}
		return jScrollPaneUsuarioes;
	}

	// M�TODOS ESPECIALES DE CONTROL //

	// Permite salir salir de forma segura cuando se cierra la ventana.
	protected void processWindowEvent(WindowEvent ev) {
		super.processWindowEvent(ev);
		if (ev.getID() == WindowEvent.WINDOW_CLOSING) {
			this.salirSeguro();
		}
	}

	// Salida segura �nica de la ventana.
	private void salirSeguro() {
		// Confirmar cierre
		if (JOptionPane.showConfirmDialog(null, "Confirme que quiere salir...",
				"Datos de Clientes", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			// Cerrar, actualizar y guardar datos.

			// Cierre de ventana.
			this.dispose();
		}
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			//DefaultTableModel defaultTableModel = UsuariosDAO.getInstancia().getTmUsuarios();
			//defaultTableModel.setColumnCount(8);
			jTable = new JTable();
			// Grupo 4: vincula con datos de la base de datos
			jTable.setAutoCreateColumnsFromModel(true);
			jTable.setBackground(new Color(199, 220, 233));
			jTable.setForeground(SystemColor.textHighlight);
			jTable.setGridColor(Color.white);
			jTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			jTable.setRowHeight(20);
			jTable.setShowVerticalLines(false);
			jTable.setIntercellSpacing(new Dimension(1, 1));
			jTable.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jTable.setLocation(new Point(0, 0));
			jTable.setSize(new Dimension(400, 300));
			//jTable.setModel(defaultTableModel);
			jTable.setPreferredSize(new Dimension(200, 300));
			jTable.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					// Asignamos el número de fila seleccionada en 'jTable'.
					int fila = jTable.getSelectedRow();

					// Asignamos valores de cada una de las columnas de la fila
					// seleccionada del 'jTable'.
					String valorCeldaCIF = (String) jTable.getValueAt(fila, 1);
					String valorCeldaNombre = (String) jTable.getValueAt(fila,
							2);
					String valorCeldaTelefono = (String) jTable.getValueAt(
							fila, 3);
					String valorCeldaFax = (String) jTable.getValueAt(fila, 4);
					String valorCeldaEmail = (String) jTable
							.getValueAt(fila, 5);
					String valorCeldaDireccion = (String) jTable.getValueAt(
							fila, 6);
					String valorCeldaFechaAlta = (String) jTable.getValueAt(
							fila, 7);

					// Asignamos los valores anteriores, a los campos de texto
					// correspondientes.
					jTextFieldCIF.setText(valorCeldaCIF);
					jTextFieldNombre.setText(valorCeldaNombre);
					jTextFieldTelefono.setText(valorCeldaTelefono);
					jTextFieldFax.setText(valorCeldaFax);
					jTextFieldEmail.setText(valorCeldaEmail);
					jTextFieldDireccion.setText(valorCeldaDireccion);
					jTextFieldFechaAlta.setText(valorCeldaFechaAlta);
				}

				public void mousePressed(java.awt.event.MouseEvent e) {
				}

				public void mouseReleased(java.awt.event.MouseEvent e) {
				}

				public void mouseEntered(java.awt.event.MouseEvent e) {
				}

				public void mouseExited(java.awt.event.MouseEvent e) {
				}
			});
		}
		return jTable;
	}

	/**
	 * This method initializes jPanelDetalle
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDetalle() {
		if (jPanelDetalle == null) {
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints13.gridx = 4;
			gridBagConstraints13.gridy = 8;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.weightx = 1.0;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints12.gridx = 3;
			gridBagConstraints12.anchor = GridBagConstraints.EAST;
			gridBagConstraints12.gridy = 8;
			jLabelFechaAlta = new JLabel();
			jLabelFechaAlta.setText("Fecha Alta : ");
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints11.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints11.gridx = 4;
			gridBagConstraints11.gridy = 7;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.weightx = 1.0;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints10.gridx = 3;
			gridBagConstraints10.anchor = GridBagConstraints.EAST;
			gridBagConstraints10.gridy = 7;
			jLabelDireccion = new JLabel();
			jLabelDireccion.setText("Dirección : ");
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints9.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints9.gridx = 4;
			gridBagConstraints9.gridy = 6;
			gridBagConstraints9.anchor = GridBagConstraints.SOUTHWEST;
			gridBagConstraints9.weightx = 1.0;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints8.gridx = 3;
			gridBagConstraints8.anchor = GridBagConstraints.EAST;
			gridBagConstraints8.gridy = 6;
			jLabelEmail = new JLabel();
			jLabelEmail.setText("E-Mail : ");
			jLabelEmail.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabelEmail.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints7.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints7.gridx = 4;
			gridBagConstraints7.gridy = 5;
			gridBagConstraints7.anchor = GridBagConstraints.SOUTHWEST;
			gridBagConstraints7.weightx = 1.0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints5.gridx = 3;
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.gridy = 4;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints4.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints4.gridx = 4;
			gridBagConstraints4.gridy = 4;
			gridBagConstraints4.anchor = GridBagConstraints.SOUTHWEST;
			gridBagConstraints4.weightx = 1.0;
			jLabelTelefono = new JLabel();
			jLabelTelefono.setText("Teléfono : ");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints3.gridx = 3;
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.gridy = 2;
			GridBagConstraints gbc_jLabelNIF = new GridBagConstraints();
			gbc_jLabelNIF.insets = new Insets(0, 0, 5, 5);
			gbc_jLabelNIF.anchor = GridBagConstraints.EAST;
			gbc_jLabelNIF.gridy = 0;
			gbc_jLabelNIF.gridx = 3;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridx = 4;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			jLabelNIF = new JLabel();
			jLabelNIF.setText("CIF : ");
			jLabelNIF.setVerticalTextPosition(SwingConstants.TOP);
			jLabelNIF.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelNIF.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabelNIF.setVerticalAlignment(SwingConstants.TOP);
			jLabelNombre = new JLabel();
			jLabelNombre.setText("Nombre : ");
			jLabelNombre.setVerticalTextPosition(SwingConstants.TOP);
			jLabelNombre.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelNombre.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabelNombre.setVerticalAlignment(SwingConstants.TOP);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridx = 4;
			gridBagConstraints.gridy = 2;
			gridBagConstraints.weightx = 1.0;
			jPanelDetalle = new JPanel();
			jPanelDetalle.setLayout(new GridBagLayout());
			jPanelDetalle.setPreferredSize(new Dimension(400, 20));
			jPanelDetalle.setBackground(new Color(199, 220, 233));
			jPanelDetalle.setName("");
			jPanelDetalle.add(jLabelNIF, gbc_jLabelNIF);
			jPanelDetalle.add(getJTextFieldCIF(), gridBagConstraints1);
			jPanelDetalle.add(jLabelNombre, gridBagConstraints3);
			jPanelDetalle.add(getJTextFieldNombre(), gridBagConstraints);
			GridBagConstraints gbc_jLabelApellidos = new GridBagConstraints();
			gbc_jLabelApellidos.insets = new Insets(0, 0, 5, 5);
			gbc_jLabelApellidos.gridx = 3;
			gbc_jLabelApellidos.anchor = GridBagConstraints.EAST;
			gbc_jLabelApellidos.gridy = 3;
			jLabelApellidos = new JLabel();
			jLabelApellidos.setText(" : ");
			jPanelDetalle.add(jLabelApellidos, gbc_jLabelApellidos);
			jPanelDetalle.add(jLabelTelefono, gridBagConstraints5);
			jPanelDetalle.add(getJTextFieldTelefono(), gridBagConstraints4);
			jPanelDetalle.add(getJTextFieldFax(), gridBagConstraints7);
			jPanelDetalle.add(jLabelEmail, gridBagConstraints8);
			jPanelDetalle.add(getJTextFieldEmail(), gridBagConstraints9);
			jPanelDetalle.add(jLabelDireccion, gridBagConstraints10);
			jPanelDetalle.add(getJTextFieldDireccion(), gridBagConstraints11);
			jPanelDetalle.add(jLabelFechaAlta, gridBagConstraints12);
			jPanelDetalle.add(getJTextFieldFechaAlta(), gridBagConstraints13);
		}
		return jPanelDetalle;
	}

	/**
	 * This method initializes jTextFieldNombre
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldNombre() {
		if (jTextFieldNombre == null) {
			jTextFieldNombre = new JTextField();
			jTextFieldNombre.setPreferredSize(new Dimension(300, 20));
			jTextFieldNombre.setHorizontalAlignment(JTextField.LEFT);
		}
		return jTextFieldNombre;
	}

	/**
	 * This method initializes jTextFieldCIF
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldCIF() {
		if (jTextFieldCIF == null) {
			jTextFieldCIF = new JTextField();
			jTextFieldCIF.setHorizontalAlignment(JTextField.LEFT);
			jTextFieldCIF.setPreferredSize(new Dimension(300, 20));
		}
		return jTextFieldCIF;
	}

	/**
	 * This method initializes jTextFieldTelefono
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldTelefono() {
		if (jTextFieldTelefono == null) {
			jTextFieldTelefono = new JTextField();
			jTextFieldTelefono.setPreferredSize(new Dimension(300, 20));
			jTextFieldTelefono.setHorizontalAlignment(JTextField.LEFT);
		}
		return jTextFieldTelefono;
	}

	/**
	 * This method initializes jTextFieldFax
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldFax() {
		if (jTextFieldFax == null) {
			jTextFieldFax = new JTextField();
			jTextFieldFax.setPreferredSize(new Dimension(300, 20));
		}
		return jTextFieldFax;
	}

	/**
	 * This method initializes jTextFieldEmail
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldEmail() {
		if (jTextFieldEmail == null) {
			jTextFieldEmail = new JTextField();
			jTextFieldEmail.setPreferredSize(new Dimension(300, 20));
		}
		return jTextFieldEmail;
	}

	/**
	 * This method initializes jTextFieldDireccion
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldDireccion() {
		if (jTextFieldDireccion == null) {
			jTextFieldDireccion = new JTextField();
			jTextFieldDireccion.setPreferredSize(new Dimension(300, 20));
		}
		return jTextFieldDireccion;
	}

	/**
	 * This method initializes jTextFieldFechaAlta
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldFechaAlta() {
		if (jTextFieldFechaAlta == null) {
			jTextFieldFechaAlta = new JTextField();
			jTextFieldFechaAlta.setPreferredSize(new Dimension(300, 20));
		}
		return jTextFieldFechaAlta;
	}

	/**
	 * This method initializes jButtonAlta	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAlta() {
		if (jButtonAlta == null) {
			jButtonAlta = new JButton();
			jButtonAlta.setName("Alta Usuario");
			jButtonAlta.setText("Alta Usuario");
			jButtonAlta.setActionCommand("Alta Usuario");
			jButtonAlta.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(java.awt.event.MouseEvent e) {		
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
				}
			});
			jButtonAlta.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Grupo 4
					
					// Utilizamos este evento, para mostrar un texto en el 'jLabel'.
					jLabelUsuario.setText(" Alta un Usuario nuevo.");
					
					// Grupo 4
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					// Grupo 4
					
					// Utilizamos este evento, para mostrar un texto en el 'jLabel'.
					jLabelUsuario.setText(" Elige opción, por favor.");
					
					// Grupo 4
				}
			});
		}
		return jButtonAlta;
	}

	/**
	 * This method initializes jButtonModificar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonModificar() {
		if (jButtonModificar == null) {
			jButtonModificar = new JButton();
			jButtonModificar.setText("Modificar Usuario");
			jButtonModificar.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Grupo 4
					
					// Utilizamos este evento, para mostrar un texto en el 'jLabel'.
					jLabelUsuario.setText(" Modificar un Usuario existente.");
					
					// Grupo 4
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
					try {
						
						// Llamamos al m�todo, pas�ndole los dos objetos declarados.
						modificarCIFUsuario(jTextFieldCIF, jTable);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					// Grupo 4
					
					// Utilizamos este evento, para mostrar un texto en el 'jLabel'.
					jLabelUsuario.setText(" Elige opción, por favor.");
					
					// Grupo 4
				}
			});
		}
		return jButtonModificar;
	}

	/**
	 * This method initializes jButtonEliminar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonEliminar() {
		if (jButtonEliminar == null) {
			jButtonEliminar = new JButton();
			jButtonEliminar.setText("Eliminar Usuario");
			jButtonEliminar.setHorizontalTextPosition(SwingConstants.RIGHT);
			jButtonEliminar.setHorizontalAlignment(SwingConstants.RIGHT);
			jButtonEliminar.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Grupo 4
					
					// Utilizamos este evento, para mostrar un texto en el 'jLabel'.
					jLabelUsuario.setText(" Eliminar un Usuario existente.");
					
					// Grupo 4
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					// Grupo 4
					
					// Utilizamos este evento, para mostrar un texto en el 'jLabel'.
					jLabelUsuario.setText(" Elige opción, por favor.");
					
					// Grupo 4
				}
			});
		}
		return jButtonEliminar;
	}

	/**
	 * This method initializes jPanelBotonera	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelBotonera() {
		if (jPanelBotonera == null) {
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = -1;
			gridBagConstraints16.anchor = GridBagConstraints.EAST;
			gridBagConstraints16.gridy = -1;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = -1;
			gridBagConstraints15.anchor = GridBagConstraints.EAST;
			gridBagConstraints15.gridy = -1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = -1;
			gridBagConstraints14.anchor = GridBagConstraints.EAST;
			gridBagConstraints14.gridy = -1;
			jPanelBotonera = new JPanel();
			jPanelBotonera.setLayout(new GridBagLayout());
			jPanelBotonera.setComponentOrientation(ComponentOrientation.UNKNOWN);
			jPanelBotonera.setBackground(new Color(199, 220, 233));
			jPanelBotonera.setPreferredSize(new Dimension(409, 26));
			jPanelBotonera.add(getJButtonAlta(), gridBagConstraints14);
			jPanelBotonera.add(getJButtonModificar(), gridBagConstraints15);
			jPanelBotonera.add(getJButtonEliminar(), gridBagConstraints16);
		}
		return jPanelBotonera;
	}
	
	// Este método, admite la entrada de un objeto JTextField, y un objeto JTable.
	public void modificarCIFUsuario(JTextField campo, JTable tabla) throws SQLException {
		
		// Obtiene la fila seleccionada en el JTable pasado por parámetro.
		int fila = tabla.getSelectedRow();
		
		// Actualiza los datos de jTextFieldCIF a la fila y columna correspondiente del TableModel.
		//DatosAplicacion5.getmDatos().getTmUsuarioes().setValueAt(campo.getText(), fila, 1);
		
		// Obtiene el ResultSet, y se posiciona en la fila equivalente a la del TableModel
		// (fila + 1), porque el ResultSet comienza por 1, y no por 0.
		//DatosAplicacion5.getmDatos().leerResultSet().absolute(fila + 1);
		
		// Actualizamos el registro en el ResultSet.
		//DatosAplicacion5.getmDatos().leerResultSet().updateString(1, campo.getText());
		
		// Guardamos los cambios en la BD.
		//DatosAplicacion5.getmDatos().leerResultSet().updateRow();
	}
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
		}
		return textArea;
	}
}