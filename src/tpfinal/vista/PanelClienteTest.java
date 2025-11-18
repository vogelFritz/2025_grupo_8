package tpfinal.vista;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Robot;
import java.util.HashMap;

import javax.swing.JTextArea;
import javax.swing.JTextPane;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Cliente;
import modeloNegocio.Empresa;
import util.Constantes;
import vista.Ventana;

public class PanelClienteTest {
	static final Empresa emp = Empresa.getInstance();

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
	public void testPedidoCompletoConExito() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			robot.delay(1000);
			loguearse(ventana,robot);
			tipearEnComp(ventana,robot,Constantes.CANT_PAX, "1");
			tipearEnComp(ventana,robot,Constantes.CANT_KM, "1");
			clickearComp(ventana,robot, Constantes.ZONA_STANDARD);
			clickearComp(ventana,robot, Constantes.NUEVO_PEDIDO);
			final JTextArea pedidosPanel = (JTextArea)TestUtils.getComponentForName(ventana, Constantes.PEDIDO_O_VIAJE_ACTUAL);
			assertTrue(!pedidosPanel.getText().isEmpty());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	private static void loguearse(Ventana ventana, Robot robot) {
		try {
			final HashMap<String,Cliente> clientes = new HashMap<>();
			clientes.put("test", new Cliente("test", "test", "test"));
			emp.setClientes(clientes);
			final Component nombreUsuarioInput = TestUtils.getComponentForName(ventana,Constantes.NOMBRE_USUARIO);
			TestUtils.clickComponent(nombreUsuarioInput, robot);
			TestUtils.tipeaTexto("test", robot);
			final Component passwordInput = TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
			TestUtils.clickComponent(passwordInput, robot);
			TestUtils.tipeaTexto("test", robot);
			final Component loginBtn = TestUtils.getComponentForName(ventana, Constantes.LOGIN);
			TestUtils.clickComponent(loginBtn, robot);
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	private static void tipearEnComp(Ventana ventana, Robot robot, String compNombre, String texto) {
		final Component comp = TestUtils.getComponentForName(ventana, compNombre);
		TestUtils.clickComponent(comp, robot);
		TestUtils.tipeaTexto(texto, robot);
	} 
	private static void clickearComp(Ventana ventana, Robot robot, String compNombre) {
		final Component comp = TestUtils.getComponentForName(ventana, compNombre);
		TestUtils.clickComponent(comp, robot);
	} 
}
