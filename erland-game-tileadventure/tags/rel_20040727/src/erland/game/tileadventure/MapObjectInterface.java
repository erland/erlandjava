package erland.game.tileadventure;

import erland.game.GameEnvironmentInterface;

import java.awt.*;

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

public interface MapObjectInterface {
    void init(GameEnvironmentInterface environment);

    void setContainer(IrregularBlockContainerInterface cont);

    IrregularBlockContainerInterface getContainer();

    int getPosX();

    int getPosY();

    int getPosZ();

    void setPos(int x, int y, int z);

    void draw(Graphics g);

    Object clone();
}