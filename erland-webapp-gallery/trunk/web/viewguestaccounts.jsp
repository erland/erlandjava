<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.guestaccount.GuestAccount,
                                erland.webapp.gallery.guestaccount.ViewGuestAccountsInterface"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGuestAccountsInterface) {
        GuestAccount[] accounts = ((ViewGuestAccountsInterface)cmd).getAccounts();
        %>
        <p class="normal">Dessa användare kommer åt dina privata bilder och privata bildarkiv</p>
        <br>
        <a href="portal?do=newguestaccount" class="bold-link">Lägg till användare</a>
        <table border="0">
        <%
        for (int i = 0; i < accounts.length; i++) {
            GuestAccount account = accounts[i];
            %>
            <tr>
            <td><%=account.getGuestUser()%></td>
            <td><a href="portal?do=removeguestaccount&guestuser=<%=account.getGuestUser()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a></td>
            </tr>
            <%
        }
        %>
        </table>
        <%
    }
%>
