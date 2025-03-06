package controllers.apppersonale;

import java.io.IOException;

import Main.MainPersonale;
import application.PersonaleApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPersonaleController {
	
	PersonaleApp app;
	
	public void initialize() {
		app = (PersonaleApp) MainPersonale.pStage.getUserData();
	}

    @FXML
    private TextField codice_field;

    @FXML
    private PasswordField pwd_field;

    @FXML
    private Label error_label;

    @FXML
    void login(ActionEvent event) throws IOException {
    	boolean autenticato = app.autenticazione(codice_field.getText(),pwd_field.getText());
    	if(autenticato) {
    		changeScene(MainPersonale.pathToViews+"/homepersonale.fxml");
    	}else {
    		error_label.setText("Codice utente e/o password errati");
    	}
    }
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,400);
		MainPersonale.pStage.setScene(scene);
		MainPersonale.pStage.show();
    }

}
