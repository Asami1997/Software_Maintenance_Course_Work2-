package application.controllers;

import java.util.Observable;
import java.util.Observer;

import application.models.Items;
import application.models.TileMap;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

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

	int[] focusPos = {-1, -1};
	private double scale = 1;

	public void initialize() {

		//load items resources
		items = new Items();
		items.init(scale);
		
		//load resources and render Tilemap
		tilemap = new TileMap();
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
	
	}
	
	public class ActiveTileObserver implements Observer{
		@Override
		public void update(Observable o, Object arg) {
			System.out.println("Active: " + ((TileMap) o).getActiveCol() + " " + ((TileMap) o).getActiveRow() + " " + ((TileMap) o).getActiveType());
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

}
