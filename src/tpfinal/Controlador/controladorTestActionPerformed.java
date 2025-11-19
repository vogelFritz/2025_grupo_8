package tpfinal.Controlador;


import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.event.ActionEvent;

import controlador.Controlador;
import vista.IVista;

public class controladorTestActionPerformed {
    private Controlador controladorReal;
    private Controlador controladorBajoPrueba;
    private IVista vistaMock;

	@Before
	public void setUp() throws Exception {
        this.vistaMock = Mockito.mock(IVista.class);

        this.controladorReal = new Controlador();
        
        this.controladorBajoPrueba = Mockito.spy(controladorReal);

        controladorBajoPrueba.setVista(vistaMock);        
	}
	@Test
    public void actionPerformed_deberiaLlamarLogin_cuandoElComandoEsLOGIN() {
        String comandoLogin = "LOGIN"; 
        ActionEvent evento = new ActionEvent(this, 1, comandoLogin);

        doNothing().when(this.controladorBajoPrueba).login();

        this.controladorBajoPrueba.actionPerformed(evento);

	     verify(this.controladorBajoPrueba).login(); 
    }
	@Test
    public void actionPerformed_deberiaLlamarRegistrar_cuandoComandoEsREGISTRAR() {
        String comando = "REG_BUTTON_REGISTRAR"; 
        ActionEvent evento = new ActionEvent(this, 1, comando);
        
        doNothing().when(this.controladorBajoPrueba).registrar();

        this.controladorBajoPrueba.actionPerformed(evento);

        verify(this.controladorBajoPrueba).registrar();
    }
	@Test
    public void actionPerformed_deberiaLlamarLogout_cuandoComandoEsCERRAR_SESION_CLIENTE() {
        String comando = "CERRAR_SESION_CLIENTE";
        ActionEvent evento = new ActionEvent(this, 1, comando);
        
        doNothing().when(this.controladorBajoPrueba).logout();

        this.controladorBajoPrueba.actionPerformed(evento);

        verify(this.controladorBajoPrueba).logout();
    }
	@Test
    public void actionPerformed_deberiaLlamarLogout_cuandoComandoEsCERRAR_SESION_ADMIN() {
        String comando = "CERRAR_SESION_ADMIN";
        ActionEvent evento = new ActionEvent(this, 1, comando);
        
        doNothing().when(this.controladorBajoPrueba).logout();

        this.controladorBajoPrueba.actionPerformed(evento);

        verify(this.controladorBajoPrueba).logout();
    }
	@Test
    public void actionPerformed_deberiaLlamarNuevoPedido_cuandoComandoEsNUEVO_PEDIDO() {
        String comando = "NUEVO_PEDIDO";
        ActionEvent evento = new ActionEvent(this, 1, comando);
        
        doNothing().when(this.controladorBajoPrueba).nuevoPedido();
        
        this.controladorBajoPrueba.actionPerformed(evento);

        verify(this.controladorBajoPrueba).nuevoPedido();
    }
	@Test
    public void actionPerformed_deberiaLlamarNuevoViaje_cuandoComandoEsNUEVO_VIAJE() {
        String comando = "NUEVO_VIAJE"; 
        ActionEvent evento = new ActionEvent(this, 1, comando);
        
        doNothing().when(this.controladorBajoPrueba).nuevoViaje();

        this.controladorBajoPrueba.actionPerformed(evento);

        verify(this.controladorBajoPrueba).nuevoViaje();
    }
	@Test
    public void actionPerformed_deberiaLlamarNuevoVehiculo_cuandoComandoEsNUEVO_VEHICULO() {
        String comando = "NUEVO_VEHICULO";
        ActionEvent evento = new ActionEvent(this, 1, comando);
        
        doNothing().when(this.controladorBajoPrueba).nuevoVehiculo();

        this.controladorBajoPrueba.actionPerformed(evento);

        verify(this.controladorBajoPrueba).nuevoVehiculo();
    }
	@Test
    public void actionPerformed_deberiaLlamarNuevoChofer_cuandoComandoEsNUEVO_CHOFER() {

        String comando = "NUEVO_CHOFER";
        ActionEvent evento = new ActionEvent(this, 1, comando);
        
        doNothing().when(this.controladorBajoPrueba).nuevoChofer();

        this.controladorBajoPrueba.actionPerformed(evento);

        verify(this.controladorBajoPrueba).nuevoChofer();
    }
	@Test
    public void actionPerformed_deberiaLlamarCalificarPagar_cuandoComandoEsCALIFICAR_PAGAR() {

        String comando = "CALIFICAR_PAGAR"; 
        ActionEvent evento = new ActionEvent(this, 1, comando);
        
        doNothing().when(this.controladorBajoPrueba).calificarPagar();

        this.controladorBajoPrueba.actionPerformed(evento);

        verify(this.controladorBajoPrueba).calificarPagar();
    }
	@Test
    public void actionPerformed_noDeberiaLlamarNada_cuandoComandoEsDesconocido() {
        String comando = "ESTO_NO_EXISTE";
        ActionEvent evento = new ActionEvent(this, 1, comando);
        
        this.controladorBajoPrueba.actionPerformed(evento);

        verify(this.controladorBajoPrueba, never()).login();
        verify(this.controladorBajoPrueba, never()).logout();
        verify(this.controladorBajoPrueba, never()).registrar();
        verify(this.controladorBajoPrueba, never()).nuevoPedido();
        verify(this.controladorBajoPrueba, never()).calificarPagar();
        verify(this.controladorBajoPrueba, never()).nuevoChofer();
        verify(this.controladorBajoPrueba, never()).nuevoVehiculo();
        verify(this.controladorBajoPrueba, never()).nuevoViaje();
    }
	
}
