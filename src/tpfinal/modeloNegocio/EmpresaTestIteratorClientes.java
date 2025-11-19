package tpfinal.modeloNegocio;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import modeloNegocio.Empresa;
import modeloDatos.Cliente;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
public class EmpresaTestIteratorClientes {
    private Empresa empresa;
    private Cliente c1;
    private Cliente c2;
    private Cliente c3;
    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
        c1 = new Cliente("user1", "pass1", "Cliente Uno");
        c2 = new Cliente("user2", "pass2", "Cliente Dos");
        c3 = new Cliente("user3", "pass3", "Cliente Tres");
        this.setupEscenario1();
    }
    /**
     * Empresa sin clientes.
     */
    private void setupEscenario1() {
        empresa.setClientes(new HashMap<String, Cliente>());
    }

    /**
     * Empresa con 1 cliente.
     */
    private void setupEscenario2() {
        HashMap<String, Cliente> map = new HashMap<>();
        map.put(c1.getNombreUsuario(), c1);
        empresa.setClientes(map);
    }

    /**
     * Empresa con 3 clientes (N > 1).
     */
    private void setupEscenario3() {
        HashMap<String, Cliente> map = new HashMap<>();
        map.put(c1.getNombreUsuario(), c1);
        map.put(c2.getNombreUsuario(), c2);
        map.put(c3.getNombreUsuario(), c3);
        empresa.setClientes(map);
    }
    /**
     * Prueba (1 - Escenario 1): Verifica que el iterador
     * de una colección vacía no tiene elementos.
     * Cubre: CE 1.
     */
    @Test
    public void testIteratorClientesVacio() {
        Iterator<Cliente> it = empresa.iteratorClientes();        
        assertNotNull("El iterador no debe ser null", it);
        assertFalse("El iterador de una lista vacía no debe tener next", it.hasNext());
    }

    /**
     * Prueba (2 - Escenario 2): Verifica que el iterador
     * de una colección con 1 elemento funciona correctamente.
     * Cubre: CE 2.
     */
    @Test
    public void testIteratorClientesUnElemento() {
        this.setupEscenario2();        
        Iterator<Cliente> it = empresa.iteratorClientes();        
        assertNotNull("El iterador no debe ser null", it);       
        assertTrue("El iterador debe tener un elemento", it.hasNext());
        Cliente clienteObtenido = it.next();
        assertSame("El cliente obtenido no es el esperado (c1)", c1, clienteObtenido);        
        assertFalse("El iterador no debe tener más elementos", it.hasNext());
    }

    /**
     * Prueba (3 - Escenario 3): Verifica que el iterador
     * de una colección con N elementos los recorre todos.
     * Cubre: CE 3.
     */
    @Test
    public void testIteratorClientesNElementos() {
        this.setupEscenario3();
        Iterator<Cliente> it = empresa.iteratorClientes();
        assertNotNull("El iterador no debe ser null", it);
        HashSet<Cliente> encontrados = new HashSet<>();       
        while (it.hasNext()) {
            encontrados.add(it.next());
        }
        assertEquals("El número de elementos iterados (3) no es el esperado", 3, encontrados.size());
        assertTrue("No se encontró el Cliente c1", encontrados.contains(c1));
        assertTrue("No se encontró el Cliente c2", encontrados.contains(c2));
        assertTrue("No se encontró el Cliente c3", encontrados.contains(c3));
        assertFalse("El iterador no debe tener más elementos al finalizar", it.hasNext());
    }
}