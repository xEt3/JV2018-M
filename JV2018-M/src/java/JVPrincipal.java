/** 
 * Proyecto: Juego de la vida.
 * Implementación del control de inicio de sesión y ejecución de la simulación por defecto.
 * Permite incluir id de usuario en la línea de comandos.
 * @since: prototipo1.0
 * @source: JVPrincipal.java 
 * @version: 2.1 - 2019/05/16
 * @author: ajp
 */

import accesoUsr.consola.control.ControlPrincipal;

public class JVPrincipal {

	public static void main(String[] args) {		
		if (args.length > 0) {
			new ControlPrincipal(args[0]);
			return;
		}
		new ControlPrincipal();
	}
	
} //class
