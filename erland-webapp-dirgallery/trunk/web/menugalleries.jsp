<%@ page session="true" import="erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.dirgallery.gallery.ViewGalleriesInterface"%>
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
            <jsp:param name="searchcmd" value="searchgalleryentries"/>
        </jsp:include>
        </table>
        <%
    }
%>
