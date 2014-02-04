package za.ac.uct;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import za.ac.uct.frame.FrameLocator;
import za.ac.uct.lineplot.MinEnergyDeltaG;
import za.ac.uct.lineplot.MinFinder;
import za.ac.uct.pmf.PmfMinMaxFinder;
import za.ac.uct.primaryAlcohol.PrimAlcConfRatio;
import za.ac.uct.primaryAlcohol.PrimAlcProbability;
import za.ac.uct.util.ScatterDataGenerator;

/**
 * Provides central ChUI menus and submenus.
 * @author marc
 *
 */
public class Init {
	/**
	 * Prints StrAP main menu. Called again after each function completes, 
	 * with the exception of the "0. Exit" option which of course exits.
	 * @throws Exception
	 */
	public void init() throws Exception {
		
		
		System.out.println();
		System.out.println("StrAP MAIN MENU:");
		System.out.println("Please select desired option: ");
		System.out.println();
		System.out.println("1. Minima and Maxima Detection (provide a phi and psi range in which to find the minimum and maximum energy values).");
		System.out.println("2. File preparation (run for scatter plot data generation and framelocator - formats xmgrace/vmd outputs into something usable by the rest of the classes).");
		System.out.println("3. Frame Locators (generate list of all frames containing particular dihedhrals).");
		System.out.println("4. Line Plot Minimum Energy Path Extraction (generates a file containing info on the lowets energy value for each psi value in a pmf file).");
			// \u0394 is UTF-8 Greek delta
		System.out.println("5. Line Plot \u0394G Calculator (find the maximum and minimum values within a provided psi range and calculate the difference).");
		System.out.println("6. Primary Alcohol Processing (binning and probability analyses)");
		System.out.println("0. Exit.");
		int input = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			input = Integer.parseInt(reader.readLine());
		} catch (Exception e) {
				e.printStackTrace();
		}
		System.out.println();
		
		
		switch(input){
		case 1:	minMax();
			break;
		case 2:	filePrep();
			break;
		case 3:	frameLoc();
			break;
		case 4:	minPath();
			break;
		case 5:	deltaG();
			break;
		case 6:	primAlcSubMenu();
			break;
		case 0: System.out.println("Exiting...\nGoodbye.");
			System.exit(0);
		default: System.out.println("Invalid selection, please select again.");
			init();
			break;
		}
	}
	
	/**
	 * Prints primary alcohol function submenu.
	 * @throws Exception
	 */
	private void primAlcSubMenu() throws Exception {
		System.out.println("PRIMARY ALCOHOL PROCESSING:");
		System.out.println("Please select desired option: ");
		System.out.println();
		System.out.println("1. Primary Alcohol Omega Dihedhral Bin (bin all omega dihedhral angles of the primary alcohol group in 1° increments).");
		System.out.println("2. Primary Alcohol Omega Dihedhral Probability Analysis (bins all omega dihedhral angles in 1° increments then determines probability of each bin occurring)");
		System.out.println("0. Return to Main Menu.");
		
		int input = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			input = Integer.parseInt(reader.readLine());
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		switch(input){
		case 1:	primAlcConf();
			break;
		case 2: primAlcProb();
			break;
		case 0: init();
			break;
		default: System.out.println("Invalid selection, please select again.");
		init();
		break;
		}
		init();
	}
	
	/**
	 * Prints ChUI input system for Min/Max detection function and hands 
	 * input over to PmfMinMaxFinder class.
	 * @throws Exception
	 */
	private void minMax() throws Exception {
		System.out.println("MINIMUM/MAXIMUM DETECTION:");
		System.out.println();
		String file = input("Please enter the absolute filepath of the pmf file:");
		
		double lowerPhi = Double.parseDouble(input("Enter lower phi value:"));
		double upperPhi = Double.parseDouble(input("Enter upper phi value:"));
		double lowerPsi = Double.parseDouble(input("Enter lower psi value:"));
		double upperPsi = Double.parseDouble(input("Enter upper psi value:"));
		
		PmfMinMaxFinder minmax = new PmfMinMaxFinder();
		minmax.run(file, lowerPhi, upperPhi, lowerPsi, upperPsi);
		init();
	}
	
	private void filePrep() throws Exception {
		System.out.println("FILE PREPARATION:");
		System.out.println();
		System.out.println("Note that this process requires that you first concatenate the phi and psi input files" +
				" into a single file and replace the tab field separators with spaces. Please use a combination of" +
				" the 'cat' and 'sed' console commands to do this.");
		System.out.println();
		System.out.println("The process will generate 2 output files. One containing frame numbers and one containing no frame numbers." +
				" 'Merged.dat' and 'MergedWithFrames.dat' will be added to the end of the input file name to create the 2 output file names.");
		System.out.println();
		String file = input("Please enter the absolute filepath of the source file:");
		
		
		ScatterDataGenerator scatter = new ScatterDataGenerator();
		scatter.run(file);
		init();
	}
	
	private void frameLoc() throws Exception {
		System.out.println("FRAME LOCATOR:");
		System.out.println();
		System.out.println("This process requires the output file containing frames as generated using" +
				" the File Preparation process (option 2 on the original menu).");
		System.out.println();
		System.out.println("Note that this proces does not write an output file but instead simply prints the list to standard out." +
				" Considering the volume of frames being selected for in this manner it was more efficient to simply print it.");
		System.out.println();
		String file = input("Please enter the absolute filepath of the source file:");
		
		double phi = Double.parseDouble(input("Enter desired phi value:"));
		double psi = Double.parseDouble(input("Enter desired psi value:"));
		
		FrameLocator frame = new FrameLocator();
		frame.run(file, phi, psi);
		init();
	}
	
	/**
	 * Prints ChUI input system for minimum path calculator function. 
	 * @throws Exception
	 */
	private void minPath() throws Exception {
		System.out.println("MINIMUM ENERGY PATH EXTRACTOR:");
		System.out.println();
		System.out.println("This process requires a NAMD colvars module pmf file as an input." +
				" It will extract the entries with the lowest energy values for each psi bin" +
				" value and write those values to a file with the name of the input file appended with 'minEnergyPath.dat'.");
		System.out.println();
		String file = input("Please enter the absolute filepath of the source file:");
		
		MinFinder min = new MinFinder();
		min.run(file);
		init();
	}
	
	
	/**
	 * Prints ChUI input system for the deltaG calculator function. 
	 * Takes user inputs and calls MinEnergyDeltaG.run(psi1, psi2, file).
	 * @throws Exception
	 */
	private void deltaG() throws Exception {
		System.out.println("DELTA G CALCULATOR:");
		System.out.println();
		System.out.println("This process requires the minimum energy path file as generated using option 4 from the main menu");
		System.out.println();
		System.out.println("Given a psi dihedhral angle range this process" +
				" will find the maximum and minimum energy values within that" +
				" range and calculate the difference between them, the deltaG.");
		System.out.println();
		String file = input("Please enter the absolute filepath of the source file:");
		
		double psi1 = Double.parseDouble(input("Enter lower psi value:"));
		double psi2 = Double.parseDouble(input("Enter upper psi value:"));
		
		MinEnergyDeltaG g = new MinEnergyDeltaG();
		g.run(psi1, psi2, file);
		init();
	}
	
	/**
	 * Prints ChUI input system for the primary alcohol conformation frequency calculator function. 
	 * Takes taget file as user keyboard input.  
	 * Then calls the PrimAlcConfRatio()calcConfFrequencies(File) method.
	 * @throws Exception
	 */
	private void primAlcConf() throws Exception {
		System.out.println("PRIMARY ALCOHOL OMEGA DIHEDHRAL BIN:");
		System.out.println();
		System.out.println("This process requires an outputted VMD dihedhral" +
				" list as generated from a dcd trajectory file. Note that prior" +
				" to running this process all tab separators in the file must be" +
				" replaced with spaces. (The 'sed' command is perfect for this)");
		System.out.println();
		System.out.println("This process will bin all omega dihedhral angles of the primary alcohol group in 1° intervals");
		System.out.println();
		String file = input("Please enter the absolute filepath of the source file:");
		
		PrimAlcConfRatio prim = new PrimAlcConfRatio();
		prim.calcConfFrequencies(file);
	}
	
	
	/**
	 * Prints ChUI input system for the primary alcohol probability calculation function. 
	 * Takes taget file as user keyboard input.  
	 * Then calls the PrimAlcConfRatio()calcConfFrequencies(File) method.
	 * @throws Exception
	 */
	private void primAlcProb() throws Exception {
		System.out.println("PRIMARY ALCOHOL OMEGA DIHEDHRAL PROBABILITY ANALYSIS:");
		System.out.println();
		System.out.println("This process requires an outputted VMD dihedhral" +
				" list as generated from a dcd trajectory file. Note that prior" +
				" to running this process all tab separators in the file must be" +
				" replaced with spaces. The 'sed' command is perfect for this.");
		System.out.println();
		System.out.println("This process will bin all omega dihedhral angles of the primary alcohol " +
				"group in 1° intervals then determine the probability of each occurring");
		System.out.println();
		String file = input("Please enter the absolute filepath of the source file:");
		
		PrimAlcProbability prob = new PrimAlcProbability();
		prob.init(file);
	}
	
	
	/**
	 * Allows for easy message display requesting inputs in ChUI
	 * @param message text displayed when requesting user input
	 * @return
	 */
	private String input(String message) {
		System.out.println(message);
		String input = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			input = reader.readLine();
			//TODO: could do with some more explicit Exception handling here - not critical
		} catch (Exception e) {
				e.printStackTrace();
		}
		return input;
	}
}
