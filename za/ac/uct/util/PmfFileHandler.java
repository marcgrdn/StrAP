package za.ac.uct.util;

import java.io.*;

/**
 * Used to work with the NAMD colvars module pmf file
 * @author marc
 *
 */
public class PmfFileHandler {
	
	
	/**
	 * Converts the NAMD colvars module pmf file into a String[][]<br />
	 * String[i][0] is phi<br />
	 * String[i][1] is psi<br />
	 * String[i][2] is energy<br />
	 * @param pmf
	 * @return
	 * @throws Exception
	 */
	public static String[][] getFileArray(File pmf) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(pmf));
		
		String line;
		
		// determine number of lines in file and set array length to match
		int i = 0;
		while((line=reader.readLine()) != null) {
			if(ignoreLine(line))
				continue;
			
			i++;
		}
		String[][] fileArray = new String[i][3];
		
		// reinitialise reader to reset to beginning of stream
		reader = new BufferedReader(new FileReader(pmf));
		
		// populate array
		i = 0;
		while((line=reader.readLine()) != null) {
			
			if(ignoreLine(line))
				continue;
			
			// populate array with current values
			String[] tokens = line.split("  ");
			String[] subtokens = line.trim().split(" ");
			fileArray[i][0] = subtokens[0];
			fileArray[i][1] = subtokens[1];
			fileArray[i][2] = tokens[1].trim();
			
			i++;
		}
		
		return fileArray;
	}
	
	/*
	 * TODO: create new holistic class for file handling and 
	 * reference ignoreLine method there instead of repeating separate 
	 * file handling instructions in each class
	 */
	
	/**
	 * Determines whether a line is to be ignored or not. If true line is to be ignored, if false line is to be included.
	 * @param line
	 * @return
	 */
	private static boolean ignoreLine(String line) {
		// ignore comment lines starting with hash
		if (line.startsWith("#"))
			return true;
		
		// ignore blank lines
		if (line.trim().length() == 0)
			return true;
		
		return false;
	}
	
}
