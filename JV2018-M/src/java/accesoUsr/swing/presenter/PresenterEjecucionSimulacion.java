/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el estado, 
 *  sincronización y lógica de presentación de una simulación. 
 *  Colabora en el patrón MVP
 *  @since: prototipo2.1
 *  @source: PresenterSimulación.java 
 *  @version: 2.2 - 2019.05.17
 *  @author: ajp
 */

package accesoUsr.swing.presenter;

import accesoUsr.swing.vista.VistaEjecucionSimulacion;
import config.Configuracion;
import modelo.Mundo;
import modelo.Simulacion;

public class PresenterEjecucionSimulacion {
	private int ciclos;
	private VistaEjecucionSimulacion vistaSimulacion;
	private Simulacion simulacion;
	private Mundo mundo;
	private Thread hiloSimulacion = new Thread(new Runnable() {
		@Override
		public void run() {
			movimientoSimulacion();
		}
	});
	
	public PresenterEjecucionSimulacion(Simulacion simulacion) {
		assert simulacion != null;
		this.simulacion = simulacion;
		initControlSimulacion();
	}
	
	private void initControlSimulacion() {	
		ciclos = Integer.parseInt((Configuracion.get().getProperty("simulacion.ciclosPredeterminados")));
		mundo = simulacion.getMundo();	
		vistaSimulacion = new VistaEjecucionSimulacion();
		//configListener();
		vistaSimulacion.pack();
		vistaSimulacion.setVisible(true);
		arrancarSimulacion();
	}
	
	private void arrancarSimulacion() {
		hiloSimulacion.start();
	}

	/**
	 * Ejecuta una simulación del juego de la vida, en la consola,
	 * durante un número de CICLOS.
	 */
	public void movimientoSimulacion() {
		int gen = 0; 		//Generaciones
		do {
			vistaSimulacion.getTextAreaVisualizacion().append("\nGeneración: " + gen + "\n");
			vistaSimulacion.mostrarSimulacion(this);
			mundo.actualizarMundo();
			gen++;
		}
		while (gen <= ciclos);
	}
	
	public void pararSimulacion() {
		
	}
	
	public Simulacion getSimulacion() {
		return simulacion;
	}
	
	public Mundo getMundo() {
		return mundo;
	}
	
} // class
