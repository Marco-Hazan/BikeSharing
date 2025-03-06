package ClassiPrincipali;


import java.sql.SQLException;
import java.util.Set;

import Eccezioni.UserNotFoundException;

/**
 * 
 */
public interface ClienteDao {

    /**
     * Default constructor
     */

    /**
     * @param String codice 
     * @return
     */
    public Cliente get(String codice) throws UserNotFoundException;

    /**
     * @param Utente u 
     * @param String pwd
     */
    public void update(Cliente c);

    /**
     * @return
     */
    public Set<Cliente> getAllClients();

    /**
     * @param Utente u
     */

    /**
     * @param Persona p 
     * @param String pwd 
     * @return
     * @throws SQLException 
     */
    public boolean insert(Cliente client);
    
    
    public Cliente classicLogin(String username,String password) throws UserNotFoundException;
    
    public Cliente loginClient(String codice, String password) throws UserNotFoundException;
    
    public boolean isUnique(String username);
    
    public void delete(Cliente client);
}