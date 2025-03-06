package application;


import java.util.Random;

import ClassiPrincipali.Abbonamento;
import ClassiPrincipali.AbbonamentoFactory;
import ClassiPrincipali.Bicicletta;
import ClassiPrincipali.BiciclettaDao;
import ClassiPrincipali.BiciclettaDaoImpl;
import ClassiPrincipali.CartaDao;
import ClassiPrincipali.CartaDaoImpl;
import ClassiPrincipali.Cassiere;
import ClassiPrincipali.Cliente;
import ClassiPrincipali.ClienteDao;
import ClassiPrincipali.ClienteDaoImpl;
import ClassiPrincipali.CreditCard;
import ClassiPrincipali.ValidazioneStudenti;
import Eccezioni.BikeNotFoundException;
import Eccezioni.IllegalCardException;
import Eccezioni.LoginException;
import Eccezioni.RoofReachedException;
import Eccezioni.UserNotFoundException;
import controllers.appuser.RecData;

public class UserApp {

	public final static String SCADUTO = "scaduto";
	public final static String ANNULLATO = "annullato";
	public final static String SOSPESO = "sospeso";
	public final static String OK = "ok";
	
	private ClienteDao clienti;
	private Cliente current_client;
	private BiciclettaDao biciclette;
	
	
	public UserApp() {
		clienti = new ClienteDaoImpl();
		biciclette = new BiciclettaDaoImpl();
	}
	
	public boolean usernameUnivoco(String username) {
		return clienti.isUnique(username);
	}
	
	
	
	public boolean validaStudente(String universita,String matricola) {
		return ValidazioneStudenti.check(universita, matricola);
	}
	
	public String attivaAbbonamento(RecData recdata) {
		CreditCard carta = recdata.getCarta();
		if(!validaCarta(carta,recdata.getTipo())) {
			throw new IllegalCardException();
		}
		CartaDao carte = new CartaDaoImpl();
		carte.registraCarta(carta); 
		Abbonamento abbonamento = AbbonamentoFactory.createAbbonamento(recdata.getTipo());
		boolean buonfine = Cassiere.getInstance().paga(carta, abbonamento.getCosto());
		if(buonfine) {
			String codiceClient = generaCodice();
			Cliente cliente = new Cliente(codiceClient,recdata.getUsername(),recdata.getPwd(),recdata.studente(),0,carta,abbonamento);
			clienti.insert(cliente);
			current_client = cliente;
			return cliente.getCodice();
		}else {
			throw new RoofReachedException();
		}
	}
	
	public boolean autenticazione(String username,String pwd) {
		try {
			current_client = clienti.classicLogin(username, pwd);
			return true;
		} catch (UserNotFoundException | LoginException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public String getCodiceUtente() {
		return current_client.getCodice();
	}
	
	public Cliente getCurrentClient() {
		return current_client;
	}
	
	public String getStatoUtente() {
		if(current_client.getAbbonamento().isOver()){
			return SCADUTO;
		}else if(current_client.getPen() >= 3) {
			return ANNULLATO;
		}else if(current_client.getDebito() > 0) {
			return SOSPESO;
		}else{
			return OK;
		}
	}
	
	public boolean modificaPwd(String vecchia,String nuova) {
		if(!current_client.getPwd().equals(vecchia)) {
			return false;
		}else {
			current_client.setPwd(nuova);
			clienti.update(current_client);
			return true;
		}
	}
	
	public boolean validaCarta(CreditCard carta,String tipo_abbonamento) {
		if(tipo_abbonamento.equals(AbbonamentoFactory.GIORNALIERO) ||tipo_abbonamento.equals(AbbonamentoFactory.SETTIMANALE)) {
			if(carta.isExpired(1)) {
				return false;
			}
		}else if(tipo_abbonamento.equals(AbbonamentoFactory.ANNUALE)) {
			if(carta.isExpired(12)) {
				return false;
			}
		}
		return true;
	}
	
	public void modificaPagamento(CreditCard carta) {
		boolean cartavalida = validaCarta(carta,current_client.getTypeAbbonamento());
		if(!cartavalida) {
			throw new IllegalCardException();
		}
		CartaDao carte = new CartaDaoImpl();
		carte.registraCarta(carta);
		current_client.setCarta(carta);
		clienti.update(current_client);
		if(current_client.getDebito() > 0) {
			Cassiere cassa = Cassiere.getInstance();
			boolean buonfine = cassa.paga(current_client, current_client.getDebito());
			if(buonfine) {
				current_client.setDebito(0);
				clienti.update(current_client);
			}
		}
	}
	
	public boolean segnalaDanno(String codice,String danno) {
		try{
			Bicicletta bici = biciclette.get(codice);
			if(bici.getStato().equals("OK")) {
				bici.setStato("Danno riportato da utente "+current_client.getCodice()+": "+danno+"\n");
			}else {
				bici.setStato(bici.getStato()+"Danno riportato da utente "+current_client.getCodice()+": "+danno+"\n");
			}
			biciclette.update(bici);
			return true;
		}catch(BikeNotFoundException e) {
			return false;
		}
	}
	
	public boolean logout() {
		if(current_client != null) {
			current_client = null;
			return true;
		}
		return false;
	}
	
	private String generaCodice() {
    	char codice[] = new char[7];
    	char array[] = {
    			'A','B','C','D','E','F','G','H','I','J','K','L','M',
    			'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
    			'1','2','3','4','5','6','7','8','9'
    	};
    	Random r = new Random();
    	for(int i=0;i<7;i++) {
    		int random = Math.abs(r.nextInt())% 35;
    		codice[i] = array[random];
    	}
    	String codiceString = new String(codice,0,7);
    	while(clienti.get(codiceString) != null) {
    		for(int i=0;i<7;i++) {
        		int random = Math.abs(r.nextInt())% 35;
        		codice[i] = array[random];
        	}
    		codiceString = new String(codice,0,7);
    	}
    	return codiceString;
    }
}
