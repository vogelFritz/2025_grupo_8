package tpfinal.persistencia;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import modeloNegocio.Empresa;
import persistencia.EmpresaDTO;
import persistencia.UtilPersistencia;
import util.Constantes;

public class TestUtilPersistencia {

    private Empresa e;

    private ChoferPermanente chofer;
    private Cliente cliente;
    private Pedido pedido;
    private Vehiculo vehiculo;
    private Viaje viaje;

    private HashMap<String, Chofer> choferes;
    private ArrayList<Chofer> choferesDesocupados;
    private HashMap<String, Cliente> clientes;
    private HashMap<Cliente, Pedido> pedidos;
    private HashMap<String, Vehiculo> vehiculos;
    private ArrayList<Vehiculo> vehiculosDesocupados;
    private HashMap<Cliente, Viaje> viajesIniciados;
    private ArrayList<Viaje> viajesTerminados;

    @Before
    public void setUp() {

        // Empresa singleton limpia
        e = Empresa.getInstance();
        e.setChoferes(new HashMap<>());
        e.setChoferesDesocupados(new ArrayList<>());
        e.setClientes(new HashMap<>());
        e.setPedidos(new HashMap<>());
        e.setVehiculos(new HashMap<>());
        e.setVehiculosDesocupados(new ArrayList<>());
        e.setViajesIniciados(new HashMap<>());
        e.setViajesTerminados(new ArrayList<>());
        e.setUsuarioLogeado(null);

        // Objetos de prueba
        chofer = new ChoferPermanente("CH123", "1234", 2000, 2);
        cliente = new Cliente("LU1234", "1234", "Lucas");
        pedido = new Pedido(cliente, 1, true, true, 1, Constantes.ZONA_PELIGROSA);
        vehiculo = new Auto("AA111", 2, true);
        viaje = new Viaje(pedido, chofer, vehiculo);

        // Colecciones cargadas
        choferes = new HashMap<>();
        choferesDesocupados = new ArrayList<>();
        clientes = new HashMap<>();
        pedidos = new HashMap<>();
        vehiculos = new HashMap<>();
        vehiculosDesocupados = new ArrayList<>();
        viajesIniciados = new HashMap<>();
        viajesTerminados = new ArrayList<>();

        choferes.put("CH1", chofer);
        choferesDesocupados.add(chofer);
        clientes.put("C1", cliente);
        pedidos.put(cliente, pedido);
        vehiculos.put("V1", vehiculo);
        vehiculosDesocupados.add(vehiculo);
        viajesIniciados.put(cliente, viaje);
        viajesTerminados.add(viaje);
    }

    @Test
    public void testEmpresaDtoFromEmpresa_Completa() {

        e.setChoferes(choferes);
        e.setChoferesDesocupados(choferesDesocupados);
        e.setClientes(clientes);
        e.setPedidos(pedidos);
        e.setVehiculos(vehiculos);
        e.setVehiculosDesocupados(vehiculosDesocupados);
        e.setViajesIniciados(viajesIniciados);
        e.setViajesTerminados(viajesTerminados);
        e.setUsuarioLogeado(cliente);

        EmpresaDTO dto = UtilPersistencia.EmpresaDtoFromEmpresa();

        assertEquals(choferes, dto.getChoferes());
        assertEquals(choferesDesocupados, dto.getChoferesDesocupados());
        assertEquals(clientes, dto.getClientes());
        assertEquals(pedidos, dto.getPedidos());
        assertEquals(vehiculos, dto.getVehiculos());
        assertEquals(vehiculosDesocupados, dto.getVehiculosDesocupados());
        assertEquals(viajesIniciados, dto.getViajesIniciados());
        assertEquals(viajesTerminados, dto.getViajesTerminados());
        assertEquals(cliente, dto.getUsuarioLogeado());
    }

    @Test
    public void testEmpresaDtoFromEmpresa_ColeccionesVacias() {

        EmpresaDTO dto = UtilPersistencia.EmpresaDtoFromEmpresa();

        assertTrue(dto.getChoferes().isEmpty());
        assertTrue(dto.getClientes().isEmpty());
        assertNull(dto.getUsuarioLogeado());
    }

    @Test
    public void testEmpresaDtoFromEmpresa_UsuarioNull() {

        e.setUsuarioLogeado(null);

        EmpresaDTO dto = UtilPersistencia.EmpresaDtoFromEmpresa();

        assertNull(dto.getUsuarioLogeado());
    }

    @Test
    public void testEmpresaFromDTO_Completo() {

        EmpresaDTO dto = new EmpresaDTO();

        dto.setChoferes(choferes);
        dto.setChoferesDesocupados(choferesDesocupados);
        dto.setClientes(clientes);
        dto.setPedidos(pedidos);
        dto.setVehiculos(vehiculos);
        dto.setVehiculosDesocupados(vehiculosDesocupados);
        dto.setViajesIniciados(viajesIniciados);
        dto.setViajesTerminados(viajesTerminados);
        dto.setUsuarioLogeado(cliente);

        UtilPersistencia.empresaFromEmpresaDTO(dto);

        assertEquals(dto.getChoferes(), e.getChoferes());
        assertEquals(dto.getClientes(), e.getClientes());
        assertEquals(dto.getUsuarioLogeado(), e.getUsuarioLogeado());
    }

    @Test
    public void testEmpresaFromDTO_ColeccionesVacias() {

        EmpresaDTO dto = new EmpresaDTO();
        UtilPersistencia.empresaFromEmpresaDTO(dto);

        assertTrue(e.getChoferes().isEmpty());
        assertTrue(e.getClientes().isEmpty());
        assertNull(e.getUsuarioLogeado());
    }

}
