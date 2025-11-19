package tpfinal.modeloDatos.auto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modeloDatos.Auto;

public class IsMascotaTest {


	@Test
	public void test() {
		
		Auto a = new Auto("1", 4, true);
		
		assertEquals(a.isMascota(), true);
		
	}

}
