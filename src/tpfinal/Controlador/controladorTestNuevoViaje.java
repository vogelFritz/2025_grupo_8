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
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;
import vista.IOptionPane;
import vista.IVista;

public class controladorTestNuevoViaje {
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
	
	private void cargarEscenarioConUnChofer() {
        Chofer chofer = null;
		Vehiculo auto = null;
		try {
			Empresa.getInstance().agregarCliente("clientePedido", "pass", "Cliente Pedido");		
			chofer = new ChoferPermanente("111", "Chofer Perm", 2010, 0);
			auto = new Auto("AUTO123", 4, false); // Auto común
		    Empresa.getInstance().agregarChofer(chofer);
		    Empresa.getInstance().agregarVehiculo(auto);
			Cliente clientePedido = (Cliente) Empresa.getInstance().login("clientePedido", "pass");		
		    Empresa.getInstance().setUsuarioLogeado(clientePedido);
		    
			Pedido pedido = new Pedido(clientePedido, 2, false, true, 10,Constantes.ZONA_STANDARD); 
			Empresa.getInstance().agregarPedido(pedido);
		}
		catch(Exception e) {
		}
	}
	
    @Test
    public void nuevoViaje_deberiaCrearViaje() {
        cargarEscenarioConUnChofer();
        Cliente cliente = Empresa.getInstance().getClientes().get("clientePedido");
        Pedido pedido = Empresa.getInstance().getPedidoDeCliente(cliente);
        Chofer chofer = Empresa.getInstance().getChoferes().get("111"); 
        ArrayList<Vehiculo> lista = Empresa.getInstance().getVehiculosDesocupados();
        Vehiculo vehiculo = lista.get(0);
        

        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculo);
        
        this.controladorBajoPrueba.nuevoViaje();

        assertEquals("El viaje no se creó", 1, Empresa.getInstance().getViajesIniciados().size());

        verify(this.vistaMock).actualizar(); 

        verify(optionPaneMock, never()).ShowMessage(anyString());
        
        assertEquals("El chofer no coincide",chofer,Empresa.getInstance().getViajesIniciados().get(cliente).getChofer());
        assertEquals("El vehiculo no coincide",vehiculo,Empresa.getInstance().getViajesIniciados().get(cliente).getVehiculo());

    }
    @Test
    public void nuevoViaje_PedidoEsInexistente() {
        cargarEscenarioConUnChofer();
        Chofer chofer = Empresa.getInstance().getChoferes().get("111");
        Vehiculo vehiculo = Empresa.getInstance().getVehiculosDesocupados().get(0);
        
        Pedido pedidoFalso = Mockito.mock(Pedido.class);

        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedidoFalso);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculo);
        
        this.controladorBajoPrueba.nuevoViaje();

    
        verify(optionPaneMock).ShowMessage(Mensajes.PEDIDO_INEXISTENTE.getValor());

        verify(this.vistaMock, never()).actualizar();

        assertEquals("Se creó un viaje incorrectamente",0,Empresa.getInstance().getViajesIniciados().size());
    }
    @Test
    public void nuevoViaje_ChoferNoEstaDisponible() {
        cargarEscenarioConUnChofer();
        Cliente cliente = Empresa.getInstance().getClientes().get("clientePedido");
        Pedido pedido = Empresa.getInstance().getPedidoDeCliente(cliente);
        Vehiculo vehiculo = Empresa.getInstance().getVehiculosDesocupados().get(0);
        Chofer chofer=Empresa.getInstance().getChoferes().get("111");
        Empresa.getInstance().setChoferesDesocupados(new ArrayList<>());        
        
        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculo);
        
        this.controladorBajoPrueba.nuevoViaje();

        verify(optionPaneMock).ShowMessage(Mensajes.CHOFER_NO_DISPONIBLE.getValor());

        verify(this.vistaMock, never()).actualizar();

        assertEquals("Se creó un viaje incorrectamente",0,Empresa.getInstance().getViajesIniciados().size());
   
    }
    @Test
    public void nuevoViaje_VehiculoNoEstaDisponible() {
        cargarEscenarioConUnChofer();
        Cliente cliente = Empresa.getInstance().getClientes().get("clientePedido");
        Pedido pedido = Empresa.getInstance().getPedidoDeCliente(cliente);
        Chofer chofer = Empresa.getInstance().getChoferes().get("111");
        
        Vehiculo vehiculoFalso = new Auto("FALSO123", 4, false);

        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculoFalso);
        
        this.controladorBajoPrueba.nuevoViaje();

        verify(optionPaneMock).ShowMessage(Mensajes.VEHICULO_NO_DISPONIBLE.getValor());

        verify(this.vistaMock, never()).actualizar();

        assertEquals("Se creó un viaje incorrectamente",0,Empresa.getInstance().getViajesIniciados().size());
    }
    @Test
    public void nuevoViaje_VehiculoNoEsValidoParaPedido() {
        cargarEscenarioConUnChofer(); 
        Cliente cliente = Empresa.getInstance().getClientes().get("clientePedido");
        Pedido pedido = Empresa.getInstance().getPedidoDeCliente(cliente);
        
        Chofer chofer = Empresa.getInstance().getChoferes().get("111");
        
        Empresa.getInstance().setVehiculosDesocupados(new ArrayList<>());        
        //Agregamos un auto que no cumple
        Vehiculo vehiculoNoCumple = new Auto("NOCUMPLE123", 1, true);
    	try {
			Empresa.getInstance().agregarVehiculo(vehiculoNoCumple);
		} catch (VehiculoRepetidoException e) {
			fail();
		}

        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculoNoCumple);
        
        this.controladorBajoPrueba.nuevoViaje();
        verify(optionPaneMock).ShowMessage(Mensajes.VEHICULO_NO_VALIDO.getValor());

        verify(this.vistaMock, never()).actualizar();

        assertEquals("Se creó un viaje incorrectamente",0,Empresa.getInstance().getViajesIniciados().size());
    }
    @Test
    public void nuevoViaje_ClienteYaTieneViajePendiente() {
        cargarEscenarioConUnChofer();
        
        Cliente cliente = Empresa.getInstance().getClientes().get("clientePedido");
        Pedido pedido1 = Empresa.getInstance().getPedidoDeCliente(cliente);
        Chofer chofer1 = Empresa.getInstance().getChoferes().get("111");
        Vehiculo vehiculo1 = Empresa.getInstance().getVehiculosDesocupados().get(0);
        
        try {
        Empresa.getInstance().login("clientePedido", "pass");
        Empresa.getInstance().crearViaje(pedido1, chofer1, vehiculo1);
        }
        catch(Exception e) {
        	fail();
        }
        Pedido pedido2=new Pedido(cliente, 1, false, false, 1, Constantes.ZONA_STANDARD);
        Chofer chofer2=new ChoferTemporario("456", "chofer2");
        Vehiculo vehiculo2 = new Auto("4567", 1, true);
        HashMap<Cliente, Pedido> nuevosPedidos = new HashMap<>();        
        nuevosPedidos.put(cliente, pedido2);
        Empresa.getInstance().setPedidos(nuevosPedidos);        
        
        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedido2);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer2);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculo2);
        
        this.controladorBajoPrueba.nuevoViaje();

        assertEquals("Se creó un segundo viaje incorrectamente", 1,Empresa.getInstance().getViajesIniciados().size());
        verify(optionPaneMock).ShowMessage(Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor());

        verify(this.vistaMock, never()).actualizar();
   
    }

}
