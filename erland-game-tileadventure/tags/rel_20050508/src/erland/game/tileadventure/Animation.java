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

public class Animation implements AnimationInterface, Cloneable {
    private int[] imageList;
    private int current;
    private int speed;
    private int animationCounter;
    public Animation(int speed, int[] imageList) {
        this.speed= speed;
        this.imageList = imageList;
        this.current = 0;
        this.animationCounter = 0;
    }
    public int getNextImage() {
        animationCounter++;
        if(speed>0 && animationCounter>=speed) {
            animationCounter=0;
            current++;
            if(current>=imageList.length) {
                current = 0;
            }
        }
        return imageList[current];
    }

    public Object clone() {
        Animation a = null;
        try {
            a = (Animation) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return a;
    }
}