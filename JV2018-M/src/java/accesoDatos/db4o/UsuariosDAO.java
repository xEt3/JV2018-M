/** 
 * Proyecto: Juego de la vida.
 * Versión de UsuariosDAO orientada a utilizar
 * la base de datos db4o.
 * @since: prototipo2.1
 * @source: UsuariosDAO.java 
 * @version: 2.1 - 2019/05/09 
 * @author: Grupo 2
 * @author: Javier Muñoz Iniesta
 * @author: Jose Manuel Ibáñez Sola-Belando
 * @author: Juan Carlos Peña Fernández
 * @author: Nilo Gómez Fernández
 * @author: Pablo Muelas Ballesta
 * @author: Pablo Meseguer Lax
 */

package accesoDatos.db4o;


import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.Mundo;
import modelo.Usuario;


public class UsuariosDAO implements OperacionesDAO {
	
	//Singleton.
	private static UsuariosDAO instance;
	
	//Base de Datos.
	private ObjectContainer db;
	
	//Constructor.
	private UsuariosDAO() {
		db = Conexion.getInstance();
	}
	
	public static UsuariosDAO getInstance() {
		if (instance == null) {
			instance = new UsuariosDAO();
		}
		return instance;
	}
	
	@Override
	public Object obtener(String id) throws DatosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List obtenerTodos() {
		Query query = db.query();
		query.constrain(Usuario.class);
		ObjectSet<Usuario> result = query.execute();
		return result;
	}

	@Override
	public void alta(Object obj) throws DatosException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object baja(String id) throws DatosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(Object obj) throws DatosException {
		// TODO Auto-generated method stub

	}

	public String toStringDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	public String toStringId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void borrarTodo() {
		// TODO Auto-generated method stub

	}

	@Override
	public String listarDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listarId() {
		// TODO Auto-generated method stub
		return null;
	}

} //class