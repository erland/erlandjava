<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.gallery.Gallery,
                                erland.webapp.diary.gallery.ViewGalleryCommand"%>

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
                <input type="hidden" name="gallery" value="<%=gallery.getId()%>">
                </td></tr>
                <%
            }
            %>
            <tr><td>Titel</td><td>
            <input type="text" name="title" value="<%=gallery!=null?gallery.getTitle():""%>">
            </td></tr>
            <tr><td>Beskrivning</td><td>
            <textarea name="description" cols="80" rows="15" wrap="virtual"><%=gallery!=null?gallery.getDescription():""%></textarea>
            </td></tr>
            <tr><td>Officiell</td><td>
            <input type="checkbox" name="official" value="true" <%=(gallery!=null && gallery.getOfficial().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchgalleryentries<%=gallery!=null?"&gallery="+gallery.getId():""%>'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
