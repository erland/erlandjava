package erland.game.tileadventure;

import erland.util.ParameterSerializable;
import erland.util.ParameterValueStorageExInterface;
import erland.util.StringUtil;

import java.util.StringTokenizer;

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

public class AnimationExtendedLevelInfo implements ParameterSerializable {
    private int speed = 1;
    private int[] images;

    public void write(ParameterValueStorageExInterface out) {
        out.setParameter("speed",""+speed);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < images.length; i++) {
            sb.append(images[i]);
            if(i<images.length-1) {
                sb.append(' ');
            }
        }
        out.setParameter("images",sb.toString());
    }

    public void read(ParameterValueStorageExInterface in) {
        speed = StringUtil.asInteger(in.getParameter("speed"),new Integer(1)).intValue();
        StringTokenizer tokens = new StringTokenizer(StringUtil.asEmpty(in.getParameter("images")));
        images = new int[tokens.countTokens()];
        int i=0;
        while (tokens.hasMoreTokens()) {
            String s = (String) tokens.nextToken();
            images[i++] = StringUtil.asInteger(s,new Integer(0)).intValue();
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int[] getImages() {
        return images;
    }

    public void setImages(int[] images) {
        this.images = images;
    }
}