package tpfinal.vista;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Robot;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controlador.Controlador;
import excepciones.SinVehiculoParaPedidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;
import vista.Ventana;

public class PanelClienteTest2 {
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
			prepararEscenarioConChoferesYVehiculos();
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
	@Test
	public void testPedidoCantPasajerosVacia() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			robot.delay(1000);
			loguearse(ventana,robot);
			prepararEscenarioConChoferesYVehiculos();
			tipearEnComp(ventana,robot,Constantes.CANT_KM, "1");
			clickearComp(ventana,robot, Constantes.ZONA_STANDARD);
			clickearComp(ventana,robot, Constantes.NUEVO_PEDIDO);
			final JButton nuevoPedidoBtn = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);
			assertTrue(!nuevoPedidoBtn.isEnabled());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testPedidoCantKMVacia() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			robot.delay(1000);
			loguearse(ventana,robot);
			prepararEscenarioConChoferesYVehiculos();
			tipearEnComp(ventana,robot,Constantes.CANT_PAX, "1");
			clickearComp(ventana,robot, Constantes.ZONA_STANDARD);
			clickearComp(ventana,robot, Constantes.NUEVO_PEDIDO);
			final JButton nuevoPedidoBtn = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);
			assertTrue(!nuevoPedidoBtn.isEnabled());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testPedidoCompletoYViajeCalificadoConExito() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			robot.delay(1000);
			loguearse(ventana,robot);
			prepararEscenarioConChoferesYVehiculos();
			tipearEnComp(ventana,robot,Constantes.CANT_PAX, "1");
			tipearEnComp(ventana,robot,Constantes.CANT_KM, "1");
			clickearComp(ventana,robot, Constantes.ZONA_STANDARD);
			clickearComp(ventana,robot, Constantes.NUEVO_PEDIDO);
			final JTextArea pedidosPanel = (JTextArea)TestUtils.getComponentForName(ventana, Constantes.PEDIDO_O_VIAJE_ACTUAL);
			assertTrue(!pedidosPanel.getText().isEmpty());
			final Cliente cliente = emp.getClientes().get("test");
			emp.crearViaje(emp.getPedidoDeCliente(cliente), emp.getChoferes().get("test"), emp.getVehiculos().get("test"));
			ventana.actualizar();
			tipearEnComp(ventana, robot, Constantes.CALIFICACION_DE_VIAJE, "5");
			clickearComp(ventana, robot, Constantes.CALIFICAR_PAGAR);
			final JTextArea historialViajes = (JTextArea)TestUtils.getComponentForName(ventana, Constantes.LISTA_VIAJES_CLIENTE);
			assertTrue(!historialViajes.getText().isEmpty());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testBtnCalificarDeshabilitadoCuandoNoHayViajes() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			robot.delay(1000);
			loguearse(ventana,robot);
			final JTextArea pedidosPanel = (JTextArea) TestUtils.getComponentForName(ventana, Constantes.PEDIDO_O_VIAJE_ACTUAL);
			assertTrue(pedidosPanel.getText().isEmpty());
			final JTextArea calificacionInput = (JTextArea) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_DE_VIAJE);
			assertTrue(!calificacionInput.isEnabled());
			final JButton pagarBtn = (JButton) TestUtils.getComponentForName(ventana, Constantes.CALIFICAR_PAGAR);
			assertTrue(!pagarBtn.isEnabled());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testPedidoCompletoSinExito() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			final FalsoOptionPane falsoOptionPane = new FalsoOptionPane();
			ventana.setOptionPane(falsoOptionPane);
			controlador.setVista(ventana);
			robot.delay(1000);
			loguearse(ventana,robot);
			prepararEscenarioSinChoferesNiVehiculos();
			tipearEnComp(ventana,robot,Constantes.CANT_PAX, "1");
			tipearEnComp(ventana,robot,Constantes.CANT_KM, "1");
			clickearComp(ventana,robot, Constantes.ZONA_STANDARD);
			clickearComp(ventana,robot, Constantes.NUEVO_PEDIDO);
			final JTextArea pedidosPanel = (JTextArea)TestUtils.getComponentForName(ventana, Constantes.PEDIDO_O_VIAJE_ACTUAL);
			assertTrue(pedidosPanel.getText().isEmpty());
			final SinVehiculoParaPedidoException errorEsperado = new SinVehiculoParaPedidoException(new Pedido(new Cliente("test", "test", "test"), 1, false, false, 1, Constantes.ZONA_STANDARD));
			assertTrue(falsoOptionPane.getMensaje() == errorEsperado.getMessage());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testFormularioPedidoDeshabilitadoCuandoHayUnoPendiente() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			robot.delay(1000);
			loguearse(ventana,robot);
			prepararEscenarioConChoferesYVehiculos();
			tipearEnComp(ventana,robot,Constantes.CANT_PAX, "1");
			tipearEnComp(ventana,robot,Constantes.CANT_KM, "1");
			clickearComp(ventana,robot, Constantes.ZONA_STANDARD);
			clickearComp(ventana,robot, Constantes.NUEVO_PEDIDO);
			final JTextArea pedidosPanel = (JTextArea)TestUtils.getComponentForName(ventana, Constantes.PEDIDO_O_VIAJE_ACTUAL);
			assertTrue(!pedidosPanel.getText().isEmpty());
			final JButton nuevoPedidoBtn = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);
			assertTrue(!nuevoPedidoBtn.isEnabled());
		} catch(Exception e) {
			fail("No debería lanzar excepción: " + e.getMessage());
		}
	}
	@Test
	public void testFormularioPedidoDeshabilitadoCuandoHayUnViajePendiente() {
		try {
			final Robot robot = new Robot();
			final Controlador controlador = new Controlador();
			final Ventana ventana = new Ventana();
			controlador.setVista(ventana);
			robot.delay(1000);
			loguearse(ventana,robot);
			prepararEscenarioConChoferesYVehiculos();
			tipearEnComp(ventana,robot,Constantes.CANT_PAX, "1");
			tipearEnComp(ventana,robot,Constantes.CANT_KM, "1");
			clickearComp(ventana,robot, Constantes.ZONA_STANDARD);
			clickearComp(ventana,robot, Constantes.NUEVO_PEDIDO);
			final JTextArea pedidosPanel = (JTextArea)TestUtils.getComponentForName(ventana, Constantes.PEDIDO_O_VIAJE_ACTUAL);
			assertTrue(!pedidosPanel.getText().isEmpty());
			final Cliente cliente = emp.getClientes().get("test");
			emp.crearViaje(emp.getPedidoDeCliente(cliente), emp.getChoferes().get("test"), emp.getVehiculos().get("test"));
			ventana.actualizar();
			final JButton nuevoPedidoBtn = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);
			assertTrue(!nuevoPedidoBtn.isEnabled());
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
	private static void prepararEscenarioConChoferesYVehiculos() {
		final HashMap<String, Chofer> choferes = new HashMap<>();
		final HashMap<String, Vehiculo> vehiculos = new HashMap<>();
		choferes.put("test", new ChoferPermanente("test", "test", 2000, 1));
		vehiculos.put("test", new Auto("test", 4, true));
		emp.setChoferes(choferes);
		emp.setVehiculos(vehiculos);
	}
	private static void prepararEscenarioSinChoferesNiVehiculos() {
		emp.setChoferes(new HashMap<>());
		emp.setVehiculos(new HashMap<>());
	}
}
