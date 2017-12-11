package application.models;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class TileMap {

	private int[][] map;
	private Image tileset;
	private int numCols;
	private int numRows;
	
	public int[][] getMap(){
		return map;
	}
	
	public Image getTileSet() {
		return tileset;
	}
	
	public int getNumCols() {
		return numCols;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	//copied and modified from Diamond Hunter
	public void loadMap(String s) {
		
		try {
			
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(
						new InputStreamReader(in)
					);
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		    		
	}
	
	public void loadTileSet(String s) {
		
		tileset = new Image(s);
		
	}
	
	//render map on tilepane
	public void render(TilePane pane) {
		
		if(tileset == null) return;
		
		pane.setPrefColumns(numCols);
		pane.setPrefRows(numRows);
		
		for(int row=0; row<numRows; row++) {
			for(int col=0; col<numCols; col++) {
				int tile = map[row][col];
				int tile_row = tile/20;
				int tile_col = tile%20;
				
				ImageView view = new ImageView(tileset);
				//viewport helps to show part of tileset, as a tile
				view.setViewport(new Rectangle2D(tile_col*16, tile_row*16, 16, 16));
				
				pane.getChildren().add(view);
			}
		}
		
	}
	
}
