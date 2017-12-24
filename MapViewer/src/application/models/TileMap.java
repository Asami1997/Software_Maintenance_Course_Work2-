package application.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Observable;

import javafx.event.Event;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class TileMap extends Observable{

	private int[][] map;
	private String tileset;
<<<<<<< HEAD
	private String diamond = "images/diamond.gif";
	private int[][] dpos = {
		{20,20},
		{12,36},
		{28,4},
		{4,34}
	};
	private int numCols = -1;
	private int numRows = -1;
	
	private int active_col = -1;
	private int active_row = -1;
	private String active_type = null;
	
	Item dragging;
	
	public int[][] getMap() {
=======
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
	ImageView source;
	boolean isBoat = false;
	int tileBefore;

	static int AxeDesRow, AxeDesCol, BoatDesRow, BoatDesCol;

	boolean fromBlocked = false;
	ImageView view2;
	MainController mainController = new MainController();

	public TileMap() throws IOException {
	}

	public int[][] getMap(){
>>>>>>> Elsayed_Branch
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
	
	public int getActiveCol() {
		return active_col;
	}

	public int getActiveRow() {
		return active_row;
	}

	public String getActiveType() {
		return active_type;
	}

	// copied and modified from Diamond Hunter
	public void loadMap(String s) {

		try {

			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];

			String delims = "\\s+";
			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadTileSet(String s) {
		tileset = s;
	}
	
	public void loadDiamond(String s) {
		diamond = s;
	}
	
	public boolean isBlocked(int col, int row) {
		int tile = map[row][col];
		return ((tile==20) || (tile==21) || (tile==22));
	}

	// render map on tilepane
	public void render(GridPane grid, Items items, double scale) {
		if (tileset==null || numRows<0 || numCols<0) return;

		//set up pane
		grid.getChildren().clear();
		grid.setMinHeight(16 * scale * numRows + 1 + numRows);
		grid.setMinWidth(16 * scale * numCols + 1 + numCols);

		Image image = new Image(tileset, 320 * scale, 32 * scale, true, true);

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				//get representation
				int tile = map[row][col];
				int tile_row = tile / 20;
				int tile_col = tile % 20;

				//tile stack
				StackPane stack = new StackPane();
				
				//render tile background
				ImageView view = new ImageView(image);
				view.setViewport(
							new Rectangle2D(tile_col * 16 * scale, tile_row * 16 * scale, 16 * scale, 16 * scale));
				stack.getChildren().add(view);
				
				//render item
				Item item = items.get(col, row);
				if(item != null) 
					stack.getChildren().add(item.getView());
				
				grid.add(stack, col, row);
								
				//add hints
				stack.setOnMouseEntered(e -> { this.activeTile(e, grid, stack); this.showHint(grid, stack, scale); });
				stack.setOnMouseExited(e -> { this.removeHint(grid, stack); });
				
				//drag items
				stack.setOnDragDetected(e -> { this.dragItem(e, grid, stack, items); });
				stack.setOnDragEntered(e -> { this.showHint(grid, stack, scale); });
				stack.setOnDragOver(e -> { this.overItem(e, stack); });
				stack.setOnDragExited(e -> { this.removeHint(grid, stack); });
				stack.setOnDragDropped(e -> { this.dropItem(e, grid, stack); });
			}
		}
		
		//render diamonds
		Image dimage = new Image(diamond, 64*scale, 16*scale, true, true);
		
		for(int i=0; i<dpos.length; i++) {
			StackPane stack = (StackPane) grid.getChildren().get(numRows*dpos[i][0]+dpos[i][1]);
			ImageView view = new ImageView(dimage);
			view.setViewport(new Rectangle2D(0,0, 16*scale, 16*scale));
			stack.getChildren().add(view);
		}

	}
	
	public void activeTile(Event e, GridPane grid, StackPane stack) {
		int index = grid.getChildren().indexOf(stack);
		active_col = index%40;
		active_row = index/40;
		
		switch(map[active_row][active_col]) {
		
	    //1 grass , 2 bush , 3 flower , 20 : green tree, 21 daed tree , 22 water
		
		case 1: 
			active_type = "Grass";
		    break;
		case 2 : 
			active_type = "Bush";
			break;
		case 3 :
			active_type = "Flower";
			break;
		case 21 :
			active_type = "Dead Tree";
			break;
		case 22: 
			active_type ="water";
			break;
		default:
			active_type = " Green Tree";
		}
		
		//notify active tile changed
		setChanged();
		notifyObservers();
	}
	
	public void showHint(GridPane grid, StackPane stack, double scale) {
		int index = grid.getChildren().indexOf(stack);
		int stack_col = index%40;
		int stack_row = index/40;
		
		Rectangle hint = new Rectangle();
		hint.setWidth(16*scale);
		hint.setHeight(16*scale);
		if(this.isBlocked(stack_col, stack_row)) {
			hint.getStyleClass().add("hint-block");
		}else {
			hint.getStyleClass().add("hint-allow");
		}
		stack.getChildren().add(1, hint);
	}
	
	public void removeHint(GridPane grid, StackPane stack) {
		Iterator<Node> itr = stack.getChildren().iterator();
		while(itr.hasNext()) {
			if(itr.next().getClass().getName() == "javafx.scene.shape.Rectangle")
				itr.remove();
		}
	}
	
	public void dragItem(Event e, GridPane grid, StackPane stack, Items items) {
		int index = grid.getChildren().indexOf(stack);
		int stack_col = index%40;
		int stack_row = index/40;
		
		dragging = items.get(stack_col, stack_row);
		if(dragging != null) {
			// allow dragging mode
			Dragboard db = stack.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putImage(dragging.getView().getImage());
			db.setContent(content);
			
			//remove image from grid
			stack.getChildren().remove(dragging.getView());
		}
		
<<<<<<< HEAD
		e.consume();
	}
	
	public void overItem(DragEvent e, StackPane stack) {
		if (e.getGestureSource() != stack &&
                e.getDragboard().hasImage()) 
            e.acceptTransferModes(TransferMode.MOVE);
        
        e.consume();
	}
	
	public void dropItem(DragEvent e, GridPane grid, StackPane stack) {
		int index = grid.getChildren().indexOf(stack);
		int stack_col = index%40;
		int stack_row = index/40;
	
		//update item position
		if((dragging != null)){			
			if(this.isBlocked(stack_col, stack_row)) {
				StackPane old = (StackPane) grid.getChildren().get(40*dragging.getRow()+dragging.getCol());
				old.getChildren().add(dragging.getView());
				dragging.setCol(dragging.getCol());
				dragging.setRow(dragging.getRow());
			}else {
				stack.getChildren().add(dragging.getView());
				dragging.setCol(stack_col);
				dragging.setRow(stack_row);
=======
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

				AxeDesRow =  GridPane.getRowIndex(image2);
				AxeDesCol = GridPane.getColumnIndex(image2);

				System.out.println("Row and Column of Axe: " + AxeDesRow + " " + AxeDesCol);
            }else{
            	TempAxeView.setImage(image2.getImage());
                image2.setImage(boatImage);
                isBoat = false;

				BoatDesRow = GridPane.getRowIndex(image2);
				BoatDesCol = GridPane.getColumnIndex(image2);

				System.out.println("Row and Column of Boat: " + BoatDesRow + " " + BoatDesCol);
            }

            image2.setViewport(new Rectangle2D(0,0, 16*scale, 16*scale));
            
            event.setDropCompleted(success);
            event.consume();

    		System.out.println(tile);
    		
>>>>>>> Elsayed_Branch
			}
			dragging = null;
		}
		
		//complete drag mode
		Dragboard db = e.getDragboard();
		boolean success = db.hasImage();
		e.setDropCompleted(success);
		e.consume();
	}
}