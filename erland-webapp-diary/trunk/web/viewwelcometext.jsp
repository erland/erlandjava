<%@ page session="true" import="erland.webapp.common.CommandInterface,
                 erland.webapp.diary.account.ViewUserAccountInterface,
                                erland.webapp.diary.account.UserAccount,
                                erland.webapp.diary.HTMLEncoder"%>


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