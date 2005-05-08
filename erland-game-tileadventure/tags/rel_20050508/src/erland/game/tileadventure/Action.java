package erland.game.tileadventure;
/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

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
