/** 
 * Proyecto: Juego de la vida.
 * Implementa los mecanismos de interaccion con el usuario.
 *  Tiene defectos:
 * - Clase acaparadora que realiza tareas diversas.
 * - Está demasiado acoplada la lógica con la tecnología de E/S de usuario.
 * @since: prototipo1.1
 * @source: Presentacion.java 
 * @version: 2.0 - 2019/03/20
 * @author: ajp
 */

package accesoUsr;

import java.util.Scanner;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.ModeloException;
import modelo.Simulacion;
import modelo.Usuario;
import util.Fecha;

public class Presentacion {

	private Datos datos;
	private Usuario usrEnSesion;
	private Simulacion simulacion;

	public Presentacion() throws ModeloException, DatosException  {
		datos = new Datos();
	}

	public Usuario getUsrEnSesion() {
		return this.usrEnSesion;
	}

	public Simulacion getSimulacion() {
		return this.simulacion;
	}

	/**
	 * Despliega en la consola el estado almacenado, corresponde
	 * a una generación del Juego de la vida.
	 * @throws ModeloException 
	 */
	public void mostrarSimulacion() throws ModeloException {
		int generacion = 0; 
		System.out.println(simulacion.getMundo().getTipoMundo());
		do {
			System.out.println("\nGeneración: " + generacion);
			simulacion.getMundo().actualizarMundo();
			generacion++;
			System.out.println(simulacion.getMundo().toStringEstadoMundo());
		}
		while (generacion < simulacion.getCiclos());
	}

	/**
	 * Controla el acceso de usuario.
	 * @return true si la sesión se inicia correctamente.
	 */
	public boolean inicioSesionCorrecto() {
		Scanner teclado = new Scanner(System.in);	// Entrada por consola.
		int intentosPermitidos = Integer.parseInt(Configuracion.get().getProperty("sesion.intentosPermitidos"));

		do {
			// Pide usuario y contraseña.
			System.out.print("Introduce el id de usuario: ");
			String id = teclado.nextLine().toUpperCase();
			System.out.print("Introduce clave acceso: ");
			try {
				ClaveAcceso clave = new ClaveAcceso(teclado.nextLine());
				// Busca usuario coincidente con las credenciales.
				usrEnSesion = datos.obtenerUsuario(id);

				if (usrEnSesion != null && usrEnSesion.getClaveAcceso().equals(clave)) {
					simulacion = datos.obtenerSimulacion(new Usuario().getId() + "-" + new Fecha(Configuracion.get().getProperty("fecha.predeterminadaFija")).toStringMarcaTiempo());
					return true; 	
				} 
			}
			catch (ModeloException e) { 	
				//e.printStackTrace();
			}
			intentosPermitidos--;
			System.out.print("Credenciales incorrectas: ");
			System.out.println("Quedan " + intentosPermitidos + " intentos... ");
		} 
		while (intentosPermitidos > 0);

		return false;
	}

} //class
