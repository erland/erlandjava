package erland.webapp.tvguide.fb.subscription;

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

import java.util.Date;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class SelectSubscriptionFB extends SelectUserFB {
    private String subscriptionDisplay;

    public Integer getSubscription() {
        return StringUtil.asInteger(subscriptionDisplay,null);
    }

    public void setSubscription(Integer subscription) {
        this.subscriptionDisplay = StringUtil.asString(subscription,null);
    }

    public String getSubscriptionDisplay() {
        return subscriptionDisplay;
    }

    public void setSubscriptionDisplay(String subscriptionDisplay) {
        this.subscriptionDisplay = subscriptionDisplay;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        subscriptionDisplay = null;
    }
}
