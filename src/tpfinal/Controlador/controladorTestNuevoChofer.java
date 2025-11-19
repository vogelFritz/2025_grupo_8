package tpfinal.Controlador;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import controlador.Controlador;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloNegocio.Empresa;
import util.Mensajes;
import vista.IOptionPane;
import vista.IVista;

public class controladorTestNuevoChofer {

	private IVista vistaMock;
	private IOptionPane optionPaneMock;
	private Controlador controladorReal;
	private Controlador controladorBajoPrueba;
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
    public void nuevoChofer_AgregarChoferTemporario() {
        
        String dni = "123456";
        String nombre = "Chofer Temporal";
        
        when(this.vistaMock.getTipoChofer()).thenReturn("TEMPORARIO");
        when(this.vistaMock.getNombreChofer()).thenReturn(nombre);
        when(this.vistaMock.getDNIChofer()).thenReturn(dni);
        
        this.controladorBajoPrueba.nuevoChofer();
        
        verify(this.vistaMock).actualizar(); 

        verify(optionPaneMock, never()).ShowMessage(anyString());

        Chofer choferAgregado = Empresa.getInstance().getChoferes().get(dni);
        assertNotNull("El chofer no se agregó a la empresa", choferAgregado);
        assertTrue("El chofer no es de tipo Temporario", choferAgregado instanceof ChoferTemporario);
        assertEquals("El nombre del chofer es incorrecto", nombre, choferAgregado.getNombre());
        assertEquals("El DNI del chofer es incorrecto", dni, choferAgregado.getDni());
    }
	@Test
    public void nuevoChofer_AgregarChoferPermanente() {
        String dni = "789012";
        String nombre = "Chofer Permanente";
        
        when(this.vistaMock.getTipoChofer()).thenReturn("PERMANENTE");
        when(this.vistaMock.getNombreChofer()).thenReturn(nombre);
        when(this.vistaMock.getDNIChofer()).thenReturn(dni);
        when(this.vistaMock.getAnioChofer()).thenReturn(2010);
        when(this.vistaMock.getHijosChofer()).thenReturn(2);
        
        this.controladorBajoPrueba.nuevoChofer();
        
        verify(this.vistaMock).actualizar(); 

        verify(optionPaneMock, never()).ShowMessage(anyString());

        Chofer choferAgregado = Empresa.getInstance().getChoferes().get(dni);
        assertNotNull("El chofer no se agregó a la empresa", choferAgregado);
        assertTrue("El chofer no es de tipo Permanente", choferAgregado instanceof ChoferPermanente);
        assertEquals("El nombre del chofer es incorrecto", nombre, choferAgregado.getNombre());
        assertEquals("El DNI del chofer es incorrecto", dni, choferAgregado.getDni());
        assertEquals("La cantidad de hijos es incorrecta", 2, ((ChoferPermanente) choferAgregado).getCantidadHijos());
        assertEquals("El anio de ingreso es incorrecto",2010,((ChoferPermanente) choferAgregado).getAnioIngreso());
    }
	@Test
    public void nuevoChofer_DNIEstaRepetido() {
        
        String dniRepetido = "111111";
        
        try {
            Empresa.getInstance().agregarChofer(new ChoferTemporario(dniRepetido, "Primer Chofer"));
        } catch (Exception e) {
            fail("Falla en el setup: " + e.getMessage());
        }

        when(this.vistaMock.getTipoChofer()).thenReturn("TEMPORARIO");
        when(this.vistaMock.getNombreChofer()).thenReturn("Segundo Chofer");
        when(this.vistaMock.getDNIChofer()).thenReturn(dniRepetido);
        
        this.controladorBajoPrueba.nuevoChofer();

        verify(optionPaneMock).ShowMessage(Mensajes.CHOFER_YA_REGISTRADO.getValor());

        verify(this.vistaMock, never()).actualizar();
    }

}
