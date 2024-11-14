package project.text.adventure.gameitems;

/**
 * Abstract class for all kinds of Items with necessary information they all need as well as methods for getting useful
 * characteristics and which type of Item it is.
 */
public abstract class AbstractItem implements Item
{
    protected String description;
    protected String afarDescription;
    protected String referenceName;

    protected AbstractItem(final String description, final String afarDescription, final String referenceName) {
	this.description = description;
	this.afarDescription = afarDescription;
	this.referenceName = referenceName;
    }

    public boolean nameEquals(String str){
        return referenceName.equals(str);
    }

    public AbstractItem findItem(String itemName){
	if (itemName.equals(referenceName)) {
	    return this;
	}
	return null;
    }

    public Key getKey(String keyName){
	if (keyName.equals(referenceName) && this.isKey()) {
	    return (Key) this;
	}
	return null;
    }

    public String getDescription() {
	return description;
    }

    public String getAfarDescription() {
	return afarDescription;
    }

    public String getReferenceName() {
	return referenceName;
    }

    public void setAfarDescription(final String afarDescription) {
	this.afarDescription = afarDescription;
    }

    public void setDescription(final String description) {
	this.description = description;
    }

    public String cantTakeString() {
        return "My pocket is too small for this!";
    }

    public String pressButton(String buttonName, Room currentRoom) {
        return "";
    }

    public String open() {
        return "Can't open this.";
    }

    public String cut(String wireName, Inventory inventory, Key key) {
	return "";
    }

    public boolean getWinsGame() {
        return false;
    }

    public boolean givesGameOver() {
        return false;
    }

    public boolean canUnlock() {
	return false;
    }

    public boolean isKey() {
	return false;
    }

    public boolean isCorrectWire() {
        return false;
    }

    public boolean isCorrectButton() {
        return false;
    }

    public boolean hasWire(String wireName) {
        return false;
    }

    public boolean hasButton(String buttonName) {
        return false;
    }
}
