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
    String user = request.getParameter("user");
    if(user!=null && user.length()==0) {
        user=null;
    }
    if(cmd instanceof ViewInventoryEntriesInterface) {
        InventoryEntry[] entries = ((ViewInventoryEntriesInterface)cmd).getEntries();
        %>
        <table border="0">
            <tr>
            <td valign="top">
            <table border="0">
            <%
            for (int i = 0; i < entries.length; i++) {
                InventoryEntry entry = entries[i];
                %>
                <tr>
                <td nowrap><a class="bold-link" href="portal?do=searchactiveinventoryentries<%=user!=null?"&user="+user:""%>&id=<%=entry.getId()%>"><%=entry.getName()%></a></td>
                <td nowrap><p class="normal">&nbsp&nbsp&nbsp</p></td>
                <%
                    InventoryEntryEvent[] events = ((ViewInventoryEntriesInterface)cmd).getEvents(entry);
                    if(events.length>0) {
                        %>
                        <td nowrap><p class="normal"><%=events[0].getSize()%> cm</p></td>
                        <td nowrap><p class="normal"><%=DescriptionIdHelper.getInstance().getDescription("diary-inventoryentryeventtype",events[events.length-1].getDescription())%></p></td>
                        <td nowrap><p class="normal"><%=dateFormat.format(events[events.length-1].getDate())%></p></td>
                        <td nowrap><a href="portal?do=newinventoryentry&id=<%=entry.getId()%>" class="bold-link">Uppdatera</a></td>
                        </tr>
                        <tr>
                        <td><p class="normal">&nbsp</p></td>
                        <td><p class="normal">&nbsp</p></td>
                        <td colspan="3" nowrap><a href="portal?do=newinventoryentryevent&id=<%=entry.getId()%>&size=<%=events[0].getSize()%>" class="bold-link">L�gg till h�ndelse</a></td>
                        <%
                    }else {
                        %>
                        <td nowrap><p class="normal">&nbsp</p></td>
                        <td nowrap><p class="normal">&nbsp</p></td>
                        <td nowrap><p class="normal">&nbsp</p></td>
                        <td nowrap><a href="portal?do=newinventoryentry&id=<%=entry.getId()%>" class="bold-link">Uppdatera</a></td>
                        </tr>
                        <tr>
                        <td><p class="normal">&nbsp</p></td>
                        <td><p class="normal">&nbsp</p></td>
                        <td colspan="3" nowrap><a href="portal?do=newinventoryentryevent&id=<%=entry.getId()%>" class="bold-link">L�gg till h�ndelse</a></td>
                        <%
                    }
                %>
                </tr>
                <%
            }
            %>
            </table>
            </td>
            <td valign="top">
            <jsp:include page="viewinventoryentry.jsp"/>
            </td>
            </tr>
        </table>
        <%
    }
%>
