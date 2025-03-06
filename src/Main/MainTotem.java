package Main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainTotem extends Application {
	public static Stage pStage;
	public static String pathToViews = "/FXML/apptotem";
	@Override
	public void start(Stage primaryStage) {
		pStage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/apptotem/maintotem.fxml"));
			Scene scene = new Scene(root,600,400);
			primaryStage.setScene(scene);
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