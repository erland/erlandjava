package erland.webapp.diary;
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
import erland.webapp.common.CommandInterface;
import erland.webapp.common.html.HTMLBasicStringReplace;
import erland.webapp.common.html.HTMLEncoder;
import erland.webapp.common.html.HTMLLinkStringReplace;
import erland.webapp.diary.appendix.AppendixStringReplace;
import erland.util.Log;
import erland.util.ParameterStorageString;
import erland.util.StringStorage;

import javax.servlet.http.HttpServletRequest;

public class DiaryServlet extends UserMgmtServlet {
    public void initEnd() {
        Log.setLogConfig(new ParameterStorageString(new StringStorage(
                "<log>"+
                "<logitem1>erland.webapp.usermgmt.UserMgmtServlet</logitem1>"+
                "<logitem2>erland.webapp.usermgmt.LoginCommand</logitem2>"+
                "<logitem3>erland.webapp.usermgmt.User</logitem3>"+
                "<logitem4>erland.webapp.diary.DiaryEntryStorageDisabled</logitem4>"+
                "<logitem5>erland.webapp.diary.DiaryServlet</logitem5>"+
                "<logitem6>erland.webapp.usermgmt.UserMgmtServletDisabled</logitem6>"+
                "<logitem7>erland.webapp.common.GenericEntityStorageDisabled</logitem7>"+
                "<logitem8>erland.webapp.diary.HTMLBasicStringReplaceDisabled</logitem8>"+
                "<logitem9>erland.webapp.diary.HTMLLinkStringReplaceDisabled</logitem9>"+
                "<logitem10>erland.webapp.diary.inventory.EditInventoryEntryEventCommandDisabled</logitem10>"+
                "</log>"),null,"log"));
        System.out.println(getEnvironment().getResources().getParameter("pages.default"));
        HTMLEncoder.addReplaceRoutine(new HTMLBasicStringReplace());
        HTMLEncoder.addReplaceRoutine(new AppendixStringReplace(getEnvironment()));
        HTMLEncoder.addReplaceRoutine(new HTMLLinkStringReplace());
        DescriptionIdHelper.getInstance().init(getEnvironment());
    }
}
