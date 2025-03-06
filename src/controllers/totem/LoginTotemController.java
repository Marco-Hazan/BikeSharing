package controllers.totem;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import Eccezioni.LoginException;
import Main.MainTotem;
import application.TotemApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class LoginTotemController {
	
	@FXML
    private Pane loginPane;
	
	@FXML
    private Pane corsaconclusaPane;
	
	@FXML
    private Pane utenteDisabilitatoPane;
	
    @FXML
    private Button login_button;

    @FXML
    private TextField code_field;

    @FXML
    private PasswordField pwd_field;
    

    @FXML
    private Label error_label;
    
    @FXML
    private Label totem_title;
    
    @FXML
    private Label dettagli_corsa_field;
    
    
    private TotemApp app;
    
    public void initialize() {
    	app = (TotemApp) MainTotem.pStage.getUserData();
    	totem_title.setText("TOTEM DI "+app.getRastrelliera().getNome().toUpperCase());
    }

    @FXML
    void login(ActionEvent event) throws IOException {
    	try {
    			String stato_utente = app.autenticazione(code_field.getText(), pwd_field.getText());
    			switch(stato_utente) {
	    			case TotemApp.ABILITATO : changeScene(MainTotem.pathToViews+"/sceltabici.fxml");
	    			break;
	    			case TotemApp.DISABILITATO : showDisabilitato();
	    			break;
	    			case TotemApp.CORSAATTIVA: changeScene(MainTotem.pathToViews+"/runongoing.fxml");
	    			break;
	    			case TotemApp.CORSACONCLUSA : showConclusa();
	    			break;
    			}
    	}catch(LoginException e) {
    		error_label.setText("user e/o password errati");
    	}
    	
    	
    }
    
    public void showConclusa() {
    	dettagli_corsa_field.setText("Da "+app.getCorsaConclusa().getRPartenza()+" a "+app.getCorsaConclusa().getRArrivo()+ " - "+ app.getCorsaConclusa().getPrice()+"€");
    	loginPane.setVisible(false);
    	corsaconclusaPane.setVisible(true);
    	Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  Platform.runLater(() ->{
					    showLogin();
				  });
			  }
			}, 5000);
    }
    
    public void showDisabilitato() {
    	loginPane.setVisible(false);
    	utenteDisabilitatoPane.setVisible(true);
    	Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  Platform.runLater(() ->{
					    showLogin();
				  });
			  }
		}, 5000);
    }
    
    public void showLogin() {
    	loginPane.setVisible(true);
    	code_field.setText("");
    	pwd_field.setText("");
    	error_label.setText("");
    	corsaconclusaPane.setVisible(false);
    	utenteDisabilitatoPane.setVisible(false);
    }
    
    @FXML
    void backToChoiceTotem(ActionEvent event) throws IOException {
    	changeScene(MainTotem.pathToViews+"/maintotem.fxml");
    }
    
    
    
    public  void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,400);
		MainTotem.pStage.setScene(scene);
		MainTotem.pStage.show();
    } 

}
