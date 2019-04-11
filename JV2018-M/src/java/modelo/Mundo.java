/** 
 * Proyecto: Juego de la vida.
 * Organiza aspectos de gestión de la simulación según el modelo2
 * @since: prototipo1.2
 * @source: Simulacion.java 
 * @version: 2.0 - 2019.03.23
 * @author: ajp
 */

package modelo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import config.Configuracion;
import util.Formato;

public class Mundo implements Identificable, Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private byte[][] espacio;
	private int tamañoMundo;
	private List<Posicion> distribucion;
	private Map<String, int[]> constantes;
	public enum FormaEspacio { PLANO, ESFERICO }
	private FormaEspacio tipoMundo;

	public Mundo(String nombre, byte[][] espacio, 
			List distribucion, Map constantes, FormaEspacio tipoMundo) throws ModeloException {
		setNombre(nombre);
		setEspacio(espacio);
		setDistribucion(distribucion);
		setConstantes(constantes);
		setTipoMundo(tipoMundo);
		
		establecerTamañoMundo();	
		establecerLeyes();
	}

	public Mundo() throws ModeloException {	
		this(Configuracion.get().getProperty("mundo.nombrePredeterminado"), 
				new byte[Integer.parseInt(Configuracion.get().getProperty("mundo.sizePredeterminado"))]
						[Integer.parseInt(Configuracion.get().getProperty("mundo.sizePredeterminado"))], 
				new LinkedList<Posicion>(), 
				new HashMap<String, int[]>(), 
				FormaEspacio.PLANO
		);
	}

	public Mundo(Mundo mundo) {
		this.nombre = new String(mundo.nombre);
		this.espacio = mundo.espacio.clone();
		setDistribucion(new LinkedList<Posicion>(mundo.distribucion));
		this.constantes = new HashMap<String, int[]>(mundo.constantes);
		this.tipoMundo = mundo.tipoMundo;
		
		establecerTamañoMundo();	
		establecerLeyes();
	}
	
	public void setNombre(String nombre) throws ModeloException {	
		assert nombre != null;
		if (nombreValido(nombre)) {
			this.nombre = nombre;
		}
		else {
			throw new ModeloException("Nombre Mundo: Formato no válido.");
		}
	}
	
	/**
	 * Comprueba validez de formato del nombre.
	 * @param nombre.
	 * @return true si cumple.
	 */
	private boolean nombreValido(String nombre) {
		return nombre.matches(Formato.PATRON_NOMBRE_MUNDO_JV);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String getId() {
		return nombre;
	}
	
	public FormaEspacio getTipoMundo() {
		return tipoMundo;
	}
	
	public Map getConstantes() {
		return constantes;
	}
	
	public int getTamañoMundo() {
		return tamañoMundo;
	}
	
	public void setTipoMundo(FormaEspacio tipoMundo) {
		assert tipoMundo != null;
		this.tipoMundo = tipoMundo;
	}
	
	public void setEspacio(byte[][] espacio) {
		assert espacio != null;
		this.espacio = espacio;
	}
	
	private void setConstantes(Map constantes) {
		assert constantes != null;
		this.constantes = constantes;	
	}
	
	public void setDistribucion(List distribucion) {
		assert distribucion != null;
		this.distribucion = distribucion;
		cargarDistribucion();
	}
	private void establecerLeyes() {
		constantes.put("ValoresSobrevivir", new int[] {2, 3});
		constantes.put("ValoresRenacer", new int[] {3});
	}
	
	private void cargarDistribucion() {
		espacio = new byte[tamañoMundo][tamañoMundo];
		for(Posicion pos : distribucion) {
			espacio[pos.getX()][pos.getY()] = 1;
		}	
	}

	private void extraerDistribucion() {
		for (int i=0; i < espacio.length; i++) {
			for (int j=0; i < espacio.length; j++) {
				if(espacio[i][j] == 1) {	
					distribucion.add(new Posicion(i, j));
					tamañoMundo = i;
				}
			}	
		}
	}
	
	private void establecerTamañoMundo() {
		this.tamañoMundo = espacio.length;
	}
	
	/**
	 * hashCode() complementa al método equals y sirve para comparar objetos de forma 
	 * rápida en estructuras Hash. 
	 * Cuando Java compara dos objetos en estructuras de tipo hash (HashMap, HashSet etc)
	 * primero invoca al método hashcode y luego el equals.
	 * @return un número entero de 32 bit.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constantes == null) ? 0 : constantes.hashCode());
		result = prime * result + ((distribucion == null) ? 0 : distribucion.hashCode());
		result = prime * result + Arrays.deepHashCode(espacio);
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + tamañoMundo;
		result = prime * result + ((tipoMundo == null) ? 0 : tipoMundo.hashCode());
		return result;
	}

	/**
	 * Dos objetos son iguales si: 
	 * Son de la misma clase.
	 * Tienen los mismos valores en los atributos; o son el mismo objeto.
	 * @return falso si no cumple las condiciones.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			if (this == obj) {
				return true;
			}
			if (nombre.equals(((Mundo)obj).nombre) 
					&& Arrays.deepEquals(espacio, ((Mundo)obj).espacio)
					&& tamañoMundo == ((Mundo)obj).tamañoMundo
					&& distribucion.equals(((Mundo)obj).distribucion)
					&& constantes.equals(((Mundo)obj).constantes)
					&& tipoMundo.equals(((Mundo)obj).tipoMundo)
					) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Genera un clon del propio objeto realizando una copia profunda.
	 * @return el objeto clonado.
	 */
	@Override
	public Object clone() {
		// Utiliza el constructor copia.
		return new Mundo(this);
	}
	
	/**
	 * Despliega en texto el estado almacenado, corresponde
	 * a una generación del Juego de la vida.
	 */
	public String toStringEstadoMundo() {	
		StringBuilder salida = new StringBuilder();	
		for (int i = 0; i < espacio.length; i++) {
			for (int j = 0; j < espacio.length; j++) {		
				salida.append(espacio[i][j] == 1 ? "|o" : "| ");
			}
			salida.append("|\n");
		}
		return salida.toString();
	}

	/**
	 * Actualiza el estado del Juego de la Vida.
	 * Actualiza según la configuración establecida para la forma del espacio.
	 */
	public void actualizarMundo() {
		if (tipoMundo == FormaEspacio.PLANO) {
			actualizarMundoPlano();
		}
		if (tipoMundo == FormaEspacio.ESFERICO) {
			actualizarMundoEsferico();
		}
	}

	/**
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * Las celdas periféricas son adyacentes con las del lado contrario.
	 * El mundo representado sería esférico cerrado sin límites para células de dos dimensiones.
	 */
	private void actualizarMundoEsferico()  {     					
		byte[][] nuevoEstado = new byte[espacio.length][espacio.length];

		for (int i = 0; i < espacio.length; i++) {
			for (int j = 0; j < espacio.length; j++) {

				int filaSuperior = i-1;
				int filaInferior = i+1;
				// Reajusta filas adyacentes.
				if (i == 0) {
					filaSuperior = espacio.length-1;
				}
				if (i == espacio.length-1) {
					filaInferior = 0;
				}

				int colAnterior = j-1;
				int colPosterior = j+1;
				// Reajusta columnas adyacentes.
				if (j == 0) {
					colAnterior = espacio.length-1;
				}
				if (j == espacio.length-1) {
					colPosterior = 0;
				}

				int vecinas = 0;							
				vecinas += espacio[filaSuperior][colAnterior];			// Celda NO
				vecinas += espacio[filaSuperior][j];					// Celda N		NO | N | NE
				vecinas += espacio[filaSuperior][colPosterior];			// Celda NE   	-----------
				vecinas += espacio[i][colPosterior];					// Celda E		 O | * | E
				vecinas += espacio[filaInferior][colPosterior];			// Celda SE	  	----------- 
				vecinas += espacio[filaInferior][j]; 					// Celda S		SO | S | SE
				vecinas += espacio[filaInferior][colAnterior]; 			// Celda SO 
				vecinas += espacio[i][colAnterior];						// Celda O           			                                     	

				actualizarCelda(nuevoEstado, i, j, vecinas);
			}
		}
		espacio = nuevoEstado;
	}

	/**
	 * Actualiza el estado de cada celda almacenada del Juego de la Vida.
	 * Las celdas periféricas son los límites absolutos.
	 * El mundo representado sería plano, cerrado y con límites para células de dos dimensiones.
	 */
	private void actualizarMundoPlano()  {     					
		byte[][] nuevoEstado = new byte[espacio.length][espacio.length];

		for (int i = 0; i < espacio.length; i++) {
			for (int j = 0; j < espacio.length; j++) {
				int vecinas = 0;							
				vecinas += visitarCeldaNoroeste(i, j);		
				vecinas += visitarCeldaNorte(i, j);			// 		NO | N | NE
				vecinas += visitarCeldaNoreste(i, j);		//    	-----------
				vecinas += visitarCeldaEste(i, j);			// 		 O | * | E
				vecinas += visitarCeldaSureste(i, j);		// 	  	----------- 
				vecinas += visitarCeldaSur(i, j); 			// 		SO | S | SE
				vecinas += visitarCeldaSuroeste(i, j); 	  
				vecinas += visitarCeldaOeste(i, j);		          			                                     	

				actualizarCelda(nuevoEstado, i, j, vecinas);
			}
		}
		espacio = nuevoEstado;
	}

	/**
	 * Aplica las leyes del mundo a la celda indicada dada la cantidad de células adyacentes vivas.
	 * @param nuevoEstado
	 * @param fila
	 * @param col
	 * @param vecinas
	 */
	private void actualizarCelda(byte[][] nuevoEstado, int fila, int col, int vecinas) {	
		for (int valor : constantes.get("ValoresRenacer")) {
			if (vecinas == valor) {									// Pasa a estar viva.
				nuevoEstado[fila][col] = 1;
				return;
			}
		}	
		for (int valor : constantes.get("ValoresSobrevivir")) {
			if (vecinas == valor && espacio[fila][col] == 1) {		// Permanece viva, si lo estaba.
				nuevoEstado[fila][col] = 1;
				return;
			}
		}
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Oeste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Oeste.
	 */
	private byte visitarCeldaOeste(int fila, int col) {
		if (col-1 >= 0) {
			return espacio[fila][col-1]; 			// Celda O.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Suroeste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Suroeste.
	 */
	private byte visitarCeldaSuroeste(int fila, int col) {
		if (fila+1 < espacio.length && col-1 >= 0) {
			return espacio[fila+1][col-1]; 		// Celda SO.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Sur de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Sur.
	 */
	private byte visitarCeldaSur(int fila, int col) {
		if (fila+1 < espacio.length) {
			return espacio[fila+1][col]; 			// Celda S.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Sureste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Sureste.
	 */
	private byte visitarCeldaSureste(int fila, int col) {
		if (fila+1 < espacio.length && col+1 < espacio.length) {
			return espacio[fila+1][col+1]; 			// Celda SE.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Este de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Este.
	 */
	private byte visitarCeldaEste(int fila, int col) {
		if (col+1 < espacio.length) {
			return espacio[fila][col+1]; 			// Celda E.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Noreste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Noreste.
	 */
	private byte visitarCeldaNoreste(int fila, int col) {
		if (fila-1 >= 0 && col+1 < espacio.length) {
			return espacio[fila-1][col+1]; 			// Celda NE.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al NO de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda NO.
	 */
	private byte visitarCeldaNorte(int fila, int col) {
		if (fila-1 >= 0) {
			return espacio[fila-1][col]; 			// Celda N.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Noroeste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Noroeste.
	 */
	private byte visitarCeldaNoroeste(int fila, int col) {
		if (fila-1 >= 0 && col-1 >= 0) {
			return espacio[fila-1][col-1]; 			// Celda NO.
		}
		return 0;
	}

} // class
