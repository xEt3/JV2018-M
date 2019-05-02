/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Mundo utilizando una base de datos.
 * Aplica el patron Singleton.
 * Colabora en el patrón Façade.
 * @since: prototipo2.0
 * @source: MundosDAO.java 
 * @version: 2.1 - 2019/03/25
 *  @author: Grupo 3
 *  @author Antonio Ruiz
 *  @author Atanas Genchev
 *  @author Roberto Bastida
 *  @author Ignacio Belmonte
 *  @author Ramon Moreno
 *  @author Ramon Moñino
 */

package accesoDatos.db4o;

import java.util.ArrayList;
import java.util.List;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import accesoDatos.memoria.DAOIndexSort;
import modelo.Identificable;
import modelo.ModeloException;
import modelo.Mundo;

public class MundosDAO extends DAOIndexSort implements OperacionesDAO {

	@Override
	public Object obtener(String id) throws DatosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List obtenerTodos() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void borrarTodo() {
		// TODO Auto-generated method stub
		
	}


} // class