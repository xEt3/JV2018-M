/** 
 * Proyecto: Juego de la vida.
 * Maneja los errores de las clases del paquete de utilidades.
 *  @since: prototipo2.0
 *  @source: UtilException.java 
 * @version: 2.0 - 2019.03.12
 *  @author: ajp
 */

package util;

public class UtilException extends Exception {

	/**
	 * Excepción por defecto sin mensaje.
	 */
	public UtilException() {
		super();
	}
	
	/**
	 * Excepción con mensaje
	 * @param msg - el mensaje de error asociado.
	 */
	public UtilException(String mensaje) {
		super(mensaje);
	}

} // class
