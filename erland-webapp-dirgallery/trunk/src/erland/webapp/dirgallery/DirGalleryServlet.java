package erland.webapp.dirgallery;

import erland.util.Log;
import erland.util.ParameterStorageString;
import erland.util.StringStorage;
import erland.webapp.usermgmt.UserMgmtServlet;


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
