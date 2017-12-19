package application.controllers;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;


import application.models.TileMap;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.InputEvent;
import javafx.event.EventHandler;



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
	    private GridPane mapviewer;
	    
	    @FXML
	    private StackPane stackPane;
	    
	    private Image axeImage;
	  
	    int tileRow = -1;
	    
	    int tileCol = -1;          

	    Image AxeImage;
	    
	    ImageView axeImageView;
    
       private TileMap tilemap;
    
       private double scale = 1;
    
    	public void initialize() {
    		
    		 AxeImage = new Image("/images/axe.png", 16*scale, 16*scale, true, true);

    		tilemap = new TileMap();
    		tilemap.loadMap("/map.map");
    		tilemap.loadTileSet("/images/tileset.gif");
    		
    		tilemap.render(mapviewer, scale);
    		
    		mapscroll.setMaxSize(mapviewer.getMinWidth()+3, mapviewer.getMinHeight()+3);
    		
    		enlargeBtn.setOnMouseClicked(e -> { this.zoomIn(e); });
    		shrinkBtn.setOnMouseClicked(e -> { this.zoomOut(e); });
    	
    	}
    	
    	
    	public void zoomIn(Event e) {
    		if(scale>=5) return;
    		
    		scale = scale + 0.5;
    		tilemap.render(mapviewer, scale);
    		mapscroll.setMaxSize(mapviewer.getMinWidth()+3, mapviewer.getMinHeight()+3);
    	}
    	
    	public void zoomOut(Event e) {
    		if(scale<=1) return;
    		
    		scale = scale - 0.5;
    		tilemap.render(mapviewer, scale);
    		mapscroll.setMaxSize(mapviewer.getMinWidth()+3, mapviewer.getMinHeight()+3);
    	}
    	
    	
    	@FXML
    	private void handleDragOver(DragEvent event){
    		
    		//Check If Tile Is Blocked
    
    		if(event.getDragboard().hasImage())
    		event.acceptTransferModes(TransferMode.ANY);
    	}


      public void setAxeBack(int colDragged , int rowDragged){
    	  
    	  
      }
    	
}
