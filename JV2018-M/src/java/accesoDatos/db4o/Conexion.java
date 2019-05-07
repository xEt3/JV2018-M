/** 
 * Proyecto: Juego de la vida.
 * Establece acceso a la base de datos OO db4o.
 * Aplica el patron Singleton.
 * @since: prototipo2.1
 * @source: Conexion.java 
 * @version: 2.1 - 2019/05/02
 * @author: ajp
 
 */

package accesoDatos.db4o;

import java.io.File;
import java.util.Calendar;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import config.Configuracion;

public class Conexion {
	
	// Singleton
	private static ObjectContainer db;
	
	private Conexion() {
		initConexion();
	}

	/**
	 * Configura la conexion.
	 */
	private void initConexion() {
		new File("datos").mkdirs();
		final String PATH = "." 
				+ File.separator + Configuracion.get().getProperty("datos.nombreDirectorio") 
				+ File.separator + Configuracion.get().getProperty("db4o.nombreFicheroDB");
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().objectClass(Calendar.class).callConstructor(true);
		db = Db4oEmbedded.openFile(config, PATH);
	}
	
	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instance
	 */
	public static ObjectContainer getInstance() {
		if (db == null) {
			new Conexion();
		}
		return db;
	}
	
	/**
	 * Cierra conexion.
	 */
	public static void cerrarConexiones() {
		if (db != null) {
			db.close();
		}
	}

} // class
