<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.act.gallery.ViewGalleryInterface,
                                erland.webapp.gallery.entity.gallery.Gallery,
                                erland.webapp.gallery.entity.gallery.GalleryInterface,
                                erland.webapp.common.html.HTMLEncoder"%>


<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryInterface) {
        GalleryInterface gallery = ((ViewGalleryInterface)cmd).getGallery();
        if(gallery!=null) {
            String user = request.getParameter("user");
            %>
            <table width="600" class="no-border">
            <tr><td colspan="3"><p class="title"><%=gallery.getTitle()%></p></td></tr>
            <tr><td colspan="3"><p class="normal"><%=HTMLEncoder.encode(gallery.getDescription())%></p></td></tr>
            <tr><td colspan="3"><a href="portal?do=newsearchgalleryentriesadvancedguest&gallery=<%=gallery.getId()%>&user=<%=user%>&backcmd=<%=request.getParameter("do")%>" class="bold-link">Sök</a></td></tr>

            <jsp:include page="prevnextbuttons.jsp"/>
            <jsp:include page="viewgallerypicturepart.jsp">
                <jsp:param name="writable" value="false"/>
            </jsp:include>
            </table>
            <%
        }
    }
%>
