package controllers.totem;

import java.io.IOException;

import ClassiPrincipali.Rastrelliera;
import Main.MainTotem;
import application.TotemApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class RunOnGoingController {
	
	@FXML
    private GridPane positionsGrid;
	TotemApp app;
	private int posizioneScelta;
	private String statoMorsaScelta;
	@FXML
    private Label valutazioneDecisioneLabel;
	
	 @FXML
	 private Button agganciaBtn;
	
	public void initialize() {
		app = (TotemApp)MainTotem.pStage.getUserData();
		Rastrelliera rastrelliera = app.getRastrelliera();
		positionsGrid.getColumnConstraints().add(new ColumnConstraints(100));
		for(int i=1;i<=rastrelliera.getSize();i++) {
			Label label_posizione;
			if(rastrelliera.isFree(i)) {
				if(rastrelliera.isEletric(i)) {
					label_posizione = generaLabel("ELETTRICA",i);
				}else {
					label_posizione = generaLabel("CLASSICA",i);
				}
			}else {
				label_posizione = generaLabel("OCCUPATA",i);
			}
			label_posizione.setFont(new Font(10));
    		label_posizione.setPrefWidth(100);
    		label_posizione.setMinWidth(100);
    		label_posizione.setAlignment(Pos.CENTER);
    		label_posizione.setTextAlignment(TextAlignment.CENTER);
    		positionsGrid.add(label_posizione,(i-1)%6,(i-1)/6);
		}
		
	}
    
    public Label generaLabel(String tipo,int posizione) {
    	
    	Label label_posizione = new Label(Integer.toString(posizione)+"\n"+tipo);
    	if(tipo == "OCCUPATA") {
    		label_posizione.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY, Insets.EMPTY)));
    	}else if(tipo == "CLASSICA") {
    		label_posizione.setBackground(new Background(new BackgroundFill(Color.GREEN,CornerRadii.EMPTY, Insets.EMPTY)));
    	}else {
    		label_posizione.setBackground(new Background(new BackgroundFill(Color.YELLOW,CornerRadii.EMPTY, Insets.EMPTY)));
    	}
    	if(tipo!= "OCCUPATA") {
    		label_posizione.setOnMousePressed(new EventHandler<MouseEvent>() {
    	        @Override
    	        public void handle(MouseEvent event) {
    	        	if(posizioneScelta != 0) {
    	        		Label labelprecedente = (Label) getNodeFromGridPane((posizioneScelta-1)%6,(posizioneScelta-1)/6);
            			if(statoMorsaScelta == "CLASSICA") {
    		        		labelprecedente.setBackground(new Background(new BackgroundFill(Color.GREEN,CornerRadii.EMPTY, Insets.EMPTY)));
    		        	}else if(statoMorsaScelta == "ELETTRICA") {
    		        		labelprecedente.setBackground(new Background(new BackgroundFill(Color.YELLOW,CornerRadii.EMPTY, Insets.EMPTY)));
    		        	}
    	        	}
    	        	label_posizione.setBackground(new Background(new BackgroundFill(Color.BLUE,CornerRadii.EMPTY, Insets.EMPTY)));
    	        	posizioneScelta = posizione;
    	        	statoMorsaScelta = tipo;
    	        	if(app.valuta(posizioneScelta)) {
    	        		agganciaBtn.setDisable(false);
    	        		valutazioneDecisioneLabel.setText("");
    	        	}else{
    	        		agganciaBtn.setDisable(true);
    	        		valutazioneDecisioneLabel.setText("Morsa sbagliata per tipo di bici");
    	        	};
    	        }
    		});
    	}
		return label_posizione;
    }
    
    
    
    
    @FXML
    void agganciaBici(ActionEvent event) throws IOException {
    	app.agganciaBici(posizioneScelta);
    	changeScene(MainTotem.pathToViews+"/logintotem.fxml");
    }
    
    private Node getNodeFromGridPane(int col, int row) {
        for (Node node : positionsGrid.getChildren()) {
            if (node instanceof Label && GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
    
    @FXML
    void backToLogin(ActionEvent event) throws IOException {
    	changeScene(MainTotem.pathToViews+"/logintotem.fxml");
    }
    
    public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,400);
		MainTotem.pStage.setScene(scene);
		MainTotem.pStage.show();
    }
   

}
