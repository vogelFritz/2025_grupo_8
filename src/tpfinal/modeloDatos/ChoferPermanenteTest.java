package tpfinal.modeloDatos;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;

public class ChoferPermanenteTest {

	private ChoferPermanente choferPrueba;
	private static final double SUELDO_BASICO_PRUEBA = 1000.0;
	private static final double DELTA = 0.01;
	private static final int ANIO_ACTUAL = 2025;
	private static final int ANTIGUEDAD = 5;
	private static final int HIJOS = 2;


	@Before
	public void setUp() throws Exception {
		Chofer.setSueldoBasico(SUELDO_BASICO_PRUEBA); 
		choferPrueba = new ChoferPermanente("111222", "Juan Perez", ANIO_ACTUAL - ANTIGUEDAD, HIJOS);
	}

	@Test
	public void testConstructorConGetters() {
		assertEquals("El DNI no coincide", "111222", choferPrueba.getDni());
		assertEquals("El nombre no coincide", "Juan Perez", choferPrueba.getNombre());
		assertEquals("El año de ingreso no coincide", ANIO_ACTUAL - ANTIGUEDAD, choferPrueba.getAnioIngreso());
		assertEquals("La cantidad de hijos no coincide", HIJOS, choferPrueba.getCantidadHijos());
	}
	@Test 
	public void testGetAnioIngreso() {
		assertEquals("El año de ingreso no coincide", ANIO_ACTUAL - ANTIGUEDAD, choferPrueba.getAnioIngreso());
	}
	@Test 
	public void testGetAnioIngresoHistorico() {
		ChoferPermanente choferHistorico = new ChoferPermanente("111222", "Juan Perez", 1900, HIJOS);

		assertEquals("El año de ingreso no coincide", 1900, choferHistorico.getAnioIngreso());
	}
	@Test 
	public void testGetAnioIngresoFutura() {
		ChoferPermanente choferFuturo = new ChoferPermanente("111222", "Juan Perez", 3000, HIJOS);
		assertEquals("El año de ingreso no coincide", 3000, choferFuturo.getAnioIngreso());
	}
	@Test
	public void testGetAntiguedad() {
		assertEquals("La antigüedad calculada es incorrecta", ANTIGUEDAD, choferPrueba.getAntiguedad());
	}
	
	@Test
	public void testGetSueldoBrutoBase() {
		double bonusXant;
		int antiguedad = choferPrueba.getAntiguedad();
		if (antiguedad > 20)
			bonusXant = 1;
		else
			bonusXant = antiguedad * 0.05;
		double bonusXHij = 0.07 * choferPrueba.getCantidadHijos();
		double esperado = SUELDO_BASICO_PRUEBA * (1.0 + bonusXant + bonusXHij);
		assertEquals("Sueldo bruto incorrecto", esperado, choferPrueba.getSueldoBruto(), DELTA);
	}
	
	@Test
	public void testGetSueldoBrutoAntIgual20() {
		ChoferPermanente chofer20 = new ChoferPermanente("999", "Veterano", ANIO_ACTUAL - 20, 1);
		double bonusXant;
		bonusXant = 1;
		double bonusXHij = 0.07 * chofer20.getCantidadHijos();
		double esperado = SUELDO_BASICO_PRUEBA * (1.0 + bonusXant + bonusXHij); 
		assertEquals("Sueldo bruto con >20 años", esperado, chofer20.getSueldoBruto(), DELTA);
	}
	
	@Test
	public void testGetSueldoBrutoAntmay20() {
		ChoferPermanente choferAntiguo = new ChoferPermanente("999", "Veterano", ANIO_ACTUAL - 21, 1);
		double bonusXant;
		int antiguedad = choferAntiguo.getAntiguedad();
		if (antiguedad > 20)
			bonusXant = 1;
		else
			bonusXant = antiguedad * 0.05;
		double bonusXHij = 0.07 * choferAntiguo.getCantidadHijos();
		double esperado = SUELDO_BASICO_PRUEBA * (1.0 + bonusXant + bonusXHij); 
		assertEquals("Sueldo bruto con >20 años", esperado, choferAntiguo.getSueldoBruto(), DELTA);
	}
	@Test
	public void testGetSueldoBrutoHijosCero() {
		ChoferPermanente choferSinHijos = new ChoferPermanente("999", "Veterano", ANIO_ACTUAL - ANTIGUEDAD, 0);
		double bonusXant;
		int antiguedad = choferSinHijos.getAntiguedad();
		if (antiguedad > 20)
			bonusXant = 1;
		else
			bonusXant = antiguedad * 0.05;
		double esperado = SUELDO_BASICO_PRUEBA * (1.0 + bonusXant + 0);
		assertEquals("Sueldo bruto sin ningun hijos ", esperado, choferSinHijos.getSueldoBruto(), DELTA);
	}
	@Test
	public void testGetAntiguedad1900() {
		ChoferPermanente choferAntiguo = new ChoferPermanente("999", "Veterano", 1900, 1);
		assertEquals("La antigüedad calculada es incorrecta", ANIO_ACTUAL - 1900, choferAntiguo.getAntiguedad());
	}
	@Test
	public void testGetAntiguedad3000() {
		ChoferPermanente choferFuturo = new ChoferPermanente("999", "Futuro", 3000, 1);
		assertEquals("La antigüedad calculada es incorrecta", ANIO_ACTUAL - 3000, choferFuturo.getAntiguedad());
	}

	@Test
	public void testGetCantidadHijos() {
		ChoferPermanente choferSinHijos = new ChoferPermanente("999", "Veterano", ANIO_ACTUAL - ANTIGUEDAD, 0);
		assertEquals("La cantidad de hijos no coincide", 0, choferSinHijos.getCantidadHijos());
	}
	
	@Test
    public void testSetCantidadHijos() {
		ChoferPermanente choferJunior;
		choferJunior = new ChoferPermanente("11111111", "Pepe Junior", 2025, 0);
        assertEquals(0, choferJunior.getCantidadHijos());
        
        choferJunior.setCantidadHijos(3);
        
        assertEquals("El setter no actualizó la cantidad de hijos", 
                     3, choferJunior.getCantidadHijos());
    }
	@Test
	public void testGetSueldoNeto() {
		double sueldoBruto = choferPrueba.getSueldoBruto();
		double esperadoNeto = sueldoBruto * 0.86;
		assertEquals("Sueldo neto incorrecto", esperadoNeto, choferPrueba.getSueldoNeto(), DELTA);
	}
	@Test
	public void testGetDNI() {
		assertEquals("El DNI no coincide", "111222", choferPrueba.getDni());
	}
	@Test
	public void testGetNombre() {
		assertEquals("El nombre no coincide", "Juan Perez", choferPrueba.getNombre());
	}
	@Test
	public void testGetSueldoBasico(){
		assertEquals("El Sueldo Basico no coincide", SUELDO_BASICO_PRUEBA, Chofer.getSueldoBasico(),DELTA);
	}
}
