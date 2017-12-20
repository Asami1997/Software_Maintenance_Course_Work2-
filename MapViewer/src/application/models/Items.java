package application.models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Items {

	TileMap tileMap = new TileMap();
	Gson gson = new Gson();
	GsonBuilder gsonBuilder = new GsonBuilder();
	Writer writer = new FileWriter("OutputGSON.json");

	private int[] axe_pos;
	private int[] boat_pos;

    public Items() throws IOException {
    }

    public int[] getAxePos() {
		axe_pos = new int[2];

		axe_pos[0] = tileMap.AxeDesRow;
		axe_pos[1] = tileMap.AxeDesCol;

		return axe_pos;

	}
	
	public int[] getBoatPos() {
		boat_pos = new int[2];

		boat_pos[0] = tileMap.BoatDesRow;
		boat_pos[1] = tileMap.BoatDesCol;

		return boat_pos;
	}
	
	//accept json file location and parse the file
	public void load(String s) {
		
	}
	
	//save position in json file format
	public void save() throws IOException {
    	gsonBuilder.create();
    	gson.toJson(getAxePos(), writer);
    	gson.toJson(getBoatPos(), writer);
    	writer.close();
	}
	
}
