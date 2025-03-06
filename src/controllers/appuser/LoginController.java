package controllers.appuser;

import java.io.IOException;
import Main.MainUser;
import application.UserApp;
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

public class LoginController {

	@FXML
    private TextField codicelogin_field;

    @FXML
    private PasswordField pwdlogin_field;
    
    @FXML
    private Label errorlogin_label;

    @FXML
    private Button login_button;

    @FXML
    private Pane loginPane;
    
    @FXML
    private Pane infobikesharing_pane;
    
    @FXML
    private TextField usernamelog_field;
    
    
    private UserApp app;
    
    public void initialize() {
    	loginPane.setVisible(true);
    	infobikesharing_pane.setVisible(false);
    	app = (UserApp) MainUser.pStage.getUserData();
    }

    @FXML
    void backToLogin(ActionEvent event) {
    	infobikesharing_pane.setVisible(false);
    	loginPane.setVisible(true);
    }


    @FXML
    void goToRec(ActionEvent event) throws IOException {
    	try{
    		changeScene(MainUser.pathToViews+"/registrazione.fxml");
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    
    
    @FXML
    void login(ActionEvent event) throws IOException {
    	errorlogin_label.setText("User e/o password errati");
		boolean autenticato = app.autenticazione(usernamelog_field.getText(),pwdlogin_field.getText());
		if(autenticato) {
			changeScene(MainUser.pathToViews+"/mainpage_abbonati.fxml");
		}else {
	    	errorlogin_label.setText("User e/o password errati");
		}
    }
    
    @FXML
    void showInfo(ActionEvent event) {
    	loginPane.setVisible(false);
    	infobikesharing_pane.setVisible(true);
    }
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,449);
		MainUser.pStage.setScene(scene);
		MainUser.pStage.show();
    } 

}
