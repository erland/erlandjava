<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.diary.Diary,
                                erland.webapp.diary.diary.ViewDiaryCommand"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewDiaryCommand) {
        Diary diary = ((ViewDiaryCommand)cmd).getDiary();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editdiary">
            <table>
            <%
            if(diary!=null && diary.getId()!=null) {
                %>
                <tr><td>Id</td><td>
                <input type="hidden" name="id" value="<%=diary.getId()%>"><a class="normal"><%=diary.getId()%></a>
                <input type="hidden" name="diary" value="<%=diary.getId()%>">
                </td></tr>
                <%
            }
            %>
            <tr><td>Titel</td><td>
            <input type="text" name="title" value="<%=diary!=null?diary.getTitle():""%>">
            </td></tr>
            <tr><td>Beskrivning</td><td>
            <textarea name="description" cols="80" rows="15" wrap="virtual"><%=diary!=null?diary.getDescription():""%></textarea>
            </td></tr>
            <tr><td>Officiell</td><td>
            <input type="checkbox" name="official" value="true" <%=(diary!=null && diary.getOfficial().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchentries<%=diary!=null?"&diary="+diary.getId():""%>'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
