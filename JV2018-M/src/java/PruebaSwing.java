import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PruebaSwing extends JFrame implements ActionListener {
	public PruebaSwing() {
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFichero = new JMenu("Fichero");
		menuBar.add(mnFichero);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		
		mntmSalir.addActionListener(this);
		
		mnFichero.add(mntmSalir);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de...");
		mnAyuda.add(mntmAcercaDe);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
		
	}

}
