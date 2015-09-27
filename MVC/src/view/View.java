package view;

import java.util.HashMap;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
import controller.Command;
import controller.MyController;
/**
 * interface view
 *
 */
public interface View {
	/**
	 * setting the commands
	 * @param map gets map of commands
	 */
	void setCommands(HashMap<String,Command> commands);
	/**
	 * printing the directory path and files
	 * @param str path of directory
	 */
	void printDir(String[] path);
	/**
	 * set controller to the view
	 * @param controller
	 */
	void setController(MyController controller);
	/**
	 * getting the commands from MyView
	 * @return the commands of HashMap
	 */
	HashMap<String, Command> getCommands();
	/**
	 * display maze with the name mazeName
	 * @param mazeName the name of the maze we want to display
	 */
	void display(Maze3d maze);
	
	/**
	 * display cross section by axis (x,y,z), index and maze name
	 * @param arr arr of ints
	 * @param axis axis we print by
	 * @param index index in the axis
	 */
	void displayCrossSectionBy(int[][] arr, String axis, String index);
/**
 * displaying the size of the maze which is in the memory
 * @param maze the size of the maze
 * @param args the name of the maze
 */
	void mazeSize(int maze,String args);
	/**
	 * display the maze size in a file
	 * @param args the name of the file
	 */
	void fileSize(String[] args);
	/**
	 * display the solution of a maze
	 * @param args the name of the maze
	 */
	void displaySolution(Solution<Position> solve);
	/**
	 * send what to print to output
	 * @param str the output we want to display
	 */
	void printOutput(String str);
	/**
	 * starting the program
	 */
	void start();
	/**
	 * getting the commands of the controller
	 * @return the commands in hasmap
	 */
	HashMap<String, Command> controllerCommands();
	
	
}
