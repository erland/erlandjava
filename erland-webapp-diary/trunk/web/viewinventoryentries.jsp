<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.inventory.ViewInventoryEntriesInterface,
                                erland.webapp.diary.inventory.InventoryEntry,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat,
                                erland.webapp.diary.inventory.InventoryEntryEvent,
                                erland.webapp.diary.DescriptionIdHelper"%>

<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewInventoryEntriesInterface) {
        InventoryEntry[] entries = ((ViewInventoryEntriesInterface)cmd).getEntries();
        %>
        <table border="0">
        <%
        for (int i = 0; i < entries.length; i++) {
            InventoryEntry entry = entries[i];
            %>
            <tr>
            <td><p class="normal"><%=entry.getId()%></p></td>
            <td><p class="normal"><%=DescriptionIdHelper.getInstance().getDescription("diary-inventoryentrytype",entry.getType())%></p></td>
            <td><p class="normal"><%=entry.getName()%></p></td>
            <td><p class="normal">&nbsp&nbsp&nbsp</p></td>
            <%
                InventoryEntryEvent[] events = ((ViewInventoryEntriesInterface)cmd).getEvents(entry);
                if(events.length>0) {
                    %>
                    <td><p class="normal"><%=events[0].isSizeRelevant()?events[0].getSize().toString()+" cm":""%></p></td>
                    <td><p class="normal"><%=DescriptionIdHelper.getInstance().getDescription("diary-inventoryentryeventtype",events[0].getDescription())%></p></td>
                    <td><p class="normal"><%=dateFormat.format(events[0].getDate())%></p></td>
                    <td><a href="portal?do=newinventoryentryevent&id=<%=events[0].getId()%>&eventid=<%=events[0].getEventId()%>" class="bold-link">Uppdatera</a></td>
                    <td><a href="portal?do=removeinventoryentryevent&id=<%=events[0].getId()%>&eventid=<%=events[0].getEventId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a></td>
                    <%
                }else {
                    %>
                    <td><p class="normal">&nbsp</p></td>
                    <td><p class="normal">&nbsp</p></td>
                    <td><p class="normal">&nbsp</p></td>
                    <td><p class="normal">&nbsp</p></td>
                    <td><p class="normal">&nbsp</p></td>
                    <%
                }
            %>
            </tr>
            <%
                for(int j=1;j<events.length;j++) {
                    %>
                    <tr>
                    <td><p class="normal">&nbsp</p></td>
                    <td><p class="normal">&nbsp</p></td>
                    <td><p class="normal">&nbsp</p></td>
                    <td><p class="normal">&nbsp&nbsp&nbsp</p></td>
                    <td><p class="normal"><%=events[j].isSizeRelevant()?events[j].getSize().toString()+" cm":""%></p></td>
                    <td><p class="normal"><%=DescriptionIdHelper.getInstance().getDescription("diary-inventoryentryeventtype",events[j].getDescription())%></p></td>
                    <td><p class="normal"><%=dateFormat.format(events[j].getDate())%></p></td>
                    <td><a href="portal?do=newinventoryentryevent&id=<%=events[j].getId()%>&eventid=<%=events[j].getEventId()%>" class="bold-link">Uppdatera</a></td>
                    <td><a href="portal?do=removeinventoryentryevent&id=<%=events[j].getId()%>&eventid=<%=events[j].getEventId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a></td>
                    </tr>
                    <%
                }
            %>
            <tr>
            <td></td>
            <td><a href="portal?do=newinventoryentry&id=<%=entry.getId()%>" class="bold-link">Uppdatera</a></td>
            <td><a href="portal?do=removeinventoryentry&id=<%=entry.getId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a></td>
            <td></td>
            <td colspan="3"><a href="portal?do=newinventoryentryevent&id=<%=entry.getId()%><%=events.length>0?"&size="+events[0].getSize():""%>" class="bold-link">Lägg till händelse</a></td>
            </tr>
            <%
        }
        %>
        </table>
        <%
    }
%>
