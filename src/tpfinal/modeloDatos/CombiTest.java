package tpfinal.modeloDatos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Pedido;

public class CombiTest {

    // --- Tests de Getters y Constructor (Happy Path) ---
    
    @Test
    public void testConstructorYGetters() {
        // Verifica instanciación correcta y recuperación de atributos
        Combi c = new Combi("AAA111", 8, true);
        
        assertEquals("AAA111", c.getPatente());
        assertEquals(8, c.getCantidadPlazas());
        assertEquals(true, c.isMascota());
    }

    @Test
    public void testGetPuntajePedidoSinBaul() {
        // Formula: (10 * cantPax)
        // Caso: 10 pax * 10 = 100
        Combi c = new Combi("1", 10, true);
        Pedido p = new Pedido(new Cliente("f", "f", "f"), 10, true, false, 10, "ZONA_STANDARD");
        
        assertEquals((Integer)100, c.getPuntajePedido(p));
    }
    
}