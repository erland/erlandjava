<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.appendix.AppendixEntry,
                                erland.webapp.diary.appendix.ViewAppendixEntryCommand"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewAppendixEntryCommand) {
        AppendixEntry entry = ((ViewAppendixEntryCommand)cmd).getEntry();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editappendixentry">
            <table>
            <%
            if(entry!=null && entry.getId()!=null) {
                %>
                <tr><td>Id</td><td>
                <input type="hidden" name="id" value="<%=entry.getId()%>">
                </td></tr>
                <%
            }
            %>
            <tr><td>Mönster</td><td>
            <input type="text" name="name" value="<%=entry!=null?entry.getName():""%>">
            </td></tr>
            <tr><td>Utbytestext</td><td>
            <input type="text" name="description" value="<%=entry!=null?entry.getDescription():""%>">
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchappendixentries'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
