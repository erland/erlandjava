package erland.webapp.stocks;

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
                "<logitem3>erland.webapp.stocks.sb.SBXMLEncoder</logitem3>"+
                "<logitem4>erland.webapp.stocks.sb.SBXMLParser</logitem4>"+
                "<logitem5>erland.webapp.stocks.Stock</logitem5>"+
                "<logitem6>erland.webapp.stocks.StockAccount</logitem6>"+
                "<logitem7>erland.webapp.stocks.StockAccountCommand</logitem7>"+
                "<logitem8>erland.webapp.common.BaseServlet</logitem8>"+
                "<logitem9>erland.webapp.diagram.DateValueDiagram</logitem9>"+
                "<logitem10>erland.webapp.common.EntityStorage</logitem10>"+
                "</log>"),null,"log"));

    }
}
