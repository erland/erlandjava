package erland.webapp.cdcollection.logic.media.disc;

import com.antelmann.util.Settings;
import com.antelmann.cddb.*;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.cdcollection.entity.media.disc.Disc;
import erland.webapp.cdcollection.entity.media.disc.DiscTrack;
import erland.util.StringUtil;

import java.io.IOException;

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

public class DiscHelper {
    public static int importDiscToMedia(Integer mediaId, String category, String discId) throws IOException {
        int result = -1;
        Settings.setContextClassLoader();
        FreeDB freeDB = new FreeDB();
        try {
            CDInfo cdInfo = freeDB.readCDInfo(new CDDBRecord(category,discId,null,true));
            if(cdInfo instanceof CDDBEntry) {
                CDDBEntry entry = (CDDBEntry) cdInfo;
                QueryFilter filter = new QueryFilter("allforuniquediscid");
                filter.setAttribute("uniquediscid",entry.getCDDBRecord().getCategory()+" "+cdInfo.getCDDBRecord().getDiscID());
                EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("cdcollection-disc").search(filter);
                Disc disc = null;
                if(entities.length>0) {
                    disc = (Disc) entities[0];
                }else {
                    disc = (Disc) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("cdcollection-disc");
                    disc.setUniqueDiscId(entry.getCDDBRecord().getCategory()+" "+cdInfo.getCDDBRecord().getDiscID());
                    disc.setId(null);
                }
                Artist artist = entry.extractCDArtist();
                if(artist!=null && StringUtil.asNull(disc.getArtist())==null) {
                    disc.setArtist(artist.getName());
                }
                Composition composition = entry.extractComposition(false);
                disc.setMediaId(mediaId);
                if(StringUtil.asNull(disc.getTitle())==null) {
                    disc.setTitle(composition.getTitle());
                }
                disc.setYear(new Integer(composition.getRecordingYear()));
                WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("cdcollection-disc").store(disc);
                result = disc.getId().intValue();

                filter = new QueryFilter("allfordisc");
                filter.setAttribute("disc",disc.getId());
                WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("cdcollection-disctrack").delete(filter);

                Track[] tracks = entry.extractTracks(false);
                for (int j = 0; j < tracks.length; j++) {
                    Track track = tracks[j];
                    DiscTrack discTrack = (DiscTrack) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("cdcollection-disctrack");
                    artist = entry.extractTrackArtist(track.getTrackNo());
                    if(artist!=null) {
                        discTrack.setArtist(artist.getName());
                    }
                    discTrack.setDiscId(disc.getId());
                    discTrack.setId(null);
                    discTrack.setTrackNo(new Integer(track.getTrackNo()));
                    discTrack.setTitle(track.getTitle());
                    discTrack.setLength(new Integer(track.getLength()));
                    WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("cdcollection-disctrack").store(discTrack);
                }
            }
        } catch (CDDBProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XmcdFormatException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }
}