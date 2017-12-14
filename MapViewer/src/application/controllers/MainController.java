package application.controllers;

import application.models.TileMap;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

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
    private ImageView axe;
    
    private double axeXCoordinate;
    
    private double axeYCoordinate; 
    
    private TileMap tilemap;
    
    private double scale = 1;
    
    	public void initialize() {
    		
    		//getting the coordinates of the axe
    		
    		
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
    		axe.setTranslateX(30);
    		
    		axe.setTranslateY(30);
    		
    		
    	
    		//updating the variables
    		axeXCoordinate = axe.getTranslateX();
      		
      		axeYCoordinate = axe.getTranslateY();
      		
      		System.out.println(axeXCoordinate);
      		
      		System.out.println(axeYCoordinate);
    		
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
    	
    
    
}
