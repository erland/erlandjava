<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.entity.gallery.Gallery,
                                erland.webapp.gallery.act.gallery.ViewGalleryAction,
                                erland.webapp.gallery.act.gallery.picture.ViewPictureAction,
                                erland.webapp.gallery.entity.gallery.picture.Picture"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPictureAction) {
        Picture entry = ((ViewPictureAction)cmd).getPicture();
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
            <tr><td>Titel</td><td>
            <input type="text" name="title" size="30" value="<%=entry!=null?entry.getTitle():""%>">
            </td></tr>
            <tr><td>Beskrivning</td><td>
            <textarea class="normal" name="description" cols="80" rows="15" wrap="virtual"><%=entry!=null?entry.getDescription():""%></textarea>
            </td></tr>
            <tr><td>Liten bild</td><td>
            <input type="text" name="image" size="80" value="<%=entry!=null?entry.getImage():""%>">
            </td></tr>
            <tr><td>Stor bild</td><td>
            <input type="text" name="link" size="80" value="<%=entry!=null?entry.getLink():""%>">
            </td></tr>
            <tr><td>Officiell</td><td>
            <input type="checkbox" name="official" value="true" <%=(entry!=null && entry.getOfficial().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchgalleryentries<%=entry!=null?"&gallery="+entry.getGallery():"&gallery="+request.getParameter("gallery")%>&start=0&max=9'">
            </td></tr>
            </table>
        </form>
        <%
    }
%>
