package application.controllers;

import application.models.TileMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

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
    private TilePane mapviewer;
    
    private TileMap tilemap;
    
    	public void initialize() {
    		
    		tilemap = new TileMap();
    		tilemap.loadMap("/map.map");
    		tilemap.loadTileSet("/images/tileset.gif");
    		
    		tilemap.render(mapviewer);
    		
    	}
    
}
