/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Mundo utilizando una base de datos.
 * Aplica el patron Singleton.
 * Colabora en el patrón Façade.
 * @since: prototipo2.1
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

package accesoDatos.mySql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import config.Configuracion;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Mundo.FormaEspacio;
import modelo.Posicion;

public class MundosDAO implements OperacionesDAO {

	// Singleton.
	private static MundosDAO instance;

	// Elemento de almacenamiento, base datos mySql.
	private Connection db;
	private Statement stMundo;
	private ArrayList<Mundo> bufferMundos;
	private DefaultTableModel tmMundos;
	private ResultSet rsMundo;

	/**
	 * Método estático de acceso a la instancia única. Si no existe la crea
	 * invocando al constructor interno. Utiliza inicialización diferida. Sólo se
	 * crea una vez; instancia única -patrón singleton-
	 * 
	 * @return instance
	 */
	public static MundosDAO getInstance() {
		if (instance == null) {
			instance = new MundosDAO();
		}
		return instance;
	}

	/**
	 * Constructor por defecto de uso interno. Sólo se ejecutará una vez.
	 */
	private MundosDAO() {
		db = Conexion.getDB();
		try {
			inicializar();
		} catch (SQLException e) {
		}
		
		if (obtener(Configuracion.get().getProperty("mundo.nombrePredeterminado")) == null) {
			cargarPredeterminados();
		}
	}

	/**
	 * Inicializa el DAO, detecta si existen las tablas de datos capturando la excepcion SQLException
	 * @throws SQLException
	 */
	private void inicializar() throws SQLException {
		db = Conexion.getDB();
		try {
			
			stMundo = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			crearTablaMundo();
		} catch (SQLException e) {
		}	
		
		
		tmMundos = new DefaultTableModel();
		bufferMundos = new ArrayList<>();
	}

