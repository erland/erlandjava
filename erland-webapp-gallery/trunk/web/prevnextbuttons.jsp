<%@ page import="erland.webapp.gallery.gallery.picture.ViewPicturePageInterface,
                 erland.webapp.common.CommandInterface,
                 erland.webapp.common.ServletParameterHelper"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("prevnext");
    if(cmd instanceof ViewPicturePageInterface) {
        %>
        <tr>
        <%
        Integer prev = ((ViewPicturePageInterface)cmd).getPreviousPage();
        Integer next =((ViewPicturePageInterface)cmd).getNextPage();
        if(prev!=null) {
            %>
            <td align="left"><a class="bold-link" href="portal?<%=ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"start",prev.toString())%>">&lt;-Prev</a></td>
            <%
        }else {
            %>
            <td></td>
            <%
        }
        String upCmd = request.getParameter("backcmd");
        if(upCmd!=null && upCmd.length()>0) {
            String tmp = ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"max","9");
            String withBack = ServletParameterHelper.replaceParameter(tmp,"do",request.getParameter("backcmd"));
            String link = ServletParameterHelper.removeParameter(withBack,"backcmd");
            %>
            <td align="center"><a class="bold-link" href="portal?<%=link%>">Up</a></td>
            <%
        }else {
            %>
            <td></td>
            <%
        }
        if(next!=null) {
            %>
            <td align="right"><a class="bold-link" href="portal?<%=ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"start",next.toString())%>">Next-&gt;</a></td>
            <%
        }else {
            %>
            <td></td>
            <%
        }
        %>
        </tr>
        <%
    }
%>
