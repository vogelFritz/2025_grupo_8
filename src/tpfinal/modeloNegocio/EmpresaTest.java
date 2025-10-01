package tpfinal.modeloNegocio;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import excepciones.UsuarioYaExisteException;
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
			emp.agregarPedido(pedido);
			assertSame(emp.getPedidos().size(), 1);
			assertSame(emp.getPedidoDeCliente(cliente), pedido);
		} catch (Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage() + "\n" + e.getStackTrace());
		}
	}
}
