<%@ page session="true" import="erland.webapp.common.CommandInterface,
                 erland.webapp.gallery.act.account.ViewUserAccountInterface,
                                erland.webapp.gallery.entity.account.UserAccount,
                                erland.webapp.common.html.HTMLEncoder"%>


<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewUserAccountInterface) {
        UserAccount account = ((ViewUserAccountInterface)cmd).getAccount();
        if(account!=null) {
            %>
            <%=HTMLEncoder.encode(account.getWelcomeText())%>
            <%
        }
    }
%>