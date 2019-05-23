/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  de inicio de sesión de usuario. Colabora en el patrón MVC
 *  @since: prototipo2.1
 *  @source: ControlInicioSesion.java 
 *  @version: 2.1 - 2019.05.06
 *  @author: ajp
 */

package accesoUsr.consola.control;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import accesoUsr.consola.vista.VistaInicioSesion;
import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.ModeloException;
import modelo.SesionUsuario;
import modelo.SesionUsuario.EstadoSesion;
import modelo.Usuario;
import util.Fecha;

public class ControlInicioSesion {
	private VistaInicioSesion vistaSesion;
	private Usuario usrEnSesion;
	private SesionUsuario sesion;
	private Datos fachada;

	public ControlInicioSesion() {
		this(null);
	}

	public ControlInicioSesion(String idUsr) {
		initControlSesion(idUsr);
	}

	private void initControlSesion(String idUsr) {
		fachada = new Datos();
		vistaSesion = new VistaInicioSesion();
		vistaSesion.mostrarMensaje(Configuracion.get().getProperty("aplicacion.titulo"));
		iniciarSesionUsuario(idUsr);
	}

	/**
	 * Controla el acceso de usuario 
	 * y registro de la sesión correspondiente.
	 * @param credencialUsr ya obtenida, puede ser null.
	 */
	private void iniciarSesionUsuario(String idUsr) {
		int intentosPermitidos = new Integer(Configuracion.get().getProperty("sesion.intentosPermitidos"));
		String credencialUsr = idUsr;
		do {
			if (idUsr == null) {
				// Pide credencial usuario si llega null.
				credencialUsr = vistaSesion.pedirIdUsr();	
			}
			credencialUsr = credencialUsr.toUpperCase();
			String clave = vistaSesion.pedirClaveAcceso();
			vistaSesion.mostrarMensaje(credencialUsr);		
			try {
				// Busca usuario coincidente con las credenciales.
				usrEnSesion = fachada.obtenerUsuario(credencialUsr);			
				if (usrEnSesion != null 
						&& usrEnSesion.getClaveAcceso().equals(new ClaveAcceso(clave))) {
					registrarSesion();
					return;
				} 
				throw new ModeloException();
			} 
			catch (ModeloException e) {
				intentosPermitidos--;
				vistaSesion.mostrarMensaje("Credenciales incorrectas...");
				vistaSesion.mostrarMensaje("Quedan " + intentosPermitidos + " intentos... ");
			}
		}
		while (intentosPermitidos > 0);
		vistaSesion.mostrarMensaje("Fin del programa...");
		fachada.cerrar();
		System.exit(0);	
	}

	public SesionUsuario getSesion() {
		return sesion;
	}

	/**
	 * Crea la sesion de usuario 
	 */
	private void registrarSesion() {
		// Registra sesión.
		// Crea la sesión de usuario en el sistema.
		try {
			sesion = new SesionUsuario(usrEnSesion, new Fecha(), EstadoSesion.ACTIVA);
			fachada.altaSesion(sesion);

		} 
		catch (DatosException e) {
			e.printStackTrace();
		}	
		vistaSesion.mostrarMensaje("Sesión: " + sesion.getId()
		+ '\n' + "Iniciada por: " + usrEnSesion.getNombre());	
	}

} //class
