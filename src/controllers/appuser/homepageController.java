package controllers.appuser;

import java.io.IOException;
import java.util.Set;

import Main.MainUser;
import application.UserApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import principalclass.Corsa;
import principalclass.GestoreCorse;

public class homepageController {

    @FXML
    private Label username;

    @FXML
    private Label codice_utente;
    
    @FXML
    private Pane menuPane;


    @FXML
    private Pane storicoPane;

    @FXML
    private VBox lista_corse;

    @FXML
    private Label corsaattiva_label;
    
    @FXML
    private Label statoutente_label;
    
    private UserApp app;

    public void initialize() {
    	menuPane.setVisible(true);
    	storicoPane.setVisible(false);
    	app = (UserApp) MainUser.pStage.getUserData();
    	codice_utente.setText(app.getCodiceUtente());
    	String statoutente = app.getStatoUtente();
    	if(statoutente.equals(UserApp.SCADUTO)) {
    		statoutente_label.setTextFill(Paint.valueOf("RED"));
    		statoutente_label.setText("Abbonamento scaduto");
    	}else if(statoutente.equals(UserApp.ANNULLATO)) {
    		statoutente_label.setTextFill(Paint.valueOf("RED"));
    		statoutente_label.setText("Abbonamento annullato:raggiunte le 3 penalit�");
    	}else if(statoutente.equals(UserApp.SOSPESO)) {
    		statoutente_label.setTextFill(Paint.valueOf("RED"));
    		statoutente_label.setText("Pagamento non riuscito, cambia metodo di pagamento");
    	}
    }
    
    
    @FXML
    void goBackHome(ActionEvent event) {
    	menuPane.setVisible(true);
    	storicoPane.setVisible(false);
    }
    
    @FXML
    void getStorico(ActionEvent event) {
    	menuPane.setVisible(false);
    	storicoPane.setVisible(true);
    	GestoreCorse gestoreCorse = GestoreCorse.getInstance();
    	Set<Corsa>  corse = gestoreCorse.getAll(app.getCurrentClient());
    	for(Corsa c:corse) {
    		if(!c.isOver()) {
    			corsaattiva_label.setText("CORSA ATTIVA INIZIATA IL"+c.getStart()+" da "+c.getRPartenza()+ " ATTIVA DA "+ c.calcola() + " MINUTI");
    			corsaattiva_label.setTextFill(Paint.valueOf("RED"));
    		}else {
    			String string_multa = "";
    			if(c.multata()) {
    				string_multa = GestoreCorse.MULTA + "�";
    			}else {
    				string_multa = "NO";
    			}
        		lista_corse.getChildren().add(new Label("CORSA CONCLUSA DA "+c.getRPartenza() + " A "+ c.getRArrivo()));
        		lista_corse.getChildren().add(new Label(" IL "+ c.getStart() + ", DURATA: "+ c.calcola() + " MINUTI, BICICLETTA:"+c.getBike().getCodice()+", PREZZO: "+ c.getPrice() + "�"));
        		lista_corse.getChildren().add(new Label("MULTA: "+ string_multa));
    		}
    		Separator separator = new Separator();
    		separator.setOrientation(Orientation.HORIZONTAL);
    		lista_corse.getChildren().add(separator);
    	}
    }

    @FXML
    void modificaPagamento(ActionEvent event) throws IOException {
    	changeScene(MainUser.pathToViews+"/modificapagamento.fxml");
    }

    @FXML
    void modificaPwd(ActionEvent event) throws IOException {
    	changeScene(MainUser.pathToViews+"/modificapwd.fxml");
    	
    }

    @FXML
    void segnalaDanno(ActionEvent event) throws IOException {
    	changeScene(MainUser.pathToViews+"/segnaladanno.fxml");
    }
    
    @FXML
    void logout(ActionEvent event) throws IOException {
    	app.logout();
    	changeScene(MainUser.pathToViews+"/login.fxml");
    }
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,449);
		MainUser.pStage.setScene(scene);
		MainUser.pStage.show();
    }

}
