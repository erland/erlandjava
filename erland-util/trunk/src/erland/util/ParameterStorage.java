package erland.util;
import java.io.*;
import java.util.*;

/**
 * Get, set or delete parameters stored in a file
 * @author Erland Isaksson
 */
public class ParameterStorage
	extends ParameterStorageString
{
	/**
	 * The filename of the file that holds the parameters
	 */
	protected String file;
	
	

    /**
     * @param file The name of the file which the parameters should be
     *             accessed from
     */
	public ParameterStorage(String file)
	{
		this.file = file;
		init(loadData(file),"parameters");
	}

    /**
     * @param file The name of the file which the parameters should be
     *             accessed from
     * @param documentName The name of the section in the file where parameters
     *                     are stored
     */
	public ParameterStorage(String file, String documentName)
	{
		this.file = file;
		init(loadData(file),documentName);
	}
	
	/**
	 * Reads data from file
     * @param file The name of the file which the parameters should be
     *             accessed from
     * @return The string that was read from the file
	 */
	protected String loadData(String file)
	{
		BufferedReader r=null;
		String str=null;
		try {
			r = new BufferedReader(new FileReader(file));

			str = new String();
			String line;
			line = r.readLine();
			while(line!=null) {
				str+=line;
				line = r.readLine();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	
	/**
	 * Save the parameters in {@link #data data} to file.
	 * This will be automatically called in all the parameter access methods
	 */
	protected void save() 
	{
		if(data!=null) {
			try {
				BufferedWriter w = new BufferedWriter(new FileWriter(file));
				w.write(data.toString());
				w.flush();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
