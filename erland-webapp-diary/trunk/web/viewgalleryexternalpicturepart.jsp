<%@ page session="true" import="erland.webapp.common.CommandInterface,
                 erland.webapp.gallery.gallery.picture.ViewPicturesInterface,
                 erland.webapp.gallery.gallery.picture.Picture"%>
<%
CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
if(cmd instanceof ViewPicturesInterface) {
    Picture[] entries = ((ViewPicturesInterface)cmd).getPictures();
    for (int i = 0; i < entries.length; i++) {
        Picture entry = entries[i];
        if(i%3==0) {
            %>
            <tr>
            <%
        }
        %>
        <td>
        <%
        String writable = request.getParameter("writable");
        if(writable!=null && writable.equalsIgnoreCase("true")) {
            %>
            <a href="portal?do=newpicture&gallery=<%=entry.getGallery()%>&id=<%=entry.getId()%>" class="bold-link">Uppdatera</a>
            <a href="portal?do=removepicture&gallery=<%=entry.getGallery()%>&id=<%=entry.getId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a><br>
            <%
        }
        String user = request.getParameter("user");
        %>
        <a class="bold-link" href="portal?do=loadthumbnail&gallery=<%=entry.getGallery()%>&image=<%=entry.getId()%><%=user!=null?"&user="+user:""%>&usecache=false&width=640" target="_blank" title="<%=entry.getDescription()!=null?entry.getDescription():""%>"><img src="portal?do=loadthumbnail&gallery=<%=entry.getGallery()%>&image=<%=entry.getId()%><%=user!=null?"&user="+user:""%>" border="0"></img><br><div align="center"><%=entry.getTitle()!=null?entry.getTitle():""%></div></a>
        </td>
        <%
        if(i%3==2 || i==entries.length-1) {
            %>
            </tr>
            <%
        }
    }
}
%>