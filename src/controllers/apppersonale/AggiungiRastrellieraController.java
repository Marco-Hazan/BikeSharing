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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import principalclass.Rastrelliera;
import principalclass.RastrellieraDao;
import principalclass.RastrellieraDaoImpl;

public class AggiungiRastrellieraController {
	PersonaleApp app;
	
	public void initialize() {
		app = (PersonaleApp)MainPersonale.pStage.getUserData();
		for(int i=15;i<=30;i++) {
			size_field.getItems().add(i);
		}
		RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		Set<Rastrelliera> list_rastrelliere = rastrelliere.getAllRastrelliere();
		for(Rastrelliera r: list_rastrelliere) {
			rastrellierarmv_field.getItems().add(r.getNome());
		}
	}

    @FXML
    private TextField nome_field;

    @FXML
    private ComboBox<Integer> size_field;

    @FXML
    private ComboBox<String> rastrellierarmv_field;

    @FXML
    private Label resultadd_label;

    @FXML
    private Label resultdelete_label;
    
    @FXML
    private ChoiceBox<Integer> tot_morse_elettriche_field;

    @FXML
    void aggiungirastrelliera(ActionEvent event) {
    	if(nome_field.getText() == null || size_field.getValue() == null || tot_morse_elettriche_field.getValue() == null) {
    		resultadd_label.setText("dati incorretti");
    		return;
    	}
    	if(nome_field.getText().length() >=5 && size_field.getValue() != null) {
        	app.aggiungiRastrelliera(nome_field.getText(),size_field.getValue(),tot_morse_elettriche_field.getValue());
        	resultadd_label.setTextFill(Paint.valueOf("GREEN"));
    		resultadd_label.setText("rastrelliera "+ nome_field.getText()+ " aggiunta con successo");
    		updateCombo();
    		nome_field.setText("");
    	}else {
    		resultadd_label.setText("dati incorretti");
    	}
    }

    @FXML
    void eliminarastrelliera(ActionEvent event) {
    	if(rastrellierarmv_field.getValue() != null) {
        	boolean buonfine = app.rimuoviRastrelliera(rastrellierarmv_field.getValue());
        	if(buonfine) {
        		resultdelete_label.setTextFill(Paint.valueOf("GREEN"));
        		resultdelete_label.setText("eliminazione rastrelliera "+ rastrellierarmv_field.getValue()+ " avvenuta con successo");
        		updateCombo();
        	}
    	}else {
    		resultdelete_label.setText("dati incorretti");
    	}
    }
    
    @FXML
    void goBackHome(ActionEvent event) throws IOException {
    	changeScene(MainPersonale.pathToViews+"/homepersonale.fxml");
    }
    
    void updateCombo() {
    	rastrellierarmv_field.getItems().clear();
    	RastrellieraDao rastrelliere = new RastrellieraDaoImpl();
		Set<Rastrelliera> list_rastrelliere = rastrelliere.getAllRastrelliere();
		for(Rastrelliera r: list_rastrelliere) {
			rastrellierarmv_field.getItems().add(r.getNome());
		}
    }
    
    @FXML
    void aggiorna_elettriche_field(ActionEvent event) {
		tot_morse_elettriche_field.getItems().clear();
    	for(int i=0;i<=size_field.getValue();i++) {
    		tot_morse_elettriche_field.getItems().add(i);
    	}
    }
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,400);
		MainPersonale.pStage.setScene(scene);
		MainPersonale.pStage.show();
    }

}
