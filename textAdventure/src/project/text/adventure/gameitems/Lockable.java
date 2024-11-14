package project.text.adventure.gameitems;

import project.text.adventure.Direction;

/**
 * Interface for objects that can be locked and therefore unlocked.
 */
public interface Lockable
{

    /**
    * A box can be opened whereas an exit cannot. The different values made us choose not to create
    * a new abstract class.
     */
    public boolean isBox();

    public boolean hasExit(Direction direction);
}
