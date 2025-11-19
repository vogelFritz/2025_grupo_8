package tpfinal.modeloDatos.auto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modeloDatos.Auto;

public class GetCantidadPlazasTest {


	@Test
	public void test() {
		
		Auto a = new Auto("1", 4, true);
		
		assertEquals(a.getCantidadPlazas(), 4);
		
	}

}
