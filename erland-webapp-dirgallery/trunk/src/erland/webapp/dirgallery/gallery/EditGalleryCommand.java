package erland.webapp.dirgallery.gallery;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import erland.webapp.common.CommandInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class EditGalleryCommand implements CommandInterface, ViewGalleryInterface {
    private WebAppEnvironmentInterface environment;
    private GalleryInterface gallery;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        gallery = (GalleryInterface) environment.getEntityFactory().create("gallery");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        gallery.setUsername(username);
        gallery.setOfficial(ServletParameterHelper.asBoolean(request.getParameter("official")));
        gallery.setId(ServletParameterHelper.asInteger(request.getParameter("id")));
        gallery.setTitle(request.getParameter("title"));
        gallery.setMenuName(request.getParameter("menuname"));
        gallery.setDescription(request.getParameter("description"));
        gallery.setDirectory(request.getParameter("directory"));
        gallery.setOriginalDownloadable(ServletParameterHelper.asBoolean(request.getParameter("originaldownloadable")));
        gallery.setIncludeSubDirectories(ServletParameterHelper.asBoolean(request.getParameter("includesubdirs")));
        gallery.setShowLogoInGalleryPage(ServletParameterHelper.asBoolean(request.getParameter("showlogo")));
        gallery.setShowDownloadLinks(ServletParameterHelper.asBoolean(request.getParameter("showdownloadlinks")));
        gallery.setShowPictureNames(ServletParameterHelper.asBoolean(request.getParameter("showpicturenames")));
        gallery.setUseLogoSeparator(ServletParameterHelper.asBoolean(request.getParameter("uselogoseparator")));
        gallery.setUseShortPictureNames(ServletParameterHelper.asBoolean(request.getParameter("useshortpicturenames")));
        gallery.setUseThumbnailCache(ServletParameterHelper.asBoolean(request.getParameter("usethumbnailcache")));
        gallery.setShowPictureNameInTooltip(ServletParameterHelper.asBoolean(request.getParameter("shoppicturenameintooltip")));
        gallery.setUseTooltip(ServletParameterHelper.asBoolean(request.getParameter("usetooltip")));
        gallery.setLogoSeparatorColor(request.getParameter("logoseparatorcolor"));
        gallery.setLogoSeparatorHeight(ServletParameterHelper.asInteger(request.getParameter("logoseparatorheight")));
        gallery.setThumbnailWidth(ServletParameterHelper.asInteger(request.getParameter("thumbnailwidth")));
        gallery.setNumberOfThumbnailsPerRow(ServletParameterHelper.asInteger(request.getParameter("noofthumbnailsperrow")));
        gallery.setLogo(request.getParameter("logo"));
        gallery.setLogoLink(request.getParameter("logolink"));
        gallery.setMaxNumberOfThumbnailRows(ServletParameterHelper.asInteger(request.getParameter("maxnoofthumbnailrows")));
        gallery.setOrderNumber(ServletParameterHelper.asInteger(request.getParameter("ordernumber")));
        gallery.setThumbnailCompression(ServletParameterHelper.asDouble(request.getParameter("thumbnailcompression")));
        gallery.setTypeOfFiles(ServletParameterHelper.asInteger(request.getParameter("typeoffiles")));
        gallery.setNumberOfMovieThumbnailColumns(ServletParameterHelper.asInteger(request.getParameter("noofmoviethumbnailcolumns")));
        gallery.setNumberOfMovieThumbnailRows(ServletParameterHelper.asInteger(request.getParameter("noofmoviethumbnailrows")));
        gallery.setMaxPictureNameLength(ServletParameterHelper.asInteger(request.getParameter("maxpicturenamelength")));
        gallery.setShowFileSizeBelowPicture(ServletParameterHelper.asBoolean(request.getParameter("showfilesizebelowpicture")));
        gallery.setShowCommentBelowPicture(ServletParameterHelper.asBoolean(request.getParameter("showcommentbelowpicture")));
        environment.getEntityStorageFactory().getStorage("gallery").store(gallery);
        String[] friendGalleries = request.getParameterValues("friendgalleries");
        if (friendGalleries != null) {
            QueryFilter filter = new QueryFilter("allforgallery");
            filter.setAttribute("gallery", gallery.getId());
            environment.getEntityStorageFactory().getStorage("friendgallery").delete(filter);
            for (int i = 0; i < friendGalleries.length; i++) {
                if (friendGalleries[i].length() > 0) {
                    Integer friendGalleryId = Integer.valueOf(friendGalleries[i]);
                    FriendGallery entity = (FriendGallery) environment.getEntityFactory().create("friendgallery");
                    entity.setGallery(gallery.getId());
                    entity.setFriendGallery(friendGalleryId);
                    environment.getEntityStorageFactory().getStorage("friendgallery").store(entity);
                }
            }
        }
        return null;
    }

    public GalleryInterface getGallery() {
        return gallery;
    }
}
