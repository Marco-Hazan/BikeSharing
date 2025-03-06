package ClassiPrincipali;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Eccezioni.BikeNotFoundException;

public class BiciclettaFactory {
	public static final String CLASSICA = "C";
	public static final String ELETTRICA = "E";
	public static final String SEGGIOLINO = "S";
	
	public static Set<Bicicletta> generaBiciclette(int totc, int tote, int tots){
		Set<Bicicletta> bikes = new HashSet<>();
		for(int i=0;i<totc;i++) {
			bikes.add(generaBici(CLASSICA));
		}
		for(int i=0;i<tote;i++) {
			bikes.add(generaBici(ELETTRICA));
		}
		for(int i=0;i<tots;i++) {
			bikes.add(generaBici(SEGGIOLINO));
		}
		return bikes;
	}
	
	
	public static Bicicletta generaBici(String type) {
		if(!type.equals(CLASSICA) && !type.equals(ELETTRICA) && !type.equals(SEGGIOLINO)) {
			return null;
		}
		BiciclettaDao biciclette = new BiciclettaDaoImpl();
		while(true) {
			String codice = generaCodice(type);
			try {
				biciclette.get(codice);
			}catch(BikeNotFoundException e) {
				if(type.equals(CLASSICA)) {
					return new BiciClassica(codice);
				}else if(type.equals(ELETTRICA)){
					return new BiciElettrica(codice);
				}else if(type.equals(SEGGIOLINO)) {
					return new BiciConSeggiolino(codice);
				}
			}
		}
	}
	
	public static Bicicletta generaBici(String codice,String type) {
		if(type.equals(CLASSICA)) {
			return new BiciClassica(codice);
		}else if(type.equals(ELETTRICA)) {
			return new BiciElettrica(codice);
		}else if(type.equals(SEGGIOLINO)){
			return new BiciConSeggiolino(codice);
		}
		return null;
	}
	
	private static String generaCodice(String type) {
		if(!type.equals(CLASSICA) && !type.equals(ELETTRICA) && !type.equals(SEGGIOLINO)) {
			return null;
		}
    	char randomstring[] = new char[8];
    	char characters[] = {
    			'1','2','3','4','5','6','7','8','9','0'
    	};
    	if(type.equals(CLASSICA)) {
    		randomstring[0] = 'C' ;
    	}else if(type.equals(ELETTRICA)) {
    		randomstring[0] = 'E';
    	}else if(type.equals(SEGGIOLINO)){
    		randomstring[0] = 'S';
    	}
    	Random r = new Random();
    	for(int i=1;i<8;i++) {
    		int random = Math.abs(r.nextInt())% 10;
    		randomstring[i] = characters[random];
    	}
    	return new String(randomstring,0,8);
    }
	
	public static String getType(Bicicletta b) {
		if(b instanceof BiciClassica) {
			return CLASSICA;
		}else if(b instanceof BiciElettrica) {
			return ELETTRICA;
		}else if(b instanceof BiciConSeggiolino) {
			return SEGGIOLINO;
		}
		return null;
	}
	
	public static int conta(String type,Set<Bicicletta> bikes) {
		int count = 0;
		for(Bicicletta b:bikes) {
			if(getType(b).equals(type)) {
				count++;
			}
		}
		return count;
	}
}
