<%@ page session="true" import="erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.gallery.ViewGalleriesInterface,
                                erland.webapp.gallery.gallery.Gallery,
                                erland.webapp.gallery.gallery.category.ViewCategoriesInterface,
                                erland.webapp.gallery.gallery.category.Category"%>
<%
    CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
    if(cmd instanceof ViewGalleriesInterface) {
        %>
        <table class="no-border">
        <tr>
        <td class="sub-menu"></td>
        <td>
        <a href="portal?do=newgallery" class="bold-link">Add new</a>
        </td>
        </tr>
        <jsp:include page="menugalleriescommon.jsp">
            <jsp:param name="searchcategoriescmd" value="searchcategories"/>
        </jsp:include>
        </table>
        <%
    }
%>
