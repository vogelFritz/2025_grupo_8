package tpfinal.modeloNegocio;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import modeloNegocio.Empresa;
import modeloDatos.Cliente;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Vehiculo;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Viaje;
import util.Constantes; // [cite: 17]

import java.util.ArrayList;

public class EmpresaTestGetViajesTerminados {
    private Empresa empresa;
    private Viaje viaje1;
    private Viaje viaje2;
    private ArrayList<Viaje> listVacia;
    private ArrayList<Viaje> listUno;
    private ArrayList<Viaje> listN;
    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
        Cliente c1 = new Cliente("userT1", "pass1", "Cliente Term 1");
        Cliente c2 = new Cliente("userT2", "pass2", "Cliente Term 2");
       
        Chofer ch1 = new ChoferTemporario("30111T", "Chofer T1");
        Chofer ch2 = new ChoferTemporario("30222T", "Chofer T2");
        
        Vehiculo v1 = new Moto("MOTO_T1");
        Vehiculo v2 = new Moto("MOTO_T2"); 
        
        String zona = Constantes.ZONA_STANDARD;
        
        Pedido p1 = new Pedido(c1, 1, false, false, 5, zona); 
        Pedido p2 = new Pedido(c2, 1, false, false, 8, zona); 

        viaje1 = new Viaje(p1, ch1, v1);
        viaje2 = new Viaje(p2, ch2, v2);

        viaje1.finalizarViaje(5);
        viaje2.finalizarViaje(4);

        listVacia = new ArrayList<Viaje>();
        
        listUno = new ArrayList<Viaje>();
        listUno.add(viaje1);
        
        listN = new ArrayList<Viaje>();
        listN.add(viaje1);
        listN.add(viaje2);
        
        this.setupEscenario1();
    }
    /**
     * Empresa sin viajes terminados.
     */
    private void setupEscenario1() {
        empresa.setViajesTerminados(listVacia);
    }

    /**
     * Empresa con 1 viaje terminado.
     */
    private void setupEscenario2() {
        empresa.setViajesTerminados(listUno);
    }

    /**
     * Empresa con 2 (N > 1) viajes terminados.
     */
    private void setupEscenario3() {
        empresa.setViajesTerminados(listN);
    }

    /**
     * Prueba (1 - Escenario 1): Verifica que el getter retorna
     * una lista vacía cuando el estado interno está vacío.
     * Cubre: CE 1.
     */
    @Test
    public void testGetViajesTerminadosVacio() {
        ArrayList<Viaje> result = empresa.getViajesTerminados();
        
        assertNotNull("El ArrayList retornado no debe ser null", result);
        assertTrue("El ArrayList debe estar vacío", result.isEmpty());
        assertSame("El getter no retornó la instancia esperada (listVacia)", listVacia, result);
    }

    /**
     * Prueba (2 - Escenario 2): Verifica que el getter retorna
     * una lista con 1 elemento.
     * Cubre: CE 2.
     */
    @Test
    public void testGetViajesTerminadosUnElemento() {
        this.setupEscenario2();
        
        ArrayList<Viaje> result = empresa.getViajesTerminados();
        
        assertNotNull("El ArrayList retornado no debe ser null", result);
        assertEquals("El ArrayList debe tener 1 elemento", 1, result.size());
        assertSame("El elemento 0 no es el viaje1 esperado", viaje1, result.get(0));
        assertSame("El getter no retornó la instancia esperada (listUno)", listUno, result);
    }

    /**
     * Prueba (3 - Escenario 3): Verifica que el getter retorna
     * una lista con N elementos.
     * Cubre: CE 3.
     */
    @Test
    public void testGetViajesTerminadosNElementos() {
        this.setupEscenario3();
        
        ArrayList<Viaje> result = empresa.getViajesTerminados();
        
        assertNotNull("El ArrayList retornado no debe ser null", result);
        assertEquals("El ArrayList debe tener 2 elementos", 2, result.size());
        
        assertSame("El elemento 0 no es el viaje1 esperado", viaje1, result.get(0));
        assertSame("El elemento 1 no es el viaje2 esperado", viaje2, result.get(1));
        
        assertSame("El getter no retornó la instancia esperada (listN)", listN, result);
    }
}
