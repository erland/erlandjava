<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.dirgallery.gallery.ViewGalleryInterface,
                                erland.webapp.dirgallery.gallery.GalleryInterface,
                                erland.webapp.dirgallery.HTMLEncoder,
                                erland.webapp.usermgmt.User"%>


<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryInterface) {
        GalleryInterface gallery = ((ViewGalleryInterface)cmd).getGallery();
        if(gallery!=null) {
            int noOfThumbnail = gallery.getNumberOfThumbnailsPerRow().intValue();
            if(noOfThumbnail<=0) {
                noOfThumbnail=3;
            }
            %>
            <jsp:include page="gallerycategoryupdatelinks.jsp"/>

            <table width="450" class="no-border">
            <tr><td colspan="<%=noOfThumbnail%>"><p class="title"><%=gallery.getTitle()%></p></td></tr>
            <tr><td colspan="<%=noOfThumbnail%>"><p class="normal"><%=HTMLEncoder.encode(gallery.getDescription())%></p></td></tr>
            <tr><td colspan="<%=noOfThumbnail%>"><a class="bold-link" href="portal?do=viewgallery&gallery=<%=gallery.getId()%>" target="_blank">Öppna i nytt fönster</a></td></tr>

            <jsp:include page="viewgallerypicturepart.jsp">
                <jsp:param name="writable" value="true"/>
            </jsp:include>
            <tr><td colspan="<%=noOfThumbnail%>"><jsp:include page="prevnextbuttons.jsp"/></td></tr>
            </table>
            <%
        }
    }
%>