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
import junit.framework.Assert;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;
import vista.IOptionPane;
import vista.IVista;

@SuppressWarnings("deprecation")
public class controladorTestCalificaPagar {
	private Controlador controladorReal;
	private Controlador controladorBajoPrueba; 
	private IVista vistaMock;
	private IOptionPane optionPaneMock;
	
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
	private void cargarEscenarioCompleto() {
        
		Chofer chofer1 = null;
		Vehiculo auto1 = null;
		Cliente clientePedido = null;
		Cliente clienteViaje = null;
		
		try {
			Empresa.getInstance().agregarCliente("clientePedido", "pass", "Cliente Con Pedido");
			Empresa.getInstance().agregarCliente("clienteViaje", "pass", "Cliente Con Viaje");
			Empresa.getInstance().agregarCliente("clienteLibre", "pass", "Cliente Libre");
		}
		catch(Exception e) {
		}
		try {
			chofer1 = new ChoferTemporario("111", "Chofer Temp");
			Chofer chofer2 = new ChoferPermanente("222", "Chofer Perm", 2010, 0);
		    Empresa.getInstance().agregarChofer(chofer1);
		    Empresa.getInstance().agregarChofer(chofer2);
		}
		catch(Exception e) {
		}
		try {
			auto1 = new Auto("AUTO123", 4, false); // Auto común
			Auto auto2 = new Auto("AUTO234", 4, true); // Auto común
			Vehiculo moto = new Moto("MOTO123");
		    Empresa.getInstance().agregarVehiculo(auto1);
		    Empresa.getInstance().agregarVehiculo(auto2);
		    Empresa.getInstance().agregarVehiculo(moto);
		}
		catch(Exception e) {
		}
		try {
			clientePedido = (Cliente) Empresa.getInstance().login("clientePedido", "pass");		
			Pedido pedido = new Pedido(clientePedido, 2, false, true, 10,Constantes.ZONA_STANDARD); 

			Empresa.getInstance().agregarPedido(pedido);
			
		}
		catch(Exception e) {
		}
		try {
		clienteViaje = (Cliente) Empresa.getInstance().login("clienteViaje", "pass");
		Pedido pedidoViaje = new Pedido(clienteViaje, 1, false, false, 5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedidoViaje);

		Empresa.getInstance().logout();
		Empresa.getInstance().login("admin", "admin");
		Empresa.getInstance().crearViaje(pedidoViaje, chofer1, auto1);
		
		Empresa.getInstance().logout();
		}
		catch(Exception e) {
		}
	}
	
	
    //CALIFICA PAGAR VIAJE
    //Caso de exito cliente con viaje
    @Test
    public void calificarPagar_clienteConViaje() {
        cargarEscenarioCompleto();
        
        try {
            Empresa.getInstance().login("clienteViaje", "pass");
        } catch (Exception e) {
            fail("Falló el login del 'clienteViaje': " + e.getMessage());
        }
        
        when(this.vistaMock.getCalificacion()).thenReturn(5);
        
        this.controladorBajoPrueba.calificarPagar();

        verify(this.vistaMock).getCalificacion();
        Viaje viaje=Empresa.getInstance().getViajesTerminados().get(0);
        Assert.assertEquals(viaje.getCalificacion(),5);
        verify(this.vistaMock).actualizar(); 

        verify(optionPaneMock, never()).ShowMessage(anyString());

        assertEquals("El viaje no se finalizó", 0, Empresa.getInstance().getViajesIniciados().size());
    }
    //Caso de error con cliente no en viaje
    @Test
    public void calificarPagar_clienteSinViaje() {                
        cargarEscenarioCompleto();
        try {
            Empresa.getInstance().login("clienteLibre", "pass");
        } catch (Exception e) {
            fail("Falló el login del 'clienteLibre': " + e.getMessage());
        }

        when(this.vistaMock.getCalificacion()).thenReturn(5);
                
        this.controladorBajoPrueba.calificarPagar();

        verify(optionPaneMock).ShowMessage(Mensajes.CLIENTE_SIN_VIAJE_PENDIENTE.getValor());
        
        verify(this.vistaMock, never()).actualizar();
    }
    
}
