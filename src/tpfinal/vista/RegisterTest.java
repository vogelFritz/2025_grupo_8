package tpfinal.vista;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Robot;
import java.util.HashMap;

import javax.swing.JButton;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controlador.Controlador;
import excepciones.UsuarioYaExisteException;
import modeloDatos.Cliente;
import modeloNegocio.Empresa;
import util.Constantes;
import vista.Ventana;

public class RegisterTest {
	final Empresa emp = Empresa.getInstance();
	final Cliente cliente1 = new Cliente("test", "test", "test");
	final HashMap<String,Cliente> unClienteRegistrado = new HashMap<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		unClienteRegistrado.put(cliente1.getNombreUsuario(), cliente1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegistrarseConExito() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			emp.setClientes(new HashMap<>());
			robot.delay(1000);
			Component regBtn = TestUtils.getComponentForName(ventana,Constantes.REGISTRAR);
			TestUtils.clickComponent(regBtn, robot);
			tipearEnComp(ventana, robot, Constantes.REG_USSER_NAME, "test");
			tipearEnComp(ventana, robot, Constantes.REG_PASSWORD, "test");
			tipearEnComp(ventana, robot, Constantes.REG_CONFIRM_PASSWORD, "test");
			tipearEnComp(ventana, robot, Constantes.REG_REAL_NAME, "test");
			final Component confirmarBtn = TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
			TestUtils.clickComponent(confirmarBtn, robot);
			regBtn = TestUtils.getComponentForName(ventana,Constantes.REGISTRAR);
			assertTrue(regBtn != null);
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testRegistrarseUsuarioYaExiste() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			final FalsoOptionPane falsoOptionPane = new FalsoOptionPane();
			ventana.setOptionPane(falsoOptionPane);
			controlador.setVista(ventana);
			emp.setClientes(unClienteRegistrado);
			robot.delay(1000);
			Component regBtn = TestUtils.getComponentForName(ventana,Constantes.REGISTRAR);
			TestUtils.clickComponent(regBtn, robot);
			tipearEnComp(ventana, robot, Constantes.REG_USSER_NAME, "test");
			tipearEnComp(ventana, robot, Constantes.REG_PASSWORD, "test");
			tipearEnComp(ventana, robot, Constantes.REG_CONFIRM_PASSWORD, "test");
			tipearEnComp(ventana, robot, Constantes.REG_REAL_NAME, "test");
			final Component confirmarBtn = TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
			TestUtils.clickComponent(confirmarBtn, robot);
			regBtn = TestUtils.getComponentForName(ventana,Constantes.REGISTRAR);
			final UsuarioYaExisteException errorEsperado = new UsuarioYaExisteException("test");
			assertTrue(falsoOptionPane.getMensaje() == errorEsperado.getMessage());
			assertTrue(regBtn != null);
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testRegistrarseNombreUsuarioVacio() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			final FalsoOptionPane falsoOptionPane = new FalsoOptionPane();
			ventana.setOptionPane(falsoOptionPane);
			controlador.setVista(ventana);
			emp.setClientes(unClienteRegistrado);
			robot.delay(1000);
			Component regBtn = TestUtils.getComponentForName(ventana,Constantes.REGISTRAR);
			TestUtils.clickComponent(regBtn, robot);
			tipearEnComp(ventana, robot, Constantes.REG_PASSWORD, "test");
			tipearEnComp(ventana, robot, Constantes.REG_CONFIRM_PASSWORD, "test");
			tipearEnComp(ventana, robot, Constantes.REG_REAL_NAME, "test");
			final JButton confirmarBtn = (JButton)TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
			assertTrue(!confirmarBtn.isEnabled());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testRegistrarseContraseñaVacia() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			final FalsoOptionPane falsoOptionPane = new FalsoOptionPane();
			ventana.setOptionPane(falsoOptionPane);
			controlador.setVista(ventana);
			emp.setClientes(unClienteRegistrado);
			robot.delay(1000);
			Component regBtn = TestUtils.getComponentForName(ventana,Constantes.REGISTRAR);
			TestUtils.clickComponent(regBtn, robot);
			tipearEnComp(ventana, robot, Constantes.REG_USSER_NAME, "test");
			tipearEnComp(ventana, robot, Constantes.REG_CONFIRM_PASSWORD, "test");
			tipearEnComp(ventana, robot, Constantes.REG_REAL_NAME, "test");
			final JButton confirmarBtn = (JButton)TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
			assertTrue(!confirmarBtn.isEnabled());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testRegistrarseRepetirContraseñaVacio() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			final FalsoOptionPane falsoOptionPane = new FalsoOptionPane();
			ventana.setOptionPane(falsoOptionPane);
			controlador.setVista(ventana);
			emp.setClientes(unClienteRegistrado);
			robot.delay(1000);
			Component regBtn = TestUtils.getComponentForName(ventana,Constantes.REGISTRAR);
			TestUtils.clickComponent(regBtn, robot);
			tipearEnComp(ventana, robot, Constantes.REG_USSER_NAME, "test");
			tipearEnComp(ventana, robot, Constantes.REG_PASSWORD, "test");
			tipearEnComp(ventana, robot, Constantes.REG_REAL_NAME, "test");
			final JButton confirmarBtn = (JButton)TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
			assertTrue(!confirmarBtn.isEnabled());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testRegistrarseNombreRealVacio() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			final FalsoOptionPane falsoOptionPane = new FalsoOptionPane();
			ventana.setOptionPane(falsoOptionPane);
			controlador.setVista(ventana);
			emp.setClientes(unClienteRegistrado);
			robot.delay(1000);
			Component regBtn = TestUtils.getComponentForName(ventana,Constantes.REGISTRAR);
			TestUtils.clickComponent(regBtn, robot);
			tipearEnComp(ventana, robot, Constantes.REG_USSER_NAME, "test");
			tipearEnComp(ventana, robot, Constantes.REG_PASSWORD, "test");
			tipearEnComp(ventana, robot, Constantes.REG_CONFIRM_PASSWORD, "test");
			final JButton confirmarBtn = (JButton)TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
			assertTrue(!confirmarBtn.isEnabled());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	private static void tipearEnComp(Ventana ventana, Robot robot, String compNombre, String texto) {
		final Component comp = TestUtils.getComponentForName(ventana, compNombre);
		TestUtils.clickComponent(comp, robot);
		TestUtils.tipeaTexto(texto, robot);
	} 
}
