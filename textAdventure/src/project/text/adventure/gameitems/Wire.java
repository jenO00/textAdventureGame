package project.text.adventure.gameitems;

/**
 * Is an Item which can give game over, and returns a string (feature) when cut.
 */
public class Wire extends AbstractItem
{
    private String feature;
    private boolean correctWire;
    private boolean givesGameOver;

    public Wire(final String description, final boolean correctWire, final String afarDescription, final String referenceName,
		final String feature, final boolean givesGameOver)
    {
	super(description, afarDescription, referenceName);
	this.feature = feature;
	this.correctWire = correctWire;
	this.givesGameOver = givesGameOver;
    }

    public boolean isCorrectWire() {
	return correctWire;
    }

    public boolean givesGameOver() {
	return givesGameOver;
    }

    public String cutWire() {
	return feature;
    }
}
