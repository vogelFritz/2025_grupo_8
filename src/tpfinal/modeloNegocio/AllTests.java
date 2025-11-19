package tpfinal.modeloNegocio;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    EmpresaTest.class,
    EmpresaTestCrearViaje.class,
    EmpresaTestGettersVarios1.class,
    EmpresaTestGetViajesDeCliente.class,
    EmpresaTestGetViajesIniciados.class,
    EmpresaTestGetViajesTerminados.class,
    EmpresaTestIsAdmin.class,
    EmpresaTestIteratorClientes.class,
    EmpresaTestIteratorsChoferyVehiculos.class,
    EmpresaTestIteratorViajesIniciados.class,
    EmpresaTestLogout.class,
    EmpresaTestPagarYFinalizarViaje.class,
    EmpresaTestSetViajesIniciados.class,
    EmpresaTestSetViajesTerminados.class,
    EmpresaTestSetYGetClientes.class,
    EmpresaTestValidarPedido.class,
    GetPedidosTest.class,
    GetVehiculosDesocupadosTest.class,
    GetVehiculosTest.class,
    IteratorPedidosTest.class,
    IteratorViajesTerminadosTest.class,
    LoginTest.class,
    VehiculosOrdenadosTest.class
})
public class AllTests {
    // Clase vacía, solo sirve para ejecutar la suite
}