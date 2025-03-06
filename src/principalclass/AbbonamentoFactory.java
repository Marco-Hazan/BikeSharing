package principalclass;

import java.sql.Timestamp;

/**
 * Classe che serve per fabbricare oggetti Abbonamento dal servizio del bikesharing.
 * In questa classe vengono definiti tre attributi statici, GIORNALIERO,SETTIMANALE e ANNUALE che definiscono i tipi di abbonamento
 * disponibili. Essi dovranno essere passati nei metodi fabbricatori per costruire l'oggetto Abbonamento che si vuole.
 */

public class AbbonamentoFactory {
	
	/**
	 * Tutti i metodi di questa classe sono statici in quanto questa classe non ha nessuno stato.
	 */

	public static final String GIORNALIERO = "giornaliero";
	public static final String SETTIMANALE = "settimanale";
	public static final String ANNUALE = "annuale";
	
	
	/**
	 * Genera abbonamento dato il tipo.
	 * Se il tipo non corrisponde a nessuno dei valori statici presentati da questa classe viene ritornato null.
	 * @param type: String
	 * @return Abbonamento
	 */
	
	/*@ensures type.equals("GIORNALIERO") ==> \result instanceof AbbonamentoGiornaliero &&
	 *  type.equals("SETTIMANALE") ==> \result instanceof AbbonamentoSettimanale &&
	 *  type.equals("ANNUALE") ==> \result instanceof AbbonamentoAnnuale &&
	 *  (!type.equals("GIORNALIERO) && !type.equals("SETTIMANALE") && !type.equals("ANNUALE")) ==> \result == null
	@*/
	
	
	public static Abbonamento createAbbonamento(String type) {
		if(type.equals(GIORNALIERO)) {
			return new AbbonamentoGiornaliero();
		}else if(type.equals(SETTIMANALE)) {
			return new AbbonamentoSettimanale();
		}else if(type.equals(ANNUALE)) {
			Abbonamento abbonamentoAnnuale = new AbbonamentoAnnuale();
			abbonamentoAnnuale.Start();
			return abbonamentoAnnuale;
		}
		return null;
	}
	
	/**
	 * Dato il tipo di abbonamento e il timestamp di fine abbonamento ritorner� un oggetto Abbonamento del tipo desiderato 
	 * con scadenza il timestamp indicato.
	 * @param type
	 * @param fine_abbonamento
	 * @return Abbonamento
	 */
	
	/*@ensures type.equals("GIORNALIERO") ==> \result instanceof AbbonamentoGiornaliero &&
	 *  type.equals("SETTIMANALE") ==> \result instanceof AbbonamentoSettimanale &&
	 *  type.equals("ANNUALE") ==> \result instanceof AbbonamentoAnnuale &&
	 *  (!type.equals("GIORNALIERO) && !type.equals("SETTIMANALE") && !type.equals("ANNUALE")) ==> \result == null
	@*/
	public static Abbonamento createAbbonamento(String type,Timestamp fine_abbonamento) {
		if(type.equals(GIORNALIERO)) {
			return new AbbonamentoGiornaliero(fine_abbonamento);
		}else if(type.equals(SETTIMANALE)) {
			return new AbbonamentoSettimanale(fine_abbonamento);
		}else if(type.equals(ANNUALE)) {
			return new AbbonamentoAnnuale(fine_abbonamento);
		}
		return null;
	}
	
	/**
	 * Dato un Abbonamento restuisce il tipo.
	 * @param abb:Abbonamento
	 * @return String
	 */
	
	public static String getType(Abbonamento abb) {
		if(abb instanceof AbbonamentoGiornaliero) {
			return GIORNALIERO;
		}else if(abb instanceof AbbonamentoSettimanale) {
			return SETTIMANALE;
		}else if(abb instanceof AbbonamentoAnnuale) {
			return ANNUALE;
		}
		return null;
	}
	
}
