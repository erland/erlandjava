<%@ page session="true" import="erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.dirgallery.gallery.ViewGalleriesInterface"%>
<%
    CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
    if(cmd instanceof ViewGalleriesInterface) {
        %>
        <table class="no-border">
        <jsp:include page="menugalleriescommon.jsp">
            <jsp:param name="searchcmd" value="searchgalleryentriesguest"/>
        </jsp:include>
        </table>
        <%
    }
%>
