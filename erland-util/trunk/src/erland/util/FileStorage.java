package erland.util;

import java.io.*;
import java.util.*;

/**
 * Save a string to a file
 * @author Erland Isaksson
 */
public class FileStorage implements StorageInterface
{
	/**
	 * The filename of the file where the string is stored
	 */
	protected String file;

    /**
     * @param file The name of the file which the string should
     *             be accessed frin
     */
	public FileStorage(String file)
	{
		this.file = file;
	}

	/**
	 * Reads data from file
     * @return The string that was read from the file
	 */
	public String load()
	{
		BufferedReader r=null;
		StringBuffer str=null;
		try {
			r = new BufferedReader(new FileReader(file));

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


	/**
	 * Save the string to file.
     * @param data The string to store
	 */
	public void save(String data)
	{
        Log.println(this,"Save to: "+file);
        Log.println(this,data);
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(file));
            w.write(data);
            w.flush();
        }catch(IOException e) {
            e.printStackTrace();
        }
	}
}
