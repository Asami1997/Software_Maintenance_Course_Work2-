package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			
			Pane root = FXMLLoader.load(getClass().getResource("views/Main.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/styles/Main.css").toExternalForm());
			
			/*final Delta dragDelta = new Delta();
			scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
			    // record a delta distance for the drag and drop operation.
			    dragDelta.x = primaryStage.getX() - mouseEvent.getScreenX();
			    dragDelta.y = primaryStage.getY() - mouseEvent.getScreenY();
			  }
			});
			scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
			    primaryStage.setX(mouseEvent.getScreenX() + dragDelta.x);
			    primaryStage.setY(mouseEvent.getScreenY() + dragDelta.y);
			  }
			});*/
			
			primaryStage.setTitle("Viola Map Editor");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	class Delta { double x, y; } 
	
	public static void main(String[] args) {
		launch(args);
	}
}
