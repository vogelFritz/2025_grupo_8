package tpfinal.persistencia; // Puedes ponerla en el paquete que prefieras

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        TestPersistenciaBIN.class, 
        TestPersistenciaEmpresaDTO.class, 
        TestUtilPersistencia.class 
})
public class AllTests {
}