package ClassiPrincipali;

import java.sql.Timestamp;

/**
 * Implementazione della classe astratta Abbonamento rappresenta un abbonamento settimanale al bikesharing.
 * Esso perciò ha durata un giorno (1440 minuti) ed ha costo di 4.5 €
 * 
 *
 */

public class AbbonamentoGiornaliero extends Abbonamento {
	/**
	 * invariante di rappresentazione:
	 * dal momento che l'abbonamento è cominciato la differenza tra end e start deve essere di un giorno,
	 * calcolata in millisecondi (86400000)	 
	 */
	
	//@ start != null ==> end.getTime() - start.getTime() ==  86400000
	public static final double COSTOABBONAMENTO = 4.5;
	private Timestamp start;
	private Timestamp end;
	
	public AbbonamentoGiornaliero() {
		super();
	}
	
	public AbbonamentoGiornaliero(Timestamp fine_abbonamento) {
		end = fine_abbonamento;
		start = new Timestamp(end.getTime() - 86400000);
	}
	
	@Override
	public void Start() {
		start = new Timestamp(System.currentTimeMillis());
		end = new Timestamp(start.getTime() + 86400000);
	}
	
	@Override
	public double getCosto() {
		return COSTOABBONAMENTO;
	}

	@Override
	public Timestamp getStart() {
		return start;
	}

	@Override
	public Timestamp getEnd() {
		return end;
	}
}
