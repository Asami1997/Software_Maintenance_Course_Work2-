package application.models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;

public class Items {

	TileMap tileMap = new TileMap();
	Gson gson = new Gson();

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
	
	//accept json file location and save it in the correct format
	public void load(String s) {
		
	}
	
	//save position in json file format
	public void save() throws IOException {

    	// System file paths

		String filename = "file.json";
		String workingDir = System.getProperty("user.dir");
		String absPath = workingDir + File.separator + filename;

    	// Created JSON Array for axe position
		System.out.println("Json file created"); // Notification that the JSON file was saved successfully
		JsonArray jsonArrayAxe = new JsonArray();
		jsonArrayAxe.add(this.getAxePos()[0]);
		jsonArrayAxe.add(this.getAxePos()[1]);
		// Created JSON Array for the boat position
		JsonArray jsonArrayBoat = new JsonArray();
		jsonArrayBoat.add(this.getBoatPos()[0]);
		jsonArrayBoat.add(this.getBoatPos()[1]);
		// Added both JSON Arrays to a JSON Object after naming them
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("Axe Position", jsonArrayAxe);
		jsonObject.add("Boat Position", jsonArrayBoat);

		// Saved JSON object into a file after converting it to a string
		Writer writer = new FileWriter(absPath);
		writer.write(String.valueOf(jsonObject));
		writer.close();

		System.out.println("Saved JSON to " + absPath); // Notification that the JSON file was saved successfully
	}
	
}
