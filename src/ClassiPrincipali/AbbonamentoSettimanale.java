package ClassiPrincipali;

import java.sql.Timestamp;


/**
 * Implementazione della classe astratta Abbonamento rappresenta un abbonamento settimanale al bikesharing.
 * Esso perciò ha durata una settimana (1440*7 minuti) ed ha costo di 9 €
 * 
 *
 */
public class AbbonamentoSettimanale extends Abbonamento {
	/**
	 * invariante di rappresentazione:
	 * dal momento che l'abbonamento è cominciato la differenza tra end e start deve essere di una settimana,
	 * calcolata in millisecondi (86400000 * 7)	 
	 */
	
	//@ start != null ==> end.getTime() - start.getTime() ==  86400000 * 7
	
	public static final double COSTOABBONAMENTO = 9;
	
	private Timestamp start;
	private Timestamp end;
	
	public AbbonamentoSettimanale() {
		super();
	}
	
	public AbbonamentoSettimanale(Timestamp fine_abbonamento) {
		end = fine_abbonamento;
		start = new Timestamp(end.getTime() - 86400000*7);
	}
	
	
	
	public void Start() {
		start = new Timestamp(System.currentTimeMillis());
		end = new Timestamp(start.getTime() + 86400000 * 7);
	}
	
	
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
