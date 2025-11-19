package tpfinal.modeloNegocio;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import modeloNegocio.Empresa;
import modeloDatos.Cliente;
import modeloDatos.Vehiculo;
import modeloDatos.Moto;
import modeloDatos.Auto;
import modeloDatos.Combi;
import modeloDatos.Pedido;
import util.Constantes;
import java.util.HashMap;

public class EmpresaTestValidarPedido {
    private Empresa empresa;
    private Cliente clientePrueba;
    private Vehiculo moto;
    private Vehiculo auto;
    private Vehiculo combi;
    private Pedido pedido_moto;
    private Pedido pedido_auto;
    private Pedido pedido_combi;

    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
        clientePrueba = new Cliente("clienteTest", "pass1", "Cliente de Prueba");
        moto = new Moto("MOTO001");
        auto = new Auto("AUTO001", 4, true);
        combi = new Combi("COMBI001", 8, false);
        String zona = Constantes.ZONA_STANDARD;
        pedido_moto = new Pedido(clientePrueba, 1, false, false, 10, zona);
        pedido_auto = new Pedido(clientePrueba, 3, false, true, 10, zona);
        pedido_combi = new Pedido(clientePrueba, 6, false, false, 10, zona);

        this.setupEscenario1();
    }

    /**
     * Empresa sin vehículos registrados.
     */
    private void setupEscenario1() {
        // Asignamos un nuevo HashMap vacío, sin usar .clear()
        empresa.setVehiculos(new HashMap<String, Vehiculo>());
    }

    /**
     * Empresa solo con una Moto.
     */
    private void setupEscenario2() {
        HashMap<String, Vehiculo> vehiculosMap = new HashMap<String, Vehiculo>();
        vehiculosMap.put(moto.getPatente(), moto);
        empresa.setVehiculos(vehiculosMap);
    }

    /**
     * Empresa con todos los tipos de vehículos.
     */
    private void setupEscenario3() {
        HashMap<String, Vehiculo> vehiculosMap = new HashMap<String, Vehiculo>();
        vehiculosMap.put(moto.getPatente(), moto);
        vehiculosMap.put(auto.getPatente(), auto);
        vehiculosMap.put(combi.getPatente(), combi);
        empresa.setVehiculos(vehiculosMap);
    }

    /**
     * Prueba (1 - Escenario 1): Valida un pedido cuando la empresa no
     * tiene vehículos.
     * Cubre: CE 2, 3.
     * Espera: false.
     */
    @Test
    public void testValidarPedidoEmpresaVacia() {
        boolean resultado = empresa.validarPedido(pedido_auto);
        assertFalse("validarPedido debe retornar false si no hay vehículos registrados", resultado);
    }

    /**
     * Prueba (2 - Escenario 2): Valida un pedido (de Auto) cuando la
     * empresa solo tiene Motos.
     * Cubre: CE 2, 4.
     * Espera: false.
     */
    @Test
    public void testValidarPedidoNoSatisfecho() {
        this.setupEscenario2();
        boolean resultado = empresa.validarPedido(pedido_auto);
        assertFalse("validarPedido debe retornar false si ningún vehículo satisface el pedido", resultado);
    }

    /**
     * Prueba (3 - Escenario 2): Valida un pedido (de Moto) cuando la
     * empresa solo tiene Motos.
     * Cubre: CE 2, 5.
     * Espera: true.
     */
    @Test
    public void testValidarPedidoSatisfechoMoto() {
        this.setupEscenario2();
        boolean resultado = empresa.validarPedido(pedido_moto);
        assertTrue("validarPedido debe retornar true si la Moto puede satisfacer el pedido", resultado);
    }

    /**
     * Prueba (4 - Escenario 3): Valida un pedido (de Auto) cuando la
     * empresa tiene todos los vehículos.
     * Cubre: CE 2, 5.
     * Espera: true.
     */
    @Test
    public void testValidarPedidoSatisfechoAuto() {
        this.setupEscenario3();
        boolean resultado = empresa.validarPedido(pedido_auto);
        assertTrue("validarPedido debe retornar true si el Auto puede satisfacer el pedido", resultado);
    }

    /**
     * Prueba (5 - Escenario 3): Valida un pedido (de Combi) cuando la
     * empresa tiene todos los vehículos.
     * Cubre: CE 2, 5.
     * Espera: true.
     */
    @Test
    public void testValidarPedidoSatisfechoCombi() {
        this.setupEscenario3();
        boolean resultado = empresa.validarPedido(pedido_combi);
        assertTrue("validarPedido debe retornar true si la Combi puede satisfacer el pedido", resultado);
    }

}
