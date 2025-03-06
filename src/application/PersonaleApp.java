package application;

import java.util.Set;

import Eccezioni.BikeNotFoundException;
import Eccezioni.FullRackException;
import Eccezioni.LoginException;
import Eccezioni.NoFreeSpotException;
import Eccezioni.RackNotFoundException;
import Eccezioni.UserNotFoundException;
import principalclass.Bicicletta;
import principalclass.BiciclettaDao;
import principalclass.BiciclettaDaoImpl;
import principalclass.BiciclettaFactory;
import principalclass.GestoreCorse;
import principalclass.PersonaleDao;
import principalclass.PersonaleDaoImpl;
import principalclass.Rastrelliera;
import principalclass.RastrellieraDao;
import principalclass.RastrellieraDaoImpl;

public class PersonaleApp {

	private boolean autenticato;
	PersonaleDao personale;
	RastrellieraDao rastrelliere;
	BiciclettaDao biciclette;
	GestoreCorse gc;
	public PersonaleApp() {
		personale = new PersonaleDaoImpl();
		rastrelliere = new RastrellieraDaoImpl();
		biciclette = new BiciclettaDaoImpl();
		gc = GestoreCorse.getInstance();
	}

	public boolean autenticazione(String codice,String password) {
		try{
			personale.loginPersonale(codice, password);
			autenticato = true;
			return true;
		}catch(UserNotFoundException | LoginException e) {
			return false;
		}
	}
	
	public boolean aggiungiBici(String nome_rastrelliera,int totc,int tote,int tots) throws NoFreeSpotException {
		if(!autenticato) return false;
		Rastrelliera rastrelliera = rastrelliere.get(nome_rastrelliera);
		if(rastrelliera == null) {
			return false;
		}
		if(rastrelliera.getTotFreeEletricSpots() >= tote+tots && rastrelliera.getTotFreeClassicSpots() >= totc) {
			Set<Bicicletta> bikes = BiciclettaFactory.generaBiciclette(totc,tote,tots);
			for(Bicicletta b:bikes) {
				biciclette.insert(b);
			}
			rastrelliera.addBikes(bikes);
			rastrelliere.update(rastrelliera);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean rimuoviBici(String nome_rastrelliera,int totc,int tote,int tots){
		if(!autenticato) return false;
		Rastrelliera rastrelliera = rastrelliere.get(nome_rastrelliera);
		if(rastrelliera.getTot(BiciclettaFactory.CLASSICA) >= totc && rastrelliera.getTot(BiciclettaFactory.ELETTRICA) >= tote && rastrelliera.getTot(BiciclettaFactory.SEGGIOLINO) >= tots) 
		{
			rastrelliera.removeBikes(totc, tote, tots);
			rastrelliere.update(rastrelliera);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean riloca(String rastrelliera_srg,String rastrelliera_dst,int totc,int tote,int tots) throws FullRackException, NoFreeSpotException {
		if(!autenticato) return false;
		if(rastrelliera_srg.equals(rastrelliera_dst)) {
			return false;
		}
		Rastrelliera srg = rastrelliere.get(rastrelliera_srg);
		Rastrelliera dst = rastrelliere.get(rastrelliera_dst);
		if(srg == null || dst == null) {
			return false;
		}
		if(srg.getTot(BiciclettaFactory.CLASSICA) >= totc && srg.getTot(BiciclettaFactory.ELETTRICA) >= tote && srg.getTot(BiciclettaFactory.SEGGIOLINO) >= tots) {
			if(dst.getTotFreeClassicSpots() >= totc && dst.getTotFreeEletricSpots() >= tote+tots) {
				Set<Bicicletta> bikes = srg.removeBikes(totc, tote, tots);
				rastrelliere.update(srg);
				dst.addBikes(bikes);
				rastrelliere.update(dst);
				return true;
			}else {
				throw new NoFreeSpotException();
			}
		}else {
			throw new BikeNotFoundException();
		}
	}
	
	public boolean aggiungiRastrelliera(String nome,int size,int tot_morse_elettriche){
		if(!autenticato) {
			return false;
		}
		try{
			Rastrelliera rastrelliera = new Rastrelliera(nome,size,tot_morse_elettriche);
			if(rastrelliere.get(nome) == null) {
				rastrelliere.insert(rastrelliera);
				return true;
			}else {
				return false;
			}
		}catch(IllegalArgumentException e) {
			return false;
		}
	}
	
	public boolean rimuoviBici(String codice) {
		if(!autenticato) return false;
		try {
			Bicicletta b = biciclette.get(codice);
			if(gc.get(b) == null) {
				Rastrelliera r = rastrelliere.get(biciclette.getRastrelliera(b));
				boolean rimossa = r.removeBike(b);
				if(rimossa) {
					rastrelliere.update(r);
					return true;
				}
			}
			return false;
		}catch(BikeNotFoundException e ) {
			return false;
		}
	}
	
	public boolean rimuoviRastrelliera(String nome) {
		if(!autenticato) return false;
		rastrelliere.delete(nome);
		return true;
	}
	
	public void logout() {
		autenticato = false;
	}
}
