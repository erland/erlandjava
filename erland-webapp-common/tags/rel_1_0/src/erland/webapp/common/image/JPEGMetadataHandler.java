package erland.webapp.common.image;

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

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import erland.webapp.common.DescriptionTagHelper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JPEGMetadataHandler implements MetadataHandlerInterface {
    private Metadata metaData;
    private Map metaDataMap = new HashMap();
    private boolean onlySelected;

    public JPEGMetadataHandler(boolean onlySelected) {
        this.onlySelected = onlySelected;
    }

    public boolean load(String filename) {
        metaData = null;
        try {
            BufferedInputStream input = null;
            try {
                input = new BufferedInputStream(new FileInputStream(filename));
            } catch (FileNotFoundException e) {
                input = new BufferedInputStream(new URL(filename).openConnection().getInputStream());
            }
            if (input != null) {
                metaData = JpegMetadataReader.readMetadata(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JpegProcessingException e) {
            e.printStackTrace();
        }
        return metaData != null;
    }

    public String[] getNames() {
        if (metaData != null) {
            for (Iterator it = metaData.getDirectoryIterator(); it.hasNext();) {
                Directory directory = (Directory) it.next();
                for (Iterator tagsIt = directory.getTagIterator(); tagsIt.hasNext();) {
                    Tag tag = (Tag) tagsIt.next();
                    try {
                        if (!onlySelected || DescriptionTagHelper.getInstance().getDescription("common-metadatafielddescription", tag.getDirectoryName() + " " + tag.getTagName()) != null) {
                            metaDataMap.put(tag.getDirectoryName() + " " + tag.getTagName(), tag.getDescription());
                        }
                    } catch (MetadataException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        String[] result = (String[]) metaDataMap.keySet().toArray(new String[0]);
        Arrays.sort(result);
        return result;
    }

    public String getValue(String name) {
        return (String) metaDataMap.get(name);
    }

    public String getDescription(String name) {
        String description = DescriptionTagHelper.getInstance().getDescription("common-metadatafielddescription", name);
        if (!onlySelected) {
            return name;
        }
        return description;
    }
}