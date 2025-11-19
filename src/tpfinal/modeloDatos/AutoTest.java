package tpfinal.modeloDatos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Pedido;

public class AutoTest {

    // --- Tests de Getters básicos ---
    
    @Test
    public void testGetPatente() {
        Auto a = new Auto("1", 4, true);
        assertEquals("1", a.getPatente());
    }

    @Test
    public void testIsMascota() {
        Auto a = new Auto("1", 4, true);
        assertEquals(true, a.isMascota());
    }

    @Test
    public void testGetCantidadPlazas() {
        Auto a = new Auto("1", 4, true);
        assertEquals(4, a.getCantidadPlazas());
    }

    // --- Tests de Lógica de Negocio (Puntaje) ---

    @Test
    public void testGetPuntajePedidoConBaul() {
        // Caso: Pedido con baul (true) -> 4 pax * 40 = 160
        Auto a = new Auto("1", 4, true);
        Pedido p = new Pedido(new Cliente("f", "f", "f"), 4, true, true, 10, "ZONA_STANDARD");
        
        assertEquals((Integer)160, a.getPuntajePedido(p));
    }
    
    @Test
    public void testGetPuntajePedidoSinBaul() {
        // Caso: Pedido sin baul (false) -> 4 pax * 30 = 120
        Auto a = new Auto("1", 4, true);
        Pedido p = new Pedido(new Cliente("f", "f", "f"), 4, true, false, 10, "ZONA_STANDARD");
        
        assertEquals((Integer)120, a.getPuntajePedido(p));
    }
    
    @Test
    public void testGetPuntajePedidoSuperaCapacidad() {
        // Caso: Pedido (5 pax) supera capacidad Auto (4 plazas) -> Retorna null
        Auto a = new Auto("1", 4, true);
        Pedido p = new Pedido(new Cliente("f", "f", "f"), 5, true, false, 10, "ZONA_STANDARD");
        
        assertNull("Debería retornar null si la capacidad no es suficiente", a.getPuntajePedido(p));
    }

    @Test
    public void testGetPuntajePedidoRechazaMascota() {
        // Caso: Pedido requiere mascota (true), pero Auto NO acepta mascota (false) -> Retorna null
        Auto a = new Auto("1", 4, false); 
        Pedido p = new Pedido(new Cliente("f", "f", "f"), 2, true, false, 10, "ZONA_STANDARD");
        
        assertNull("Debería retornar null si el auto no acepta mascotas y el pedido lo requiere", a.getPuntajePedido(p));
    }

}