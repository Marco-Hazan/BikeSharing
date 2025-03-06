package controllers.appuser;

import java.io.IOException;

import Main.MainUser;
import application.UserApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Paint;

public class ModificaPwdController {

    @FXML
    private PasswordField vecchiapwd_field;

    @FXML
    private PasswordField nuovapwd_field;

    @FXML
    private PasswordField confermapwd_field;

    @FXML
    private Label risultatomodifica_label;
    
    private UserApp app;
    
    public void initialize() {
    	app = (UserApp) MainUser.pStage.getUserData();
    }

    @FXML
    void modificaPwd(ActionEvent event) {
    	if(nuovapwd_field.getText().length() < 6) {
    		risultatomodifica_label.setTextFill(Paint.valueOf("RED"));
    		risultatomodifica_label.setText("La nuova password deve avere almeno 6 caratteri");
    	}else if(!nuovapwd_field.getText().equals(confermapwd_field.getText())) {
    		risultatomodifica_label.setTextFill(Paint.valueOf("RED"));
    		risultatomodifica_label.setText("Le due password non corrispondono");
    	}else {
    		boolean modificata = app.modificaPwd(vecchiapwd_field.getText(),nuovapwd_field.getText());
    		if(modificata) {
    			risultatomodifica_label.setTextFill(Paint.valueOf("GREEN"));
        		risultatomodifica_label.setText("Password modificata correttamente");
    		}else {
    			risultatomodifica_label.setTextFill(Paint.valueOf("GREEN"));
        		risultatomodifica_label.setText("La vecchia password inserita non corrisponde con la tua password");
    		}
    	}

    }
    
    @FXML
    void goBackHome(ActionEvent event) throws IOException {
    	changeScene(MainUser.pathToViews+"/mainpage_abbonati.fxml");
    }
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,449);
		MainUser.pStage.setScene(scene);
		MainUser.pStage.show();
    }

}
