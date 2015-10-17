package view;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;

/**
 * 
 * interface View
 *
 */
public interface View  {
	/**
	 * start the program
	 */
	void start();
	
	/**
	 * print the output we get
	 * @param string the output we will print
	 */
	void printOutput(String string);

	/**
	 * printing the directory path and files
	 * @param arr path of directory
	 */
	void printDir(String[] arr);

	/**
	 * display maze with the name mazeName
	 * @param maze the name of the maze we want to display
	 */
	void displayMaze(Maze3d maze);

	/**
	 * display cross section by axis (x,y,z), index and maze name
	 * @param arr array of ints
	 * @param axis axis we print by
	 * @param index index in the axis
	 */
	void displayCrossSectionBy(int[][] arr, String axis, String index);

	/**
	 * displaying the size of the maze which is in the memory
	 * @param size the size of the maze
	 * @param name the name of the maze
	 */
	void displayMazeSize(int size, String name);

	/**
	 * displaying the size of a file 
	 * @param size the size of the file
	 * @param name the name of the file
	 */
	void displayFileSize(long size, String name);

	/**
	 * display the solution of a maze
	 * @param sol the Solution of the maze
	 */
	void displaySolution(Solution<Position> sol);
	/**
	 * Handling errors
	 * @param string the error message
	 */
	void error(String string);

	/**
	 * change to the view style property
	 * @param args view style name
	 */
	void changeProp(String[] args);

	/**
	 * update that a maze was generated in the model
	 * @param string
	 */
	void mazegenerated(String string);

	
	
}
