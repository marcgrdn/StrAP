package za.ac.uct.lineplot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Determines the change in free energy (deltaG) between global maximum and minimum. Within range of psi values.
 * Used as an alternative to BezierDeltaG due to different file formats (no double spaces in minimum path files).
 * 
 * @author marc
 *
 */
public class MinEnergyDeltaG {
	private String filepath;
	private double psi1;
	private double psi2;
	private Double maxAngle = -1000.0;
	private Double minAngle = -1000.0;
	
	public void run(double psi1, double psi2, String filepath) {
		try {
			this.filepath = filepath;
			this.psi1 = psi1;
			this.psi2= psi2;
			String[][] array = getFileArray();
			printMinMaxDelta(array);
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void printMinMaxDelta(String[][] array) {
		Double max = getMax(array);
		Double min = getMin(array);
		System.out.println("minAngle = " + minAngle + ";  maxAngle = " + maxAngle);
		System.out.println(max + " - " + min + " = " + (max-min));
	}
	
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String[][] getFileArray() throws Exception {
		// setup readers and writers
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		
		String line;
		
		// count number of lines in the file
		int i = 0;
		while((line=reader.readLine()) != null) {
			// ignore comment lines starting with hash
			if (line.startsWith("#"))
				continue;
			
			// ignore blank lines
			if (line.trim().length() == 0)
				continue;
			
			i++;
		}
		
		String[][] array = new String[i][2];
		
		// to get back to beginning of stream - easier than marking and resetting
		reader = new BufferedReader(new FileReader(filepath));
		
		i=0;
		while((line=reader.readLine()) != null) {
			// ignore comment lines starting with hash
			if (line.startsWith("#"))
				continue;
			
			// ignore blank lines
			if (line.trim().length() == 0)
				continue;
			
			// get current value and save it to array
			String [] tokens = line.split(" ");
			array[i][0] = tokens[1];
			array[i][1] = tokens[2];
			i++;
		}
		reader.close();
		
		return array;
	}
	
	/**
	 * Returns the largest energy value
	 * @param array String[][] containing every line of the bezier points table input file
	 * @return max Largest energy value
	 */
	private Double getMax(String[][] array) {
		double max = -1000.0;
		double tmp;
		
		for(int i=0; i<array.length; i++) {
			tmp =  Double.parseDouble(array[i][0]);
			if(tmp>=psi1 && tmp<=psi2) {
				if(Double.parseDouble(array[i][1])>max) {
					max = Double.parseDouble(array[i][1]);
					maxAngle = tmp;
				}
			}
		}
		
		return max;
	}
	
	/**
	 * Returns the smallest energy value
	 * @param array String[][] containing every line of the bezier points table input file
	 * @return min Smallest energy value
	 */
	private Double getMin(String[][] array) {
		double min = 1000.0;
		double tmp;
		
		for(int i=0; i<array.length; i++) {
			tmp =  Double.parseDouble(array[i][0]);
			if(tmp>=psi1 && tmp<=psi2) {
				if(Double.parseDouble(array[i][1])<min) {
					min = Double.parseDouble(array[i][1]);
					minAngle = Double.parseDouble(array[i][0]);
				}
			}
		}
		
		return min;
	}
}
