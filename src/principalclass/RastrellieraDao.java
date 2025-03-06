package principalclass;




import Eccezioni.RackNotFoundException;


import java.util.Set;

/**
 * 
 */
public interface RastrellieraDao {

    /**
     * Default constructor
     */


    /**
     * @param String nome 
     * @return
     */
    public Rastrelliera get(String nome) throws RackNotFoundException;

    /**
     * @param Rastrelliera r 
     * @param int posizione 
     * @param Bicicletta b
     */
    //public void addBike(Rastrelliera r, int posizione,Bicicletta b) throws RackNotFoundException,FullRackException,BikeNotFoundException;

    /**
     * @param Rastrelliera r 
     * @param int posizione
     */
    //public Bicicletta removeBike(Rastrelliera r,int posizione) throws RackNotFoundException,BikeNotFoundException;

    /**
     * @return
     */
    public Set<Rastrelliera> getAllRastrelliere();
    
    public void insert(Rastrelliera rastrelliera);

    
    public void update(Rastrelliera r);
    
    public boolean delete(String nome);
}