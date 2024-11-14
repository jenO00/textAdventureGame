package project.text.adventure.gameitems;

import java.util.List;

/**
 * Is an Item with cords on it and can unlock an Obstacle when a cord is cut. Returns a feature string when a cord is cut.
 */
public class Robot extends AbstractItem
{
    private List<Wire> wires;
    private String lockedDoorReferenceName;

    public Robot(final String description, final String lockedDoorReferenceName,
		 final String afarDescription, final String referenceName, final List<Wire> wires)
    {
	super(description, afarDescription, referenceName);
	this.wires = wires;
	this.lockedDoorReferenceName = lockedDoorReferenceName;
    }

    public String cut(String wireName, Inventory inventory, Key key) {
        Wire wire = this.findWire(wireName);
	if (wire != null) {
	    if (wire.isCorrectWire() && lockedDoorReferenceName != null) {
		this.setAfarDescription("The robot is sizzling on the floor, some electricity shooting from its neck. \n" +
					"I think it is safe to step over now?");
		this.setDescription("It seems harmless enough... I really did a number on it.");
		inventory.removeKey(key);
	    }
	    return wire.cutWire();
	}
	return "Can't find that? Maybe I should cut something else...";
    }

    public Wire findWire(String wireName) {
	for (Wire wire : wires) {
	    if (wireName.equals(wire.referenceName)) {
		return wire;
	    }
	}
	return null;
    }

    @Override public AbstractItem findItem(final String itemName) {
    	AbstractItem findThisItem = super.findItem(itemName);
    	if (findThisItem != null) {
    	    return findThisItem;
    	}
    	else if (wires != null) {
    	    return findWire(itemName);
    	}
    	return null;
    }

    public boolean hasWire(String wireName) {
	return findWire(wireName) != null;
    }

    public String getLockedDoorReferenceName() {
	return lockedDoorReferenceName;
    }
}
