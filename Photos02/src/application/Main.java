package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import model.Admin;
import model.Album;
import model.User;

public class Main extends Application {
	
	public Stage mainStage;

	public static Admin driver = new Admin();
	@Override
	public void start(Stage primaryStage) {
		try {
			mainStage = primaryStage;
			//AnchorPane root = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/Login.fxml"));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			AnchorPane root = (AnchorPane) fxmlLoader.load();

			
			Scene scene = new Scene(root);
			mainStage.setResizable(false);
			mainStage.setTitle("Photo Library");
			mainStage.setScene(scene);
			mainStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
}
