<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.purchase.ViewPurchaseEntryCommand,
                                erland.webapp.diary.purchase.PurchaseEntry,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat"%>

<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPurchaseEntryCommand) {
        PurchaseEntry entry = ((ViewPurchaseEntryCommand)cmd).getEntry();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editpurchaseentry">
            <table>
            <%
            if(entry!=null && entry.getId()!=null) {
                %>
                <tr><td>Id</td><td>
                <input type="hidden" name="id" value="<%=entry.getId()%>"><a class="normal"><%=entry.getId()%></a>
                </td></tr>
                <%
            }
            %>
            <tr><td>Datum</td><td>
            <input type="text" name="date" value="<%=entry!=null?dateFormat.format(entry.getDate()):""%>">
            </td></tr>
            <tr><td>Affär</td><td>
            <input type="text" name="store" value="<%=entry!=null?entry.getStore():""%>">
            </td></tr>
            <tr><td>Beskrivning</td><td>
            <input type="text" name="description" value="<%=entry!=null?entry.getDescription():""%>">
            </td></tr>
            <tr><td>Pris</td><td>
            <input type="text" name="price" value="<%=entry!=null?entry.getPrice().toString():""%>">
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchpurchaseentries'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
