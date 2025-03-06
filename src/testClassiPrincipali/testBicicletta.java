package testClassiPrincipali;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import ClassiPrincipali.BiciClassica;
import ClassiPrincipali.BiciConSeggiolino;
import ClassiPrincipali.BiciElettrica;
import ClassiPrincipali.Bicicletta;

public class testBicicletta {

	@Test
	public void testaBiciClassica() {
		Bicicletta b = new BiciClassica("C456123");
		assertEquals(3.5,b.getPrezzo(120),0.001);
		assertEquals(0,b.getPrezzo(29),0.001);
		assertEquals(1.5,b.getPrezzo(90),0.001);
		assertEquals(5.5,b.getPrezzo(180),0.001);
	}
	
	@Test
	public void testaBiciElettrica() {
		Bicicletta b = new BiciElettrica("E456123");
		assertEquals(7.75,b.getPrezzo(120),0.001);
		assertEquals(0.25,b.getPrezzo(29),0.001);
		assertEquals(0,b.getPrezzo(2),0.001);
		assertEquals(3.75,b.getPrezzo(90),0.001);
		assertEquals(1.75,b.getPrezzo(60),0.001);
		assertEquals(0.75,b.getPrezzo(30),0.001);
		assertEquals(11.75,b.getPrezzo(180),0.001);
	}
	
	@Test
	public void testaBiciSeggiolino() {
		Bicicletta b = new BiciConSeggiolino("S987098");
		assertEquals(7.75,b.getPrezzo(120),0.001);
		assertEquals(0.25,b.getPrezzo(29),0.001);
		assertEquals(0,b.getPrezzo(2),0.001);
		assertEquals(3.75,b.getPrezzo(90),0.001);
		assertEquals(1.75,b.getPrezzo(60),0.001);
		assertEquals(0.75,b.getPrezzo(30),0.001);
		assertEquals(11.75,b.getPrezzo(180),0.001);
	}
}
