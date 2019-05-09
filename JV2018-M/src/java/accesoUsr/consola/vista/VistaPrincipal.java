/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con la presentación principal del programa con un menú. 
 *  Colabora en el patrón MVC.
 *  @since: prototipo2.1
 *  @source: VistaPrincipal.java 
 *  @version: 2.1 - 2019.05.06
 *  @author: ajp
 */

package accesoUsr.consola.vista;

import java.io.Console;
import java.util.Scanner;

import accesoUsr.OperacionesVista;
import config.Configuracion;

public class VistaPrincipal implements OperacionesVista {

	private Console consola;
	private int opcionActiva;

	public VistaPrincipal() {
		consola = System.console();
		opcionActiva = 0;
	}

	public void mostrar() {
		this.mostrarMensaje("\n-- " 
										+ Configuracion.get().getProperty("aplicacion.titulo")
										+ " GESTIÓN PRINCIPAL --");
		this.mostrarMensaje(
				"  \nSIMULACIONES\n" +
						"    1. Crear nueva simulación\n" +
						"    2. Modificar simulación existente\n" +
						"    3. Eliminar simulación exitente\n" +
						"    4. Mostrar datos de simulaciones\n" +
						"    5. Mostrar identificadores de simulaciones\n" +
						"    6. Ejecutar simulación de demostración\n" +
						"  \nMUNDOS\n" +
						"    7. Crear nuevo mundo\n" +
						"    8. Modificar mundo existente\n" +
						"    9. Eliminar mundo existente\n" +
						"    10. Mostrar datos de mundos\n" +
						"  \nUSUARIOS\n" +
						"    11. Crear nuevo usuario\n" +
						"    12. Modificar usuario existente\n" +
						"    13. Eliminar usuario existente\n" +
						"    14. Mostrar datos de usuarios\n" +
						"  \nSESIONES\n" +
						"    15. Modificar sesión existente\n" +
						"    16. Eliminar sesión existente\n" +
						"    17. Mostrar datos de sesiones\n" +
						"    18. Mostrar identificadores de sesiones\n\n" +
						"     0. SALIR\n " +
						"\nElige una opción: \n" 
				);
	}

	public void pedirOpcion() {
		if (consola != null) {
			opcionActiva = Integer.parseInt(consola.readLine());
			return;
		}
		// Desde entorno Eclipse la consola falla.
		opcionActiva = new Scanner(System.in).nextInt();
	}

	public int getOpcionActiva() {
		return opcionActiva;
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		if (consola != null) {
			consola.writer().println(mensaje);
			return;
		}
		System.out.println(mensaje);
	}

} //class		
