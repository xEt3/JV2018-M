/** 
 * Proyecto: Juego de la vida.
 * Clase de excepción para errores de usuario de las clases del modelo.
 * @since: prototipo1.2
 * @source: ModeloException.java 
 * @version: 1.2 - 2019.03.02
 * @author: ajp
 */

package modelo;

public class ModeloException extends Exception {

	public ModeloException(String mensaje) {
		super(mensaje);
	}

	public ModeloException() {
		super();
	}
}
