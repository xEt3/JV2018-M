/** 
Proyecto: Juego de la vida.
 * Implementa el control de inicio de sesión 
 * y ejecución de la simulación por defecto. 
 * @since: prototipo1.0
 * @source: JVPrincipal.java 
 * @version: 2.0 - 2019/03/21
 * @author: ajp
 */

import accesoDatos.Datos;
import accesoUsr.Presentacion;
import modelo.SesionUsuario;

public class JVPrincipal {

	private Datos datos;
	private Presentacion interfazUsr;
	
	/**
	 * Secuencia principal del programa.
	 * @throws Exception 
	 */
	public static void main(String[] args) {				
		try {
			JVPrincipal jv = new JVPrincipal();
			jv.datos = new Datos();
			jv.interfazUsr = new Presentacion();		
			System.out.println(jv.datos.toStringDatosUsuarios());
				
				if (jv.interfazUsr.inicioSesionCorrecto()) {	
					SesionUsuario sesion = new SesionUsuario();
					sesion.setUsr(jv.interfazUsr.getUsrEnSesion());  
					jv.datos.altaSesion(sesion);
								
					System.out.println("Sesión: " + jv.datos.getSesionesRegistradas() + '\n' + "Iniciada por: " 
							+ 	jv.interfazUsr.getUsrEnSesion().getNombre() + " " 
							+ jv.interfazUsr.getUsrEnSesion().getApellidos());			
					jv.interfazUsr.mostrarSimulacion();
				}
				else {
					System.out.println("\nDemasiados intentos fallidos...");
				}		
				jv.datos.cerrar();
				System.out.println("Fin del programa.");
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
	} 

} //class
