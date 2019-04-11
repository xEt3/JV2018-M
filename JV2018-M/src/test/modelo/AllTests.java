package modelo;
/** 
 * Proyecto: Juego de la vida.
 *  Prueba Junit5 del paquete modelo según el modelo1.1
 *  @since: prototipo1.0
 *  @source: AllTest.java 
 *  @version: 1.1 - 2019/01/25
 *  @author: ajp
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	UsuarioTest.class,
	SesionUsuarioTest.class,
	SimulacionTest.class
})

public class AllTests {

}
