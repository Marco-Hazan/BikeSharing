package controllers.totem;

import java.io.IOException;
import java.util.Set;

import ClassiPrincipali.Rastrelliera;
import ClassiPrincipali.RastrellieraDao;
import ClassiPrincipali.RastrellieraDaoImpl;
import Main.MainTotem;
import application.TotemApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MainTotemController {

    @FXML
    private Pane mainPane;

    @FXML
    private VBox listarastrellierePane;
    
    
    private RastrellieraDao rastrelliere;
    
    @FXML
    private Button scegliRastrellieraBtn;
    
    private Rastrelliera rastrellieraSelezionata;
    
    
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,400);
		MainTotem.pStage.setScene(scene);
		MainTotem.pStage.show();
    }
   
    
    public void initialize() {
    	rastrelliere = new RastrellieraDaoImpl();
    	Set<Rastrelliera> all_rastrelliere = rastrelliere.getAllRastrelliere();
    	for(Rastrelliera r:all_rastrelliere) {
    		Label label_r = new Label(r.toString());
    		label_r.setFont(new Font(20));
    		label_r.setPrefWidth(517);
    		label_r.setTextAlignment(TextAlignment.CENTER);
    		label_r.setOnMousePressed(new EventHandler<MouseEvent>() {
    	        @Override
    	        public void handle(MouseEvent event) {
    	        	for(Node node:listarastrellierePane.getChildren()) {
    	        		if(node instanceof Label) {
        	    			Label l = (Label) node;
        	    			l.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
        	    		}
        	    		label_r.setBackground(new Background(new BackgroundFill(Color.BLUE,CornerRadii.EMPTY, Insets.EMPTY)));
        	    		rastrellieraSelezionata = r;
        	    		scegliRastrellieraBtn.setDisable(false);
    	        		}
    	        }
    	    });
    		listarastrellierePane.getChildren().add(label_r);
    		Separator separator = new Separator();
    		separator.setOrientation(Orientation.HORIZONTAL);
    		listarastrellierePane.getChildren().add(separator);
    	}
    }
    
    @FXML
    void goToLoginTotem(ActionEvent event) throws IOException {
    	TotemApp app = new TotemApp(rastrellieraSelezionata);
		MainTotem.pStage.setUserData(app);
    	changeScene(MainTotem.pathToViews+"/logintotem.fxml");
    }
}

