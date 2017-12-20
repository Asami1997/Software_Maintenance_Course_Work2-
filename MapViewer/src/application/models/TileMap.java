package application.models;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.sun.glass.ui.Robot;

import application.controllers.MainController;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileMap {

	private int[][] map;
	private String tileset;
	private String Item;
	private int numCols;
	private int numRows;	
	static ImageView TempAxeView;
	static int flag = 1;
	int rowDragged;
	int colDragged;
	int indexDragged;
	Image AxeImage;
	Image boatImage;
	Image redAxe;
	ImageView source;
	boolean isBoat = false;
	int tileBefore;
	boolean fromBlocked = false;
	ImageView view2;
	Image draggedOver;
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
	
   public void loadItemSet(String s) {
		
		Item = s;
		
	}
	
	//render map on tilepane
	public void render(GridPane pane, double scale, Rectangle redRectangle) {
		
		if(tileset == null) return;
		
		pane.getChildren().clear();
		pane.setMinHeight(16*scale*numRows);
		pane.setMinWidth(16*scale*numCols);
		
		Image image = new Image(tileset, 320*scale, 32*scale, true, true);
	    AxeImage = new Image("/images/axe.png", 16*scale, 16*scale, true, true);
	    boatImage = new Image("/images/boat.gif", 16*scale, 16*scale, true, true);
	    redAxe = new Image("/images/axeprohib.gif");
		TempAxeView = new ImageView(image); 
		
		
		for(int row=0; row<numRows; row++) {
			
			
			for(int col=0; col<numCols; col++) {
				int tile = map[row][col];
				int tile_row = tile/20;
				int tile_col = tile%20;
				
				ImageView view;
				
				//Adding axe
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
				
				//Adding boat
				if(row == 13 && col == 20){
					
					view = new ImageView(boatImage);
					//viewport helps to show part of tileset, as a tile
					//view.setViewport(new Rectangle2D(20*16*scale, 20*16*scale, 16*scale, 16*scale));
					view.setPreserveRatio(false);
					pane.getChildren().add(view);	
				}
		
				GridPane.setConstraints(view, col, row);

				dragDetection(view,tile_row,tile_col,scale,pane,false,tile);
				
				dropDetection(view , tile_row , tile_col ,scale , pane , tile);
		    	    	 	        							
				dragOver(view,scale, redRectangle);
				
				
			}
						
		}	
		
	}
	
	public void dragDetection(ImageView image2 , int tile_row, int tile_col,double scale , GridPane pane , boolean blocked,int tile ){
		
		image2.setOnDragDetected(event -> {

			rowDragged = GridPane.getRowIndex(image2);
			colDragged = GridPane.getColumnIndex(image2);
			indexDragged = pane.getChildren().indexOf(image2);

            if(image2.getImage() == AxeImage || image2.getImage() == boatImage) 
            {
            	if(image2.getImage() == boatImage){
            		
            		isBoat = true;
            	}
            	System.out.println("View matches AxeImage");
            	/* allow any transfer mode */
            	Dragboard db = image2.startDragAndDrop(TransferMode.MOVE);

	            /* put a string on dragboard */
	            ClipboardContent content = new ClipboardContent();
	            content.putImage(image2.getImage());
	            db.setContent(content);	
	            if(flag==1) {
	            	image2.setImage(TempAxeView.getImage());
	            	//grass
		            image2.setViewport(new Rectangle2D(1*16*scale, 0*16*scale, 16*scale, 16*scale));
		            flag =0;	         
	            }
	            else{ 	  

	            	if(blocked == true){
	    		
	            		int blockedRow = tileBefore/20;
	    				int blockedCol = tileBefore%20;
	    			
	                	Image image = new Image(tileset, 320*scale, 32*scale, true, true);
	                	TempAxeView = new ImageView(image);
	                	image2.setImage(TempAxeView.getImage());
		                image2.setViewport(new Rectangle2D(blockedCol*16*scale,blockedRow*16*scale, 16*scale, 16*scale));  
	            		
	            	}else{
	            		
	            		tileBefore = tile;
	            		image2.setImage(TempAxeView.getImage());
		                image2.setViewport(new Rectangle2D(tile_col*16*scale, tile_row*16*scale, 16*scale, 16*scale));  
	            	}
	              	                     	
	            }
	            
	            event.consume();
            }
            
        });	
		
		
	}
	
	public void dropDetection(ImageView image2 , int tile_row , int tile_col , double scale , GridPane pane , int tile){
		
		image2.setOnDragDropped(event -> {
			System.out.println("Dropped");
		
			
			if(tile == 20 || tile == 21 || tile == 22){
				
				
			System.out.println("Blocked Tile");
		
			//get axe back to where player dragged it from
			
			//remove
			pane.getChildren().remove(indexDragged);
			
			if(isBoat == false){
				
				 view2 = new ImageView(AxeImage);
				 
			}else{
				
				 view2 = new ImageView(boatImage);
				 
				 isBoat = false;
			}
		
			//add
			pane.add(view2, colDragged, rowDragged);
			
			
        	System.out.println(GridPane.getColumnIndex(image2));
        	
			dragDetection(view2,tile_row,tile_col,scale,pane,true,tile);
						
			}else{
				Dragboard db = event.getDragboard();
    	
	    	  boolean success = false;
            if (db.hasImage()) {
                System.out.println("Dropped: ");
                
                success = true;
            }
            
            
            if(isBoat == false){
            	TempAxeView.setImage(image2.getImage());
               image2.setImage(AxeImage);
            }else{
            	
            	TempAxeView.setImage(image2.getImage());
                image2.setImage(boatImage);
                isBoat = false;
            }
            
            
            image2.setViewport(new Rectangle2D(0,0, 16*scale, 16*scale));
            
            event.setDropCompleted(success);
            event.consume();
            	
    		System.out.println(tile);
    		
			}
    	    			
        });		
		
	}
	
	//runs when dragging the axe or boat under a tile
	public void dragOver(ImageView target , double scale, Rectangle redRectangle){
		
        target.setOnDragOver(e -> {
            	redRectangle.setX(700);
				redRectangle.setY(700);
            	
        		if(e.getDragboard().hasImage()){
        			
        			if(GridPane.getColumnIndex(target) == 36 && GridPane.getRowIndex(target) == 39){
        				
        			}
        			  
            		e.acceptTransferModes(TransferMode.ANY);
            	}

                e.consume();
        });

   }
	
}
