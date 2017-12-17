package application.controllers;

import com.sun.javafx.event.EventHandlerManager;

import application.models.TileMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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
    
    
    @FXML
    private Label viewLbl;
    
    @FXML
    private TilePane mapviewer;
    
    @FXML
    private StackPane stackPane;
    
    private Image axeImage;
    
    //for dragging
    private ImageView source;
    
    private double axeXCoordinate;
    
    private double axeYCoordinate; 
    
    private TileMap tilemap;
    
    private double scale = 1;
    
    int tileRow = -1;
    
    int tileCol = -1;          

    ImageView axeImageView;

        public void initialize() {
        
    		axeImage = new Image("/images/itemsq.jpeg", 16*scale, 16*scale, true, true);
    				
    		tilemap = new TileMap();
    		tilemap.loadMap("/map.map");
    		tilemap.loadTileSet("/images/tileset.gif");
  
    		tilemap.render(mapviewer, scale);
    		
    		mapscroll.setMaxSize(mapviewer.getMinWidth()+3, mapviewer.getMinHeight()+3);
    		enlargeBtn.setOnMouseClicked(e -> { this.enlarge(e); });
    		shrinkBtn.setOnMouseClicked(e -> { this.shrink(e); });
    		
      		System.out.println(axeXCoordinate);  		
      		System.out.println(axeYCoordinate);
        	System.out.println("in this function");
        	
        	source  = TileMap.axeImageView;
    		//drag the axe imageView
	   		source.setOnDragDetected(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent events) {
	                Dragboard storeImage = source.startDragAndDrop(TransferMode.MOVE);
	                ClipboardContent content = new ClipboardContent();
	                content.putImage(source.getImage());
	                storeImage.setContent(content); 
	                events.consume(); 
	                System.out.println("Dragging");
	            }
	        });
   		

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
                  * accept it only if it is not dragged from the same node 
                  * 
                  * code for detecting if a tile is blocked or not or if the user is dragging to
                  * outside the map should go here
                  * 
                  */
                 if (event.getGestureSource() != target) {
                     /* allow for moving */
                     event.acceptTransferModes(TransferMode.MOVE);
                     System.out.println("accepted");
                 }

                 
                 event.consume();
             });
        	 
        	 
        	 target.setOnDragDropped(event -> {
                 /* data dropped */
                 System.out.println("onDragDropped");
                 
                 /* if there is a string data on dragboard, read it and use it */
                 Dragboard db = event.getDragboard();
                 boolean success = false;
                 if (db.hasImage()) {
                	 target.setImage(db.getImage());
                	 target.setViewport(new Rectangle2D(0,0, 16, 16));
                	 //target.setImage(axeImage);
                     success = true;
                 }
                
                event.setDropCompleted(success);
                event.consume();
             });
       
        	 
        	 target.setOnDragDone(event -> {
                 /* the drag-and-drop gesture ended */
                 System.out.println("onDragDone");
                 /* if the data was successfully moved, clear it */
                 if (event.getTransferMode() == TransferMode.MOVE) {
                   System.out.println("Successfully dropped");
                 }   
                    event.consume();
             });
         }
	
}
