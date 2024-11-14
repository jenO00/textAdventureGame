package project.text.adventure.gameitems;

import project.text.adventure.Direction;

/**
 * Creates an Exit between two rooms. The Exit can be locked which means that the player can't go through it without
 * unlocking an Obstacle first.
 */
public class Exit implements Lockable
{
    private Direction direction;
    private String toRoomReferenceName;

    public Exit(final Direction direction, final Room to) {
	this.direction = direction;
	this.toRoomReferenceName = to.getReferenceName();
    }

    public Direction getDirection() {
	return direction;
    }

    public String getToRoomReferenceName() {
	return toRoomReferenceName;
    }

    @Override public boolean isBox() {
	return false;
    }

    @Override public boolean hasExit(final Direction direction) {
	return this.direction.equals(direction);
    }
}
