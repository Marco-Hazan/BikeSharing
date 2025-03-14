package Main;

import java.io.IOException;

import application.PersonaleApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPersonale extends Application {
	public static Stage pStage;
	public static String pathToViews = "/FXML/apppersonale";
	
	@Override
	public void start(Stage primaryStage) {
		pStage = primaryStage;
		try {
			primaryStage.setUserData(new PersonaleApp());
			Parent root = FXMLLoader.load(getClass().getResource(pathToViews+"/loginpersonale.fxml"));
			Scene scene = new Scene(root,600,400);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}
	
	public void changeScene(String url) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(url));
		Scene scene = new Scene(root,600,400);
		MainTotem.pStage.setScene(scene);
		MainTotem.pStage.show();
    }
}