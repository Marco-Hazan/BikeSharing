package principalclass;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import Eccezioni.RunNotFoundException;

/**
 * 
 */
public interface CorsaDao {

    /**
     * Default constructor
     *



    /**
     * 
     */

    /**
     * @param Bicicletta b 
     * @return
     */
    public Corsa getAttiva(Bicicletta b);

    /**
     * @param Utente u 
     * @return
     */
    public Corsa getAttiva(Cliente c) throws RunNotFoundException;

    /**
     * @param Corsa c
     */
    public void insert(Corsa c);

    /**
     * @param Corsa c 
     * @param int fine 
     * @param Rastrelliera r
     */
    
    public void update(Corsa c,boolean multata);
    
    public void update(Corsa c,Timestamp fine,String rastrelliera);

    public Set<Corsa> getAll(Cliente c);
    
    public List<Corsa> getAll();

    public int totAttive();
    
    public long tot();
    
    public Corsa getLast(Cliente c);

    public Set<Corsa> getAllAttive();
}