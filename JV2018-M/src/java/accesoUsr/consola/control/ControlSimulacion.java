/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  de una simulación. Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: ControlSimulación.java 
 *  @version: 2.1 - 2019.05.06
 *  @author: ajp
 */

package accesoUsr.consola.control;

import accesoDatos.Datos;
import accesoUsr.consola.vista.VistaSimulacion;
import config.Configuracion;
import modelo.Mundo;
import modelo.Simulacion;

public class ControlSimulacion {
	
	final int CICLOS = Integer.parseInt((Configuracion.get().getProperty("simulacion.ciclosPredeterminados")));
	VistaSimulacion vistaSimulacion;
	Simulacion simulacion;
	Mundo mundo;
	Datos fachadaDatos;
	
	public ControlSimulacion(Simulacion simulacion) {
		fachadaDatos = new Datos();
		this.simulacion = simulacion;
		initControlSimulacion();
	}
	
	private void initControlSimulacion() {	
		mundo = simulacion.getMundo();	
		vistaSimulacion = new VistaSimulacion();
		arrancarSimulacion();
		vistaSimulacion.confirmar();
	}

	/**
	 * Ejecuta una simulación del juego de la vida, en la consola,
	 * durante un número de CICLOS.
	 */
	public void arrancarSimulacion() {
		int gen = 0; 		//Generaciones
		do {
			vistaSimulacion.mostrarMensaje("\nGeneración: " + gen);
			vistaSimulacion.mostrarSimulacion(this);
			mundo.actualizarMundo();
			gen++;
		}
		while (gen <= CICLOS);
	}
	
	public Simulacion getSimulacion() {
		return simulacion;
	}
	
	public Mundo getMundo() {
		return mundo;
	}
	
} // class
