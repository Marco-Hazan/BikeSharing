package ClassiPrincipali;



/**
 * Implementazione della classe astratta Utente.
 * Questa classe rappresenta un cliente del servizio bikesharing
 * Ogni cliente ha un username, una carta di credito (CreditCard) e un abbonamento al servizio (Abbonamento), oltre codice e password ereditati da Utente.
 * Ogni cliente inoltre ha anche un attributo di debito che indica il debito eventuale al servizio, un attributo booleano che indica se è uno studente
 * oppure no e un attributo di penalità che indica quante penalità sono state assegnate a lui.
 */
public class Cliente extends Utente {

    /**
     * Default constructor
     */

	//@ensures username != null && this.studente == studente && this.carta == carta && this.abbonamento == abb
    public Cliente(String codice,String username,String pwd,boolean studente,double debito,CreditCard carta,Abbonamento abb) {
    	super(codice,pwd);
    	this.username = username;
    	this.studente = studente;
    	this.carta = carta;
    	this.debito = debito;
    	this.abbonamento = abb;
    }

    /**
     * 
     */
    private double debito;

    /**
     * 
     */

    /**
     * 
     */
    private String username;
    
    private boolean studente;
    
    
    private CreditCard carta;
    
    
    private Abbonamento abbonamento;
    
    private int penalita;
    

    /**
     * @return
     */
    
    public String getUsername() {
    	return username;
    }
    public double getDebito() {
        return debito;
    }
    
    public void setDebito(double debt) {
    	this.debito = debt;
    }
    
    public void addDebito(double debt) {
    	this.debito += debt;
    }
    
    public void addPen() {
    	penalita++;
    }

    /**
     * @return
     */
    public int getPen() {
        return penalita;
    }
    
    public void setPen(int pen) {
    	penalita = pen;
    }
    
    public boolean disabilitato() {
    	return abbonamento.isOver() || penalita >= 3 || this.debito > 0;
    }

    /**
     * @return
     */
    public boolean isStudent() {
        return studente;
    }
    
    public CreditCard getCarta() {
    	return this.carta;
    }
    
    public void setCarta(CreditCard carta) {
    	this.carta = carta;
    }
    
    public Abbonamento getAbbonamento() {
    	return this.abbonamento;
    }
    
    public boolean abbonamentoInCorso() {
    	return abbonamento.inCorso();
    }
    
    /**
     * restituisce il tipo di abbonamento associato a @this
     * Il tipo di abbonamento viene estratto da AbbonamentoFactory e perciò viene ritornato uno dei suoi attributi statici che indicano il tipo di
     * abbonamento
     * @return String
     */
    public String getTypeAbbonamento() {
    	return AbbonamentoFactory.getType(abbonamento);
    }
    
    
    /**
     * inizia l'abbonamento associato a @this
     */
    public void startAbbonamento() {
    	abbonamento.Start();
    }
    
    

}