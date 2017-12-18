package application.models;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

public class TileMap {

	private int[][] map;
	private String tileset;
	private String Item;
	private int numCols;
	private int numRows;	
	static ImageView TempAxeView;
	static int flag = 1;
	
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
	
public void loadItemSet(String s) {
		
		Item = s;
		
	}
	
	//render map on tilepane
	public void render(GridPane pane, double scale) {
		
		if(tileset == null) return;
		
		pane.getChildren().clear();
		pane.setMinHeight(16*scale*numRows);
		pane.setMinWidth(16*scale*numCols);
		
		Image image = new Image(tileset, 320*scale, 32*scale, true, true);
		Image AxeImage = new Image("/images/axe.png", 16*scale, 16*scale, true, true);
		Image grass = new Image("/images/grass.png", 16*scale, 16*scale, true, true);
		TempAxeView = new ImageView(image); 
		
		
		for(int row=0; row<numRows; row++) {
			for(int col=0; col<numCols; col++) {
				int tile = map[row][col];
				int tile_row = tile/20;
				int tile_col = tile%20;
				
				ImageView view;
				
				if(row==20 && col==20) {
					
					view = new ImageView(AxeImage);
					//viewport helps to show part of tileset, as a tile
					//view.setViewport(new Rectangle2D(20*16*scale, 20*16*scale, 16*scale, 16*scale));
					view.setPreserveRatio(false);
					
					pane.getChildren().add(view);					
				}
				else {
					view = new ImageView(image);
					//viewport helps to show part of tileset, as a tile
					view.setViewport(new Rectangle2D(tile_col*16*scale, tile_row*16*scale, 16*scale, 16*scale));
					view.setPreserveRatio(false);
					
					pane.getChildren().add(view);					
					
				}
				
				GridPane.setConstraints(view, col, row);
				view.setOnDragDetected(event -> {
		            /* drag was detected, start drag-and-drop gesture */
		            System.out.println("onDragDetected");
		           
		            if(view.getImage() == AxeImage) 
		            {
		            	System.out.println("View matches AxeImage");
		            	/* allow any transfer mode */
		            	Dragboard db = view.startDragAndDrop(TransferMode.MOVE);

			            /* put a string on dragboard */
			            ClipboardContent content = new ClipboardContent();
			            content.putImage(view.getImage());
			            db.setContent(content);	
			            if(flag==1) {
			            	view.setImage(TempAxeView.getImage());
				            view.setViewport(new Rectangle2D(1*16*scale, 0*16*scale, 16*scale, 16*scale));
				            flag =0;
			            }
			            else{
			            	view.setImage(TempAxeView.getImage());
				            view.setViewport(new Rectangle2D(tile_col*16*scale, tile_row*16*scale, 16*scale, 16*scale));
			            }
			            
			            
			            event.consume();
		            }
		            
		        });	
				
				view.setOnDragDropped(event -> {
					System.out.println("Dropped");
					if(tile_col!= 0 && tile_row!=1){
							Dragboard db = event.getDragboard();
		            boolean success = false;
		            if (db.hasImage()) {
		                System.out.println("Dropped: ");
		                
		                success = true;
		            }
		            
		            
		            TempAxeView.setImage(view.getImage());
		            view.setImage(AxeImage);
		            
		            view.setViewport(new Rectangle2D(0,0, 16*scale, 16*scale));
		            
		            event.setDropCompleted(success);
		            event.consume();
					}
		    	
		            
		        });				
				
			}
						
		}	
		
	}
		
}
	

