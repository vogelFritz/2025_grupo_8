package tpfinal.modeloNegocio;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;

import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ClienteConViajePendienteException;
import excepciones.PedidoInexistenteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import util.Constantes;

public class EmpresaTestCrearViaje {

    private Empresa empresa;

    // Entidades para Escenario 1
    private Cliente cliente1;
    private Pedido pedido1;
    private Chofer chofer1;
    private Vehiculo vehiculo1;

    private Cliente cliente2;
    private Chofer chofer2;
    private Vehiculo vehiculo2;
    private Viaje viaje1; // Viaje en curso del cliente2

    private Vehiculo vehiculo3; // Vehiculo invalido para pedido1

    @Before
    public void setUp() {
        // Obtenemos la instancia Singleton
        empresa = Empresa.getInstance();

        // --- Inicialización de objetos para el escenario ---
        cliente1 = new Cliente("user1", "pass1", "Cliente Uno");
        cliente2 = new Cliente("user2", "pass2", "Cliente Dos");

        chofer1 = new ChoferPermanente("20000001", "Chofer Uno", 2020, 1);
        chofer2 = new ChoferPermanente("20000002", "Chofer Dos", 2018, 2);

        vehiculo1 = new Auto("AAA111", 4, true); // Auto válido para pedido1
        vehiculo2 = new Combi("BBB222", 8, true); // Ocupado
        vehiculo3 = new Moto("CCC333"); // Inválido para pedido1

        // Pedido que requiere 3 pasajeros, baul y mascota
        pedido1 = new Pedido(cliente1, 3, true, true, 10, Constantes.ZONA_STANDARD);

        // Viaje en curso para el cliente2
        Pedido pedidoPrevioCliente2 = new Pedido(cliente2, 5, false, true, 20, Constantes.ZONA_PELIGROSA);
        viaje1 = new Viaje(pedidoPrevioCliente2, chofer2, vehiculo2);

        // Estado interno de la Empresa
        // Se crean nuevas colecciones para garantizar un estado limpio
        HashMap<String, Cliente> clientes = new HashMap<>();
        clientes.put(cliente1.getNombreUsuario(), cliente1);
        clientes.put(cliente2.getNombreUsuario(), cliente2);
        empresa.setClientes(clientes);

        HashMap<String, Chofer> choferes = new HashMap<>();
        choferes.put(chofer1.getDni(), chofer1);
        choferes.put(chofer2.getDni(), chofer2);
        empresa.setChoferes(choferes);

        HashMap<String, Vehiculo> vehiculos = new HashMap<>();
        vehiculos.put(vehiculo1.getPatente(), vehiculo1);
        vehiculos.put(vehiculo2.getPatente(), vehiculo2);
        vehiculos.put(vehiculo3.getPatente(), vehiculo3);
        empresa.setVehiculos(vehiculos);
        
        ArrayList<Chofer> choferesDesocupados = new ArrayList<>();
        choferesDesocupados.add(chofer1);
        empresa.setChoferesDesocupados(choferesDesocupados);

        ArrayList<Vehiculo> vehiculosDesocupados = new ArrayList<>();
        vehiculosDesocupados.add(vehiculo1);
        vehiculosDesocupados.add(vehiculo3); // Moto también está desocupada
        empresa.setVehiculosDesocupados(vehiculosDesocupados);

        HashMap<Cliente, Pedido> pedidos = new HashMap<>();
        pedidos.put(cliente1, pedido1);
        empresa.setPedidos(pedidos);

        HashMap<Cliente, Viaje> viajesIniciados = new HashMap<>();
        viajesIniciados.put(cliente2, viaje1);
        empresa.setViajesIniciados(viajesIniciados);
        
        empresa.setViajesTerminados(new ArrayList<Viaje>());
    }

