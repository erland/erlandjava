<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.appendix.ViewAppendixEntriesInterface,
                                erland.webapp.diary.appendix.AppendixEntry"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewAppendixEntriesInterface) {
        AppendixEntry[] entries = ((ViewAppendixEntriesInterface)cmd).getEntries();
        %>
        <table border="0">
        <%
        for (int i = 0; i < entries.length; i++) {
            AppendixEntry entry = entries[i];
            %>
            <tr>
            <td colspan="2"><%=entry.getName()%></td>
            </tr>
            <tr>
            <td colspan="2"><%=entry.getDescription()%></td>
            </tr>
            <tr>
            <td><a href="portal?do=newappendixentry&id=<%=entry.getId()%>" class="bold-link">Uppdatera</a></td>
            <td><a href="portal?do=removeappendixentry&id=<%=entry.getId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a></td>
            <tr>
            <%
        }
        %>
        </table>
        <%
    }
%>
