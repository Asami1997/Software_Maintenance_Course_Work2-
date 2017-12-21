package application.models;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class TileMap {

	private int[][] map;
	private String tileset;
	private int numCols = -1;
	private int numRows = -1;
	static int flag = 1;
	int rowDragged;
	int colDragged;
	int indexDragged;
	Image AxeImage;
	Image boatImage;
	Image redAxe;
	ImageView source;
	ImageView TempAxeView;
	boolean isBoat = false;
	int tileBefore;
	boolean fromBlocked = false;
	ImageView view2;
	Image draggedOver;
	Rectangle redRectangle;

	public int[][] getMap() {
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

	// render map on tilepane
	public void render(GridPane pane, double scale) {
		if (tileset==null || numRows<0 || numCols<0) return;

		//set up pane
		pane.getChildren().clear();
		pane.setMinHeight(16 * scale * numRows);
		pane.setMinWidth(16 * scale * numCols);

		Image image = new Image(tileset, 320 * scale, 32 * scale, true, true);

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				//get representation
				int tile = map[row][col];
				int tile_row = tile / 20;
				int tile_col = tile % 20;

				// render tile
				ImageView view = new ImageView(image);
				view.setViewport(
							new Rectangle2D(tile_col * 16 * scale, tile_row * 16 * scale, 16 * scale, 16 * scale));
				pane.add(view, col, row);
				
				view.setOnMouseEntered(e -> {
					System.out.println("a");
					int index = pane.getChildren().indexOf(view);
					System.out.println("Active Tile: " + index%40 + " " + index/40);
				});

				//attach events
				/*view.setOnDragDetected(e -> {
					this.dragDetection(e, view, tile_row, tile_col, scale, pane, false, tile_col);
				});
				view.setOnDragDropped(e -> {
					this.dropDetection(e, view, tile_row, tile_col, scale, pane, tile);
				});*/
			}
		}

	}

	public void dragDetection(MouseEvent e, ImageView image2, int tile_row, int tile_col, double scale, GridPane pane,
			boolean blocked, int tile) {

		rowDragged = GridPane.getRowIndex(image2);
		colDragged = GridPane.getColumnIndex(image2);
		indexDragged = pane.getChildren().indexOf(image2);

		if (image2.getImage() == AxeImage || image2.getImage() == boatImage) {
			if (image2.getImage() == boatImage) {
				isBoat = true;
			}
			/* allow any transfer mode */
			Dragboard db = image2.startDragAndDrop(TransferMode.MOVE);

			/* put image on dragboard */
			ClipboardContent content = new ClipboardContent();
			content.putImage(image2.getImage());
			db.setContent(content);
			if (flag == 1) {
				image2.setImage(TempAxeView.getImage());
				// grass
				image2.setViewport(new Rectangle2D(1 * 16 * scale, 0 * 16 * scale, 16 * scale, 16 * scale));
				flag = 0;
			} else if (blocked) {

				int blockedRow = tileBefore / 20;
				int blockedCol = tileBefore % 20;

				Image image = new Image(tileset, 320 * scale, 32 * scale, true, true);
				TempAxeView = new ImageView(image);
				image2.setImage(TempAxeView.getImage());
				image2.setViewport(
						new Rectangle2D(blockedCol * 16 * scale, blockedRow * 16 * scale, 16 * scale, 16 * scale));

			} else {

				tileBefore = tile;
				image2.setImage(TempAxeView.getImage());
				image2.setViewport(
						new Rectangle2D(tile_col * 16 * scale, tile_row * 16 * scale, 16 * scale, 16 * scale));
			}

			e.consume();
		}

	}

	public void dropDetection(DragEvent event, ImageView image2, int tile_row, int tile_col, double scale, GridPane pane,
			int tile) {

		if (tile == 20 || tile == 21 || tile == 22) {

			// get axe back to where player dragged it from

			// remove
			pane.getChildren().remove(indexDragged);

			if (!isBoat) {
				view2 = new ImageView(AxeImage);
			} else {
				view2 = new ImageView(boatImage);
				isBoat = false;
			}

			// add
			pane.add(view2, colDragged, rowDragged);

			view2.setOnDragDetected(e -> {
				this.dragDetection(e, view2, tile_row, tile_col, scale, pane, true, tile);
			});

		} else {
			Dragboard db = event.getDragboard();
			boolean success = db.hasImage();

			if (!isBoat) {
				TempAxeView.setImage(image2.getImage());
				image2.setImage(AxeImage);
			} else {

				TempAxeView.setImage(image2.getImage());
				image2.setImage(boatImage);
				isBoat = false;
			}

			image2.setViewport(new Rectangle2D(0, 0, 16 * scale, 16 * scale));

			event.setDropCompleted(success);
			event.consume();
		}

	}

}
