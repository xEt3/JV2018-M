/** Proyecto: Juego de la vida.
 *  Implementa el concepto de Nif según el modelo2 
 *  @since: prototipo1.1
 *  @source: Nif.java 
 *  @version: 2.0 - 2019/03/23 
 *  @author: ajp
 */

package modelo;

import java.io.Serializable;

import config.Configuracion;
import util.Formato;

public class Nif implements Serializable {

	private static final long serialVersionUID = 1L;
	private String texto;

	public Nif() throws ModeloException {
		this(Configuracion.get().getProperty("nif.predeterminado"));
	}

	public Nif(String texto) throws ModeloException {
		setTexto(texto);
	}

	public Nif(Nif nif) {
		this.texto = new String(nif.texto);
	}

	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) throws ModeloException {
		assert texto != null;
		if (nifValido(texto)) {
			this.texto = texto;
		}
		else {
			if (this.texto == null) {					// En tiempo de constructor.	
				this.texto = new Nif().getTexto();		// Valor por defecto.
			}
			throw new ModeloException("Nif: formato no válido.");
		}
	}

	private boolean nifValido(String texto) {	
		texto = texto.toUpperCase();
		return texto.matches(Formato.PATRON_NIF) 
				&& letraNIFValida(texto);
	}

	/**
	 * Comprueba la validez de la letra de un NIF
	 * @param texto del NIF
	 * @return true si la letra es correcta.
	 */
	private boolean letraNIFValida(String texto) {
		int numeroNIF = Integer.parseInt(texto.substring(0,8));
		return texto.charAt(8) == Formato.LETRAS_NIF.charAt(numeroNIF % 23);
	} 
	
	/**
	 * Reproduce el estado -valores de atributos- de objeto en forma de texto. 
	 * @return el texto formateado.  
	 */
	@Override
	public String toString() {
		return this.texto;
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
		final int primo = 31;
		int result = 1;
		result = primo * result + ((texto == null) ? 0 : texto.hashCode());
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
			if (texto.equals(((Nif) obj).texto)) {
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
		return new Nif(this);
	}
	
}
