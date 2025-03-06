package controllers.appuser;

import java.io.IOException;
import java.util.Set;

import ClassiPrincipali.Corsa;
import ClassiPrincipali.GestoreCorse;
import Main.MainUser;
import application.UserApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

public class SegnalaDannoController {

	@FXML
    private ComboBox<String> codicebici_field;

	@FXML
    private Label risultato_label;

	@FXML
    private TextField danno_field;
    
    private UserApp app;
    
    public void initialize() {
    	app = (UserApp) MainUser.pStage.getUserData();
    	GestoreCorse gc = GestoreCorse.getInstance();
    	Set<Corsa> corseutente = gc.getAll(app.getCurrentClient());
    	int count = 0;
    	for(Corsa c: corseutente) {
    		if(count == 3) {
    			break;
    		}
    		codicebici_field.getItems().add(c.getBike().getCodice() + " (corsa iniziata il "+ c.getStart() + " da "+ c.getRPartenza()+")");
    		count++;
    	}
    }
    
    
    @FXML
    void segnalaDanno(ActionEvent event) {
    	boolean segnalato = app.segnalaDanno(codicebici_field.getValue().split(" ")[0],danno_field.getText());
    	if(!segnalato) {
    		risultato_label.setText("Bicicletta non trovata");
    		risultato_label.setTextFill(Paint.valueOf("RED"));
    	}else {
    		risultato_label.setText("Grazie per la segnalazione");
    		risultato_label.setTextFill(Paint.valueOf("GREEN"));
    		codicebici_field.setValue("");;
    		danno_field.setText("");
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
