/** Proyecto: Juego de la vida.
 *  Implementa el concepto de Usuario según el modelo2.
 *  @since: prototipo2.0
 *  @source: Usuario.java 
 *  @version: 2.0 - 2019/03/23
 *  @author: ajp
 */

package modelo;

import config.Configuracion;
import util.Fecha;
import util.Formato;


public class Usuario extends Persona implements Identificable {

	private static final long serialVersionUID = 1L;
	private String id;
	private Fecha fechaAlta;
	private ClaveAcceso claveAcceso;
	public enum RolUsuario { INVITADO, NORMAL, ADMINISTRADOR };
	private RolUsuario rol;
	
	/**
	 * Constructor convencional. Utiliza métodos set...()
	 * @param nif
	 * @param nombre
	 * @param apellidos
	 * @param domicilio
	 * @param correo
	 * @param fechaNacimiento
	 * @param fechaAlta
	 * @param claveAcceso
	 * @param rol
	 * @throws ModeloException 
	 */
	public Usuario(Nif nif, String nombre, String apellidos,
			DireccionPostal domicilio, Correo correo, Fecha fechaNacimiento,
			Fecha fechaAlta, ClaveAcceso claveAcceso, RolUsuario rol) 
					throws ModeloException {
		super(nif, nombre, apellidos,
				domicilio, correo, fechaNacimiento);
		setFechaAlta(fechaAlta);
		setClaveAcceso(claveAcceso);
		setRol(rol);
		generarId();	
	}

	/**
	 * Constructor por defecto. Reenvía al constructor convencional.
	 * @throws ModeloException 
	 */
	public Usuario() throws ModeloException {
		this(new Nif(), 
			Configuracion.get().getProperty("usuario.nombrePredeterminado"), 
			Configuracion.get().getProperty("usuario.nombrePredeterminado") + " " + Configuracion.get().getProperty("usuario.nombrePredeterminado"), 
			new DireccionPostal(),
			new Correo(), 
			new Fecha(Configuracion.get().getProperty("usuario.fechaNacimientoPredeterminada")), 
			new Fecha(), 
			new ClaveAcceso(), 
			RolUsuario.INVITADO);
	}

	/**
	 * Constructor copia.
	 * @param usr
	 */
	public Usuario(Usuario usr) {
		super(usr);
		this.id = new String(usr.id);
		this.fechaAlta = new Fecha(usr.fechaAlta.getAño(), 
				usr.fechaAlta.getMes(), usr.fechaAlta.getDia());
		this.claveAcceso = new ClaveAcceso(usr.claveAcceso);
		this.rol = usr.rol;
		this.generarVarianteIdUsr();
		
	}
	
	/**
	 * Genera un identificador sintético a partir de:
	 * La letra inicial del nombre, 
	 * Las dos iniciales del primer y segundo apellido,
	 * Los dos último caracteres del nif.
	 */
	private void generarId() {
		assert this.nif != null;
		assert this.nombre != null;
		assert this.apellidos != null;
		String[] apellidos = this.apellidos.split(" ");
		this.id =  ""+ this.nombre.charAt(0) 
				+ apellidos[0].charAt(0) + apellidos[1].charAt(0)
				+ this.nif.getTexto().substring(7);
		this.id = this.id.toUpperCase();
	}
	
	/**
	 * Genera una variante cambiando la última letra del idUsr 
	 * por la siguiente en el alfabeto previsto para el nif.
	 * @param idUsr
	 */
	private void generarVarianteIdUsr() {
		String alfabetoNif = Formato.LETRAS_NIF;
		String alfabetoNifDesplazado = alfabetoNif.substring(1) + alfabetoNif.charAt(0);
		this.id = this.id.substring(0, 4) 
				+ alfabetoNifDesplazado.charAt(alfabetoNif.indexOf(id.charAt(4)));
	}
	
	@Override
	public String getId() {
		return id;
	}

	/**
	 * Comprueba validez de una fecha de nacimiento.
	 * @param fechaNacimiento.
	 * @return true si cumple.
	 */
	@Override
	protected boolean fechaNacimientoValida(Fecha fechaNacimiento) {
		return !fechaNacimiento.after(new Fecha().addAños(
				-Integer.parseInt(Configuracion.get().getProperty("usuario.edadMinima"))));
	}

	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) throws ModeloException {
		assert fechaAlta != null;
		if (fechaAltaValida(fechaAlta)) {
			this.fechaAlta = fechaAlta;
		}
		else {
			throw new ModeloException("Fecha alta Usuario: no válida.");
		}
	}

	/**
	 * Comprueba validez de una fecha de alta.
	 * @param fechaAlta.
	 * @return true si cumple.
	 */
	private boolean fechaAltaValida(Fecha fechaAlta) {
		return !fechaAlta.after(new Fecha()); 	
	}

	public ClaveAcceso getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(ClaveAcceso claveAcceso) {
		assert claveAcceso != null;
		this.claveAcceso = claveAcceso;
	}

	public RolUsuario getRol() {
		return rol;
	}

	public void setRol(RolUsuario rol) {
		assert	rol != null;
		this.rol = rol;
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
		int result = super.hashCode();
		result = prime * result + ((claveAcceso == null) ? 0 : claveAcceso.hashCode());
		result = prime * result + ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		return result;
	}

	/**
	 * Dos objetos son iguales si: 
	 * Son de la misma clase.
	 * Tienen los mismos valores en la parte heredada.
	 * Tienen los mismos valores en los atributos; o son el mismo objeto.
	 * @return falso si no cumple las condiciones.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			if (this == obj) {
				return true;
			}
			if (super.equals((Persona)obj)
					&& id.equals(((Usuario)obj).id)  
					&& claveAcceso.equals(((Usuario)obj).claveAcceso) 
					&& fechaAlta.equals(((Usuario)obj).fechaAlta)
					&& rol.equals(((Usuario)obj).rol)
					) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Redefine el método heredado de la clase Objecto.
	 * @return el texto formateado del estado -valores de atributos- de objeto de la clase Usuario.  
	 */
	@Override
	public String toString() {
		return super.toString() + String.format(
				"%-16s %s\n"
				+ "%-16s %s\n"
				+ "%-16s %s\n"
				+ "%-16s %s\n",
				"id:", id,	
				"fechaAlta:", this.fechaAlta, 
				"claveAcceso:", this.claveAcceso, 
				"rol:", this.rol
		);		
	}

	/**
	 * Genera un clon del propio objeto realizando una copia profunda.
	 * @return el objeto clonado.
	 */
	@Override
	public Usuario clone() {
		// Utiliza el constructor copia.
		return new Usuario(this);
	}
	
} // class

