/** 
 * Proyecto: Juego de la vida.
 *  Establece acceso a base de datos MySQL
 *  @since: Prototipo2.2
 *  @source: Conexion.java 
 *  @version: 2.2 - 2019/05/15
 *  @author: ajp
 */
package accesoDatos.mySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import config.Configuracion;

public class Conexion {

	private static Connection db;
	private String url;
	private String usr;
	private String passwd;

	/**
	 * Constructor de uso interno que ejecuta la configuración por defecto.
	 * Sólo se ejecutará una vez según Singleton.
	 */
	private Conexion() {
		url = Configuracion.get().getProperty("mySql.url");
		usr = Configuracion.get().getProperty("mySql.user");
		passwd = Configuracion.get().getProperty("mySql.passwd");
		configurarConexion();
	}

	/**
	 * Obtiene la base de datos conectada.
	 * La primera vez la instacia.
	 * @return db - la instancia única de base de datos conectada.
	 */
	public static Connection getDB() {
		if (db == null) 
			new Conexion();
		return db;
	}

	/**
	 * Método interno auxiliar para configurar los detalles de la conexión.
	 */
	private void configurarConexion()  {
		try {	
			// Carga el driver de base de datos.
			//DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();	
			
			// Establece la conexión con la BD utilizando el driver
			String zonaHoraria = "?useTimezone=true&serverTimezone=UTC";
			db = DriverManager.getConnection(url+zonaHoraria, usr, passwd);
		} 
		catch (SQLException  e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cierra la conexión a la base de datos si ha sido abierta.  
	 */
	public void cerrarConexion() {
		if (db != null) {
			try {
				db.close();
			} 
			catch (SQLException e) { }
		}
	}

} //class