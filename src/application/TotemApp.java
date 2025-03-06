package application;
import java.sql.Timestamp;
import ClassiPrincipali.Bicicletta;
import ClassiPrincipali.BiciclettaFactory;
import ClassiPrincipali.Cliente;
import ClassiPrincipali.ClienteDao;
import ClassiPrincipali.ClienteDaoImpl;
import ClassiPrincipali.Corsa;
import ClassiPrincipali.GestoreCorse;
import ClassiPrincipali.Rastrelliera;
import ClassiPrincipali.RastrellieraDao;
import ClassiPrincipali.RastrellieraDaoImpl;
import Eccezioni.BikeNotFoundException;
import Eccezioni.LoginException;
import Eccezioni.MorsaSbagliataException;
import Eccezioni.PositionOccupiedException;
import Eccezioni.UserNotFoundException;

public class TotemApp {
	
	public TotemApp(Rastrelliera rastrelliera) {
		this.rastrelliera = rastrelliera;
		clienti = new ClienteDaoImpl();
		rastrelliere = new RastrellieraDaoImpl();
		gestoreCorse = GestoreCorse.getInstance();
	}
	public final static String DISABILITATO = "disabilitato";
	public final static String ABILITATO = "abilitato";
	public final static String CORSAATTIVA = "corsa attiva";
	public final static String CORSACONCLUSA = "corsa conclusa";
	private int posizione_sbloccata;

	
	private ClienteDao clienti;
	private Rastrelliera rastrelliera;
	private RastrellieraDao rastrelliere;
	private GestoreCorse gestoreCorse;
	private Cliente current_client;
	private Corsa corsadachiudere;
	private Corsa corsaConclusa;

	public String autenticazione(String codice,String pwd){
		try {
			current_client = clienti.loginClient(codice,pwd);
			if(gestoreCorse.appenaFinita(current_client)) {
				corsaConclusa = gestoreCorse.getLast(current_client);
				return CORSACONCLUSA;
			};
			if(gestoreCorse.isActive(current_client)) {
				corsadachiudere = gestoreCorse.get(current_client);
				return CORSAATTIVA;
			};
			if (current_client.disabilitato()) {
				return DISABILITATO;
			}
			return ABILITATO;
			
		} catch (UserNotFoundException | LoginException e) {
			// TODO Auto-generated catch block
			throw new LoginException();
		}
	}
	
	 public int askBike(String tipo) {
		 if(current_client==null) {
			 return -1;
		 }
	    	try {
	    		int posizione = rastrelliera.get(tipo);
	    		Bicicletta b = rastrelliera.removeBike(posizione);
	    		rastrelliere.update(rastrelliera);
	    		gestoreCorse.iniziaCorsa(new Corsa(current_client,b,rastrelliera.getNome(),new Timestamp(System.currentTimeMillis())));
	    		logout();
	    		return posizione;
	    	}catch(BikeNotFoundException e) {
	    		throw new BikeNotFoundException();
	    	}
	  }
	 
	 public boolean agganciaBici(int posizione) {
		 if(current_client==null) {
			 return false;
		 }
		 Bicicletta bicicletta = gestoreCorse.get(this.current_client).getBike();
		 try{
			 rastrelliera.addBike(bicicletta,posizione);
			 rastrelliere.update(rastrelliera);
			 Corsa corsa = gestoreCorse.get(bicicletta);
			 if(corsa == null) {
				 return true;
			 }
			 gestoreCorse.termina(corsa, rastrelliera.getNome());
			 logout();
			 return true;
		 }catch(MorsaSbagliataException e) {
			 return false;
		 }catch(PositionOccupiedException e) {
			 return false;
		 }
	 }
	 
	 /* ritorna l'ultima posizione sbloccata */
	 
	 public int getPosizioneSbloccata() {
		 return posizione_sbloccata;
	 }
	 
	 public Rastrelliera getRastrelliera() {
		 return rastrelliera;
	 }
	 
	 public Corsa getCorsaConclusa() {
		 return corsaConclusa;
	 }
	 
	 public boolean valuta(int posizione) {
		 return (BiciclettaFactory.getType(corsadachiudere.getBike()).equals(BiciclettaFactory.ELETTRICA) || BiciclettaFactory.getType(corsadachiudere.getBike()).equals(BiciclettaFactory.SEGGIOLINO)) && (rastrelliera.isEletric(posizione))||
		 (BiciclettaFactory.getType(corsadachiudere.getBike()).equals(BiciclettaFactory.CLASSICA) && !rastrelliera.isEletric(posizione));
	 }
	 
	 public void logout() {
		 current_client = null;
	 }
	 
	
}