/** 
 * Proyecto: Juego de la vida.
 * Template Method para los DAOs que requieren el método indexSort() para mantener ordenados los datos.
 * @since: prototipo2.0
 * @source: DAOIndexSort.java  
 * @version: 2.0 - 2019.03.24
 * @author: ajp
 */

package accesoDatos.memoria;

import java.util.List;

import modelo.Identificable;

public abstract class DAOIndexSort {

	/**
	 *  Obtiene por búsqueda binaria, la posición que ocupa, o ocuparía,  un objeto en la estructura.
	 *	@param Id - id del objeto a buscar.
	 * 	@param datos - Estructura de datos con elementos identificables (poseen un id utilizado para la busqueda y ordenación).
	 *	@return - la posición, en base 1, que ocupa un objeto o la que ocuparía (negativo).
	 */
	protected int indexSort(String id, List<Identificable> datos) {
		assert id != null;
		assert datos != null;
		int comparacion;
		int inicio = 0;
		int fin = datos.size() - 1;
		int medio = 0;
		while (inicio <= fin) {
			medio = (inicio + fin) / 2;			// Calcula posición central.
			// Obtiene > 0 si id va después que medio.
			comparacion = id.compareTo(datos.get(medio).getId());
			if (comparacion == 0) {			
				return medio + 1;   			// Posción ocupada, base 1	  
			}		
			if (comparacion > 0) {
				inicio = medio + 1;
			}			
			else {
				fin = medio - 1;
			}
		}	
		return -(inicio + 1);					// Posición que ocuparía -negativo- base 1
	}	

} //class
