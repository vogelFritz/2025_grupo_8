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

import java.util.HashMap;

public class EmpresaTestGetViajesDeCliente {
    private Empresa empresa;
    private Cliente cliente1;
    private Cliente cliente2;
    private Viaje viaje1;

    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
        cliente1 = new Cliente("userViaje1", "pass1", "Cliente Con Viaje");
        cliente2 = new Cliente("userViaje2", "pass2", "Cliente Sin Viaje");
        Chofer chofer = new ChoferTemporario("30555666", "Chofer Viaje");
        Vehiculo vehiculo = new Moto("MOTOVIAJE");
        Pedido pedido = new Pedido(cliente1, 1, false, false, 5, Constantes.ZONA_STANDARD);
        viaje1 = new Viaje(pedido, chofer, vehiculo);
        this.setupEscenario1();
    }
    /**
     * Empresa sin viajes iniciados.
     */
    private void setupEscenario1() {
        empresa.setViajesIniciados(new HashMap<Cliente, Viaje>());
    }

    /**
     * Empresa con 1 viaje iniciado (para cliente1).
     */
    private void setupEscenario2() {
        HashMap<Cliente, Viaje> map = new HashMap<>();
        map.put(cliente1, viaje1);
        empresa.setViajesIniciados(map);
    }
    /**
     * Prueba (1 - Escenario 1): Verifica que retorna null si la
     * colección de viajes iniciados está vacía.
     * Cubre: CE 2, 3.
     */
    @Test
    public void testGetViajeDeClienteMapaVacio() {
        Viaje resultado = empresa.getViajeDeCliente(cliente1);
        assertNull("Debe retornar null si la lista de viajes iniciados está vacía", resultado);
    }

    /**
     * Prueba (2 - Escenario 2): Verifica que retorna null si se
     * pide el viaje de un cliente que no tiene uno (pero otros sí).
     * Cubre: CE 2, 4.
     */
    @Test
    public void testGetViajeDeClienteNoEncontrado() {
        this.setupEscenario2();
        Viaje resultado = empresa.getViajeDeCliente(cliente2);
        assertNull("Debe retornar null si el cliente no tiene un viaje iniciado", resultado);
    }

    /**
     * Prueba (3 - Escenario 2): Verifica que retorna el Viaje
     * correcto para un cliente que sí tiene uno.
     * Cubre: CE 2, 5.
     */
    @Test
    public void testGetViajeDeClienteExitoso() {
        this.setupEscenario2();
        Viaje resultado = empresa.getViajeDeCliente(cliente1);
        assertNotNull("No debe retornar null si el cliente tiene un viaje", resultado);
        assertSame("El viaje retornado no es el correcto (viaje1)", viaje1, resultado);
    }
}
