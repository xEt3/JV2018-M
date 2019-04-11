/** 
 *  Proyecto: Juego de la vida.
 *  Implementa el concepto de direccion postal según el modelo2
 *  Utiliza un varios string para representar los distintos campos.  
 *  @since: prototipo1.1
 *  @source: DireccionPostal.java 
 *  @version: 2.0 - 2019/03/23 
 *  @author: ajp
 */

package modelo;

import java.io.Serializable;

import config.Configuracion;
import util.Formato;

public class DireccionPostal implements Serializable {

	private static final long serialVersionUID = 1L;
	private String calle;
	private String numero;
	private String cp;
	private String poblacion;

	public DireccionPostal(String calle, String numero, String cp, String poblacion) throws ModeloException {
		setCalle(calle);
		setNumero(numero);
		setCp(cp);
		setPoblacion(poblacion);
	}

	public DireccionPostal() throws ModeloException {
		this(Configuracion.get().getProperty("direccion.predeterminada"));
	}
	
	public DireccionPostal(String textoDireccion) throws ModeloException {
		String[] campos = textoDireccion.split("[,.-/]");
		setCalle(campos[0]);
		setNumero(campos[1]);
		setCp(campos[2]);
		setPoblacion(campos[3]);
	}
	
	public DireccionPostal(DireccionPostal dp) {
		calle = new String(dp.calle);
		numero = new String(dp.numero);
		cp = new String(dp.cp);
		poblacion = new String(dp.poblacion);
	}

	public void setCalle(String calle) throws ModeloException {
		assert calle != null;
		if  (calleValida(calle)) {
			this.calle = calle;
		}
		else {
			if (this.calle == null) {							// En tiempo de constructor.	
				this.calle = new DireccionPostal().getCalle();	// Valor por defecto.
			}
			throw new ModeloException("DireccionPostal: formato no válido.");
		}
	}

	/**
	 * Comprueba validez de una calle.
	 * @param calle.
	 * @return true si cumple.
	 */
	private boolean calleValida(String calle) {
		return	calle.matches(Formato.PATRON_NOMBRE_VIA);
	}

	public void setNumero(String numero) throws ModeloException {
		assert numero != null;
		if (numeroValido(numero)) {
			this.numero = numero;
		}
		else {
			if (this.numero == null) {								// En tiempo de constructor.	
				this.numero = new DireccionPostal().getNumero();	// Valor por defecto.
			}
			throw new ModeloException("DireccionPostal: formato no válido.");
		}
	}

	/**
	 * Comprueba validez de un numero postal.
	 * @param numero.
	 * @return true si cumple.
	 */
	private boolean numeroValido(String numero) {
		return	numero.matches(Formato.PATRON_NUMERO_POSTAL);
	}

	public void setCp(String cp) throws ModeloException {
		assert cp != null;
		if (cpValido(cp)) {
			this.cp = cp;
		}
		else {
			if (this.cp == null) {							// En tiempo de constructor.	
				this.cp = new DireccionPostal().getCp();	// Valor por defecto.
			}
			throw new ModeloException("DireccionPostal: formato no válido.");
		}
	}

	/**
	 * Comprueba validez de un código postal.
	 * @param cp.
	 * @return true si cumple.
	 */
	private boolean cpValido(String cp) {
		// Número entre 01000 y 52999
		return cp.matches(Formato.PATRON_CP);
	}

	public void setPoblacion(String poblacion) throws ModeloException {
		assert poblacion != null;
		if (poblacionValida(poblacion)) {
			this.poblacion = poblacion;
		}
		else {
			if (this.poblacion == null) {								// En tiempo de constructor.	
				this.poblacion = new DireccionPostal().getPoblacion();	// Valor por defecto.
			}
			throw new ModeloException("DireccionPostal: formato no válido.");
		}
	}

	/**
	 * Comprueba validez de una población.
	 * @param poblacion.
	 * @return true si cumple.
	 */
	private boolean poblacionValida(String poblacion) {
		return	poblacion.matches(Formato.PATRON_TOPONIMO);
	}

	public String getCalle() {
		return calle;
	}

	public String getNumero() {
		return numero;
	}

	public String getCp() {
		return cp;
	}

	public String getPoblacion() {
		return poblacion;
	}

	/**
	 * Reproduce el estado -valores de atributos- de objeto en forma de texto. 
	 * @return el texto formateado.  
	 */
	@Override
	public String toString() {
		return calle + ", " + numero + ", " + cp + ", " + poblacion;
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
		result = prime * result + ((calle == null) ? 0 : calle.hashCode());
		result = prime * result + ((cp == null) ? 0 : cp.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((poblacion == null) ? 0 : poblacion.hashCode());
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
			if (calle.equals(((DireccionPostal)obj).calle) 
					&& cp.equals(((DireccionPostal)obj).cp) 
					&& numero.equals(((DireccionPostal)obj).numero) 
					&& poblacion.equals(((DireccionPostal)obj).poblacion)
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
		return new DireccionPostal(this);
	}

} // class
