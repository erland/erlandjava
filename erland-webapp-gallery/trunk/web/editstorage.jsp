<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.act.gallery.picturestorage.ViewPictureStorageAction,
                                erland.webapp.gallery.entity.gallery.picturestorage.PictureStorage"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPictureStorageAction) {
        PictureStorage storage = ((ViewPictureStorageAction)cmd).getStorage();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editstorage">
            <table>
            <%
            if(storage!=null) {
                %>
                <tr><td>Id</td><td>
                <input type="hidden" name="id" value="<%=storage.getId()%>"><a class="normal"><%=storage.getId()%></a>
                </td></tr>
                <%
            }
            %>
            <tr><td>Början på sökväg</td><td>
            <input type="text" name="name" size="40" value="<%=storage!=null?storage.getName():""%>">
            </td></tr>
            <tr><td>Början på sökväg som skall användas</td><td>
            <input type="text" name="path" size="80" value="<%=storage!=null?storage.getPath():""%>">
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=home'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
