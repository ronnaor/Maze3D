package view;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
import presenter.Presenter;

/**
 * 
 * interface View
 *
 */
public interface View {
	
	void printOutput(String string);
	/**
	 * printing the directory path and files
	 * @param str path of directory
	 */
	void printDir(String[] arr);
	/**
	 * display cross section by axis (x,y,z), index and maze name
	 * @param arr arr of ints
	 * @param axis axis we print by
	 * @param index index in the axis
	 */
	void displayCrossSectionBy(int[][] arr, String string, String string2);
	/**
	 * display the solution of a maze
	 * @param args the name of the maze
	 */
	void displaySolution(Solution<Position> sol);
	/**
	 * display maze with the name mazeName
	 * @param mazeName the name of the maze we want to display
	 */
	void display(Maze3d maze);
	/**
	 * set presenter to the view
	 * @param presenter
	 */
	void setPresenter(Presenter presenter);
/**
 * displaying the file size of maze file
 * @param args the file path
 */
	void fileSize(String[] args);
	/**
	 * displaying the size of the maze which is in the memory
	 * @param maze the size of the maze
	 * @param args the name of the maze
	 */
	void mazeSize(int maze, String args);
}
