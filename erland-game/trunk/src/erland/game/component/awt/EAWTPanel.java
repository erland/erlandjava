package erland.game.component.awt;
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

import erland.game.component.EPanel;

import java.awt.*;

/**
 * An AWT implementation for the EPanel
 * @author Erland Isaksson
 */
public class EAWTPanel extends EPanel {
    /**
     * Creates a new instance
     */
    public EAWTPanel() {
        super(new Container());
    }
    /**
     * Creates a new instance
     * @param layoutManager The layout manager to use
     */
    public EAWTPanel(LayoutManager layoutManager) {
        super(new Container());
        getContainer().setLayout(layoutManager);
    }
}
