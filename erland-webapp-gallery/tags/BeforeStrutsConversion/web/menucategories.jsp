<%@ page session="true" import="erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.gallery.ViewGalleriesInterface,
                                erland.webapp.gallery.gallery.Gallery,
                                erland.webapp.gallery.gallery.category.ViewCategoriesInterface,
                                erland.webapp.gallery.gallery.category.Category"%>
<%
    String indentString = request.getParameter("indent");
    int indent=0;
    if(indentString!=null&&indentString.length()>0) {
        indent = Integer.valueOf(indentString).intValue();
    }
    CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menucategories"+(indent>0?""+indent:""));
    CommandInterface subcategoriescmd = (CommandInterface) request.getSession().getAttribute("menucategories"+(indent+1));
    if(cmd instanceof ViewCategoriesInterface) {
        %>
        <table class="no-border">
        <%
        Category[] categories = ((ViewCategoriesInterface)cmd).getCategories();
        String user = request.getParameter("user");
        String command = request.getParameter("searchcategoriescmd");
        for (int i = 0; i < categories.length; i++) {
            Category category = categories[i];
            %>
            <tr>
            <%
            String indentGalleries = request.getParameter("indentgalleries");
            if(indentGalleries!=null && indentGalleries.equalsIgnoreCase("true")) {
                %>
                <td class="sub-menu"></td>
                <%
            }
            %>
            <td class="sub-menu"></td>
            <%
            for(int j=0;j<indent;j++) {
                %>
                <td class="sub-menu"></td>
                <%
            }
            String categoryString = request.getParameter("category");
            Integer currentCategoryId = null;
            String linkType = "bold-link";
            if(categoryString!=null && categoryString.length()>0) {
                currentCategoryId = Integer.valueOf(categoryString);
                if(currentCategoryId.equals(category.getCategory())) {
                    linkType = "bold-link-selected";
                }
            }
            %>
            <td nowrap>
            <a href="portal?do=<%=command%>&gallery=<%=category.getGallery()%>&category=<%=category.getCategory()%>&indent=<%=(indent+1)%><%=user!=null?"&user="+user:""%>&start=0&max=9" class="<%=linkType%>"><%=category.getName()%></a>
            </td>
            </tr>
            <%
            if(subcategoriescmd instanceof ViewCategoriesInterface) {
                Category[] subcategories = ((ViewCategoriesInterface)subcategoriescmd).getCategories();
                if(subcategories.length>0 && subcategories[0].getGallery().equals(category.getGallery()) &&
                        subcategories[0].getParentCategory().equals(category.getCategory())) {
                    %>
                    <tr>
                    <td class="left-margin"></td>
                    <td colspan="8">
                    <jsp:include page="menucategories.jsp">
                        <jsp:param name="indent" value="<%=(indent+1)%>"/>
                    </jsp:include>
                    </td>
                    </tr>
                    <%
                }
            }
        }
        %>
        </table>
        <%
    }
%>
