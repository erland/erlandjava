<%@ page session="true" import="erland.webapp.common.CommandInterface,
                 erland.webapp.diary.account.ViewUserAccountInterface,
                                erland.webapp.diary.account.UserAccount,
                                erland.webapp.diary.diary.ViewDiaryInterface,
                                erland.webapp.diary.diary.Diary"%>


<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewDiaryInterface) {
        Diary diary = ((ViewDiaryInterface)cmd).getDiary();
        if(diary!=null) {
            %>
            <a href="portal?do=newdiary&id=<%=diary.getId()%>" class="bold-link">Uppdatera</a>
            <a href="portal?do=removediary&id=<%=diary.getId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a>
            <br>
            <jsp:include page="viewdiaryguest.jsp" />
            <%
        }
    }
%>