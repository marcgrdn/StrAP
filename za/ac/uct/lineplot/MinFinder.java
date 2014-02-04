package za.ac.uct.lineplot;

import java.io.*;

/**
 * Finds mins for each bin of psi (in 2.5 degree intervals) then saves them to a file
 * used to find a path of minimum energy through the energy landscape
 * @author marc
 *
 */
public class MinFinder {
	private String filepath = null;
	private String output = null;
	
	
	public void run(String filepath) {
		this.filepath = filepath;
		output = filepath+"minEnergyPath.dat";
		try {
			//initArray();
			//findMinsRelativeToPhi();
			findMinsRelativeToPsi();
		} catch(Exception e) {
			
		}
			
	}
	
	/**
	 * initial population of array with psi bin values
	 */
	/*private void initArray() {
		int j=0;
		for(double i=-178.75; i>178.75; i+=2.5) {
			// psi values must go in [x][1]- must be y coords in gnuplot
			array[j][1] = i;
			j++;
		}
	}*/
	
	// TODO: add ability to vary increments - only works for 2.5 at the moment
	
	
	/**
	 * Finds minima points using 2.5 degree phi increments (not yet used)
	 */
	private void findMinsRelativeToPhi() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		
		double phi=-178.75;
		double energy=100.0;
		double psi = -1000.0;
		
		String line ="";
		int i=0;
		while((line = br.readLine())!=null) {
			// ignore blanks
			if(line.trim().length()==0)
				continue;
			// ignore comments
			if(line.startsWith("#"))
				continue;
			
			String tokens[] = line.trim().split("  ");
			String[] tmp = tokens[0].split(" ");
			
			if(phi==Double.parseDouble(tmp[0])) {
				if(energy>Double.parseDouble(tokens[1])) {
					System.out.println("DEBUG: lower energy detected");
					energy=Double.parseDouble(tokens[1]);
					psi=Double.parseDouble(tmp[1]);
					System.out.println("DEBUG: energy="+energy);
				}
			} else {
				System.out.println("DEBUG: new phi bin encountered");
				bw.write(phi+" "+psi+" "+energy);
				bw.newLine();
				bw.flush();
				phi=Double.parseDouble(tmp[0]);
				energy=100.0;
			}
			
		}
		bw.flush();
		bw.close();
	}
	
	
	/**
	 * Walks through PMF file in 2.5 degree psi increments looking for the 
	 * lowest energy value for each metadynamics psi bin.
	 * @throws Exception
	 */
	private void findMinsRelativeToPsi() throws Exception {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		
		//double psi = -178.75;
		// obviously wrong values assigned to help with debugging
		double phi=-1000.00;
		double energy=100.0;
		
		String line ="";
		int i=0;
		
		for(double psi=-178.75; psi<=178.75; psi+=2.5) {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			
			while((line=br.readLine())!=null) {
				if(line.trim().length()==0)
					continue;
				if(line.startsWith("#"))
					continue;
				String tokens[] = line.trim().split("  ");
				String[] tmp = tokens[0].split(" ");
				
				if(psi==Double.parseDouble(tmp[1])) {
					if(energy>Double.parseDouble(tokens[1])) {
						energy=Double.parseDouble(tokens[1]);
						phi=Double.parseDouble(tmp[0]);
					}
				}
			}
			bw.write(phi+" "+psi+" "+energy);
			bw.newLine();
			bw.flush();
			phi=-1000.0;
			energy=100.0;
			br.close();
		}
		
		
		/*while((line = br.readLine())!=null) {
			// ignore blanks
			if(line.trim().length()==0)
				continue;
			// ignore comments
			if(line.startsWith("#"))
				continue;
			
			String tokens[] = line.trim().split("  ");
			String[] tmp = tokens[0].split(" ");
			
		}*/
		bw.flush();
		bw.close();
	}
	
}
