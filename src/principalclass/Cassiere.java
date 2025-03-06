package principalclass;

import Eccezioni.RoofReachedException;

public class Cassiere {

	ServizioPagamento sp;
	private static Cassiere instance;
	
	private Cassiere() {
		sp = ServizioPagamento.getInstance();
	}
	
	public static Cassiere getInstance() {
		if(instance == null) {
			instance = new Cassiere();
		}
		return instance;
	}
	
	
	public boolean paga(Cliente c,double importo) {
		CreditCard cc = c.getCarta();
		try{
			sp.pagamento(cc, importo);
			return true;
		}catch(RoofReachedException e) {
			return false;
		}
	}
	
	public boolean paga(CreditCard cc, double importo) {
		try{
			sp.pagamento(cc, importo);
			return true;
		}catch(RoofReachedException e) {
			return false;
		}
	}
	
}
