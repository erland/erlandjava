<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.gallery.Gallery,
                                erland.webapp.gallery.gallery.ViewGalleryCommand,
                                erland.webapp.gallery.gallery.category.ViewCategoryCommand,
                                erland.webapp.gallery.gallery.category.Category"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewCategoryCommand) {
        Category category = ((ViewCategoryCommand)cmd).getCategory();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editcategory">
            <input type="hidden" name="gallery" value="<%=category!=null?category.getGallery().toString():request.getParameter("gallery")%>">
            <table>
            <%
            if(category!=null && category.getCategory()!=null) {
                %>
                <input type="hidden" name="category" value="<%=category.getCategory()%>">
                <%
            }
            %>
            <tr><td>Namn</td><td>
            <input type="text" name="name" value="<%=category!=null?category.getName():""%>">
            </td></tr>
            <tr><td>Visa i officiellt bildarkiv</td><td>
            <input type="checkbox" name="visible" value="true" <%=(category!=null && category.getOfficialVisible().booleanValue())?"checked":""%>>
            (Uppdatera alltid
            <input type="checkbox" name="forcedofficial" value="true">)
            </td></tr>
            <tr><td>Officiell</td><td>
            <input type="checkbox" name="official" value="true" <%=(category!=null && category.getOfficial().booleanValue())?"checked":""%>>
            (Uppdatera alltid
            <input type="checkbox" name="forcedofficial" value="true">)
            </td></tr>
            <tr><td>Alltid officiell</td><td>
            <input type="checkbox" name="alwaysofficial" value="true" <%=(category!=null && category.getOfficialAlways().booleanValue())?"checked":""%>>
            (Uppdatera alltid
            <input type="checkbox" name="forcedofficial" value="true">)
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchgalleryentries<%=category!=null?"&gallery="+category.getGallery():""%><%=category!=null?"&category="+category.getCategory():""%>&start=0&max=9'">
            </td></tr>
            </table>
        </form>
        <%
    }
%>
