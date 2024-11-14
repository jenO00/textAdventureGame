package project.text.adventure.gameplay;

import project.text.adventure.Direction;
import project.text.adventure.gameitems.Exit;
import project.text.adventure.gameitems.Inventory;
import project.text.adventure.gameitems.AbstractItem;
import project.text.adventure.gameitems.Key;
import project.text.adventure.gameitems.Obstacle;
import project.text.adventure.gameitems.Room;

import javax.swing.*;

/**
 * Takes a command and execute necessary changes in the game and returns a string for printing to the player, if it is a valid
 * command.
 */
public class Command
{
    private Inventory inventory;
    private Room currentRoom;
    private boolean gameOver = false;
    private Iterable<Room> listRoom;
    private JFrame frame;
    private boolean gameWon = false;

    public Command(final Room currentRoom, final Iterable<Room> listRoom) {
	this.inventory = new Inventory();
	this.currentRoom = currentRoom;
	this.listRoom = listRoom;
	frame = null;
    }

    private static String getRestWord(final String str) {
	return str.substring(str.indexOf(" ") + 1);
    }

    public static String getWordAfter(String fullStr, String str) {
	if (fullStr.contains(str) && fullStr.indexOf(str) + 1 + str.length() < fullStr.length()) {
	    return fullStr.substring(fullStr.indexOf(str) + str.length() + 1);
	} else {
	    return "";
	}
    }

    public static String getWordBeforeWith(String fullStr) {
	int indexWith = fullStr.indexOf("with");
	if (indexWith > 0) {
	    return fullStr.substring(0, indexWith - 1);
	} else {
	    return "";
	}
    }

    public static String getFirstWord(String str) {
	if (str.contains(" ")) {
	    return str.substring(0, str.indexOf(" "));
	} else {
	    return str;
	}
    }

    public String useCommandLook() {
	return currentRoom.getDescription();
    }

    public String useCommandGo(String command) {
	String direction = getRestWord(command);
	if (isEnum(direction)) {
	    Direction dir = Direction.valueOf(direction.toUpperCase());
	    Exit exit = currentRoom.getExit(dir);
	    if (currentRoom.hasLockedDoor(dir)) {
		return "The door is locked.";
	    }
	    if (exit != null) {
		String moveToRoomString = exit.getToRoomReferenceName();
		Room moveToRoom = getRoomByID(moveToRoomString);
		if (moveToRoom != null) {
		    currentRoom = moveToRoom;
		    return moveToRoom.getDescription();
		}
	    }
	}
	return "You can't go that way.";
    }

    public boolean isEnum(String dir) {
        Direction[] directions = Direction.values();
	for (Direction direction : directions) {
	    boolean isEnum = direction.toString().equals(dir.toUpperCase());
	    if (isEnum) {
	        return true;
	    }
	}
	return false;
    }

    public Room getRoomByID(String id) {
	for (Room room : listRoom) {
	    /** Used to match a room with its reference name (id), not for type checking. */
	    if (room.getReferenceName().equals(id)) {
		return room;
	    }
	}
	return null;
    }

    public String useCommandExamine(String command) {
	AbstractItem abstractItemToExamine = currentRoom.findItem(getRestWord(command));
	if (abstractItemToExamine != null) {
	    return abstractItemToExamine.getDescription();
	} else {
	    return "There is no such item to examine.";
	}
    }

    public String useCommandOpen(String command) {
	if (!getRestWord(command).isEmpty()) {
	    AbstractItem abstractItemToOpen = currentRoom.findItem((getRestWord(command)));
	    if (abstractItemToOpen != null) {
		gameWon = abstractItemToOpen.getWinsGame();
		gameOver = abstractItemToOpen.givesGameOver();
		return abstractItemToOpen.open();
	    } else {
		return "Can't find what to open.";
	    }
	} else {
	    return "I don't know what to open.";
	}
    }

    public String openInventory() {
	return inventory.getDescription();
    }

    public String useCommandTake(String command) {
	String toTake = getRestWord(command);
	Key keyInInventory = inventory.getKey(toTake);
	if (keyInInventory != null) {
	    return "This is already in your inventory.";
	} else {
	    Key itemToTake = currentRoom.getKey(toTake);
	    if (itemToTake != null) {
		return inventory.add(itemToTake, currentRoom);
	    } else {
		AbstractItem abstractItem = currentRoom.findItem(toTake);
		if (abstractItem != null) {
		    return abstractItem.cantTakeString();
		}
		return "No such thing to take. Did you misspell?";
	    }
	}
    }

    public String useCommandDrop(String command) {
	return inventory.drop(getRestWord(command), currentRoom);
    }

    public String useCommandUse(String command) {
	String restOfCommand = getRestWord(command);
	if (!restOfCommand.isEmpty()) {
	    String useKey = getFirstWord(restOfCommand);
	    String useBox = getWordAfter(restOfCommand, "on");
	    if (!useBox.isEmpty()) {
		AbstractItem abstractItemToUnlock = currentRoom.findItem(useBox);
		if (abstractItemToUnlock != null) {
		    if (abstractItemToUnlock.canUnlock()) {
			Key toUnlock = inventory.getKey(useKey);
			if (toUnlock != null) {
			    return ((Obstacle) abstractItemToUnlock).unlockWithKey(toUnlock, currentRoom, inventory);
			} else {
			    return "You don't have " + useKey + " in your inventory.";
			}
		    } else {
			return "You can't unlock " + abstractItemToUnlock.getReferenceName();
		    }
		} else {
		    return "Can't use this.";
		}
	    } else {
		return "I don't know what to use this on.";
	    }
	} else {
	    return "I don't know what to use.";
	}
    }

