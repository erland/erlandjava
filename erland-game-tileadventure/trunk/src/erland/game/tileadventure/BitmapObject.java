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

public class BitmapObject extends GameObject {
    private int subImage;
    private Image image;
    private SubImageHandler subImages;
    private String imageName;
    public static int drawCount;
    /**
     * Set the background image
     * @param image The main image
     * @param subImage The sub image number of the sub image to use
     */
    public void setImage(Image image, int subImage) {
        this.subImage = subImage;
        this.image = image;
        this.subImages = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).createSubImageHandler(this.image);
    }

    /**
     * Set the background image
     * @param image The name of the main image
     * @param subImage The sub image number of the sub image to use
     */
    public void setImage(String image, int subImage)
    {
        if(getEnvironment().getImageHandler()!=null) {
            setImage(getEnvironment().getImageHandler().getImage(image),subImage);
        }
        this.imageName = image;
    }

    public void draw(Graphics g) {
        if(subImages!=null) {
            if(getContainer().getVisible(getPosX(),getPosY(),getPosZ())) {
                //System.out.println("Draw at "+getDrawingPosX()+","+getDrawingPosY()+"="+getPosX()+","+getPosY()+","+getPosZ());
                if(subImage>=0) {
                    drawCount++;
                    subImages.drawImage(g,subImage,getDrawingPosX(0f,0f,0f),getDrawingPosY(0f,0f,0f));
                }
            }
        }
    }

    public void write(ParameterValueStorageExInterface out) {
        super.write(out);
        if(StringUtil.asNull(imageName)!=null) {
            out.setParameter("image",imageName);
        }
        out.setParameter("subimage",Integer.toString(subImage));
    }

    public void read(ParameterValueStorageExInterface in) {
        super.read(in);
        int subImage = Integer.valueOf(in.getParameter("subimage")).intValue();
        setImage(image,subImage);
    }
}
