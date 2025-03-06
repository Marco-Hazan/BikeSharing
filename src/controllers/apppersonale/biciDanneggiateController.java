package controllers.apppersonale;

import java.io.IOException;
import java.util.Set;

import ClassiPrincipali.Bicicletta;
import ClassiPrincipali.BiciclettaDao;
import ClassiPrincipali.BiciclettaDaoImpl;
import Main.MainPersonale;
import application.PersonaleApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class biciDanneggiateController {

    @FXML
    private VBox lista_bici;

    @FXML
    private TextField codice_field;
    
    @FXML
    private Label result_label;
    
    private PersonaleApp app;

    public void initialize() {
    	app = (PersonaleApp) MainPersonale.pStage.getUserData();
    	updateContainer();
    }
    
    public void updateContainer() {
    	BiciclettaDao bikes = new BiciclettaDaoImpl();
    	Set<Bicicletta> damaged = bikes.getBiciDanneggiate();
    	lista_bici.getChildren().clear();
    	if(damaged.size() == 0) {
    		lista_bici.getChildren().add(new Label("Non c'è nessuna segnalazione di bici danneggiate"));
    	}
    	for(Bicicletta b:damaged) {
    		lista_bici.getChildren().add(new Label("Bici: " + b.getCodice() + ", Rastrelliera: "+bikes.getRastrelliera(b)));
    		lista_bici.getChildren().add(new Label("Stato: " + b.getStato()));
    		Separator separator = new Separator();
    		separator.setOrientation(Orientation.HORIZONTAL);
    		lista_bici.getChildren().add(separator);
    	}
    }
    
    
    @FXML
    void goBackHome(ActionEvent event) throws IOException {
    	changeScene(MainPersonale.pathToViews+"/homepersonale.fxml");
    }

    @FXML
    void rimuoviBici(ActionEvent event) {
    	boolean bicirimossa = app.rimuoviBici(codice_field.getText());
    	if(bicirimossa) {
    		result_label.setTextFill(Paint.valueOf("GREEN"));
    		result_label.setText("Bici rimossa correttamente");
    		updateContainer();
    	}else {
    		result_label.setTextFill(Paint.valueOf("RED"));
    		result_label.setText("Errore nella rimozione della bici");
    	}
    }
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,400);
		MainPersonale.pStage.setScene(scene);
		MainPersonale.pStage.show();
    }

}
