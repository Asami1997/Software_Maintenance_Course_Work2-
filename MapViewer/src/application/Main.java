package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	public static Scene scene;

	@Override
	public void start(Stage primaryStage) {
		try {

			Pane root = FXMLLoader.load(getClass().getResource("views/Main.fxml"));

			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/styles/Main.css").toExternalForm());

			primaryStage.setTitle("Viola Map Editor");
			primaryStage.setScene(scene);
			primaryStage.show();

			// changing cursor image
			scene.setCursor(new ImageCursor(new Image("images/2dCusor.png")));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
