package tpfinal.Controlador;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class) 

@SuiteClasses({ 
	controladorTestActionPerformed.class, 
	controladorTestCalificaPagar.class, 
	controladorTestGettersYSetters.class, 
	controladorTestLeerEscribir.class, 
	controladorTestLoginLogout.class, 
	controladorTestNuevoChofer.class, 
	controladorTestNuevoPedido.class, 
	controladorTestNuevoVehiculo.class, 
	controladorTestNuevoViaje.class, 
	controladorTestAgregarCliente.class, 
})
public class AllTest {

}
