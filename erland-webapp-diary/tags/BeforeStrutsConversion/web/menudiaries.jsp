<%@ page session="true" import="erland.webapp.diary.appendix.ViewAppendixEntriesInterface,
                                erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.diary.diary.ViewDiariesInterface,
                                erland.webapp.diary.diary.Diary"%>
<jsp:useBean id="user" scope="session" class="User" />

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewDiariesInterface) {
        %>
        <table class="no-border">
        <tr>
        <td class="sub-menu"></td>
        <td>
        <a href="portal?do=newdiary" class="bold-link">Lägg till</a>
        </td>
        </tr>
        <%
        Diary[] diaries = ((ViewDiariesInterface)cmd).getDiaries();
        for (int i = 0; i < diaries.length; i++) {
            Diary diary = diaries[i];
            %>
            <tr>
            <td class="sub-menu"></td>
            <td>
            <a href="portal?do=searchentries&diary=<%=diary.getId()%>" class="bold-link"><%=diary.getTitle()%></a>
            </td>
            </tr>
            <%
        }
        %>
        </table>
        <%
    }
%>
