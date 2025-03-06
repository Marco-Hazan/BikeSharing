package principalclass;

public interface CartaDao {
	
	public void registraCarta(CreditCard c);
	
	public void update(CreditCard c,double importo);
	
	public double getDisponibilita(CreditCard carta);
	
	public CreditCard get(String numero);
	
	public void delete(CreditCard carta);
}
