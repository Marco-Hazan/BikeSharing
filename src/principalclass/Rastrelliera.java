package principalclass;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Map.Entry;

import exceptions.BikeNotFoundException;
import exceptions.FullRackException;
import exceptions.MorsaSbagliataException;
import exceptions.NoFreeSpotException;
import exceptions.PositionOccupiedException;
/**
 * 
 */
public class Rastrelliera implements Iterable<Bicicletta>{

    /**
     * Default constructor
     */
	public Rastrelliera(String nome,int size,int tot_morse_elettriche) {
		if(nome == null) {
			throw new NullPointerException();
		}
		if(size > 30 || size < 15) {
			throw new IllegalArgumentException("size troppo grande");
		}
		this.nome = nome;
		this.size = size;
		this.tot_morse_elettriche = tot_morse_elettriche;
		tot_morse_classiche = size - tot_morse_elettriche;
		bikes = new TreeMap<>();
		for(int i=1;i<=size;i++) {
			bikes.put(i, null);
		}
	}
	
    public Rastrelliera(String nome,Map<Integer,Bicicletta> biciclette,int size,int tot_morse_elettriche) {
    	tot_morse_classiche = size - tot_morse_elettriche;
    	this.tot_morse_elettriche = tot_morse_elettriche;
        this.nome = nome;
        if(size > 30 || size < 15) {
			throw new IllegalArgumentException("size non valido");
		}
        this.size = size;
        bikes = new TreeMap<>();
        if(biciclette.size() != size ){
            throw new IllegalArgumentException("Il numero di biciclette nella lista non corrisponde al size indicato");
        }
        for (Entry<Integer,Bicicletta> entry : biciclette.entrySet()) {
        	if(!correctPosition(entry.getValue(), entry.getKey())) {
        		throw new MorsaSbagliataException();
        	}
            bikes.put(entry.getKey(),entry.getValue());
        }
    }

    /**
     * 
     */
    
    private final int tot_morse_elettriche;
    private final int tot_morse_classiche;
    private Map<Integer,Bicicletta> bikes;
    

    /**
     * 
     */
    private final int size;

    private final String nome;

    public int getTot(){
    	int tot = 0;
        for(int i=1;i<=size;i++) {
        	if(bikes.get(i) instanceof Bicicletta) {
        		tot++;
        	}
        }
        return tot;
    }

    public String getNome(){
        return nome;
    }
    
    public int getSize() {
    	return size;
    }

    /**
     * @param Bicicletta b 
     * @return
     */
    public int get(Bicicletta b) {
        for(int i=1;i<=size;i++){
            if(b.equals(bikes.get(i))){
                return i;
            }
        }
        throw new IllegalArgumentException(b.toString() + "non � presente nella rastrelliera");
    }

    /**
     * @param int posizione 
     * @return
     */
    public Bicicletta get(int posizione) {
    	if(posizione > size) {
    		throw new IllegalArgumentException("posizione fuori ordine");
    	}
        return bikes.get(posizione);
    }

    /**
     * @return
     * @throws BikeNotFoundException 
     */
    public int get(String type)  throws BikeNotFoundException{
    	for(int i=1;i<=size;i++){
            if(bikes.get(i) != null && BiciclettaFactory.getType(bikes.get(i)).equals(type)){
                return i;
            }
        }
    	throw new BikeNotFoundException();
    }
    
    public int getTot(String type) {
    	int tot = 0;
    	for(int i=1;i<=size;i++){
            if(bikes.get(i) != null && BiciclettaFactory.getType(bikes.get(i)).equals(type)){
                tot++;
            }
        }
    	return tot;
    }
    
    
    
    public void addBikes(Set<Bicicletta> biciclette) {
    	int contaclassiche = BiciclettaFactory.conta(BiciclettaFactory.CLASSICA, biciclette);
    	int contaelettriche = BiciclettaFactory.conta(BiciclettaFactory.ELETTRICA, biciclette);
    	int contaseggiolini = BiciclettaFactory.conta(BiciclettaFactory.SEGGIOLINO, biciclette);
    	if(this.getTotFreeClassicSpots() >= contaclassiche && this.getTotFreeEletricSpots() >= contaelettriche + contaseggiolini) {
        	for(Bicicletta bicicletta: biciclette) {
        		addBike(bicicletta);
        	}
    	}
    }
    
    public boolean removeBike(Bicicletta b) {
    	for(int i=1;i<=size;i++) {
    		if(b.equals(bikes.get(i))) {
    			bikes.put(i, null);
    			return true;
    		}
    	}
    	return false;
    }
    
    public Set<Bicicletta> removeBikes(int totc,int tote,int tots) {
    	if(this.getTot(BiciclettaFactory.ELETTRICA) >= tote && this.getTot(BiciclettaFactory.CLASSICA) >= totc && this.getTot(BiciclettaFactory.CLASSICA) >= tots) {
    		Set<Bicicletta> biciclette = new HashSet<>();
        	for(int i=0;i<totc;i++) {
        		biciclette.add(this.removeBike(BiciclettaFactory.CLASSICA));
        	}
        	for(int i=0;i<tote;i++) {
        		biciclette.add(this.removeBike(BiciclettaFactory.ELETTRICA));
        	}
        	for(int i=0;i<tots;i++) {
        		biciclette.add(this.removeBike(BiciclettaFactory.SEGGIOLINO));
        	}
        	return biciclette;
    	}
    	return null;
    }

