package tpfinal.modeloNegocio;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class GetVehiculosDesocupadosTest {

	private Empresa emp;
	private ArrayList<Vehiculo> lista;
	
	@Before
	public void setUp() throws Exception {
		
		
		emp = Empresa.getInstance();		
		Vehiculo v1 = new Auto("1", 4, true);
		
		Vehiculo v2 = new Auto("2", 4, true);
		
		lista = new ArrayList<Vehiculo>();
		
		lista.add(v1);
		lista.add(v2);
		
		emp.setVehiculosDesocupados(lista);
			
	}

	@Test
	public void test() {
		ArrayList<Vehiculo> vehiculos = emp.getVehiculosDesocupados();
		
		assertEquals(vehiculos.get(0), lista.get(0));
		assertEquals(vehiculos.get(1), lista.get(1));
		
		
	}

}
