<%@ page session="true" import="erland.webapp.diary.appendix.ViewAppendixEntriesInterface,
                                erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.diary.diary.ViewDiariesInterface,
                                erland.webapp.diary.diary.Diary"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewDiariesInterface) {
        %>
        <table class="no-border">
        <%
        Diary[] diaries = ((ViewDiariesInterface)cmd).getDiaries();
        String user = request.getParameter("user");
        for (int i = 0; i < diaries.length; i++) {
            Diary diary = diaries[i];
            %>
            <tr>
            <td class="sub-menu"></td>
            <td>
            <a href="portal?do=searchentriesguest<%=user!=null?"&user="+user:""%>&diary=<%=diary.getId()%>" class="bold-link"><%=diary.getTitle()%></a>
            </td>
            </tr>
            <%
        }
        %>
        </table>
        <%
    }
%>
