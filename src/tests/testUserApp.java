package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


import org.junit.jupiter.api.Test;

import Eccezioni.IllegalCardException;
import application.UserApp;
import controllers.appuser.RecData;
import principalclass.AbbonamentoAnnuale;
import principalclass.AbbonamentoFactory;
import principalclass.AbbonamentoSettimanale;
import principalclass.Bicicletta;
import principalclass.BiciclettaDao;
import principalclass.BiciclettaDaoImpl;
import principalclass.CartaDaoImpl;
import principalclass.Cliente;
import principalclass.ClienteDao;
import principalclass.ClienteDaoImpl;
import principalclass.CreditCard;
import principalclass.Rastrelliera;
import principalclass.RastrellieraDaoImpl;

public class testUserApp {

	UserApp appuser = new UserApp();
	String username_rec = "testerx";
	String pwd_rec = "test123";
	String username_test = "tester";
	String pwd_test = "testerpassword";
	String codice_test = "JGHFE6J";
	
	CreditCard carta_test = new CreditCard("3636363636363636", "222", 11,2023);
	
	
	//Cliente con abbonamento settimanale non studente
	
	@Test
	public void testaRegistrazione1() {
		RecData data = new RecData(username_rec, AbbonamentoFactory.SETTIMANALE,pwd_rec, false);
		data.setCarta(carta_test);
		String codice = appuser.attivaAbbonamento(data);
		assertEquals(7,codice.length());
		ClienteDao clients = new ClienteDaoImpl();
		Cliente client = clients.get(codice);
		assertNotNull(client);
		assertEquals(client.getUsername(),username_rec);
		assertEquals(client.getPwd(),pwd_rec);
		assertEquals(0,client.getPen());
		assertEquals(0,client.getDebito(),0.001);
	 	assertFalse(client.isStudent());
	 	assertEquals(client.getTypeAbbonamento(), AbbonamentoFactory.SETTIMANALE);
	 	assertNull(client.getAbbonamento().getStart());
	 	assertEquals(UserApp.OK,appuser.getStatoUtente());
	 	double disponibilita = new CartaDaoImpl().getDisponibilita(carta_test);
	 	assertEquals(200-AbbonamentoSettimanale.COSTOABBONAMENTO,disponibilita,0.001);
	 	assertTrue(appuser.autenticazione(username_rec, pwd_rec));
	 	clients.delete(client);
	 	new CartaDaoImpl().delete(carta_test);
	 	client = clients.get(codice);
	 	CreditCard carta = new CartaDaoImpl().get("3636363636363636");
	 	assertNull(client);
	 	assertNull(carta);
	 	assertFalse(appuser.autenticazione(username_rec, pwd_rec));
	}
	
	// cliente studente con abbonamento annuale
	
	@Test
	public void testaRegistrazione2() {
		RecData data = new RecData(username_rec, AbbonamentoFactory.ANNUALE,pwd_rec, true);
		data.setCarta(carta_test);
		String codice = appuser.attivaAbbonamento(data);
		assertEquals(7,codice.length());
		ClienteDao clients = new ClienteDaoImpl();
		Cliente client = clients.get(codice);
		assertNotNull(client);
		assertEquals(client.getUsername(),username_rec);
		assertEquals(client.getPwd(),pwd_rec);
		assertEquals(0,client.getPen());
		assertEquals(0,client.getDebito(),0.001);
	 	assertTrue(client.isStudent());
	 	assertEquals(client.getTypeAbbonamento(), AbbonamentoFactory.ANNUALE);
	 	assertNotNull(client.getAbbonamento().getStart());
	 	assertTrue(appuser.autenticazione(username_rec, pwd_rec));
	 	double disponibilita = new CartaDaoImpl().getDisponibilita(carta_test);
	 	assertEquals(200-AbbonamentoAnnuale.COSTOABBONAMENTO,disponibilita,0.001);
	 	assertEquals(UserApp.OK,appuser.getStatoUtente());
	 	clients.delete(client);
	 	new CartaDaoImpl().delete(carta_test);
	 	client = clients.get(codice);
	 	carta_test = new CartaDaoImpl().get("3636363636363636");
	 	assertNull(client);
	 	assertNull(carta_test);
	 	assertFalse(appuser.autenticazione(username_rec, pwd_rec));
	}
	
	
	@Test
	public void testaValidazioneStudente() {
		assertFalse(appuser.validaStudente("sbagliato", "sbagliato"));
		assertTrue(appuser.validaStudente("Bicocca", "123456"));
	}
	@Test
	public void testaSegnalaDanno() {
		 assertTrue(appuser.autenticazione(username_test,pwd_test));
		 Bicicletta biciTester = getBiciclettaTester();
		 assertTrue(appuser.segnalaDanno(biciTester.getCodice(), "Danno di prova"));
		 BiciclettaDao biciclette = new BiciclettaDaoImpl();
		 Bicicletta b = biciclette.get(biciTester.getCodice());
		 assertEquals("Danno riportato da utente "+appuser.getCodiceUtente()+": Danno di prova\n",b.getStato());
		 assertTrue(appuser.segnalaDanno(biciTester.getCodice(), "Altro danno di prova"));
		 b =  biciclette.get(biciTester.getCodice());
		 assertEquals("Danno riportato da utente "+appuser.getCodiceUtente()+": Danno di prova\n"+
				 "Danno riportato da utente "+appuser.getCodiceUtente()+": Altro danno di prova\n",b.getStato()
		 );
		 b.setStato("OK");
		 biciclette.update(b);
		 biciclette.get(b.getCodice());
		 assertEquals("OK",b.getStato());
		 assertTrue(appuser.logout());
		 assertNull(appuser.getCurrentClient());
	}
	
