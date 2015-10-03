package presenter;

import java.io.Serializable;

/**
 * class of the Properties we will use
 *
 */
public class Properties implements Serializable {

	/**
	 *  serialVersionUID is used as a version control in a Serializable class
	 */
	private static final long serialVersionUID = 1L;

	private int numThreads;
	private String generateAlgorithm;
	private String solveAlgorithm;
	private String viewStyle;
	
	/**
	 * default Ctor
	 */
	public Properties() {
		this.numThreads =0;
		this.solveAlgorithm = null;
		this.generateAlgorithm = null;
		this.viewStyle = null;
	}
	/**
	 * Ctor
	 * @param numThreads the number we will put in the threadPool
	 * @param generateAlgorithm the Algorithm we will use to generate maze
	 * @param solveAlgorithm the Algorithm we will use to solve a maze
	 * @param viewStyle the type of view we will use
	 */
	public Properties(int numThreads, String generateAlgorithm, String solveAlgorithm, String viewStyle) {
		this.numThreads =numThreads;
		this.solveAlgorithm = solveAlgorithm;
		this.generateAlgorithm = generateAlgorithm;
		this.viewStyle = viewStyle;
	}
	/**
	 * copy Ctor
	 * @param prop Properties we copy
	 */
	public Properties(Properties prop) {
		this.numThreads =prop.numThreads;
		this.solveAlgorithm = prop.solveAlgorithm;
		this.generateAlgorithm = prop.generateAlgorithm;
		this.viewStyle = prop.viewStyle;
	}
	/**
	 * get the number of threads
	 * @return int number of threads
	 */
	public int getNumThreads() {
		return numThreads;
	}
	/**
	 * set the number of threads
	 * @param numThreads int number of threads
	 */
	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}
	/**
	 * get the Algorithm we solve maze with
	 * @return String the Algorithm 
	 */
	public String getSolveAlgorithm() {
		return solveAlgorithm;
	}
	/**
	 * set the Algorithm we solve maze with
	 * @param solveAlgorithm String the Algorithm 
	 */
	public void setSolveAlgorithm(String solveAlgorithm) {
		this.solveAlgorithm = solveAlgorithm;
	}
	/**
	 * get the Algorithm we Generate maze with
	 * @return String the Algorithm 
	 */
	public String getGenerateAlgorithm() {
		return generateAlgorithm;
	}
	/**
	 * set the Algorithm we Generate maze with
	 * @param generateAlgorithm String the Algorithm 
	 */
	public void setGenerateAlgorithm(String generateAlgorithm) {
		this.generateAlgorithm = generateAlgorithm;
	}
	/**
	 * get the view type we will work with
	 * @return  String the view type
	 */
	public String getViewStyle() {
		return viewStyle;
	}
	/**
	 * set  the view type we will work with
	 * @param viewStyle String the view type
	 */
	public void setViewStyle(String viewStyle) {
		this.viewStyle = viewStyle;
	}
	
	
	
}
