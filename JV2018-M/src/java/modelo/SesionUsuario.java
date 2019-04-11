/** 
 * Proyecto: Juego de la vida.
 *  Implementa el concepto de SesionUsuario según el modelo2
 *  @since: prototipo1.0
 *  @source: SesionUsuario.java 
 *  @version: 2.0 - 2019.03.23
 *  @author: ajp
 */

package modelo;

import java.io.Serializable;

import util.Fecha;

public class SesionUsuario implements Identificable, Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usr;
	private Fecha fecha;
	public enum EstadoSesion { EN_PREPARACION, ACTIVA, CERRADA }
	private EstadoSesion estado;
	
	/**
	 * Constructor convencional. Utiliza métodos set...()
	 * @param usr
	 * @param fecha
	 */
	public SesionUsuario(Usuario usr, Fecha fecha, EstadoSesion estado) {
		setUsr(usr);
		setFecha(fecha);
		setEstado(estado);
	}

	/**
	 * Constructor por defecto. Utiliza constructor convencional.
	 * @throws ModeloException 
	 */
	public SesionUsuario() throws ModeloException {
		this(new Usuario(), new Fecha(), EstadoSesion.EN_PREPARACION);
	}

	/**
	 * Constructor copia.
	 * @param sesion
	 */
	public SesionUsuario(SesionUsuario sesion) {
		this.usr = new Usuario(sesion.usr);
		this.fecha = new Fecha(sesion.fecha.getAño(), sesion.fecha.getMes(), sesion.fecha.getDia());
		this.estado = sesion.estado;
	}

	public String getId() {	
		return this.usr.getId() + ":" + fecha.toStringMarcaTiempo();
	}
	
	public Usuario getUsr() {
		return usr;
	}

	public void setUsr(Usuario usr) {
		assert usr != null;
		this.usr = usr;
	}

	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		assert fecha != null;
		this.fecha = fecha;
	}

	public EstadoSesion getEstado() {
		return estado;
	}
	
	public void setEstado(EstadoSesion estado) {
		this.estado = estado;
	}
	
	/**
	 * Redefine el método heredado de la clase Objecto.
	 * @return el texto formateado del estado (valores de atributos) 
	 * del objeto de la clase SesionUsuario  
	 */
	@Override
	public String toString() {
		return String.format("SesionUsuario \n[usr=%s, \nfecha=%s, \nestado=%s]",
				usr, fecha, estado);
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
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((usr == null) ? 0 : usr.hashCode());
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
			if (usr.equals(((SesionUsuario)obj).usr) 
					&& fecha.equals(((SesionUsuario)obj).fecha)
					&& estado.equals(((SesionUsuario)obj).estado) 
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
		return new SesionUsuario(this);
	}

} // class
