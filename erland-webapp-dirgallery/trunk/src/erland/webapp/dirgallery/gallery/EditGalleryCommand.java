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
        gallery = (GalleryInterface) environment.getEntityFactory().create("dirgallery-gallery");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        gallery.setUsername(username);
        gallery.setOfficial(ServletParameterHelper.asBoolean(request.getParameter("official"),Boolean.FALSE));
        gallery.setId(ServletParameterHelper.asInteger(request.getParameter("id"),null));
        gallery.setTitle(request.getParameter("title"));
        gallery.setMenuName(request.getParameter("menuname"));
        gallery.setDescription(request.getParameter("description"));
        gallery.setDirectory(request.getParameter("directory"));
        gallery.setOriginalDownloadable(ServletParameterHelper.asBoolean(request.getParameter("originaldownloadable"),Boolean.FALSE));
        gallery.setIncludeSubDirectories(ServletParameterHelper.asBoolean(request.getParameter("includesubdirs"),Boolean.FALSE));
        gallery.setShowLogoInGalleryPage(ServletParameterHelper.asBoolean(request.getParameter("showlogo"),Boolean.FALSE));
        gallery.setShowDownloadLinks(ServletParameterHelper.asBoolean(request.getParameter("showdownloadlinks"),Boolean.FALSE));
        gallery.setShowPictureNames(ServletParameterHelper.asBoolean(request.getParameter("showpicturenames"),Boolean.FALSE));
        gallery.setUseLogoSeparator(ServletParameterHelper.asBoolean(request.getParameter("uselogoseparator"),Boolean.FALSE));
        gallery.setUseShortPictureNames(ServletParameterHelper.asBoolean(request.getParameter("useshortpicturenames"),Boolean.FALSE));
        gallery.setUseThumbnailCache(ServletParameterHelper.asBoolean(request.getParameter("usethumbnailcache"),Boolean.FALSE));
        gallery.setShowPictureNameInTooltip(ServletParameterHelper.asBoolean(request.getParameter("shoppicturenameintooltip"),Boolean.FALSE));
        gallery.setUseTooltip(ServletParameterHelper.asBoolean(request.getParameter("usetooltip"),Boolean.FALSE));
        gallery.setLogoSeparatorColor(request.getParameter("logoseparatorcolor"));
        gallery.setLogoSeparatorHeight(ServletParameterHelper.asInteger(request.getParameter("logoseparatorheight"),null));
        gallery.setThumbnailWidth(ServletParameterHelper.asInteger(request.getParameter("thumbnailwidth"),null));
        gallery.setNumberOfThumbnailsPerRow(ServletParameterHelper.asInteger(request.getParameter("noofthumbnailsperrow"),null));
        gallery.setLogo(request.getParameter("logo"));
        gallery.setLogoLink(request.getParameter("logolink"));
        gallery.setMaxNumberOfThumbnailRows(ServletParameterHelper.asInteger(request.getParameter("maxnoofthumbnailrows"),null));
        gallery.setOrderNumber(ServletParameterHelper.asInteger(request.getParameter("ordernumber"),null));
        gallery.setThumbnailCompression(ServletParameterHelper.asDouble(request.getParameter("thumbnailcompression"),null));
        gallery.setTypeOfFiles(ServletParameterHelper.asInteger(request.getParameter("typeoffiles"),null));
        gallery.setNumberOfMovieThumbnailColumns(ServletParameterHelper.asInteger(request.getParameter("noofmoviethumbnailcolumns"),null));
        gallery.setNumberOfMovieThumbnailRows(ServletParameterHelper.asInteger(request.getParameter("noofmoviethumbnailrows"),null));
        gallery.setMaxPictureNameLength(ServletParameterHelper.asInteger(request.getParameter("maxpicturenamelength"),null));
        gallery.setShowFileSizeBelowPicture(ServletParameterHelper.asBoolean(request.getParameter("showfilesizebelowpicture"),Boolean.FALSE));
        gallery.setShowCommentBelowPicture(ServletParameterHelper.asBoolean(request.getParameter("showcommentbelowpicture"),Boolean.FALSE));
        environment.getEntityStorageFactory().getStorage("dirgallery-gallery").store(gallery);
        String[] friendGalleries = request.getParameterValues("friendgalleries");
        if (friendGalleries != null) {
            QueryFilter filter = new QueryFilter("allforgallery");
            filter.setAttribute("gallery", gallery.getId());
            environment.getEntityStorageFactory().getStorage("dirgallery-friendgallery").delete(filter);
            for (int i = 0; i < friendGalleries.length; i++) {
                if (friendGalleries[i].length() > 0) {
                    Integer friendGalleryId = Integer.valueOf(friendGalleries[i]);
                    FriendGallery entity = (FriendGallery) environment.getEntityFactory().create("dirgallery-friendgallery");
                    entity.setGallery(gallery.getId());
                    entity.setFriendGallery(friendGalleryId);
                    environment.getEntityStorageFactory().getStorage("dirgallery-friendgallery").store(entity);
                }
            }
        }
        return null;
    }

    public GalleryInterface getGallery() {
        return gallery;
    }
}
