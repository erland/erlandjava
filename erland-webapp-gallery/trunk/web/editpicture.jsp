<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.gallery.Gallery,
                                erland.webapp.gallery.gallery.ViewGalleryCommand,
                                erland.webapp.gallery.gallery.picture.ViewPictureCommand,
                                erland.webapp.gallery.gallery.picture.Picture"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPictureCommand) {
        Picture entry = ((ViewPictureCommand)cmd).getPicture();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editpicture">
            <table>
            <input type="hidden" name="gallery" value="<%=entry!=null?entry.getGallery().toString():request.getParameter("gallery")%>">
            <%
            if(entry!=null && entry.getId()!=null) {
                %>
                <tr><td>Id</td><td>
                <input type="hidden" name="id" value="<%=entry.getId()%>"><a class="normal"><%=entry.getId()%></a>
                </td></tr>
                <%
            }
            %>
            <tr><td>Title</td><td>
            <input type="text" name="title" value="<%=entry!=null?entry.getTitle():""%>">
            </td></tr>
            <tr><td>Description</td><td>
            <textarea name="description" cols="80" rows="15" wrap="virtual"><%=entry!=null?entry.getDescription():""%></textarea>
            </td></tr>
            <tr><td>Small image</td><td>
            <input type="text" name="image" value="<%=entry!=null?entry.getImage():""%>">
            </td></tr>
            <tr><td>Large image</td><td>
            <input type="text" name="link" value="<%=entry!=null?entry.getLink():""%>">
            </td></tr>
            <tr><td>Official</td><td>
            <input type="checkbox" name="official" value="true" <%=(entry!=null && entry.getOfficial().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Save">
            <input type="button" value="Cancel" onClick="window.location='portal?do=searchgalleryentries<%=entry!=null?"&gallery="+entry.getGallery():"&gallery="+request.getParameter("gallery")%>&start=0&max=9'">
            </td></tr>
            </table>
        </form>
        <%
    }
%>
