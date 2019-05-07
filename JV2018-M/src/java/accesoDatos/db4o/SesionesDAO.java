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

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.SesionUsuario;

public class SesionesDAO implements OperacionesDAO {

    // Singleton.
    private static SesionesDAO instance;

    // Base de datos
    private ObjectContainer db;

    // Constructor
    private SesionesDAO() {
        db = Conexion.getInstance();
    }

    /**
     * Método estático de acceso a la instancia única. Si no existe la crea
     * invocando al constructor interno. Utiliza inicialización diferida. Sólo se
     * crea una vez; instancia única -patrón singleton-
     *
     * @return instance
     */
    public static SesionesDAO getInstance() {
        if (instance == null) {
            instance = new SesionesDAO();
        }
        return instance;
    }
    
    @Override
    public Object obtener(String id) {
    	 // TODO OperacionesDAO.baja
        return null;    
    }
    /**
     * Obtención de la lista de Sesiones almacenadas
     * @return lista de Sesiones de Usuario
     */
    @Override
    public List obtenerTodos() {
        Query query = db.query();
        query.constrain(SesionUsuario.class);
        ObjectSet<SesionUsuario> result = query.execute();
        return result;
    }

    /**
     * Alta de una nueva SesionUsuario.
     * @param obj - la SesionUsuario a almacenar.
     * @throws DatosException - si ya existe.
     */
    @Override
    public void alta(Object obj) throws DatosException {
        assert obj != null;
        SesionUsuario sesionNueva = (SesionUsuario) obj;
        ObjectSet<SesionUsuario> sesionesAlmacenadas = db.queryByExample(sesionNueva);

        if (sesionesAlmacenadas.next() == null) {
            db.store(sesionNueva);
        } else {
            throw new DatosException("SesionesDAO.alta: " + sesionNueva.getId() + " ya existe");
        }
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