	private void crearTablaMundo() {
		try {
			stMundo.executeUpdate("CREATE TABLE IF NOT EXISTS MUNDO(" + 
					"  nombre VARCHAR(100) NOT NULL PRIMARY KEY," + 
					"  espacioX INT NOT NULL," + 
					"  espacioY INT NOT NULL," + 
					"  distribucion VARCHAR(255) NOT NULL," + 
					"  valoresSobrevivir VARCHAR(100) NOT NULL," + 
					"  valoresRenacer VARCHAR(100) NOT NULL," + 
					"  tipoMundo ENUM(\"PLANO\",\"ESFERICO\") NOT NULL" + 
					")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para generar de datos predeterminados.
	 */
	private void cargarPredeterminados() {
		try {
			Mundo mundoDemo = new Mundo(); // En este array los 0 indican celdas con célula muerta y los 1 vivas
			byte[][] espacioDemo = new byte[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Planeador
					{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Flip-Flop
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Still Life
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } //
			};
			mundoDemo.setEspacio(espacioDemo);
			mundoDemo.setTipoMundo(Mundo.FormaEspacio.ESFERICO);
			alta(mundoDemo);
		} catch (DatosException | ModeloException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cierra conexión.
	 */
	@Override
	public void cerrar() {
		try {
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Mundo obtener(String id) {
		assert id != null;
		ejecutarConsulta(id);
		etiquetarColumnasModelo(); 
		borrarFilasModelo();
		rellenarFilasModelo();
		sincronizarBufferMundos();
		
		if(bufferMundos.size() > 0) {
			return (Mundo) bufferMundos.get(0);
		}
		return null;
	}

	private void ejecutarConsulta(String idMundo) {
		try {
			rsMundo = stMundo.executeQuery("select * from MUNDO where nombre = '"+idMundo+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Crea las columnas del TableModel a partir de los metadatos del ResultSet
	 * de la base de datos
	 */

	private void etiquetarColumnasModelo() {
		try {
			ResultSetMetaData metaDatos = this.rsMundo.getMetaData();
			
			// numero total de columnas
			int numCol = metaDatos.getColumnCount();
			
			//etiqueta de cada columna
			Object[] etiquetas = new Object[numCol];
			for (int i = 0; i < numCol; i++) {
				etiquetas[i] = metaDatos.getColumnLabel(i + 1);
			}
			
			// Incorpora array de etiquetas en el TableModel.
			this.tmMundos.setColumnIdentifiers(etiquetas);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void borrarFilasModelo() {
		while(this.tmMundos.getRowCount()>0) {
			this.tmMundos.removeRow(0);
		}
		
	}

	private void rellenarFilasModelo() {
		Object[] datosFila = new Object[this.tmMundos.getColumnCount()];
		
		try {
			while(rsMundo.next()) {
				for (int i = 0; i < this.tmMundos.getColumnCount(); i++) {
					datosFila[i] = this.rsMundo.getObject(i+1);
				}
				this.tmMundos.addRow(datosFila);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void sincronizarBufferMundos() {
		bufferMundos.clear();
		for (int i = 0; i < tmMundos.getRowCount(); i++) {
			try {
				String nombre = (String) tmMundos.getValueAt(i, 0);
				int espacioX = (Integer) tmMundos.getValueAt(i, 1);
				int espacioY = (Integer) tmMundos.getValueAt(i, 2);
				byte[][] espacio = new byte[espacioX][espacioY];
				List<Posicion> distribucion = obtenerDistribucion((String)tmMundos.getValueAt(i, 3));
				int valoresSobrevivir[] = obtenerReglas((String) tmMundos.getValueAt(i, 4));
				int valoresRenacer[] = obtenerReglas((String) tmMundos.getValueAt(i, 5));
				Map<String, int[]> constantes = new HashMap<String, int[]>();
				constantes.put("ValoresSobrevivir", valoresSobrevivir);
				constantes.put("ValoresRenacer", valoresRenacer);
				FormaEspacio tipoMundo = obtenerTipoMundo((String)tmMundos.getValueAt(i,6));
				bufferMundos.add(new Mundo(nombre,espacio,distribucion,constantes,tipoMundo));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	private List<Posicion> obtenerDistribucion(String distribucionFormateada) {
		String[] coordenadas = distribucionFormateada.split(",");
		List<Posicion> distribucion = new LinkedList<Posicion>();
		if(coordenadas[0].length()==1) {
			return distribucion;
		}
		for (int i = 0; i < coordenadas.length; i++) {
			int x = coordenadas[i].charAt(0);
			int y = coordenadas[i].charAt(1);
			Posicion posicion = new Posicion(x,y);
			distribucion.add(posicion);
		}
		return distribucion;
	}

	private int[] obtenerReglas(String reglasFormateadas) {
		String[] valores = reglasFormateadas.split(",");
		int[] valorFinal = new int[valores.length];
		for (int i = 0; i < valores.length; i++) {
			valorFinal[i]= Integer.parseInt(valores[i]);
		}
		return valorFinal;
	}

	private FormaEspacio obtenerTipoMundo(String tipoMundoFomateado) {
		if(tipoMundoFomateado.equals("PLANO")) {
			return FormaEspacio.PLANO;
		}
		if(tipoMundoFomateado.equals("ESFERICO")) {
			return FormaEspacio.ESFERICO;
		}
		return FormaEspacio.PLANO;
	}

	public void establecerColumnasModelo() {
		try {
			ResultSetMetaData metaDatos = this.rsMundo.getMetaData();
			int numCol = metaDatos.getColumnCount();
			Object[] etiquetas = new Object[numCol];
			for (int i = 0; i < numCol; i++) {
				etiquetas[i] = metaDatos.getColumnLabel(i+1);
			}
			
			this.tmMundos.setColumnIdentifiers(etiquetas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Búsqueda de Usuario dado un objeto, reenvía al método que utiliza nombre.
	 * @param obj - el Mundo a buscar.
	 * @return - el Mundo encontrado o null si no existe.
	 */
	public Mundo obtener(Mundo obj) throws DatosException {
		assert obj != null;
		return (Mundo) this.obtener(obj.getId());
	}
	
	@Override
	public List<Mundo> obtenerTodos() {
		try {
			rsMundo = stMundo.executeQuery("select * from MUNDO");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		etiquetarColumnasModelo();
		borrarFilasModelo();
		rellenarFilasModelo();
		sincronizarBufferMundos();
		return this.bufferMundos;
	}

	@Override
	public void alta(Object obj) throws DatosException {
		assert obj != null;
		Mundo mundo = (Mundo)obj;
		
		String sqlQuery = obtenerConsultaAlta(mundo);
		
		if (obtener(mundo.getId()) == null) {
			try {
				Statement statement = db.createStatement();
				statement.executeUpdate(sqlQuery);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			throw new DatosException("Mundo dado de Alta ya existe con el nombre: " + mundo.getId());
		}
		
	}
	
	/**
	 * Consulta que se lanza para dar de alta el mundo en la base de datos.
	 * @param mundo
	 * @return - Devuelve la QuerySQL para dar de alta el mundo.
	 */
	private String obtenerConsultaAlta(Mundo mundo) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("INSERT INTO MUNDO (nombre, espacioX, espacioY, distribucion,ValoresSobrevivir, ValoresRenacer, tipoMundo) VALUES "
				+ "('" + mundo.getNombre() 
				+ "'," + mundo.getEspacio().length 
				+ "," + mundo.getEspacio()[0].length 
				+ ",'" + formatearDistribucion(mundo.getDistribucion())
				+"','" + formatearRegla((int[]) mundo.getConstantes().get("ValoresSobrevivir")) 
				+ "','"+ formatearRegla((int[]) mundo.getConstantes().get("ValoresRenacer")) 
				+ "','" + formatearTipoMundo(mundo.getTipoMundo()) +"')");
		
		return sqlQuery.toString();
	}

	@Override
	public Mundo baja(String id) throws DatosException {
		assert id != null;
		assert !id.matches("");
		assert !id.matches("[ ]+");
		
		Mundo mundo = (Mundo) obtener(id);
		
		if (mundo != null) {
			try {
				bufferMundos.remove(mundo);
				String sql = "DELETE FROM MUNDO WHERE Nombre='" + id + "'";
				stMundo.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return mundo;
		}
		throw new DatosException("Baja: " + id + " no existe...");
	}
	
	
	/**
	 * Actualiza un mundo de la base de datos.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		assert obj != null;
		Mundo mundoActualizado = (Mundo) obj;
		Mundo mundoPrevio = (Mundo) obtener(mundoActualizado.getId());
		String sqlQuery = obtenerConsultaActualizar(mundoActualizado);
		if (mundoPrevio != null) {
			try {
				this.bufferMundos.remove(mundoPrevio);
				this.bufferMundos.add(mundoActualizado);
				this.stMundo.executeUpdate(sqlQuery);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		throw new DatosException("Actualizar: "+ mundoActualizado.getId() + " no existe.");
	}
	
	/**
	 * Consulta que se lanza para actualizar el mundo de la base de datos.
	 * @param mundoActualizado
	 * @return - Devuelve la QuerySQL para actualizar el mundo.
	 */
	private String obtenerConsultaActualizar(Mundo mundoActualizado) {
		String distribucion = formatearDistribucion(mundoActualizado.getDistribucion());
		int espacioX = mundoActualizado.getEspacio().length;
		int espacioY = mundoActualizado.getEspacio()[0].length;
		String valoresSobrevivir = formatearRegla((int[]) mundoActualizado.getConstantes().get("ValoresSobrevivir"));
		String valoresRenacer = formatearRegla((int[]) mundoActualizado.getConstantes().get("ValoresRenacer"));
		String tipoMundo = formatearTipoMundo(mundoActualizado.getTipoMundo());
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE MUNDO SET distribucion = '" + distribucion + "', espacioX = '" + espacioX + "', espacioY = '" + espacioY
				+ "', valoresSobrevivir = '" + valoresSobrevivir + "', valoresRenacer = '" + valoresRenacer	+ "', tipoMundo ='" + tipoMundo
				+ "' WHERE nombre LIKE'"+ mundoActualizado.getNombre()+"'");
		return query.toString();
	}

	@Override
	public String listarDatos() {
	    List<Mundo> mundos = obtenerTodos();
        StringBuilder mundosAMostrar = new StringBuilder();
        for (int i = 0; i < mundos.size(); i++) {
            mundosAMostrar.append(mundos.get(i).getId()).append("\n");
        }
        return mundosAMostrar.toString();
	}

	@Override
	public String listarId() {
	    List<Mundo> mundos = obtenerTodos();
        StringBuilder mundosAMostrar = new StringBuilder();
        for (int i = 0; i < mundos.size(); i++) {
            mundosAMostrar.append(mundos.get(i).getId()).append("\n");
        }
        return mundosAMostrar.toString();
	}

	@Override
	public void borrarTodo() {
		try {
			stMundo = db.createStatement();
			stMundo.executeUpdate("DELETE FROM MUNDO");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String formatearDistribucion(List<Posicion> distibucion) {
		StringBuilder valorFormateado = new StringBuilder();
		
		for (int i = 0; i < distibucion.size(); i++) {
			int x = distibucion.get(i).getX();
			int y = distibucion.get(i).getY();
			valorFormateado.append(x + y + ",");
		}
		if (valorFormateado.length() == 0) {
			return valorFormateado.append("0").toString();
		}
		return valorFormateado.toString();
	}
	
	private String formatearRegla(int[] reglas) {
		StringBuilder reglaFormateada = new StringBuilder();
        String valoresFormateados;
        for (int i = 0; i < reglas.length; i++) {
            reglaFormateada.append(reglas[i] + ",");
        }
        valoresFormateados = reglaFormateada.substring(0,reglaFormateada.length() - 1);
        return valoresFormateados;
	}

	private String formatearTipoMundo(FormaEspacio tipoMundo) {
		if(tipoMundo.equals(FormaEspacio.PLANO)) {
			return "PLANO";
		}
		if(tipoMundo.equals(FormaEspacio.ESFERICO)) {
			return "ESFERICO";
		}
		return "PLANO";
	}

} // class
