<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.account.ViewUserAccountInterface,
                                erland.webapp.gallery.account.UserAccount,
                                erland.webapp.usermgmt.User"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewUserAccountInterface) {
        UserAccount account = ((ViewUserAccountInterface)cmd).getAccount();
        User user = ((ViewUserAccountInterface)cmd).getUser();
        %>
        A new user has been created for you with the following information
        <table>
        <tr>
        <td>Username</td>
        <td><%=account.getUsername()%></td>
        </tr>
        <tr>
        <td>First name</td>
        <td><%=user.getFirstName()%></td>
        </tr>
        <tr>
        <td>Last name</td>
        <td><%=user.getLastName()%></td>
        </tr>
        </table>
        Login below to start using your gallery
        <jsp:include page="loginform.jsp">
            <jsp:param name="loginuser" value="<%=account.getUsername()%>"/>
            <jsp:param name="logincmd" value="login"/>
        </jsp:include>
        <%
    }
%>
