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

import java.awt.*;

/**
 * Represents a Component object
 * @author Erland Isaksson
 */
public class EComponent {
    /** The Component object of this component */
    protected Component me;
    /**
     * Initializes a new instance
     * @param component The Component object for this component
     */
    protected EComponent(Component component) {
        me = component;
    }
    /**
     * Get the Component object for this component
     * @return The Component object for this component
     */
    public Component getComponent() {
        return this.me;
    }
}
