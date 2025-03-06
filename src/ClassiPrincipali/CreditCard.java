package ClassiPrincipali;


import java.util.Calendar;

import java.util.Random;

import Eccezioni.ExpiredCardException;
import Eccezioni.IllegalCardException;
import Eccezioni.RoofReachedException;

/**
 * Questa classe rappresenta un oggetto carta di credito associata a un abbonamento bikesharing.
 * Come attributi ci sono gli attributi di una carta di credito reale e perciò il numero, il cvv, mese di scadenza e anno di scadenza
 *
 */

public class CreditCard {
	
	/**
	 * invariante di rappresentazione:
	 * il numero deve essere per forza di 16 cifre, il cvv di 3 cifre e la data di scadenza indicata da mese e anno non deve superare 
	 * la data attuale, deve essere una data valida e non può scadere tra più di 30 anni
	 */
	
	//@ invariant numero.length() == 16 && cvv.length() == 3 && mese_scadenza >= 0 && mese_scadenza <= 12 && anno_scadenza <= Calendar.getInstance().get(Calendar.YEAR)+30
	//@ invariant anno_scadenza == Calendar.getInstance().get(Calendar.YEAR) ==> mese_scadenza > mese_scadenza <= Calendar.getInstance().get(Calendar.MONTH) + 1
	//@invariant anno_scadenza >= Calendar.getInstance().get(Calendar.YEAR)
	
	private String numero;
	private String cvv;
	private int mese_scadenza;
	private int anno_scadenza;
	public CreditCard(String numero,String cvv,int mese_scadenza,int anno_scadenza) throws ExpiredCardException,IllegalCardException,RoofReachedException {
		if(numero.length() != 16) {
			throw new IllegalCardException("Carta non valida, numero non ha 16 cifre");
		}
		if (cvv.length() != 3) {
			throw new IllegalCardException("Carta non valida, cvv non ha 3 cifre");
		}
		if(mese_scadenza <=0 || mese_scadenza >12 || anno_scadenza > Calendar.getInstance().get(Calendar.YEAR) + 30) {
			throw new IllegalCardException("Data sulla carta non valida");
		}
		if(
			anno_scadenza < Calendar.getInstance().get(Calendar.YEAR) || 
			(anno_scadenza == Calendar.getInstance().get(Calendar.YEAR) && mese_scadenza <= Calendar.getInstance().get(Calendar.MONTH) + 1)
		) {
			throw new ExpiredCardException("Carta scaduta");
		}
		this.numero = numero;
		this.cvv = cvv;
		this.mese_scadenza = mese_scadenza;
		this.anno_scadenza = anno_scadenza;
	}
	
	/**
	 * ritorna il numero di questa carta di credito
	 * @return String
	 */
	
	public String getNumero() {
		return numero;
	}
	
	/**
	 * ritorna il cvv di questa carta di credito
	 * @return String
	 */
	
	public String getCvv() {
		return cvv;
	}
	
	/**
	 * ritorna intero che rappresenta mese di scadenza
	 * @return int
	 */
	public int getMeseScadenza() {
		return this.mese_scadenza;
	}
	
	/**
	 * ritorna intero che rappresenta anno di scadenza
	 * @return int
	 */
	public int getAnnoScadenza() {
		return this.anno_scadenza;
	}
	@Override 
	public String toString() {
		return "Numero: "+ numero + "\nCvv: " + cvv + "\n"+Integer.toString(this.mese_scadenza)+"/"+Integer.toString(this.anno_scadenza);
	}
	
	/**
	 * ritorna un valore booleano che indica se la carta sarà scaduta tra il numero di mesi indicati come parametro
	 * @param int:numero_mesi
	 * @return boolean
	 */
	public boolean isExpired(int numero_mesi) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, numero_mesi+1) ;
		
		if(this.anno_scadenza < calendar.get(Calendar.YEAR)) {
			return true;
		}else if(this.anno_scadenza == calendar.get(Calendar.YEAR) && this.mese_scadenza <= calendar.get(Calendar.MONTH)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CreditCard) {
			CreditCard c = (CreditCard) obj;
			return c.getNumero().equals(this.numero) && c.getCvv().equals(this.cvv);
		}
		return false;
	}
	
	@Override
	
	public int hashCode() {
		return Integer.hashCode(numero.hashCode() + cvv.hashCode());
	}
	
	/**
	 * genera una stringa casuale che rappresenta un numero valido di carta di credito
	 * @return String
	 */
	public static String generaNumero() {
    	char randomstring[] = new char[16];
    	char characters[] = {
    			'1','2','3','4','5','6','7','8','9','0'
    	};
    	Random r = new Random();
    	for(int i=0;i<16;i++) {
    		int random = Math.abs(r.nextInt())% 10;
    		randomstring[i] = characters[random];
    	}
    	return new String(randomstring,0,16);
    }
}
