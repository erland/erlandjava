<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat,
                                erland.webapp.gallery.act.account.ViewUserAccountsInterface,
                                erland.webapp.gallery.entity.account.UserAccount,
                                erland.webapp.usermgmt.User"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewUserAccountsInterface) {
        UserAccount[] accounts = ((ViewUserAccountsInterface)cmd).getAccounts();
        %>
        <table border="0">
        <%
        for (int i = 0; i < accounts.length; i++) {
            UserAccount account = accounts[i];
            User user = ((ViewUserAccountsInterface)cmd).getUser(account);
            %>
            <tr>
            <td>
            <a class="bold-link" href="portal?do=<%=request.getParameter("viewusercmd")%>&user=<%=account.getUsername()%>" title="<%=account.getDescription()!=null?account.getDescription():""%>">
            <%
            if(account.getLogo()!=null && account.getLogo().length()>0) {
                %>
                <img src="<%=account.getLogo()%>" border="0" width="300"></img><br>
                <%
            }
            %>
            <div align="center"><%=user.getFirstName()%> <%=user.getLastName()%></div>
            </a>
            </td>
            </tr>
            <tr><td>&nbsp</td></tr>
            <%
        }
        %>
        </table>
        <%
    }
%>
