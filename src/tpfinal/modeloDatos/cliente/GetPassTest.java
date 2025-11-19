package tpfinal.modeloDatos.cliente;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modeloDatos.Cliente;

public class GetPassTest {


	@Test
	public void test() {
		Cliente c = new Cliente("Fede", "1234", "Federico");
		
		assertEquals("1234", c.getPass());
	}

}
