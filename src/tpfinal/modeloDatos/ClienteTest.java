package tpfinal.modeloDatos;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import modeloDatos.Cliente;

public class ClienteTest {

    // --- Test del Constructor (Inicialización Completa) ---
    @Test
    public void testConstructor() {
        // Verifica que el constructor asigne todos los valores correctamente de una sola vez
        Cliente c = new Cliente("Fede", "1234", "Federico");
        
        assertEquals("Fede", c.getNombreUsuario());
        assertEquals("1234", c.getPass());
        assertEquals("Federico", c.getNombreReal());
    }

    // --- Tests de Getters Individuales ---
    
    @Test
    public void testGetNombreReal() {
        Cliente c = new Cliente("Fede", "1234", "Federico");
        assertEquals("Federico", c.getNombreReal());
    }

    @Test
    public void testGetNombreUsuario() {
        Cliente c = new Cliente("Fede", "1234", "Federico");
        assertEquals("Fede", c.getNombreUsuario());
    }

    @Test
    public void testGetPass() {
        Cliente c = new Cliente("Fede", "1234", "Federico");
        assertEquals("1234", c.getPass());
    }

}