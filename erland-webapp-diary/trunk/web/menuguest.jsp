<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                java.text.SimpleDateFormat,
                                java.text.DateFormat,
                                erland.webapp.diary.diary.DiaryEntryInterface,
                                java.util.Calendar,
                                java.util.Date,
                                erland.webapp.diary.HTMLEncoder,
                                erland.webapp.diary.diary.ViewEntriesInterface,
                                erland.webapp.diary.inventory.ViewInventoryEntriesInterface,
                                erland.webapp.diary.diary.ViewDiariesInterface,
                                erland.webapp.diary.gallery.ViewGalleriesInterface"%>
<table class="no-border">
<tr><td class="left-margin"></td>
<td>
<jsp:include page="menudiary.jsp"/>
</td>
<tr><td class="left-margin"></td><td>&nbsp</td></tr>
<tr><td class="left-margin"></td>
<td>
<a href="portal?do=searchactiveinventoryentriesguest&user=<%=request.getParameter("user")%>" class="bold-link">Fiskar och Växter</a>
</td>
</tr>
    <%
        String user = request.getParameter("user");
        CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    %>
    <tr>
    <td class="left-margin"></td>
    <td colspan="3">
    <a href="portal?do=searchgalleriesguest<%=user!=null?"&user="+user:""%>" class="bold-link">Bildarkiv</a>
    </td>
    </tr>
    <%
        CommandInterface galleriesCmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
        if(galleriesCmd instanceof ViewGalleriesInterface) {
            %>
            <tr>
            <td class="left-margin"></td>
            <td colspan="3"><jsp:include page="menugalleriesguest.jsp" /></td>
            </tr>
            <%
        }
    %>
    <tr>
    <td class="left-margin"></td>
    <td colspan="3">
    <a href="portal?do=searchdiariesguest<%=user!=null?"&user="+user:""%>" class="bold-link">Dagböcker</a>
    </td>
    </tr>
    <%
        if(cmd instanceof ViewDiariesInterface) {
            %>
            <tr>
            <td class="left-margin"></td>
            <td colspan="3"><jsp:include page="menudiariesguest.jsp" /></td>
            </tr>
            <%
        }
    %>
</table>
