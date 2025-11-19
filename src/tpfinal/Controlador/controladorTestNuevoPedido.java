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
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;
import vista.IOptionPane;
import vista.IVista;
@SuppressWarnings("deprecation")
public class controladorTestNuevoPedido {
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
			auto1 = new Auto("AUTO123", 4, false);
			Auto auto2 = new Auto("AUTO234", 4, true); 
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
	private void cargarEscenarioSinPedidos() {
		Chofer chofer1 = null;
		Vehiculo auto1 = null;
		try {
			Empresa.getInstance().agregarCliente("clienteLibre", "pass", "Cliente Libre");
		}
		catch(Exception e) {
		}
		try {
			chofer1 = new ChoferPermanente("222", "Chofer Perm", 2010, 0);
		    Empresa.getInstance().agregarChofer(chofer1);
		}
		catch(Exception e) {
		}
		try {
			auto1 = new Auto("AUTO123", 4, true); // Auto común
		    Empresa.getInstance().agregarVehiculo(auto1);
		}
		catch(Exception e) {
		}
	}
	//NUEVO PEDIDO
	@Test
    public void nuevoPedido_testeoComponentes() {
        cargarEscenarioSinPedidos();
        
        String usuario = "clienteLibre";
        String pass = "pass";
        
        try {
            Empresa.getInstance().login(usuario, pass);
        } catch (Exception e) {
            fail("Falló la preparación del test (login): " + e.getMessage());
        }
        
        when(this.vistaMock.getCantidadPax()).thenReturn(2);
        when(this.vistaMock.isPedidoConMascota()).thenReturn(true);
        when(this.vistaMock.isPedidoConBaul()).thenReturn(false);
        when(this.vistaMock.getCantKm()).thenReturn(10);
        when(this.vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
        
        
        this.controladorBajoPrueba.nuevoPedido();
        
        Cliente cliente = Empresa.getInstance().getClientes().get(usuario);
        Pedido pedido=Empresa.getInstance().getPedidoDeCliente(cliente);
        
        Assert.assertEquals(2,pedido.getCantidadPasajeros());
        Assert.assertEquals(true,pedido.isMascota());
        Assert.assertEquals(false,pedido.isBaul());
        Assert.assertEquals(10,pedido.getKm());
        Assert.assertEquals(Constantes.ZONA_STANDARD,pedido.getZona());
        
        verify(this.vistaMock).actualizar(); 
        verify(optionPaneMock, never()).ShowMessage(anyString());
        assertEquals(1, Empresa.getInstance().getPedidos().size());
    }
    //Sin vehiculo para pedido
    @Test
    public void nuevoPedido_noHayVehiculo() {
        cargarEscenarioCompleto();
        
        String usuario = "clienteLibre";
        String pass = "pass";
        
        try {
            Empresa.getInstance().login(usuario, pass);
        } catch (Exception e) {
            fail("Falló la preparación del test (login): " + e.getMessage());
        }
        
        when(this.vistaMock.isPedidoConMascota()).thenReturn(false);
        when(this.vistaMock.isPedidoConBaul()).thenReturn(false);
        when(this.vistaMock.getCantKm()).thenReturn(10);
        when(this.vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
        when(this.vistaMock.getCantidadPax()).thenReturn(5);

        this.controladorBajoPrueba.nuevoPedido();
        
        verify(optionPaneMock).ShowMessage(Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor());
        
        assertEquals(1, Empresa.getInstance().getPedidos().size());
    }
    //Cliente con pedido pendiente
    @Test
    public void nuevoPedido_clienteConPedido() {
        cargarEscenarioCompleto();
        
        String usuario = "clientePedido";
        String pass = "pass";
        
        try {
            Empresa.getInstance().login(usuario, pass);
        } catch (Exception e) {
            fail("Falló la preparación del test (login): " + e.getMessage());
        }
        
        when(this.vistaMock.getCantidadPax()).thenReturn(2);
        when(this.vistaMock.isPedidoConMascota()).thenReturn(true);
        when(this.vistaMock.isPedidoConBaul()).thenReturn(false);
        when(this.vistaMock.getCantKm()).thenReturn(10);
        when(this.vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
        
        this.controladorBajoPrueba.nuevoPedido();
        verify(optionPaneMock).ShowMessage(Mensajes.CLIENTE_CON_PEDIDO_PENDIENTE.getValor());
        assertEquals(1, Empresa.getInstance().getPedidos().size());
    }
    //Cliente con viaje pendiente
    @Test
    public void nuevoPedido_clienteConViaje() {
                
        cargarEscenarioCompleto();

        try {
            Empresa.getInstance().login("clienteViaje", "pass");
        } catch (Exception e) {
            fail("Falló el login del 'clienteViaje': " + e.getMessage());
        }
        
        when(this.vistaMock.getCantidadPax()).thenReturn(1);
        when(this.vistaMock.isPedidoConMascota()).thenReturn(false);
        when(this.vistaMock.isPedidoConBaul()).thenReturn(false);
        when(this.vistaMock.getCantKm()).thenReturn(5);
        when(this.vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
        
        this.controladorBajoPrueba.nuevoPedido();
       
        verify(optionPaneMock).ShowMessage(Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor());

        verify(this.vistaMock, never()).actualizar();
    }

}
