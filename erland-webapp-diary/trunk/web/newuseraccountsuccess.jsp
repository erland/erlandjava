<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.account.ViewUserAccountInterface,
                                erland.webapp.diary.account.UserAccount,
                                erland.webapp.usermgmt.User"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewUserAccountInterface) {
        UserAccount account = ((ViewUserAccountInterface)cmd).getAccount();
        User user = ((ViewUserAccountInterface)cmd).getUser();
        %>
        En ny användare ha skapats åt dig med följande data
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
        Logga in nedan för att börja använda din dagbok
        <jsp:include page="loginform.jsp">
            <jsp:param name="user" value="<%=account.getUsername()%>"/>
        </jsp:include>
        <%
    }
%>
