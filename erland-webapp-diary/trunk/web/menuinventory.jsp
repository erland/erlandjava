<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.inventory.ViewInventoryEntriesInterface,
                                java.util.Date,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat"%>
<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewInventoryEntriesInterface) {
        %>
        <table class="no-border">
        <tr>
        <td class="sub-menu"></td>
        <td>
        <a href="portal?do=newinventoryentry" class="bold-link">Lägg till</a>
        </td>
        </tr>
        <tr>
        <td class="sub-menu"></td>
        <td>
        <%
        if(request.getParameter("do")!=null && request.getParameter("do").equals("searchinventoryentries")) {
            %>
            <a href="portal?do=searchactiveinventoryentries&date=<%=dateFormat.format(new Date())%>" class="bold-link">Visa aktuella</a>
            <%
        }else {
            %>
            <a href="portal?do=searchinventoryentries" class="bold-link">Visa historik</a>
            <%
        }
        %>
        </td>
        </tr>
        </table>
        <%
    }
%>
