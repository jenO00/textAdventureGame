package project.text.adventure.gameitems;
/**
 * Interface for all Items. They need to be able to compare itself to a String and return an AbstractItem if they match.
 */
public interface Item
{
    public AbstractItem findItem(String itemName);

    public Key getKey(String keyName);
}
