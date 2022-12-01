package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import model.*;

/**
 * @author Liz Lam
 * 
 *
 */
/**
 * @author hpate
 *
 */
public class Photos extends Application {
	
	public Stage mainStage;

	public static Admin driver = new Admin();
	
	/**
	 * creates UI window
	 * @param primaryStage - the stage that contains the UI information
	 */
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
		
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent we) {
				try {
					Admin.save(driver);
				} catch (IOException exception) {
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * begins the photo program 
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		try {
			driver = Admin.load();
		}catch (IOException exception) {
			//exception.printStackTrace();
		}
		launch(args);
	}
	
	
}
