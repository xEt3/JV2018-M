/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos relacionados con el estado, 
 * sincronización y lógica de presentación de la ejecución
 * de una simulación.
 * Colabora en el patrón MVP. 
 * @since: prototipo 2.1
 * @source: PresenterEjecucionSimulacion.java
 * @version: 2.1 - 2019.05.15
 * @author: Grupo 2:
 * @author: VictorJLucas
 * @author: themajoser
 */
package accesoUsr.consola.presenter;

import accesoDatos.Datos;
import accesoUsr.consola.vista.VistaEjecucionSimulacion;
import config.Configuracion;
import modelo.Mundo;
import modelo.Simulacion;


/*
 * Constructor por defecto.
 */
public class PresenterEjecucionSimulacion {

	private final Integer CICLOS = Integer
			.parseInt(Configuracion.get().getProperty("simulacion.ciclosPredeterminados"));
	private VistaEjecucionSimulacion vistaSimulacion;
	private Simulacion simulacion;
	private Mundo mundo;
	private Datos datos;

	/*
	 * Constructor adicional que recibe el objeto demo de la clase simulacion.
	 */
	public PresenterEjecucionSimulacion(Simulacion demo) {
		datos = new Datos();
		this.simulacion = demo;
		initControlSimulacion();
	}

	/*
	 * Constructor para inicializar la simulación.
	 */
	private void initControlSimulacion() {

		mundo = simulacion.getMundo();
		vistaSimulacion = new VistaEjecucionSimulacion();
		arrancarSimulacion();
		vistaSimulacion.confirmar();

	}
	
	/**
	 * Reproduce una simulacion
	 */
	private void arrancarSimulacion() {
		int generacion = 0;
		do {
			vistaSimulacion.mostrarMensaje("\nGeneración: " + generacion);
			simulacion.getMundo().actualizarMundo();
			generacion++;
			vistaSimulacion.mostrarSimulacion(this);
		} while (generacion < CICLOS);
	}

	/*
	 * Método que devuelve el número de ciclos
	 */
	public Integer getCICLOS() {
		return CICLOS;
	}

	/*
	 * Método que devuelve el objeto vistaSimulacion
	 */
	public VistaEjecucionSimulacion getVistaSimulacion() {
		return vistaSimulacion;
	}

	/*
	 * Método que devuelve el objeto vistaSimulacion
	 */
	public Simulacion getSimulacion() {
		return simulacion;
	}

	/*
	 * Método que devuelve el objeto mundo
	 */
	public Mundo getMundo() {
		return mundo;
	}

	/*
	 * Método que devuelve el objeto datos
	 */
	public Datos getDatos() {
		return datos;
	}

}


