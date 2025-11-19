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
import modeloDatos.Auto;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Mensajes;
import vista.IOptionPane;
import vista.IVista;

public class controladorTestNuevoVehiculo {

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
    public void nuevoVehiculo_AgregarMoto() {
        String patente = "MOTO123";
        
        when(this.vistaMock.getTipoVehiculo()).thenReturn("MOTO");
        when(this.vistaMock.getPatente()).thenReturn(patente);
        
        this.controladorBajoPrueba.nuevoVehiculo();
        
        verify(this.vistaMock).actualizar(); 

        verify(optionPaneMock, never()).ShowMessage(anyString());

        Vehiculo vehiculoAgregado = Empresa.getInstance().getVehiculos().get(patente);
        assertNotNull("El vehículo no se agregó", vehiculoAgregado);
        assertTrue("El vehículo no es de tipo Moto", vehiculoAgregado instanceof Moto);
        assertEquals("La patente no coincide",patente,vehiculoAgregado.getPatente());
	}
	
	@Test
    public void nuevoVehiculo_AgregarAuto() {
        String patente = "AUTO123";
        int plazas=4;
        boolean mascotas=true;
        when(this.vistaMock.getTipoVehiculo()).thenReturn("AUTO");
        when(this.vistaMock.getPatente()).thenReturn(patente);
        when(this.vistaMock.getPlazas()).thenReturn(plazas);
        when(this.vistaMock.isVehiculoAptoMascota()).thenReturn(mascotas);
        
        this.controladorBajoPrueba.nuevoVehiculo();
        
        verify(this.vistaMock).actualizar(); 

        verify(optionPaneMock, never()).ShowMessage(anyString());

        Vehiculo vehiculoAgregado = Empresa.getInstance().getVehiculos().get(patente);
	    assertNotNull("El vehículo no se agregó", vehiculoAgregado);
	    assertTrue("El vehículo no es de tipo Auto", vehiculoAgregado instanceof Auto);
        assertEquals("La patente no coincide",patente,vehiculoAgregado.getPatente());
	    assertEquals("Las plazas son incorrectas", plazas, ((Auto) vehiculoAgregado).getCantidadPlazas());
	    assertEquals("La condicion de mascotas no coincide",mascotas,((Auto) vehiculoAgregado).isMascota());
	}
	@Test
    public void nuevoVehiculo_AgregarCombi() {
        String patente = "COMBI123";
        int plazas=7;
        boolean mascotas=true;
        when(this.vistaMock.getTipoVehiculo()).thenReturn("COMBI");
        when(this.vistaMock.getPatente()).thenReturn(patente);
        when(this.vistaMock.getPlazas()).thenReturn(plazas);
        when(this.vistaMock.isVehiculoAptoMascota()).thenReturn(mascotas);
        
        this.controladorBajoPrueba.nuevoVehiculo();
        
        verify(this.vistaMock).actualizar(); 

        verify(optionPaneMock, never()).ShowMessage(anyString());

        Vehiculo vehiculoAgregado = Empresa.getInstance().getVehiculos().get(patente);
	    assertNotNull("El vehículo no se agregó", vehiculoAgregado);
	    assertTrue("El vehículo no es de tipo Auto", vehiculoAgregado instanceof Combi);
        assertEquals("La patente no coincide",patente,vehiculoAgregado.getPatente());
	    assertEquals("Las plazas son incorrectas", plazas, ((Combi) vehiculoAgregado).getCantidadPlazas());
	    assertEquals("La condicion de mascotas no coincide",mascotas,((Combi) vehiculoAgregado).isMascota());
	}
	@Test
    public void nuevoVehiculo_PatenteEstaRepetida() {
        String patenteRepetida = "REP123";
        
        try {
            Empresa.getInstance().agregarVehiculo(new Moto(patenteRepetida));
        } catch (Exception e) {
            fail("Falla en el setup: " + e.getMessage());
        }

        when(this.vistaMock.getTipoVehiculo()).thenReturn("MOTO");
        when(this.vistaMock.getPatente()).thenReturn(patenteRepetida);
        
        this.controladorBajoPrueba.nuevoVehiculo();

        verify(optionPaneMock).ShowMessage(Mensajes.VEHICULO_YA_REGISTRADO.getValor());
        
        verify(this.vistaMock, never()).actualizar();
    }

}
