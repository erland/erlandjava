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

import erland.game.component.awt.EAWTPanel;
import erland.game.component.swing.ESwingPanel;

import java.awt.*;

/**
 * Represents a Panel object
 * @author Erland Isaksson
 */
public class EPanel extends EContainer {
    /**
     * Initiates a new instance
     * @param container The container of the new instance
     */
    protected EPanel(Container container) {
        super(container);
    }
    /**
     * Creates a new EPanel using the selected implementation
     * @return The newly created EPanel object
     */
    public static EPanel create() {
        return EPanel.create(null);
    }
    /**
     * Creates a new EPanel using the specified layout manager with
     * the selected implementation
     * @param layoutManager The layout manager to use
     * @return The newly created EPanel object
     */
    public static EPanel create(LayoutManager layoutManager) {
        if(EComponentMode.isApplet()) {
            return new EAWTPanel(layoutManager);
        }else {
            return new ESwingPanel(layoutManager);
        }
    }
}
