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
        <tr><td align="center" colspan="3">
        <a align="center" class="bold-link" href="portal?do=loadimage&gallery=<%=request.getParameter("gallery")%>&image=<%=request.getParameter("image")%><%=(user!=null?"&user="+user:"")%>" target="_blank"><img src="portal?do=loadimage&gallery=<%=request.getParameter("gallery")%>&image=<%=request.getParameter("image")%><%=(user!=null?"&user="+user:"")%>" width="400" align="center" border="0"></img><br><div align="center"><%=entry.getTitle()!=null?entry.getTitle():""%></div></a>
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