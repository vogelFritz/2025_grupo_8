package tpfinal.persistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistencia.PersistenciaBIN;

public class TestPersistenciaBIN {

	private PersistenciaBIN pbin;
	private File temp ;

	@Before
	public void setUp() throws Exception {
		this.pbin = new PersistenciaBIN();
	    this.temp = File.createTempFile("temp", ".bin");
	}

	@After
	public void tearDown() throws Exception {
		if (this.temp.exists()) {
			this.temp.delete();
		}
	}

	@Test
	public void testLecturaYEscritura() {
		try {
			this.pbin.abrirOutput(temp.getAbsolutePath());
			String objeto= "prueba de lectura y escritura";
			this.pbin.escribir(objeto);
			this.pbin.cerrarOutput();
			
			this.pbin.abrirInput(temp.getAbsolutePath());
			String leido= (String)pbin.leer();
			this.pbin.cerrarInput();
			assertEquals("lo escrito y lo leido son dos cosas distintas",objeto,leido);
		}
		catch(Exception e) {
			fail("no deberia lanzar exepcion");
		}
	}
	
	@Test
	public void testLectura_SinArchivo() {
		try {
			this.pbin.abrirInput("archivo_que_no_existe.bin");
			fail("deberia lanzar exepcion por archivo inexistente");
		}
		catch(IOException e) {
			assertTrue(e.getMessage(),true); //dudoso
			
		}
		catch(Exception e) {
			fail("deberia lanzar IOException");
		}
	}
	
	@Test
	public void testLectura_ArchivoNull() {
		try {
			this.pbin.abrirInput(null);
			fail("Deberia lanzar IOException por archivo nulo"); 
		}
		catch(IOException e) {
			assertTrue(e.getMessage(),true); 
			
		}
		catch(Exception e) {
			fail("deberia lanzar IOException");
		}
	}
	
	@Test
	public void testCerrarSinAbrir_Input() {
		try {
			this.pbin.cerrarInput();
			fail("Deberia lanzar excepcion al cerrar sin abrir");
		}
		catch(IOException e) {
			assertTrue("IOException al cerrar sin abrir",true);
		}
		catch(Exception e) {
			fail("Deberia lanzar IOException");
		}
	}
	
	@Test
	public void testCerrarSinAbrir_Output() {
		try {
			this.pbin.cerrarOutput();
			fail("Deberia lanzar excepcion al cerrar sin abrir");
		}
		catch(IOException e) {
			assertTrue("IOException al cerrar sin abrir",true);
		}
		catch(Exception e) {
			fail("Deberia lanzar IOException");
		}
	}
	@Test
	public void testAbrirInput_NombreVacio() {
	    try {
	        pbin.abrirInput("");
	        fail("Se esperaba IOException por nombre vacío.");
	    } catch (IOException e) {
	    } catch (Exception e) {
	        fail("Se esperaba IOException pero se lanzó: " + e.getClass());
	    }
	}

	@Test
	public void testAbrirInput_ArchivoIlegible() throws Exception {
	    // Creamos archivo y quitamos permisos de lectura
	    File ilegible = File.createTempFile("ile", ".bin");
	    ilegible.setReadable(false);

	    try {
	        pbin.abrirInput(ilegible.getAbsolutePath());
	        fail("Se esperaba IOException por archivo sin permisos de lectura.");
	    } catch (IOException e) {
	    } finally {
	        ilegible.setReadable(true);
	        ilegible.delete();
	    }
	}


}
