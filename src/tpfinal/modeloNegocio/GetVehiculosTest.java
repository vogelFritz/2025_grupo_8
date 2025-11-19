package tpfinal.modeloNegocio;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
public class GetVehiculosTest {

	
	private Empresa emp;
	private ArrayList<Vehiculo> vehiculos;
	
	@Before
	public void setUp() throws Exception {
		
		emp = Empresa.getInstance();
		
		Vehiculo v1 = new Auto("1", 4, false);
		Vehiculo v2 = new Auto("2", 4, false);
		
		this.vehiculos = new ArrayList<>();
		
		this.vehiculos.add(v1);
		this.vehiculos.add(v2);
		
		emp.agregarVehiculo(v1);
		emp.agregarVehiculo(v2);
		
	}

	@Test
	public void test() {
		
		HashMap<String, Vehiculo> hm = emp.getVehiculos();
		
		assertEquals(hm.get("1"), vehiculos.get(0));
		assertEquals(hm.get("2"), vehiculos.get(1));
		
	}

}
