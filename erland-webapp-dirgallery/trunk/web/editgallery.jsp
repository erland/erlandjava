<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                java.util.Arrays,
                                java.util.Collection,
                                erland.webapp.dirgallery.gallery.ViewGalleryCommand,
                                erland.webapp.dirgallery.gallery.GalleryInterface,
                                erland.webapp.dirgallery.gallery.ViewGalleriesInterface,
                                erland.webapp.dirgallery.gallery.ViewFriendGalleriesInterface,
                                erland.webapp.usermgmt.User"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryCommand) {
        GalleryInterface gallery = ((ViewGalleryCommand)cmd).getGallery();
        User userEntity = (User) request.getSession().getAttribute("user");
        String user = userEntity!=null?userEntity.getUsername():"";
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editgallery">
            <table>
            <%
            if(gallery!=null && gallery.getId()!=null) {
                %>
                <tr><td>Id</td><td>
                <input type="hidden" name="id" value="<%=gallery.getId()%>"><a class="normal"><%=gallery.getId()%></a>
                </td></tr>
                <%
            }
            %>
            <tr><td>Titel</td><td>
            <input type="text" name="title" value="<%=gallery!=null?gallery.getTitle():""%>">
            </td></tr>
            <tr><td>Meny namn</td><td>
            <input type="text" name="menuname" value="<%=gallery!=null?gallery.getMenuName():""%>">
            </td></tr>
            <tr><td>Beskrivning</td><td>
            <textarea class="normal" name="description" cols="80" rows="15" wrap="virtual"><%=gallery!=null?gallery.getDescription():""%></textarea>
            </td></tr>
            <tr><td>Katalog</td><td>
            <input type="text" name="directory" size="80" value="<%=gallery!=null?gallery.getDirectory():"D:\\users\\"+user+"\\public_html"%>">
            </td></tr>
            <tr><td>Ordningsnummer</td><td>
            <input type="text" name="ordernumber" value="<%=gallery!=null?gallery.getOrderNumber().toString():""%>">
            </td></tr>
            <tr><td>Bilder i underkataloger</td><td>
            <input type="checkbox" name="includesubdirs" value="true" <%=(gallery!=null && gallery.getIncludeSubDirectories().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Typ av filer</td><td>
            <select name="typeoffiles" size="1">
            <option value="0" <%=gallery==null || gallery.getTypeOfFiles()==null || gallery.getTypeOfFiles().intValue()==GalleryInterface.PICTUREFILES?"selected":""%>>Bilder</option>
            <option value="1" <%=gallery!=null && gallery.getTypeOfFiles()!=null && gallery.getTypeOfFiles().intValue()==GalleryInterface.MOVIEFILES?"selected":""%>>Filmer</option>
            </select>
            </td></tr>
            <tr><td>Orginal bilder skall kunna visas</td><td>
            <input type="checkbox" name="originaldownloadable" value="true" <%=(gallery!=null && gallery.getOriginalDownloadable().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Officiell</td><td>
            <input type="checkbox" name="official" value="true" <%=(gallery!=null && gallery.getOfficial().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Visa logo högst upp i gallerisidan</td><td>
            <input type="checkbox" name="showlogo" value="true" <%=(gallery!=null && gallery.getShowLogoInGalleryPage().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Visa bildnamn nedanför thumbnails</td><td>
            <input type="checkbox" name="showpicturenames" value="true" <%=(gallery==null || gallery.getShowPictureNames().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Använd korta bildnamn</td><td>
            <input type="checkbox" name="useshortpicturenames" value="true" <%=(gallery==null || gallery.getUseShortPictureNames().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Max tecken i bildnamn</td><td>
            <input type="text" name="maxpicturenamelength" value="<%=gallery!=null?gallery.getMaxPictureNameLength().toString():""%>">
            </td></tr>
            <tr><td>Visa bildnamn i tooltip</td><td>
            <input type="checkbox" name="shoppicturenameintooltip" value="true" <%=(gallery==null || gallery.getShowPictureNameInTooltip().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Visa kommentar nedanför thumbnail</td><td>
            <input type="checkbox" name="showcommentbelowpicture" value="true" <%=(gallery!=null && gallery.getShowCommentBelowPicture().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Visa filstorlek nedanför thumbnail</td><td>
            <input type="checkbox" name="showfilesizebelowpicture" value="true" <%=(gallery!=null && gallery.getShowFileSizeBelowPicture().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Thumbnail bredd</td><td>
            <input type="text" name="thumbnailwidth" value="<%=gallery!=null?gallery.getThumbnailWidth().toString():""%>">
            </td></tr>
            <tr><td>Antal thumbnails per rad</td><td>
            <input type="text" name="noofthumbnailsperrow" value="<%=gallery!=null?gallery.getNumberOfThumbnailsPerRow().toString():""%>">
            </td></tr>
            <tr><td>Antal thumbnail rader</td><td>
            <input type="text" name="maxnoofthumbnailrows" value="<%=gallery!=null?gallery.getMaxNumberOfThumbnailRows().toString():""%>">
            </td></tr>
            <tr><td>Antal thumbnail kolumner i film thumbnails</td><td>
            <input type="text" name="noofmoviethumbnailcolumns" value="<%=gallery!=null?gallery.getNumberOfMovieThumbnailColumns().toString():""%>">
            </td></tr>
            <tr><td>Antal thumbnail rader i film thumbnails</td><td>
            <input type="text" name="noofmoviethumbnailrows" value="<%=gallery!=null?gallery.getNumberOfMovieThumbnailRows().toString():""%>">
            </td></tr>
            <tr><td>Komprimeringsgrad på thumbnails (0.1 - 1.0)</td><td>
            <input type="text" name="thumbnailcompression" value="<%=gallery!=null?gallery.getThumbnailCompression().toString():""%>">
            </td></tr>
            <tr><td>Använd thumbnail cache</td><td>
            <input type="checkbox" name="usethumbnailcache" value="true" <%=(gallery==null || gallery.getUseThumbnailCache().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Logo</td><td>
            <input type="text" name="logo" value="<%=gallery!=null?gallery.getLogo():""%>">
            </td></tr>
            <tr><td>Länk vid klick på logo</td><td>
            <input type="text" name="logolink" value="<%=gallery!=null?gallery.getLogoLink():""%>">
            </td></tr>
            <tr><td>Visa logo avskiljare</td><td>
            <input type="checkbox" name="uselogoseparator" value="true" <%=(gallery!=null && gallery.getUseLogoSeparator().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Färg på logo avskiljare</td><td>
            <input type="text" name="logoseparatorcolor" value="<%=gallery!=null?gallery.getLogoSeparatorColor():""%>">
            </td></tr>
            <tr><td>Höjd på logo avskiljare</td><td>
            <input type="text" name="logoseparatorheight" value="<%=gallery!=null?gallery.getLogoSeparatorHeight().toString():""%>">
            </td></tr>
            <tr><td>Visa nedladdningslänkar</td><td>
            <input type="checkbox" name="showdownloadlinks" value="true" <%=(gallery!=null && gallery.getShowDownloadLinks().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td>Visa kommentarer som tooltips</td><td>
            <input type="checkbox" name="usetooltip" value="true" <%=(gallery!=null && gallery.getUseTooltip().booleanValue())?"checked":""%>>
            </td></tr>
            <%
            if(cmd instanceof ViewGalleriesInterface && cmd instanceof ViewFriendGalleriesInterface) {
                %>
                <tr><td>Relaterade bildarkiv</td><td>
                <select name="friendgalleries" size="10" multiple>
                <%
                GalleryInterface[] galleries = ((ViewGalleriesInterface)cmd).getGalleries();
                Collection friendGalleries = Arrays.asList(((ViewFriendGalleriesInterface)cmd).getFriendGalleries());
                %>
                <option value="" <%=friendGalleries.size()==0?"selected":""%>></option>
                <%
                for (int i = 0; i < galleries.length; i++) {
                    GalleryInterface galleryEntity = galleries[i];
                    %>
                    <option value="<%=galleryEntity.getId()%>" <%=friendGalleries.contains(galleryEntity.getId())?"selected":""%>><%=galleryEntity.getTitle()%></option>
                    <%
                }
                %>
                </select>
                </td></tr>
                <%
            }
            %>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchgalleryentries<%=gallery!=null?"&gallery="+gallery.getId():""%>'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
