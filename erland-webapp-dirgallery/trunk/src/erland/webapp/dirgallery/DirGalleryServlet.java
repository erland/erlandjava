package erland.webapp.dirgallery;
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

import erland.util.Log;
import erland.util.ParameterStorageString;
import erland.util.StringStorage;
import erland.webapp.usermgmt.UserMgmtServlet;
import erland.webapp.common.html.HTMLEncoder;
import erland.webapp.common.html.HTMLBasicStringReplace;
import erland.webapp.common.html.HTMLLinkStringReplace;


public class DirGalleryServlet extends UserMgmtServlet {
    public void initEnd() {
        Log.setLogConfig(new ParameterStorageString(new StringStorage(
                "<log>" +
                "<timestamp>true</timestamp>" +
                "<timestampformat>HH:mm:ss.SSS</timestampformat>" +
                "<classname>true</classname>" +
                "<logitem1>erland.webapp.usermgmt.UserMgmtServletDisabled</logitem1>" +
                "<logitem2>erland.webapp.usermgmt.LoginCommand</logitem2>" +
                "<logitem3>erland.webapp.usermgmt.UserDisabled</logitem3>" +
                "<logitem4>erland.webapp.dirgallery.DirGalleryServlet</logitem4>" +
                "<logitem5>erland.webapp.usermgmt.UserMgmtServletDisabled</logitem5>" +
                "<logitem6>erland.webapp.common.GenericEntityStorageDisabled</logitem6>" +
                "<logitem7>erland.webapp.dirgallery.HTMLBasicStringReplaceDisabled</logitem7>" +
                "<logitem8>erland.webapp.dirgallery.HTMLLinkStringReplaceDisabled</logitem8>" +
                "<logitem9>erland.webapp.dirgallery.loader.LoadThumbnailCommandDisabled</logitem9>" +
                "<logitem10>erland.webapp.common.FileEntityStorageDisabled</logitem10>" +
                "</log>"), null, "log"));
        HTMLEncoder.addReplaceRoutine(new HTMLBasicStringReplace());
        HTMLEncoder.addReplaceRoutine(new HTMLLinkStringReplace());
        DescriptionTagHelper.getInstance().init(getEnvironment());
    }
}
