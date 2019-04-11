/** 
 * Proyecto: Juego de la vida.
 *  Clase-utilidades de validación de formatos utilizando regex.
 *  @since: prototipo2.0
 *  @source: Formato.java 
 *  @version: 2.0 - 2019/03/20
 *  @author: ajp
 */

package util;

import java.util.regex.Pattern;

public class Formato {

	public static final String PATRON_CORREO1 = "^[\\w-\\+]+(\\.[\\w-\\+]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String PATRON_CORREO2 = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$";

	public static final String PATRON_CONTRASEÑA1 = "[A-ZÑa-zñ0-9%&#_-]{6,18}";
	public static final String PATRON_CONTRASEÑA2 = "(?=^.{6,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
	public static final String PATRON_CONTRASEÑA3 = "^(?=[^\\d_].*?\\d)\\w(\\w|[!@#$%]){6,16}";
	/**
	 * Verifica que una contraseña sea robusta:
	 * al menos una minúscula.
	 * Al menos una mayúscula.
	 * Al menos un número.
	 * Al menos un caracter especial.
	 * Mínimo 6 caracteres. 
	 */
	public static final String PATRON_CONTRASEÑA4 = "(?=.*\\d)(?=.*[A-ZÃ‘])(?=.*[a-zÃ±])(?=.*[#$*-+&!?%]).{6,}";

	public static final String LETRAS_NIF = "TRWAGMYFPDXBNJZSQVHLCKE";
	public static final String PATRON_NIF = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]";
	public static final String PATRON_NIE = "^[X-Z][0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKE]";
	public static final String PATRON_CIF = "^[ABCDEFGHJPQRSUVNW][0-9]{7}[A-J0-9]";
		
	public static final String PATRON_CP = "^([1-9]{2}|[0-9][1-9]|[1-9][0-9])[0-9]{3}$";
	public static final String PATRON_NUMERO_POSTAL = "[\\d]+[\\w]?";

	public static final String PATRON_NOMBRE_PERSONA = "^[A-ZÑ][áéíóúña-z \\w]+";
	public static final String PATRON_APELLIDOS = "^[A-ZÑ][áéíóúña-z]+[ A-ZÑáéíóúñ\\w]*";
	public static final String PATRON_TOPONIMO = "^[A-ZÑ][áéíóúña-z \\w]+";
	public static final String PATRON_NOMBRE_VIA = "^[A-ZÑ][/áéíóúña-z \\w]+";
	
	public static final String PATRON_NOMBRE_MUNDO_JV = "^[A-ZÑ][A-ZÑáéíóúña-z \\d]+";
	public static final String PATRON_NOMBRE_PATRON_JV = "^[A-ZÑ][A-ZÑáéíóúña-z \\d]+";
	
	/**
	 * Verifica que un texto tiene un formato válido.
	 * @param texto - a validar.
	 * @param patron - a utilizar.
	 * @return - true si es correcto.
	 */
	public static boolean validar(String texto,  String patron) {
		return Pattern.compile(patron).matcher(texto).matches();
	}

} // class