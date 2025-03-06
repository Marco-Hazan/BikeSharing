package controllers.appuser;

import java.io.IOException;

import Eccezioni.ExpiredCardException;
import Eccezioni.IllegalCardException;
import Main.MainUser;
import application.UserApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import principalclass.CreditCard;

public class ModificaPagamentoController {

    @FXML
    private TextField numero_field;

    @FXML
    private TextField cvv_field;

    @FXML
    private TextField month_field;

    @FXML
    private TextField year_field;
    
    @FXML
    private Label risultatomodifica_label;
    
    private UserApp app;
    
    public void initialize() {
    	app = (UserApp) MainUser.pStage.getUserData();
    }


    @FXML
    void modificaPagamento(ActionEvent event) {
    	try{
    		CreditCard carta = new CreditCard(numero_field.getText(),cvv_field.getText(),Integer.parseInt(month_field.getText()),Integer.parseInt(year_field.getText()));
    		app.modificaPagamento(carta);
    		risultatomodifica_label.setTextFill(Paint.valueOf("GREEN"));
    		risultatomodifica_label.setText("Dati pagamento modificati con successo!");
    	}catch(IllegalCardException | ExpiredCardException e) {
    		risultatomodifica_label.setTextFill(Paint.valueOf("RED"));
    		risultatomodifica_label.setText("Dati carta non validi");
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
