<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.common.ServletParameterHelper,
                                erland.webapp.dirgallery.gallery.picture.ViewPictureInterface,
                                erland.webapp.dirgallery.gallery.picture.Picture"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPictureInterface) {
        Picture picture = ((ViewPictureInterface)cmd).getPicture();
        if(picture!=null) {
            %><table class="no-border"><tr><td>
            <%
                String user = request.getParameter("user");
                if(user!=null && user.length()==0) {
                    user = null;
                }
                String compression = request.getParameter("compression");
                if(compression!=null && compression.length()==0) {
                    compression=null;
                }
            %><img src="portal?do=loadimage&gallery=<%=picture.getGallery()%>&image=<%=picture.getId()%><%=(user!=null?"&user="+user:"")%><%=compression!=null?"&compression="+compression:""%>" align="center" border="0"></img>
            </td></tr>
            <%
            String comment = ((ViewPictureInterface)cmd).getComment();
            %>
            <tr><td><p class="title"><%=picture.getShortName()%></p></td></tr>
            <tr><td><p class="normal"><%=comment!=null?comment:""%></p><td></tr>
            <%
            String showMetadata = request.getParameter("showmetadata");
            if(showMetadata!=null && showMetadata.equalsIgnoreCase("true")) {
                %>
                <tr><td>
                <a class="bold-link" href="portal?<%=ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"showmetadata","false")%>">Göm bildinformation</a>
                </td><tr>
                <tr><td>
                <jsp:include page="viewmetadata.jsp"/>
                </td></tr>
                <%
            }else {
                %>
                <tr><td>
                <a class="bold-link" href="portal?<%=ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"showmetadata","true")%>">Visa extra bildinformation</a>
                </td><tr>
                <%
            }
            %>
            </table>
            <%
        }
    }
%>
