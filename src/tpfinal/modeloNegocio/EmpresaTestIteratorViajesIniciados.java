package tpfinal.modeloNegocio;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import modeloNegocio.Empresa;
import modeloDatos.Cliente;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Vehiculo;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Viaje;
import util.Constantes; // Para el pedido

import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet; // Usado para verificar N elementos

public class EmpresaTestIteratorViajesIniciados {
    private Empresa empresa;
    private Cliente c1;
    private Cliente c2;
    private Viaje viaje1;
    private Viaje viaje2;

    @Before
    public void setUp() {
        empresa = Empresa.getInstance();

        c1 = new Cliente("user1", "pass1", "Cliente Uno");
        c2 = new Cliente("user2", "pass2", "Cliente Dos");
        
        Chofer ch1 = new ChoferTemporario("30111222", "Chofer 1");
        Chofer ch2 = new ChoferTemporario("30333444", "Chofer 2");
        
        Vehiculo v1 = new Moto("MOTO1");
        Vehiculo v2 = new Moto("MOTO2");
        
        String zona = Constantes.ZONA_STANDARD;
        
        Pedido p1 = new Pedido(c1, 1, false, false, 5, zona);
        Pedido p2 = new Pedido(c2, 1, false, false, 8, zona);
        
        viaje1 = new Viaje(p1, ch1, v1);
        viaje2 = new Viaje(p2, ch2, v2);
        
        this.setupEscenario1();
    }

    /**
     * Empresa sin viajes iniciados.
     */
    private void setupEscenario1() {
        // Asignamos un nuevo HashMap vacío (sin usar .clear())
        empresa.setViajesIniciados(new HashMap<Cliente, Viaje>());
    }

    /**
     * Empresa con 1 viaje iniciado.
     */
    private void setupEscenario2() {
        HashMap<Cliente, Viaje> map = new HashMap<>();
        map.put(c1, viaje1);
        empresa.setViajesIniciados(map);
    }

    /**
     * Empresa con 2 (N > 1) viajes iniciados.
     */
    private void setupEscenario3() {
        HashMap<Cliente, Viaje> map = new HashMap<>();
        map.put(c1, viaje1);
        map.put(c2, viaje2);
        empresa.setViajesIniciados(map);
    }

    /**
     * Prueba (1 - Escenario 1): Verifica que el iterador
     * de una colección vacía no tiene elementos.
     * Cubre: CE 1.
     */
    @Test
    public void testIteratorViajesIniciadosVacio() {
        Iterator<Viaje> it = empresa.iteratorViajesIniciados();
        
        assertNotNull("El iterador no debe ser null", it);
        assertFalse("El iterador de una lista vacía no debe tener next", it.hasNext());
    }

    /**
     * Prueba (2 - Escenario 2): Verifica que el iterador
     * de una colección con 1 elemento funciona correctamente.
     * Cubre: CE 2.
     */
    @Test
    public void testIteratorViajesIniciadosUnElemento() {
        this.setupEscenario2();
        
        Iterator<Viaje> it = empresa.iteratorViajesIniciados();
        
        assertNotNull("El iterador no debe ser null", it);
        
        assertTrue("El iterador debe tener un elemento", it.hasNext());
        Viaje viajeObtenido = it.next();
        assertSame("El viaje obtenido no es el esperado (viaje1)", viaje1, viajeObtenido);
        
        assertFalse("El iterador no debe tener más elementos", it.hasNext());
    }

    /**
     * Prueba (3 - Escenario 3): Verifica que el iterador
     * de una colección con N elementos los recorre todos.
     * Cubre: CE 3.
     */
    @Test
    public void testIteratorViajesIniciadosNElementos() {
        this.setupEscenario3();
        
        Iterator<Viaje> it = empresa.iteratorViajesIniciados();
        assertNotNull("El iterador no debe ser null", it);
        HashSet<Viaje> encontrados = new HashSet<>();
        
        while (it.hasNext()) {
            encontrados.add(it.next());
        }

        assertEquals("El número de elementos iterados (2) no es el esperado", 2, encontrados.size());
        assertTrue("No se encontró el Viaje 1", encontrados.contains(viaje1));
        assertTrue("No se encontró el Viaje 2", encontrados.contains(viaje2));
        
        assertFalse("El iterador no debe tener más elementos al finalizar", it.hasNext());
    }
}
