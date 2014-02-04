package za.ac.uct.frame;

import java.io.*;

/**
 * Prints frame numbers for disaccharides at desired conformations
 * (phi and psi values).
 * @author marc
 *
 */
public class FrameLocator {
	
	private double phi;
	private double psi;
	
	// used when looking for single matching dihedhral (primAlc mostly)
	private double angle;
	
	private String[][] array;
	private String filepath;
	
	/**
	 * find particular frames containing torsional angles to the nearest degree
	 * @param filepath trajectory file path
	 * @param phi value of desired phi
	 * @param psi value of desired psi
	 */
	public void run(String filepath, double phi, double psi) {
		this.filepath = filepath;
		this.phi = phi;
		this.psi = psi;
		try {
			getMatchingFrameNumberTwoAngles();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public FrameLocator() {
		super();
	}
	
	public FrameLocator(double phi, double psi, String filepath) {
		super();
		this.phi = phi;
		this.psi = psi;
		this.filepath = filepath;
	}
	
	
	public FrameLocator(double angle, String filepath) {
		super();
		this.angle = angle;
		this.filepath = filepath;
	}
	
	/**
	 * Used for primary alcohol dihedhral angle. Finds frames matching a single dihedhral.
	 * @throws Exception
	 */
	private void getMatchingFrameNumber(boolean useFile, String outFile) throws Exception {
		BufferedWriter bw = null;
		if(useFile==true) {
			bw = new BufferedWriter(new FileWriter(outFile));
		}
		toArray(1);
		String frame = "";
		
		for(int i=0; i<array.length; i++) {
			if(roundUp(Double.parseDouble(array[i][1])) == angle) {
				frame = array[i][0];
				System.out.println("Frame " +frame+ " original value (primAlc Dihedhral): "+array[i][1]);
				if(useFile==true) {
					bw.write("Frame " +frame+ " original value (primAlc Dihedhral): "+array[i][1]);
					bw.newLine();
					bw.flush();
				}
			}
		}
		bw.close();
		
		// puts some spaces in to separate multiple runs
		if(frame.length()!=0){
			//System.out.println("Frame number "+frame+" contains phi="+phi+" and psi="+psi);
			System.out.println();
			System.out.println();
			System.out.println();
		} else {
			System.out.println("No matching frames found for phi, psi = "+phi+", "+psi);
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}
	
	/**
	 * Used for glycosidic bond phi and psi dihedhrals. Finds matching frames.
	 * @throws Exception
	 */
	private void getMatchingFrameNumberTwoAngles() throws Exception {
		toArray(2);
		String frame="";
		
		for(int i=0; i<array.length; i++) {
			if(roundUp(Double.parseDouble(array[i][1])) == phi) {
				if(roundUp(Double.parseDouble(array[i][2]))==psi) {
					frame = array[i][0];
					System.out.println("Frame " +frame+ " original values (phi,psi): ("+array[i][1]+", "+array[i][2]+")");
				}
			}
		}
		if(frame.length()!=0){
			//System.out.println("Frame number "+frame+" contains phi="+phi+" and psi="+psi);
			System.out.println();
			System.out.println();
			System.out.println();
		} else {
			System.out.println("No matching frames found for phi, psi = "+phi+", "+psi);
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}
	
	
	/**
	 * populates a String[][] with the contents of the input file.<br />
	 * If processing 1 angle 1 is provided as argument, if 2 angles then 2 is provided as argument.
	 * @throws Exception
	 */
	private void toArray(int angleNumber) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		
		if (angleNumber==2){
			String line = "";
			int i = 0;
			
			// count number of lines in file
			while(br.readLine() != null) {
				i++;
			}
			
			// reset for next read through
			br = new BufferedReader(new FileReader(filepath));
			
			array = new String[i][3];
			
			i = 0;
			while((line=br.readLine()) != null) {
				String[] tmp = line.trim().split(" ");
				
				// populate frame numbers
				array[i][0] = tmp[0];
				
				// populate phi
				array[i][1] = tmp[1];
				
				//populate psi
				array[i][2] = tmp[2];
				
				i++;
			}
			//printArray();
		}
		
		if(angleNumber==1) {
			String line = "";
			int i = 0;
			
			// count number of lines in file
			while(br.readLine() != null) {
				i++;
			}
			
			// reset for next read through
			br = new BufferedReader(new FileReader(filepath));
			
			array = new String[i][2];
			
			i = 0;
			while((line=br.readLine()) != null) {
				String[] tmp = line.trim().split(" ");
				
				// populate frame numbers
				array[i][0] = tmp[0];
				
				// populate angle
				array[i][1] = tmp[1];
				
				i++;
			}
		}
		
	}
	
	
	/**
	 * Debugging - used to make sure array is populated
	 */
	private void printArray() {
		for(int i=0; i<array.length; i++) {
			System.out.println(array[i][0]+" "+array[i][1]+" "+array[i][2]);
		}
	}
	
	/**
	 * Rounds angle to 2 decimal places
	 * @param angle
	 * @return
	 */
	private double roundUp2(double angle) {
		double tmp = angle*100;
		tmp = Math.round(tmp);
		tmp = tmp/100;
		return tmp;
	}
	
	/**
	 * Rounds angle to 1 decimal places
	 * @param angle
	 * @return
	 */
	private double roundUp1(double angle) {
		double tmp = angle*10;
		tmp = Math.round(tmp);
		tmp = tmp/10;
		return tmp;
	}
	
	/**
	 * Rounds angle with no decimal places
	 * @param angle
	 * @return
	 */
	private double roundUp(double angle) {
		return Math.round(angle);
	}
}
