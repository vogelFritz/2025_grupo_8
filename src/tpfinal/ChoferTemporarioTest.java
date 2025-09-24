package tpfinal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;

public class ChoferTemporarioTest {

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
	public void testConstructor() {
		final ChoferTemporario chof = new ChoferTemporario("12345678", "Test");
		assertSame(chof.getDni(), "12345678");
		assertSame(chof.getNombre(), "Test");
	}
	@Test
	public void testGetSueldoBruto() {
		final ChoferTemporario chof = new ChoferTemporario("12345678", "Test");
		assertTrue(chof.getSueldoBruto() == Chofer.getSueldoBasico());
	}
}
