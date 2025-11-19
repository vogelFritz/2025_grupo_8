package tpfinal.modeloNegocio;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloNegocio.Empresa;

public class GetPedidosTest {

	Empresa emp;
	
	HashMap<Cliente, Pedido> hmOriginal;
	
	@Before
	public void setUp() throws Exception {
		
		emp = Empresa.getInstance();
		
		Cliente cl1 = new Cliente("f", "f", "f");
		Cliente cl2 = new Cliente("g", "g", "g");
		
		Pedido p1 = new Pedido(cl1, 2, false, false, 10, "ZONA_STANDARD");
		Pedido p2 = new Pedido(cl2, 2, false, false, 10, "ZONA_STANDARD");
		
		HashMap<Cliente, Pedido> hm = new HashMap<>();
		
		hm.put(cl1, p1);
		hm.put(cl2, p2);
		
		emp.setPedidos(hm);
		
		hmOriginal = hm;
	}

	@Test
	public void test() {
		
		assertEquals(hmOriginal, emp.getPedidos());
		
		
	}

}
