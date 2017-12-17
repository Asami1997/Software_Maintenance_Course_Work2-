package application.models;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import application.controllers.*;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.TilePane;

public class TileMap {

	private int[][] map;
	private String tileset;
	private int numCols;
	private int numRows;
	private Image image;
	public static ImageView view;
	private int tileIndex;
	public static ImageView axeImageView;
	MainController mainController = new MainController();
	
	public int[][] getMap(){
		return map;
	}
	
	public String getTileSet() {
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
		
		tileset = s;
		
	}
	
	//render map on tile pane
	public void render(TilePane pane, double scale) {
		
		if(tileset == null) return;
		
		pane.getChildren().clear();   
		pane.setMinHeight(16*scale*numRows);
		pane.setMinWidth(16*scale*numCols);
		
	    Image axeImage = new Image("/images/itemsq.jpeg" , 16*scale, 16*scale, true, true);

		Image image = new Image(tileset, 320*scale, 32*scale, true, true);
		
		for(int row=0; row<numRows; row++) {
			for(int col=0; col<numCols; col++) {
				int tile = map[row][col];
				int tile_row = tile/20;
				int tile_col = tile%20;
			    int rowTilePane = tileIndex/40;
                int colTilePane = tileIndex%40;
                
                //setting the axe intial position 
                if(rowTilePane == 20 && colTilePane ==20){
                	
                	axeImageView = new ImageView(axeImage);
                  	
                	pane.getChildren().add(axeImageView);
                	
                	//getting index of view in the pane
    				
    		        tileIndex = pane.getChildren().indexOf(axeImageView);
    		        
    				mainController.dropAxeBoat(axeImageView ,tileIndex);
                		
			}else{
				
	        	    view = new ImageView(image);
					//viewport helps to show part of tileset, as a tile
		   		    view.setViewport(new Rectangle2D(tile_col*16*scale, tile_row*16*scale, 16*scale, 16*scale));
		   		
			    	view.setPreserveRatio(false);
					
				    pane.getChildren().add(view);
			
				  //getting index of view in the pane
				
		           tileIndex = pane.getChildren().indexOf(view);
		        
				   mainController.dropAxeBoat(view ,tileIndex);			}
                    			
			}
		
	  }
	
	}
}
