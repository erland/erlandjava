<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.diary.ViewEntryCommand,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat,
                                erland.webapp.diary.diary.DiaryEntryInterface"%>

<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewEntryCommand) {
        DiaryEntryInterface entry = ((ViewEntryCommand)cmd).getEntry();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editentry">
            <%
            String diary = request.getParameter("diary");
            if(diary!=null) {
                %>
                <input type="hidden" name="diary" value="<%=diary%>">
                <%
            }
            %>
            <table>
            <tr><td>Datum</td><td>
            <input type="hidden" name="date" value="<%=entry!=null?dateFormat.format(entry.getDate()):request.getParameter("date")%>"><%=entry!=null?dateFormat.format(entry.getDate()):request.getParameter("date")%></input>
            </td></tr>
            <tr><td>Titel</td><td>
            <input type="text" name="title" value="<%=entry!=null?entry.getTitle():""%>">
            </td></tr>
            <tr><td>Beskrivning</td><td>
            <textarea name="description" cols="80" rows="15" wrap="virtual"><%=entry!=null?entry.getDescription():""%></textarea>
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchentries<%=diary!=null?"&diary="+diary:""%>'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
