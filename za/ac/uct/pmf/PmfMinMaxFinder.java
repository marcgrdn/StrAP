package za.ac.uct.pmf;

import java.io.*;

import za.ac.uct.util.PmfFileHandler;

/**
 * Finds minima and maxima in a given NAMD colvars module pmf file within phi and psi limits
 * 
 * @author marc
 *
 */
public class PmfMinMaxFinder {

	private String[][] array;
	private double upperPhi;
	private double lowerPhi;
	private double upperPsi;
	private double lowerPsi;
	
	
	public void run(String filepath, double lowerPhi, double upperPhi, double lowerPsi, double upperPsi) {
		try {
			File pmf = new File(filepath);
			array = PmfFileHandler.getFileArray(pmf);
			this.lowerPhi = lowerPhi;
			this.upperPhi = upperPhi;
			this.lowerPsi = lowerPsi;
			this.upperPsi = upperPsi;
			
			
			getMin();
			getMax();
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Prints the minimum free energy value in the provided phi; psi range
	 */
	private void getMin() {
		double min = 1000.0;
		double phi = 1000.0;
		double psi = 1000.0;
		
		for(int i=0; i<array.length; i++) {
			if(Double.parseDouble(array[i][0]) >= lowerPhi 
				&& Double.parseDouble(array[i][0]) <= upperPhi 
				&& Double.parseDouble(array[i][1]) >= lowerPsi 
				&& Double.parseDouble(array[i][1]) <= upperPsi) {
					if(Double.parseDouble(array[i][2]) < min) {
						phi= Double.parseDouble(array[i][0]);
						psi = Double.parseDouble(array[i][1]);
						min = Double.parseDouble(array[i][2]);
					}
			}
			
		}
		System.out.println("Minimum of "+min+" located at (phi, psi) = "+phi+", "+psi);
	}
	
	
	/**
	 * Prints the maximum free energy value in the provided phi; psi range
	 */
	private void getMax() {
		double max = -1000.0;
		double phi = 1000.0;
		double psi = 1000.0;
		
		for(int i=0; i<array.length; i++) {
			if(Double.parseDouble(array[i][0]) >= lowerPhi
				&& Double.parseDouble(array[i][0]) <= upperPhi 
				&& Double.parseDouble(array[i][1]) >= lowerPsi
				&& Double.parseDouble(array[i][1]) <= upperPsi) {
					if(Double.parseDouble(array[i][2]) > max) {
						phi = Double.parseDouble(array[i][0]);
						psi = Double.parseDouble(array[i][1]);
						max = Double.parseDouble(array[i][2]);
					}
					
			}
		}
		System.out.println("Maximum of "+max+" located at (phi, psi) = "+phi+", "+psi);
	}
}
