package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


import org.junit.jupiter.api.Test;

import ClassiPrincipali.BiciclettaFactory;
import ClassiPrincipali.Rastrelliera;
import ClassiPrincipali.RastrellieraDao;
import ClassiPrincipali.RastrellieraDaoImpl;
import Eccezioni.BikeNotFoundException;
import Eccezioni.NoFreeSpotException;
import application.PersonaleApp;

public class testPersonaleApp {
	PersonaleApp apppersonale = new PersonaleApp();
	String rastrellieratest = "Loreto";
	String personalecodice = "QWERTY98";
	String personalepassword = "123";
	
	//Cliente con abbonamento settimanale non studente
	@Test
	public void testaCreazioneRastrelliera() {
		assertTrue(apppersonale.autenticazione(personalecodice, personalepassword));
		assertTrue(apppersonale.aggiungiRastrelliera(rastrellieratest,25,15));
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		Rastrelliera rastrelliera = rastrelliere.get(rastrellieratest);
		assertNotNull(rastrelliera);
		assertEquals(10,rastrelliera.getTotMorseClassiche());
		assertEquals(15,rastrelliera.getTotMorseElettriche());
		assertEquals(0,rastrelliera.getTot());
		assertEquals(25, rastrelliera.getSize());
		assertTrue(apppersonale.aggiungiBici(rastrellieratest, 7, 8, 3));
		rastrelliera = rastrelliere.get(rastrellieratest);
		assertEquals(18,rastrelliera.getTot());
		assertEquals(7,rastrelliera.getTot(BiciclettaFactory.CLASSICA));
		assertEquals(8,rastrelliera.getTot(BiciclettaFactory.ELETTRICA));
		assertEquals(3,rastrelliera.getTot(BiciclettaFactory.SEGGIOLINO));
		assertEquals(3,rastrelliera.getTotFreeClassicSpots());
		assertEquals(4,rastrelliera.getTotFreeEletricSpots());
		rastrelliere.delete(rastrellieratest);
		apppersonale.logout();
	}
	
	@Test
	public void testaCreazioneRastrelliera2() {
		assertTrue(apppersonale.autenticazione(personalecodice, personalepassword));
		assertFalse(apppersonale.aggiungiRastrelliera(rastrellieratest,35,15));
		apppersonale.logout();
	}
	
	// test rilocazione: vengono create due rastrelliere fittizie e si verificano le rilocazioni
	@Test
	public void testaRilocazione() {
		assertTrue(apppersonale.autenticazione(personalecodice, personalepassword));
		assertTrue(apppersonale.aggiungiRastrelliera(rastrellieratest,25,15));
		String rastrellieratest2 = "rastrellieraprova2";
		assertTrue(apppersonale.aggiungiRastrelliera(rastrellieratest2,25,15));
		assertTrue(apppersonale.aggiungiBici(rastrellieratest, 7, 8, 3));
		assertTrue(apppersonale.aggiungiBici(rastrellieratest2, 8, 8, 2));
		assertThrows(BikeNotFoundException.class,() ->{
			apppersonale.riloca(rastrellieratest, rastrellieratest2, 8, 3, 2);
		});
		assertThrows(NoFreeSpotException.class,() ->{
			apppersonale.riloca(rastrellieratest, rastrellieratest2, 5, 3, 2);
		});
		assertTrue(apppersonale.riloca(rastrellieratest, rastrellieratest2, 2, 2, 3));
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		Rastrelliera rtest = rastrelliere.get(rastrellieratest);
		Rastrelliera rtest2 = rastrelliere.get(rastrellieratest2);
		assertEquals(5,rtest.getTot(BiciclettaFactory.CLASSICA));
		assertEquals(6,rtest.getTot(BiciclettaFactory.ELETTRICA));
		assertEquals(0,rtest.getTot(BiciclettaFactory.SEGGIOLINO));
		assertEquals(10,rtest2.getTot(BiciclettaFactory.CLASSICA));
		assertEquals(10,rtest2.getTot(BiciclettaFactory.ELETTRICA));
		assertEquals(5,rtest2.getTot(BiciclettaFactory.SEGGIOLINO));
		assertTrue(apppersonale.rimuoviRastrelliera(rastrellieratest));
		assertTrue(apppersonale.rimuoviRastrelliera(rastrellieratest2));
		apppersonale.logout();
	}
	
