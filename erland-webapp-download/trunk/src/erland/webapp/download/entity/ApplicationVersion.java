package erland.webapp.download.entity;

import erland.webapp.common.BaseEntity;
import erland.webapp.common.EntityReadUpdateInterface;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

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

public class ApplicationVersion extends BaseEntity implements EntityReadUpdateInterface {
    private String id;
    private String name;
    private String directory;
    private String version;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void preReadUpdate() {
        if (id != null) {
            id = id.replaceAll("[@:]", "/");
            id = directory + id;
        }
    }

    public void postReadUpdate() {
        if(getId()!=null) {
            String pathWithoutExtension = getId();
            int pos = getId().lastIndexOf(".");
            if(pos>=0) {
                pathWithoutExtension = pathWithoutExtension.substring(0,pos);
            }
            try {
                BufferedReader reader = new BufferedReader(new FileReader(pathWithoutExtension+".txt"));
                StringBuffer description = new StringBuffer();
                String line = reader.readLine();
                while(line!=null) {
                    description.append(line);
                    line = reader.readLine();
                }
                setDescription(description.toString());
            } catch (IOException e) {
                setDescription(null);
            }
            id = id.substring(directory.length());
            id = id.replaceAll("[/\\\\]", ":");
        }
        if(getName()!=null) {
            String nameWithoutExtension = getName();
            int pos = getName().lastIndexOf(".");
            if(pos>=0) {
                nameWithoutExtension = nameWithoutExtension.substring(0,pos);
            }
            pos = nameWithoutExtension.lastIndexOf("-");
            if(pos>=0) {
                setVersion(nameWithoutExtension.substring(pos+1));
            }else {
                setVersion(null);
            }
        }
    }
}