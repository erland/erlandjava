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

import erland.webapp.common.DescriptionTagHelper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class FileMetadataHandler implements MetadataHandlerInterface {
    private Map metaDataMap = new HashMap();
    private boolean onlySelected;

    public FileMetadataHandler(boolean onlySelected) {
        this.onlySelected = onlySelected;
    }

    public boolean load(String filename) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BufferedInputStream input = null;
        try {
            try {
                File file = new File(filename);
                input = new BufferedInputStream(new FileInputStream(file));
                addField("File Size",getFileSizeString(file.length()));
                addField("File Date/Time",dateFormat.format(new Date(file.lastModified())));
            } catch (FileNotFoundException e) {
                URLConnection urlConnection = new URL(filename).openConnection();
                input = new BufferedInputStream(urlConnection.getInputStream());
                addField("File Size",""+getFileSizeString(urlConnection.getContentLength()));
                addField("File Date/Time",dateFormat.format(new Date(urlConnection.getLastModified())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input != null;
    }

    private void addField(String name, String value) {
        if(DescriptionTagHelper.getInstance().getDescription("common-metadatafielddescription",name)!=null) {
            metaDataMap.put(name,value);
        }
    }
    public String[] getNames() {
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
    private String getFileSizeString(long size) {
        final NumberFormat formatNoDecimal = new DecimalFormat("#");
        final NumberFormat formatOneDecimal = new DecimalFormat("#.##");
        if(size<1024) {
            return formatNoDecimal.format(size)+" b";
        }else if(size<1024*1024) {
            return formatNoDecimal.format((size/1024))+" kb";
        }else {
            return formatOneDecimal.format((double)size/(1024*1024))+" Mb";
        }
    }
}