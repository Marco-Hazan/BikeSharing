package ClassiPrincipali;

import java.sql.Timestamp;

/**
 * Questa classe rappresenta una corsa in corso o terminata di un utente del servizio bikesharing con una certa bicicletta.
 * Sono indicate anche la rastrelliera di partenza e la rastrelliera di arrivo come informazioni aggiuntive che indicano il nome delle rastrelliere
 * di partenza e di arrivo della corsa del cliente.
 * Sono specificate anche l'ora di inizio della corsa e l'eventuale ora di fine.
 */
public class Corsa {

    /**
     * invariante di rappresentazione:
     * cliente,bike,start, e rastrelliera_start non possono esssere nulli
     * end e rastrelliera_end invece possono essere nulli se la corsa non è ancora finita.
     * Se end è nullo anche rastrelliera_end deve essere nullo e viceversa.
     */
	
	//@invariant cliente != null && bike != null && start != null && (end!= null <==> rastrelliera_end != null)
	
	
    public Corsa(Cliente c,Bicicletta b,String rastrelliera,Timestamp start) {
        this.cliente = c;
        this.bike = b;
        this.start = start;
        rastrelliera_start = rastrelliera;
        rastrelliera_end = null;
        end = null;
    }

    public Corsa(Cliente c,Bicicletta b,String r,Timestamp start,String r2,Timestamp end){
        this.cliente = c;
        this.bike = b;
        this.start = start;
        rastrelliera_start = r;
        this.rastrelliera_end = r2;
        this.end = end;
    }

    /**
     * 
     */
    private Bicicletta bike;

    /**
     * 
     */
    private Cliente cliente;

    /**
     * 
     */
    private Timestamp start;

    /**
     * 
     */
    private String rastrelliera_start;

    private String rastrelliera_end;
    private Timestamp end;
    private boolean multata;



    /**
     * restituisce Bicicletta associata alla corsa
     * @return Bicicletta
     */
    public Bicicletta getBike() {
        return bike;
    }

    /**
     * retituisce Cliente associato alla corsa
     * @return Cliente
     */
    public Cliente getClient() {
        return cliente;
    }

    /**
     * restituisce timestamp di inizio corsa
     * @return Timestamp
     */
    public Timestamp getStart() {
        return start;
    }


    /**
     * restituisce nome della rastrelliera di partenza
     * @return Rastrelliera
     */
    public String getRPartenza(){
        return rastrelliera_start;
    }
    
    /**
     * restituisce nome della rastrelliera di arrivo
     * @return Rastrelliera
     */
    public String getRArrivo() {
    	return rastrelliera_end;
    }

    /**
     * restituisce valore booleano che indica se la corsa è finita oppure no
     * @return
     */
    public boolean isOver(){
        return !(rastrelliera_end == null && end == null);
    }
    
    //@ensures multata == true
    public void setMulta() {
    	multata = true;
    }
    
    /**
     * resttuisce un valore booleano che indica se la corsa è stata multata oppure no
     * @return boolean
     */
    public boolean multata() {
    	return multata;
    }

    
    /**
     * restituisce il minutaggio della corsa.
     * Se la corsa non è finita viene calcolato il minutaggio da adesso al timestamp di inizio corsa sennò viene calcolato il minutaggio
     * tra inizio e fine corsa.
     * @return int
     */
    public int calcola() {
        if(!isOver()){
            Timestamp actual = new Timestamp(System.currentTimeMillis());
            return (int) (actual.getTime() - start.getTime())/60000;
        }else{
            return (int) (end.getTime() - start.getTime())/60000;
        }
    }
    
    
    /**
     * restituisce un valore booleano che indica se la corsa è finita da meno di 5 minuti
     * @return boolean
     */
    public boolean appenaFinita() {
    	return isOver() && ((System.currentTimeMillis() - this.end.getTime())/60000)<5;
    }
    
    
    /**
     * restituisce prezzo della corsa. Se il cliente associato a @this è uno studente e la bicicletta associata a @this viene ritornato 0.
     * @return double
     */
    
    //@ensures !cliente.isStudent() && !BiciclettaFactory.getType(bike).equals(BiciclettaFactory.CLASSICA) ==> \result == bike.getPrezzo(calcola())
    //@ensures cliente.isStudent() && BiciclettaFactory.getType(bike).equals(BiciclettaFactory.CLASSICA) ==> \result == 0
    public double getPrice() {
    	if(BiciclettaFactory.getType(bike).equals(BiciclettaFactory.CLASSICA) && cliente.isStudent()) {
    		return 0;
    	}
    	return bike.getPrezzo(calcola());
    }

    /**
     * restituisce un valore booleano che indica se la corsa è finita da più di 2 ore
     * @return boolean
     */
    public boolean isOver2h() {
        return calcola() >= 120;
    }

    /**
     * restituisce un valore booleano che indica se la corsa è finita da più di 24 ore
     * @return boolean
     */
    public boolean isOver24h() {
        return calcola() >= 1440;
    }
    
    /**
     * restituisce il tipo di bici associata a @this. Il valore di ritorno è una stringa che viene specificata in un attributo di BiciclettaFactory
     * specifico per quel tipo di bici
     * @return
     */
    public String getBikeType() {
    	return BiciclettaFactory.getType(bike);
    }
    
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Corsa){
            Corsa c = (Corsa) obj;
            return c.cliente.equals(this.cliente) && c.start.equals(this.start);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(this.cliente.hashCode() + start.hashCode());
    }

    @Override
    public String toString(){
        return "Corsa da "+rastrelliera_start+", utente:"+cliente.getCodice()+", bicicletta: "+ bike.toString();
    }

}