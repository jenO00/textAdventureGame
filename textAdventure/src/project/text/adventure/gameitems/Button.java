package project.text.adventure.gameitems;

/**
 * A type of Item which can give game over, and a string (feature) is returned when pressed.
 */
public class Button extends AbstractItem
{
    private String feature;
    private boolean givesGameOver;
    private boolean correctButton;

    public Button(final String description, final String afarDescription, final String referenceName, final String feature,
		  final boolean givesGameOver, final boolean correctButton)
    {
	super(description, afarDescription, referenceName);
	this.feature = feature;
	this.givesGameOver = givesGameOver;
	this.correctButton = correctButton;
    }

    public String printButtonFeature() {
	return feature;
    }

    public boolean isCorrectButton() {
	return correctButton;
    }

    public boolean givesGameOver() {
	return givesGameOver;
    }
}
