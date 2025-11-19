package tpfinal.modeloNegocio;
import modeloNegocio.Empresa;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Vehiculo;
import modeloDatos.Auto;
import org.junit.Test;
import java.util.HashMap;
import java.util.Iterator;
import static org.junit.Assert.*;

/**
 * Clase de prueba para los métodos iteradores de la clase Empresa.
 */
public class EmpresaTestIteratorsChoferyVehiculos {

    private Empresa empresa;
    private Chofer choferDePrueba;
    private Vehiculo vehiculoDePrueba;

    public void setupEscenario1() {
        empresa = Empresa.getInstance();
        empresa.setChoferes(new HashMap<String, Chofer>());
        empresa.setVehiculos(new HashMap<String, Vehiculo>());
    }

    public void setupEscenario2() {
        empresa = Empresa.getInstance();

        // Configuración de choferes
        HashMap<String, Chofer> choferes = new HashMap<>();
        choferDePrueba = new ChoferTemporario("25111222", "Carlos Perez");
        choferes.put(choferDePrueba.getDni(), choferDePrueba);
        empresa.setChoferes(choferes);

        // Configuración de vehículos
        HashMap<String, Vehiculo> vehiculos = new HashMap<>();
        vehiculoDePrueba = new Auto("A01B02", 4, false);
        vehiculos.put(vehiculoDePrueba.getPatente(), vehiculoDePrueba);
        empresa.setVehiculos(vehiculos);
    }

    /**
     * Prueba que el iterador de choferes esté vacío cuando no hay choferes registrados.
     * Cubre la Clase de Equivalencia 1.
     */
    @Test
    public void testIteratorChoferesColeccionVaciaDevuelveIteradorVacio() {
        setupEscenario1();
        Iterator<Chofer> iterator = empresa.iteratorChoferes();
        
        assertNotNull("El iterador no debería ser null", iterator);
        assertFalse("El iterador de una colección vacía no debería tener elementos", iterator.hasNext());
    }

    /**
     * Prueba que el iterador de choferes contenga los elementos correctos cuando hay datos.
     * Cubre la Clase de Equivalencia 2.
     */
    @Test
    public void testIteratorChoferesColeccionConDatosDevuelveIteradorConDatos() {
        setupEscenario2();
        Iterator<Chofer> iterator = empresa.iteratorChoferes();
        
        assertNotNull("El iterador no debería ser null", iterator);
        assertTrue("El iterador debería tener al menos un elemento", iterator.hasNext());
        
        Chofer choferObtenido = iterator.next();
        assertEquals("El chofer obtenido no es el esperado", choferDePrueba, choferObtenido);
        assertFalse("El iterador no debería tener más elementos", iterator.hasNext());
    }

    /**
     * Prueba que el iterador de vehículos esté vacío cuando no hay vehículos registrados.
     * Cubre la Clase de Equivalencia 1.
     */
    @Test
    public void testIteratorVehiculosColeccionVaciaDevuelveIteradorVacio() {
        setupEscenario1();
        Iterator<Vehiculo> iterator = empresa.iteratorVehiculos();
        
        assertNotNull("El iterador no debería ser null", iterator);
        assertFalse("El iterador de una colección vacía no debería tener elementos", iterator.hasNext());
    }

    /**
     * Prueba que el iterador de vehículos contenga los elementos correctos cuando hay datos.
     * Cubre la Clase de Equivalencia 2.
     */
    @Test
    public void testIteratorVehiculosColeccionConDatosDevuelveIteradorConDatos() {
        setupEscenario2();
        Iterator<Vehiculo> iterator = empresa.iteratorVehiculos();
        
        assertNotNull("El iterador no debería ser null", iterator);
        assertTrue("El iterador debería tener al menos un elemento", iterator.hasNext());
        
        Vehiculo vehiculoObtenido = iterator.next();
        assertEquals("El vehículo obtenido no es el esperado", vehiculoDePrueba, vehiculoObtenido);
        assertFalse("El iterador no debería tener más elementos", iterator.hasNext());
    }
    
}