	@Test
	public void testaRilocazione2() {
		assertTrue(apppersonale.autenticazione(personalecodice, personalepassword));
		assertTrue(apppersonale.aggiungiRastrelliera(rastrellieratest,25,15));
		String rastrellieratest2 = "rastrellieraprova2";
		assertTrue(apppersonale.aggiungiRastrelliera(rastrellieratest2,25,15));
		assertTrue(apppersonale.aggiungiBici(rastrellieratest, 10, 10, 5));
		assertTrue(apppersonale.riloca(rastrellieratest, rastrellieratest2, 10, 10, 5));
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		Rastrelliera rtest = rastrelliere.get(rastrellieratest);
		Rastrelliera rtest2 = rastrelliere.get(rastrellieratest2);
		assertEquals(0,rtest.getTot(BiciclettaFactory.CLASSICA));
		assertEquals(0,rtest.getTot(BiciclettaFactory.ELETTRICA));
		assertEquals(0,rtest.getTot(BiciclettaFactory.SEGGIOLINO));
		assertEquals(10,rtest2.getTot(BiciclettaFactory.CLASSICA));
		assertEquals(10,rtest2.getTot(BiciclettaFactory.ELETTRICA));
		assertEquals(5,rtest2.getTot(BiciclettaFactory.SEGGIOLINO));
		assertTrue(apppersonale.rimuoviRastrelliera(rastrellieratest));
		assertTrue(apppersonale.rimuoviRastrelliera(rastrellieratest2));
		apppersonale.logout();
	}
	
	@Test
	public void testaRimuoviBici() {
		assertTrue(apppersonale.autenticazione(personalecodice, personalepassword));
		assertTrue(apppersonale.aggiungiRastrelliera(rastrellieratest,30,15));
		assertTrue(apppersonale.aggiungiBici(rastrellieratest, 10, 5, 5));
		assertFalse(apppersonale.rimuoviBici(rastrellieratest,12,3,3));
		assertTrue(apppersonale.rimuoviBici(rastrellieratest,8,2,3));
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
;		Rastrelliera rtest = rastrelliere.get(rastrellieratest);
		assertEquals(2,rtest.getTot(BiciclettaFactory.CLASSICA));
		assertEquals(3,rtest.getTot(BiciclettaFactory.ELETTRICA));
		assertEquals(2,rtest.getTot(BiciclettaFactory.SEGGIOLINO));
		assertTrue(apppersonale.rimuoviRastrelliera(rastrellieratest));
		apppersonale.logout();
	}
	
	@Test 
	public void testaAggiungiBici() {
		assertTrue(apppersonale.autenticazione(personalecodice, personalepassword));
		assertTrue(apppersonale.aggiungiRastrelliera(rastrellieratest,30,15));
		assertFalse(apppersonale.aggiungiBici(rastrellieratest, 16, 9, 6));
		assertFalse(apppersonale.aggiungiBici(rastrellieratest,15,10,6));
		assertTrue(apppersonale.aggiungiBici(rastrellieratest, 15, 9, 6));
		assertTrue(apppersonale.rimuoviBici(rastrellieratest,15,9,6));
		assertFalse(apppersonale.aggiungiBici("Nome sbagliato", 5, 5, 5));
		assertTrue(apppersonale.rimuoviRastrelliera(rastrellieratest));
		apppersonale.logout();
	}
	
	@Test
	public void testanonAutenticato() {
		assertFalse(apppersonale.aggiungiRastrelliera(rastrellieratest,25,15));
	}
}

