package erland.webapp.cdcollection.act.media;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

import erland.webapp.cdcollection.entity.media.Media;
import erland.webapp.cdcollection.entity.media.disc.Disc;
import erland.webapp.cdcollection.entity.media.disc.DiscTrack;
import erland.webapp.cdcollection.fb.media.MediaFB;
import erland.webapp.cdcollection.fb.media.MediaPB;
import erland.webapp.cdcollection.fb.media.disc.DiscPB;
import erland.webapp.cdcollection.fb.media.disc.DiscTrackPB;
import erland.webapp.common.act.BaseAction;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ViewMediaInfoAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MediaFB fb = (MediaFB) form;
        Media template = (Media) getEnvironment().getEntityFactory().create("cdcollection-media");

        template.setId(fb.getId());
        Media media = (Media) getEnvironment().getEntityStorageFactory().getStorage("cdcollection-media").load(template);
        MediaPB pb = new MediaPB();
        if (media != null) {
            PropertyUtils.copyProperties(pb, media);
            Map parameters = new HashMap();
            if(StringUtil.asNull(request.getServerName())!=null) {
                parameters.put("hostname",request.getServerName());
                if(request.getServerPort()!=80) {
                    parameters.put("port",new Integer(request.getServerPort()));
                }
            }
            parameters.put("contextpath",request.getContextPath());
            parameters.put("media",pb.getId());
            ActionForward forward = mapping.findForward("media-update-link");
            if(forward != null) {
                pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
            forward = mapping.findForward("media-cover-link");
            if(forward != null && StringUtil.asNull(media.getCoverUrl())!=null) {
                pb.setCoverLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
            forward = mapping.findForward("media-remove-link");
            if(forward != null) {
                pb.setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
            forward = mapping.findForward("media-newdisc-link");
            if(forward != null) {
                pb.setNewDiscLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
            forward = mapping.findForward("media-importdisc-link");
            if(forward != null) {
                pb.setImportDiscLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
            forward = mapping.findForward("media-import-link");
            if(forward != null && StringUtil.asNull(media.getUniqueMediaId())!=null) {
                StringTokenizer tokens = new StringTokenizer(media.getUniqueMediaId());
                if(tokens.hasMoreTokens()) {
                    parameters.put("category",tokens.nextToken());
                    if(tokens.hasMoreTokens()) {
                        parameters.put("uniquediscid",tokens.nextToken());
                        pb.setImportLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
                    }
                }
            }

            ActionForward discUpdateForward = mapping.findForward("disc-update-link");
            ActionForward discRemoveForward = mapping.findForward("disc-remove-link");
            ActionForward discNewTrackForward = mapping.findForward("disc-newtrack-link");
            ActionForward discTrackUpdateForward = mapping.findForward("disctrack-update-link");
            ActionForward discTrackRemoveForward = mapping.findForward("disctrack-remove-link");
            QueryFilter discFilter = new QueryFilter("allformedia");
            discFilter.setAttribute("media",pb.getId());
            EntityInterface[] discs = getEnvironment().getEntityStorageFactory().getStorage("cdcollection-disc").search(discFilter);
            DiscPB[] discsPB = new DiscPB[discs.length];
            for (int i = 0; i < discs.length; i++) {
                Disc disc = (Disc) discs[i];
                discsPB[i] = new DiscPB();
                PropertyUtils.copyProperties(discsPB[i],disc);
                if(StringUtil.asEmpty(disc.getArtist()).equals(media.getArtist())) {
                    discsPB[i].setArtist(null);
                }
                if(StringUtil.asEmpty(disc.getTitle()).equals(media.getTitle())) {
                    discsPB[i].setTitle(null);
                }
                parameters.put("disc",disc.getId());
                if(discUpdateForward!=null) {
                    discsPB[i].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(discUpdateForward.getPath(),parameters));
                }
                if(discRemoveForward!=null) {
                    discsPB[i].setRemoveLink(ServletParameterHelper.replaceDynamicParameters(discRemoveForward.getPath(),parameters));
                }
                if(discNewTrackForward!=null) {
                    discsPB[i].setNewTrackLink(ServletParameterHelper.replaceDynamicParameters(discNewTrackForward.getPath(),parameters));
                }
                QueryFilter trackFilter = new QueryFilter("allfordisc");
                trackFilter.setAttribute("disc",disc.getId());
                EntityInterface[] tracks = getEnvironment().getEntityStorageFactory().getStorage("cdcollection-disctrack").search(trackFilter);
                DiscTrackPB[] tracksPB = new DiscTrackPB[tracks.length];
                for (int j = 0; j < tracks.length; j++) {
                    DiscTrack track = (DiscTrack) tracks[j];
                    tracksPB[j] = new DiscTrackPB();
                    PropertyUtils.copyProperties(tracksPB[j],track);

                    if(StringUtil.asNull(disc.getTrackArtistPattern())!=null) {
                        Matcher matcher = Pattern.compile(disc.getTrackArtistPattern()).matcher(track.getTitle());
                        if(matcher.matches()) {
                            String artist = matcher.group(1);
                            if(artist!=null) {
                                tracksPB[j].setArtist(artist);
                            }
                        }
                    }
                    if(StringUtil.asNull(disc.getTrackTitlePattern())!=null) {
                        Matcher matcher = Pattern.compile(disc.getTrackTitlePattern()).matcher(track.getTitle());
                        if(matcher.matches()) {
                            String title = matcher.group(1);
                            if(title!=null) {
                                tracksPB[j].setTitle(title);
                            }
                        }
                    }

                    if(StringUtil.asEmpty(tracksPB[j].getArtist()).equals(media.getArtist())) {
                        tracksPB[j].setArtist(null);
                    }else if(StringUtil.asEmpty(tracksPB[j].getArtist()).equals(disc.getArtist())) {
                        tracksPB[j].setArtist(null);
                    }
                    parameters.put("disctrack",track.getId());
                    if(discTrackUpdateForward!=null) {
                        tracksPB[j].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(discTrackUpdateForward.getPath(),parameters));
                    }
                    if(discTrackRemoveForward!=null) {
                        tracksPB[j].setRemoveLink(ServletParameterHelper.replaceDynamicParameters(discTrackRemoveForward.getPath(),parameters));
                    }
                }
                discsPB[i].setTracks(tracksPB);
            }
            pb.setDiscs(discsPB);
        }
        request.setAttribute("mediaPB",pb);
    }
}
