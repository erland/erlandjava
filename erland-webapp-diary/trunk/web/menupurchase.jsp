<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.purchase.ViewPurchaseEntriesInterface"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPurchaseEntriesInterface) {
        %>
        <table class="no-border">
        <tr>
        <td class="sub-menu"></td>
        <td>
        <a href="portal?do=newpurchaseentry" class="bold-link">Lägg till</a>
        </td>
        </tr>
        </table>
        <%
    }
%>
