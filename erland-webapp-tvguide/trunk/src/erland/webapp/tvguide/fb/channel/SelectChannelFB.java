package erland.webapp.tvguide.fb.channel;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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

import erland.util.StringUtil;
import erland.webapp.tvguide.fb.account.SelectUserFB;

public class SelectChannelFB extends SelectUserFB {
    private String channelDisplay;

    public Integer getChannel() {
        return StringUtil.asInteger(channelDisplay,null);
    }

    public void setChannel(Integer channel) {
        this.channelDisplay = StringUtil.asString(channel,null);
    }

    public String getChannelDisplay() {
        return channelDisplay;
    }

    public void setChannelDisplay(String channelDisplay) {
        this.channelDisplay = channelDisplay;
    }
}
