package tpfinal.modeloDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Viaje;
import util.Constantes;

public class ViajeTest {

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
		final ChoferTemporario chofer = new ChoferTemporario("test","test");
		final Cliente cliente = new Cliente("test","test","test");
		final Pedido pedido = new Pedido(cliente,1,false,false,10,Constantes.ZONA_STANDARD);
		final Moto moto = new Moto("test");
		final Viaje viaje = new Viaje(pedido, chofer, moto);
		assertSame(viaje.getCalificacion(), 0);
		assertSame(viaje.getChofer(), chofer);
		assertSame(viaje.getPedido(), pedido);
		assertSame(viaje.getVehiculo(), moto);
	}

}
