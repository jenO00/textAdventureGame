package project.text.adventure.gameitems;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains Keys which the player has picked up. Keys can be dropped and placed in current room and has a method that
 * return a string describing what the it contains.
 */
public class Inventory
{
    private List<Key> pocket;

    public Inventory() {
	pocket = new ArrayList<>();
    }

    public String add(Key key, Room currentRoom) {
	pocket.add(key);
	currentRoom.removeItem(key);
	return key.referenceName.substring(0, 1).toUpperCase() + key.referenceName.substring(1) + " added to inventory.";
    }

    public String drop(String toDrop, Room room) {
	for (Key keyInPocket : pocket) {
	    if (keyInPocket.nameEquals(toDrop)) {
		room.addItem(keyInPocket);
		removeKey(keyInPocket);
		keyInPocket.setAfarDescription("There is a " + keyInPocket.referenceName + " on the floor");
		return "Your " + toDrop + " has been dropped.";
	    }
	}
	return "Can't find " + toDrop + " in your inventory.";
    }

    public void removeKey(final Key key) {
	pocket.remove(key);
    }

    public void removeKeyByID(final String keyReferenceName) {
        Key key = getKey(keyReferenceName);
        pocket.remove(key);
    }

    public Key getKey(String keyName) {
	for (Key key : pocket) {
	    Key keyFound = key.getKey(keyName);
	    if (key.equals(keyFound)){
	        return keyFound;
	    }
	}
	return null;
    }

    public String getDescription() {
	StringBuilder descriptionBuilder = new StringBuilder();
	descriptionBuilder.append("Inventory:<br/>");
	if (!pocket.isEmpty()) {
	    for (Key key : pocket) {
		descriptionBuilder.append(key.referenceName).append("<br/>");
	    }
	} else {
	    descriptionBuilder.append("Empty");
	}
	return descriptionBuilder.toString();
    }
}
