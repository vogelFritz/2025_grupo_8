package tpfinal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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
	public void test() {
		final Administrador adm = Administrador.getInstance();
		Assert.assertSame(adm.getNombreUsuario(), "admin");
		Assert.assertSame(adm.getPass(), "admin");
	}

}
