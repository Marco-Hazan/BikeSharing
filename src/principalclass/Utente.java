package principalclass;

import java.util.Random;

/**
 * 
 */
public abstract class Utente {

    /**
     * Default constructor
     */
    protected Utente(String codice,String pwd) {
        if(codice == null || pwd == null){
            throw new NullPointerException("codice utente o password nulli");
        }
        this.codice = codice;
        this.pwd = pwd;
    }

    /**
     * 
     */
    private String codice;
    
    /**
     * 
     */
    private String pwd;


    /**
     * @return
     */
    public String getCodice() {
        return codice;
    }

    /**
     * @return
     */
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
    	this.pwd = pwd;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Utente){
            Utente u = (Utente) obj;
            return u.codice.equals(this.codice);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return codice.hashCode();
    }
    
    private static String generaCodice() {
    	char codice[] = new char[7];
    	char array[] = {
    			'A','B','C','D','E','F','G','H','I','J','K','L','M',
    			'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
    			'1','2','3','4','5','6','7','8','9'
    	};
    	Random r = new Random();
    	for(int i=0;i<7;i++) {
    		int random = Math.abs(r.nextInt())% 35;
    		codice[i] = array[random];
    	}
    	return new String(codice,0,7);
    }
    
    public static void main(String args[]) {
    	System.out.println(generaCodice());
    }

}