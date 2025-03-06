package controllers.apppersonale;

import java.io.IOException;

import ClassiPrincipali.Statistiche;
import Main.MainPersonale;
import application.PersonaleApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HomePersonaleController {
	
	@FXML
    private Pane mainPane;

    @FXML
    private Pane statistichePane;

    @FXML
    private VBox statistiche_container;
    
    private PersonaleApp app;
	
	public void initialize() {
		app = (PersonaleApp) MainPersonale.pStage.getUserData();
		mainPane.setVisible(true);
		statistichePane.setVisible(false);
	}
	
	

    @FXML
    void aggiungiBici(ActionEvent event) throws IOException {
    	changeScene(MainPersonale.pathToViews+"/aggiungibici.fxml");
    }

    @FXML
    void aggiungiRastrelliere(ActionEvent event) throws IOException {
    	changeScene(MainPersonale.pathToViews+"/aggiungirastrelliera.fxml");
    }

    @FXML
    void guardaStatistiche(ActionEvent event) {
    	mainPane.setVisible(false);
		statistichePane.setVisible(true);
		statistiche_container.getChildren().add(new Label("Numero utenti abbonati: "+Integer.toString(Statistiche.abbonati())));
		statistiche_container.getChildren().add(new Label("Percentuale studenti: "+Statistiche.percentualeStudenti()));
		statistiche_container.getChildren().add(new Label("Rastrelliera più usata in partenza: "+Statistiche.getRPartenzaPiuUsata()));
		statistiche_container.getChildren().add(new Label("Rastrelliera più usata in arrivo: "+Statistiche.getRPartenzaPiuUsata()));
		statistiche_container.getChildren().add(new Label("Media biciclette usate in un giorno: "+Double.toString(Statistiche.mediaCorse())));
		statistiche_container.getChildren().add(new Label("Tipo di bicicletta più usata: "+Statistiche.getTipoPiuUsato()));
    }
    
    @FXML
    void goBackHome(ActionEvent event) {
    	mainPane.setVisible(true);
		statistichePane.setVisible(false);
    }

    @FXML
    void rilocaBiciclette(ActionEvent event) throws IOException {
    	changeScene(MainPersonale.pathToViews+"/rilocazione.fxml");
    }
    
    @FXML
    void biciDanneggiate(ActionEvent event) throws IOException {
    	changeScene(MainPersonale.pathToViews+"/bicidanneggiate.fxml");
    }
    
    @FXML
    void logout(ActionEvent event) throws IOException {
    	app.logout();
    	changeScene(MainPersonale.pathToViews+"/loginpersonale.fxml");
    }

    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,400);
		MainPersonale.pStage.setScene(scene);
		MainPersonale.pStage.show();
    }

}
