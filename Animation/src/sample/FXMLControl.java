package sample;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javax.swing.text.html.ImageView;
import javafx.scene.text.Text;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class FXMLControl implements Initializable {



    @FXML
    private Text status;

    @FXML
    private Button button;

    @FXML
    private ImageView imageView;


    @Override

    public void initialize(URL url, ResourceBundle rc) {
        Circle circle = new Circle(100);
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(10));
        transition.setNode(button);




        ScaleTransition transition1 = new ScaleTransition(Duration.seconds(10), button);
        transition1.setToX(1);
        transition1.setToY(1);



        RotateTransition transition2 = new RotateTransition(Duration.seconds(10),button);
        transition2.setFromAngle(45);
        transition2.setToAngle(360);



        ParallelTransition parallelTransition = new ParallelTransition(transition,transition1,transition2);
        parallelTransition.getChildren().addAll();
        parallelTransition.setAutoReverse(true);
        parallelTransition.setCycleCount(8);





        parallelTransition.play();


    }


    @FXML

    private void displayPosition (MouseEvent event){
        status.setText("X = " + event.getX() + " Y = " + event.getY());
    }

}


 /*  TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(10));
        transition.setNode(button);




        ScaleTransition transition1 = new ScaleTransition(Duration.seconds(10), button);
        transition1.setToX(0.5);
        transition1.setToY(0.5);



        RotateTransition transition2 = new RotateTransition(Duration.seconds(10),button);
        transition2.setFromAngle(45);
        transition2.setToAngle(360);



        ParallelTransition parallelTransition = new ParallelTransition(transition,transition1,transition2);
        parallelTransition.getChildren().addAll();
        parallelTransition.setAutoReverse(true);
        parallelTransition.setCycleCount(8);





        parallelTransition.play();

*/