    /**
     * Prueba el caso de éxito de la creación de un viaje.
     * CE que abarca: 1
     */
    @Test
    public void testCrearViaje_Exito_CreaViajeCorrectamente() {
        // Pre-condiciones
        assertEquals("Debería haber 1 pedido pendiente.", 1, empresa.getPedidos().size());
        assertTrue("El chofer1 debería estar desocupado.", empresa.getChoferesDesocupados().contains(chofer1));
        assertTrue("El vehiculo1 debería estar desocupado.", empresa.getVehiculosDesocupados().contains(vehiculo1));
        assertNull("El cliente1 no debería tener un viaje iniciado.", empresa.getViajeDeCliente(cliente1));

        try {
            empresa.crearViaje(pedido1, chofer1, vehiculo1);
        } catch (Exception e) {
            fail("No debería lanzarse ninguna excepción en el caso de éxito: " + e.getMessage());
        }

        // Post-condiciones
        assertEquals("No deberían quedar pedidos pendientes.", 0, empresa.getPedidos().size());
        assertNotNull("El cliente1 debería tener un viaje iniciado.", empresa.getViajeDeCliente(cliente1));
        assertFalse("El chofer1 ya no debería estar desocupado.", empresa.getChoferesDesocupados().contains(chofer1));
        assertFalse("El vehiculo1 ya no debería estar desocupado.", empresa.getVehiculosDesocupados().contains(vehiculo1));
        assertEquals("Debería haber 2 viajes iniciados en total.", 2, empresa.getViajesIniciados().size());
    }

    /**
     * Prueba la excepción cuando el pedido no está registrado.
     * CE que abarca: 2
     */
    @Test
    public void testCrearViaje_Falla_PedidoInexistente() {
        Pedido pedidoInexistente = new Pedido(cliente1, 1, false, false, 5, Constantes.ZONA_SIN_ASFALTAR);
        try {
            empresa.crearViaje(pedidoInexistente, chofer1, vehiculo1);
            fail("Debería haberse lanzado PedidoInexistenteException.");
        } catch (PedidoInexistenteException e) {
            // Éxito, se esperaba esta excepción
        } catch (Exception e) {
            fail("Se lanzó una excepción incorrecta: " + e.getClass().getName());
        }
    }

    /**
     * Prueba la excepción cuando el chofer no está disponible.
     * CE que abarca: 3
     */
    @Test
    public void testCrearViaje_Falla_ChoferNoDisponible() {
        try {
            empresa.crearViaje(pedido1, chofer2, vehiculo1); // chofer2 está ocupado
            fail("Debería haberse lanzado ChoferNoDisponibleException.");
        } catch (ChoferNoDisponibleException e) {
            // Éxito
        } catch (Exception e) {
            fail("Se lanzó una excepción incorrecta: " + e.getClass().getName());
        }
    }

    /**
     * Prueba la excepción cuando el vehículo no está disponible.
     * CE que abarca: 4
     */
    @Test
    public void testCrearViaje_Falla_VehiculoNoDisponible() {
        try {
            empresa.crearViaje(pedido1, chofer1, vehiculo2); // vehiculo2 está ocupado
            fail("Debería haberse lanzado VehiculoNoDisponibleException.");
        } catch (VehiculoNoDisponibleException e) {
            // Éxito
        } catch (Exception e) {
            fail("Se lanzó una excepción incorrecta: " + e.getClass().getName());
        }
    }

    /**
     * Prueba la excepción cuando el vehículo no es válido para el pedido.
     * CE que abarca: 5
     */
    @Test
    public void testCrearViaje_Falla_VehiculoNoValido() {
        try {
            empresa.crearViaje(pedido1, chofer1, vehiculo3); // vehiculo3 es Moto, pedido1 necesita baúl y 3 plazas
            fail("Debería haberse lanzado VehiculoNoValidoException.");
        } catch (VehiculoNoValidoException e) {
            // Éxito
        } catch (Exception e) {
            fail("Se lanzó una excepción incorrecta: " + e.getClass().getName());
        }
    }

    /**
     * Prueba la excepción cuando el cliente ya tiene un viaje pendiente.
     * CE que abarca: 6
     */
    @Test
    public void testCrearViaje_Falla_ClienteConViajePendiente() {
        // Creamos un nuevo pedido para el cliente2, que ya tiene un viaje en curso
        Pedido pedido2_cliente2 = new Pedido(cliente2, 1, false, false, 2, Constantes.ZONA_STANDARD);
        // Lo agregamos a la empresa para que la validación de pedido existente pase
        empresa.getPedidos().put(cliente2, pedido2_cliente2);
        
        try {
            empresa.crearViaje(pedido2_cliente2, chofer1, vehiculo1);
            fail("Debería haberse lanzado ClienteConViajePendienteException.");
        } catch (ClienteConViajePendienteException e) {
            // Éxito
        } catch (Exception e) {
            fail("Se lanzó una excepción incorrecta: " + e.getClass().getName());
        }
    }
    
}



