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
import erland.game.component.swing.ESwingNumberSpinner;
import erland.game.component.awt.EAWTButton;
import erland.game.component.awt.EAWTCheckBox;
import erland.game.component.awt.EAWTNumberSpinner;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.TextListener;

/**
 * Represents a number Spinner object
 * @author Erland Isaksson
 */
public abstract class ENumberSpinner extends EComponent {
    /**
     * Initializes a new instance
     * @param component The Component object for this spinner
     */
    protected ENumberSpinner(Component component) {
        super(component);
    }
    /**
     * Creates a new ENumberSpinner using the selected implementation
     * @param min The min value of the spinner
     * @param max The max value of the spinner
     * @param value The current value of the spinner
     * @param step The increase/decrease step of the spinner buttons
     * @return The newly created ENumberSpinner object
     */
    public static ENumberSpinner create(int value, int min, int max, int step) {
        if(EComponentMode.isApplet()) {
            return new EAWTNumberSpinner(value,min,max,step);
        }else {
            return new ESwingNumberSpinner(value,min,max,step);
        }
    }
    /**
     * Get the current value of the spinner
     * @return The value of this spinner
     */
    public abstract int getValue();

    /**
     * Set the current value of the spinner
     * @param value The value of the spinner
     */
    public abstract void setValue(int value);

    /**
     * Get the current max value of the spinner
     * @param max The max value of this spinner
     */
    public abstract void setMax(int max);

    /**
     * Set the current min value of the spinner
     * @param min The min value of the spinner
     */
    public abstract void setMin(int min);

    /**
     * Adds a listener which will be called when value in the
     * spinner is changed
     * @param actionListener The listener object that should be called
     */
    public abstract void addActionListener(ActionListener actionListener);
}
