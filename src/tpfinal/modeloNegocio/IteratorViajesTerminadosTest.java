package tpfinal.modeloNegocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;

public class IteratorViajesTerminadosTest {

	Empresa emp;
	ArrayList<Viaje> finalizados;
	
	public void setUp() {
		emp = Empresa.getInstance();
		
		Cliente cl1 = new Cliente("f", "f", "f");
		Cliente cl2 = new Cliente("g", "g", "g");
		
		ChoferPermanente ch1 = new ChoferPermanente("1", "Jorge", 2000, 1);
		ChoferPermanente ch2 = new ChoferPermanente("2", "Carlos", 2000, 1);
		
		Vehiculo v1 = new Auto("1", 4, true);
		Vehiculo v2 = new Auto("2", 4, true);
		
		Pedido p1 = new Pedido(cl1, 2, false, false, 10, "ZONA_STANDARD");
		Pedido p2 = new Pedido(cl2, 2, false, false, 10, "ZONA_STANDARD");
		
		
		Viaje viaje1 = new Viaje(p1, ch1, v1);
		
		Viaje viaje2 = new Viaje(p2, ch2, v2);
		
		ArrayList<Viaje> x = new ArrayList<>();
		
		x.add(viaje1);
		x.add(viaje2);
		
		emp.setViajesTerminados(x);
		
		finalizados = x;
		
		
	}
	
	@Test
	public void caso1() {
		setUp();
		
		ArrayList<Viaje> it = emp.iteratorViajesTerminados();
		
		assertEquals(finalizados.get(0), it.get(0));
		assertEquals(finalizados.get(1), it.get(1));
		
	}

}
