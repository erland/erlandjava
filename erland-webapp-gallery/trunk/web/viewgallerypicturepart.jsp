<%@ page import="erland.webapp.gallery.gallery.picture.ViewPicturesInterface,
                 erland.webapp.gallery.gallery.picture.Picture,
                 erland.webapp.common.CommandInterface,
                 erland.webapp.gallery.gallery.ViewGalleryInterface,
                 erland.webapp.gallery.gallery.Gallery,
                 erland.webapp.common.ServletParameterHelper"%>
 <%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryInterface) {
        Gallery gallery = ((ViewGalleryInterface)cmd).getGallery();
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
                    if(writable!=null && writable.equalsIgnoreCase("true")) {
                        %>
                        <a href="portal?do=newpicture&gallery=<%=gallery.getId()%>&id=<%=entry.getId()%>" class="bold-link">Update</a>
                        <a href="portal?do=removepicture&gallery=<%=gallery.getId()%>&id=<%=entry.getId()%>" class="bold-link" onClick="return confirm('Are you sure you want to delete this ?')">Delete</a><br>
                        <%
                    }
                    %>
                    <%
                        String image = entry.getImage();
                        if(image==null || image.length()==0) {
                            image = "portal?do=loadthumbnail&gallery="+entry.getGallery()+"&image="+entry.getId()+(user!=null?"&user="+user:"");
                        }
                        String link = entry.getLink();
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
                    %>
                    <a class="bold-link" href="<%=link%>" title="<%=entry.getDescription()!=null?entry.getDescription():""%>"><img src="<%=image%>" border="0" width="150"></img><br><div align="center"><%=entry.getTitle()!=null?entry.getTitle():""%></div></a>
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
