package tpfinal.vista;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Robot;

import javax.swing.JButton;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controlador.Controlador;
import excepciones.UsuarioNoExisteException;
import util.Constantes;
import util.Mensajes;
import vista.Ventana;

public class LoginTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoginAdminSuccess() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			robot.delay(1000);
			final Component nombreUsuarioInput = TestUtils.getComponentForName(ventana,Constantes.NOMBRE_USUARIO);
			TestUtils.clickComponent(nombreUsuarioInput, robot);
			TestUtils.tipeaTexto("admin", robot);
			final Component passwordInput = TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
			TestUtils.clickComponent(passwordInput, robot);
			TestUtils.tipeaTexto("admin", robot);
			final Component loginBtn = TestUtils.getComponentForName(ventana, Constantes.LOGIN);
			TestUtils.clickComponent(loginBtn, robot);
			final Component cerrarSesionAdmin = TestUtils.getComponentForName(ventana, Constantes.CERRAR_SESION_ADMIN);
			assertTrue(cerrarSesionAdmin != null);
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testLoginAdminCamposVacios() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			robot.delay(1000);
			final JButton loginBtn = (JButton)TestUtils.getComponentForName(ventana, Constantes.LOGIN);
			assertTrue(!loginBtn.isEnabled());
			TestUtils.clickComponent(loginBtn, robot);
			final Component cerrarSesionAdmin = TestUtils.getComponentForName(ventana, Constantes.CERRAR_SESION_ADMIN);
			assertTrue(cerrarSesionAdmin == null);
			assertTrue(TestUtils.getComponentForName(ventana, Constantes.LOGIN) != null);
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testLoginAdminPasswordVacia() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			robot.delay(1000);
			final Component nombreUsuarioInput = TestUtils.getComponentForName(ventana,Constantes.NOMBRE_USUARIO);
			TestUtils.clickComponent(nombreUsuarioInput, robot);
			TestUtils.tipeaTexto("admin", robot);
			final JButton loginBtn = (JButton)TestUtils.getComponentForName(ventana, Constantes.LOGIN);
			assertTrue(!loginBtn.isEnabled());
			TestUtils.clickComponent(loginBtn, robot);
			final Component cerrarSesionAdmin = TestUtils.getComponentForName(ventana, Constantes.CERRAR_SESION_ADMIN);
			assertTrue(cerrarSesionAdmin == null);
			assertTrue(TestUtils.getComponentForName(ventana, Constantes.LOGIN) != null);
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testLoginAdminNombreUsuarioVacio() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			robot.delay(1000);
			final Component passwordInput = TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
			TestUtils.clickComponent(passwordInput, robot);
			TestUtils.tipeaTexto("admin", robot);
			final JButton loginBtn = (JButton)TestUtils.getComponentForName(ventana, Constantes.LOGIN);
			assertTrue(!loginBtn.isEnabled());
			TestUtils.clickComponent(loginBtn, robot);
			final Component cerrarSesionAdmin = TestUtils.getComponentForName(ventana, Constantes.CERRAR_SESION_ADMIN);
			assertTrue(cerrarSesionAdmin == null);
			assertTrue(TestUtils.getComponentForName(ventana, Constantes.LOGIN) != null);
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testLoginUsuarioNoExiste() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			final FalsoOptionPane falsoOptionPane = new FalsoOptionPane();
			ventana.setOptionPane(falsoOptionPane);
			controlador.setVista(ventana);
			robot.delay(1000);
			final Component nombreUsuarioInput = TestUtils.getComponentForName(ventana,Constantes.NOMBRE_USUARIO);
			TestUtils.clickComponent(nombreUsuarioInput, robot);
			TestUtils.tipeaTexto("test", robot);
			final Component passwordInput = TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
			TestUtils.clickComponent(passwordInput, robot);
			TestUtils.tipeaTexto("test", robot);
			final Component loginBtn = TestUtils.getComponentForName(ventana, Constantes.LOGIN);
			TestUtils.clickComponent(loginBtn, robot);
			final Component cerrarSesionAdmin = TestUtils.getComponentForName(ventana, Constantes.CERRAR_SESION_ADMIN);
			assertTrue(cerrarSesionAdmin == null);
			assertTrue(ventana.getOptionPane() != null);
			final Exception errorEsperado = new UsuarioNoExisteException("test");
			assertTrue(falsoOptionPane.getMensaje() == errorEsperado.getMessage());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
}
