<%@ page import="erland.webapp.common.CommandInterface,
                 erland.webapp.common.ServletParameterHelper,
                 erland.webapp.dirgallery.gallery.ViewGalleryInterface,
                 erland.webapp.dirgallery.gallery.GalleryInterface,
                 erland.webapp.dirgallery.gallery.picture.ViewPicturesInterface,
                 erland.webapp.dirgallery.gallery.picture.Picture"%>
 <%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryInterface) {
        GalleryInterface gallery = ((ViewGalleryInterface)cmd).getGallery();
        if(gallery!=null) {
            int noOfRows = gallery.getNumberOfThumbnailsPerRow().intValue();
            if(noOfRows<=0) {
                noOfRows=3;
            }
            int thumbnailWidth = gallery.getThumbnailWidth().intValue();
            if(cmd instanceof ViewPicturesInterface) {
                Picture[] entries = ((ViewPicturesInterface)cmd).getPictures();
                for (int i = 0; i < entries.length; i++) {
                    Picture entry = entries[i];
                    String comment = null;
                    if(gallery.getUseTooltip().booleanValue()) {
                        comment = ((ViewPicturesInterface)cmd).getPictureComment(entry);
                    }
                    if(gallery.getShowPictureNameInTooltip().booleanValue()) {
                        String name = null;
                        if(gallery.getUseShortPictureNames().booleanValue()) {
                            name = entry.getShortName();
                        }else {
                            name = entry.getName();
                        }
                        if(comment!=null && comment.length()>0) {
                            comment+="\n\n"+name;
                        }else {
                            comment=name;
                        }
                    }
                    if(i%noOfRows==0) {
                        %>
                        <tr>
                        <%
                    }
                    %>
                    <td valign="bottom" align="center">
                    <%
                    String writable = request.getParameter("writable");
                    if(writable!=null && writable.equalsIgnoreCase("true")) {
                        %>
                        <a class="bold-link" href="portal?do=newpicturecomment&gallery=<%=entry.getGallery()%>&id=<%=entry.getId()%>">Kommentar</a>
                        <%
                    }
                    %>
                    <%
                    String thumbnailCmd = "loadthumbnail";
                    if(gallery.getTypeOfFiles().intValue()==GalleryInterface.MOVIEFILES) {
                        int cols = gallery.getNumberOfMovieThumbnailColumns().intValue();
                        int rows = gallery.getNumberOfMovieThumbnailRows().intValue();
                        thumbnailCmd = "loadmoviethumbnail"+(cols>0?"&cols="+cols:"")+(rows>0?"&rows="+rows:"");
                    }
                    String image = "portal?do="+thumbnailCmd+(thumbnailWidth>0?"&width="+thumbnailWidth:"")+"&gallery="+entry.getGallery()+"&image="+entry.getId()+(gallery.getThumbnailCompression().floatValue()>0?"&compression="+gallery.getThumbnailCompression():"")+(gallery.getUseThumbnailCache().booleanValue()?"":"&usecache=false");
                    String linkCmd = "viewpictureguest";
                    if(gallery.getOriginalDownloadable().booleanValue()) {
                        if(gallery.getTypeOfFiles().intValue()==GalleryInterface.MOVIEFILES) {
                            linkCmd = "loadimage";
                        }else {
                            linkCmd = "viewpicture";
                        }
                    }else if(!gallery.getShowDownloadLinks().booleanValue()) {
                        linkCmd = null;
                    }
                    String link = "do=viewpictureguest&gallery="+request.getParameter("gallery")+"&image="+entry.getId();
                    if(gallery.getShowDownloadLinks().booleanValue()) {
                        %>
                        <a class="bold-link" href="portal?<%=link%>&width=640" target="_blank" title="640 x 480">640</a>
                        <a class="bold-link" href="portal?<%=link%>&width=800" target="_blank" title="800 x 600">800</a>
                        <a class="bold-link" href="portal?<%=link%>&width=1024" target="_blank" title="1024 x 768">1024</a>
                        <a class="bold-link" href="portal?<%=link%>&width=1280" target="_blank" title="1280 x 1024">1280</a><br>
                        <%
                    }
                    if(!gallery.getOriginalDownloadable().booleanValue()) {
                        link+="&width=800";
                    }
                    %>
                    <a class="bold-link<%=linkCmd==null?"-selected":""%>" <%=linkCmd!=null?"href=\"portal?"+ServletParameterHelper.replaceParameter(link,"do",linkCmd)+"\" target=\"_blank\"":""%> <%=comment!=null?"title=\""+comment+"\"":""%>><img src="<%=image%>" border="0"></img><%
                    if(gallery.getShowPictureNames().booleanValue()) {
                        String name = null;
                        if(gallery.getUseShortPictureNames().booleanValue()) {
                            name = entry.getShortName();
                        }else {
                            name = entry.getName();
                        }
                        if(gallery.getMaxPictureNameLength().intValue()>0 && name.length()>gallery.getMaxPictureNameLength().intValue()) {
                            name = name.substring(0,gallery.getMaxPictureNameLength().intValue()-3)+"...";
                        }
                        %><br><div align="center"><%=name!=null?name:""%></div><%
                    }
                    %></a>
                    </td>
                    <%
                    if(i%noOfRows==(noOfRows-1) || i==entries.length-1) {
                        %>
                        </tr>
                        <%
                    }
                }
            }
        }
    }
%>
