<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.dirgallery.gallery.ViewGalleryInterface,
                                erland.webapp.dirgallery.gallery.GalleryInterface,
                                erland.webapp.dirgallery.HTMLEncoder,
                                erland.webapp.dirgallery.account.ViewUserAccountInterface,
                                erland.webapp.dirgallery.account.UserAccount,
                                erland.webapp.dirgallery.gallery.ViewGalleriesInterface,
                                erland.webapp.common.ServletParameterHelper"%>


<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryInterface) {
        GalleryInterface gallery = ((ViewGalleryInterface)cmd).getGallery();
        if(gallery!=null) {
            int noOfThumbnail = gallery.getNumberOfThumbnailsPerRow().intValue();
            if(noOfThumbnail<=0) {
                noOfThumbnail=3;
            }
            String command = request.getParameter("do");
            GalleryInterface[] friendGalleries = new GalleryInterface[0];
            CommandInterface galleriesCmd = (CommandInterface) request.getAttribute("galleriescmd");
            if(command==null || command.startsWith("viewgallery") && galleriesCmd instanceof ViewGalleriesInterface) {
                friendGalleries = ((ViewGalleriesInterface)galleriesCmd).getGalleries();
            }
            int friendMenu = friendGalleries.length>0?1:0;
            %>
            <table width="450" class="no-border">
            <%
            if(command==null || command.startsWith("viewgallery")) {
                if(gallery.getShowLogoInGalleryPage().booleanValue()) {
                    String logo = gallery.getLogo();
                    String logoLink = gallery.getLogoLink();
                    if(logo!=null && logo.length()>0) {
                        if(logoLink!=null && logoLink.length()>0) {
                            %>
                            <tr><td align="center" colspan="<%=noOfThumbnail+friendMenu%>"><a href="<%=logoLink%>" target="_blank" border="0"><img src="<%=logo%>" border="0"></img></a></td></tr>
                            <%
                        }else {
                            %>
                            <tr><td align="center" colspan="<%=noOfThumbnail+friendMenu%>"><img src="<%=logo%>" border="0"></img></td></tr>
                            <%
                        }
                        if(gallery.getUseLogoSeparator().booleanValue()) {
                            %>
                            <tr><td align="center" colspan="<%=noOfThumbnail+friendMenu%>" bgcolor="<%=gallery.getLogoSeparatorColor()%>" height="<%=gallery.getLogoSeparatorHeight()%>"></td></tr>
                            <%
                        }
                    }
                }
            }
            if(friendGalleries.length>0) {
                %>
                <tr><td valign="top" halign="left"><table class="no-border">
                <%
                for (int i = 0; i < friendGalleries.length; i++) {
                    GalleryInterface friendGallery = friendGalleries[i];
                    String link = ServletParameterHelper.replaceParameter((String)request.getAttribute("cmdparameters"),"gallery",friendGallery.getId().toString());
                    link = ServletParameterHelper.removeParameter(link,"start");
                    String menuTitle = friendGallery.getMenuName();
                    if(menuTitle==null || menuTitle.length()==0) {
                        menuTitle = friendGallery.getTitle();
                    }
                    %>
                    <tr><td nowrap><a class="bold-link<%=friendGallery.getId().equals(gallery.getId())?"-selected":""%>" href="portal?<%=link%>"><%=menuTitle%></a></td></tr>
                    <%
                }
                %>
                </table></td><td><table class="no-border">
                <%
            }
            %>
            <tr><td width="100%" colspan="<%=noOfThumbnail%>"><p class="title"><%=gallery.getTitle()%></p></td></tr>
            <tr><td width="100%" colspan="<%=noOfThumbnail%>"><p class="normal"><%=HTMLEncoder.encode(gallery.getDescription())%></p></td></tr>
            <%
            if(command==null || !command.startsWith("viewgallery")) {
                %>
                <tr><td colspan="<%=noOfThumbnail%>"><a class="bold-link" href="portal?do=viewgallery&gallery=<%=gallery.getId()%>" target="_blank">Öppna i nytt fönster</a></td></tr>
                <%
            }
            %>
            <jsp:include page="viewgallerypicturepart.jsp">
                <jsp:param name="writable" value="false"/>
            </jsp:include>
            <tr><td colspan="<%=noOfThumbnail%>"><jsp:include page="prevnextbuttons.jsp"/></td></tr>
            <%
            if(friendGalleries.length>0) {
                %>
                </table>
                </td>
                </tr>
                <%
            }
            %>
            </table>
        <%
        }
    }
%>
