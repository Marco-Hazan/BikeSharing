package controllers.apppersonale;

import java.io.IOException;
import java.util.Set;

import Eccezioni.BikeNotFoundException;
import Eccezioni.NoFreeSpotException;
import Main.MainPersonale;
import application.PersonaleApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import principalclass.Rastrelliera;
import principalclass.RastrellieraDaoImpl;

public class RilocazioneController {
	

    @FXML
    private ComboBox<String> rastrellierasrg_field;

    @FXML
    private ComboBox<String> rastrellieradst_field;

    @FXML
    private TextField totc_field;

    @FXML
    private TextField tote_field;

    @FXML
    private TextField tots_field;
    
    @FXML
    private Label result_label;
    
    PersonaleApp app;

    
    public void initialize() {
    	Set<Rastrelliera> rastrelliere = new RastrellieraDaoImpl().getAllRastrelliere();
    	for(Rastrelliera r:rastrelliere) {
    		rastrellierasrg_field.getItems().add(r.toString());
    		rastrellieradst_field.getItems().add(r.toString());
    	}
    	app = (PersonaleApp) MainPersonale.pStage.getUserData();
    }
    
    public void updatechoicebox() {
    	Set<Rastrelliera> rastrelliere = new RastrellieraDaoImpl().getAllRastrelliere();
    	rastrellierasrg_field.getItems().clear();
    	rastrellieradst_field.getItems().clear();
    	for(Rastrelliera r:rastrelliere) {
    		rastrellierasrg_field.getItems().add(r.toString());
    		rastrellieradst_field.getItems().add(r.toString());
    	}
    }
    
    @FXML
    void goBackHome(ActionEvent event) throws IOException {
    	changeScene(MainPersonale.pathToViews+"/homepersonale.fxml");
    }

    @FXML
    void riloca(ActionEvent event) {
    	try {
    		int totc = Integer.parseInt(totc_field.getText());
    		int tote = Integer.parseInt(tote_field.getText());
    		int tots = Integer.parseInt(tots_field.getText());
    		if(rastrellierasrg_field.getValue() == null || rastrellieradst_field.getValue() == null) {
    			result_label.setText("Errore nell'inserimento dati");
    			return;
    		}
        	boolean rilocata = app.riloca(rastrellierasrg_field.getValue().split(",")[0],rastrellieradst_field.getValue().split(",")[0], totc,tote,tots);
        	if(rilocata) {
        		result_label.setText("Rilocazione andata a buon fine");
            	updatechoicebox();
        	}else {
        		result_label.setText("Errore nell'inserimento dati");
        	}
    	}catch(NumberFormatException e) {
    		result_label.setText("Errore nell'inserimento dati");
    	}catch(BikeNotFoundException e) {
    		result_label.setText("La rastrelliera "+ rastrellierasrg_field.getValue()+" non ha abbastanza biciclette");
    	}catch(NoFreeSpotException e) {
    		result_label.setText("La rastrelliera "+ rastrellieradst_field.getValue()+" non ha abbastanza posti liberi");
    	}	
    }
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,400);
		MainPersonale.pStage.setScene(scene);
		MainPersonale.pStage.show();
    }

}
