package tpfinal.modeloNegocio;

import static org.junit.Assert.*;

import modeloDatos.Cliente;
import modeloNegocio.Empresa;

import org.junit.Before;
import org.junit.Test;

public class EmpresaTestLogout {

    private Empresa empresa;

    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
        empresa.setUsuarioLogeado(null); // reinicio estado entre pruebas
    }

    /**
     * E1 – Sesión activa → logout() deja usuarioLogeado en null
     * CE: C1
     */
    @Test
    public void testLogout_E1_UsuarioLogueado() {
        Cliente c = new Cliente("user1", "pass", "Nombre");
        empresa.setUsuarioLogeado(c);

        empresa.logout();

        assertNull("logout() debería dejar usuarioLogeado en null", empresa.getUsuarioLogeado());
    }

    /**
     * E2 – Sesión inactiva → logout() no rompe y mantiene el null
     * CE: C2
     */
    @Test
    public void testLogout_E2_SinUsuarioLogueado() {
        empresa.setUsuarioLogeado(null);

        empresa.logout();

        assertNull("logout() no debe arrojar error y debe dejar usuarioLogeado en null", empresa.getUsuarioLogeado());
    }
}
