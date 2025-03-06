package ClassiPrincipali;

import java.util.Set;

import Eccezioni.BikeNotFoundException;

public interface BiciclettaDao {

    public Bicicletta get(String codice) throws BikeNotFoundException;

    public void insert(Bicicletta b,String nome_rastrelliera,int posizione);
    
    public String getRastrelliera(Bicicletta bicicletta);
    
    public void insert(Bicicletta b);
    
    public void update(Bicicletta b);
    
    public boolean delete(Bicicletta b);
    
    public Set<Bicicletta> getBiciDanneggiate();

}
