package tpfinal.modeloDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
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
	@Test
	public void testGetValorStandard() {
		try {
			final Auto auto = new Auto("test",4,true);
			final Chofer chofer = new ChoferPermanente("test","test",2000,1);
			final Cliente cliente = new Cliente("test","test","test");
			final Pedido pedido = new Pedido(cliente,2,false,false,5,Constantes.ZONA_STANDARD);
			final Viaje viaje = new Viaje(pedido,chofer,auto);
			Viaje.setValorBase(100);
			assertTrue(viaje.getValor() == 170);
		} catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testGetValorSinAsfaltar() {
		try {
			final Auto auto = new Auto("test",4,true);
			final Chofer chofer = new ChoferPermanente("test","test",2000,1);
			final Cliente cliente = new Cliente("test","test","test");
			final Pedido pedido = new Pedido(cliente,2,false,false,5,Constantes.ZONA_SIN_ASFALTAR);
			final Viaje viaje = new Viaje(pedido,chofer,auto);
			Viaje.setValorBase(100);
			assertTrue(viaje.getValor() == 215);
		} catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testGetValorZonaPeligrosa() {
		try {
			final Auto auto = new Auto("test",4,true);
			final Chofer chofer = new ChoferPermanente("test","test",2000,1);
			final Cliente cliente = new Cliente("test","test","test");
			final Pedido pedido = new Pedido(cliente,2,false,false,5,Constantes.ZONA_PELIGROSA);
			final Viaje viaje = new Viaje(pedido,chofer,auto);
			Viaje.setValorBase(100);
			assertTrue(viaje.getValor() == 220);
		} catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testGetValorConMascota() {
		try {
			final Auto auto = new Auto("test",4,true);
			final Chofer chofer = new ChoferPermanente("test","test",2000,1);
			final Cliente cliente = new Cliente("test","test","test");
			final Pedido pedido = new Pedido(cliente,2,true,false,5,Constantes.ZONA_STANDARD);
			final Viaje viaje = new Viaje(pedido,chofer,auto);
			Viaje.setValorBase(100);
			assertTrue(viaje.getValor() == 220);
		} catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
	@Test
	public void testGetValorConBaul() {
		try {
			final Auto auto = new Auto("test",4,true);
			final Chofer chofer = new ChoferPermanente("test","test",2000,1);
			final Cliente cliente = new Cliente("test","test","test");
			final Pedido pedido = new Pedido(cliente,2,false,true,5,Constantes.ZONA_STANDARD);
			final Viaje viaje = new Viaje(pedido,chofer,auto);
			Viaje.setValorBase(100);
			assertTrue(viaje.getValor() == 145);
		} catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage());
		}
	}
}
