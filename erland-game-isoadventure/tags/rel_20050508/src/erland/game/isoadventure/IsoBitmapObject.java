package erland.game.isoadventure;
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

import erland.util.SubImageHandler;
import erland.util.ParameterValueStorageExInterface;

import java.awt.*;

public class IsoBitmapObject extends IsoObject {
    private int subImageTop;
    private int subImageBottom;
    private Image image;
    private SubImageHandler subImages;
    private String imageName;

    /**
     * Set the background image
     * @param image The main image
     * @param subImageTop The sub image number of the sub image to use as top
     * @param subImageBottom The sub image number of the sub image to use as bottom
     */
    public void setImage(Image image, int subImageTop, int subImageBottom) {
        this.subImageTop = subImageTop;
        this.subImageBottom = subImageBottom;
        this.image = image;
        this.subImages = new SubImageHandler(this.image,getContainer().getSquareSizeX(),getContainer().getSquareSizeY(),8,8);
    }

    /**
     * Set the background image
     * @param image The name of the main image
     * @param subImageTop The sub image number of the sub image to use for the top
     * @param subImageBottom The sub image number of the sub image to use for the bottom
     */
    public void setImage(String image, int subImageTop, int subImageBottom)
    {
        if(getEnvironment().getImageHandler()!=null) {
            setImage(getEnvironment().getImageHandler().getImage(image),subImageTop,subImageBottom);
        }
        this.imageName = image;
    }

    public void draw(Graphics g) {
        if(subImages!=null) {
            if(getContainer().getVisible(getPosX(),getPosY(),getPosZ())) {
                //System.out.println("Draw at "+getDrawingPosX()+","+getDrawingPosY()+"="+getPosX()+","+getPosY()+","+getPosZ());
                if(subImageBottom>=0) {
                    subImages.drawImage(g,subImageBottom,getDrawingPosX(0f,0f,-1f),getDrawingPosY(0f,0f,-1f));
                }
                if(subImageTop>=0) {
                    subImages.drawImage(g,subImageTop,getDrawingPosX(0f,0f,0f),getDrawingPosY(0f,0f,0f));
                }
            }
        }
    }

    public void write(ParameterValueStorageExInterface out) {
        super.write(out);
        out.setParameter("image",imageName);
        out.setParameter("subimagetop",Integer.toString(subImageTop));
        out.setParameter("subimagebottom",Integer.toString(subImageBottom));
    }

    public void read(ParameterValueStorageExInterface in) {
        super.read(in);
        int subImageTop = Integer.valueOf(in.getParameter("subimagetop")).intValue();
        int subImageBottom = Integer.valueOf(in.getParameter("subimagebottom")).intValue();
        setImage(image,subImageTop,subImageBottom);
    }
}
