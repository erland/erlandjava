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
                <td><a class="bold-link" href="portal?do=<%=request.getParameter("do")%><%=user!=null?"&user="+user:""%>&id=<%=entry.getId()%>"><%=entry.getName()%></a></td>
                <td><p class="normal">&nbsp&nbsp&nbsp</p></td>
                <%
                    InventoryEntryEvent[] events = ((ViewInventoryEntriesInterface)cmd).getEvents(entry);
                    if(events.length>0) {
                        %>
                        <td><%=events[0].getSize()%> cm</td>
                        <td><%=DescriptionIdHelper.getInstance().getDescription("inventoryentryeventtype",events[events.length-1].getDescription())%></td>
                        <td><%=dateFormat.format(events[events.length-1].getDate())%></td>
                        <%
                    }else {
                        %>
                        <td><p class="normal">&nbsp</p></td>
                        <td><p class="normal">&nbsp</p></td>
                        <td><p class="normal">&nbsp</p></td>
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
