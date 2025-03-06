package testClassiPrincipali;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

import ClassiPrincipali.Abbonamento;
import ClassiPrincipali.AbbonamentoFactory;

public class testAbbonamento {

	@Test
	public void abbonamentoAnnuale() {
		Abbonamento abb = AbbonamentoFactory.createAbbonamento(AbbonamentoFactory.ANNUALE);
		assertNotNull(abb.getStart());
		assertNotNull(abb.getEnd());
		assertEquals((long)1440*60*7*52*1000,abb.getEnd().getTime() - abb.getStart().getTime());
	}
	
	@Test 
	public void abbonamentoSettimanale() {
		Abbonamento abb = AbbonamentoFactory.createAbbonamento(AbbonamentoFactory.SETTIMANALE);
		assertNull(abb.getStart());
		assertNull(abb.getEnd());
		abb.Start();
		assertNotNull(abb.getStart());
		assertNotNull(abb.getEnd());
		assertEquals((long)1440*60*7*1000,abb.getEnd().getTime() - abb.getStart().getTime());
	}
	
	@Test 
	public void abbonamentoGiornaliero() {
		Abbonamento abb = AbbonamentoFactory.createAbbonamento(AbbonamentoFactory.GIORNALIERO);
		assertNull(abb.getStart());
		assertNull(abb.getEnd());
		abb.Start();
		assertNotNull(abb.getStart());
		assertNotNull(abb.getEnd());
		assertEquals((long)1440*60*1000,abb.getEnd().getTime() - abb.getStart().getTime());
	}
}
