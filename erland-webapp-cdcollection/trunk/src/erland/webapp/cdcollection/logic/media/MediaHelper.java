package erland.webapp.cdcollection.logic.media;

import com.antelmann.util.Settings;
import com.antelmann.cddb.*;

import java.io.IOException;
import java.util.StringTokenizer;

import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.cdcollection.entity.media.disc.Disc;
import erland.webapp.cdcollection.entity.media.Media;
import erland.webapp.cdcollection.logic.media.disc.DiscHelper;
import erland.util.StringUtil;

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

public class MediaHelper {
    public static int importMedia(String category, String discId) throws IOException {
        int mediaId = -1;
        Settings.setContextClassLoader();
        FreeDB freeDB = new FreeDB();
        CDInfo cdInfo = freeDB.readCDInfo(new CDDBRecord(category,discId,null,true));
        if(cdInfo instanceof CDDBEntry) {
            CDDBEntry entry = (CDDBEntry) cdInfo;
            QueryFilter filter = new QueryFilter("allforuniquemediaid");
            filter.setAttribute("uniquemediaid",entry.getCDDBRecord().getCategory()+" "+cdInfo.getCDDBRecord().getDiscID());
            EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("cdcollection-media").search(filter);
            Media media = null;
            if(entities.length>0) {
                media = (Media) entities[0];
            }else {
                media = (Media) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("cdcollection-media");
                media.setUniqueMediaId(entry.getCDDBRecord().getCategory()+" "+cdInfo.getCDDBRecord().getDiscID());
                media.setId(null);
            }
            Artist artist = entry.extractCDArtist();
            if(artist!=null && StringUtil.asNull(media.getArtist())==null) {
                media.setArtist(artist.getName());
            }
            Composition composition = entry.extractComposition(false);
            if(StringUtil.asNull(media.getTitle())==null) {
                media.setTitle(composition.getTitle());
            }
            media.setYear(new Integer(composition.getRecordingYear()));
            WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("cdcollection-media").store(media);

            mediaId = media.getId().intValue();
            filter = new QueryFilter("allformedia");
            filter.setAttribute("media",media.getId());
            EntityInterface[] discEntities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("cdcollection-disc").search(filter);
            String defaultUniqueDiscId = entry.getCDDBRecord().getCategory()+" "+cdInfo.getCDDBRecord().getDiscID();
            for (int i = 0; i < discEntities.length; i++) {
                Disc discEntity = (Disc) discEntities[i];
                if(defaultUniqueDiscId!=null && defaultUniqueDiscId.equals(discEntity.getUniqueDiscId())) {
                    defaultUniqueDiscId = null;
                }
                StringTokenizer tokens = new StringTokenizer(discEntity.getUniqueDiscId());
                if(tokens.hasMoreTokens()) {
                    String discCategory = tokens.nextToken();
                    if(tokens.hasMoreTokens()) {
                        DiscHelper.importDiscToMedia(media.getId(),discCategory,tokens.nextToken());
                    }
                }
            }
            // Import default disc if it is not already imported
            if(defaultUniqueDiscId!=null) {
                StringTokenizer tokens = new StringTokenizer(defaultUniqueDiscId);
                if(tokens.hasMoreTokens()) {
                    String discCategory = tokens.nextToken();
                    if(tokens.hasMoreTokens()) {
                        DiscHelper.importDiscToMedia(media.getId(),discCategory,tokens.nextToken());
                    }
                }
            }
        }
        return mediaId;
    }
}