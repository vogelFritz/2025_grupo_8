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

import java.util.ArrayList;

public class EmpresaTestSetViajesTerminados {
    private Empresa empresa;
    private ArrayList<Viaje> listEscenario;
    private ArrayList<Viaje> listVacia;
    private ArrayList<Viaje> listConDatosN;

    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
        listEscenario = new ArrayList<Viaje>();
        listVacia = new ArrayList<Viaje>();
        listConDatosN = new ArrayList<Viaje>();
        Cliente c1 = new Cliente("userSetT1", "p1", "C Set T1");
        Cliente c2 = new Cliente("userSetT2", "p2", "C Set T2");
        Chofer ch1 = new ChoferTemporario("30T1S", "Ch Set T1");
        Chofer ch2 = new ChoferTemporario("30T2S", "Ch Set T2");
        Vehiculo v1 = new Moto("MOTO_ST1");
        Vehiculo v2 = new Moto("MOTO_ST2");
        String zona = Constantes.ZONA_STANDARD;
        Pedido p1 = new Pedido(c1, 1, false, false, 5, zona);
        Pedido p2 = new Pedido(c2, 1, false, false, 8, zona);
        Viaje viaje1 = new Viaje(p1, ch1, v1);
        Viaje viaje2 = new Viaje(p2, ch2, v2);

        listConDatosN.add(viaje1);
        listConDatosN.add(viaje2);
        this.setupEscenario1();
        assertNotSame("Pre-condición: listEscenario y listConDatosN deben ser instancias distintas", listEscenario,
                listConDatosN);
        assertNotSame("Pre-condición: listEscenario y listVacia deben ser instancias distintas", listEscenario,
                listVacia);
    }

    /**
     * Empresa con una lista de viajes terminados conocida (listEscenario).
     */
    private void setupEscenario1() {
        empresa.setViajesTerminados(listEscenario);
        assertSame("Setup Escenario 1 falló", listEscenario, empresa.getViajesTerminados());
    }

    /**
     * Prueba (1 - Escenario 1): Verifica que el setter reemplaza
     * la lista (listEscenario) por una lista con N elementos (listConDatosN).
     * Cubre: CE 3.
     */
    @Test
    public void testSetViajesTerminadosNElementos() {
        empresa.setViajesTerminados(listConDatosN);
        ArrayList<Viaje> resultado = empresa.getViajesTerminados();
        assertSame("El getter no retornó la lista N seteada", listConDatosN, resultado);
        assertEquals("El tamaño de la lista (2) no es el esperado", 2, resultado.size());
    }

    /**
     * Prueba (2 - Escenario 1): Verifica que el setter puede reemplazar
     * la lista (listEscenario) por una lista vacía (listVacia).
     * Cubre: CE 2.
     */
    @Test
    public void testSetViajesTerminadosVacio() {
        empresa.setViajesTerminados(listVacia);
        ArrayList<Viaje> resultado = empresa.getViajesTerminados();

        assertSame("El getter no retornó la lista vacía seteada", listVacia, resultado);
        assertTrue("La lista retornada no está vacía", resultado.isEmpty());
    }
}
