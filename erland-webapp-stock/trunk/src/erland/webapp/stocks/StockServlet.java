package erland.webapp.stocks;
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

import erland.webapp.usermgmt.UserMgmtServlet;
import erland.webapp.common.WebAppEnvironmentInterface;

import erland.util.*;

public class StockServlet extends UserMgmtServlet {
    private StockServletEnvironment environment;
    protected WebAppEnvironmentInterface getEnvironment() {
        if(environment==null) {
            environment = new StockServletEnvironment(this);
        }
        return environment;
    }
    public void initEnd() {
        Log.setLogConfig(new ParameterStorageString(new StringStorage(
                "<log>"+
                "<logitem1>erland.webapp.usermgmt.UserMgmtServlet</logitem1>"+
                "<logitem2>erland.webapp.stocks.StockServlet</logitem2>"+
                "<logitem3>erland.webapp.stocks.sb.SBXMLEncoderDisabled</logitem3>"+
                "<logitem4>erland.webapp.stocks.sb.SBXMLParserDisabled</logitem4>"+
                "<logitem5>erland.webapp.stocks.StockDisabled</logitem5>"+
                "<logitem6>erland.webapp.stocks.StockAccountDisabled</logitem6>"+
                "<logitem7>erland.webapp.stocks.StockAccountCommandDisabled</logitem7>"+
                "<logitem8>erland.webapp.common.BaseServletDisabled</logitem8>"+
                "<logitem9>erland.webapp.diagram.DateValueDiagramDisabled</logitem9>"+
                "<logitem10>erland.webapp.common.EntityStorageDisabled</logitem10>"+
                "</log>"),null,"log"));

    }
}
