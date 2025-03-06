package ClassiPrincipali;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import Eccezioni.BikeNotFoundException;
import Eccezioni.RunNotFoundException;

/**
 * Questa classe rappresenta l'ente nel servizio bikesharing che si occupa di iniziare e terminare le corse gestendo i relativi pagamenti
 * dialogando con gli oggetti DAO di Corsa,Cliente e gestire e con l'oggetto Cassiere per pagare la corsa.
 */
public class GestoreCorse {

    /**
     * Default constructor
     */
	
	public final static int MULTA = 150;
    private GestoreCorse() {
        corse = new CorsaDaoImpl();
        clienti = new ClienteDaoImpl();
        cassiere = Cassiere.getInstance();
    }

    /**
     * 
     */
    
    private static GestoreCorse instance;
    private Cassiere cassiere;

    /**
     * 
     */
    private CorsaDao corse;

    private ClienteDao clienti;
    /**
     * @param Corsa c
     * @throws BikeNotFoundException 
     */
    public static GestoreCorse getInstance() {
    	if(instance == null) {
    		instance = new GestoreCorse();
    	}
    	return instance;
    }
    
    /**
     * aggiunge la corsa al database. Se il cliente non ha ancora l'abbonamento incominciato, viene cominciato qui
     * @param corsa
     */
    public void iniziaCorsa(Corsa corsa) {
    	if(!corsa.getClient().abbonamentoInCorso()) {
    		Cliente client = corsa.getClient();
    		client.startAbbonamento();
    		clienti.update(client);
    	}
    	corse.insert(corsa);
    }

    /**
     * termina la corsa associata all'utente assegnando eventuali multe o penalità e facendo pagare la corsa all'utente
     * @param Corsa c 
     * @param Rastrelliera r
     */
    public Timestamp termina(Corsa corsa,String rastrelliera){
    		Cliente client = corsa.getClient();
    		double prezzo = corsa.getPrice();
    		client.addDebito(prezzo);
            setPenalita(corsa,client);
            paga(client,client.getDebito());
            Timestamp ora_fine_corsa = new Timestamp(System.currentTimeMillis());
            corse.update(corsa,ora_fine_corsa , rastrelliera);
            return ora_fine_corsa;
    }
    
    /**
     *  questo metodo si occupa del pagamento del debito assegnato all'utente tramite Cassiere
     * @param client : Cliente
     * @param importo: double
     */
    private void paga(Cliente client,double importo) {
    	client.setDebito(importo);
    	clienti.update(client);
        boolean buonfine = cassiere.paga(client, importo);
        if(buonfine) {
        	client.setDebito(0);
        	clienti.update(client);
        }
    }
    
    /**
     * questo metodo si occupa di assegnare eventuali multe o penalità al cliente
     * @param corsa: Corsa
     * @param client : Cliente 
     */
    private void setPenalita(Corsa corsa,Cliente client) {
        if(corsa.isOver2h()) {
        	client.addPen();
        }
        if(!corsa.multata() && corsa.isOver24h()){
           client.addDebito(GestoreCorse.MULTA);
           corsa.setMulta();
        }
    }

    /**
     * Questo metodo ritorna l'insieme di clienti che hanno una corsa attiva da più di 24 ore
     * @return
     */
    public Set<Cliente> checkMulta() {
        Set<Corsa> corseattive = corse.getAllAttive();
        Set<Cliente> clienti = new HashSet<>();
        for (Corsa corsa : corseattive) {
            if(corsa.isOver24h()){
                clienti.add(corsa.getClient());
            }
        }
        return clienti;
    }
    
    /**
     * Questo metodo setta la multa a tutti i clienti che hanno superato le 24 ore di corsa
     * @return
     */
    public Set<Cliente> setMulta() {
    	Set<Corsa> corseattive = corse.getAllAttive();
        Set<Cliente> clients = new HashSet<>();
        for (Corsa corsa : corseattive) {
            if(corsa.isOver24h() && !corsa.multata()){
            	Cliente client = corsa.getClient();
                clients.add(client);
                corse.update(corsa,true);
                paga(client,MULTA);
            }
        }
        return clients;
    }
    
    
    /**
     * Restituisce un valore booleano che indica se il Cliente passato come argomento ha una corsa in attivo
     * @param c
     * @return
     */
    public boolean isActive(Cliente c) {
    	boolean corsaattiva = false;
    	try {
    		corse.getAttiva(c);
    		corsaattiva = true;
    	}catch(RunNotFoundException e) {
    		corsaattiva = false;
    	}
    	return corsaattiva;
    }
    
    /**
     * Restituisce un valore booleano che indica se il cliente ha terminato una corsa da meno di 5 minuti
     * @param c
     * @return boolean
     */
    public boolean appenaFinita(Cliente c) {
    	Corsa corsa = getLast(c);
    	if(corsa != null && corsa.appenaFinita()) {
    		return true;
    	}else {
    		return false;
    	}
    }

    /**
     * Se è attiva una corsa associata al Cliente c viene restituita quella corsa altrimenti viene restituito null
     * @param c
     * @return
     */
    
    //@ensures 
    public Corsa get(Cliente c){
    	try{
    		return corse.getAttiva(c);
    	}catch(RunNotFoundException e ) {
    		return null;
    	}
    }
    
    /**
     * restituisce tutte le corse effettuate dal cliente c
     * @param c
     * @return
     */
    public Set<Corsa> getAll(Cliente c){
    	return corse.getAll(c);
    }
    
    /**
     * restituisce tutte le corse attive del servizio bikesharing
     * @return
     */
    public Set<Corsa> getAllAttive(){
    	return corse.getAllAttive();
    }
    
    /**
     * data una bicicletta, se è associata una corsa attiva viene restituita quella corsa
     * @param b
     * @return Corsa
     */
    public Corsa get(Bicicletta b) {
    	try{
    		return corse.getAttiva(b);
    	}catch(RunNotFoundException e ) {
    		return null;
    	}
    }
    
    /**
     * ritorna l'ultima corsa del Cliente c
     * @param c
     * @return
     */
    public Corsa getLast(Cliente c) {
    	try{
    		return corse.getLast(c);
    	}catch(RunNotFoundException e ) {
    		return null;
    	}
    }
    
    

}