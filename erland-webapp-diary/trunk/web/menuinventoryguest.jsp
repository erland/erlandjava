<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.inventory.ViewInventoryEntriesInterface,
                                java.util.Date,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat"%>
<table border="0">
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewInventoryEntriesInterface) {
        String user = request.getParameter("user");
        if(user.length()==0) {
            user = null;
        }
        %>
        <tr><td><p class="normal">&nbsp&nbsp&nbsp</p></td>
        <td colspan="2">
        <a href="portal?do=searchentriesguest<%=user!=null?"&user="+user:""%>" class="bold-link">Tillbaka</a>
        </td>
        </tr>
        <%
    }
%>
</table>