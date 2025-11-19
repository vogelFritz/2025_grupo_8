package tpfinal.Controlador;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import controlador.Controlador;
import excepciones.UsuarioYaExisteException;
import junit.framework.Assert;
import modeloDatos.Cliente;
import modeloNegocio.Empresa;
import util.Mensajes;
import vista.IOptionPane;
import vista.IVista;

@SuppressWarnings("deprecation")
public class controladorTestAgregarCliente {
	private Controlador controladorReal;
    private Controlador controladorBajoPrueba; // El "spy"
    private IVista vistaMock; // El único mock que necesitamos
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
	//AGREGAR CLIENTE
		//AgregarCliente contraseñas distintas  
	    @Test
	    public void registrar_deberiaMostrarError_PasswordsNoCoinciden() {
	        when(this.vistaMock.getRegNombreReal()).thenReturn("Usuario de Prueba");
	        when(this.vistaMock.getRegUsserName()).thenReturn("usuarioPrueba");
	        when(this.vistaMock.getRegPassword()).thenReturn("pass123");
	        when(this.vistaMock.getRegConfirmPassword()).thenReturn("pass456");
	        
	        this.controladorBajoPrueba.registrar();
	                
	        verify(optionPaneMock).ShowMessage(Mensajes.PASS_NO_COINCIDE.getValor());
	 
	    }
	    //AgregarCliente exitoso en lista precargada
	    @Test
	    public void registrar_deberiaAgregarClienteNuevo_OtroClienteYaExiste() {
	        
	        String usuarioViejo = "usuarioViejo";
	        String passVieja = "pass123";

	        String usuarioNuevo = "usuarioNuevo";
	        String passNueva = "pass456";

	        try {
	            Empresa.getInstance().agregarCliente(usuarioViejo, passVieja, "Nombre Viejo");
	        } catch (Exception e) {
	        }

	        when(this.vistaMock.getRegNombreReal()).thenReturn("Usuario Nuevo");
	        when(this.vistaMock.getRegUsserName()).thenReturn(usuarioNuevo);
	        when(this.vistaMock.getRegPassword()).thenReturn(passNueva);
	        when(this.vistaMock.getRegConfirmPassword()).thenReturn(passNueva); 
	        
	        this.controladorBajoPrueba.registrar();
	                
	        verify(optionPaneMock, never()).ShowMessage(anyString());
	     
	        try {
	            Empresa.getInstance().login(usuarioNuevo, passNueva);
	        } catch (Exception e) {
	            fail("El login con el NUEVO usuario falló: " + e.getMessage());
	        }
	        assertNotNull(Empresa.getInstance().getUsuarioLogeado());
	        assertEquals("El usuario logueado no es el nuevo", 
	                     usuarioNuevo, 
	                     Empresa.getInstance().getUsuarioLogeado().getNombreUsuario());

	        Empresa.getInstance().logout();
	        try {
	            Empresa.getInstance().login(usuarioViejo, passVieja);
	        } catch (Exception e) {
	            fail("El login con el VIEJO usuario falló: " + e.getMessage());
	        }
	        assertNotNull(Empresa.getInstance().getUsuarioLogeado());
	        assertEquals("El usuario logueado no es el viejo", 
	                     usuarioViejo, 
	                     Empresa.getInstance().getUsuarioLogeado().getNombreUsuario());
	    }
	    // Error Usuario Existente
	    @Test
	    public void registrar_deberiaMostrarError_UsuarioYaExiste() {
	        String usuarioExistente = "usuarioExistente";
	        try {
	            Empresa.getInstance().agregarCliente(usuarioExistente, "pass1", "Nombre");
	        } catch (UsuarioYaExisteException e) {
	        }
	        when(this.vistaMock.getRegNombreReal()).thenReturn("Otro Nombre");
	        when(this.vistaMock.getRegUsserName()).thenReturn(usuarioExistente);
	        when(this.vistaMock.getRegPassword()).thenReturn("pass2");
	        when(this.vistaMock.getRegConfirmPassword()).thenReturn("pass2"); 
	        
	        this.controladorBajoPrueba.registrar(); 

	        verify(optionPaneMock).ShowMessage(Mensajes.USUARIO_REPETIDO.getValor());
	    }
		@Test
	    public void registrar_deberiaAgregarCliente_DatosSonValidos() {
	        String nuevoUsuario = "usuarioNuevo";
	        String nuevaPass = "passValida123";

	        when(this.vistaMock.getRegNombreReal()).thenReturn("Usuario Nuevo");
	        when(this.vistaMock.getRegUsserName()).thenReturn(nuevoUsuario);
	        when(this.vistaMock.getRegPassword()).thenReturn(nuevaPass);
	        when(this.vistaMock.getRegConfirmPassword()).thenReturn(nuevaPass); 
	        
	        this.controladorBajoPrueba.registrar();
	        verify(optionPaneMock, never()).ShowMessage(anyString());
	        Cliente cliente= Empresa.getInstance().getClientes().get(nuevoUsuario);
	        Assert.assertEquals(cliente.getNombreReal(),"Usuario Nuevo");
	        Assert.assertEquals(cliente.getNombreUsuario(),nuevoUsuario);
	        Assert.assertEquals(cliente.getPass(),nuevaPass);

	    }

}
