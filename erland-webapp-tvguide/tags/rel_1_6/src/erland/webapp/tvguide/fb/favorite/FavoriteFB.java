package erland.webapp.tvguide.fb.favorite;

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

import erland.webapp.common.fb.BaseFB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class FavoriteFB extends BaseFB {
    private String idDisplay;
    private String channelDisplay;
    private String orderNoDisplay;

    public String getIdDisplay() {
        return idDisplay;
    }

    public void setIdDisplay(String idDisplay) {
        this.idDisplay = idDisplay;
    }

    public Integer getId() {
        return StringUtil.asInteger(idDisplay,null);
    }

    public void setId(Integer id) {
        this.idDisplay = StringUtil.asString(id,null);
    }

    public String getChannelDisplay() {
        return channelDisplay;
    }

    public void setChannelDisplay(String channelDisplay) {
        this.channelDisplay = channelDisplay;
    }

    public Integer getChannel() {
        return StringUtil.asInteger(channelDisplay,null);
    }

    public void setChannel(Integer channel) {
        this.channelDisplay = StringUtil.asString(channel,null);
    }

    public String getOrderNoDisplay() {
        return orderNoDisplay;
    }

    public void setOrderNoDisplay(String orderNoDisplay) {
        this.orderNoDisplay = orderNoDisplay;
    }

    public Integer getOrderNo() {
        return StringUtil.asInteger(orderNoDisplay,null);
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNoDisplay = StringUtil.asString(orderNo,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        idDisplay = null;
        channelDisplay = null;
        orderNoDisplay = null;
    }
}
