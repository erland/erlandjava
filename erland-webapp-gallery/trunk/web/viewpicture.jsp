<%@ page import="erland.webapp.common.CommandInterface,
                 erland.webapp.gallery.gallery.picture.ViewPictureInterface,
                 erland.webapp.gallery.gallery.picture.Picture"%>
<%@ page session="true" %>
<table class="no-border">
<jsp:include page="prevnextbuttons.jsp"/>
<%
    String user = request.getParameter("user");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPictureInterface) {
        Picture entry = (Picture) ((ViewPictureInterface)cmd).getPicture();
        %>
        <tr><td align="center" colspan="2">
        <a align="center" class="bold-link" href="portal?do=loadimage&gallery=<%=request.getParameter("gallery")%>&image=<%=request.getParameter("image")%><%=(user!=null?"&user="+user:"")%>" target="_blank"><img src="portal?do=loadimage&gallery=<%=request.getParameter("gallery")%>&image=<%=request.getParameter("image")%><%=(user!=null?"&user="+user:"")%>" width="400" align="center" border="0" galleryimg="no"></img><br><div align="center"><%=entry.getTitle()!=null?entry.getTitle():""%></div></a>
        </td><td halign="left" valign="top">
        Ladda ned i storlek:
        <br><a halign="left" valign="top" class="bold-link" href="portal?do=loadimage&gallery=<%=request.getParameter("gallery")%>&image=<%=request.getParameter("image")%><%=(user!=null?"&user="+user:"")%>" target="_blank">Original</a>
        <br><a halign="left" valign="top" class="bold-link" href="portal?do=loadthumbnail&width=1280&usecache=false&gallery=<%=request.getParameter("gallery")%>&image=<%=request.getParameter("image")%><%=(user!=null?"&user="+user:"")%>" target="_blank">1280x1024</a>
        <br><a halign="left" valign="top" class="bold-link" href="portal?do=loadthumbnail&width=1024&usecache=false&gallery=<%=request.getParameter("gallery")%>&image=<%=request.getParameter("image")%><%=(user!=null?"&user="+user:"")%>" target="_blank">1024x768</a>
        <br><a halign="left" valign="top" class="bold-link" href="portal?do=loadthumbnail&width=800&usecache=false&gallery=<%=request.getParameter("gallery")%>&image=<%=request.getParameter("image")%><%=(user!=null?"&user="+user:"")%>" target="_blank">800x600</a>
        <br><a halign="left" valign="top" class="bold-link" href="portal?do=loadthumbnail&width=640&usecache=false&gallery=<%=request.getParameter("gallery")%>&image=<%=request.getParameter("image")%><%=(user!=null?"&user="+user:"")%>" target="_blank">640x480</a>
        </td></tr>
        <tr><td colspan="3">
        <p class="normal"><%=entry.getDescription()!=null?entry.getDescription():""%></p>
        </td></tr>
        <%
    }
%>
<tr><td colspan="3">
<jsp:include page="viewmetadata.jsp"/>
</td></tr>
</table>