package project.text.adventure.gameitems;

import project.text.adventure.Direction;

import static java.lang.Character.toLowerCase;

/**
 * Is an Item which can contain a ContentInBox-object. Has to be open to be able to remove content and it is closed from start.
 * Has methods for opening it and getting its content. Keeps the name Box in spite of the warning, since changing it to
 * something else would confuse the reader. It is a box or a container, both which are existing classes in Java. Renaming it to
 * e.g 'Chest' would make it too specific.
 */
public class Box extends AbstractItem implements Lockable
{
    private ContentInBox content;
    private boolean open = false;
    private boolean winsGame;

    public Box(ContentInBox item, String referenceName, String afarDescription, String outsideDescription, final boolean winsGame) {
	super(outsideDescription, afarDescription, referenceName);
	content = item;
	this.winsGame = winsGame;
    }

    public String getInsideDescription() {
	if (content != null) {
	    return content.getAfarDescription();
	} else {
	    return "There is nothing to take.";
	}
    }

    public Key getKey(String keyName) {
	if (open) {
	    if (content != null && content.canTake()) {
		Key keyFound = content.getKey(keyName);
		if (content.equals(keyFound)) {
		    Key keyContent = (Key) content;
		    content = null;
		    return keyContent;
		}
	    }
	}
	return null;
    }

    public String open() {
	open = true;
	if (afarDescription != null) {
	    int indexItem = afarDescription.indexOf(getReferenceName()) - 1;
	    String addString = " open";
	    if (!hasVowel(getReferenceName().charAt(0))) {
		addString = "n" + addString;
	    }
	    setAfarDescription(afarDescription.substring(0, indexItem) + addString + afarDescription.substring(indexItem));
	}
	boolean isHatch = this.nameEquals("hatch");
	if (isHatch) {
	    return "I cautiously watch as the hatch slowly opens, ready to sprint if I have to. \n" +
		   "A few astronauts appear on the other side, each sporting a serious look. \n" +
		   "'<i>Citizen</i>', one of them says while extending a hand towards mine. \n" +
		   "'<i>I am glad you contacted us. Rowland is a foul, cowardly country.</i>' \n" +
		   "The corners of my mouth itch upwards as I firmly grab his hand. \n" +
		   "'<i>Now that I agree on</i>', I say as the others start to move inside, mentioning 'other survivors'. \n" +
		   "My gaze wanders to the spaceship on the other side and I can feel my legs itching. \n" +
		   "I can finally leave.";
	}
	return getInsideDescription();
    }

    @Override public AbstractItem findItem(final String itemName) {
	AbstractItem findThisItem = super.findItem(itemName);
	if (findThisItem != null) {
	    return findThisItem;
	} else if (content != null) {
	    return content.findItem(itemName);
	}
	return null;
    }

    @Override public boolean getWinsGame() {
	return winsGame;
    }

    public boolean hasVowel(char c) {
	c = toLowerCase(c);
	return c == 'a' || c == 'o' || c == 'u' || c == 'e' || c == 'i' || c == 'y';
    }

    @Override public boolean isBox() {
	return true;
    }

    @Override public boolean hasExit(final Direction direction) {
	return false;
    }
}
