<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.guestaccount.GuestAccount,
                                erland.webapp.gallery.guestaccount.ViewGuestAccountsInterface"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGuestAccountsInterface) {
        GuestAccount[] accounts = ((ViewGuestAccountsInterface)cmd).getAccounts();
        %>
        <p class="normal">These users will have access to all of your private pictures and galleries</p>
        <br>
        <a href="portal?do=newguestaccount" class="bold-link">Add</a>
        <table border="0">
        <%
        for (int i = 0; i < accounts.length; i++) {
            GuestAccount account = accounts[i];
            %>
            <tr>
            <td><%=account.getGuestUser()%></td>
            <td><a href="portal?do=removeguestaccount&guestuser=<%=account.getUsername()%>" class="bold-link" onClick="return confirm('Are you sure you want to delete this ?')">Delete</a></td>
            </tr>
            <%
        }
        %>
        </table>
        <%
    }
%>
