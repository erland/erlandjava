<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.gallery.picturestorage.ViewPictureStoragesInterface,
                                erland.webapp.gallery.gallery.picturestorage.PictureStorage"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPictureStoragesInterface) {
        PictureStorage[] storages = ((ViewPictureStoragesInterface)cmd).getStorages();
        %>
        <a href="portal?do=newstorage" class="bold-link">Add</a>
        <table border="0">
        <%
        for (int i = 0; i < storages.length; i++) {
            PictureStorage storage = storages[i];
            %>
            <tr>
            <td><%=storage.getId()%></td>
            <td><%=storage.getName()%></td>
            <td><%=storage.getPath()%></td>
            <td><a href="portal?do=newstorage&id=<%=storage.getId()%>" class="bold-link">Update</a></td>
            <td><a href="portal?do=removestorage&id=<%=storage.getId()%>" class="bold-link" onClick="return confirm('Are you sure you want to delete this ?')">Delete</a></td>
            </tr>
            <%
        }
        %>
        </table>
        <%
    }
%>
