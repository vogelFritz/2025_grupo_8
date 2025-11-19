package empresa;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloNegocio.Empresa;


public class IteratorPedidosTest {

	Empresa emp;
	HashMap<Cliente, Pedido> hmOriginal;
	Cliente cl1, cl2;
	
	@Before
	public void setUp() {
		emp = Empresa.getInstance();
		
		cl1 = new Cliente("f", "f", "f");
		cl2 = new Cliente("g", "g", "g");
		
		Pedido p1 = new Pedido(cl1, 2, false, false, 10, "ZONA_STANDARD");
		Pedido p2 = new Pedido(cl2, 2, true, false, 10, "ZONA_STANDARD");
		
		hmOriginal = new HashMap<>();
		
		hmOriginal.put(cl2, p2);
		hmOriginal.put(cl1, p1);
		
		emp.setPedidos(hmOriginal);
	} 
	
	@Test
	public void caso1() {
		
		Iterator<Pedido> it = emp.iteratorPedidos();
		
		assertEquals(hmOriginal.get(cl1), it.next());
		assertEquals(hmOriginal.get(cl2), it.next());
			
	}

}
