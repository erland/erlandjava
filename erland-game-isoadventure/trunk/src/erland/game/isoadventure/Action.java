package erland.game.isoadventure;

public class Action {

    private Action() {}
    public final static Action NONE = new Action();
    public final static Action MOVE_WEST = new Action();
    public final static Action MOVE_EAST = new Action();
    public final static Action MOVE_NORTH = new Action();
    public final static Action MOVE_SOUTH = new Action();
    public final static Action MOVE_DOWN = new Action();

    public final static Action PUSH_WEST = new Action();
    public final static Action PUSH_EAST = new Action();
    public final static Action PUSH_NORTH = new Action();
    public final static Action PUSH_SOUTH = new Action();

    public final static Action JUMP = new Action();

    public final static Action DROP = new Action();

    public boolean isMove() {
        return this==MOVE_WEST || this==MOVE_EAST || this==MOVE_NORTH || this==MOVE_SOUTH || this==MOVE_DOWN;
    }
    public boolean isPush() {
        return this==PUSH_WEST || this==PUSH_EAST || this==PUSH_NORTH || this==PUSH_SOUTH;
    }
    public Direction getDirection() {
        if(this==MOVE_WEST||this==PUSH_WEST) {
            return Direction.WEST;
        }else if(this==MOVE_EAST||this==PUSH_EAST) {
            return Direction.EAST;
        }else if(this==MOVE_NORTH||this==PUSH_NORTH) {
            return Direction.NORTH;
        }else if(this==MOVE_SOUTH||this==PUSH_SOUTH) {
            return Direction.SOUTH;
        }else if(this==MOVE_DOWN) {
            return Direction.DOWN;
        }else {
            return null;
        }
    }
}
