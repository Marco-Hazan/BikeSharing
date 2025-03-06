package ClassiPrincipali;

import java.sql.Timestamp;


/**
 * Classe astratta che rappresenta un abbonamento al servizio bikesharing.
 * Questa classe incapsula le informazioni sull'inizio,la fine dell'abbonamento e il costo di esso
 * Per implementare questa classe astratta bisogna implementare il metodo Start() che inzializza start e end
 * e il metodo getCosto() che restituisce il costo dell'abbonamento
 */

public abstract class Abbonamento {
	
	/**
	 * invariante di rappresentazione:
	 * getStart() e getEnd() non possono ritornare valori nulli quando l'abbonamento non è ancora cominciato, ma dal momento che comincia
	 * (viene invocato il metodo Start()) essi devono essere entrambi non nulli, 
	 * inoltre se non sono nulli getEnd() deve ritornare un valore maggiore di getStart()
	 */
	
	/*@ getStart() != null ==> getEnd() != null && 
	 * getStart() != null ==> getEnd().compareTo(getStart()) > 0
	 @*/
	
	/**
	 * restituisce un valore booleano che indica se l'abbonamento è finito oppure no, e per finito si intende se
	 * il timestamp attuale ha superato quello di fine abbonamento
	 * @return boolean
	 */
	
	public boolean isOver() {
		if(this.getStart() != null && this.getEnd() != null) {
			return new Timestamp(System.currentTimeMillis()).compareTo(this.getEnd()) > 0;
		}
		return false;
	}
	
	/**
	 * restiuisce un valore booleano che indica se l'abbonamento è ancora in corso (ovvero è cominciato e non è finito)
	 * @return
	 */
	
	public boolean inCorso() {
		return this.getStart() != null && this.getEnd()!=null && !isOver();
	}
	
	/**
	 * restituisce il timestamp di inizio dell'abbonamento
	 * @return Timestamp
	 */
	public abstract Timestamp getStart();
	
	/**
	 * resituisce il timestamp di fine dell'abbonamento
	 * @return
	 */
	public abstract Timestamp getEnd();
	
	
	/**
	 * incomincia l'abbonamento: con questo metodo vengono settati inizio e fine dell'abbonamento
	 */
	
	//@ ensures getStart() != null && getEnd() != null
	public abstract void Start();
	
	/**
	 * restituisce costo dell'abbonamento
	 * @return double
	 */
	public abstract double getCosto();
	
}
