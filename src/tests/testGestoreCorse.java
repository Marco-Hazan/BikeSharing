package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import application.PersonaleApp;
import application.TotemApp;
import principalclass.Bicicletta;
import principalclass.BiciclettaDaoImpl;
import principalclass.BiciclettaFactory;
import principalclass.CartaDao;
import principalclass.CartaDaoImpl;
import principalclass.Cliente;
import principalclass.ClienteDao;
import principalclass.ClienteDaoImpl;
import principalclass.Corsa;
import principalclass.CorsaDao;
import principalclass.CorsaDaoImpl;
import principalclass.GestoreCorse;
import principalclass.Rastrelliera;
import principalclass.RastrellieraDao;
import principalclass.RastrellieraDaoImpl;

public class testGestoreCorse {
	String username_test = "tester";
	String pwd_test = "testerpassword";
	String codice_test = "JGHFE6J";
	String numerocarta_test = "1100110011001100";
	String personalecodice = "QWERTY98";
	String personalepwd = "123";
	Rastrelliera rastrelliera = new RastrellieraDaoImpl().get("Ple Lagosta");
	TotemApp apptotem;
	PersonaleApp apppersonale = new PersonaleApp();
	
	
	// viene creata una rastrelliera fittizia ("Loreto") e poi distrutta su cui testare una corsa
	@Test
	public void testaCorsa1() {
		assertTrue(apppersonale.autenticazione(personalecodice, personalepwd));
		assertTrue(apppersonale.aggiungiRastrelliera("Loreto", 30, 15));
		assertTrue(apppersonale.aggiungiBici("Loreto", 15, 10, 5));
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		Rastrelliera r = rastrelliere.get("Loreto");
		apptotem = new TotemApp(r);
		// verifica che l'utente � abilitato
		assertEquals(TotemApp.ABILITATO,apptotem.autenticazione(codice_test, pwd_test));
		//verifica bici rimanenti dopo che ho estratto una bici sia dalla rastrelliera associata al totem sia guardando direttamente il db
		assertEquals(1,apptotem.askBike(BiciclettaFactory.CLASSICA));
		assertEquals(29,apptotem.getRastrelliera().getTot());
		assertEquals(14,apptotem.getRastrelliera().getTot(BiciclettaFactory.CLASSICA));
		r = rastrelliere.get("Loreto");
		assertEquals(29,r.getTot());
		assertEquals(14,r.getTot(BiciclettaFactory.CLASSICA));
		//verifica che lo stato dell'utente sia CORSAATTIVA mentre � in corsa
		assertEquals(TotemApp.CORSAATTIVA,apptotem.autenticazione(codice_test, pwd_test));
		assertFalse(apptotem.valuta(16));
		assertFalse(apptotem.agganciaBici(16));
		assertTrue(apptotem.valuta(1));
		assertTrue(apptotem.agganciaBici(1));
		//valutazione se la bici agganciata � stata registrata come tale sulla rastrelliera
		assertEquals(30,apptotem.getRastrelliera().getTot());
		assertEquals(15,apptotem.getRastrelliera().getTot(BiciclettaFactory.CLASSICA));
		assertEquals(TotemApp.CORSACONCLUSA,apptotem.autenticazione(codice_test, pwd_test));
		assertEquals(0,apptotem.getCorsaConclusa().getPrice(),0.001);
		assertEquals(0,apptotem.getCorsaConclusa().calcola(),0);
		rastrelliere.delete("Loreto");
	}
	
	//test che un cliente con debito = 10� viene considerato come disabilitato
	@Test
	public void testaCorsa2() {
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		apptotem = new TotemApp(rastrelliere.get("Ple Lagosta"));
		ClienteDao clienti = new ClienteDaoImpl();
		Cliente client = clienti.get(codice_test);
		client.setDebito(10);
		clienti.update(client);
		assertEquals(TotemApp.DISABILITATO,apptotem.autenticazione(codice_test, pwd_test));
		client.setDebito(0);
		clienti.update(client);
	}
	 //test che un cliente non autenticato non pu� agganciare una bicicletta
	@Test
	public void testaFailures() {
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		apptotem = new TotemApp(rastrelliere.get("Ple Lagosta"));
		assertFalse(apptotem.agganciaBici(1));
		assertEquals(-1,apptotem.askBike(BiciclettaFactory.CLASSICA));
	}
	
	
	// test vari sul GestoreCorse
	@Test
	
