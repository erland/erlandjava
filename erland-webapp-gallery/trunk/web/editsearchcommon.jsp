<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.gallery.category.ViewCategoriesInterface,
                                erland.webapp.gallery.gallery.category.Category"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    String user = request.getParameter("user");
    if(cmd instanceof ViewCategoriesInterface) {
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="<%=request.getParameter("searchcmd")%>">
            <%
            if(user!=null) {
                %>
                <input type="hidden" name="user" value="<%=request.getParameter("user")%>">
                <%
            }
            %>
            <input type="hidden" name="start" value="0">
            <input type="hidden" name="max" value="9">
            <table>
            <input type="hidden" name="gallery" value="<%=request.getParameter("gallery")%>">
            <tr><td>Start date</td><td>
            <input type="text" name="dateafter" value="">
            </td></tr>
            <tr><td>End date</td><td>
            <input type="text" name="datebefore" value="">
            </td></tr>
            <tr><td>Categories</td><td>
            <select name="categories" size="10" multiple>
            <%
            Category[] categories = ((ViewCategoriesInterface)cmd).getCategories();
            for (int i = 0; i < categories.length; i++) {
                Category category = categories[i];
                %>
                <option value=<%=category.getCategory()%>><%=category.getName()%></option>
                <%
            }
            %>
            </select>
            </td></tr>
            <tr><td>All categories required</td><td>
            <input type="checkbox" name="allcategories" value="true">
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Search">
            <input type="button" value="Cancel" onClick="window.location='portal?do=<%=request.getParameter("backcmd")%>&gallery=<%=request.getParameter("gallery")%><%=(user!=null?"&user="+user:"")%>&start=0&max=9'">
            </td></tr>
            </table>
        </form>
        <%
    }
%>
