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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 * Load and Save a string to a InputStream and OutputStream
 * @author Erland Isaksson
 */
public class StreamStorage implements StorageInterface
{
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(StreamStorage.class);
	/** The InputStream to read the string from	 */
	private InputStream input;
    /** The OutputStream to write the string to */
    private OutputStream output;

    /**
     * Creates a new FileStorage object
     * @param input The InputStream that data should be read from, if null nothing will be read
     * @param output The OutputStream that data should be written to, if null nothing will be written
     */
	public StreamStorage(InputStream input, OutputStream output)
	{
		this.input = input;
        this.output = output;
	}

	/**
	 * Reads data from stream
     * @return The string that was read from the stream
	 */
	public String load()
	{
        if(input!=null) {
            BufferedReader r=null;
            StringBuffer str=null;
            try {
                r = new BufferedReader(new InputStreamReader(input));

                str = new StringBuffer(100000);
                String line;
                line = r.readLine();
                while(line!=null) {
                    str.append(line);
                    line = r.readLine();
                }
            }catch(IOException e) {

            }
            if(LOG.isDebugEnabled()) {
                LOG.debug("Load data from stream:");
                LOG.debug(str!=null?str.toString():"");
            }

            if(str!=null) {
                return str.toString();
            }
        }
        return null;
	}


	/**
	 * Save the string to stream.
     * @param data The string to store
	 */
	public void save(String data)
	{
        if(output!=null) {
            LOG.debug("Save data to stream:");
            LOG.debug(data);
            try {
                BufferedWriter w = new BufferedWriter(new OutputStreamWriter(output));
                w.write(data);
                w.flush();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
	}
}
