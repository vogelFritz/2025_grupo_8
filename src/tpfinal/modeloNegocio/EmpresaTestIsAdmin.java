package tpfinal.modeloNegocio;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import modeloNegocio.Empresa;
import modeloDatos.Usuario;
import modeloDatos.Administrador;
import modeloDatos.Cliente;

public class EmpresaTestIsAdmin {

    private Empresa empresa;
    private Usuario admin;
    private Usuario clientePrueba;

    @Before
    public void setUp() {
        empresa = Empresa.getInstance();

        admin = Administrador.getInstance(); 
        
        clientePrueba = new Cliente("clienteUser", "clientePass", "Cliente de Prueba");

        this.setupEscenario1();
    }

    /**
     * El usuario logueado se establece en null.
     */
    private void setupEscenario1() {
        empresa.setUsuarioLogeado(null);
    }

    /**
     * Un Cliente se establece como usuario logueado.
     */
    private void setupEscenario2() {
        empresa.setUsuarioLogeado(clientePrueba);
    }

    /**
     * El Administrador se establece como usuario logueado.
     */
    private void setupEscenario3() {
        empresa.setUsuarioLogeado(admin);
    }

    /**
     * Prueba (1 - Escenario 1): Verifica que isAdmin() retorna false
     * cuando no hay ningún usuario logueado.
     * Cubre: CE 1.
     */
    @Test
    public void testIsAdminUsuarioNull() {
        boolean resultado = empresa.isAdmin();
        
        assertFalse("isAdmin() debe retornar false cuando no hay usuario logueado (null)", resultado);
    }

    /**
     * Prueba (2 - Escenario 2): Verifica que isAdmin() retorna false
     * cuando el usuario logueado es un Cliente.
     * Cubre: CE 2.
     */
    @Test
    public void testIsAdminUsuarioCliente() {
        this.setupEscenario2();
        
        boolean resultado = empresa.isAdmin();
        
        assertFalse("isAdmin() debe retornar false cuando el usuario logueado es un Cliente", resultado);
    }

    /**
     * Prueba (3 - Escenario 3): Verifica que isAdmin() retorna true
     * cuando el usuario logueado es el Administrador.
     * Cubre: CE 3.
     */
    @Test
    public void testIsAdminUsuarioAdmin() {
        this.setupEscenario3();
        
        boolean resultado = empresa.isAdmin();
        
        assertTrue("isAdmin() debe retornar true cuando el usuario logueado es un Administrador", resultado);
    }
}
