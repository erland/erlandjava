<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.gallery.Gallery,
                                erland.webapp.diary.gallery.ViewGalleryCommand,
                                erland.webapp.diary.gallery.ViewGalleryEntryCommand,
                                erland.webapp.diary.gallery.GalleryEntry"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryEntryCommand) {
        GalleryEntry entry = ((ViewGalleryEntryCommand)cmd).getEntry();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editgalleryentry">
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
            <input type="text" name="title" value="<%=entry!=null?entry.getTitle():""%>">
            </td></tr>
            <tr><td>Beskrivning</td><td>
            <textarea class="normal" name="description" cols="80" rows="15" wrap="virtual"><%=entry!=null?entry.getDescription():""%></textarea>
            </td></tr>
            <tr><td>Liten bild (150 pixel bred)</td><td>
            <input type="text" name="image" value="<%=entry!=null?entry.getImage():""%>">
            </td></tr>
            <tr><td>Stor bild</td><td>
            <input type="text" name="link" value="<%=entry!=null?entry.getLink():""%>">
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchgalleryentries<%=entry!=null?"&gallery="+entry.getGallery():"&gallery="+request.getParameter("gallery")%>'">
            </td></tr>
            </table>
        </form>
        <%
    }
%>
