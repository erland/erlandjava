package erland.webapp.diary;

import erland.webapp.usermgmt.UserMgmtServlet;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandInterface;
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
                "<logitem4>erland.webapp.diary.DiaryEntryStorage</logitem4>"+
                "<logitem5>erland.webapp.diary.DiaryServlet</logitem5>"+
                "<logitem6>erland.webapp.usermgmt.UserMgmtServlet</logitem6>"+
                "<logitem7>erland.webapp.common.GenericEntityStorage</logitem7>"+
                "<logitem8>erland.webapp.diary.HTMLBasicStringReplace</logitem8>"+
                "<logitem9>erland.webapp.diary.HTMLLinkStringReplace</logitem9>"+
                "<logitem10>erland.webapp.diary.inventory.EditInventoryEntryEventCommand</logitem10>"+
                "</log>"),null,"log"));
        System.out.println(getEnvironment().getResources().getParameter("pages.default"));
        HTMLEncoder.addReplaceRoutine(new HTMLBasicStringReplace());
        HTMLEncoder.addReplaceRoutine(new AppendixStringReplace(getEnvironment()));
        HTMLEncoder.addReplaceRoutine(new HTMLLinkStringReplace());
        DescriptionIdHelper.getInstance().init(getEnvironment());
    }
}
