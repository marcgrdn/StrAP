package za.ac.uct;


public class Main {
	public static void main(String[] args) {
		
		System.out.println("Welcome to the Streptococcus pneumoniae Analysis Application (StrAP)");
		
		Init init = new Init();
		try {
			init.init();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
