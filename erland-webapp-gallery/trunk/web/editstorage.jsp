<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.gallery.picturestorage.ViewPictureStorageCommand,
                                erland.webapp.gallery.gallery.picturestorage.PictureStorage"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPictureStorageCommand) {
        PictureStorage storage = ((ViewPictureStorageCommand)cmd).getStorage();
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
            <tr><td>Prefix path</td><td>
            <input type="text" name="name" value="<%=storage!=null?storage.getName():""%>">
            </td></tr>
            <tr><td>Real path</td><td>
            <input type="text" name="path" value="<%=storage!=null?storage.getPath():""%>">
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Save">
            <input type="button" value="Cancel" onClick="window.location='portal?do=home'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
