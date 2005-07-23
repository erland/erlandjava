package erland.webapp.issuetracking.fb.issue;

import erland.webapp.common.ServletParameterHelper;
import erland.webapp.issuetracking.fb.application.SelectApplicationFB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

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

public class SearchIssueFB extends SelectApplicationFB {
    private Integer[] states;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDisplay() {
        return StringUtil.asString(type,null);
    }

    public void setTypeDisplay(String typeDisplay) {
        this.type = StringUtil.asInteger(typeDisplay,null);
    }

    public Integer[] getStates() {
        return states;
    }

    public void setStates(Integer[] states) {
        this.states = states;
    }

    public String[] getStatesDisplay() {
        if(states!=null) {
            String[] result = new String[states.length];
            for (int i = 0; i < states.length; i++) {
                result[i] = StringUtil.asString(states[i],null);
            }
            return result;
        }else {
            return null;
        }
    }

    public void setStatesDisplay(String[] statesDisplay) {
        if(statesDisplay!=null) {
            this.states = new Integer[statesDisplay.length];
            for (int i = 0; i < statesDisplay.length; i++) {
                this.states[i] = StringUtil.asInteger(statesDisplay[i],null);
            }
        }else {
            this.states = null;
        }
    }


    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        states = null;
        type = null;
    }
}