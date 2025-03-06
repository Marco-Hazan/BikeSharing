package principalclass;

/**
 * Implementazione di Bicicletta.
 * Tariffa bici elettrica:
 * 0� (0-2 min)
 * 0.25 � (3-29min)
 * 0.75� (30-59min)
 * 1.75� (60-89min)
 * 3.75� (90-119min)
 * 4�/h (120min - )
 */
public class BiciConSeggiolino extends Bicicletta {

    /**
     * Default constructor
     */
    public BiciConSeggiolino(String codice) {
        super(codice);
    }
    
    @Override
    public double getPrezzo(int tempo){
    	if(tempo <3) return 0.0;
        if(tempo < 30) return 0.25;
        if(tempo < 60) return 0.75;
        if(tempo < 90) return 1.75;
        if(tempo < 120) return 3.75;
        return 3.75 + (tempo / 60 - 1)*4; 
    }
    
    /*@Override
    public String getType() {
    	return Bicicletta.SEGGIOLINO;
    }*/

    @Override
    public String toString(){
        return "Bicicletta elettrica con seggiolino " + super.getCodice();
    }

}