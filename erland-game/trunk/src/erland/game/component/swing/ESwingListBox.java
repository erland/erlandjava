package erland.game.component.swing;
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

import erland.game.component.EButton;
import erland.game.component.ECheckBox;
import erland.game.component.EListBox;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * A Swing implementation for a EListbox
 * @author Erland Isaksson
 */
public class ESwingListBox extends EListBox {
    /**
     * Creates a new instance
     * @param values The values that should be shown in the listbox
     */
    public ESwingListBox(String[] values) {
        super(new JComboBox(values));
        ((JComboBox)getComponent()).setEditable(false);
        ((JComboBox)getComponent()).
    }

    public String getSelection() {
        Object item = ((JComboBox)getComponent()).getSelectedItem();
        if(item!=null) {
            return item.toString();
        }else {
            return null;
        }
    }

    public void setSelection(String selectedItem) {
        ((JComboBox)getComponent()).setSelectedItem(selectedItem);
    }
}
