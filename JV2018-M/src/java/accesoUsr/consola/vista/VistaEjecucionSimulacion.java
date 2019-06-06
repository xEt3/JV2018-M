/**
 * Proyecto: Juego de la vida.
 * Implementación de la clase VistaSimulacion.java para mostrar el aspecto
 * de un mundo para la simulacion.
 * Colabora en el patrón MVP.
 * @since: prototipo2.1
 * @source: VistaPrincipal.java 
 * @version: 2.1 - 2019/05/15
 * @author: Grupo2
 * @author: arm
 * @author: Fran Arce
 */

package accesoUsr.consola.vista;

import java.io.Console;

import accesoUsr.OperacionesVista;
import accesoUsr.consola.presenter.PresenterEjecucionSimulacion;

public class VistaEjecucionSimulacion implements OperacionesVista {

	private Console consola; //Atributo para teclado de consola.

	/**
	 * Constructor de la clase VistaSimulacion.
	 * Crea objeto para consola por teclado.
	 */
	
	public VistaEjecucionSimulacion() {
		consola = System.console();
	}
	
	/**
	 * Metodo que muestra la forma de un mundo para la simulacion. 
	 * @param controlSimulacion
	 */

	public void mostrarSimulacion(PresenterEjecucionSimulacion controlSimulacion) {
		byte[][] espacio = controlSimulacion.getMundo().getEspacio();
		for(int i=0 ; i< espacio.length ; i++) {
			for(int j=0 ; j< espacio.length ; j++) {
				this.mostrarSimple((espacio[i][j] == 1) ? "|o" : "| ");
			}
			this.mostrarMensaje("|");
		}
	}

	/**
	 * Metodo que muestra un simple texto para imprimir el tablero del mundo. 
	 * @param texto
	 */
	
	private void mostrarSimple(String texto) {
		if(consola != null) {
			consola.writer().print(texto);
			return;
		}
		System.out.print(texto);
	}
	
	/**
	 * Metodo que muestra un texto como finalizacion de la simulacion.
	 */

	public void confirmar() {
		mostrarSimple("Simulacion Completada.");
	}

	/**
	 * Metodo de la interfaz OperacionesVista que muestra un mensaje de texto.
	 */
	
	@Override
	public void mostrarMensaje(String mensaje) {
		if (consola != null) {
			consola.writer().println(mensaje);
			return;
		}
		System.out.println(mensaje);
	}

}//class