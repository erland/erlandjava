<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.inventory.ViewInventoryEntryCommand,
                                erland.webapp.diary.inventory.InventoryEntry,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat,
                                erland.webapp.diary.DescriptionIdHelper,
                                erland.webapp.diary.DescriptionId,
                                java.util.Date"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewInventoryEntryCommand) {
        InventoryEntry entry = ((ViewInventoryEntryCommand)cmd).getEntry();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editinventoryentry">
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
            <tr><td>Typ</td><td>
            <select name="type">
            <%
                DescriptionId[] typeList = DescriptionIdHelper.getInstance().getDescriptionIdList("inventoryentrytype");
                for (int i = 0; i < typeList.length; i++) {
                    DescriptionId descriptionId = typeList[i];
                    %>
                    <option <%=entry!=null&&entry.getType().equals(descriptionId.getId())?"selected":""%> value="<%=descriptionId.getId()%>"><%=descriptionId.getDescription()%></option>
                    <%
                }
            %>
            </select>
            </td></tr>
            <tr><td>Titel</td><td>
            <input type="text" name="name" value="<%=entry!=null?entry.getName():""%>">
            </td></tr>
            <tr><td>Beskrivning</td><td>
            <textarea name="description" cols="80" rows="15" wrap="virtual"><%=entry!=null?entry.getDescription():""%></textarea>
            </td></tr>
            <tr><td>Bild</td><td>
            <input type="text" name="image" value="<%=entry!=null?entry.getImage():""%>">
            </td></tr>
            <tr><td>L�nk</td><td>
            <input type="text" name="link" value="<%=entry!=null?entry.getLink():""%>">
            </td></tr>
            <%
                if(entry==null) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    %>
                    <tr><td>Datum</td><td>
                    <input type="text" name="date" value="<%=dateFormat.format(new Date())%>">
                    </td></tr>
                    <tr><td>Storlek</td><td>
                    <input type="text" name="size" value="">
                    </td></tr>
                    <tr><td>H�ndelse</td><td>
                    <select name="eventdescription">
                    <%
                        typeList = DescriptionIdHelper.getInstance().getDescriptionIdList("inventoryentryeventtype");
                        for (int i = 0; i < typeList.length; i++) {
                            DescriptionId descriptionId = typeList[i];
                            %>
                            <option value="<%=descriptionId.getId()%>"><%=descriptionId.getDescription()%></option>
                            <%
                        }
                    %>
                    </select>
                    </td></tr>
                    <%
                }
            %>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchinventoryentries'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>