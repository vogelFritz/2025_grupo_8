package tpfinal.vista;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite de pruebas para ejecutar todos los tests de la vista en conjunto.
 * Verifica el flujo completo de la aplicación: Login -> Registro -> Cliente -> Administrador.
 */
@RunWith(Suite.class)
@SuiteClasses({
    LoginTest.class,
    RegisterTest.class,
    PanelClienteTest.class,
    PanelAdministradorTest.class
})
public class AllTests {
    // Esta clase debe permanecer vacía.
    // Funciona como un contenedor para la anotación @SuiteClasses.
}