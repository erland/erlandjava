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

import erland.util.SubImageHandler;
import erland.util.ParameterValueStorageExInterface;
import erland.util.StringUtil;

import java.awt.*;

public class AnimatedObject extends GameObject implements GameObjectUpdateInterface {
    private Image image;
    private SubImageHandler subImages;
    private String imageName;
    private AnimationInterface animation;
    private int currentSubImage=-1;

    /**
     * Set the animation
     * @param image The main image
     * @param animation The animation object containing the subimages to animate between
     */
    public void setAnimation(Image image, AnimationInterface animation) {
        this.animation = animation;
        this.image = image;
        this.subImages = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).createSubImageHandler(this.image);
    }

    /**
     * Set the animation
     * @param image The name of the main image
     * @param animation The animation object containing the subimages to animate between
     */
    public void setAnimation(String image, AnimationInterface animation)
    {
        if(getEnvironment().getImageHandler()!=null) {
            setAnimation(getEnvironment().getImageHandler().getImage(image),animation);
        }
        this.imageName = image;
    }

    public boolean update() {
        if(animation!=null) {
            int image = animation.getNextImage();
            if(image!=currentSubImage) {
                currentSubImage = image;
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        if(subImages!=null) {
            if(getContainer().getVisible(getPosX(),getPosY(),getPosZ())) {
                if(currentSubImage>=0) {
                    subImages.drawImage(g,currentSubImage,getDrawingPosX(0f,0f,0f),getDrawingPosY(0f,0f,0f));
                }
            }
        }
    }

    public void write(ParameterValueStorageExInterface out) {
        // Not supported
    }

    public void read(ParameterValueStorageExInterface in) {
        // Not supported
    }

    public Object clone() {
        AnimatedObject o = (AnimatedObject) super.clone();
        o.animation = (AnimationInterface) animation.clone();
        return o;
    }
}
