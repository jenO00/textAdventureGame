package project.text.adventure.gameitems;

import project.text.adventure.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Is a ContentObject and creates a room. Items and Exits can be added and removed from it. Has methods for finding Items,
 * getting keys, pressing buttons and cutting cords.
 */
public class Room
{
    private List<Exit> exits;
    private String description;
    private List<AbstractItem> abstractItems;
    private String referenceName;

    public Room(final String description, final String referenceName) {
	this.description = description;
	exits = new ArrayList<>();
	abstractItems = new ArrayList<>();
	this.referenceName = referenceName;
    }

    public String getDescription() {
	StringBuilder descriptionBuilder = new StringBuilder();
	descriptionBuilder.append(description).append("<br/>");
	for (AbstractItem abstractItem : abstractItems) {
	    if (abstractItem.getAfarDescription() != null) {
		descriptionBuilder.append(abstractItem.getAfarDescription()).append("<br/>");
	    }
	}
	return descriptionBuilder.toString();
    }

    public String getReferenceName() {
	return referenceName;
    }

    public void addExit(Exit exit) {
	exits.add(exit);
    }

    public void addItem(AbstractItem abstractItem) {
	abstractItems.add(abstractItem);
    }

    public void addLockable(Lockable lockable) {
	if (lockable.isBox()) {
	    addItem((Box) lockable);
	} else {
	    addExit((Exit) lockable);
	}
    }

    public void removeItem(AbstractItem abstractItem) {
	abstractItems.remove(abstractItem);
    }

    public AbstractItem findItem(String itemName) {
	for (AbstractItem abstractItem : abstractItems) {
	    AbstractItem foundItem = abstractItem.findItem(itemName);
	    if (foundItem != null) {
		return foundItem;
	    }
	}
	return null;
    }

    public boolean hasLockedDoor(Direction direction) {
	for (AbstractItem abstractItem : abstractItems) {
	    if (abstractItem.canUnlock() && ((Obstacle) abstractItem).getLockable().hasExit(direction)) {
		return true;
	    }
	}
	return false;
    }

    public Key getKey(String keyName) {
	for (AbstractItem abstractItem : abstractItems) {
	    Key keyFound = abstractItem.getKey(keyName);
	    if (keyFound != null) {
		return keyFound;
	    }
	}
	return null;
    }

    public String pressButton(String buttonName, Room currentRoom, Iterable<Room> rooms) {
	String pressButtonString = "";
	for (AbstractItem abstractItem : abstractItems) {
	    pressButtonString = abstractItem.pressButton(buttonName, currentRoom);
	    AbstractItem button = findItem(buttonName);
	    if (!pressButtonString.isEmpty()) {
		if (button != null && button.isCorrectButton()) {
		    unlockLocks(buttonName, rooms);
		}
		return pressButtonString;
	    }
	}
	return "Did not find button to press.";
    }

    public void unlockLocks(String buttonName, Iterable<Room> rooms) {
	for (AbstractItem item : abstractItems) {
	    if (item.hasButton(buttonName)) {
		String locksString = ((ControlPanel) item).getLocksReferenceName();
		Room rightRoom = null;
		AbstractItem locks = null;
		for (Room room : rooms) {
		    rightRoom = room.findRoom(locksString);
		    if (rightRoom != null) {
			locks = room.findItem(locksString);
			break;
		    }
		}
		if (locks != null && locks.canUnlock()) {
		    ((Obstacle) locks).unlock(rightRoom);
		}
	    }
	}
    }

    public Room findRoom(String itemToFind) {
	if (itemToFind != null && findItem(itemToFind) != null) {
	    return this;
	}
	return null;
    }

    public String cutWire(String wireName, Inventory inventory, Key key) {
	String cutWireString = "";
	for (AbstractItem abstractItem : abstractItems) {
	    cutWireString = abstractItem.cut(wireName, inventory, key);
	    AbstractItem wire = findItem(wireName);
	    if (!cutWireString.isEmpty()) {
		if (wire != null && wire.isCorrectWire()) {
		    unlockLockedDoor(wireName);
		}
		return cutWireString;
	    }
	}
	return null;
    }

    public void unlockLockedDoor(String wireName) {
	for (AbstractItem item : abstractItems) {
	    if (item.hasWire(wireName)) {
		String lockedDoorString = ((Robot) item).getLockedDoorReferenceName();
		AbstractItem lockedDoor = findItem(lockedDoorString);
		if (lockedDoor != null && lockedDoor.canUnlock()) {
		    ((Obstacle) lockedDoor).unlock(this);
		}
	    }
	}
    }

    public Exit getExit(Direction dir) {
	for (Exit exit : exits) {
	    if (exit.getDirection().equals(dir)) {
		return exit;
	    }
	}
	return null;
    }

    public void setDescription(final String description) {
	this.description = description;
    }
}
