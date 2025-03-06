	package controllers.totem;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import Eccezioni.BikeNotFoundException;
import Main.MainTotem;
import application.TotemApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import principalclass.BiciclettaFactory;


public class BikeChoiceController {
	
	@FXML
    private Pane mostraposizionePane;

    @FXML
    private Pane sceltabiciPane; 
    
    
	@FXML
    private Label nobikes_label;
	
	@FXML
    private Label penalita_label;
    

    @FXML
    private Label posizione_label;
    
    TotemApp app;
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,400);
		MainTotem.pStage.setScene(scene);
		MainTotem.pStage.show();
    } 
    
    
    public void initialize() {
    	mostraposizionePane.setVisible(false);
    	sceltabiciPane.setVisible(true);
    	app = (TotemApp) MainTotem.pStage.getUserData();
    }
    
    @FXML
    void getClassic(ActionEvent event) throws IOException {
    	try{
    		int posizione = app.askBike(BiciclettaFactory.CLASSICA);
    		mostraPosizione(posizione);
    	}catch(BikeNotFoundException e) {
    		nobikes_label.setText("Nessuna bicicletta di questo tipo disponibile");
    	}
    }

    @FXML
    void getElettrica(ActionEvent event) throws IOException {
    	try{
    		int posizione = app.askBike(BiciclettaFactory.ELETTRICA);
    		mostraPosizione(posizione);
    	}catch(BikeNotFoundException e) {
    		nobikes_label.setText("Nessuna bicicletta di questo tipo disponibile");
    	}
    }

    @FXML
    void getSeggiolino(ActionEvent event) throws IOException {
    	try{
    		int posizione = app.askBike(BiciclettaFactory.SEGGIOLINO);
    		mostraPosizione(posizione);
    	}catch(BikeNotFoundException e) {
    		nobikes_label.setText("Nessuna bicicletta di questo tipo disponibile");
    	}
    }
    
    void mostraPosizione(int posizione) {
    	sceltabiciPane.setVisible(false);
    	mostraposizionePane.setVisible(true);
		posizione_label.setText(Integer.toString(posizione));
		setTimerLogin();
    }
    
    void setTimerLogin() {
    	Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  Platform.runLater(() ->{
					    tornalogin();
				  });
			  }
			}, 5000);
    }
    
    @FXML
    void backToLogin(ActionEvent event) {
    	tornalogin();
    }
    
    public void tornalogin() {
    	try {
			changeScene(MainTotem.pathToViews+"/logintotem.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
