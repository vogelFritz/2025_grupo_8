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
import util.Mensajes;

import java.util.HashMap;
import java.util.ArrayList;

import excepciones.*;

public class EmpresaTestPagarYFinalizarViaje {
    private Empresa empresa;
    private Cliente clienteLogueado;
    private Viaje viajeIniciado;

    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
        clienteLogueado = new Cliente("clientePago", "pass123", "Cliente Pagador");
        Chofer chofer = new ChoferTemporario("30123456", "Chofer Prueba");
        Vehiculo vehiculo = new Moto("MOTO123");
        Pedido pedido = new Pedido(clienteLogueado, 1, false, false, 5, util.Constantes.ZONA_STANDARD);
        viajeIniciado = new Viaje(pedido, chofer, vehiculo);
        this.setupEscenario1();
    }
    /**
     * Cliente logueado , sin viaje en viajesIniciados.
     */
    private void setupEscenario1() {
        empresa.setUsuarioLogeado(clienteLogueado);
        empresa.setViajesIniciados(new HashMap<Cliente, Viaje>());
        empresa.setViajesTerminados(new ArrayList<Viaje>());
    }

    /**
     * Cliente logueado , con un viaje en viajesIniciados.
     */
    private void setupEscenario2() {
        empresa.setUsuarioLogeado(clienteLogueado);
        HashMap<Cliente, Viaje> viajesIniciadosMap = new HashMap<Cliente, Viaje>();
        viajesIniciadosMap.put(clienteLogueado, viajeIniciado);
        empresa.setViajesIniciados(viajesIniciadosMap);
        empresa.setViajesTerminados(new ArrayList<Viaje>());
        assertFalse("Pre-condición Escenario 2: El viaje no debe estar finalizado", viajeIniciado.isFinalizado());
    }

    /**
     * Prueba (1 - Escenario 1): Intenta pagar sin un viaje pendiente.
     * Cubre: CE 1, 4, 7.
     * Espera: ClienteSinViajePendienteException.
     */
    @Test
    public void testPagarYFinalizarViajeSinViajePendiente() {
        int calificacionValida = 3;

        try {
            empresa.pagarYFinalizarViaje(calificacionValida);
            fail("Debería haberse lanzado ClienteSinViajePendienteException");
        } catch (ClienteSinViajePendienteException e) {
            assertEquals("El mensaje de excepción no coincide", Mensajes.CLIENTE_SIN_VIAJE_PENDIENTE.getValor(), e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción incorrecta: " + e.getClass().getName());
        }
    }

    /**
     * Prueba (2 - Escenario 2): Paga y finaliza un viaje existente.
     * Cubre: CE 1, 4, 6.
     * Espera: El viaje se mueve de viajesIniciados a viajesTerminados,
     * y se actualiza su estado.
     */
    @Test
    public void testPagarYFinalizarViajeExitoso() {
        this.setupEscenario2();
        int calificacionValida = 5;
        try {
        	empresa.pagarYFinalizarViaje(calificacionValida);
            assertEquals("La calificación del viaje no se actualizó", calificacionValida, viajeIniciado.getCalificacion());
            assertTrue("El viaje no se marcó como finalizado", viajeIniciado.isFinalizado());
            assertTrue("La lista de viajes iniciados no está vacía", empresa.getViajesIniciados().isEmpty());             
            assertEquals("La lista de viajes terminados no tiene 1 elemento", 1, empresa.getViajesTerminados().size());  
            assertSame("El viaje no se agregó a viajesTerminados", viajeIniciado, empresa.getViajesTerminados().get(0));
        } catch (ClienteSinViajePendienteException e) {
            fail("No debería lanzarse ClienteSinViajePendienteException: " + e.getMessage());
        }
    }
}
