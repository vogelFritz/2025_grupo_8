package tpfinal.Controlador;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.mockito.Mockito;
import controlador.Controlador;
import excepciones.*;
import vista.IVista;
import vista.IOptionPane;
import modeloNegocio.Empresa;
import util.Mensajes;
public class controladorTestLoginLogout {
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
        
	}
	//LOGIN
	//Login Error Lista Vacia
	@Test
    public void login_deberiaFallar_cuandoLaListaDeUsuariosEstaVacia() {
        String usuario = "usuarioInexistente";
        String pass = "passCualquiera";
        
        when(this.vistaMock.getUsserName()).thenReturn(usuario);
        when(this.vistaMock.getPassword()).thenReturn(pass);
        
        this.controladorBajoPrueba.login();
        
        verify(optionPaneMock).ShowMessage(Mensajes.USUARIO_DESCONOCIDO.getValor());

        verify(this.vistaMock, never()).logearUsuario();

        assertNull(Empresa.getInstance().getUsuarioLogeado());
       
    }
	//Login con lista no vacia,usuario inexistente
	@Test
    public void login_deberiaFallar_UsuarioDesconocido() {
        String usuario = "usuarioExistente";
        String usuarioInexistente = "usuarioInexistente";
        String pass = "passCualquiera";

        try {
        	Empresa.getInstance().agregarCliente(usuario, pass, pass);
        } catch (UsuarioYaExisteException e) {
        }

        when(this.vistaMock.getUsserName()).thenReturn(usuarioInexistente);
        when(this.vistaMock.getPassword()).thenReturn(pass);
            
        this.controladorBajoPrueba.login();
        
        verify(optionPaneMock).ShowMessage(Mensajes.USUARIO_DESCONOCIDO.getValor());

        verify(this.vistaMock, never()).logearUsuario();

        assertNull(Empresa.getInstance().getUsuarioLogeado());
       
    }
	//Login con constraseña invalida
	@Test
    public void login_deberiaFallar_contraseñaInvalida() {
        String usuario = "usuarioExistente";
        String pass = "passCualquiera";
        String passErronea = "passErronea";

        try {
        	Empresa.getInstance().agregarCliente(usuario, pass, pass);
        } catch (UsuarioYaExisteException e) {
        }
        
        when(this.vistaMock.getUsserName()).thenReturn(usuario);
        when(this.vistaMock.getPassword()).thenReturn(passErronea);
        
        this.controladorBajoPrueba.login();
 
        verify(optionPaneMock).ShowMessage(Mensajes.PASS_ERRONEO.getValor());

        verify(this.vistaMock, never()).logearUsuario();

        assertNull(Empresa.getInstance().getUsuarioLogeado());
       
    }
	//Login exitoso
	@Test
    public void login_Exitoso() {
        String usuario = "usuarioExistente";
        String pass = "passCualquiera";

       try {
        	Empresa.getInstance().agregarCliente(usuario, pass, pass);
        } catch (UsuarioYaExisteException e) {
        }
        
        when(this.vistaMock.getUsserName()).thenReturn(usuario);
        when(this.vistaMock.getPassword()).thenReturn(pass);	        
        
        this.controladorBajoPrueba.login();
        
        verify(optionPaneMock, never()).ShowMessage(anyString());
        
        assertNotNull(Empresa.getInstance().getUsuarioLogeado());
        assertTrue(Empresa.getInstance().getUsuarioLogeado().getNombreUsuario()==usuario);
       
    }
	
	
    //LOGOUT
    //logout exitoso con usuario logeado
    @Test
    public void logout_deberiaDesloguearUsuarioYEscribirEstado() {        
        // usuario logueado
        String usuario = "usuarioParaLogout";
        String pass = "pass123";
        try {
            Empresa.getInstance().agregarCliente(usuario, pass, "Test Logout");
            Empresa.getInstance().login(usuario, pass);
        } catch (Exception e) {
            fail("La preparación del test falló: " + e.getMessage());
        }
        
        doNothing().when(this.controladorBajoPrueba).escribir();

        this.controladorBajoPrueba.logout();

        assertNull("El usuario no se deslogueó, getUsuarioLogeado() no es nulo", 
                   Empresa.getInstance().getUsuarioLogeado());

        verify(this.controladorBajoPrueba, times(1)).escribir();
    }
    
}