	//carta non valida : abbonamento annuale con carta che scade tra meno di 12 mesi
	@Test
	public void testaRegistrazione3() {
		CreditCard carta = new CreditCard("3636363636363636", "222", 6,2022);
		RecData data = new RecData(username_rec, AbbonamentoFactory.ANNUALE,pwd_rec, true);
		data.setCarta(carta);
		assertThrows(IllegalCardException.class,() ->{
			appuser.attivaAbbonamento(data);
		});
	}
	
	//carta non valida: abbonamento settimanale con carta che scade tra meno di 1 mese
	
	@Test
	public void testaRegistrazione4() {
		CreditCard carta = new CreditCard("3636363636363636", "222", 11,2021);
		RecData data = new RecData(username_rec, AbbonamentoFactory.SETTIMANALE,pwd_rec, true);
		data.setCarta(carta);
		assertThrows(IllegalCardException.class,() ->{
			appuser.attivaAbbonamento(data);
		});
	}
	
	//carta non valida: abbonamento giornaliero con carta che scade tra meno di 1 mese
	
	@Test
	public void testaRegistrazione5() {
		CreditCard carta = new CreditCard("3636363636363636", "222", 11,2021);
		RecData data = new RecData(username_rec, AbbonamentoFactory.GIORNALIERO,pwd_rec, true);
		data.setCarta(carta);
		assertThrows(IllegalCardException.class,() ->{
			appuser.attivaAbbonamento(data);
		});
	}

	
	@Test 
	public void testaCambiaPwd() {
		assertTrue(appuser.autenticazione(username_test,pwd_test));
		assertTrue(appuser.modificaPwd(pwd_test, "nuovapassword"));
		ClienteDao clienti = new ClienteDaoImpl();
		Cliente client = clienti.get(codice_test);
		assertEquals("nuovapassword",client.getPwd());
		assertTrue(appuser.modificaPwd("nuovapassword", pwd_test));
	}
	
	@Test
	public void testaUnivocitaUsername() {
		assertFalse(appuser.usernameUnivoco("tester"));
		assertTrue(appuser.usernameUnivoco("XXXXXXXXXXXX"));
	}
	
	@Test
	
	public void testaCambiaPagamento() {
		assertTrue(appuser.autenticazione(username_test, pwd_test));
		Cliente client = appuser.getCurrentClient();
		CreditCard oldcard = client.getCarta();
		client.setDebito(20);
		appuser.modificaPagamento(carta_test);
		assertEquals(carta_test,client.getCarta());
		Cliente verified_client = new ClienteDaoImpl().get(codice_test);
		assertEquals(verified_client.getCarta(),carta_test);
		double disponibilita = new CartaDaoImpl().getDisponibilita(carta_test);
		assertEquals(180,disponibilita,0.001);
		appuser.modificaPagamento(oldcard);
		verified_client = new ClienteDaoImpl().get(codice_test);
		assertEquals(verified_client.getCarta(),oldcard);
		new CartaDaoImpl().delete(carta_test);
		assertNull (new CartaDaoImpl().get(carta_test.getNumero()));
	}
	
	

	public Bicicletta getBiciclettaTester() {
		Rastrelliera r = new RastrellieraDaoImpl().get("Ple Lagosta");
		int posizione = r.getBicicletta();
		Bicicletta bicitester =  r.get(posizione);
		bicitester.setStato("OK");
		new BiciclettaDaoImpl().update(bicitester);
		return bicitester;
	}
}
