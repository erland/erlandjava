package erland.webapp.gallery;

import erland.webapp.usermgmt.UserMgmtServlet;
import erland.webapp.common.ServletParameterHelper;
import erland.util.Log;
import erland.util.ParameterStorageString;
import erland.util.StringStorage;

public class GalleryServlet extends UserMgmtServlet {
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
                "<logitem6>erland.webapp.common.GenericEntityStorage</logitem6>"+
                "<logitem7>erland.webapp.gallery.HTMLBasicStringReplaceDisabled</logitem7>"+
                "<logitem8>erland.webapp.gallery.HTMLLinkStringReplaceDisabled</logitem8>"+
                "<logitem9>erland.webapp.gallery.gallery.picture.SearchPicturesCommandDisabled</logitem9>"+
                "</log>"),null,"log"));
        HTMLEncoder.addReplaceRoutine(new HTMLBasicStringReplace());
        HTMLEncoder.addReplaceRoutine(new HTMLLinkStringReplace());
        DescriptionTagHelper.getInstance().init(getEnvironment());
    }
}
