package erland.game.pipes;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
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
import erland.game.*;
/**
 * Represents a block like following:
 * O?O
 * ?X?
 * O?O
 */
class PipeBlockPool extends PipeBlock
{
    /** Game environment object */
    GameEnvironmentInterface environment;
	/** Indicates which sides that are open */
	int openDirection;
	
	/**
	 * Creates a new pipe block
	 * @param environment Game environment object
	 * @param openDirection Indicates which part that is open
	 */
	public PipeBlockPool(GameEnvironmentInterface environment, int openDirection)
	{
		this.environment = environment;
		this.openDirection = openDirection;
	}
	public void init(BlockContainerInterface cont, int x, int y)
	{
		
		PipePart[][] parts = new PipePart[size][size];
		for (int i=0; i<size; i++) {
			parts[i] = new PipePart[size];
			for (int j=0; j<size; j++) {
				parts[i][j] = new PipePartNone(environment);
		    }
	    }
	    
	    if(openDirection==Direction.LEFT) {
			parts[0][1] = new PipePartLeftRight(environment);
		}
		if(openDirection==Direction.RIGHT) {
			parts[2][1] = new PipePartLeftRight(environment);
		}
	    if(openDirection==Direction.UP) {
			parts[1][0] = new PipePartUpDown(environment);
		}
	    if(openDirection==Direction.DOWN) {
			parts[1][2] = new PipePartUpDown(environment);
		}
		parts[1][1] = new PipePartPool(environment);
		
		super.init(cont,parts,x,y);
	}
	
	/**
	 * Check which direction the pool is open against
	 * @return The direction which the pool is open against
	 */
	public int getOpenDirection() {
		return openDirection;
	}

	protected int getScore(int x, int y)
	{
		if(x==1 && y==1) {
			return super.getScore(x,y)*((PipePartPool)parts[x][y]).getLevelsFilled();
		}else {
			return super.getScore(x,y);
		}
	}
}
