<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.entity.gallery.category.Category,
                                erland.webapp.gallery.act.gallery.category.ViewCategoriesInterface,
                                erland.webapp.gallery.act.account.ViewUserAccountInterface,
                                java.util.Arrays,
                                java.util.Collection,
                                erland.webapp.gallery.act.gallery.*,
                                erland.webapp.gallery.entity.gallery.GalleryInterface"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryAction) {
        GalleryInterface gallery = ((ViewGalleryAction)cmd).getGallery();
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
            <tr><td>Titel</td><td>
            <input type="text" name="title" value="<%=gallery!=null?gallery.getTitle():""%>">
            </td></tr>
            <tr><td>Beskrivning</td><td>
            <textarea class="normal" name="description" cols="80" rows="15" wrap="virtual"><%=gallery!=null?gallery.getDescription():""%></textarea>
            </td></tr>
            <%
            if(gallery!=null) {
                %>
                <tr><td>Top kategori</td><td>
                <select name="topcategory" size="1">
                <option value="" <%=gallery.getTopCategory()==null||gallery.getTopCategory().intValue()==0?"selected":""%>>None</option>
                <%
                Category[] categories = ((ViewCategoriesInterface)cmd).getCategories();
                for (int i = 0; i < categories.length; i++) {
                    Category category = categories[i];
                    %>
                    <option value="<%=category.getCategory()%>" <%=category.getCategory().equals(gallery.getTopCategory())?"selected":""%>><%=category.getName()%></option>
                    <%
                }
                %>
                </select>
                </td></tr>
                <%
            }
            %>
            <tr><td>Officiellt</td><td>
            <input type="checkbox" name="official" value="true" <%=(gallery!=null && gallery.getOfficial().booleanValue())?"checked":""%>>
            </td></tr>
            <%
            if(cmd instanceof ViewGalleriesInterface) {
                %>
                <tr><td>Hämta bilder från</td><td>
                <select name="referencedgallery" size="1">
                <option value="" <%=(gallery==null || gallery.getReferencedGallery()==null || gallery.getReferencedGallery().intValue()==0)?"selected":""%>>None</option>
                <%
                GalleryInterface[] galleries = ((ViewGalleriesInterface)cmd).getGalleries();
                for (int i = 0; i < galleries.length; i++) {
                    GalleryInterface g = galleries[i];
                    %>
                    <option value="<%=g.getId()%>" <%=(gallery!=null&&g.getId().equals(gallery.getReferencedGallery()))?"selected":""%>><%=g.getTitle()%></option>
                    <%
                }
                %>
                </select>
                </td></tr>
                <%
            }
            %>
            <%
            if(gallery!=null && gallery.getReferencedGallery()!=null && gallery.getReferencedGallery().intValue()!=0 && cmd instanceof ViewVirtualGalleryInterface && cmd instanceof ViewCategoriesInterface) {
                %>
                <tr><td>Kategorier som bilden måste ha</td><td>
                <select name="categories" size="10" multiple>
                <%
                Category[] categories = ((ViewCategoriesInterface)cmd).getCategories();
                Collection requiredCategories = Arrays.asList(((ViewVirtualGalleryInterface)cmd).getRequiredCategories());
                %>
                <option value="" <%=requiredCategories.size()==0?"selected":""%>></option>
                <%
                for (int i = 0; i < categories.length; i++) {
                    Category category = categories[i];
                    %>
                    <option value="<%=category.getCategory()%>" <%=requiredCategories.contains(category.getCategory())?"selected":""%>><%=category.getName()%></option>
                    <%
                }
                %>
                </select>
                </td></tr>
                <%
            }
            %>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchgalleryentries<%=gallery!=null?"&gallery="+gallery.getId():""%>&start=0&max=9'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
