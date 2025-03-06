package principalclass;

import java.sql.Timestamp;

/**
 * Implementazione della classe astratta Abbonamento rappresenta un abbonamento annuale al bikesharing.
 * Esso perci� ha durata un anno (1440*7*52 minuti) ed ha costo di 36 �
 * 
 *
 */

public class AbbonamentoAnnuale extends Abbonamento {
	/**
	 * invariante di rappresentazione:
	 * dal momento che l'abbonamento � cominciato la differenza tra end e start deve essere di un anno,
	 * calcolata in millisecondi (86400000*7*52)	 
	 */
	
	//@ start != null ==> end.getTime() - start.getTime() ==  86400000 * 7 * 52
	
	public static final double COSTOABBONAMENTO = 36;
	private Timestamp start;
	private Timestamp end;
	
	public AbbonamentoAnnuale() {
		super();
	}
	
	public AbbonamentoAnnuale(Timestamp fine_abbonamento) {
		end = fine_abbonamento;
		start = new Timestamp(end.getTime() - (long) 86400000*7*52);
	}
	
	@Override
	public void Start() {
		start = new Timestamp(System.currentTimeMillis()) ;
		end = new Timestamp(start.getTime() + (long) 86400000 * 7 * 52);
	}
	
	@Override 
	public Timestamp getStart() {
		return start;
	}
	
	@Override 
	public Timestamp getEnd() {
		return end;
	}
	
	public double getCosto() {
		return COSTOABBONAMENTO;
	}
	
}
