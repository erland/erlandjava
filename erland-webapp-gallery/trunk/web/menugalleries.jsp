<%@ page session="true" import="erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.act.gallery.ViewGalleriesInterface,
                                erland.webapp.gallery.entity.gallery.Gallery,
                                erland.webapp.gallery.act.gallery.category.ViewCategoriesInterface,
                                erland.webapp.gallery.entity.gallery.category.Category"%>
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
        <jsp:include page="menugalleriescommon.jsp">
            <jsp:param name="indentgalleries" value="true"/>
            <jsp:param name="searchcategoriescmd" value="searchcategories"/>
        </jsp:include>
        </table>
        <%
    }
%>
