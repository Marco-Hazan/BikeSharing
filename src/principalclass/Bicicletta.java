package principalclass;

/**
 * Questa classe astratta rappresenta un oggetto bicicletta del servizio bikesharing.
 * Ogni bicicletta ha un codice e uno stato che di default � "OK".
 * Per ogni implementazione di questa classe bisogna specificare la tariffa dato un minutaggio tramite il metodo getPrezzo().
 */
public abstract class Bicicletta {

    /**
     * Default constructor
     */
    
    //@ invariant codice != null && stato != null
    private String codice;
    private String stato;


    /**
     * restituisce il codice di @this
     * @return String
     */
    public String getCodice() {
        return codice;
    }
    
    /*
     * Metodo costruttore che assegna a @this il codice dato
     */
    //@ensures codice != null && stato != null && this.codice == codice
    protected Bicicletta(String codice){
        this.codice = codice;
        this.stato = "OK";
    }

    /**
     * metodo che restituisce il prezzo di una corsa con @this dato il minutaggio (tempo)
     * @param tempo: int
     * @return double:prezzo
     */
    public abstract double getPrezzo(int tempo);
    
    //public abstract String getType();

    /*
     * setta lo stato della bici 
     */
    public void setStato(String s) {
    	this.stato = s;
    }
    
    /*
     * restituisce lo stato della bici
     */
    public String getStato() {
    	return stato;
    }
    
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Bicicletta){
            Bicicletta b = (Bicicletta) obj;
            return b.codice.equals(this.codice);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return codice.hashCode();
    }
}