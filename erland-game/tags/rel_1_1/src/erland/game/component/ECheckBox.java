package erland.game.component;
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

import erland.game.component.swing.ESwingButton;
import erland.game.component.swing.ESwingCheckBox;
import erland.game.component.awt.EAWTButton;
import erland.game.component.awt.EAWTCheckBox;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents a Checkbox object
 * @author Erland Isaksson
 */
public abstract class ECheckBox extends EComponent {
    /**
     * Initializes a new instance
     * @param component The Component object for this button
     */
    protected ECheckBox(Component component) {
        super(component);
    }
    /**
     * Creates a new ECheckBox using the selected implementation
     * @param label The text of the button
     * @return The newly created EButton object
     */
    public static ECheckBox create(String label) {
        if(EComponentMode.isApplet()) {
            return new EAWTCheckBox(label);
        }else {
            return new ESwingCheckBox(label);
        }
    }
    /**
     * Get the selection state of this checkbox
     * @return The selection state of this checkbox
     */
    public abstract boolean getSelection();

    /**
     * Set the selection state of this checkbox
     * @param selected The selection state of this checkbox
     */
    public abstract void setSelection(boolean selected);
}
