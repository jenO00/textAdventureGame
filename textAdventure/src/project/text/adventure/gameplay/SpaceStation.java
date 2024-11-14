package project.text.adventure.gameplay;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import project.text.adventure.gameitems.AbstractItem;
import project.text.adventure.gameitems.ContentInBox;
import project.text.adventure.gameitems.Lockable;
import project.text.adventure.gameitems.Room;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates all Rooms, Items and Exits needed for the game. Items and Exits are added in the correct room.
 */
public class SpaceStation
{
    private List<Room> rooms;

    public SpaceStation() {
	rooms = new ArrayList<>();
	try {
	    rooms = readSpaceStation();
	} catch (IOException e) {
	    rooms = startAgain(e.getMessage());
	}
    }

    public List<Room> getRooms() {
	return rooms;
    }

    public List<Room> startAgain(String errorMessage) {
	while (true) {
	    try {
		if (askUser(errorMessage + "\n\nTry Again?")) {
		    return readSpaceStation();
		}
		System.exit(0);
	    } catch (IOException e) {
		errorMessage = e.getMessage();
	    }
	}
    }
    public static boolean askUser(String question) {
        return JOptionPane.showConfirmDialog(null, question, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static List<Room> readSpaceStation() throws IOException, FileNotFoundException {
	String jsonString = readFile();
	Gson gsonParser = new GsonBuilder()
		.registerTypeAdapter(AbstractItem.class, new ItemAdapterBorrowedCode<AbstractItem>())
		.registerTypeAdapter(Lockable.class, new ItemAdapterBorrowedCode<Lockable>())
		.registerTypeAdapter(ContentInBox.class, new ItemAdapterBorrowedCode<ContentInBox>())
		.create();
	Type type = new TypeToken<ArrayList<Room>>(){}.getType();
	List<Room> rooms = gsonParser.fromJson(jsonString, type);
	return rooms;
    }

    public static String readFile() throws IOException, FileNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    try (InputStream inputStream = classLoader.getResourceAsStream("SpaceStation.json")) {
	        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
		    String line = bufferedReader.readLine();
		    StringBuilder sb = new StringBuilder();
		    while (line != null) {
			sb.append(line).append("\n");
			line = bufferedReader.readLine();
		    }
		    String fileAsString = sb.toString();
		    return fileAsString;
		}
	    }
    }
}
