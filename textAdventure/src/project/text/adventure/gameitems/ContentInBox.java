package project.text.adventure.gameitems;

/**
 * Interface for objects that can be stored in other objects.
 */
public interface ContentInBox extends Item
{

    /**Depending on child it returns either true or false, so no abstract class should be used.*/
    public boolean canTake();

    public String getAfarDescription();
}
