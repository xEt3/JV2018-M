/** 
 * Proyecto: Juego de la vida.
 * Implementación del control de inicio de sesión y ejecución de la simulación por defecto.
 * Permite incluir id de usuario en la línea de comandos.
 * @since: prototipo1.0
 * @source: JVPrincipal.java 
 * @version: 2.2- 2019/05/16
 * @author: ajp
 */

package _main;

import accesoUsr.consola.presenter.PresenterPrincipal;

public class JVPrincipalConsola {

	public static void main(String[] args) {		
		if (args.length > 0) {
			new PresenterPrincipal(args[0]);
			return;
		}
		new PresenterPrincipal();
	}
	
} //class
