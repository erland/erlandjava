<%@ page session="true" import="erland.webapp.common.CommandInterface,
                 erland.webapp.diary.account.ViewUserAccountInterface,
                                erland.webapp.diary.account.UserAccount,
                                erland.webapp.common.html.HTMLEncoder"%>


<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewUserAccountInterface) {
        UserAccount account = ((ViewUserAccountInterface)cmd).getAccount();
        if(account!=null) {
            %>
            <font class="normal"><%=HTMLEncoder.encode(account.getWelcomeText())%></font>
            <%
        }
    }
%>