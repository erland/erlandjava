<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.purchase.ViewPurchaseEntriesInterface,
                                erland.webapp.diary.purchase.PurchaseEntry,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat"%>

<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPurchaseEntriesInterface) {
        PurchaseEntry[] entries = ((ViewPurchaseEntriesInterface)cmd).getEntries();
        %>
        <table border="0">
        <%
        for (int i = 0; i < entries.length; i++) {
            PurchaseEntry entry = entries[i];
            %>
            <tr>
            <td nowrap><p class="normal"><%=dateFormat.format(entry.getDate())%></p></td>
            <td nowrap><p class="normal"><%=entry.getDescription()%></p></td>
            <td nowrap><p class="normal">&nbsp&nbsp&nbsp</p></td>
            <td nowrap><p class="normal"><%=entry.getPrice()%> kr</p></td>
            </tr>
            <tr>
            <td></td>
            <td nowrap><p class="normal"><%=entry.getStore()%></p></td>
            <td nowrap><a href="portal?do=newpurchaseentry&id=<%=entry.getId()%>" class="bold-link">Uppdatera</a></td>
            <td nowrap><a href="portal?do=removepurchaseentry&id=<%=entry.getId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a></td>
            <tr>
            <%
        }
        %>
        </table>
        <%
    }
%>
