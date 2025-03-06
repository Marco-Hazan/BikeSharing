package controllers.appuser;

import principalclass.CreditCard;

public class RecData {
	
	private String username;
	private String tipo_abbonamento;
	private String password;
	private boolean studente;
	private CreditCard carta;
	
	
	public RecData(String username,String tipo,String pwd,boolean studente) {
		this.username = username;
		this.tipo_abbonamento = tipo;
		this.password = pwd;
		this.studente = studente;
	}
	
	public void setCarta(CreditCard carta) {
		this.carta = carta;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getTipo() {
		return tipo_abbonamento;
	}
	
	public boolean studente() {
		return studente;
	}
	
	public CreditCard getCarta() {
		return carta;
	}
	
	public String getPwd() {
		return password;
	}
}
