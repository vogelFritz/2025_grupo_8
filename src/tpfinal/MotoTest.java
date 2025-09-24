package tpfinal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import util.Constantes;

public class MotoTest {
	Moto moto = new Moto("test");
	
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
	public void testConstructor() {
		final Moto moto = new Moto("test");
		assertSame(moto.getPatente(), "test");
		assertSame(moto.getCantidadPlazas(), 1);
		assertSame(moto.isMascota(), false);
	}
	@Test
	public void testGetPuntajePedidoValido() {
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente, 1, false, false, 10, Constantes.ZONA_STANDARD);
		final Integer puntaje = moto.getPuntajePedido(pedido);
		assertTrue(puntaje != null);
		assertTrue(puntaje == 1000);
	}
	@Test
	public void testGetPuntajePedidoMuchosPasajeros() {
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente, 2, false, false, 10, Constantes.ZONA_STANDARD);
		final Integer puntaje = moto.getPuntajePedido(pedido);
		assertTrue(puntaje == null);
	}
	@Test
	public void testGetPuntajePedidoConMascota() {
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente, 1, true, false, 10, Constantes.ZONA_STANDARD);
		final Integer puntaje = moto.getPuntajePedido(pedido);
		assertTrue(puntaje == null);
	}
	@Test
	public void testGetPuntajePedidoConBaul() {
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente, 1, false, true, 10, Constantes.ZONA_STANDARD);
		final Integer puntaje = moto.getPuntajePedido(pedido);
		assertTrue(puntaje == null);
	}
}
