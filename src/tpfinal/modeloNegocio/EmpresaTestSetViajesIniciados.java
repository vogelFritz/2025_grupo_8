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
import util.Constantes; // Importamos las constantes

import java.util.HashMap;

public class EmpresaTestSetViajesIniciados {
    private Empresa empresa;
    private HashMap<Cliente, Viaje> mapEscenario; 
    private HashMap<Cliente, Viaje> mapVacio;
    private HashMap<Cliente, Viaje> mapConDatosN;
    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
        mapEscenario = new HashMap<Cliente, Viaje>(); 
        mapVacio = new HashMap<Cliente, Viaje>();
        mapConDatosN = new HashMap<Cliente, Viaje>();
        
        Cliente c1 = new Cliente("userSet1", "p1", "C Set 1");
        Cliente c2 = new Cliente("userSet2", "p2", "C Set 2");
        Chofer ch1 = new ChoferTemporario("30T1", "Ch Set 1");
        Chofer ch2 = new ChoferTemporario("30T2", "Ch Set 2");
        Vehiculo v1 = new Moto("MOTO_S1");
        Vehiculo v2 = new Moto("MOTO_S2");
        String zona = Constantes.ZONA_STANDARD; 
        Pedido p1 = new Pedido(c1, 1, false, false, 5, zona);
        Pedido p2 = new Pedido(c2, 1, false, false, 8, zona);
        Viaje viaje1 = new Viaje(p1, ch1, v1);
        Viaje viaje2 = new Viaje(p2, ch2, v2);
        
        mapConDatosN.put(c1, viaje1);
        mapConDatosN.put(c2, viaje2);
        
        this.setupEscenario1();
        
        assertNotSame("Pre-condición: mapEscenario y mapConDatosN deben ser instancias distintas", mapEscenario, mapConDatosN);
        assertNotSame("Pre-condición: mapEscenario y mapVacio deben ser instancias distintas", mapEscenario, mapVacio);
    }

    /**
     * Empresa con un mapa de viajes iniciados conocido (mapEscenario).
     */
    private void setupEscenario1() {
        empresa.setViajesIniciados(mapEscenario);
        assertSame("Setup Escenario 1 falló", mapEscenario, empresa.getViajesIniciados());
    }
    
    /**
     * Prueba (1 - Escenario 1): Verifica que el setter reemplaza
     * el mapa (mapEscenario) por un mapa con N elementos (mapConDatosN).
     * Cubre: CE 3.
     */
    @Test
    public void testSetViajesIniciadosNElementos() {
        empresa.setViajesIniciados(mapConDatosN);
        
        HashMap<Cliente, Viaje> resultado = empresa.getViajesIniciados();
        
        assertSame("El getter no retornó el mapa N seteado", mapConDatosN, resultado);
        assertEquals("El tamaño del mapa (2) no es el esperado", 2, resultado.size());
    }

    /**
     * Prueba (2 - Escenario 1): Verifica que el setter puede reemplazar
     * el mapa (mapEscenario) por un mapa vacío (mapVacio).
     * Cubre: CE 2.
     */
    @Test
    public void testSetViajesIniciadosVacio() {
        empresa.setViajesIniciados(mapVacio);
        HashMap<Cliente, Viaje> resultado = empresa.getViajesIniciados();
        
        assertSame("El getter no retornó el mapa vacío seteado", mapVacio, resultado);
        assertTrue("El mapa retornado no está vacío", resultado.isEmpty());
    }
}