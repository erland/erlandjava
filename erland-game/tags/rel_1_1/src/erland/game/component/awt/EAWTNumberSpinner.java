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

import erland.game.component.ETextField;
import erland.game.component.ENumberSpinner;

import java.awt.*;
import java.awt.event.TextListener;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.ActionEvent;

/**
 * An AWT implementation for ENumberSpinner
 * @author Erland Isaksson
 */
public class EAWTNumberSpinner extends ENumberSpinner {
    /** The maximum value allowed in the spinner */
    private int max;
    /** The maximum value allowed in the spinner */
    private int min;

    /**
     * Creates a new instance
     * @param min The min value of the spinner
     * @param max The max value of the spinner
     * @param value The current value of the spinner
     * @param step The increase/decrease step of the spinner buttons
     */
    public EAWTNumberSpinner(int value, int min, int max, int step) {
        super(new TextField(""+value));
        ((TextField)getComponent()).setEditable(true);
        this.min = min;
        this.max = max;
    }

    public int getValue() {
        String text = ((TextField)getComponent()).getText();
        if(text!=null) {
            try {
                return Integer.valueOf(text).intValue();
            } catch (NumberFormatException e) {
                // Do nothing
            }
        }
        return 0;
    }

    public void setValue(int value) {
        if(value<=max && value>=min) {
            ((TextField)getComponent()).setText(""+value);
        }
    }

    public void setMax(int max) {
        if(max>=min) {
            this.max = max;
            if(getValue()>max) {
                setValue(min);
            }
        }
    }

    public void setMin(int min) {
        if(min<=max) {
            this.min = min;
            if(getValue()<min) {
                setValue(min);
            }
        }
    }

    public void addActionListener(ActionListener actionListener) {

        ((TextField)getComponent()).addTextListener(new SpinnerTextListener(actionListener));
    }
    private class SpinnerTextListener implements TextListener {
        private ActionListener actionListener;
        public SpinnerTextListener(ActionListener actionListener) {
            this.actionListener = actionListener;
        }

        public void textValueChanged(TextEvent e) {
            actionListener.actionPerformed(new ActionEvent(e.getSource(),e.getID(),null));
        }
    }
}