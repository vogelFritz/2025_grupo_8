package tpfinal.modeloNegocio;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.SinViajesException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Administrador;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;

public class EmpresaTest {
	static private Empresa emp = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emp = Empresa.getInstance(); 
		emp.setUsuarioLogeado(Administrador.getInstance());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		emp.setChoferes(new HashMap<>());
		emp.setClientes(new HashMap<>());
		emp.setVehiculos(new HashMap<>());
		emp.setPedidos(new HashMap<>());
		emp.setViajesIniciados(new HashMap<>());
		emp.setViajesTerminados(new ArrayList<>());
		emp.setChoferesDesocupados(new ArrayList<>());
		emp.setVehiculosDesocupados(new ArrayList<>());
		emp.setUsuarioLogeado(null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAgregarChofer() {
		final ChoferPermanente chofer = new ChoferPermanente("test","test",2000,1);
		assertTrue(emp.getChoferes().isEmpty());
		try {
			emp.agregarChofer(chofer);
			assertSame(emp.getChoferes().size(), 1);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción");
		}
	}
	@Test
	public void testAgregarChoferConDniExistente() {
		final ChoferPermanente chofer = new ChoferPermanente("test","test",2000,1);
		final ChoferPermanente chofer2 = new ChoferPermanente("test","test",2000,1);
		try {
			emp.agregarChofer(chofer);
			emp.agregarChofer(chofer2);
			fail("Debería haber lanzado una excepción");
		} catch (ChoferRepetidoException e) {
			assertSame(e.getChoferExistente(), chofer);
			assertSame(e.getDniPrentendido(), "test");
		} catch (Exception e) {
			fail("No debería lanzar esta excepción");
		}
	}
	@Test
	public void testAgregarCliente() {
		try {
			emp.agregarCliente("test", "test", "test");
			assertSame(emp.getClientes().size(), 1);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción");
		}
	}
	@Test
	public void testAgregarClienteConNombreUsado() {
		try {
			emp.agregarCliente("test", "test", "test");
			emp.agregarCliente("test", "test", "test");
			fail("Debería haber lanzado una excepción");
		} catch (UsuarioYaExisteException e) {
			assertSame(e.getUsuarioPretendido(), "test");
		} catch (Exception e) {
			fail("No debería lanzar esta excepción");
		}
	}
	@Test
	public void testAgregarPedido() {
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente,1,false,false,1,Constantes.ZONA_STANDARD);
		try {
			emp.agregarCliente(cliente.getNombreUsuario(), cliente.getPass(), cliente.getNombreReal());
			setUnicoClienteRegistrado(cliente);
			emp.agregarChofer(new ChoferPermanente("test","test",2000,1));
			emp.agregarVehiculo(new Moto("test"));
			emp.agregarPedido(pedido);
			assertSame(emp.getPedidos().size(), 1);
			assertSame(emp.getPedidoDeCliente(cliente), pedido);
		} catch (Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage() + "\n" + e.getStackTrace());
		}
	}
	@Test
	public void testAgregarPedidoClienteNoExiste() {
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente,1,false,false,1,Constantes.ZONA_STANDARD);
		try {
			emp.agregarChofer(new ChoferPermanente("test","test",2000,1));
			emp.agregarVehiculo(new Moto("test"));
			emp.agregarPedido(pedido);
			fail("Debería lanzar ClienteNoExisteException");
		} catch (ClienteNoExisteException e) {
		}
		catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "\n" + e.getStackTrace());
		}
	}
	@Test
	public void testAgregarPedidoClienteConViajeIniciado() {
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente,1,false,false,1,Constantes.ZONA_STANDARD);
		final Pedido pedido2 = new Pedido(cliente,1,false,false,1,Constantes.ZONA_STANDARD);
		final ChoferPermanente chofer = new ChoferPermanente("test","test",2000,1);
		try {
			emp.agregarCliente(cliente.getNombreUsuario(), cliente.getPass(), cliente.getNombreReal());
			setUnicoClienteRegistrado(cliente);
			emp.agregarChofer(chofer);
			emp.setUsuarioLogeado(Administrador.getInstance());
			emp.agregarVehiculo(new Moto("test"));
			emp.agregarPedido(pedido2);
			final ArrayList<Vehiculo> vs = emp.vehiculosOrdenadosPorPedido(pedido2);
			final Vehiculo v = vs.getFirst();
			emp.crearViaje(pedido2, chofer, v);
			emp.agregarPedido(pedido);
			fail("Debería lanzar ClienteConViajePendienteException");
		} catch (ClienteConViajePendienteException e) {
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "\n" + e.getStackTrace());
		}
	}
	@Test
	public void testAgregarPedidoClienteConPedidoPendiente() {
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente,1,false,false,1,Constantes.ZONA_STANDARD);
		final Pedido pedido2 = new Pedido(cliente,1,false,false,1,Constantes.ZONA_STANDARD);
		final ChoferPermanente chofer = new ChoferPermanente("test","test",2000,1);
		try {
			emp.agregarCliente(cliente.getNombreUsuario(), cliente.getPass(), cliente.getNombreReal());
			setUnicoClienteRegistrado(cliente);
			emp.agregarChofer(chofer);
			emp.setUsuarioLogeado(Administrador.getInstance());
			emp.agregarVehiculo(new Moto("test"));
			emp.agregarPedido(pedido2);
			emp.agregarPedido(pedido);
			fail("Debería lanzar ClienteConPedidoPendienteException");
		} catch (ClienteConPedidoPendienteException e) {
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "\n" + e.getStackTrace());
		}
	}
	@Test
	public void testAgregarPedidoSinVehiculo() {
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente,1,false,false,1,Constantes.ZONA_STANDARD);
		try {
			emp.agregarCliente(cliente.getNombreUsuario(), cliente.getPass(), cliente.getNombreReal());
			setUnicoClienteRegistrado(cliente);
			emp.agregarChofer(new ChoferPermanente("test","test",2000,1));
			emp.login(cliente.getNombreUsuario(), cliente.getPass());
			emp.agregarPedido(pedido);
			fail("Debería lanzar SinVehiculoParaPedidoException");
		} catch (SinVehiculoParaPedidoException e) {
			assertSame(e.getPedido(),pedido);
		}catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "\n" + e.getStackTrace());
		}
	}
	@Test
	public void testAgregarVehiculo() {
		final Auto auto = new Auto("test", 1, false);
		try {
			emp.agregarVehiculo(auto);
			assertTrue(emp.getVehiculos().size() == 1);
		} catch (Exception e) {
			fail("No debería lanzar ninguna excepción");
		}
	}
	@Test
	public void testAgregarVehiculoRepetido() {
		final Auto auto = new Auto("test", 1, false);
		final Auto mismaPatente = new Auto("test", 1, false);
		try {
			emp.agregarVehiculo(auto);
			emp.agregarVehiculo(mismaPatente);
			fail("Debería lanzar VehiculoRepetidoException");
		} catch (VehiculoRepetidoException e) {
			assertSame(e.getPatentePrentendida(), "test");
			assertTrue(e.getVehiculoExistente() == auto);
		}
		catch (Exception e) {
			fail("No debería lanzar esta excepción");
		}
	}
	@Test
	public void testCalificacionDeChofer() {
		final ChoferPermanente chofer = new ChoferPermanente("test","test",2000,0);
		final Cliente cliente = new Cliente("test","test","test");
		final Auto auto = new Auto("test",2,false);
		final Pedido pedido = new Pedido(cliente,1,false,false,2,Constantes.ZONA_STANDARD);
		try {
			emp.agregarChofer(chofer);
			emp.agregarCliente("test","test","test");
			setUnicoClienteRegistrado(cliente);
			emp.login("test", "test");
			emp.agregarVehiculo(auto);
			emp.agregarPedido(pedido);
			emp.crearViaje(pedido,chofer,auto);
			emp.pagarYFinalizarViaje(5);
			final double calificacion = emp.calificacionDeChofer(chofer);
			assertTrue(calificacion == 5);
		} catch (Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage());
		}
	}
	@Test
	public void testCalificacionDeChoferSinViajes() {
		final ChoferPermanente chofer = new ChoferPermanente("test1","test1",2000,0);
		try {
			emp.agregarChofer(chofer);
			emp.calificacionDeChofer(chofer);
			fail("Debería lanzar SinViajesException");
		} catch (SinViajesException e) {
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testCalificacionDeChoferInexistente() {
		final ChoferPermanente chofer = new ChoferPermanente("1234","test",2000,0);
		final Cliente cliente = new Cliente("test","test","test");
		final Auto auto = new Auto("test",2,false);
		final Pedido pedido = new Pedido(cliente,1,false,false,2,Constantes.ZONA_STANDARD);
		try {
			emp.agregarCliente("test","test","test");
			setUnicoClienteRegistrado(cliente);
			emp.login("test", "test");
			emp.agregarVehiculo(auto);
			emp.agregarPedido(pedido);
			emp.crearViaje(pedido,chofer,auto);
			emp.pagarYFinalizarViaje(5);
			final double calificacion = emp.calificacionDeChofer(chofer);
			assertTrue(calificacion == 5);
		} catch (Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage());
		}
	}
	@Test
	public void testSetUsuarioLogueado() {
		emp.setUsuarioLogeado(Administrador.getInstance());
		assertSame(emp.getUsuarioLogeado(), Administrador.getInstance());
		assertTrue(emp.isAdmin());
	}
	@Test
	public void testLogout() {
		emp.setUsuarioLogeado(Administrador.getInstance());
		emp.logout();
		assertTrue(!emp.isAdmin());
		assertTrue(emp.getUsuarioLogeado() == null);
	}
	
	@Test
	public void testGetUsuarioLogeado() {
		try {
			final Cliente usuario1 = new Cliente("test","test","test");
			emp.setUsuarioLogeado(usuario1);
			final Usuario resultado = emp.getUsuarioLogeado();
			assertTrue(resultado == usuario1);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testGetUsuarioLogeadoRetornaNull() {
		try {
			emp.setUsuarioLogeado(null);
			final Usuario resultado = emp.getUsuarioLogeado();
			assertTrue(resultado == null);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetChoferesDesocupadosUnoDesocupado() {
		try {
			final Chofer choferDesocupado = new ChoferPermanente("test","test",2000,1);
			emp.agregarChofer(choferDesocupado);
			final ArrayList<Chofer> desocupados = emp.getChoferesDesocupados();
			assertTrue(desocupados.size() == 1);
			assertTrue(desocupados.getFirst() == choferDesocupado);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testGetChoferesDesocupadosUnoOcupado() {
		try {
			final Chofer choferOcupado = new ChoferPermanente("test","test",2000,1);
			emp.agregarChofer(choferOcupado);
			final Cliente cliente = new Cliente("test","test","test");
			emp.agregarCliente("test", "test", "test");
			setUnicoClienteRegistrado(cliente);
			final Pedido pedido = new Pedido(cliente,1,false,false,2,Constantes.ZONA_STANDARD);
			final Auto auto = new Auto("test",4,true);
			emp.agregarVehiculo(auto);
			emp.agregarPedido(pedido);
			emp.crearViaje(pedido, choferOcupado, auto);
			final ArrayList<Chofer> desocupados = emp.getChoferesDesocupados();
			assertTrue(desocupados.isEmpty());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testGetChoferesDesocupadosNingunChofer() {
		try {
			final ArrayList<Chofer> desocupados = emp.getChoferesDesocupados();
			assertTrue(desocupados.isEmpty());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testGetChoferesDesocupadosUnoOcupadoYUnoDesocupado() {
		try {
			final Chofer choferDesocupado = new ChoferPermanente("test1","test1",2000,1);
			emp.agregarChofer(choferDesocupado);
			final Chofer choferOcupado = new ChoferPermanente("test2","test2",2000,1);
			emp.agregarChofer(choferOcupado);
			final Cliente cliente = new Cliente("test","test","test");
			emp.agregarCliente("test", "test", "test");
			setUnicoClienteRegistrado(cliente);
			final Pedido pedido = new Pedido(cliente,1,false,false,2,Constantes.ZONA_STANDARD);
			final Auto auto = new Auto("test",4,true);
			emp.agregarVehiculo(auto);
			emp.agregarPedido(pedido);
			emp.crearViaje(pedido, choferOcupado, auto);
			final ArrayList<Chofer> desocupados = emp.getChoferesDesocupados();
			assertTrue(desocupados.size() == 1);
			assertTrue(desocupados.getFirst() == choferDesocupado);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void testSetChoferesDesocupadosNingunChofer() {
		try {
			final ArrayList<Chofer> choferesDesocupados = new ArrayList<>(0);
			emp.setChoferesDesocupados(choferesDesocupados);
			assertTrue(emp.getChoferesDesocupados().isEmpty());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testSetChoferesDesocupadosUnChofer() {
		try {
			final Chofer chofer1 = new ChoferPermanente("test1", "test1", 2000, 1);
			final ArrayList<Chofer> choferesDesocupados = new ArrayList<>(1);
			choferesDesocupados.add(chofer1);
			emp.setChoferesDesocupados(choferesDesocupados);
			assertTrue(emp.getChoferesDesocupados().size() == 1);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testSetChoferesDesocupadosDosChoferes() {
		try {
			final Chofer chofer1 = new ChoferPermanente("test1", "test1", 2000, 1);
			final Chofer chofer2 = new ChoferPermanente("test2", "test2", 2000, 1);
			final ArrayList<Chofer> choferesDesocupados = new ArrayList<>(2);
			choferesDesocupados.add(chofer1);
			choferesDesocupados.add(chofer2);
			emp.setChoferesDesocupados(choferesDesocupados);
			assertTrue(emp.getChoferesDesocupados().size() == 2);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void testSetChoferesNingunChofer() {
		try {
			final HashMap<String,Chofer> choferesDesocupados = new HashMap<>(0);
			emp.setChoferes(choferesDesocupados);
			assertTrue(emp.getChoferes().isEmpty());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testSetChoferesDosChoferes() {
		try {
			final Chofer chofer1 = new ChoferPermanente("test1", "test1", 2000, 1);
			final Chofer chofer2 = new ChoferPermanente("test2", "test2", 2000, 1);
			final HashMap<String,Chofer> choferesDesocupados = new HashMap<>(2);
			choferesDesocupados.put(chofer1.getDni(), chofer1);
			choferesDesocupados.put(chofer2.getDni(), chofer2);
			emp.setChoferes(choferesDesocupados);
			assertTrue(emp.getChoferes().size() == 2);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetChoferesNingunChofer() {
		try {
			assertTrue(emp.getChoferes().isEmpty());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testGetChoferesUnChofer() {
		try {
			final Chofer chofer1 = new ChoferPermanente("test1", "test1", 2000, 1);
			emp.agregarChofer(chofer1);
			assertTrue(emp.getChoferes().size() == 1);
			assertTrue(emp.getChoferes().get(chofer1.getDni()) == chofer1);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	
	
	
	public void setUnicoClienteRegistrado(Cliente cliente) {
		final HashMap<String, Cliente> clientes = new HashMap<>();
		clientes.put(cliente.getNombreUsuario(), cliente);
		emp.setClientes(clientes);
	}
}
