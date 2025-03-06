package principalclass;

import java.util.Set;

import exceptions.UserNotFoundException;

public interface PersonaleDao {
	
	public Personale get(String codice) throws UserNotFoundException;
	
	public void insert(Personale p);
	
	
	public void update(Personale p);
	
	public void delete(Personale p);
	
	public Set<Personale> getAll();
	
	public Personale loginPersonale(String codice,String password) throws UserNotFoundException;
	
}
