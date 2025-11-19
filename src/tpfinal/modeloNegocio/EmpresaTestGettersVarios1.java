package tpfinal.modeloNegocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;

import org.junit.Before;
import org.junit.Test;

public class EmpresaTestGettersVarios1 {

    private Empresa empresa;
    private Chofer chofer1;
    private Cliente cliente1;

    /**
     * Prepara un estado base limpio para cada prueba, reiniciando
     * las colecciones de la instancia Singleton para garantizar aislamiento.
     */
    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
        
        // Reinicia el estado de la empresa con colecciones nuevas
        empresa.setChoferes(new HashMap<String, Chofer>());
        empresa.setViajesTerminados(new ArrayList<Viaje>());
        empresa.setClientes(new HashMap<String, Cliente>());
    }

    /**
     * Configura una empresa con un único chofer registrado.
     */
    public void setupEscenarioGetHistorialViajeChofer() {
        chofer1 = new ChoferPermanente("20000001", "Chofer Uno", 2020, 0);
        empresa.getChoferes().put(chofer1.getDni(), chofer1);
    }
    
    /**
     * Configura una empresa con un único cliente registrado.
     */
    public void setupEscenarioGetHistorialViajeCliente() {
        cliente1 = new Cliente("cliente1", "pass", "Cliente Uno");
        empresa.getClientes().put(cliente1.getNombreUsuario(), cliente1);
    }

    /**
     * Escenario 1 para getTotalSalarios: Empresa con varios choferes.
     */
    public void setupEscenarioConChoferes() {
        Chofer.setSueldoBasico(1000.0); // Establece un sueldo base conocido
        Chofer chPerm = new ChoferPermanente("30000001", "Chofer Perm", 2020, 2); // Antiguedad y hijos suman al bruto
        Chofer chTemp = new ChoferTemporario("40000001", "Chofer Temp"); // Solo sueldo básico
        empresa.getChoferes().put(chPerm.getDni(), chPerm);
        empresa.getChoferes().put(chTemp.getDni(), chTemp);
    }

    /**
     * Prueba que getInstance() devuelve la misma instancia, verificando que un cambio
     * de estado a través de una referencia se refleja en una segunda referencia.
     */
    @Test
    public void testGetInstance_SingletonConCambioDeEstado_DevuelveMismaInstancia() {
        Empresa instancia1 = Empresa.getInstance();
        Chofer nuevoChofer = new ChoferPermanente("99999999", "Chofer Singleton", 2025, 0);
        try {
            instancia1.agregarChofer(nuevoChofer);
        } catch (Exception e) {
            // No debería fallar
        }
        Empresa instancia2 = Empresa.getInstance();
        assertEquals("El tamaño de la lista de choferes debería ser 1.", 1, instancia2.getChoferes().size());
        assertTrue("La segunda instancia debe contener el chofer agregado por la primera.", instancia2.getChoferes().containsKey("99999999"));
        assertSame("Ambas referencias deben apuntar al mismo objeto.", instancia1, instancia2);
    }
    
    /**
     * Prueba que el total de salarios es correcto cuando hay múltiples choferes.
     * Cubre CE: 1 (sistema con choferes).
     */
    @Test
    public void testGetTotalSalarios_ConChoferes_DevuelveSumaCorrecta() {
        setupEscenarioConChoferes();
        Chofer chPerm = empresa.getChoferes().get("30000001");
        Chofer chTemp = empresa.getChoferes().get("40000001");

        // Calcula el total esperado sumando los salarios netos individuales
        double totalEsperado = chPerm.getSueldoNeto() + chTemp.getSueldoNeto();
        
        assertEquals("La suma de salarios debe ser correcta.", totalEsperado, empresa.getTotalSalarios(), 0.001);
    }
    
    /**
     * Prueba que el total de salarios es cero cuando no hay choferes.
     * Cubre CE: 2 (sistema sin choferes).
     */
    @Test
    public void testGetTotalSalarios_SinChoferes_DevuelveCero() {
        // No se necesita un setup, el estado inicial de setUp() es sin choferes.
        assertEquals("El total de salarios debe ser 0.0 si no hay choferes.", 0.0, empresa.getTotalSalarios(), 0.001);
    }

    @Test
    public void testGetHistorialViajeCliente_ConClienteValido_DevuelveLista() {
    	setupEscenarioGetHistorialViajeCliente();
    	ArrayList<Viaje> historial = empresa.getHistorialViajeCliente(cliente1);
        assertNotNull("El historial devuelto no debería ser nulo para un cliente válido.", historial);
    }

    @Test
    public void testGetHistorialViajeChofer_ConChoferValido_DevuelveLista() {
        setupEscenarioGetHistorialViajeChofer();
        ArrayList<Viaje> historial = empresa.getHistorialViajeChofer(chofer1);
        assertNotNull("El historial devuelto no debería ser nulo para un chofer válido.", historial);
    }
}