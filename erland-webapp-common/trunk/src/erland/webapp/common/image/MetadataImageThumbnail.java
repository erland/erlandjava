package erland.webapp.common.image;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MetadataImageThumbnail extends ImageThumbnail {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(MetadataImageThumbnail.class);
    private static Object sync = new Object();
    private Boolean scaleMetadataThumbnails;

    public MetadataImageThumbnail() {
        scaleMetadataThumbnails = Boolean.TRUE;
    }

    public MetadataImageThumbnail(Boolean antialias, Boolean scaleMetadataThumbnails) {
        super(antialias);
        this.scaleMetadataThumbnails = scaleMetadataThumbnails!=null?scaleMetadataThumbnails:Boolean.TRUE;
    }

    public BufferedImage create(URL url, int requestedWidth, int requestedHeight, ImageFilterContainerInterface filters) throws IOException {
        BufferedImage thumbnail = null;
        synchronized (sync) {
            LOG.debug( "Opening thumbnail for " + url.getFile());
            BufferedImage image = getExifThumbnail(url);
            if (image == null) {
                thumbnail = super.create(url, requestedWidth, requestedHeight, filters);
            } else if(scaleMetadataThumbnails.booleanValue()) {
                thumbnail = createThumbnail(image, requestedWidth, requestedHeight, filters);
            } else {
                thumbnail = image;
            }
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace( "Returning thumbnail " + thumbnail);
        }
        return thumbnail;
    }

    protected BufferedImage getExifThumbnail(URL url) throws IOException {
        Metadata metadata = null;
        try {
            metadata = JpegMetadataReader.readMetadata(url.openStream());
        } catch (Exception e) {
            LOG.error("Error when reading metadata from "+(url!=null?url.getFile():"null"),e);
            return null;
        }

        ExifDirectory directory = (ExifDirectory) metadata.getDirectory(ExifDirectory.class);
        try {
            byte[] thumbnailData = directory.getThumbnailData();
            if (thumbnailData != null) {
                return ImageIO.read(new ByteArrayInputStream(thumbnailData));
            }
        } catch (MetadataException e) {
            LOG.error("Error when reading metadata thumbnail from "+(url!=null?url.getFile():"null"),e);
        }
        return null;
    }
}