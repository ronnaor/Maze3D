package view;

/**
 * interface View with the methods his implements will perform
 *
 */
public interface View {

	
	/** 
	 * Start the program
	 */
	public void start();
	
	/**
	 * open the server
	 */
	public void openServer();
	
	/**
	 * close the server 
	 */
	public void close();
	
	/**
	 * display error 
	 * @param err string the error 
	 */
	public void displayError(String err);
	
	/**
	 * display output
	 * @param str string the output
	 */
	public void printOutput(String str);
}
