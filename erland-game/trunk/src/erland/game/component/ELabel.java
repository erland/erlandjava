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

import erland.game.component.awt.EAWTLabel;
import erland.game.component.swing.ESwingLabel;

import java.awt.*;
/**
 * Represents a Label object
 * @author Erland Isaksson
 */
public class ELabel extends EComponent {
    /**
     * Initiates a new instance
     * @param component The component of the new instance
     */
    protected ELabel(Component component) {
        super(component);
    }
    /**
     * Creates a new ELabel using the selected implementation
     * @param label The text of the label object
     * @return The newly created ELabel object
     */
    public static ELabel create(String label) {
        if(EComponentMode.isApplet()) {
            return new EAWTLabel(label);
        }else {
            return new ESwingLabel(label);
        }
    }
}
