/** 
 * Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos del almacenamiento del
 *  DTO Usuario utilizando base de datos mySQL.
 *  Colabora en el patron Fachada.
 *  @since: prototipo2.2
 *  @source: UsuariosDAO.java 
 *  @version: 2.2 - 2019/05/15 
 *  @author: ajp
 */

package accesoDatos.mySql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import java.sql.Connection;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Nif;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;
import util.Fecha;
import util.Formato;

public class UsuariosDAO implements OperacionesDAO {

	// Singleton
	private static UsuariosDAO instance = null;
	private Connection db;
	private Statement stUsuarios;
	private ResultSet rsUsuarios;
	private DefaultTableModel tmUsuarios; // Tabla del resultado de la consulta.
	private ArrayList<Usuario> bufferUsuarios;

	/**
	 * Método de acceso a la instancia única. Si no existe la crea invocando al
	 * constructor interno. Utiliza inicialización diferida de la instancia única.
	 * 
	 * @return instancia
	 */
	public static UsuariosDAO getInstance() {
		if (instance == null) {
			instance = new UsuariosDAO();
		}
		return instance;
	}

	/**
	 * Constructor de uso interno.
	 */
	private UsuariosDAO() {
		try {
			inicializar();
			if (obtener("AAA0T") == null || obtener("III1R") == null) {
				cargarPredeterminados();
			}
		} catch (SQLException | DatosException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inicializa el DAO, detecta si existen las tablas de datos capturando la
	 * excepción SQLException.
	 * 
	 * @throws SQLException
	 */
	private void inicializar() {
		db = Conexion.getDB();
		try {
			crearTablaUsuarios();
			stUsuarios = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Crea el tableModel y el buffer de objetos para usuarios.
		tmUsuarios = new DefaultTableModel();
		bufferUsuarios = new ArrayList<Usuario>();
	}

	/**
	 * Crea la tabla de usuarios en la base de datos.
	 * 
	 * @throws SQLException
	 */
	private void crearTablaUsuarios() throws SQLException {
		Statement s = db.createStatement();
		s.executeUpdate("CREATE TABLE IF NOT EXISTS usuarios (" + "id VARCHAR(5) PRIMARY KEY,"
				+ "nif VARCHAR(9) NOT NULL," + "correo VARCHAR(45) NOT NULL," + "nombre VARCHAR(45) NOT NULL,"
				+ "apellidos VARCHAR(45) NOT NULL," + "calle VARCHAR(45) NOT NULL," + "numero VARCHAR(5) NOT NULL,"
				+ "cp VARCHAR(5) NOT NULL," + "poblacion VARCHAR(45) NOT NULL," + "fechaNacimiento DATE,"
				+ "fechaAlta DATE," + "claveAcceso VARCHAR(16) NOT NULL," + "rol VARCHAR(20) NOT NULL,"
				+ "UNIQUE (nif)," + "UNIQUE (correo)" + ");");
	}

	/**
	 * Método para generar los datos predeterminados.
	 * 
	 * @throws SQLException
	 * @throws DatosException
	 */
	private void cargarPredeterminados() throws SQLException, DatosException {
		try {
			alta(new Usuario()); // Invitado.

			String nombre = Configuracion.get().getProperty("usuario.admin");
			alta(new Usuario(new Nif(Configuracion.get().getProperty("usuario.nifAdmin")), nombre,
					nombre + " " + nombre, new DireccionPostal(),
					new Correo(nombre.toLowerCase() + Configuracion.get().getProperty("correo.dominioPredeterminado")),
					new Fecha(Configuracion.get().getProperty("usuario.fechaNacimientoPredeterminada")), new Fecha(),
					new ClaveAcceso(), RolUsuario.ADMINISTRADOR));
		} catch (ModeloException | DatosException e) {
			e.printStackTrace();
		}
	}

	// MÉTODOS DAO Usuarios

	/**
	 * Obtiene el usuario buscado dado un objeto. Si no existe devuelve null.
	 * 
	 * @param Usuario a obtener.
	 * @return (Usuario) buscado.
	 */
	public Usuario obtener(Object obj) {
		assert obj != null;
		return obtener(((Usuario) obj).getId());
	}

	/**
	 * Obtiene el usuario buscado dado su id, el nif o el correo. Si no existe
	 * devuelve null.
	 * 
	 * @param idUsr del usuario a obtener.
	 * @return (Usuario) buscado.
	 */
	@Override
	public Usuario obtener(String idUsr) {
		assert idUsr != null;
		assert !idUsr.equals("");
		assert !idUsr.equals(" ");

		ejecutarConsulta(idUsr);

		// Establece columnas y etiquetas.
		establecerColumnasModelo();

		// Borrado previo de filas.
		borrarFilasModelo();

		// Volcado desde el resulSet.
		rellenarFilasModelo();

		// Actualiza buffer de objetos.
		sincronizarBufferUsuarios();

		if (bufferUsuarios.size() > 0) {
			return (Usuario) bufferUsuarios.get(0);
		}
		return null;
	}

	/**
	 * Determina el idUsr recibido y ejecuta la consulta. Los resultados quedan en
	 * el ResultSet
	 * 
	 * @param idUsr
	 */
	private void ejecutarConsulta(String idUsr) {
		String id = "id";
		if (idUsr.matches(Formato.PATRON_NIF)) {
			id = "nif";
		}
		if (idUsr.matches(Formato.PATRON_CORREO1)) {
			id = "correo";
		}

		try {
			rsUsuarios = stUsuarios.executeQuery("SELECT * FROM usuarios WHERE " + id + " = '" + idUsr + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crea las columnas del TableModel a partir de los metadatos del ResultSet de
	 * una consulta a base de datos
	 */
	private void establecerColumnasModelo() {
		try {
			// Obtiene metadatos.
			ResultSetMetaData metaDatos = this.rsUsuarios.getMetaData();

			// Número total de columnas.
			int numCol = metaDatos.getColumnCount();

			// Etiqueta de cada columna.
			Object[] etiquetas = new Object[numCol];
			for (int i = 0; i < numCol; i++) {
				etiquetas[i] = metaDatos.getColumnLabel(i + 1);
			}

			// Incorpora array de etiquetas en el TableModel.
			((DefaultTableModel) this.tmUsuarios).setColumnIdentifiers(etiquetas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Borra todas las filas del TableModel
	 * 
	 * @param tm - El TableModel a vaciar
	 */
	private void borrarFilasModelo() {
		while (this.tmUsuarios.getRowCount() > 0)
			((DefaultTableModel) this.tmUsuarios).removeRow(0);
	}

	/**
	 * Replica en el TableModel las filas del ResultSet
	 */
	private void rellenarFilasModelo() {
		Object[] datosFila = new Object[this.tmUsuarios.getColumnCount()];
		// Para cada fila en el ResultSet de la consulta.
		try {
			while (rsUsuarios.next()) {
				// Se replica y añade la fila en el TableModel.
				for (int i = 0; i < this.tmUsuarios.getColumnCount(); i++) {
					datosFila[i] = this.rsUsuarios.getObject(i + 1);
				}
				((DefaultTableModel) this.tmUsuarios).addRow(datosFila);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Regenera lista de los objetos procesando el tableModel.
	 */
	private void sincronizarBufferUsuarios() {
		bufferUsuarios.clear();
		for (int i = 0; i < tmUsuarios.getRowCount(); i++) {
			try {
				Nif nif = new Nif((String) tmUsuarios.getValueAt(i, 1));
				Correo correo = new Correo((String) tmUsuarios.getValueAt(i, 2));
				String nombre = (String) tmUsuarios.getValueAt(i, 3);
				String apellidos = (String) tmUsuarios.getValueAt(i, 4);
				DireccionPostal domicilio = new DireccionPostal((String) tmUsuarios.getValueAt(i, 5),
						(String) tmUsuarios.getValueAt(i, 6), (String) tmUsuarios.getValueAt(i, 7),
						(String) tmUsuarios.getValueAt(i, 8));
				Fecha fechaNacimiento = obtenerFechaFromDate(tmUsuarios.getValueAt(i, 9).toString());
				Fecha fechaAlta = obtenerFechaFromDate(tmUsuarios.getValueAt(i, 10).toString());
				ClaveAcceso claveAcceso = new ClaveAcceso();
				claveAcceso.setTextoEncriptado((String) tmUsuarios.getValueAt(i, 11));
				RolUsuario rol = null;
				switch ((String) tmUsuarios.getValueAt(i, 12)) {
				case "INVITADO":
					rol = RolUsuario.INVITADO;
					break;
				case "NORMAL":
					rol = RolUsuario.NORMAL;
					break;
				case "ADMINISTRADOR":
					rol = RolUsuario.ADMINISTRADOR;
					break;
				}
				// Genera y guarda objeto
				bufferUsuarios.add(new Usuario(nif, nombre, apellidos, domicilio, correo, fechaNacimiento, fechaAlta,
						claveAcceso, rol));
			} catch (ModeloException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Metodo que combierte el resultado que se obtiene de una consulta de un datetime a un objeto Fecha
	 * @param dateTime
	 * @return
	 */
	private Fecha obtenerFechaFromDate(String date) {
		String[] fecha = date.split("-");
		return new Fecha(Integer.parseInt(fecha[0]),Integer.parseInt(fecha[1]),Integer.parseInt(fecha[2]));
	}

	/**
	 * Da de alta un nuevo usuario en la base de datos.
	 * @param usr, el objeto a dar de alta.
	 * @throws DatosException 
	 */	
	@Override
	public void alta(Object obj) throws DatosException {	
		assert obj != null;
		Usuario usrNuevo = (Usuario) obj;
		Usuario usrObtenido = obtener(usrNuevo.getId());
		if (usrObtenido == null) {
			try {
				almacenar(usrNuevo);
				return;
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}  		
		}

		if (!usrNuevo.equals(usrObtenido)) {
			try {
				almacenar(producirVariantesIdUsr(usrNuevo));
				return;
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		throw new DatosException("UsuariosDAO.alta:" + usrNuevo.getId() +" Ya existe.");
	}

	/**
	 * Si hay coincidencia de identificador hace 23 intentos de variar la última
	 * letra procedente del NIF. Llama al generarVarianteIdUsr() de la clase
	 * Usuario.
	 * 
	 * @param usr
	 * @return usr con el id variado.
	 * @throws DatosException
	 */
	private Usuario producirVariantesIdUsr(Usuario usr) throws DatosException {
		assert usr != null;
		// Coincidencia de id. Hay que generar variante
		int intentos = "ABCDEFGHJKLMNPQRSTUVWXYZ".length();
		do {
			usr = new Usuario(usr); // El constructor copia generar variante.
			if (obtener(usr.getId()) == null) {
				return usr;
			}
			intentos--;
		} while (intentos >= 0);
		throw new DatosException("UsuariosDAO.alta: imposible generar variante del " + usr.getId());
	}
	
	/**
	 * almacena usuario en la base de datos.
	 * @param usr, el objeto a procesar.
	 * @throws SQLException  
	 */
	private void almacenar(Usuario usr) throws SQLException {
		assert usr != null;
		// Se realiza la consulta y los resultados quedan en el ResultSet actualizable.
		this.rsUsuarios = this.stUsuarios.executeQuery("SELECT * FROM usuarios");
		this.rsUsuarios.moveToInsertRow();
		this.rsUsuarios.updateString("id", usr.getId());
		this.rsUsuarios.updateString("nif", usr.getNif().getTexto());
		this.rsUsuarios.updateString("correo", usr.getCorreo().getTexto());
		this.rsUsuarios.updateString("nombre", usr.getNombre());
		this.rsUsuarios.updateString("apellidos", usr.getApellidos());
		this.rsUsuarios.updateString("calle", usr.getDomicilio().getCalle());
		this.rsUsuarios.updateString("numero", usr.getDomicilio().getNumero());
		this.rsUsuarios.updateString("cp", usr.getDomicilio().getCp());
		this.rsUsuarios.updateString("poblacion", usr.getDomicilio().getPoblacion());
		this.rsUsuarios.updateString("fechaNacimiento",usr.getFechaNacimiento().toDateSql());
		this.rsUsuarios.updateString("fechaAlta", usr.getFechaAlta().toDateSql());
		this.rsUsuarios.updateString("claveAcceso", usr.getClaveAcceso().getTexto());
		this.rsUsuarios.updateString("rol", usr.getRol().toString());
		this.rsUsuarios.insertRow();
		this.rsUsuarios.beforeFirst();	
	}

	/**
	 * Da de baja un usuario de la base de datos.
	 * 
	 * @param idUsr, id del usuario a dar de baja.
	 * @throws DatosException
	 */
	@Override
	public Usuario baja(String id) throws DatosException {
		assert id != null;
		assert !id.equals("");
		assert !id.equals(" ");

		Usuario usr = obtener(id);
		if (usr != null) {
			try {
				this.bufferUsuarios.remove(usr);
				this.stUsuarios.executeUpdate("DELETE FROM usuarios WHERE id = '" + usr.getId() + "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return usr;
		}
		throw new DatosException("BAJA: El Usuario " + id + " no existe...");
	}

	/**
	 * Modifica un usuario de la base de datos.
	 * 
	 * @param usr, objeto Usuario con los valores a cambiar.
	 * @throws DatosException
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		assert obj != null;
		Usuario usrActualizado = (Usuario) obj;
		Usuario usrPrevio = (Usuario) obtener(usrActualizado.getId());
		if (usrPrevio != null) {
			try {
				this.bufferUsuarios.remove(usrPrevio);
				this.bufferUsuarios.add(usrActualizado);
				this.stUsuarios.executeUpdate("UPDATE usuarios SET " + " nif = '" + usrActualizado.getNif() + "',"
						+ " correo = '" + usrActualizado.getCorreo().getTexto() + "'," + " nombre = '"
						+ usrActualizado.getNombre() + "'," + " apellidos = '" + usrActualizado.getApellidos() + "',"
						+ " calle = '" + usrActualizado.getDomicilio().getCalle() + "'," + " numero = '"
						+ usrActualizado.getDomicilio().getNumero() + "'," + " cp = '"
						+ usrActualizado.getDomicilio().getCp() + "'," + " poblacion = '"
						+ usrActualizado.getDomicilio().getPoblacion() + "'," + " fechaNacimiento = '"
						+ usrActualizado.getFechaNacimiento().toDateSql() + "'," + " fechaAlta = '"
						+ usrActualizado.getFechaAlta().toDateSql() + "'," + " claveAcceso = '"
						+ usrActualizado.getClaveAcceso().getTexto() + "'," + " rol = '"
						+ usrActualizado.getRol().toString() + "' WHERE id = '" + usrPrevio.getId() + "';");
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		throw new DatosException("Actualizar: " + usrActualizado.getId() + " no existe.");
	}

	/**
	 * Devuelve listado completo de usuarios.
	 * 
	 * @return texto con el volcado de todos los usuarios.
	 */
	@Override
	public String listarDatos() {
		return obtenerTodos().toString();
	}

	/**
	 * Obtiene todos los usuarios almacenados. Si no hay resultados devuelve null.
	 * 
	 * @param idUsr a obtener.
	 * @return (DefaultTableModel) result obtenido.
	 * @throws SQLException
	 */
	public ArrayList<Usuario> obtenerTodos() {
		try {
			// Se realiza la consulta y los resultados quedan en el ResultSet
			this.rsUsuarios = stUsuarios.executeQuery("SELECT * FROM usuarios");

			// Establece columnas y etiquetas
			this.establecerColumnasModelo();

			// Borrado previo de filas
			this.borrarFilasModelo();

			// Volcado desde el resulSet
			this.rellenarFilasModelo();

			// Actualiza buffer de objetos.
			this.sincronizarBufferUsuarios();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (ArrayList<Usuario>) this.bufferUsuarios;
	}

	@Override
	public void borrarTodo() {
		bufferUsuarios.clear();
		try {
			this.stUsuarios.executeUpdate("DELETE FROM usuarios");
			cargarPredeterminados();
		} catch (SQLException | DatosException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene el listado de todos id de los objetos almacenados.
	 * 
	 * @return el texto con el volcado de id.
	 */
	@Override
	public String listarId() {
	    List<Usuario> usuarios = obtenerTodos();
        StringBuilder usuariosAMostrar = new StringBuilder();
        for (int i = 0; i < usuarios.size(); i++) {
            usuariosAMostrar.append(usuarios.get(i).getId()).append("\n");
        }
        return usuariosAMostrar.toString();
	}

	/**
	 * Cierra conexiones.
	 */
	@Override
	public void cerrar() {
		try {
			stUsuarios.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

} // class
