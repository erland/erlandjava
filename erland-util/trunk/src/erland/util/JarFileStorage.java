package erland.util;
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

import java.io.*;
/**
 * An implementation of {@link StorageInterface} that reads data from a
 * file inside a jar file
 * @author Erland Isaksson
 */
public class JarFileStorage implements StorageInterface {
    /** The filename of the file where the string is stored */
    private String file;
    /** The class loader to use when accessing the jar file */
    private ClassLoader clsLoader;

    /**
     * Creates a new FileStorage object
     * @param file The name of the file within the jar-file which the string should
     *             be accessed from
     */
    public JarFileStorage(String file)
    {
        this.file = file;
        this.clsLoader = this.getClass().getClassLoader();
    }

    public String load()
    {
        BufferedReader r=null;
        StringBuffer str=null;
        try {
            r = new BufferedReader(new InputStreamReader(clsLoader.getResource(file).openStream()));

            str = new StringBuffer(100000);
            String line;
            line = r.readLine();
            while(line!=null) {
                str.append(line);
                line = r.readLine();
            }
        }catch(IOException e) {
            e.printStackTrace();

        }
        if(Log.isEnabled(this)) {
            Log.println(this,"Load from: "+file);
            Log.println(this,str!=null?str.toString():"");
        }

        if(str!=null) {
            return str.toString();
        }else {
            return null;
        }
    }


    public void save(String data)
    {
        // Do nothing, we can't save data in a jar file
    }
}
