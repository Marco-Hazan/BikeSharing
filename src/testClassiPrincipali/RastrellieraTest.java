package testClassiPrincipali;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Eccezioni.MorsaSbagliataException;
import Eccezioni.PositionOccupiedException;
import principalclass.Bicicletta;
import principalclass.BiciclettaFactory;
import principalclass.Rastrelliera;
public class RastrellieraTest {

	@Test
	public void testaCreazione() {
		assertThrows(NullPointerException.class,() -> {
			Rastrelliera r = new Rastrelliera(null, 0, 0);
		});
		assertThrows(IllegalArgumentException.class,() -> {
			Rastrelliera r = new Rastrelliera("Prova", 31, 0);
		});
		Rastrelliera r = new Rastrelliera("Prova",30,15);
		assertEquals("Prova",r.getNome());
		assertEquals(30,r.getSize());
		assertEquals(15,r.getTotMorseElettriche());
		assertEquals(15,r.getTotMorseClassiche());
		assertEquals(0,r.getTot());
	}
	
	@Test
	public void testaCreazione2() {
		Map<Integer,Bicicletta> mapbikes = new HashMap<>();
		for(int i=1;i<=25;i++) {
			if(i <=10) {
				mapbikes.put(i, BiciclettaFactory.generaBici(BiciclettaFactory.CLASSICA));
			}else if(i >= 16 && i <= 21) {
				mapbikes.put(i, BiciclettaFactory.generaBici(BiciclettaFactory.ELETTRICA));
			}else if(i >= 22 && i <= 24) {
				mapbikes.put(i, BiciclettaFactory.generaBici(BiciclettaFactory.SEGGIOLINO));
			}else {
				mapbikes.put(i, null);
			}
		}
		Rastrelliera r = new Rastrelliera("Prova",mapbikes,25,10);
		assertEquals(10,r.getTot(BiciclettaFactory.CLASSICA));
		assertEquals(6,r.getTot(BiciclettaFactory.ELETTRICA));
		assertEquals(3,r.getTot(BiciclettaFactory.SEGGIOLINO));
		assertEquals(5,r.getTotFreeClassicSpots());
		assertEquals(1,r.getTotFreeEletricSpots());
	}
	
	// tento azioni su rastrelliera con 8 bici classiche 4 elettriche 3 con seggiolino
	@Test
	public void testaAzioniRastrelliera(){
		Map<Integer,Bicicletta> mapbikes = new HashMap<>();
		for(int i=1;i<=25;i++) {
			if(i>= 3 && i<=10) {
				mapbikes.put(i, BiciclettaFactory.generaBici(BiciclettaFactory.CLASSICA));
			}else if(i >= 18 && i <= 21) {
				mapbikes.put(i, BiciclettaFactory.generaBici(BiciclettaFactory.ELETTRICA));
			}else if(i >= 22 && i <= 24) {
				mapbikes.put(i, BiciclettaFactory.generaBici(BiciclettaFactory.SEGGIOLINO));
			}else {
				mapbikes.put(i, null);
			}
		}
		Rastrelliera r = new Rastrelliera("Prova",mapbikes,25,10);
		// testo che se chiedo una bici di un certo tipo mi ritorna la prima posizione disponibile
		assertEquals(3,r.get(BiciclettaFactory.CLASSICA));
		assertEquals(18,r.get(BiciclettaFactory.ELETTRICA));
		assertEquals(22,r.get(BiciclettaFactory.SEGGIOLINO));
		//testo che la bici viene aggiunta alla prima postazione corretta disponibile
		Bicicletta b = BiciclettaFactory.generaBici(BiciclettaFactory.CLASSICA);
		r.addBike(b);
		Bicicletta b2 = r.removeBike(1);
		assertNull(r.get(2));
		assertEquals(b2,b);
		b = BiciclettaFactory.generaBici(BiciclettaFactory.ELETTRICA);
		r.addBike(b);
		b2 = r.removeBike(16);
		assertEquals(b2,b);
		b = BiciclettaFactory.generaBici(BiciclettaFactory.SEGGIOLINO);
		r.addBike(b);
		b2 = r.removeBike(16);
		assertEquals(b2,b);
		b = BiciclettaFactory.generaBici(BiciclettaFactory.CLASSICA);
		b2 = BiciclettaFactory.generaBici(BiciclettaFactory.ELETTRICA);
		Bicicletta b3 = BiciclettaFactory.generaBici(BiciclettaFactory.SEGGIOLINO);
		Set<Bicicletta> bikes = Set.of(b,b2,b3);
		r.addBikes(bikes);
		Set<Bicicletta> bikes2 = r.removeBikes(1, 1, 1);
		assertTrue(bikes2.contains(b));
		assertTrue(bikes2.contains(b2));
		assertTrue(bikes2.contains(b3));
		assertThrows(MorsaSbagliataException.class, () ->{
			Bicicletta b_prova = BiciclettaFactory.generaBici(BiciclettaFactory.CLASSICA);
			r.addBike(b_prova,16);
		});
		assertThrows(PositionOccupiedException.class, () ->{
			Bicicletta b_prova = BiciclettaFactory.generaBici(BiciclettaFactory.CLASSICA);
			r.addBike(b_prova,3);
		});
		Bicicletta b_prova = BiciclettaFactory.generaBici(BiciclettaFactory.CLASSICA);
		r.addBike(b_prova,2);
		Bicicletta b_prova2 = r.removeBike(2);
		assertEquals(b_prova,b_prova2);
	}
	
	
}
