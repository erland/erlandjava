package erland.webapp.download.entity;

import erland.webapp.common.BaseEntity;
import erland.webapp.common.EntityReadUpdateInterface;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.regex.Pattern;

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
    private String applicationName;
    private String applicationTitle;
    private String type;
    private String directory;
    private String version;
    private String description;
    private Date date;
    private String language;

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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationTitle() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(pathWithoutExtension+"_"+(language!=null?language:"")+".txt"));
                } catch (FileNotFoundException e) {
                    reader = new BufferedReader(new FileReader(pathWithoutExtension+".txt"));
                }
                StringBuffer description = new StringBuffer();
                String line = reader.readLine();
                while(line!=null) {
                    description.append(line);
                    line = reader.readLine();
                    if(line!=null) {
                        description.append("\n");
                    }
                }
                reader.close();
                setDescription(description.toString());
            } catch (IOException e) {
                setDescription(null);
            }

            pos = Math.max(id.lastIndexOf("/"),id.lastIndexOf("\\"));
            if(pos>=0) {
                setApplicationName(id.substring(0,pos));
                int pos2 = Math.max(getApplicationName().lastIndexOf("/"),getApplicationName().lastIndexOf("\\"));
                if(pos2>=0) {
                    setApplicationName(getApplicationName().substring(pos2+1));
                }
            }else {
                setApplicationName(null);
            }

            try {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(id.substring(0,pos)+"/title_"+(language!=null?language:"")+".txt"));
                } catch (FileNotFoundException e) {
                    reader = new BufferedReader(new FileReader(id.substring(0,pos)+"/title.txt"));
                }
                setApplicationTitle(reader.readLine());
                reader.close();
            } catch (IOException e) {
                setApplicationTitle(null);
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
                if(Pattern.matches("[a-z]+",nameWithoutExtension.substring(pos+1))) {
                    int pos2 = nameWithoutExtension.lastIndexOf("-",pos-1);
                    if(pos2>=0) {
                        setVersion(nameWithoutExtension.substring(pos2+1));
                    }else {
                        setVersion(nameWithoutExtension.substring(pos+1));
                    }
                }else {
                    setVersion(nameWithoutExtension.substring(pos+1));
                }
            }else {
                setVersion(null);
            }

            if(getVersion()!=null) {
                String version = getVersion();
                pos = version.indexOf("-");
                if(pos<0) {
                    pos = version.indexOf("_");
                }
                if(pos>=0) {
                    setVersion(version.substring(0,pos));
                    setType(version.substring(pos+1));
                }else {
                    setType(null);
                }
            }
        }
    }
}