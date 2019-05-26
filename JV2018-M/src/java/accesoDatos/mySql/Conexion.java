/** 
 * Proyecto: Juego de la vida.
 * Establece acceso a la base de datos OO db4o.
 * Aplica el patron Singleton.
 * @since: prototipo2.1
 * @source: Conexion.java 
 * @version: 2.1 - 2019/05/02
 * @author: ajp
 
 */

package accesoDatos.mySql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import config.Configuracion;

public class Conexion {

	// Singleton
	private static Connection db;
	private String usr;
	private String url;
	private String passwd;

	private Conexion() {
		url = Configuracion.get().getProperty("mySql.url");
		usr = Configuracion.get().getProperty("mySql.usr");
		passwd = Configuracion.get().getProperty("mySql.passwd");
		initConexion();
	}

	/**
	 * Configura la conexion.
	 */
	private void initConexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String zonaHoraria = "?useTimezone=true&serverTimezone=UTC";
			db = DriverManager.getConnection(url+zonaHoraria,usr,passwd);
		} catch (ClassNotFoundException  | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método estático de acceso a la instancia única. Si no existe la crea
	 * invocando al constructor interno. Utiliza inicialización diferida. Sólo se
	 * crea una vez; instancia única -patrón singleton-
	 * 
	 * @return instance
	 */
	public static Connection getDB() {
		if (db == null) {
			new Conexion();
		}
		return db;
	}

	/**
	 * Cierra conexion.
	 * 
	 * @throws SQLException
	 */
	public static void cerrarConexiones()  {
		if (db != null) {
			try {
				db.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

} // class
