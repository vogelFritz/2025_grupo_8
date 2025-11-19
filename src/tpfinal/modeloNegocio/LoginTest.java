package tpfinal.modeloNegocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Usuario;
import modeloNegocio.Empresa;

public class LoginTest {
	
	private static Empresa emp; 

	@BeforeClass
	public static void setUp() {
		
		emp = Empresa.getInstance();	
		
		
		try {
			emp.agregarCliente("Fede", "1234", "Federico");
			
		}
		catch(Exception e) {
			fail("No deberia lanzar excepcion");
		}
		
		
		
	}
	
	@Test
	public void caso1() {
		
		try {
			Usuario u = emp.login("Fede", "1234");
			
			System.out.println(u.getNombreUsuario() + " " + u.getPass());
			
			assertEquals(u.getNombreUsuario(), "Fede");
			assertEquals(u.getPass(), "1234");
		}
		catch(Exception e) {}
		
	}
	
	@Test
	public void caso2() {
		
		try {
			Usuario u = emp.login("Lucas", "4321");
			
			fail("Deberia lanzar excepcion");
		}
		catch(UsuarioNoExisteException e) {
			System.out.println(e.getMessage());
			assertEquals(1, 1);
		}
		catch (Exception e) {}
		
	}
	
	@Test
	public void caso3() {
		
		try {
			Usuario u = emp.login("Fede", "4321");
			
			fail("Deberia lanzar excepcion");
		}
		catch(PasswordErroneaException e) {
			System.out.println(e.getMessage());
			assertEquals(1, 1);
		}
		catch (Exception e) {}
		
	}

}