	public void testaGestore() {
		//creazione rastrelliera fittizia
		assertTrue(apppersonale.autenticazione(personalecodice, personalepwd));
		assertTrue(apppersonale.aggiungiRastrelliera("Loreto", 30, 15));
		assertTrue(apppersonale.aggiungiBici("Loreto", 15, 10, 5));
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		CartaDao carte = new CartaDaoImpl();
		// viene registrata disponibilit� carta cos� da reimpostarla dopo che ho testato
		double disp = carte.getDisponibilita(carte.get(numerocarta_test));
		// creazione di una corsa di 50 minuti
		GestoreCorse gc = GestoreCorse.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -50);
		Timestamp start = new Timestamp(cal.getTimeInMillis());
		ClienteDao clienti = new ClienteDaoImpl();
		Cliente client = clienti.get(codice_test);
		Bicicletta b = BiciclettaFactory.generaBici(BiciclettaFactory.ELETTRICA);
		new BiciclettaDaoImpl().insert(b);
		Corsa corsa = new Corsa(client, b, "Loreto", start);
		//inizia corsa
		gc.iniziaCorsa(corsa);
		CorsaDao corse = new CorsaDaoImpl();
		corsa = corse.getAttiva(client);
		// verifica pagamento 
		assertEquals(50,corsa.calcola());
		gc.termina(corsa, "Loreto");
		Corsa c = gc.getLast(client);
		assertEquals(0.75,c.getPrice(),0.001);
		assertEquals(disp-0.75,carte.getDisponibilita(carte.get(numerocarta_test)),0.001);
		rastrelliere.delete("Loreto");
		carte.update(carte.get(numerocarta_test), - 0.75);
	}
	
	//variante del testaGestore dove si verifica che viene assegnata una penalit�
	@Test
	public void testaGestore2() {
		assertTrue(apppersonale.autenticazione(personalecodice, personalepwd));
		assertTrue(apppersonale.aggiungiRastrelliera("Loreto", 30, 15));
		assertTrue(apppersonale.aggiungiBici("Loreto", 15, 10, 5));
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		CartaDao carte = new CartaDaoImpl();
		double disp = carte.getDisponibilita(carte.get(numerocarta_test));
		GestoreCorse gc = GestoreCorse.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, - 130);
		Timestamp start = new Timestamp(cal.getTimeInMillis());
		ClienteDao clienti = new ClienteDaoImpl();
		Cliente client = clienti.get(codice_test);
		Bicicletta b = BiciclettaFactory.generaBici(BiciclettaFactory.ELETTRICA);
		new BiciclettaDaoImpl().insert(b);
		Corsa corsa = new Corsa(client, b, "Loreto", start);
		gc.iniziaCorsa(corsa);
		CorsaDao corse = new CorsaDaoImpl();
		corsa = corse.getAttiva(client);
		assertEquals(130,corsa.calcola());
		gc.termina(corsa, "Loreto");
		Corsa c = gc.getLast(client);
		assertEquals(7.75,c.getPrice(),0.001);
		assertEquals(disp-7.75,carte.getDisponibilita(carte.get(numerocarta_test)),0.001);
		assertFalse(c.multata());
		assertEquals(1,client.getPen());
		assertEquals(disp-7.75,carte.getDisponibilita(carte.get(numerocarta_test)),0.001);
		rastrelliere.delete("Loreto");
		carte.update(carte.get(numerocarta_test), - 7.75);
		client.setPen(0);
		clienti.update(client);
	}
	//variante del testaGestore dove si verifica che viene assegnata una multa oltre che una penalit�
	@Test
	public void testaGestore3() {
		assertTrue(apppersonale.autenticazione(personalecodice, personalepwd));
		assertTrue(apppersonale.aggiungiRastrelliera("Loreto", 30, 15));
		assertTrue(apppersonale.aggiungiBici("Loreto", 15, 10, 5));
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		CartaDao carte = new CartaDaoImpl();
		double disp = carte.getDisponibilita(carte.get(numerocarta_test));
		GestoreCorse gc = GestoreCorse.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, - 1440);
		Timestamp start = new Timestamp(cal.getTimeInMillis());
		ClienteDao clienti = new ClienteDaoImpl();
		Cliente client = clienti.get(codice_test);
		Bicicletta b = BiciclettaFactory.generaBici(BiciclettaFactory.ELETTRICA);
		new BiciclettaDaoImpl().insert(b);
		Corsa corsa = new Corsa(client, b, "Loreto", start);
		gc.iniziaCorsa(corsa);
		CorsaDao corse = new CorsaDaoImpl();
		corsa = corse.getAttiva(client);
		assertEquals(1440,corsa.calcola());
		gc.termina(corsa, "Loreto");
		Corsa c = gc.getLast(client);
		assertEquals(95.75,c.getPrice(),0.001);
		assertEquals(disp-245.75,carte.getDisponibilita(carte.get(numerocarta_test)),0.001);
		assertFalse(c.multata());
		assertEquals(1,client.getPen());
		rastrelliere.delete("Loreto");
		carte.update(carte.get(numerocarta_test), - 245.75);
		client.setPen(0);
		clienti.update(client);
	}
	
	// test pagamento corsa che non va a buon fine
	@Test
	public void testaGestore4() {
		assertTrue(apppersonale.autenticazione(personalecodice, personalepwd));
		assertTrue(apppersonale.aggiungiRastrelliera("Loreto", 30, 15));
		assertTrue(apppersonale.aggiungiBici("Loreto", 15, 10, 5));
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		CartaDao carte = new CartaDaoImpl();
		carte.update(carte.get(numerocarta_test), 263);
		double disp = carte.getDisponibilita(carte.get(numerocarta_test));
		GestoreCorse gc = GestoreCorse.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, - 90);
		Timestamp start = new Timestamp(cal.getTimeInMillis());
		ClienteDao clienti = new ClienteDaoImpl();
		Cliente client = clienti.get(codice_test);
		Bicicletta b = BiciclettaFactory.generaBici(BiciclettaFactory.ELETTRICA);
		new BiciclettaDaoImpl().insert(b);
		Corsa corsa = new Corsa(client, b, "Loreto", start);
		gc.iniziaCorsa(corsa);
		CorsaDao corse = new CorsaDaoImpl();
		corsa = corse.getAttiva(client);
		assertEquals(90,corsa.calcola());
		gc.termina(corsa, "Loreto");
		Corsa c = gc.getLast(client);
		assertEquals(3.75,c.getPrice(),0.001);
		// test che la carta ha tenuto la stessa disponibilit�
		assertEquals(disp,carte.getDisponibilita(carte.get(numerocarta_test)),0.001);
		//test che il debito viene registrato sul database
		assertEquals(3.75,client.getDebito(),0.001);
		client.setDebito(0);
		clienti.update(client);
		rastrelliere.delete("Loreto");
		carte.update(carte.get(numerocarta_test), - 263);
		clienti.update(client);
	}
	
	
}
