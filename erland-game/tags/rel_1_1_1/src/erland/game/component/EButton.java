package erland.game.component;
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

import erland.game.component.swing.ESwingButton;
import erland.game.component.awt.EAWTButton;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents a Button object
 * @author Erland Isaksson
 */
public abstract class EButton extends EComponent {
    /**
     * Initializes a new instance
     * @param component The Component object for this button
     */
    protected EButton(Component component) {
        super(component);
    }
    /**
     * Creates a new EButton using the selected implementation
     * @param label The text of the button
     * @return The newly created EButton object
     */
    public static EButton create(String label) {
        if(EComponentMode.isApplet()) {
            return new EAWTButton(label);
        }else {
            return new ESwingButton(label);
        }
    }
    /**
     * Add an ActionListener to this button
     * @param actionListener The action listener to add to this button
     */
    public abstract void addActionListener(ActionListener actionListener);

    /**
     * Removes an ActionListener from this button
     * @param actionListener The action listener to remove from this button
     */
    public abstract void removeActionListener(ActionListener actionListener);
}
