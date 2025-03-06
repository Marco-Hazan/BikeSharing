package ClassiPrincipali;


import Eccezioni.IllegalCardException;
import Eccezioni.RoofReachedException;

public class ServizioPagamento {
	private CartaDao carte;
	
	private static ServizioPagamento instance;
	
	private ServizioPagamento() {
		carte = new CartaDaoImpl();
	}
	
	public static ServizioPagamento getInstance() {
		if(instance == null) {
			instance = new ServizioPagamento();
		}
		return instance;
	}
	
	
	
	public boolean pagamento(CreditCard carta,double importo) {
		double disponibilita = carte.getDisponibilita(carta);
		if(disponibilita == -1) {
			throw new IllegalCardException("Carta non trovata");
		}
		if(disponibilita < importo) {
			throw new RoofReachedException();
		}
		carte.update(carta,importo);
		return true;
	}
	
	
}
