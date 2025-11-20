package tpfinal.modeloDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modeloDatos.Administrador;

public class AdministradorTest {

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
    public void testSingletonValuesAndIdentity() {
        // 1. Obtener primera instancia
        final Administrador adm1 = Administrador.getInstance();
        
        // 2. Verificación de estado (Valores iniciales correctos)
        assertEquals("El nombre de usuario debe ser 'admin'", "admin", adm1.getNombreUsuario());
        assertEquals("La contraseña debe ser 'admin'", "admin", adm1.getPass());
        
        // 3. Verificación de patrón Singleton (Unicidad de instancia)
        final Administrador adm2 = Administrador.getInstance();
        assertSame("Debe retornar exactamente la misma instancia en memoria", adm1, adm2);
    }

}