<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.gallery.Gallery,
                                erland.webapp.diary.gallery.ViewGalleryCommand,
                                erland.webapp.gallery.gallery.ViewGalleriesInterface,
                                erland.webapp.gallery.gallery.GalleryInterface"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryCommand) {
        Gallery gallery = ((ViewGalleryCommand)cmd).getGallery();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editgallery">
            <table>
            <%
            if(gallery!=null && gallery.getId()!=null) {
                %>
                <tr><td>Id</td><td>
                <input type="hidden" name="id" value="<%=gallery.getId()%>"><a class="normal"><%=gallery.getId()%></a>
                </td></tr>
                <%
            }
            %>
            <tr><td>Titel</td><td>
            <input type="text" name="title" value="<%=gallery!=null?gallery.getTitle():""%>">
            </td></tr>
            <tr><td>Beskrivning</td><td>
            <textarea class="normal" name="description" cols="80" rows="15" wrap="virtual"><%=gallery!=null?gallery.getDescription():""%></textarea>
            </td></tr>
            <tr><td>Officiell</td><td>
            <input type="checkbox" name="official" value="true" <%=(gallery!=null && gallery.getOfficial().booleanValue())?"checked":""%>>
            </td></tr>
            <%
            CommandInterface cmdGalleries = (CommandInterface) request.getAttribute("galleriescmd");
            if(cmdGalleries instanceof ViewGalleriesInterface) {
                %>
                <tr><td>Hämta bilder från</td><td>
                <select name="gallery" size="1">
                <option value="" <%=gallery==null||gallery.getGallery()==null||gallery.getGallery().intValue()==0?"selected":""%>>Lägg upp bilder manuellt</option>
                <%
                GalleryInterface[] galleries = ((ViewGalleriesInterface)cmdGalleries).getGalleries();
                for (int i = 0; i < galleries.length; i++) {
                    GalleryInterface entry = galleries[i];
                    %>
                    <option value="<%=entry.getId()%>" <%=(gallery!=null && entry.getId().equals(gallery.getGallery()))?"selected":""%>><%=entry.getTitle()%></option>
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
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchgalleryentries<%=gallery!=null?"&gallery="+gallery.getId():""%>'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
