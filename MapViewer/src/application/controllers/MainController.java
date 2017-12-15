package application.controllers;

import com.sun.javafx.event.EventHandlerManager;

import application.models.TileMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class MainController {
    
    @FXML
    private Button loadBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button enlargeBtn;

    @FXML
    private Button shrinkBtn;

    @FXML
    private Button setBoatBtn;

    @FXML
    private Button setAxeBtn;

    @FXML
    private Button clearPosBtn;

    @FXML
    private ScrollPane mapscroll;
    
    @FXML StackPane stackPane;
    
    @FXML
    private Label viewLbl;
    
    @FXML
    private TilePane mapviewer;
    
    @FXML
    private ImageView axe;
    
    private Image axeImage;
    
    private double axeXCoordinate;
    
    private double axeYCoordinate; 
    
    private TileMap tilemap;
    
    private double scale = 1;
    
    	public void initialize() {
    		
    		//getting the coordinates of the axe
    		
    		axeImage = new Image("/images/axe.gif");
    		tilemap = new TileMap();
    		tilemap.loadMap("/map.map");
    		tilemap.loadTileSet("/images/tileset.gif");
    		
    		tilemap.render(mapviewer, scale);
    	
    		
    		mapscroll.setMaxSize(mapviewer.getMinWidth()+3, mapviewer.getMinHeight()+3);
    		
    		enlargeBtn.setOnMouseClicked(e -> { this.enlarge(e); });
    		shrinkBtn.setOnMouseClicked(e -> { this.shrink(e); });
    		
    		//initial position of the axe
            axeXCoordinate = axe.getX();
    		
    		axeYCoordinate = axe.getY();
    		
    		System.out.println(axeXCoordinate);
    		
    		System.out.println(axeYCoordinate);
    		
    	
    		//changing the axe position by translating
    		axe.setTranslateX(0);
    		
    		axe.setTranslateY(0);
    		
    		
    	
    		//updating the variables
    		axeXCoordinate = axe.getTranslateX();
      		
      		axeYCoordinate = axe.getTranslateY();
      		
      		System.out.println(axeXCoordinate);
      		
      		System.out.println(axeYCoordinate);
      		
      		
      	
      		//drag the axe imageView
      		axe.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
               public void handle(MouseEvent events) {
                   Dragboard storeImage =axe.startDragAndDrop(TransferMode.MOVE);
                   ClipboardContent content = new ClipboardContent();
                   content.putImage(axe.getImage());
                   storeImage.setContent(content); // here i am getting error
                   events.consume();

                   System.out.println("Dragging");

               }
           });
    		
      		
      		//drop the axe imageView
            //we should have a target for dropping the image 
 
      	
      		
    	}
    	
    	public void enlarge(Event e) {
    		if(scale>=5) return;
    		
    		scale = scale + 0.5;
    		viewLbl.setText((100*scale) + "%");
    		tilemap.render(mapviewer, scale);
    		mapscroll.setMaxSize(mapviewer.getMinWidth()+3, mapviewer.getMinHeight()+3);
    		
    		enlargeBtn.getStyleClass().add("pressed");
    	}
    	
    	public void shrink(Event e) {
    		if(scale<=1) return;
    		
    		scale = scale - 0.5;
    		viewLbl.setText((100*scale) + "%");
    		tilemap.render(mapviewer, scale);
    		mapscroll.setMaxSize(mapviewer.getMinWidth()+3, mapviewer.getMinHeight()+3);
    	}
    	
 
    	//This function will handle the dropping of either the axe or boat
    
         public void dropAxeBoat(ImageView target ,int tileIndex){
        	 
        	 target.setOnDragOver(event -> {
                 /* data is dragged over the target */
                 //System.out.println("onDragOver");

                 /*
                  * accept it only if it is not dragged from the same node and if it
                  * has a string data
                  * 
                  * code for detecting if a tile is blocked or not or if the user is dragging to
                  * outside the map should go here
                  * 
                  */
        		 
        		 
                 if (event.getGestureSource() != target) {
                     /* allow for both copying and moving, whatever user chooses */
                     event.acceptTransferModes(TransferMode.MOVE);
                     
                     System.out.println("accepted");
                 }

                 event.consume();
             });
        	 
        	 
         }
    	
        
}
