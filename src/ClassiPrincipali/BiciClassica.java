package ClassiPrincipali;

/**
 * Implementazione di Bicicletta.
 * Tariffa bici classica:
 * 0 € (1-29min)
 * 0.5€ / 30min (30-119min)
 * 2€/h (120min - )
 */
public class BiciClassica extends Bicicletta {

    /**
     * Default constructor
     */
	
	
	
    public BiciClassica(String codice) {
        super(codice);
    }

    @Override
    public double getPrezzo(int tempo){
        if(tempo < 30) return 0;
        if(tempo < 120) return 0.5 * (tempo / 30.0); 
        return 1.5 + ((tempo/60)-1)*2;
    }

    @Override
    public String toString(){
        return "Bicicletta classica " + super.getCodice();
    }
    
    

}