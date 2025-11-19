package tpfinal.persistencia; // Puedes ponerla en el paquete que prefieras

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        TestPersistenciaBIN.class, // Tus tests de persistencia binaria
        TestPersistenciaEmpresaDTO.class, // Tus tests del DTO
        TestUtilPersistencia.class // Tus tests de la utilidad de conversión
})
public class AllTests {
    // Esta clase debe quedar vacía.
    // Solo sirve para portar las anotaciones que le dicen a JUnit qué ejecutar.
}