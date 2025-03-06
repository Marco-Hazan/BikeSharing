package principalclass;

public class ValidazioneStudenti {
	
	private static final String lista_universita[] = {
			"Bocconi",
			"Bicocca",
			"Statale",
			"Cattolica",
			"Politecnico di Milano"
	};

	public static String[] getUniversita() {
		return lista_universita;
	}
	
	
	public static boolean check(String universita,String matricola) {
		for(String s: lista_universita) {
			if(universita.equals(s)) {
				return matricola.length() == 6;
			}
		}
		return false;
	}
	
}