    /**
     * @return
     */
    public int getBicicletta() throws BikeNotFoundException{
        for(int i=1;i<=size;i++){
            if(bikes.get(i) != null){
                return i;
            }
        }
        throw new BikeNotFoundException();
    }
    
    public boolean isFull() {
    	return this.size == getTot();
    }
    
    public int getFreeEletricSpot() throws NoFreeSpotException {
    	for(int i=tot_morse_classiche + 1;i<=size;i++) {
    		if(get(i) == null) {
    			return i;
    		}
    	}
    	throw new NoFreeSpotException();
    }
    
    public int getFreeClassicSpot() throws NoFreeSpotException {
    	for(int i=1;i<=tot_morse_classiche;i++) {
    		if(get(i) == null) {
    			return i;
    		}
    	}
    	throw new NoFreeSpotException();
    }
    
    public boolean isEletric(int posizione) {
    	if(posizione>size) {
    		throw new IllegalArgumentException("fuori ordine");
    	}
    	return posizione>this.tot_morse_classiche;
    }
    
    public boolean isFree(int posizione) {
    	return bikes.get(posizione) == null;
    }
    
    public void addBike(Bicicletta b) throws FullRackException, NoFreeSpotException{
    	boolean e = BiciclettaFactory.getType(b).equals(BiciclettaFactory.ELETTRICA) || BiciclettaFactory.getType(b).equals(BiciclettaFactory.SEGGIOLINO);
    	if(!e) {
    		int pos = getFreeClassicSpot();
    		bikes.put(pos, b);
    	}else {
    		int pos = getFreeEletricSpot();
    		bikes.put(pos, b);
    	}
    }
    
    public void addBike(Bicicletta b, int posizione) throws MorsaSbagliataException{
    	if(posizione > this.size) {
    		throw new IllegalArgumentException("Posizione "+ Integer.toString(posizione) + " non valida per rastrelliera "+ this.nome);
    	}
    	if(bikes.get(posizione) != null) {
    		throw new PositionOccupiedException("Posizione occupata in posizione "+ Integer.toString(posizione));
    	}
    	if(!correctPosition(b,posizione)) {
    		throw new MorsaSbagliataException("morsa sbagliata");
    	}
    	bikes.put(posizione, b);
    }
    
    public Bicicletta removeBike(int posizione) throws BikeNotFoundException{
    	if(posizione > this.size) {
    		throw new IllegalArgumentException("Posizione "+ Integer.toString(posizione) + " non valida per rastrelliera "+ this.nome);
    	}
    	if(bikes.get(posizione) == null) {
    		throw new BikeNotFoundException("Nessuna bicicletta trovata in posizione "+ Integer.toString(posizione));
    	}
    	Bicicletta b = bikes.get(posizione);
    	bikes.put(posizione, null);
    	return b;
    }
    
    public Bicicletta removeBike(String tipo) {
    	int pos = this.get(tipo);
    	return removeBike(pos);
    }
    
    

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Rastrelliera){
            Rastrelliera r = (Rastrelliera)obj;
            return r.getNome().equals(nome);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return nome.hashCode();
    }
    
    public int getTotFreeEletricSpots() {
    	return size-tot_morse_classiche- (this.getTot(BiciclettaFactory.ELETTRICA) + this.getTot(BiciclettaFactory.SEGGIOLINO));
    }
    
    public int getTotFreeClassicSpots() {
    	return tot_morse_classiche - this.getTot(BiciclettaFactory.CLASSICA);
    }
    
    public String ExtendedToString(){
        String s = "Rastrelliera " + nome + "\n";
        for(int i=1;i<=size;i++){
        	if(bikes.get(i) instanceof Bicicletta) {
                s += "Postazione "+Integer.toString(i)+" : "+bikes.get(i).toString()+"\n";
        	}else {
        		s += "Postazione "+Integer.toString(i)+" : postazione libera \n";
        	}
        }
        return s;
    }
    
    @Override
    public String toString() {
    	return this.nome + ", Bici disponibili: "+ Integer.toString(getTot(BiciclettaFactory.CLASSICA)) + "C, "
    			+Integer.toString(getTot(BiciclettaFactory.ELETTRICA)) + "E, "+
    			Integer.toString(getTot(BiciclettaFactory.SEGGIOLINO)) + "S"+ "  ("+
    			Integer.toString(this.getTotMorseClassiche()-this.getTotFreeClassicSpots())+"/"+Integer.toString(this.getTotMorseClassiche())+", "+
    			Integer.toString(this.getTotMorseElettriche()-this.getTotFreeEletricSpots())+"/"+Integer.toString(this.getTotMorseElettriche())+")";
    }
    
    
    public boolean correctPosition(Bicicletta b,int posizione) {
    	if(b==null) return true;
    	return ((BiciclettaFactory.getType(b).equals(BiciclettaFactory.ELETTRICA)||BiciclettaFactory.getType(b).equals(BiciclettaFactory.SEGGIOLINO)) && isEletric(posizione)) || 
    			(BiciclettaFactory.getType(b).equals(BiciclettaFactory.CLASSICA) && !isEletric(posizione));
    }
    
    public int getTotMorseElettriche() {
    	return tot_morse_elettriche;
    }
    
    public int getTotMorseClassiche() {
    	return tot_morse_classiche;
    }

	@Override
	public Iterator<Bicicletta> iterator() {
		Set<Bicicletta> biciclette = new HashSet<>();
		for(int i=1;i<=size;i++) {
			biciclette.add(bikes.get(i));
		}
		return biciclette.iterator();
	}

}