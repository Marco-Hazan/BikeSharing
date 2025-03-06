package controllers.appuser;

import java.io.IOException;

import Eccezioni.ExpiredCardException;
import Eccezioni.IllegalCardException;
import Eccezioni.RoofReachedException;
import Main.MainUser;
import application.UserApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import principalclass.AbbonamentoFactory;
import principalclass.CreditCard;
import principalclass.ValidazioneStudenti;

public class RegistrazioneController {

    @FXML
    private Pane recPane;

    @FXML
    private CheckBox giornaliero_check;

    @FXML
    private CheckBox settimanale_check;

    @FXML
    private CheckBox annuale_check;

    @FXML
    private CheckBox studente_check;

    @FXML
    private CheckBox nostudente_check;

    @FXML
    private TextField matricola_field;

    @FXML
    private ChoiceBox<String> universita_choicebox;

    @FXML
    private PasswordField pwd_field;

    @FXML
    private PasswordField confermapwd_field;

    @FXML
    private Label error_label;

    @FXML
    private TextField usernamerec_field;

    @FXML
    private Pane successorec_pane;

    @FXML
    private Label codice_label;

    @FXML
    private Pane pagamentoPane;

    @FXML
    private TextField numerocarta_field;

    @FXML
    private TextField cvv_field;

    @FXML
    private TextField month_field;

    @FXML
    private TextField year_field;
    
    @FXML
    private Label pagamentoerror_label;
    
    private RecData recdata;
    
    private UserApp app;
    
    public void initialize() {
    	annuale_check.setSelected(true);
    	nostudente_check.setSelected(true);
    	pagamentoPane.setVisible(false);
    	recPane.setVisible(true);
    	successorec_pane.setVisible(false);
    	String list_uni[] = ValidazioneStudenti.getUniversita();
    	for(String u:list_uni) {
    		universita_choicebox.getItems().add(u);
    	}
    	app = (UserApp) MainUser.pStage.getUserData();
    }
    
    @FXML
    void annuale_checked(ActionEvent event) {
    	annuale_check.setSelected(true);
    	settimanale_check.setSelected(false);
    	giornaliero_check.setSelected(false);
    }
    
    @FXML
    void giornaliero_checked(ActionEvent event) {
    	giornaliero_check.setSelected(true);
    	annuale_check.setSelected(false);
    	settimanale_check.setSelected(false);
    }
    
    @FXML
    void settimanale_checked(ActionEvent event) {
    	settimanale_check.setSelected(true);
    	annuale_check.setSelected(false);
    	giornaliero_check.setSelected(false);
    }
    
    @FXML
    void studente_checked(ActionEvent event) {
    	studente_check.setSelected(true);
    	nostudente_check.setSelected(false);
    	universita_choicebox.setDisable(false);
    	matricola_field.setDisable(false);
    }
    @FXML
    void nostudente_checked(ActionEvent event) {
    	nostudente_check.setSelected(true);
    	studente_check.setSelected(false);
    	universita_choicebox.setDisable(true);
    	matricola_field.setDisable(true);
    }
    
    @FXML
    void goToPagamento(ActionEvent event) {
    	if(usernamerec_field.getText().equals("")) {
    		error_label.setText("Il campo username non pu� essere vuoto");
    		return;
    	}
    	if(!pwd_field.getText().equals(confermapwd_field.getText())) {
    		error_label.setText("Le due password non corrispondono");
    		return;
    	}
    	if(pwd_field.getText().length() < 6) {
    		error_label.setText("La password deve essere lunga almeno 6 caratteri");
    		return;
    	}
    	if(studente_check.isSelected() && ! app.validaStudente(universita_choicebox.getValue(),matricola_field.getText())) {
    		error_label.setText("Errore nella validazione dello studente");
    		return;
    	}
    	
    	if(!app.usernameUnivoco(usernamerec_field.getText())) {
    		error_label.setText("Username gi� utilizzato");
    		return;
    	}
    	
    	
    	if(giornaliero_check.isSelected()) {
        	recdata = new RecData(usernamerec_field.getText(),AbbonamentoFactory.GIORNALIERO,pwd_field.getText(),studente_check.isSelected());
    	}else if(settimanale_check.isSelected()) {
        	recdata = new RecData(usernamerec_field.getText(),AbbonamentoFactory.SETTIMANALE,pwd_field.getText(),studente_check.isSelected());
    	}else {
    		recdata = new RecData(usernamerec_field.getText(),AbbonamentoFactory.ANNUALE,pwd_field.getText(),studente_check.isSelected());
    	}
    	recPane.setVisible(false);
    	pagamentoPane.setVisible(true);
    }
    
    @FXML
    void generaNumeroCarta(ActionEvent event) {
    	String numero = CreditCard.generaNumero();
    	numerocarta_field.setText(numero);
    }
    
    @FXML
    void attivaAbbonamento(ActionEvent event) {
    	try{
    		CreditCard carta = new CreditCard(numerocarta_field.getText(),cvv_field.getText(),Integer.parseInt( month_field.getText()),2000 + Integer.parseInt(year_field.getText()));
    		recdata.setCarta(carta);
    		String codice = app.attivaAbbonamento(recdata);
    		codice_label.setText(codice);
    		pagamentoPane.setVisible(false);
    		successorec_pane.setVisible(true);
    	}catch(IllegalCardException | ExpiredCardException e) {
    		pagamentoerror_label.setText("Errore nei dati della carta");
    	}catch(RoofReachedException e) {
    		pagamentoerror_label.setText("Transazione negata");
    	}
    }
    
    @FXML
    void backToLogin(ActionEvent event) throws IOException {
    	changeScene(MainUser.pathToViews+"/login.fxml");
    }
    @FXML
    void goToRec(ActionEvent event) {
    	pagamentoPane.setVisible(false);
    	recPane.setVisible(true);
    }
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,449);
		MainUser.pStage.setScene(scene);
		MainUser.pStage.show();
    } 
    
}