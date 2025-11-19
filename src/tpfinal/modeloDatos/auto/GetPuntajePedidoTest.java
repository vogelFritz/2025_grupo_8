package tpfinal.modeloDatos.auto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Pedido;

public class GetPuntajePedidoTest {


	@Test
	public void test1() {
		
		Auto a = new Auto("1", 4, true);
		
		Pedido p = new Pedido(new Cliente("f", "f", "f"), 4, true, true, 10, "ZONA_STANDARD");
		
		assertEquals((Integer)160, a.getPuntajePedido(p));
		
	}
	
	@Test
	public void test2() {
		
		Auto a = new Auto("1", 4, true);
		
		Pedido p = new Pedido(new Cliente("f", "f", "f"), 4, true, false, 10, "ZONA_STANDARD");
		
		assertEquals((Integer)120, a.getPuntajePedido(p));
		
	}
	
	@Test
	public void test3() {
		
		Auto a = new Auto("1", 4, true);
		
		Pedido p = new Pedido(new Cliente("f", "f", "f"), 5, true, false, 10, "ZONA_STANDARD");
		
		assertEquals(null, a.getPuntajePedido(p));
		
	}

}
