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
        <jsp:include page="menugalleriescommon.jsp">
            <jsp:param name="indentgalleries" value="false"/>
            <jsp:param name="searchcategoriescmd" value="searchcategoriesguest"/>
        </jsp:include>
        </table>
        <%
    }
%>
