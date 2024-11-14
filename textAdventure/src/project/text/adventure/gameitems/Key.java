package project.text.adventure.gameitems;

/**
 * Is an Item and a ContentInBox that can be picked up.
 */
public class Key extends AbstractItem implements ContentInBox
{
    public Key(final String description, final String afarDescription, final String referenceName) {
	super(description, afarDescription, referenceName);
    }

    @Override public boolean canTake() {
	return true;
    }

    @Override public boolean isKey() {
	return true;
    }
}
