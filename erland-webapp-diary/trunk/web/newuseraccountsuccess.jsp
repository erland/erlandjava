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
        En ny anv�ndare ha skapats �t dig med f�ljande data
        <table>
        <tr>
        <td>Anv�ndarnamn</td>
        <td><%=account.getUsername()%></td>
        </tr>
        <tr>
        <td>F�rnamn</td>
        <td><%=user.getFirstName()%></td>
        </tr>
        <tr>
        <td>Efternamn</td>
        <td><%=user.getLastName()%></td>
        </tr>
        </table>
        Logga in nedan f�r att b�rja anv�nda din dagbok
        <jsp:include page="loginform.jsp">
            <jsp:param name="user" value="<%=account.getUsername()%>"/>
        </jsp:include>
        <%
    }
%>
