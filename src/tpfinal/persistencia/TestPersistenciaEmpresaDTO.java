package tpfinal.persistencia;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import persistencia.EmpresaDTO;
import util.Constantes;

public class TestPersistenciaEmpresaDTO {

    @Test
    public void testConstructorDTO() {
        EmpresaDTO dto = new EmpresaDTO();

        assertNotNull(dto.getChoferes());
        assertNotNull(dto.getChoferesDesocupados());
        assertNotNull(dto.getClientes());
        assertNotNull(dto.getPedidos());
        assertNotNull(dto.getVehiculos());
        assertNotNull(dto.getVehiculosDesocupados());
        assertNotNull(dto.getViajesIniciados());
        assertNotNull(dto.getViajesTerminados());
        assertNull(dto.getUsuarioLogeado());

        assertTrue(dto.getChoferes().isEmpty());
        assertTrue(dto.getChoferesDesocupados().isEmpty());
        assertTrue(dto.getClientes().isEmpty());
        assertTrue(dto.getPedidos().isEmpty());
        assertTrue(dto.getVehiculos().isEmpty());
        assertTrue(dto.getVehiculosDesocupados().isEmpty());
        assertTrue(dto.getViajesIniciados().isEmpty());
        assertTrue(dto.getViajesTerminados().isEmpty());
    }

    @Test
    public void testSetGetChoferes() {
        EmpresaDTO dto = new EmpresaDTO();

        HashMap<String, Chofer> choferes = new HashMap<>();
        choferes.put("C1", new ChoferPermanente("C1", "1", 2000, 1));

        dto.setChoferes(choferes);

        assertEquals(choferes, dto.getChoferes());
    }

    @Test
    public void testSetGetChoferesDesocupados() {
        EmpresaDTO dto = new EmpresaDTO();

        ArrayList<Chofer> desocupados = new ArrayList<>();
        desocupados.add(new ChoferPermanente("C1", "1", 2000, 1));

        dto.setChoferesDesocupados(desocupados);

        assertEquals(desocupados, dto.getChoferesDesocupados());
    }

    @Test
    public void testSetGetClientes() {
        EmpresaDTO dto = new EmpresaDTO();

        Cliente cliente = new Cliente("CL1", "123", "Lucas");
        HashMap<String, Cliente> clientes = new HashMap<>();
        clientes.put("CL1", cliente);

        dto.setClientes(clientes);

        assertEquals(clientes, dto.getClientes());
    }

    @Test
    public void testSetGetPedidos() {
        EmpresaDTO dto = new EmpresaDTO();

        Cliente cli = new Cliente("CL1", "123", "Lucas");
        Pedido pedido = new Pedido(cli, 1, true, true, 1, Constantes.ZONA_STANDARD);

        HashMap<Cliente, Pedido> pedidos = new HashMap<>();
        pedidos.put(cli, pedido);

        dto.setPedidos(pedidos);

        assertEquals(pedidos, dto.getPedidos());
    }

    @Test
    public void testSetGetUsuarioLogueado() {
        EmpresaDTO dto = new EmpresaDTO();

        Cliente cli = new Cliente("CL1", "123", "Lucas");
        dto.setUsuarioLogeado(cli);

        assertEquals(cli, dto.getUsuarioLogeado());
    }

    @Test
    public void testSetGetVehiculos() {
        EmpresaDTO dto = new EmpresaDTO();

        Vehiculo v = new Auto("AAA1", 2, true);
        HashMap<String, Vehiculo> vehiculos = new HashMap<>();
        vehiculos.put("AAA1", v);

        dto.setVehiculos(vehiculos);

        assertEquals(vehiculos, dto.getVehiculos());
    }

    @Test
    public void testSetGetVehiculosDesocupados() {
        EmpresaDTO dto = new EmpresaDTO();

        Vehiculo v = new Auto("AAA1", 2, true);
        ArrayList<Vehiculo> desocupados = new ArrayList<>();
        desocupados.add(v);

        dto.setVehiculosDesocupados(desocupados);

        assertEquals(desocupados, dto.getVehiculosDesocupados());
    }

    @Test
    public void testSetGetViajesIniciados() {
        EmpresaDTO dto = new EmpresaDTO();

        ChoferPermanente chofer = new ChoferPermanente("C1", "11", 2000, 1);
        Cliente cli = new Cliente("CL1", "123", "Lucas");
        Pedido pedido = new Pedido(cli, 1, true, true, 1, Constantes.ZONA_STANDARD);
        Vehiculo veh = new Auto("AAA1", 2, true);
        Viaje viaje = new Viaje(pedido, chofer, veh);

        HashMap<Cliente, Viaje> viajes = new HashMap<>();
        viajes.put(cli, viaje);

        dto.setViajesIniciados(viajes);

        assertEquals(viajes, dto.getViajesIniciados());
    }

    @Test
    public void testSetGetViajesTerminados() {
        EmpresaDTO dto = new EmpresaDTO();

        ChoferPermanente chofer = new ChoferPermanente("C1", "11", 2000, 1);
        Cliente cli = new Cliente("CL1", "123", "Lucas");
        Pedido pedido = new Pedido(cli, 1, true, true, 1, Constantes.ZONA_STANDARD);
        Vehiculo veh = new Auto("AAA1", 2, true);
        Viaje viaje = new Viaje(pedido, chofer, veh);

        ArrayList<Viaje> terminados = new ArrayList<>();
        terminados.add(viaje);

        dto.setViajesTerminados(terminados);

        assertEquals(terminados, dto.getViajesTerminados());
    }
}
