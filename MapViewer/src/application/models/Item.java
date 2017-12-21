package application.models;

import javafx.scene.image.ImageView;

public class Item {
	
	private String name;
	
	private int col;
	
	private int row;
	
	private ImageView view;
	
	public Item(String name, int col, int row, ImageView view) {
		this.name = name;
		this.col = col;
		this.row = row;
		this.view = view;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public ImageView getView() {
		return view;
	}

	public void setView(ImageView view) {
		this.view = view;
	}
	
}
