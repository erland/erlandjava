<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.common.ServletParameterHelper,
                                erland.webapp.dirgallery.gallery.picture.ViewPictureInterface,
                                erland.webapp.dirgallery.gallery.picture.Picture,
                                erland.webapp.dirgallery.gallery.GalleryInterface"%>
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
                String compression = request.getParameter("compression");
                if(compression!=null && compression.length()==0) {
                    compression=null;
                }
                String thumbnailCmd = "loadthumbnail";
                if(picture.getTypeOfFile().intValue()==Picture.MOVIEFILE) {
                    String colsString = request.getParameter("cols");
                    int cols;
                    int rows;
                    int w = 0;
                    if(width!=null && width.length()>0) {
                        w = Integer.valueOf(width).intValue();
                    }
                    if(w<=640) {
                        cols = 4;
                        rows = 3;
                    }else if(w<=800) {
                        cols = 5;
                        rows = 4;
                    }else if(w<=1024) {
                        cols = 7;
                        rows = 5;
                    }else if(w<=1280) {
                        cols = 8;
                        rows = 7;
                    }else {
                        cols = 10;
                        rows = 8;
                    }
                    if(colsString!=null && colsString.length()>0) {
                        cols = Integer.valueOf(colsString).intValue();
                    }
                    String rowsString = request.getParameter("rows");
                    if(rowsString!=null && rowsString.length()>0) {
                        rows = Integer.valueOf(rowsString).intValue();
                    }
                    thumbnailCmd = "loadmoviethumbnail"+(cols>0?"&cols="+cols:"")+(rows>0?"&rows="+rows:"");
                }
            %>
            <br><img src="portal?do=<%=thumbnailCmd%>&usecache=false&gallery=<%=picture.getGallery()%>&image=<%=picture.getId()%><%=compression!=null?"&compression="+compression:""%>&width=<%=request.getParameter("width")%>" align="center" border="0"></img>
            </td></tr>
            <%
            String comment = ((ViewPictureInterface)cmd).getComment();
            %>
            <tr><td><p class="title"><%=picture.getShortName()%></p><td></tr>
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
