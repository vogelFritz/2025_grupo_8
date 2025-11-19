package empresa;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;


public class VehiculosOrdenadosTest {

	private Empresa emp;
	ArrayList<Vehiculo> listaEsperada;
	private Vehiculo v1;
	private Vehiculo v2;
	private Vehiculo v3;
	private Cliente cl;
	

	public void escenario1() {
		
		
		this.emp = Empresa.getInstance();
		
		v1 = new Auto("1", 4, true);
		v2 = new Combi("2", 10, true);
		v3 = new Moto("3");
		
		cl = new Cliente("Fede", "1234", "Federico");
		try {
			emp.agregarCliente("Fede", "1234", "Federico");
		}
		catch(Exception e) {}
		HashMap<String, Vehiculo> hm = new HashMap<>();
		ArrayList<Vehiculo> a = new ArrayList<>();
		
		hm.put(v1.getPatente(), v1);
		hm.put(v2.getPatente(), v2);
		hm.put(v3.getPatente(), v3);
		
		a.add(v1);
		a.add(v2);
		a.add(v3);
		
		emp.setVehiculos(hm);
		emp.setVehiculosDesocupados(a);
		
		
		System.out.println(emp.getVehiculos());
		System.out.println(emp.getClientes());
		
	}
	
	@Test
	public void caso1() {
		
		escenario1();
		
		listaEsperada = new ArrayList<>();
		
		Pedido p = new Pedido(cl, 2, false, false, 10, "ZONA_STANDARD");
		
		listaEsperada.add(v1); //auto
		listaEsperada.add(v2); //combi
		
		HashMap<Cliente, Pedido> x = new HashMap<>();
		
		x.put(cl, p);
		emp.setPedidos(x);

		
		ArrayList<Vehiculo> list = emp.vehiculosOrdenadosPorPedido(p);
		
		assertEquals(listaEsperada, list);
			
	}
	
	
	@Test
	public void caso2() {
		
		escenario1();
		
		listaEsperada = new ArrayList<>();
		
		Pedido p = new Pedido(cl, 1, false, false, 10, "ZONA_STANDARD");
		
		
		listaEsperada.add(v3); //moto
		listaEsperada.add(v1); //auto
		listaEsperada.add(v2); //combi
		
		HashMap<Cliente, Pedido> x = new HashMap<>();
		
		x.put(cl, p);
		emp.setPedidos(x);

		
		ArrayList<Vehiculo> list = emp.vehiculosOrdenadosPorPedido(p);
		
		assertEquals(listaEsperada, list);
			
	}
	
	@Test
	public void caso3() {
		
		escenario1();
		
		listaEsperada = new ArrayList<>();
		
		Pedido p = new Pedido(cl, 5, false, false, 10, "ZONA_STANDARD");
		
		listaEsperada.add(v2); //combi
		
		HashMap<Cliente, Pedido> x = new HashMap<>();
		
		x.put(cl, p);
		emp.setPedidos(x);

		
		ArrayList<Vehiculo> list = emp.vehiculosOrdenadosPorPedido(p);
		
		assertEquals(listaEsperada, list);
			
	}
	
	
	
	
	


}
