package erland.webapp.dirgallery.entity.gallery.picture;

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

import erland.webapp.common.BaseEntity;
import erland.webapp.common.EntityReadUpdateInterface;

public class Picture extends BaseEntity implements EntityReadUpdateInterface {
    private String id;
    private Integer gallery;
    private String name;
    private String fileName;
    private String shortName;
    private String directory;
    private String fullPath;
    private Integer typeOfFile;
    private Long fileSize;
    public static final int PICTUREFILE = 0;
    public static final int MOVIEFILE = 1;
    private static final Integer PICTUREFILEID = new Integer(PICTUREFILE);
    private static final Integer MOVIEFILEID = new Integer(MOVIEFILE);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        if (directory != null && !(directory.endsWith("/") || directory.endsWith("\\"))) {
            this.directory = directory + "/";
        } else {
            this.directory = directory;
        }
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getTypeOfFile() {
        return typeOfFile;
    }

    public void setTypeOfFile(Integer typeOfFile) {
        this.typeOfFile = typeOfFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void preReadUpdate() {
        if (id != null) {
            id = id.replaceAll("[@:]", "/");
            id = directory + id;
        }
    }

    public void postReadUpdate() {
        if (id != null) {
            id = id.substring(directory.length());
            int extStart = id.lastIndexOf(".");
            if (extStart >= 0) {
                String extension = id.substring(extStart);
                if (extension.equalsIgnoreCase(".mpg") || extension.equalsIgnoreCase(".mpeg") || extension.equalsIgnoreCase(".mov") || extension.equalsIgnoreCase(".avi")) {
                    typeOfFile = Picture.MOVIEFILEID;
                } else {
                    typeOfFile = Picture.PICTUREFILEID;
                }
                setFileName(id);
                setName(id.substring(0, extStart));
            } else {
                setName(id);
                setFileName(id);
            }
            id = id.replaceAll("[/\\\\]", ":");
/*
            int pos = id.lastIndexOf("/");
            int pos2 = id.lastIndexOf("\\");
            if(pos2>pos) {
                pos=pos2;
            }
            id=id.substring(pos+1);
*/
        } else {
            setName(id);
            setFileName(id);
        }

        if (shortName != null) {
            int extStart = shortName.lastIndexOf(".");
            if (extStart > 0) {
                shortName = shortName.substring(0, extStart);
            }
        }
    }
}
