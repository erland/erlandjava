<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.dirgallery.account.ViewUserAccountInterface,
                                erland.webapp.dirgallery.account.UserAccount,
                                erland.webapp.dirgallery.HTMLEncoder"%>


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