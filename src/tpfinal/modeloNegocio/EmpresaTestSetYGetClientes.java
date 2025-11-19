package tpfinal.modeloNegocio;

import static org.junit.Assert.*;
import java.util.HashMap;

import modeloDatos.Cliente;
import modeloNegocio.Empresa;

import org.junit.Before;
import org.junit.Test;

public class EmpresaTestSetYGetClientes {

    private Empresa empresa;

    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
        empresa.setClientes(new HashMap<String, Cliente>()); // Reinicia estado
    }

    /**
     * E1 – Se asigna un HashMap válido con un cliente y se recupera con getClientes()
     * CE cubierta: C1 (Mapa válido)
     */
    @Test
    public void testSetGetClientes_E1() {
        // Datos de prueba
        Cliente cliente = new Cliente("usuario1", "pass", "Nombre Uno");
        HashMap<String, Cliente> mapa = new HashMap<>();
        mapa.put(cliente.getNombreUsuario(), cliente);

        // Ejecución
        empresa.setClientes(mapa);

        // Verificación
        assertEquals("El mapa de clientes no coincide con el asignado", mapa, empresa.getClientes());
    }
}
