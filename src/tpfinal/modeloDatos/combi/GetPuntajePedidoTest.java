package tpfinal.modeloDatos.combi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modeloDatos.Combi;
import modeloDatos.Cliente;
import modeloDatos.Pedido;

public class GetPuntajePedidoTest {


	@Test
	public void test1() {
		
		Combi a = new Combi("1", 10, true);
		
		Pedido p = new Pedido(new Cliente("f", "f", "f"), 10, true, true, 10, "ZONA_STANDARD");
		
		assertEquals((Integer)200, a.getPuntajePedido(p));
		
	}
	
	@Test
	public void test2() {
		
		Combi a = new Combi("1", 10, true);
		
		Pedido p = new Pedido(new Cliente("f", "f", "f"), 10, true, false, 10, "ZONA_STANDARD");
		
		assertEquals((Integer)100, a.getPuntajePedido(p));
		
	}
	
	@Test
	public void test3() {
		
		Combi a = new Combi("1", 10, false);
		
		Pedido p = new Pedido(new Cliente("f", "f", "f"), 10, true, false, 10, "ZONA_STANDARD");
		
		assertEquals(null, a.getPuntajePedido(p));
		
	}

}

