<%@ page import="erland.webapp.gallery.gallery.ViewGalleryInterface,
                 erland.webapp.gallery.gallery.category.Category,
                 erland.webapp.gallery.gallery.category.ViewCategoryInterface,
                 erland.webapp.common.CommandInterface,
                 erland.webapp.gallery.gallery.Gallery"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryInterface) {
        Gallery gallery = ((ViewGalleryInterface)cmd).getGallery();
        if(gallery!=null) {
            %>
            <a href="portal?do=newgallery&id=<%=gallery.getId()%>" class="bold-link">Update</a>
            <a href="portal?do=removegallery&id=<%=gallery.getId()%>" class="bold-link" onClick="return confirm('Are you sure you want to delete this ?')">Delete</a>
            <a href="portal?do=newpicture&gallery=<%=gallery.getId()%>" class="bold-link">Add a image</a>
            <a href="portal?do=chooseimport&gallery=<%=gallery.getId()%>" class="bold-link">Import</a>
            <%
            if(cmd instanceof ViewGalleryInterface) {
                Category category = ((ViewCategoryInterface)cmd).getCategory();
                if(category!=null) {
                    %>
                    <a href="portal?do=updatecategory&gallery=<%=gallery.getId()%>&category=<%=category.getCategory()%>" class="bold-link">Update category</a>
                    <%
                }
            }
            %>
            <br>
            <%
        }
    }
%>

