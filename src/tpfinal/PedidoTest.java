package tpfinal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modeloDatos.Cliente;
import modeloDatos.Pedido;
import util.Constantes;

public class PedidoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorValido() {
		final Cliente cliente = new Cliente("test", "test", "test");
		final Pedido pedido = new Pedido(cliente, 1, false, false, 10, Constantes.ZONA_STANDARD);
		assertSame(pedido.getCantidadPasajeros(), 1);
		assertSame(pedido.getCliente(), cliente);
		assertSame(pedido.getKm(), 10);
		assertSame(pedido.getZona(), Constantes.ZONA_STANDARD);
		assertSame(pedido.isBaul(), false);
		assertSame(pedido.isMascota(), false);
	}
}
