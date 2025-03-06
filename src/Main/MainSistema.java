package Main;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import principalclass.Cliente;
import principalclass.CorsaDao;
import principalclass.CorsaDaoImpl;
import principalclass.GestoreCorse;

/**
 * Questo main serve per settare le multe.
 * Va eseguito in background durante l'utilizzo del sistema.
 * Simula il server centrale.
 *
 */
public class MainSistema {
	public static void main(String args[]) {
		GestoreCorse gc = GestoreCorse.getInstance();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  Set<Cliente> clients =gc.setMulta();
				  if(clients.size() > 0) {
					  CorsaDao corse = new CorsaDaoImpl();
					  for(Cliente c:clients) {
						  System.out.println("Multa data a " + c.getCodice()+ ". "+ corse.getAttiva(c));
					  }
				  }
			  }
		}, 0,10000);
	}
}
