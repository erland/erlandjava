<%@ page import="erland.webapp.common.CommandInterface,
                 erland.webapp.common.ServletParameterHelper,
                 erland.webapp.dirgallery.gallery.picture.ViewPicturePageInterface"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("prevnext");
    if(cmd instanceof ViewPicturePageInterface) {
        Integer prev = ((ViewPicturePageInterface)cmd).getPreviousPage();
        Integer next =((ViewPicturePageInterface)cmd).getNextPage();
        if(prev!=null || next!=null) {
            %>
            <table width="100%" class="no-border">
            <tr>
            <%
        }
        if(prev!=null) {
            %>
            <td align="left"><a class="bold-link" href="portal?<%=ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"start",prev.toString())%>">&lt; Föregående</a></td>
            <%
        }else {
            %>
            <td>&nbsp</td>
            <%
        }
        if(next!=null) {
            %>
            <td width="*" align="right"><a class="bold-link" href="portal?<%=ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"start",next.toString())%>">Nästa &gt;</a></td>
            <%
        }else {
            %>
            <td>&nbsp</td>
            <%
        }
        if(prev!=null || next!=null) {
            %>
            </tr>
            </table>
            <%
        }
    }
%>
