<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat,
                                erland.webapp.diary.inventory.ViewInventoryEntryEventCommand,
                                erland.webapp.diary.inventory.InventoryEntryEvent,
                                erland.webapp.diary.DescriptionId,
                                erland.webapp.diary.DescriptionIdHelper,
                                java.util.Date"%>

<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewInventoryEntryEventCommand) {
        InventoryEntryEvent entry = ((ViewInventoryEntryEventCommand)cmd).getEntry();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editinventoryentryevent">
            <input type="hidden" name="id" value="<%=entry!=null?entry.getId().toString():request.getParameter("id")%>">
            <table>
            <%
            if(entry!=null && entry.getEventId()!=null) {
                %>
                <tr><td>Händelse</td><td>
                <input type="hidden" name="eventid" value="<%=entry.getEventId()%>"><a class="normal"><%=entry.getEventId()%></a>
                </td></tr>
                <%
            }
            %>
            <tr><td>Datum</td><td>
            <input type="text" name="date" value="<%=entry!=null?dateFormat.format(entry.getDate()):dateFormat.format(new Date())%>">
            </td></tr>
            <tr><td>Storlek</td><td>
            <%
                String size = request.getParameter("size");
                if(size==null) {
                    size="";
                }
            %>
            <input type="text" name="size" value="<%=entry!=null?entry.getSize().toString():size%>">
            </td></tr>
            <tr><td>Händelse</td><td>
            <select name="type">
            <%
            DescriptionId[] typeList = DescriptionIdHelper.getInstance().getDescriptionIdList("diary-inventoryentryeventtype");
            for (int i = 0; i < typeList.length; i++) {
                DescriptionId descriptionId = typeList[i];
                        %>
                        <option <%=entry!=null&&entry.getDescription().equals(descriptionId.getId())?"selected":""%> value="<%=descriptionId.getId()%>"><%=descriptionId.getDescription()%></option>
                        <%
            }
            %>
            </select>
            </td></tr>
            <tr><td></td><td>
            <%
            String abortCmd = (String) request.getSession().getAttribute("searchinventorycmd");
            if(abortCmd!=null && abortCmd.length()==0) {
                abortCmd=null;
            }
            %>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?<%=abortCmd!=null?abortCmd:"do=searchinventoryentries"%>'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
