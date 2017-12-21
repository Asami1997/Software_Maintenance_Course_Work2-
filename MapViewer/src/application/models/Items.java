package application.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Items {
	
	private Item[] items = {
		new Item("axe", 20, 20, new ImageView()),
		new Item("boat", 20, 13, new ImageView())
	};
	
	private Item axe = items[0];
		
	private Item boat = items[1];

	// accept json file location and parse the file
	public void load(String s) {

	}

	// save position in json file format
	public void save(String s) {

	}
	
	public void init(double scale) {		
		//load items images
		axe.getView().setImage(new Image("images/axe.gif", 16*scale, 16*scale, true, true));
		boat.getView().setImage(new Image("images/boat.gif", 16*scale, 16*scale, true, true));

		//put items on map
		for(int i=0; i<items.length; i++) 
			items[i].getView().getStyleClass().add("item");
	}
	
	public Item find(String name) {
		for(int i=0; i<this.items.length; i++) {
			if(this.items[i].getName() == name)
				return this.items[i];
		}
		return null;
	}
	
	public Item get(int col, int row) {
		for(int i=0; i<this.items.length; i++) {
			if(this.items[i].getCol() == col && this.items[i].getRow() == row)
				return this.items[i];
		}
		return null;
	}

}
