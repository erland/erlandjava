package erland.game.racer;
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

import erland.util.ParameterValueStorageExInterface;
import java.awt.*;

/**
 * A block that puts an overlay text on other blocks
 * @author Erland Isaksson
 */
public class BlockText extends Block {
    /** Overlay text to show on top of the block */
    private String text;
    /** Block to draw text on */
    private Block backgroundBlock;
    /** Color of the text */
    private Color color = Color.white;

    /**
     * Set the block to draw on
     * @param block The block to draw on
     */
    public void setBackgroundBlock(Block block)
    {
        backgroundBlock = block;
    }
    /**
     * Get the Block object which the text is drawn on
     * @return The block object
     */
    public Block getBackgroundBlock()
    {
        return backgroundBlock;
    }
    /**
     * Set the text to draw on top of the block
     * @param text The text
     */
    public void setText(String text)
    {
        this.text= text;
    }
    /**
     * Get the text which is drawn on top of the block
     * @return The text
     */
    public String getText()
    {
        return text;
    }
    /**
     * Set the color to draw the text with
     * @param c The text color
     */
    public void setColor(Color c)
    {
        this.color = c;
    }

    public void draw(Graphics g,int level)
    {
        if(backgroundBlock!=null) {
            backgroundBlock.draw(g,level);
        }
        Color old = g.getColor();
        g.setColor(color);
        g.drawString(text,getContainer().getDrawingPositionX(getPosX())+10,getContainer().getDrawingPositionY(getPosY())+getContainer().getSquareSize()-10);
        g.setColor(old);
    }
    public void write(ParameterValueStorageExInterface out) {
        super.write(out);
        out.setParameter("text",text);
    }

    public void read(ParameterValueStorageExInterface in) {
        super.read(in);
        text = in.getParameter("text");
        backgroundBlock = null;
    }
}
