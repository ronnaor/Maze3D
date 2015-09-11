package algorithms.search;

import java.util.ArrayList;
/**
 *generic class Solution<T>
 * @param <T> the type of class we will work with 
 */
public class Solution<T> {
	ArrayList<T> path;
	int numMoves;
	
	/**
	 * CTOR
	 * @param arr ArrayList<T> 
	 */
	public Solution(ArrayList<T> arr) { //CTOR
		path = new ArrayList<T>();
		for(T t: arr) 
		{
			path.add(t);
		}
		numMoves = path.size();
	}
	/**
	 * copy CTOR
	 * @param sol Solution<T> we will copy
	 */
	public Solution(Solution<T> sol) { //copy CTOR
		path = new ArrayList<T>();
		for(T t: sol.path)
		{
			path.add(t);
		}
		numMoves = path.size();
	}
	/**
	 * getter for path
	 * @return list of the path
	 */
	public ArrayList<T> getPath() {
		return path;
	}
	/**
	 * setter for path
	 * @param path ArrayList<T> of a path to insert in path
	 */
	public void setPath(ArrayList<T> path) {
		this.path = path;
	}
	/**
	 * getter for numMoves
	 * @return the number of moves in the path
	 */
	public int getNumMoves() {
		return numMoves;
	}
	/**
	 * setter for numMoves
	 * @param numMoves the number of moves in a path
	 */
	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}
	

}
