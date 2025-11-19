package tpfinal.vista;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Robot;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;
import vista.Ventana;

public class PanelAdministradorTest2 {
    
    static final Empresa emp = Empresa.getInstance();
    
    // Declaramos los componentes como atributos de la clase para reutilizarlos
    private Robot robot;
    private Controlador controlador;
    private Ventana ventana;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        // 1. Limpiamos los datos de la Empresa Singleton antes de cada test
        emp.setChoferes(new HashMap<>());
        emp.setVehiculos(new HashMap<>());
        emp.setClientes(new HashMap<>());
        emp.setPedidos(new HashMap<>());
        emp.setViajesIniciados(new HashMap<>());
        
        // 2. Inicializamos el entorno gráfico y el robot una sola vez por test
        robot = new Robot();
        controlador = new Controlador();
        ventana = new Ventana();
        controlador.setVista(ventana);
        robot.delay(500); // Pequeña espera para asegurar que la ventana cargó
        
        // 3. Ejecutamos el Login de Admin automáticamente
        // Esto deja a "ventana" en el estado correcto (Panel Admin) para todos los tests
        loguearseComoAdmin(ventana, robot);
    }

    @After
    public void tearDown() throws Exception {
        // Opcional: Cerrar ventana o liberar recursos si fuera necesario
        // ventana.dispose(); 
    }

    // ========================================================================
    // SECCIÓN 1: TESTS DE REGISTRO DE CHOFERES
    // ========================================================================

    @Test
    public void testHabilitacionBotonChoferTemporario() {
        try {
            // Ya estamos logueados y en el panel admin.
            
            // 1. Acción: Seleccionar Radio Temporario
            clickearComp(ventana, robot, Constantes.TEMPORARIO);
            
            // 2. Acción: Llenar campos obligatorios
            tipearEnComp(ventana, robot, Constantes.DNI_CHOFER, "12345678");
            tipearEnComp(ventana, robot, Constantes.NOMBRE_CHOFER, "Juan");
            
            // 3. Verificación: El botón debe habilitarse
            final JButton nuevoChoferBtn = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);
            assertTrue("El botón 'Nuevo Chofer' debería habilitarse con DNI y Nombre", nuevoChoferBtn.isEnabled());
            
        } catch(Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    public void testHabilitacionBotonChoferPermanente() {
        try {
            // Seleccionamos Permanente
            clickearComp(ventana, robot, Constantes.PERMANENTE);
            
            final JButton nuevoChoferBtn = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);
            // Verificación negativa: Faltan datos, debe estar deshabilitado
            assertFalse("El botón no debería habilitarse solo con seleccionar el radio", nuevoChoferBtn.isEnabled());

            // Llenamos todos los campos requeridos
            tipearEnComp(ventana, robot, Constantes.DNI_CHOFER, "87654321");
            tipearEnComp(ventana, robot, Constantes.NOMBRE_CHOFER, "Maria");
            tipearEnComp(ventana, robot, Constantes.CH_CANT_HIJOS, "2");
            tipearEnComp(ventana, robot, Constantes.CH_ANIO, "2010");
            
            // Verificación positiva
            assertTrue("El botón debería habilitarse con todos los datos correctos", nuevoChoferBtn.isEnabled());
            
        } catch(Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    public void testRegistroChoferDuplicado() {
        try {
            // Inyectamos los datos directamente en el Singleton. La validación de duplicado se hace contra el modelo.
            HashMap<String, Chofer> choferes = new HashMap<>();
            choferes.put("12345678", new ChoferPermanente("12345678", "Pedro", 2000, 2));
            emp.setChoferes(choferes);
            
            // Mockeamos el OptionPane en la ventana YA abierta
            final FalsoOptionPane falsoOptionPane = new FalsoOptionPane();
            ventana.setOptionPane(falsoOptionPane);
            
            // Intentamos registrar el mismo DNI
            clickearComp(ventana, robot, Constantes.TEMPORARIO);
            tipearEnComp(ventana, robot, Constantes.DNI_CHOFER, "12345678"); // DNI DUPLICADO
            tipearEnComp(ventana, robot, Constantes.NOMBRE_CHOFER, "Impostor");
            
            clickearComp(ventana, robot, Constantes.NUEVO_CHOFER);
            
            // Verificamos que el mensaje del OptionPane sea el correcto
            assertEquals("Debería mostrar mensaje de chofer ya registrado", 
                         Mensajes.CHOFER_YA_REGISTRADO, falsoOptionPane.getMensaje());
            
        } catch(Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    // ========================================================================
    // SECCIÓN 2: TESTS DE REGISTRO DE VEHÍCULOS
    // ========================================================================

    @Test
    public void testHabilitacionBotonMoto() {
        try {
            clickearComp(ventana, robot, Constantes.MOTO);
            tipearEnComp(ventana, robot, Constantes.PATENTE, "AAA111");
            
            final JButton nuevoVehiculoBtn = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_VEHICULO);
            assertTrue("Boton Moto debería habilitarse con patente", nuevoVehiculoBtn.isEnabled());
            
        } catch(Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    public void testHabilitacionBotonAutoInvalido() {
        try {
            clickearComp(ventana, robot, Constantes.AUTO);
            tipearEnComp(ventana, robot, Constantes.PATENTE, "BBB222");
            // Plazas inválidas para Auto (límite es 4). Ponemos 5.
            tipearEnComp(ventana, robot, Constantes.CANTIDAD_PLAZAS, "5");
            
            final JButton nuevoVehiculoBtn = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_VEHICULO);
            assertFalse("No debería habilitarse si las plazas exceden el límite permitido para Auto", nuevoVehiculoBtn.isEnabled());
            
        } catch(Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * NUEVO TEST: Verifica que al registrar un vehículo, aparezca en la lista "Vehiculos Existentes"
     */
    @Test
    public void testRegistroVehiculoExitosoEnLista() {
        try {
            // 1. Llenar datos de Moto
            clickearComp(ventana, robot, Constantes.MOTO);
            tipearEnComp(ventana, robot, Constantes.PATENTE, "MOTO999");
            
            // 2. Registrar
            clickearComp(ventana, robot, Constantes.NUEVO_VEHICULO);
            
            // 3. Verificar que la lista de vehículos totales ahora tiene 1 elemento
            JList listaVehiculos = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_VEHICULOS_TOTALES);
            
            // Pequeña espera para refresco de UI
            robot.delay(200);
            
            assertEquals("La lista de vehículos debería tener 1 elemento tras el registro", 
                         1, listaVehiculos.getModel().getSize());
            
            // Verificación opcional del modelo
            Vehiculo v = (Vehiculo) listaVehiculos.getModel().getElementAt(0);
            assertEquals("MOTO999", v.getPatente());
            
        } catch(Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    // ========================================================================
    // SECCIÓN 3: TESTS DE VISUALIZACIÓN (JLISTS)
    // ========================================================================

    @Test
    public void testVisualizacionInfoChofer() {
        try {
            HashMap<String, Chofer> choferes = new HashMap<>();
            choferes.put("111", new ChoferPermanente("111", "ChoferTest", 2010, 2));
            emp.setChoferes(choferes);
            
            // Forzamos refresco visual interactuando con la lista para que detecte el modelo
            // Al haber solo un chofer, clickear la lista en el centro lo seleccionará automáticamente
            clickearComp(ventana, robot, Constantes.LISTA_CHOFERES_TOTALES);
            
            // Verificar que los campos de texto se rellenaron con la info del modelo
            JTextField sueldoField = (JTextField) TestUtils.getComponentForName(ventana, Constantes.SUELDO_DE_CHOFER);
            JTextField calificacionField = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_CHOFER);
            
            assertFalse("El sueldo debería mostrarse", sueldoField.getText().isEmpty());
            assertFalse("La calificación debería mostrarse", calificacionField.getText().isEmpty());
            
        } catch(Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    // ========================================================================
    // SECCIÓN 4: TESTS DE GESTIÓN DE PEDIDOS (FLUJO COMPLEJO)
    // ========================================================================

    @Test
    public void testCreacionNuevoViajeHabilitado() {
        try {
            // --- PREPARACIÓN DEL ESCENARIO DE NEGOCIO ---
            HashMap<String, Cliente> clientes = new HashMap<>();
            Cliente cliente = new Cliente("cli", "pass", "NombreCliente");
            clientes.put("cli", cliente);
            emp.setClientes(clientes);
            
            HashMap<String, Chofer> choferes = new HashMap<>();
            choferes.put("111", new ChoferPermanente("111", "ChoferTest", 2010, 0));
            emp.setChoferes(choferes);
            
            HashMap<String, Vehiculo> vehiculos = new HashMap<>();
            vehiculos.put("PAT1", new Auto("PAT1", 4, false)); 
            emp.setVehiculos(vehiculos);
            
            Pedido pedido = new Pedido(cliente, 1, false, false, 10, Constantes.ZONA_STANDARD);
            emp.agregarPedido(pedido);
            
            // Esperamos un poco para asegurar que la UI refleje los cambios del modelo
            robot.delay(500);
            
            // --- INTERACCIÓN CON LA VISTA ---
            
            // 1. Seleccionar el Pedido Pendiente
            clickearComp(ventana, robot, Constantes.LISTA_PEDIDOS_PENDIENTES);
            
            // 2. Seleccionar un Vehículo de los que aparecieron disponibles
            clickearComp(ventana, robot, Constantes.LISTA_VEHICULOS_DISPONIBLES);
            
            // 3. Seleccionar un Chofer Libre
            clickearComp(ventana, robot, Constantes.LISTA_CHOFERES_LIBRES);
            
            // 4. Verificar que el botón se habilita
            final JButton nuevoViajeBtn = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_VIAJE);
            assertTrue("El botón Nuevo Viaje debería habilitarse al seleccionar los 3 elementos", nuevoViajeBtn.isEnabled());
            
            // 5. Crear el viaje
            clickearComp(ventana, robot, Constantes.NUEVO_VIAJE);
            
            // 6. Verificar post-condición: Las listas deben vaciarse (pedido procesado)
            JList listaPedidos = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_PEDIDOS_PENDIENTES);
            
            // Nota: getSize() del modelo es la forma segura de verificar si está vacío
            assertEquals("La lista de pedidos debería quedar vacía tras crear el viaje", 0, listaPedidos.getModel().getSize());
            
        } catch(Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    // ========================================================================
    // SECCIÓN 5: TESTS DE NAVEGACIÓN / LOGOUT
    // ========================================================================

    @Test
    public void testCerrarSesion() {
        try {
            // Acción: Click en "Cerrar Sesion"
            clickearComp(ventana, robot, Constantes.CERRAR_SESION_ADMIN);
            
            robot.delay(500); // Esperar transición
            
            // Verificación: Deberíamos ver componentes de Login de nuevo
            Component loginBtn = TestUtils.getComponentForName(ventana, Constantes.LOGIN);
            assertNotNull("Al cerrar sesión, debería aparecer el botón de Login", loginBtn);
            assertTrue("El botón de Login debería estar visible", loginBtn.isVisible());
            
            // Y no deberíamos ver componentes del Admin
            Component adminBtn = TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);
            assertNull("El panel de admin no debería ser visible", adminBtn);
            
        } catch(Exception e) {
            fail("Excepción al cerrar sesión: " + e.getMessage());
        }
    }

    // ========================================================================
    // MÉTODOS HELPER (UTILIDADES PARA ESTOS TESTS)
    // ========================================================================

    /**
     * Realiza el flujo completo de login con admin/admin y espera a que cargue el panel.
     */
    private static void loguearseComoAdmin(Ventana ventana, Robot robot) {
        // 1. Llenar Usuario
        final Component nombreUsuarioInput = TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        if (nombreUsuarioInput == null) fail("No se encuentra el campo Usuario en la pantalla inicial. ¿Falló la carga de la ventana?");
        TestUtils.clickComponent(nombreUsuarioInput, robot);
        TestUtils.tipeaTexto("admin", robot);
        
        // 2. Llenar Password
        final Component passwordInput = TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        TestUtils.clickComponent(passwordInput, robot);
        TestUtils.tipeaTexto("admin", robot);
        
        // 3. Click Login
        final Component loginBtn = TestUtils.getComponentForName(ventana, Constantes.LOGIN);
        TestUtils.clickComponent(loginBtn, robot);
        
        // 4. Espera crítica para la transición de pantalla
        robot.delay(1000); 
        
        // 5. Validación de seguridad: ¿Estamos realmente en el panel admin?
        Component checkAdminPanel = TestUtils.getComponentForName(ventana, Constantes.CERRAR_SESION_ADMIN);
        assertNotNull("El login de admin falló o el panel no cargó correctamente. No se encontró el botón de cerrar sesión.", checkAdminPanel);
    }

    private static void tipearEnComp(Ventana ventana, Robot robot, String compNombre, String texto) {
        final Component comp = TestUtils.getComponentForName(ventana, compNombre);
        if (comp != null) {
            TestUtils.clickComponent(comp, robot);
            // Es buena práctica borrar el campo antes de escribir por si tiene basura
            TestUtils.borraJTextField((JTextField) comp, robot); 
            TestUtils.tipeaTexto(texto, robot);
        } else {
            fail("No se pudo encontrar el componente para escribir: " + compNombre);
        }
    }

    private static void clickearComp(Ventana ventana, Robot robot, String compNombre) {
        final Component comp = TestUtils.getComponentForName(ventana, compNombre);
        if (comp != null) {
            TestUtils.clickComponent(comp, robot);
        } else {
            fail("No se pudo encontrar el componente para clickear: " + compNombre);
        }
    }
}