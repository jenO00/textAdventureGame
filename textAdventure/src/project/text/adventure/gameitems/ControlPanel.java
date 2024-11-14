package project.text.adventure.gameitems;

import java.util.List;

/**
 * Is an item which has Buttons on it and can unlock an Obstacle with a button. Returns feature string when a button is
 * pressed.
 */
public class ControlPanel extends AbstractItem
{
    private List<Button> buttons;
    private String locksReferenceName;

    public ControlPanel(final String description, final String afarDescription, final String referenceName,
			final List<Button> buttons, final String locksReferenceName)
    {
	super(description, afarDescription, referenceName);
	this.buttons = buttons;
	this.locksReferenceName = locksReferenceName;
    }

    public String pressButton(String buttonName, Room currentRoom) {
	Button button = this.findButton(buttonName);
	String pressStr = "";
	if (button != null) {
	    if (button.isCorrectButton() && locksReferenceName != null) {
		currentRoom.setDescription(
			"I don't care about this room anymore! <br/>" + "They asked me to go to the exit, so lets go! <br/>" +
			"It has to be the hatch.<br/>" + "It has to be.");
		this.setAfarDescription(null);
	    }
	    pressStr = button.printButtonFeature();
	}
	if (pressStr.isEmpty()) {
	    return "Can't find that button? Maybe I should press one of these three...";
	} else {
	    return pressStr;
	}
    }

    public Button findButton(String buttonName) {
	for (Button button : buttons) {
	    if (buttonName.equals(button.referenceName)) {
		return button;
	    }
	}
	return null;
    }

    @Override public AbstractItem findItem(final String itemName) {
	AbstractItem findThisItem = super.findItem(itemName);
	if (findThisItem != null) {
	    return findThisItem;
	} else if (buttons != null) {
	    return findButton(itemName);
	}
	return null;
    }

    public boolean hasButton(String buttonName) {
    	return findButton(buttonName) != null;
    }

    public String getLocksReferenceName() {
	return locksReferenceName;
    }
}
