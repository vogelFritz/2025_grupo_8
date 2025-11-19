package tpfinal.modeloDatos;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    AdministradorTest.class,
    ChoferTemporarioTest.class,
    MotoTest.class,
    PedidoTest.class,
    ViajeTest.class,
    AutoTest.class,
    ClienteTest.class,
    CombiTest.class, 
})
public class AllTests {
    // Clase vacía para ejecutar la suite
}