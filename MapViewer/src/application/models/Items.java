package application.models;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Items {

	private int[] axe_pos;
	private int[] boat_pos;

	public int[] getAxePos() {
		return axe_pos;
	}

	public int[] getBoatPos() {
		return boat_pos;
	}

	// accept json file location and parse the file
	public void load(String s) {

	}

	// save position in json file format
	public void save(String s) {

	}
	
	public void render(AnchorPane itemviewer, double scale) {
		itemviewer.getChildren().clear();
		
		Image items = new Image("images/items.gif", 48*scale, 32*scale, true, true);
		
		//Axe
		ImageView Axe = new ImageView(items);
		Axe.setViewport(new Rectangle2D(16*scale, 16*scale, 16*scale, 16*scale));
		this.put(Axe, 20, 20, scale);
		Axe.getStyleClass().add("item");
		itemviewer.getChildren().addAll(Axe);
		
		//Boat
		ImageView Boat = new ImageView(items);
		Boat.setViewport(new Rectangle2D(0*scale, 16*scale, 16*scale, 16*scale));
		this.put(Boat, 20, 13, scale);
		Boat.getStyleClass().add("item");
		itemviewer.getChildren().addAll(Boat);
	}
	
	private void put(Node node, int x, int y, double scale) {
		AnchorPane.setLeftAnchor(node, x*16*scale+1+x);
		AnchorPane.setTopAnchor(node, y*16*scale+1+y);
	}

}
