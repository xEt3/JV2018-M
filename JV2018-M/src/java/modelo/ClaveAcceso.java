/** 
 *  Proyecto: Juego de la vida.
 *  Implementa el concepto de ClaveAcceso según el modelo2
 *  Utiliza un string para representar el texto del ClaveAcceso.  
 *  @since: prototipo1.2
 *  @source: ClaveAcceso.java 
 *  @version: 2.0 - 2019/03/23
 *  @author: ajp
 */

package modelo;

import java.io.Serializable;

import config.Configuracion;
import util.Criptografia;
import util.Formato;

public class ClaveAcceso implements Serializable {

	private static final long serialVersionUID = 1L;
	private String texto;

	public ClaveAcceso() throws ModeloException {
		this(Configuracion.get().getProperty("claveAcceso.predeterminada"));
	}

	public ClaveAcceso(String texto) throws ModeloException   {
		setTexto(texto);
	}

	public ClaveAcceso(ClaveAcceso ClaveAcceso) {
		this.texto = new String(ClaveAcceso.texto);
	}

	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) throws ModeloException {
		assert texto != null;
		if (ClaveAccesoValida(texto)) {
			this.texto = Criptografia.cesar(texto);
		}
		else {
			if (this.texto == null) {							// En tiempo de constructor.	
				this.texto = new ClaveAcceso().getTexto();		// Valor por defecto.
			}
			throw new ModeloException("ClaveAcceso: formato no válido.");
		}
	}
	
	private boolean ClaveAccesoValida(String texto) {
		return texto.matches(Formato.PATRON_CONTRASEÑA4);
	}

	/**
	 * Reproduce el estado -valores de atributos- de objeto en forma de texto. 
	 * @return el texto formateado.  
	 */
	@Override
	public String toString() {
		return texto;
	}
	
	/**
	 * hashcode() complementa al método equals y sirve para comparar objetos de forma 
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
			if (texto.equals(((ClaveAcceso) obj).texto)) {
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
		return new ClaveAcceso(this);
	}

} // class
