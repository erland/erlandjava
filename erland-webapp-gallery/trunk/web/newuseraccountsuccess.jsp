<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.act.account.ViewUserAccountInterface,
                                erland.webapp.gallery.entity.account.UserAccount,
                                erland.webapp.usermgmt.User"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewUserAccountInterface) {
        UserAccount account = ((ViewUserAccountInterface)cmd).getAccount();
        User user = ((ViewUserAccountInterface)cmd).getUser();
        %>
        En ny användare har skapats åt dig med följande information
        <table>
        <tr>
        <td>Användarnamn</td>
        <td><%=account.getUsername()%></td>
        </tr>
        <tr>
        <td>Förnamn</td>
        <td><%=user.getFirstName()%></td>
        </tr>
        <tr>
        <td>Efternamn</td>
        <td><%=user.getLastName()%></td>
        </tr>
        </table>
        Logga in nedan för att börja använda ditt bildarkiv
        <jsp:include page="loginform.jsp">
            <jsp:param name="loginuser" value="<%=account.getUsername()%>"/>
            <jsp:param name="logincmd" value="login"/>
        </jsp:include>
        <%
    }
%>
