package project.text.adventure.gameitems;

/**
 * Creates an article with necessary information. Is an Item and a ContentInBox which the player can't pick it up.
 */
public class Article extends AbstractItem implements ContentInBox
{
    public Article(final String description, final String afarDescription, final String referenceName) {
	super(description, afarDescription, referenceName);
    }

    @Override public boolean canTake() {
	return false;
    }

    public String cantTakeString() {
	return "I don't think this is worth taking.";
    }
}
