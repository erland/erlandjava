package erland.game.tileadventure;

import erland.util.ParameterSerializable;
import erland.util.ParameterValueStorageExInterface;
import erland.util.StringUtil;

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

public class MapObjectExtendedLevelInfo implements ParameterSerializable {
    private Boolean walkable = Boolean.FALSE;
    private Boolean pushable = Boolean.FALSE;

    public void write(ParameterValueStorageExInterface out) {
        out.setParameter("walkable",walkable.toString());
        out.setParameter("pushable",pushable.toString());
    }

    public void read(ParameterValueStorageExInterface in) {
        walkable = StringUtil.asBoolean(in.getParameter("walkable"),Boolean.FALSE);
        pushable = StringUtil.asBoolean(in.getParameter("pushable"),Boolean.FALSE);
    }

    public void setWalkable(Boolean walkable) {
        this.walkable = walkable;
    }

    public void setPushable(Boolean pushable) {
        this.pushable = pushable;
    }

    public Boolean getWalkable() {
        return walkable;
    }

    public Boolean getPushable() {
        return pushable;
    }
}