<%@ page session="true" import="erland.webapp.diary.inventory.InventoryEntry,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.diary.inventory.ViewInventoryEntryInterface,
                                erland.webapp.diary.inventory.InventoryEntryEvent,
                                erland.webapp.diary.DescriptionIdHelper,
                                erland.webapp.diary.HTMLEncoder"%>
<table class="no-border">
<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewInventoryEntryInterface) {
        InventoryEntry entry = ((ViewInventoryEntryInterface)cmd).getEntry();
        if(entry!=null) {
            String image = entry.getImage();
            String description = entry.getDescription();
            String link = entry.getLink();
            InventoryEntryEvent[] events = ((ViewInventoryEntryInterface)cmd).getEvents();
            if((image!=null && image.length()>0) ||
                (description!=null && description.length()>0) ||
                (link!=null && link.length()>0)) {

                %>
                <tr>
                <td>
                <p class="title">
                <%=entry.getName()%>
                <%=events.length>0?events[events.length-1].getSize().toString()+" cm":""%>
                <%=events.length>0?DescriptionIdHelper.getInstance().getDescription("inventoryentryeventtype",events[0].getDescription()):""%>
                <%=events.length>0?dateFormat.format(events[0].getDate()):""%>
                </p>
                </td>
                </tr>
                <%
            }
            if(image!=null && image.length()>0) {
                %>
                <tr><td>&nbsp</td></tr>
                <tr>
                <td>
                <a href="<%=image%>" target="_blank"><img src="<%=image%>" border="0" width="300"></img></a>
                </td>
                </tr>
                <%
            }
            if(description!=null && description.length()>0) {
                %>
                <tr><td>&nbsp</td></tr>
                <tr>
                <td>
                <p class="normal"><%=HTMLEncoder.encode(description)%></p>
                </td>
                </tr>
                <%
            }
            %>
            <tr><td>&nbsp</td></tr>
            <%
            for (int i = 0; i < events.length; i++) {
                %>
                <tr><td><%=dateFormat.format(events[i].getDate())%> <%=DescriptionIdHelper.getInstance().getDescription("inventoryentryeventtype",events[i].getDescription())%> <%=events[i].isSizeRelevant()?events[i].getSize().toString()+" cm":""%></td><tr>
                <%
            }
            if(link!=null && link.length()>0) {
                %>
                <tr><td>&nbsp</td></tr>
                <tr>
                <td>
                <a class="bold-link" href="<%=link%>" target="_blank">Mer information</a>
                </td>
                </tr>
                <%
            }
        %>
        <%
        }
    }
%>
</table>
