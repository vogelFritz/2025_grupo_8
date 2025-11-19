package tpfinal.Controlador;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import controlador.Controlador;
import modeloNegocio.Empresa;
import persistencia.IPersistencia;
import vista.IOptionPane;
import vista.IVista;

public class controladorTestGettersYSetters {
	private Controlador controladorReal;
	private Controlador controladorBajoPrueba;
	private IVista vistaMock;
	private IOptionPane optionPaneMock;
	private Object persistenciaMock;
	

	@Before
	public void setUp() throws Exception {
        this.vistaMock = Mockito.mock(IVista.class);
        this.optionPaneMock = Mockito.mock(IOptionPane.class);
        this.controladorReal = new Controlador();
        
        this.controladorReal.setVista(this.vistaMock);

        this.controladorBajoPrueba = Mockito.spy(this.controladorReal);
        when(this.vistaMock.getOptionPane()).thenReturn(this.optionPaneMock);
        
        Empresa.getInstance().logout();
        Empresa.getInstance().setClientes(new HashMap<>());
        Empresa.getInstance().setChoferes(new HashMap<>());
        Empresa.getInstance().setPedidos(new HashMap<>());
        Empresa.getInstance().setChoferesDesocupados(new ArrayList<>());
        Empresa.getInstance().setVehiculos(new HashMap<>());
        Empresa.getInstance().setVehiculosDesocupados(new ArrayList<>());
        Empresa.getInstance().setViajesIniciados(new HashMap<>());
        Empresa.getInstance().setViajesTerminados(new ArrayList<>());
	}

	@Test
    public void setFileName_deberiaFuncionar() {

        String nuevoNombre = "mi_archivo_de_test.dat";
        this.controladorBajoPrueba.setFileName(nuevoNombre);

        assertEquals("getFileName() no devolvió el nuevo nombre",nuevoNombre,this.controladorBajoPrueba.getFileName());
    }
	@Test
    public void getFileName_deberiaDevolverNombrePorDefecto() {
        String nombreEsperado = "empresa.bin";
        
        String nombreObtenido = this.controladorBajoPrueba.getFileName();

        assertEquals("getFileName() no devolvió el nombre por defecto", nombreEsperado, nombreObtenido);
    }
	@Test
    public void setPersistencia_deberiaFuncionar() {
        IPersistencia<?> nuevaPersistenciaMock =Mockito.mock(IPersistencia.class);

        this.controladorBajoPrueba.setPersistencia(nuevaPersistenciaMock);

        assertSame("getPersistencia() no devolvió el nuevo objeto mock",nuevaPersistenciaMock,this.controladorBajoPrueba.getPersistencia());
    }
	@Test
    public void getPersistencia_deberiaDevolverPersistenciaDelSetUp() {
        
        IPersistencia<?> persistenciaObtenida = this.controladorBajoPrueba.getPersistencia();

        assertSame("getPersistencia() no devolvió la instancia del setup",this.persistenciaMock,persistenciaObtenida);
    }
	@Test
    public void setVista_deberiaActualizarVista() {

        IVista nuevaVistaMock = Mockito.mock(IVista.class);

        this.controladorBajoPrueba.setVista(nuevaVistaMock);
        
        assertSame("getVista() no devolvió el nuevo objeto vista mock",nuevaVistaMock,this.controladorBajoPrueba.getVista());
    }
	
	
}
