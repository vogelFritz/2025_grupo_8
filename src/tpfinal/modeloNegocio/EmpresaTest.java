package tpfinal.modeloNegocio;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Administrador;
import modeloDatos.Auto;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
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
		} catch (ChoferRepetidoException e) {
			fail("No debería lanzar esta excepción");
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
		} catch (UsuarioYaExisteException e) {
			fail("No debería lanzar esta excepción");
		} catch (Exception e) {
			fail("No debería lanzar esta excepción");
		}
	}
	@Test
	public void testAgregarClienteConNombreUsado() {
		try {
			emp.agregarCliente("test", "test2", "test3");
			emp.agregarCliente("test", "test2", "test3");
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
			emp.agregarChofer(new ChoferPermanente("test","test",2000,1));
			emp.agregarVehiculo(new Auto("test",1,false));
			emp.login(cliente.getNombreUsuario(), cliente.getPass());
			emp.agregarPedido(pedido);
			assertSame(emp.getPedidos().size(), 1);
			assertSame(emp.getPedidoDeCliente(cliente), pedido);
		} catch (Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage() + "\n" + e.getStackTrace());
		}
	}
	@Test
	public void testAgregarPedidoSinVehiculo() {
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente,1,false,false,1,Constantes.ZONA_STANDARD);
		try {
			emp.agregarCliente(cliente.getNombreUsuario(), cliente.getPass(), cliente.getNombreReal());
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
	public void testAgregarPedidoClienteConViajeIniciado() {
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente,1,false,false,1,Constantes.ZONA_STANDARD);
		try {
			emp.agregarCliente(cliente.getNombreUsuario(), cliente.getPass(), cliente.getNombreReal());
			emp.agregarChofer(new ChoferPermanente("test","test",2000,1));
			emp.setUsuarioLogeado(Administrador.getInstance());
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
		final Auto auto = new Auto("test", 2, false);
		try {
			emp.agregarVehiculo(auto);
			assertTrue(emp.getVehiculos().size() == 1);
		} catch (Exception e) {
			fail("No debería lanzar ninguna excepción");
		}
	}
	@Test
	public void testAgregarVehiculoRepetido() {
		final Auto auto = new Auto("test", 2, false);
		final Auto mismaPatente = new Auto("test", 2, false);
		try {
			emp.agregarVehiculo(auto);
			emp.agregarVehiculo(mismaPatente);
			fail("Debería lanzar VehiculoRepetidoException");
		} catch (VehiculoRepetidoException e) {
			assertSame(e.getPatentePrentendida(), "test");
			assertSame(e.getVehiculoExistente(), auto);
		}
		catch (Exception e) {
			fail("No debería lanzar esta excepción");
		}
	}
	@Test
	public void testCalificacionDeChofer() {
		final ChoferPermanente chofer = new ChoferPermanente("1234","test",2000,0);
		final Cliente cliente = new Cliente("test","test","test");
		final Auto auto = new Auto("test",2,false);
		final Pedido pedido = new Pedido(cliente,1,false,false,2,Constantes.ZONA_STANDARD);
		try {
			emp.agregarChofer(chofer);
			emp.agregarCliente("test","test","test");
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
	public void testCalificacionDeChoferInexistente() {
		final ChoferPermanente chofer = new ChoferPermanente("1234","test",2000,0);
		final Cliente cliente = new Cliente("test","test","test");
		final Auto auto = new Auto("test",2,false);
		final Pedido pedido = new Pedido(cliente,1,false,false,2,Constantes.ZONA_STANDARD);
		try {
			emp.agregarCliente("test","test","test");
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
}
