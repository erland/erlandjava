package erland.webapp.gallery;
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
import erland.webapp.common.html.HTMLBasicStringReplace;
import erland.webapp.common.html.HTMLEncoder;
import erland.webapp.common.html.HTMLLinkStringReplace;
import erland.util.Log;
import erland.util.ParameterStorageString;
import erland.util.StringStorage;
import erland.webapp.common.DescriptionTagHelper;


public class GalleryServlet extends UserMgmtServlet {
    protected String getApplicationName() {
        return "gallery";
    }
    public void initEnd() {
        Log.setLogConfig(new ParameterStorageString(new StringStorage(
                "<log>"+
                "<timestamp>true</timestamp>"+
                "<timestampformat>HH:mm:ss.SSS</timestampformat>"+
                "<classname>true</classname>"+
                "<logitem1>erland.webapp.usermgmt.UserMgmtServletDisabled</logitem1>"+
                "<logitem2>erland.webapp.usermgmt.LoginCommand</logitem2>"+
                "<logitem3>erland.webapp.usermgmt.UserDisabled</logitem3>"+
                "<logitem4>erland.webapp.gallery.GalleryServlet</logitem4>"+
                "<logitem5>erland.webapp.usermgmt.UserMgmtServletDisabled</logitem5>"+
                "<logitem6>erland.webapp.common.GenericEntityStorageDisabled</logitem6>"+
                "<logitem7>erland.webapp.gallery.HTMLBasicStringReplaceDisabled</logitem7>"+
                "<logitem8>erland.webapp.gallery.HTMLLinkStringReplaceDisabled</logitem8>"+
                "<logitem9>erland.webapp.gallery.gallery.picture.SearchPicturesCommandDisabled</logitem9>"+
                "<logitem10>erland.webapp.common.image.ImageWriteHelperDisabled</logitem10>"+
                "<logitem11>erland.webapp.gallery.loader.LoadThumbnailCommandDisabled</logitem11>"+
                "<logitem12>erland.webapp.gallery.loader.LoadImageCommandDisabled</logitem12>"+
                "</log>"),null,"log"));
        HTMLEncoder.addReplaceRoutine(new HTMLBasicStringReplace());
        HTMLEncoder.addReplaceRoutine(new HTMLLinkStringReplace());
        DescriptionTagHelper.getInstance().init(getEnvironment());
    }
}
