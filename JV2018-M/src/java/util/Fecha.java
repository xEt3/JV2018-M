/** 
 *  Proyecto: Juego de la vida.
 *  Clase-utilidad que adapta el uso de un Calendario para manejo de fechas en el programa.
 *  @since: prototipo1.1
 *  @source: Fecha.java 
 *  @version: 2.0 - 2019/04/21
 *  @author: ajp
 */

package util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Fecha implements Serializable {

	private Calendar tiempo;

	public Fecha(int año, int mes, int dia, int hora, int minuto, int segundo) {
		this.tiempo = new GregorianCalendar(año, mes-1, dia, hora, minuto, segundo);
	}
	
	public Fecha(int año, int mes, int dia) {
		this(año, mes, dia, 0, 0, 0);
	}
	
	public Fecha() {
		this.tiempo = new GregorianCalendar(); 
	}
	
	public Fecha(Fecha fecha) {
		this.tiempo = (Calendar) fecha.tiempo.clone();
	}

	public Fecha(String textoFecha) {
		String[] campos = textoFecha.split("[.-/]");
		this.tiempo = new GregorianCalendar(Integer.parseInt(campos[0]), Integer.parseInt(campos[1])-1, Integer.parseInt(campos[2])); 
	}
	
	@SuppressWarnings("deprecation")
	public Fecha(Date date) {
		this(date.getYear(), date.getMonth(), date.getDate(), 
				date.getHours(), date.getMinutes(), date.getSeconds());
	}
	
	public Fecha(long marcaTiempo) {
		this(new Date(marcaTiempo)); 
	}
	
	public int getAño() {
		return this.tiempo.get(Calendar.YEAR);
	}

	public int getMes() {
		return this.tiempo.get(Calendar.MONTH) + 1;
	}
	
	public int getDia() {
		return this.tiempo.get(Calendar.DAY_OF_MONTH);
	}

	public int getHora() {
		return this.tiempo.get(Calendar.HOUR_OF_DAY);
	}

	public int getMinuto() {
		return this.tiempo.get(Calendar.MINUTE);
	}

	public int getSegundo() {
		return this.tiempo.get(Calendar.SECOND);
	}
	
	public long getMarcaTiempoMilisegundos() {	
		return this.tiempo.getTimeInMillis();
	}
	
	public void setAño(int año) {
		this.tiempo.set(Calendar.YEAR, año);
	}
	
	public void setMes(int mes) {
		this.tiempo.set(Calendar.MONTH, mes-1);
	}
	
	public void setDia(int dia) {
		this.tiempo.set(Calendar.DAY_OF_MONTH, dia);
	}
	
	public void setHora(int hora) {
		this.tiempo.set(Calendar.HOUR_OF_DAY, hora);
	}

	public void setMinuto(int minuto) {
		this.tiempo.set(Calendar.MINUTE, minuto);
	}
	
	public void setSegundo(int segundo) {
		this.tiempo.set(Calendar.SECOND, segundo);
	}
	
	/**
	 * Obtiene la diferencia en minutos entre dos fechas
	 * @param fecha
	 * @return número de minutos
	 */
	public long difSegundos(Fecha fecha) {
		return (this.tiempo.getTimeInMillis() 
				- fecha.tiempo.getTimeInMillis()) / 1000;
	}
	
	/**
	 * Obtiene la diferencia en minutos entre dos fechas
	 * @param fecha
	 * @return número de minutos
	 */
	public long difMinutos(Fecha fecha) {
		return (this.tiempo.getTimeInMillis() 
				- fecha.tiempo.getTimeInMillis()) / (60 * 1000);
	}
	
	/**
	 * Obtiene la diferencia en horas entre dos fechas
	 * @param fecha
	 * @return número de horas
	 */
	public long difHoras(Fecha fecha) {
		return (this.tiempo.getTimeInMillis() 
				- fecha.tiempo.getTimeInMillis()) / (60 * 60 * 1000);
	}
	
	/**
	 * Obtiene la diferencia en dias entre dos fechas
	 * @param fecha
	 * @return número de dias
	 */
	public int difDias(Fecha fecha) {
		return (int) (this.tiempo.getTimeInMillis() 
				- fecha.tiempo.getTimeInMillis()) / (24 * 60 * 60 * 1000);
	}
	
	/**
	 * Obtiene la diferencia en semanas entre dos fechas
	 * @param fecha
	 * @return número de semanas
	 */
	public int difSemanas(Fecha fecha) {
		return (int) (this.tiempo.getTimeInMillis() 
				- fecha.tiempo.getTimeInMillis()) / (7 * 24 * 60 * 60 * 1000);
	}
	
	/**
	 * Obtiene la diferencia en meses de 30 días entre dos fechas
	 * @param fecha
	 * @return número de meses
	 */
	public int difMeses(Fecha fecha) {
		return (int) (this.tiempo.getTimeInMillis() 
				- fecha.tiempo.getTimeInMillis()) / (30 * 24 * 60 * 60 * 1000);
	}
	
	/**
	 * Obtiene la diferencia en años de 365 días entre dos fechas
	 * @param fecha
	 * @return número de años
	 */
	public int difAños(Fecha fecha) {
		return (int) (this.tiempo.getTimeInMillis() 
				- fecha.tiempo.getTimeInMillis()) / (365 * 24 * 60 * 60 * 1000);
	}
	
	/**
	 * Añade una cantidad de segundos a la hora y fecha.
	 * @param segundos - segundos a añadir.
	 * @return el propio objecto modificado.
	 */
	public Fecha addSegundos(int segundos) {
		this.tiempo.add(Calendar.SECOND, segundos);
		return this;
	}
	
	/**
	 * Añade una cantidad de minutos a la hora y fecha
	 * @param minutos - minutos a añadir
	 * @return el propio objecto modificado.
	 */
	public Fecha addMinutos(int minutos) {
		this.tiempo.add(Calendar.MINUTE, minutos);
		return this;
	}
	
	/**
	 * Añade una cantidad de horas a la hora y fecha
	 * @param horas - horas a añadir
	 * @return el propio objecto modificado.
	 */
	public Fecha addHoras(int horas) {
		this.tiempo.add(Calendar.HOUR, horas);
		return this;
	}
	
	/**
	 * Añade una cantidad de dias a la fecha
	 * @param dias - dias a añadir
	 * @return el propio objecto modificado.
	 */
	public Fecha addDias(int dias) {
		this.tiempo.add(Calendar.DAY_OF_MONTH, dias);
		return this;
	}
	
	/**
	 * Añade una cantidad de semanas a la fecha
	 * @param semanas - semanas a añadir 
	 * @return el propio objecto modificado.
	 */
	public Fecha addSemanas(int semanas) {
		this.tiempo.add(Calendar.WEEK_OF_MONTH, semanas);
		return this;
	}

	/**
	 * Añade una cantidad de meses a la fecha
	 * @param meses - meses a añadir
	 * @return el propio objecto modificado.
	 */
	public Fecha addMeses(int meses) {
		this.tiempo.add(Calendar.MONTH, meses);
		return this;
	}
	
	/**
	 * Añade una cantidad de años a la fecha
	 * @param años - años a añadir
	 * @return el propio objecto modificado.
	 */
	public Fecha addAños(int años) {
		this.tiempo.add(Calendar.YEAR, años);
		return this;
	}
	
	public Date toDate() {
		return (Date) tiempo.getTime();
		//return new Date(tiempo.getTimeInMillis());
	}
	
	public GregorianCalendar toGregorianCalendar() {
		return (GregorianCalendar) tiempo;
	}
	
	public int compareTo(Fecha fecha) {
		return this.tiempo.compareTo(fecha.tiempo);
	}
	
	public boolean before(Fecha fecha) {
		return this.tiempo.before(fecha.tiempo);
	}
	
	public boolean after(Fecha fecha) {
		return this.tiempo.after(fecha.tiempo);
	}
	
	/**
	 * Obtiene texto de 14 dígitos normalizado de la marca de tiempo con precisión de segundo.
	 * @return el texto formateado compacto.  
	 */
	public String toStringMarcaTiempo() {
		return String.format(
				"%4d%02d%02d%02d%02d%02d", getAño(), getMes(), getDia(), getHora(), getMinuto(), getSegundo());		
	}
	
	@Override
	public String toString() {
		return String.format("%4d.%02d.%02d - %02d:%02d:%02d", 
				getAño(), getMes(), getDia(), getHora(), getMinuto(), getSegundo());
	}

	/**
	 * Dos objetos son iguales si: 
	 * Son de la misma clase.
	 * Tienen los mismos valores en los atributos; o son el mismo objeto.
	 * @return false si no cumple las condiciones.
	*/
	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			if (this == obj) {
				return true;
			}
			if (tiempo.getTimeInMillis() == ((Fecha) obj).tiempo.getTimeInMillis()) {
				return true;
			}
		}
		return false;
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
		result = prime * result + ((tiempo == null) ? 0 : tiempo.hashCode());
		return result;
	}
	
	/**
	 * Genera un clon del propio objeto realizando una copia profunda.
	 * @return el objeto Fecha clonado.
	*/
	@Override
	public Fecha clone() {
		return new Fecha(this);
	}
	
} // class
