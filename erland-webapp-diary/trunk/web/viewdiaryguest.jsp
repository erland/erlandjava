<%@ page session="true" import="erland.webapp.common.CommandInterface,
                 erland.webapp.diary.account.ViewUserAccountInterface,
                                erland.webapp.diary.account.UserAccount,
                                erland.webapp.diary.HTMLEncoder,
                                erland.webapp.diary.diary.ViewDiaryInterface,
                                erland.webapp.diary.diary.Diary"%>


<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewDiaryInterface) {
        Diary diary = ((ViewDiaryInterface)cmd).getDiary();
        if(diary!=null) {
            %>
            <p class="title"><%=diary.getTitle()%></p>
            <p class="normal"><%=HTMLEncoder.encode(diary.getDescription())%></p>
            <%
        }
    }
%>