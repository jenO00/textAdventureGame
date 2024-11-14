package project.text.adventure.gameitems;

/**
 * An Item which locks a Lockable. When unlocked this object is removed from the room and the Lockable is added instead
 * and a key can be required to unlock it.
 */
public class Obstacle extends AbstractItem
{
    private String keyReferenceName;
    private String unlockedDescription;
    private Lockable lockable;
    private boolean givesGameOver;

    public Obstacle(final String description, final String afarDescription, final String referenceName,
		    final String unlockedDescription, final String keyReferenceName, final Lockable lockable, final boolean givesGameOver)
    {
	super(description, afarDescription, referenceName);
	this.unlockedDescription = unlockedDescription;
	this.keyReferenceName = keyReferenceName;
	this.lockable = lockable;
	this.givesGameOver = givesGameOver;
    }

    public String unlockWithKey(Key useKey, Room currentRoom, Inventory inventory) {
	/** Used to match a key with its reference name (id), not for type checking. */
	if (useKey.getReferenceName().equals(keyReferenceName)) {
	    inventory.removeKeyByID(keyReferenceName);
	    return unlock(currentRoom);
	} else {
	    return "It didn't work.";
	}
    }

    public String unlock(Room currentRoom) {
	if (lockable == null) {
	    description = unlockedDescription;
	} else {
	    currentRoom.removeItem(this);
	    currentRoom.addLockable(lockable);
	}
	return "It worked!";
    }

    public String open() {
	boolean isHatch = this.nameEquals("hatch");
	if (isHatch) {
	    return "As soon as the hatch opens, I find myself staring into space. \n" +
		   "One second. One second is all it takes before the floor disappears beneath my feet and I'm flung into space.";
	}
	return "Can't open this.";
    }

    @Override public boolean canUnlock() {
	return true;
    }

    @Override public boolean givesGameOver() {
	return givesGameOver;
    }

    public Lockable getLockable() {
	return lockable;
    }
}
