package testClassiPrincipali;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Calendar;
import org.junit.jupiter.api.Test;

import ClassiPrincipali.Bicicletta;
import ClassiPrincipali.BiciclettaFactory;
import ClassiPrincipali.Cliente;
import ClassiPrincipali.Corsa;

public class CorsaTest {
	
	@Test
	public void testCalcola() {
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -50);
        Timestamp dataultimonoleggio =  new Timestamp(cal.getTime().getTime());
		Corsa corsa = new Corsa(null, null, null, dataultimonoleggio);
		assertEquals(50,corsa.calcola());
	}
	
	@Test
	public void testCalcola2() {
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -50);
        Timestamp dataultimonoleggio =  new Timestamp(cal.getTime().getTime());
		Corsa corsa = new Corsa(null, null, null, dataultimonoleggio,null,new Timestamp(cal2.getTime().getTime()));
		assertEquals(50,corsa.calcola());
	}
	
	@Test
	public void testgetPrice() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -50);
		Cliente client = new Cliente("", "", "", true, 0, null, null);
		Bicicletta bici = BiciclettaFactory.generaBici(BiciclettaFactory.CLASSICA); 
		Corsa corsa = new Corsa(client,bici,null,new Timestamp(cal.getTime().getTime()));
		assertEquals(0,corsa.getPrice(),0);
	}
	
	@Test
	public void testgetPrice2() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -50);
		Cliente client = new Cliente("", "", "", false, 0, null, null);
		Bicicletta bici = BiciclettaFactory.generaBici(BiciclettaFactory.ELETTRICA); 
		Corsa corsa = new Corsa(client,bici,null,new Timestamp(cal.getTime().getTime()));
		assertEquals(0.75,corsa.getPrice(),0.001);
	}
}
