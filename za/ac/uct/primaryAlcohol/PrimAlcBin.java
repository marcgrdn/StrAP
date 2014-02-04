package za.ac.uct.primaryAlcohol;

/**
 * Collection of getters and setters for 
 * primary alcohol bins
 * @author marc
 *
 */
public class PrimAlcBin {
	/*
	 * TODO: involve inheritance or some similar 
	 * relationship structure to allow for 
	 * creation of different bin formats for a 
	 * wider range of analysis in the future
	 */
	private int angle;
	private int freq=0;
	
	public PrimAlcBin() {
		
	}
	
	public PrimAlcBin(int angle) {
		this.angle = angle;
	}
	
	/**
	 * sets bin's angle
	 * @param angle desired bin angle
	 */
	public void setAngle(int angle) {
		this.angle = angle;
	}
	
	/**
	 * sets the bin's frequency of occurrence
	 * @param freq desired frequency of occurrence
	 */
	public void setFrequency(int freq) {
		this.freq = freq;
	}
	
	/**
	 * increments the bin's frequency by 1
	 */
	public void incFreq() {
		freq++; 
	}
	
	/**
	 * returns angle corresponding to bin
	 * @return
	 */
	public int getAngle() {
		return angle;
	}
	
	/**
	 * returns frequency of occurrence of bin
	 * @return
	 */
	public int getFrequency() {
		return freq;
	}
}
