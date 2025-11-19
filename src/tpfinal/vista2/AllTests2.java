package tpfinal.vista2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tpfinal.vista.RegisterTest2;

/**
 * Suite de pruebas para ejecutar todos los tests de la vista en conjunto.
 * Verifica el flujo completo de la aplicación: Login -> Registro -> Cliente -> Administrador.
 */
@RunWith(Suite.class)
@SuiteClasses({
    LoginTest2.class,
    RegisterTest2.class,
    PanelClienteTest2.class,
    PanelAdministradorTest2.class
})
public class AllTests2 {
    // Esta clase debe permanecer vacía.
    // Funciona como un contenedor para la anotación @SuiteClasses.
}