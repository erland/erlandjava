<%@ page session="true" import="erland.webapp.gallery.gallery.picture.ViewPictureInterface,
                 erland.webapp.gallery.gallery.picture.Picture,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.common.ServletParameterHelper"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPictureInterface) {
        Picture picture = ((ViewPictureInterface)cmd).getPicture();
        if(picture!=null) {
            String width = request.getParameter("width");
            %>
            <table>
            <tr><td>
            <a class="bold-link<%=width.equals("640")?"-selected":""%>" href="portal?<%=ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"width","640")%>" title="640 x 480">640</a>
            <a class="bold-link<%=width.equals("800")?"-selected":""%>" href="portal?<%=ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"width","800")%>" title="800 x 600">800</a>
            <a class="bold-link<%=width.equals("1024")?"-selected":""%>" href="portal?<%=ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"width","1024")%>" title="1024 x 768">1024</a>
            <a class="bold-link<%=width.equals("1280")?"-selected":""%>" href="portal?<%=ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"width","1280")%>" title="1280 x 1024">1280</a>
            <%
                String user = request.getParameter("user");
                if(user!=null && user.length()==0) {
                    user = null;
                }
                String compression = request.getParameter("compression");
                if(compression!=null && compression.length()==0) {
                    compression=null;
                }
            %>
            <br><img src="portal?do=loadthumbnail&usecache=false&gallery=<%=picture.getGallery()%>&image=<%=picture.getId()%><%=(user!=null?"&user="+user:"")%><%=compression!=null?"&compression="+compression:""%>&width=<%=request.getParameter("width")%>" align="center" border="0"></img>
            </td></tr>
            <tr><td>
            <p class="title"><%=picture.getTitle()%></p>
            <p class="normal"><%=picture.getDescription()%></p>
            </td></tr>
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
                <a class="bold-link" href="portal?<%=ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"showmetadata","true")%>">Visa bildinformation</a>
                </td><tr>
                <%
            }
            %>
            </table>
            <%
        }
    }
%>
