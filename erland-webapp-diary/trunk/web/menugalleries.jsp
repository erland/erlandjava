<%@ page session="true" import="erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.diary.gallery.ViewGalleriesInterface,
                                erland.webapp.diary.gallery.Gallery"%>
<jsp:useBean id="user" scope="session" class="User" />

<%
    CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
    if(cmd instanceof ViewGalleriesInterface) {
        %>
        <table class="no-border">
        <tr>
        <td class="sub-menu"></td>
        <td>
        <a href="portal?do=newgallery" class="bold-link">Lägg till</a>
        </td>
        </tr>
        <%
        Gallery[] galleries = ((ViewGalleriesInterface)cmd).getGalleries();
        for (int i = 0; i < galleries.length; i++) {
            Gallery gallery = galleries[i];
            %>
            <tr>
            <td class="sub-menu"></td>
            <td>
            <a href="portal?do=searchgalleryentries&gallery=<%=gallery.getId()%>" class="bold-link"><%=gallery.getTitle()%></a>
            </td>
            </tr>
            <%
        }
        %>
        </table>
        <%
    }
%>
