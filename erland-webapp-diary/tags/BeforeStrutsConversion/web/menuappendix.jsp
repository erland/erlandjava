<%@ page session="true" import="erland.webapp.diary.appendix.ViewAppendixEntriesInterface,
                                erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface"%>
<jsp:useBean id="user" scope="session" class="User" />

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(user.hasRole("manager")) {
        if(cmd instanceof ViewAppendixEntriesInterface) {
            %>
            <table class="no-border">
            <tr>
            <td class="sub-menu"></td>
            <td>
            <a href="portal?do=newappendixentry" class="bold-link">Utöka appendix</a>
            </td>
            </tr>
            </table>
            <%
        }
    }
%>
