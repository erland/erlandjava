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
 * OxO
 * XXX
 * OxO
 */
class PipeBlockCross extends PipeBlock
{
	/** Game environment object */
	GameEnvironmentInterface environment;
	
	/**
	 * Creates a new pipe block
	 * @param environment Game environment object
	 */
	public PipeBlockCross(GameEnvironmentInterface environment)
	{
		this.environment = environment;
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
	    
		parts[0][1] = new PipePartLeftRight(environment);
		parts[1][2] = new PipePartUpDown(environment);
		parts[2][1] = new PipePartLeftRight(environment);
		parts[1][0] = new PipePartUpDown(environment);
		parts[1][1] = new PipePartCross(environment);
		
		super.init(cont,parts,x,y);
	}
}
