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
import erland.game.component.ENumberSpinner;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * A Swing implementation for a ENumberSpinner
 * @author Erland Isaksson
 */
public class ESwingNumberSpinner extends ENumberSpinner {
    /** The spinner model */
    private SpinnerNumberModel model;
    /**
     * Creates a new instance
     * @param min The min value of the spinner
     * @param max The max value of the spinner
     * @param value The current value of the spinner
     * @param step The increase/decrease step of the spinner buttons
     */
    public ESwingNumberSpinner(int value,  int min, int max, int step) {
        super(new JSpinner(new SpinnerNumberModel(value,min,max,step)));
        model = (SpinnerNumberModel) ((JSpinner)getComponent()).getModel();
        (((JSpinner.DefaultEditor)((JSpinner)getComponent()).getEditor()).getTextField()).setEditable(false);
    }

    public int getValue() {
        return model.getNumber()!=null?model.getNumber().intValue():0;
    }

    public void setValue(int value) {
        model.setValue(new Integer(value));
    }

    public void setMax(int max) {
        model.setMaximum(new Integer(max));
    }

    public void setMin(int min) {
        model.setMinimum(new Integer(min));
    }

    public void addActionListener(ActionListener actionListener) {
        ((JSpinner)getComponent()).addChangeListener(new SpinnerChangeListener(actionListener));
    }
    private class SpinnerChangeListener implements ChangeListener {
        private ActionListener actionListener;
        public SpinnerChangeListener(ActionListener actionListener) {
            this.actionListener = actionListener;
        }

        public void stateChanged(ChangeEvent e) {
            actionListener.actionPerformed(new ActionEvent(e.getSource(),1,null));
        }
    }
}
