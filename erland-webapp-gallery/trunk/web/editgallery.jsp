<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.gallery.Gallery,
                                erland.webapp.gallery.gallery.ViewGalleryCommand,
                                erland.webapp.gallery.gallery.category.Category,
                                erland.webapp.gallery.gallery.category.ViewCategoriesInterface"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryCommand) {
        Gallery gallery = ((ViewGalleryCommand)cmd).getGallery();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editgallery">
            <table>
            <%
            if(gallery!=null && gallery.getId()!=null) {
                %>
                <tr><td>Id</td><td>
                <input type="hidden" name="id" value="<%=gallery.getId()%>"><a class="normal"><%=gallery.getId()%></a>
                <input type="hidden" name="gallery" value="<%=gallery.getId()%>">
                </td></tr>
                <%
            }
            %>
            <tr><td>Title</td><td>
            <input type="text" name="title" value="<%=gallery!=null?gallery.getTitle():""%>">
            </td></tr>
            <tr><td>Description</td><td>
            <textarea name="description" cols="80" rows="15" wrap="virtual"><%=gallery!=null?gallery.getDescription():""%></textarea>
            </td></tr>
            <%
            if(gallery!=null) {
                %>
                <tr><td>Top category</td><td>
                <select name="topcategory" size="1">
                <option value="" <%=gallery.getTopCategory()==null||gallery.getTopCategory().intValue()==0?"selected":""%>>None</option>
                <%
                Category[] categories = ((ViewCategoriesInterface)cmd).getCategories();
                for (int i = 0; i < categories.length; i++) {
                    Category category = categories[i];
                    %>
                    <option value=<%=category.getCategory()%> <%=category.getCategory().equals(gallery.getTopCategory())?"selected":""%>><%=category.getName()%></option>
                    <%
                }
                %>
                </select>
                </td></tr>
                <%
            }
            %>
            <tr><td>Official</td><td>
            <input type="checkbox" name="official" value="true" <%=(gallery!=null && gallery.getOfficial().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Save">
            <input type="button" value="Cancel" onClick="window.location='portal?do=searchgalleryentries<%=gallery!=null?"&gallery="+gallery.getId():""%>&start=0&max=9'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
