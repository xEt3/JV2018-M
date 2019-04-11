/** 
 * Proyecto: Juego de la vida.
 *  Organiza y gestiona la configuración de la aplicación.
 *  Utiliza Properties para organizar y almacenar la configuración.
 *  @since: prototipo2.0
 *  @source: Configuracion.java 
 *  @version: 2.0 - 2019/03/20 
 *  @author: ajp
 */

package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuracion {

	// Singleton.
	private static Properties configuracion;
	private File fConfig;
	
	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor privado.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 */
	public static Properties get() {
		if (configuracion == null) {
			new Configuracion();
		}
		return configuracion;
	}
	
	/**
	 * Constructor por defecto de uso interno.
	 */
	private Configuracion() {
		configuracion = new Properties();
		try {
			new File("datos").mkdirs();
			fConfig = new File("." + File.separator + "datos"	+ File.separator + "jv2018.cfg");
			InputStream is = new FileInputStream(fConfig);
			if (fConfig.exists()) {
				configuracion.load(is);
				return;
			}
			cargarPredeterminada();
			guardar();
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void cargarPredeterminada() {
		configuracion.put("usuario.admin", "admin");
		configuracion.put("usuario.invitado", "invitado");
		configuracion.put("claveAcceso.predeterminada", "Miau#0");
		
		// resto de parametros de configuración predeterminada.
	}

	/**
	 * Guarda configuración al fichero.
	 */
	public void guardar() {
		try {
			new File("datos").mkdirs();
			fConfig = new File("." + File.separator + "datos"	+ File.separator + "jv2018.cfg");
			OutputStream os = new FileOutputStream(fConfig);
			configuracion.store(os, "Configuracion actualizada");
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
} // class