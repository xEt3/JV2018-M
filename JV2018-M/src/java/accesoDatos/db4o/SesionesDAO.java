/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Patron utilizando un ArrayList.
 * Aplica el patron Singleton.
 * Participa del patron Template Method heredando el método indexSort().
 * Colabora en el patrón Façade.
 * @since: prototipo2.1
 * @source: SesionesDAO.java
 * @version: 2.1 - 2019.05.03
 * @author: Grupo 1
 * @author: Miguel Fernández Piñero (MiguelFerPi)
 * @author: Jesús Pérez Robles (jebles)
 */

package accesoDatos.db4o;

import java.util.List;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import accesoDatos.memoria.DAOIndexSort;

public class SesionesDAO extends DAOIndexSort implements OperacionesDAO  {

	@Override
	public Object obtener(String id) throws DatosException {
		// TODO OperacionesDAO.obtener
		return null;
	}

	@Override
	public List obtenerTodos() {
		// TODO OperacionesDAO.obtenerTodos
		return null;
	}

	@Override
	public void alta(Object obj) throws DatosException {
		// TODO OperacionesDAO.alta
		
	}

	@Override
	public Object baja(String id) throws DatosException {
		// TODO OperacionesDAO.baja
		return null;
	}

	@Override
	public void actualizar(Object obj) throws DatosException {
		// TODO OperacionesDAO.actualizar
		
	}

	@Override
	public String listarDatos() {
		// TODO OperacionesDAO.listarDatos
		return null;
	}

	@Override
	public String listarId() {
		// TODO OperacionesDAO.listarId
		return null;
	}

	@Override
	public void borrarTodo() {
		// TODO OperacionesDAO.borrarTodo
		
	}

}

