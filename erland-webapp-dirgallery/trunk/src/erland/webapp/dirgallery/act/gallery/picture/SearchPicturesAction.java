package erland.webapp.dirgallery.act.gallery.picture;

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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.dirgallery.entity.gallery.Gallery;
import erland.webapp.dirgallery.entity.gallery.picture.Picture;
import erland.webapp.dirgallery.entity.gallery.picture.PictureComment;
import erland.webapp.dirgallery.fb.gallery.GalleryPB;
import erland.webapp.dirgallery.fb.gallery.picture.PictureCollectionPB;
import erland.webapp.dirgallery.fb.gallery.picture.PicturePB;
import erland.webapp.dirgallery.fb.gallery.picture.ResolutionPB;
import erland.webapp.dirgallery.fb.gallery.picture.SelectPictureFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SearchPicturesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectPictureFB fb = (SelectPictureFB) form;
        PictureCollectionPB pb = new PictureCollectionPB();
        PicturePB[] pbPictures = new PicturePB[0];
        if (fb.getGallery() != null) {
            Gallery template = (Gallery) getEnvironment().getEntityFactory().create("dirgallery-gallery");
            template.setId(fb.getGallery());
            Gallery gallery = (Gallery) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-gallery").load(template);
            if (gallery == null) {
                saveErrors(request, Arrays.asList(new String[]{"dirgallery.gallery.view.gallery-dont-exist"}));
                return;
            }
            int thumbnailsPerRow = gallery.getNumberOfThumbnailsPerRow().intValue();
            if (thumbnailsPerRow == 0) {
                thumbnailsPerRow = 3;
            }
            QueryFilter filter = new QueryFilter("allfordirectoryandextensions");
            filter.setAttribute("directory", getEnvironment().getConfigurableResources().getParameter("basedirectory") + "/" + gallery.getUsername() + "/" + gallery.getDirectory());
            if (gallery.getTypeOfFiles().equals(Gallery.PICTUREFILES)) {
                filter.setAttribute("extensions", ".jpg,.jpeg");
            } else if (gallery.getTypeOfFiles().equals(Gallery.MOVIEFILES)) {
                filter.setAttribute("extensions", ".mpg,.mpeg,.avi,.mov");
            }
            filter.setAttribute("tree", gallery.getIncludeSubDirectories());
            EntityInterface[] entities = new EntityInterface[0];
            entities = getEnvironment().getEntityStorageFactory().getStorage("dirgallery-picture").search(filter);

            Integer max = new Integer(gallery.getMaxNumberOfThumbnailRows().intValue() * thumbnailsPerRow);
            Picture[] pictures = null;
            if (max.intValue() != 0) {
                int length = max.intValue();
                if (max.intValue() >= entities.length - fb.getStart().intValue()) {
                    length = entities.length - fb.getStart().intValue();
                }
                pictures = new Picture[length];
            } else {
                max = null;
                pictures = new Picture[entities.length];
            }
            for (int i = fb.getStart().intValue(), j = 0; j < pictures.length; i++, j++) {
                pictures[j] = (Picture) entities[i];
                pictures[j].setGallery(fb.getGallery());
            }
            Map comments = loadPictureComments(pictures);
            Map parameters = new HashMap();
            parameters.put("gallery", fb.getGallery());
            parameters.put("user", gallery.getUsername());
            ActionForward pictureImageForward = mapping.findForward("picture-image");
            ActionForward pictureLinkForward = mapping.findForward("picture-link");
            ActionForward movieImageForward = mapping.findForward("movie-image");
            ActionForward movieLinkForward = mapping.findForward("movie-link");
            ActionForward pictureResolutionForward = mapping.findForward("picture-resolution-link");
            ActionForward movieResolutionForward = mapping.findForward("movie-resolution-link");
            ActionForward commentLinkForward = mapping.findForward("update-comment-link");
            ResolutionPB[] resolutionsPB = new ResolutionPB[0];
            if (gallery.getShowDownloadLinks().booleanValue()) {
                if (gallery.getMaxWidth() != null && gallery.getMaxWidth().intValue() > 0) {
                    filter = new QueryFilter("all-smaller-than");
                    filter.setAttribute("width", gallery.getMaxWidth());
                } else {
                    filter = new QueryFilter("all");
                }
                EntityInterface[] resolutionEntities = getEnvironment().getEntityStorageFactory().getStorage("dirgallery-resolution").search(filter);
                if (resolutionEntities.length > 0) {
                    resolutionsPB = new ResolutionPB[resolutionEntities.length];
                    for (int i = 0; i < resolutionsPB.length; i++) {
                        resolutionsPB[i] = new ResolutionPB();
                        try {
                            PropertyUtils.copyProperties(resolutionsPB[i], resolutionEntities[i]);
                        } catch (IllegalAccessException e) {
                        } catch (InvocationTargetException e) {
                        } catch (NoSuchMethodException e) {
                        }
                    }
                }
            }
            pbPictures = new PicturePB[pictures.length];
            if (gallery.getThumbnailWidth() != null && gallery.getThumbnailWidth().intValue() > 0) {
                parameters.put("thumbnailwidth", gallery.getThumbnailWidth());
            }
            if (gallery.getUseThumbnailCache() != null && !gallery.getUseThumbnailCache().booleanValue()) {
                parameters.put("useCache", "false");
            }
            if (gallery.getThumbnailCompression() != null && gallery.getThumbnailCompression().doubleValue() > 0.0) {
                parameters.put("compression", gallery.getThumbnailCompression());
            }
            if (gallery.getNumberOfMovieThumbnailRows() != null && gallery.getNumberOfMovieThumbnailRows().intValue() > 0) {
                parameters.put("movieRows", gallery.getNumberOfMovieThumbnailRows());
            }
            if (gallery.getNumberOfMovieThumbnailColumns() != null && gallery.getNumberOfMovieThumbnailColumns().intValue() > 0) {
                parameters.put("movieCols", gallery.getNumberOfMovieThumbnailColumns());
            }
            for (int i = 0; i < pictures.length; i++) {
                pbPictures[i] = new PicturePB();
                PropertyUtils.copyProperties(pbPictures[i], pictures[i]);
                parameters.put("picture", pictures[i].getId());

                pbPictures[i].setComment((String) comments.get(pictures[i].getFullPath()));

                if (gallery.getUseTooltip().booleanValue()) {
                    pbPictures[i].setDescription(pbPictures[i].getComment());
                }
                if (gallery.getShowPictureNameInTooltip().booleanValue()) {
                    String description = "";
                    if (pbPictures[i].getDescription() != null) {
                        description = pbPictures[i].getDescription() + "\n\n";
                    }
                    if (gallery.getUseShortPictureNames().booleanValue()) {
                        pbPictures[i].setDescription(description + pictures[i].getShortName());
                    } else {
                        pbPictures[i].setDescription(description + pictures[i].getName());
                    }
                }
                if (gallery.getUseShortPictureNames().booleanValue()) {
                    pbPictures[i].setName(pictures[i].getShortName());
                }

                if (gallery.getTypeOfFiles().equals(Gallery.PICTUREFILES)) {
                    if (pictureImageForward != null) {
                        pbPictures[i].setImage(ServletParameterHelper.replaceDynamicParameters(pictureImageForward.getPath(), parameters));
                    }
                    if (pictureLinkForward != null) {
                        pbPictures[i].setLink(ServletParameterHelper.replaceDynamicParameters(pictureLinkForward.getPath(), parameters));
                    }
                } else if (gallery.getTypeOfFiles().equals(Gallery.MOVIEFILES)) {
                    if (movieImageForward != null) {
                        pbPictures[i].setImage(ServletParameterHelper.replaceDynamicParameters(movieImageForward.getPath(), parameters));
                    }
                    if (movieLinkForward != null) {
                        pbPictures[i].setLink(ServletParameterHelper.replaceDynamicParameters(movieLinkForward.getPath(), parameters));
                    }
                }
                ActionForward resolutionForward = null;
                if (gallery.getTypeOfFiles().equals(Gallery.PICTUREFILES)) {
                    resolutionForward = pictureResolutionForward;
                } else {
                    resolutionForward = movieResolutionForward;
                }
                if (gallery.getShowDownloadLinks().booleanValue()) {
                    if (resolutionForward != null) {
                        pbPictures[i].setResolutionLink(ServletParameterHelper.replaceDynamicParameters(resolutionForward.getPath(), parameters));
                        pbPictures[i].setResolutions(resolutionsPB);
                    }
                }
                if (!gallery.getOriginalDownloadable().booleanValue()) {
                    if (resolutionForward != null) {
                        pbPictures[i].setLink(ServletParameterHelper.replaceDynamicParameters(resolutionForward.getPath(), parameters));
                    } else {
                        pbPictures[i].setLink(null);
                    }
                }
                if (!gallery.getShowPictureNames().booleanValue()) {
                    pbPictures[i].setName(null);
                } else {
                    String name = pbPictures[i].getName();
                    if (name != null && gallery.getMaxPictureNameLength().intValue() > 0 && name.length() > gallery.getMaxPictureNameLength().intValue()) {
                        name = name.substring(0, gallery.getMaxPictureNameLength().intValue() - 3) + "...";
                    }
                    pbPictures[i].setName(name);
                }
                if (!gallery.getShowCommentBelowPicture().booleanValue()) {
                    pbPictures[i].setComment(null);
                }
                if (!gallery.getShowFileSizeBelowPicture().booleanValue()) {
                    pbPictures[i].setFileSize(null);
                }
                if (commentLinkForward != null) {
                    pbPictures[i].setUpdateCommentLink(ServletParameterHelper.replaceDynamicParameters(commentLinkForward.getPath(), parameters));
                }
            }

            Integer prevStart = getPreviousPage(fb.getStart(), max);
            if (prevStart != null) {
                parameters.put("start", prevStart.toString());
                ActionForward forward = mapping.findForward("previous-link");
                if (forward != null) {
                    pb.setPrevLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(), parameters));
                }
            } else {
                pb.setPrevLink(null);
            }
            Integer nextStart = getNextPage(fb.getStart(), max, entities.length);
            if (nextStart != null) {
                parameters.put("start", nextStart.toString());
                ActionForward forward = mapping.findForward("next-link");
                if (forward != null) {
                    pb.setNextLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(), parameters));
                }
            } else {
                pb.setNextLink(null);
            }

            if (!gallery.getShowLogoInGalleryPage().booleanValue()) {
                gallery.setLogo(null);
                gallery.setLogoLink(null);
            }
            GalleryPB pbGallery = new GalleryPB();
            PropertyUtils.copyProperties(pbGallery, gallery);
            if (pbGallery.getNumberOfThumbnailsPerRow() == null || pbGallery.getNumberOfThumbnailsPerRow().intValue() == 0) {
                pbGallery.setNumberOfThumbnailsPerRow(new Integer(3));
            }
            ActionForward forward = mapping.findForward("update-gallery-link");
            if (forward != null) {
                pbGallery.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(), parameters));
            }
            forward = mapping.findForward("remove-gallery-link");
            if (forward != null) {
                pbGallery.setDeleteLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(), parameters));
            }
            request.setAttribute("galleryPB", pbGallery);
        }
        pb.setPictures(pbPictures);
        request.setAttribute("picturesPB", pb);
    }

    public Map loadPictureComments(Picture[] pictures) {
        Map comments = new HashMap();
        Collection commentIds = new ArrayList();
        for (int i = 0; i < pictures.length; i++) {
            commentIds.add(pictures[i].getFullPath());
        }
        comments = new HashMap();
        if (commentIds.size() > 0) {
            QueryFilter filter = new QueryFilter("allforcommentlist");
            filter.setAttribute("comments", commentIds);
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("dirgallery-picturecomment").search(filter);
            for (int i = 0; i < entities.length; i++) {
                PictureComment comment = (PictureComment) entities[i];
                comments.put(comment.getId(), comment.getComment());
            }
        }
        return comments;
    }

    private Integer getPreviousPage(Integer start, Integer max) {
        if (max != null && start != null) {
            if (start.intValue() > 0) {
                if (start.intValue() - max.intValue() < 0) {
                    return new Integer(0);
                } else {
                    return new Integer(start.intValue() - max.intValue());
                }
            }
        }
        return null;
    }

    private Integer getNextPage(Integer start, Integer max, int noOfPictures) {
        if (max != null && start != null) {
            if (start.intValue() + max.intValue() < noOfPictures) {
                return new Integer(start.intValue() + max.intValue());
            }
        }
        return null;
    }
}
