<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.usermgmt.User,
                                erland.webapp.dirgallery.account.ViewUserAccountInterface,
                                erland.webapp.dirgallery.account.UserAccount"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewUserAccountInterface) {
        UserAccount account = ((ViewUserAccountInterface)cmd).getAccount();
        User user = ((ViewUserAccountInterface)cmd).getUser();
        %>
        En ny anv�ndare har skapats �t dig med f�ljande information
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
        Logga in h�r nedanf�r om du vill b�rja anv�nda ditt bildarkiv
        <jsp:include page="loginform.jsp">
            <jsp:param name="loginuser" value="<%=account.getUsername()%>"/>
            <jsp:param name="logincmd" value="login"/>
        </jsp:include>
        <%
    }
%>