    public String useCommandPress(String command) {
	String buttonName = getRestWord(command);
	AbstractItem toPress = currentRoom.findItem(buttonName);
	String featureStr = "";
	if (toPress != null) {
	    gameOver = toPress.givesGameOver();
	    if (buttonName.equals("yellow button") && inventory.getKey("medicine") != null) {
		featureStr = "\n" +
			     "My heart stops in my chest as I watch the gas fill the room and the desperation claws in my chest. \n" +
			     "Immediately, I rip my pockets inside out for a solution, for something. \n" +
			     "The weird labelless medicine rests in my hand, not even a second before I chug it all down. <br/>" +
			     "It tastes bitter.. but i don't mind. <br/>" + "As least... I attentively watch the gas. <br/>" +
			     "It doesn't seem to bother me anymore? <br/>" +
			     "I exhale loudly as I put a hand over my frantically beating heart. <br/>" +
			     "I'm a lucky bastard.";
		gameOver = false;
	    }
	}
	return currentRoom.pressButton(buttonName, currentRoom, listRoom) + featureStr;
    }

    public String useCommandCut(String command) {
	String restOfCommand = getRestWord(command);
	if (!restOfCommand.isEmpty()) {
	    String useKnifeOn = getWordBeforeWith(restOfCommand);
	    String useKnife = getWordAfter(restOfCommand, "with");
	    Key toUse = inventory.getKey(useKnife);
	    if (toUse != null) {
		switch (useKnifeOn) {                //Code relies on input, so we refrained from using enums.
		    case "self":
		    case "myself":
			gameOver = true;
			return "The stab sends a horrible pain through my stomach, and as I lose my breath, <br/>" +
			       "I fall to my knees. Hail Rowland.";
		    case "robot":
			AbstractItem robotInRoom = currentRoom.findItem("robot");
			if (robotInRoom != null) {
			    gameOver = true;
			    return "The small surgery knife doesn't even leave a scratch, and I watch in horror as the robot's eyes suddenly meet mine. " +
				   "Immediately, a loud bang fills the room, and as I look down I notice blood soaking my shirt. <br/>" +
				   "Shit.";
			} else {
			    return "Can't find that in here. <br/>" + "Maybe I should use it one something else?";
			}
		    default:
			AbstractItem useKnifeOnAbstractItem = currentRoom.findItem(useKnifeOn);
			String cutString = currentRoom.cutWire(useKnifeOn, inventory, toUse);
			if (cutString != null && useKnifeOnAbstractItem != null) {
			    gameOver = useKnifeOnAbstractItem.givesGameOver();
			    return cutString;
			}
			return "Why would I want to cut that?";
		}
	    } else {
		return "Can't use this.";
	    }
	} else {
	    return "I don't know what to cut this with.";
	}
    }

    public String useCommandHelp() {
	JOptionPane.showMessageDialog(frame, "COMMANDLIST AND EXAMPLE OF USAGES: \n" + "\n" + "Look around\n" +
					     "prints the current room's description again.\n \n" + "Go - e.g 'go north'\n" +
					     "takes you to the next room in that direction.\n \n" +
					     "Examine or x - e.g 'examine note' or 'x note'\n" +
					     "gives you a closer look at things. This might even give you some hints!\n \n" +
					     "Open - e.g 'open cabinet'\n" +
					     "lets you open encapsulated areas and explore further. \n \n" +
					     "Use - e.g 'use key on east door', 'use key on box'\n" +
					     "lets you unlock things. \n \n" + "Take - e.g 'take apple'\n" +
					     "lets you pick up things and store them in your inventory. \n\n" +
					     "Drop - e.g 'drop apple'\n" + "lets you drop things. \n" +
					     "Do not worry though! They will lay there on the ground, ready for pickup if you would regret it. \n\n" +
					     "Inventory, inv or i - easy way to see what you have in your pockets. \n\n" +
					     "Press - e.g 'press button'\n" + "lets you press a button. \n\n" +
					     "Cut - e.g 'cut wall with knife'\n" + "lets you cut something. \n\n" +
					     "Exit - lets you exit game, if you want to that is.\n\n" +
					     "Help - Access to this popup.\n\n" + "Good luck!");
	return "";
    }

    public String doCommand(String command) {
	String cmdStr = "";
	String firstWord = getFirstWord(command);
	switch (firstWord) {                //Even if the warning suggests enums instead, the code relies on input.
	    case "exit":
		cmdStr = "exit";
		break;
	    case "look":
		cmdStr = useCommandLook();
		break;
	    case "go":
		cmdStr = useCommandGo(command);
		break;
	    case "examine":
	    case "x":
		cmdStr = useCommandExamine(command);
		break;
	    case "open":
		cmdStr = useCommandOpen(command);
		break;
	    case "inventory":
	    case "i":
	    case "inv":
		cmdStr = openInventory();
		break;
	    case "get":
	    case "take":
		cmdStr = useCommandTake(command);
		break;
	    case "drop":
		cmdStr = useCommandDrop(command);
		break;
	    case "use":
		cmdStr = useCommandUse(command);
		break;
	    case "press":
		cmdStr = useCommandPress(command);
		break;
	    case "cut":
		cmdStr = useCommandCut(command);
		break;
	    case "help":
		cmdStr = useCommandHelp();
		break;
	    default:
		return "I don't understand. Maybe it is not spelled right?";
	}
	return cmdStr;
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public boolean isGameWon() {
	return gameWon;
    }
}
