/** 
 * Proyecto: Juego de la vida.
 * Coordenada de una celda del espacio según el modelo2
 * @since: prototipo1.2
 * @source: Posicion.java 
 * @version: 2.0 - 2019.03.25
 * @author: ajp
 */

package modelo;

import java.io.Serializable;

public class Posicion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	
	public Posicion(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Posicion() {
		this(0, 0);
	}

	public Posicion(Posicion posicion) {
		this.x = posicion.x;
		this.y = posicion.y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) throws ModeloException {
		if (coordenadaValida(x)) {
			this.x = x;
		}
		else {
			throw new ModeloException("Posicion: coordenada negativa");
		}
	}
	
	private boolean coordenadaValida(int c) {
		return c >= 0;
	}

	public int getY() {
		return y;
	}
	
	public void setY(int y) throws ModeloException {
		if (coordenadaValida(y)) {
			this.y = y;
		}
		else {
			throw new ModeloException("Posicion: coordenada negativa");
		}
	}

	/**
	 * Reproduce el estado -valores de atributos- de objeto en forma de texto. 
	 * @return el texto formateado.  
	 */
	@Override
	public String toString() {
		return String.format("Posicion [x=%s, y=%s]", x, y);
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
		result = prime * result + x;
		result = prime * result + y;
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
			if (x == ((Posicion)obj).x 
					&& y == ((Posicion)obj).y  
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
	public Posicion clone() {
		return new Posicion(this);
	}
	
} // class
