package application.controllers;

import java.util.Observable;
import java.util.Observer;

import application.Main;
import application.models.Items;
import application.models.TileMap;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;

public class MainController {

	@FXML
	public static AnchorPane anchor;

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
	private TextArea tileType;
	
	@FXML
	private TextArea tileCoordinate;

	@FXML
	private ScrollPane mapscroll;

	@FXML
	private Label viewLbl;

	@FXML
	private GridPane mapviewer;
	
	@FXML
	private AnchorPane itemviewer;

	@FXML
	private StackPane stackPane;
	
	private TileMap tilemap;
	
	private Items items;

	static boolean b = false;

	private TextArea tempTypeTA;
	private TextArea tempCoordinateTA;

	@FXML
	private ImageView player1;
	
	@FXML
	private ImageView player2;
	
	@FXML
	private ImageView player3;
	
	@FXML
	private ImageView player4;
	
	//contains the path to the chosen player
	@FXML 
	private String chosenPlayerPath;
	
	int[] focusPos = {-1, -1};
	private double scale = 1;

	Main mains = new Main();
	
	public void initialize() {
		//load items resources

		items = new Items();
		items.init(scale);
		
		//load resources and render Tilemap
		tilemap = new TileMap();
		//storing it temporarily to avoid null pointer exception error 
		storeTextArea(tileType,tileCoordinate);
		tilemap.loadMap("/map.map");
		tilemap.loadTileSet("/images/tileset.gif");
		tilemap.loadDiamond("/images/diamond.gif");
		tilemap.render(mapviewer, items, scale);
		
		//listen to active tile
		tilemap.addObserver(new ActiveTileObserver());
		
		//scroll pane align center of parent
		mapscroll.setMaxSize(mapviewer.getMinWidth() + 3, mapviewer.getMinHeight() + 3);

		//focus scroll pane to cursor location
		mapscroll.setOnMouseEntered(e -> { this.focusPos(e); });
		mapscroll.setOnMouseMoved(e -> { this.focusPos(e); });

		//enlarge and shrink viewport
		enlargeBtn.setOnMouseClicked(e -> { this.zoomIn(e); });
		shrinkBtn.setOnMouseClicked(e -> { this.zoomOut(e); });
		  
        player1.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
             
            	chosenPlayerPath = "images/player1.gif";
            	
            	player1.setOpacity(1);
            	
            	player2.setOpacity(0.3);
            	player3.setOpacity(0.3);
            	player4.setOpacity(0.3);
            }
        });
	
        player2.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	 
            @Override
            public void handle(MouseEvent event) {
            
            	chosenPlayerPath = "images/player2.gif";
            	
            	player2.setOpacity(1);
            	
            	player1.setOpacity(0.3);
            	player3.setOpacity(0.3);
            	player4.setOpacity(0.3);
            }
        });
	
        player3.setOnMouseClicked(new EventHandler<MouseEvent>(){
       	 
            @Override
            public void handle(MouseEvent event) {
              
            	chosenPlayerPath = "images/player3.gif";
            	
            	player3.setOpacity(1);
            	
            	player1.setOpacity(0.3);
            	player2.setOpacity(0.3);
            	player4.setOpacity(0.3);
            	
            	
            }
        });
        
        player4.setOnMouseClicked(new EventHandler<MouseEvent>(){
       	 
            @Override
            public void handle(MouseEvent event) {

            	chosenPlayerPath = "images/player4.gif";
            	
            	player4.setOpacity(1);
            	
            	player1.setOpacity(0.3);
            	player2.setOpacity(0.3);
            	player3.setOpacity(0.3);
            }
        });
	}
	
	public class ActiveTileObserver implements Observer{
		@Override
		public void update(Observable o, Object arg) {
			System.out.println("Active: " + ((TileMap) o).getActiveCol() + " " + ((TileMap) o).getActiveRow() + " " + ((TileMap) o).getActiveType());

			changeTileType(((TileMap) o).getActiveType(),"("+((TileMap) o).getActiveRow()+")" + "," + "("+((TileMap) o).getActiveCol()+")");
		}
	}

	public void zoomIn(Event e) {
		if (scale >= 5)
			return;

		scale = scale + 0.5;
		items.init(scale);
		tilemap.render(mapviewer, items, scale);
		mapscroll.setMaxSize(mapviewer.getMinWidth() + 3, mapviewer.getMinHeight() + 3);
	}

	public void zoomOut(Event e) {
		if (scale <= 1)
			return;

		scale = scale - 0.5;
		items.init(scale);
		tilemap.render(mapviewer, items, scale);
		mapscroll.setMaxSize(mapviewer.getMinWidth() + 3, mapviewer.getMinHeight() + 3);
	}
	
	public void focusPos(MouseEvent e) {
		if(focusPos[0] != -1 && focusPos[1] != -1) {
			//scroll according movement difference and direction
			mapscroll.setHvalue(mapscroll.getHvalue() + (e.getScreenX() - focusPos[0]) * 0.0025 * (1 / scale));
			mapscroll.setVvalue(mapscroll.getVvalue() + (e.getScreenY() - focusPos[1]) * 0.0025 * (1 / scale));
		}
		
		//new reference point
		focusPos[0] = (int) e.getScreenX();
		focusPos[1] = (int) e.getScreenY();
	}

	//this function changes the text area that contain the tile type 
	public void changeTileType(String activeType,String activeCoordinates){

	  tempTypeTA.setText(activeType);
	  
	  tempCoordinateTA.setText(activeCoordinates);
		  
	}
	
	public void storeTextArea(TextArea tileType,TextArea tileCoordinate){
		
		tempTypeTA = tileType;
		tempCoordinateTA = tileCoordinate;
	}
}
