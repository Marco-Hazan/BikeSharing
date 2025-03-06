package controllers.apppersonale;

import java.io.IOException;
import java.util.Set;

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
import javafx.scene.paint.Paint;
import principalclass.Rastrelliera;
import principalclass.RastrellieraDao;
import principalclass.RastrellieraDaoImpl;

public class AggiungiBiciController {

    @FXML
    private ComboBox<String> rastrelliera_add;

    @FXML
    private TextField classiche_add;

    @FXML
    private TextField elettriche_add;

    @FXML
    private TextField segg_add;

    @FXML
    private ComboBox<String> rastrelliera_rmv;

    @FXML
    private TextField classiche_rmv;

    @FXML
    private TextField elettriche_rmv;

    @FXML
    private TextField segg_rmv;
    
    @FXML
    private Label erroraggiungi_label;

    @FXML
    private Label removeerror_label;

    private PersonaleApp app;
    
    public void initialize() {
    	app = (PersonaleApp)MainPersonale.pStage.getUserData();
    	RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
    	Set<Rastrelliera> listar = rastrelliere.getAllRastrelliere();
    	for(Rastrelliera r:listar) {
    		rastrelliera_rmv.getItems().add(r.toString());
    		rastrelliera_add.getItems().add(r.toString());
    	}
    }
    
    public void updatechoicebox() {
    	RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
    	Set<Rastrelliera> listar = rastrelliere.getAllRastrelliere();
    	rastrelliera_rmv.getItems().clear();
		rastrelliera_add.getItems().clear();
    	for(Rastrelliera r:listar) {
    		rastrelliera_rmv.getItems().add(r.toString());
    		rastrelliera_add.getItems().add(r.toString());
    	}
    }
    
    
    @FXML
    void aggiungi(ActionEvent event) {
    	try {
    		int totc = Integer.parseInt(classiche_add.getText());
    		int tote = Integer.parseInt(elettriche_add.getText());
    		int tots = Integer.parseInt(segg_add.getText());
    		if(rastrelliera_add.getValue() == null) {
    			erroraggiungi_label.setText("Dati inseriti non corretti");
    			return;
    		}
        	boolean aggiunta = app.aggiungiBici(rastrelliera_add.getValue().split(",")[0],totc,tote,tots);
        	if(aggiunta) {
        		updatechoicebox();
            	erroraggiungi_label.setTextFill(Paint.valueOf("GREEN"));
            	erroraggiungi_label.setText("Biciclette aggiunte correttamente");
        	}else {
        		erroraggiungi_label.setText("Rastrelliera non ha abbastanza posti disponibili");
        	}
    	}catch(NumberFormatException e) {
    		erroraggiungi_label.setText("Dati inseriti non corretti");
    	}
    	
    }

    @FXML
    void rimuovi(ActionEvent event) {
		int totc = Integer.parseInt(classiche_rmv.getText());
		int tote = Integer.parseInt(elettriche_rmv.getText());
		int tots = Integer.parseInt(segg_rmv.getText());
		if(rastrelliera_rmv.getValue() == null) {
			removeerror_label.setText("Dati inseriti non corretti");
			return;
		}
		if(app.rimuoviBici(rastrelliera_rmv.getValue().split(",")[0],totc,tote,tots)) {
			updatechoicebox();
			removeerror_label.setTextFill(Paint.valueOf("GREEN"));
			removeerror_label.setText("Biciclette rimosse correttamente");
		}else {
			removeerror_label.setText("Non ci sono abbastanza biciclette");
		}
    }
    
    @FXML
    void selezionorastrelliera(ActionEvent event) {
    	erroraggiungi_label.setText("");
    }
    
    @FXML
    void goBackHome(ActionEvent event) throws IOException {
    	changeScene(MainPersonale.pathToViews+"/homepersonale.fxml");
    }
    
    
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,400);
		MainPersonale.pStage.setScene(scene);
		MainPersonale.pStage.show();
    }

}
