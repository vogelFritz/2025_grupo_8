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
import util.Constantes; // Importamos las constantes [cite: 17]

import java.util.HashMap;

public class EmpresaTestGetViajesIniciados {
    private Empresa empresa;
    private Cliente c1;
    private Cliente c2;
    private Viaje viaje1;
    private Viaje viaje2;
    private HashMap<Cliente, Viaje> mapVacio;
    private HashMap<Cliente, Viaje> mapUno;
    private HashMap<Cliente, Viaje> mapN;
    
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
        mapVacio = new HashMap<Cliente, Viaje>();
        mapUno = new HashMap<Cliente, Viaje>();
        mapUno.put(c1, viaje1);        
        mapN = new HashMap<Cliente, Viaje>();
        mapN.put(c1, viaje1);
        mapN.put(c2, viaje2);
        this.setupEscenario1();
    }
    /**
     * Empresa sin viajes iniciados.
     */
    private void setupEscenario1() {
        empresa.setViajesIniciados(mapVacio); // 
    }
    /**
     * Empresa con 1 viaje iniciado.
     */
    private void setupEscenario2() {
        empresa.setViajesIniciados(mapUno);
    }
    /**
     * Empresa con 2 (N > 1) viajes iniciados.
     */
    private void setupEscenario3() {
        empresa.setViajesIniciados(mapN);
    }
    /**
     * Prueba (1 - Escenario 1): Verifica que el getter retorna
     * un mapa vacío cuando el estado interno está vacío.
     * Cubre: CE 1.
     */
    @Test
    public void testGetViajesIniciadosVacio() {
        HashMap<Cliente, Viaje> map = empresa.getViajesIniciados();
        assertNotNull("El HashMap retornado no debe ser null", map);
        assertTrue("El HashMap debe estar vacío", map.isEmpty());
        assertSame("El getter no retornó la instancia esperada (mapVacio)", mapVacio, map);
    }

    /**
     * Prueba (2 - Escenario 2): Verifica que el getter retorna
     * un mapa con 1 elemento.
     * Cubre: CE 2.
     */
    @Test
    public void testGetViajesIniciadosUnElemento() {
        this.setupEscenario2();
        HashMap<Cliente, Viaje> map = empresa.getViajesIniciados(); 
        assertNotNull("El HashMap retornado no debe ser null", map);
        assertEquals("El HashMap debe tener 1 elemento", 1, map.size());
        assertTrue("El HashMap debe contener la clave (c1)", map.containsKey(c1));
        assertSame("El valor del mapa no es el viaje esperado (viaje1)", viaje1, map.get(c1));
        assertSame("El getter no retornó la instancia esperada (mapUno)", mapUno, map);
    }

    /**
     * Prueba (3 - Escenario 3): Verifica que el getter retorna
     * un mapa con N elementos.
     * Cubre: CE 3.
     */
    @Test
    public void testGetViajesIniciadosNElementos() {
        this.setupEscenario3();
        HashMap<Cliente, Viaje> map = empresa.getViajesIniciados();
        assertNotNull("El HashMap retornado no debe ser null", map);
        assertEquals("El HashMap debe tener 2 elementos", 2, map.size());
        assertTrue("El HashMap debe contener la clave (c1)", map.containsKey(c1));
        assertSame("El valor para c1 no es el viaje esperado (viaje1)", viaje1, map.get(c1));
        assertTrue("El HashMap debe contener la clave (c2)", map.containsKey(c2));
        assertSame("El valor para c2 no es el viaje esperado (viaje2)", viaje2, map.get(c2));
        assertSame("El getter no retornó la instancia esperada (mapN)", mapN, map);
    }
}
