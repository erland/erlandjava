<%@ page session="true" import="erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.diary.gallery.ViewGalleriesInterface,
                                erland.webapp.diary.gallery.Gallery"%>
<%
    CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
    if(cmd instanceof ViewGalleriesInterface) {
        %>
        <table class="no-border">
        <%
        Gallery[] galleries = ((ViewGalleriesInterface)cmd).getGalleries();
        String user = request.getParameter("user");
        for (int i = 0; i < galleries.length; i++) {
            Gallery gallery = galleries[i];
            %>
            <tr>
            <td class="sub-menu"></td>
            <td>
            <a href="portal?do=searchgalleryentriesguest<%=user!=null?"&user="+user:""%>&gallery=<%=gallery.getId()%>" class="bold-link"><%=gallery.getTitle()%></a>
            </td>
            </tr>
            <%
        }
        %>
        </table>
        <%
    }
%>
