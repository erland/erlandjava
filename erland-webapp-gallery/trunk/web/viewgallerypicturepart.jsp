<%@ page import="erland.webapp.gallery.act.gallery.picture.ViewPicturesInterface,
                 erland.webapp.gallery.entity.gallery.picture.Picture,
                 erland.webapp.common.CommandInterface,
                 erland.webapp.gallery.act.gallery.ViewGalleryInterface,
                 erland.webapp.gallery.entity.gallery.Gallery,
                 erland.webapp.common.ServletParameterHelper,
                 erland.webapp.gallery.entity.gallery.GalleryInterface"%>
 <%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryInterface) {
        GalleryInterface gallery = ((ViewGalleryInterface)cmd).getGallery();
        if(gallery!=null) {
            if(cmd instanceof ViewPicturesInterface) {
                String user = request.getParameter("user");
                Picture[] entries = ((ViewPicturesInterface)cmd).getPictures();
                for (int i = 0; i < entries.length; i++) {
                    Picture entry = entries[i];
                    if(i%3==0) {
                        %>
                        <tr>
                        <%
                    }
                    %>
                    <td align="center">
                    <%
                    String writable = request.getParameter("writable");
                    String image = entry.getImage();
                    if(image==null || image.length()==0) {
                        image = "portal?do=loadthumbnail&gallery="+entry.getGallery()+"&image="+entry.getId()+(user!=null?"&user="+user:"");
                    }
                    String link = entry.getLink();
                    Boolean newWindow=Boolean.FALSE;
                    if(writable!=null && writable.equalsIgnoreCase("true")) {
                        %>
                        <a href="portal?do=newpicture&gallery=<%=gallery.getId()%>&id=<%=entry.getId()%>" class="bold-link">Uppdatera</a>
                        <a href="portal?do=removepicture&gallery=<%=gallery.getId()%>&id=<%=entry.getId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a><br>
                        <%
                        if(link==null || link.length()==0) {
                            String withCmd = ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"do",request.getParameter("do")+"viewpicture");
                            String startString = request.getParameter("start");
                            Integer start = null;
                            if(startString!=null && startString.length()>0) {
                                start = Integer.valueOf(startString);
                            }
                            String withStart = ServletParameterHelper.replaceParameter(withCmd,"start",""+(start.intValue()+i));
                            String withMax = ServletParameterHelper.replaceParameter(withStart,"max","1");
                            String withBack = ServletParameterHelper.replaceParameter(withMax,"backcmd",request.getParameter("do"));
                            link = "portal?"+withBack;
                        }
                    }else {
                        newWindow=Boolean.TRUE;
                        String withCmd = ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"do","viewpictureguest");
                        link = "portal?do=viewpictureguest&gallery="+request.getParameter("gallery")+"&image="+entry.getId()+(user!=null?"&user="+user:"");
                        %>
                        <a class="bold-link" href="<%=link%>&width=640" target="_blank" title="640 x 480">640</a>
                        <a class="bold-link" href="<%=link%>&width=800" target="_blank" title="800 x 600">800</a>
                        <a class="bold-link" href="<%=link%>&width=1024" target="_blank" title="1024 x 768">1024</a>
                        <a class="bold-link" href="<%=link%>&width=1280" target="_blank" title="1280 x 1024">1280</a><br>
                        <%
                        link+="&width=800";
                    }
                    %>
                    <a class="bold-link" href="<%=link%>" <%=newWindow.booleanValue()?"target=\"_blank\"":""%> title="<%=entry.getDescription()!=null?entry.getDescription():""%>"><img src="<%=image%>" border="0"></img><br><div align="center"><%=entry.getTitle()!=null?entry.getTitle():""%></div></a>
                    </td>
                    <%
                    if(i%3==2 || i==entries.length-1) {
                        %>
                        </tr>
                        <%
                    }
                }
            }
        }
    }
%>
