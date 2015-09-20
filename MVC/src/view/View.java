package view;

import java.util.HashMap;

import controller.Command;
import controller.Controller;
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
	 * getting the name of the command
	 * @param str the name of the command
	 */
	void getCommandFromCLI(String str);
	
	/**
	 * set controller to the view
	 * @param controller
	 */
	void setController(Controller controller);
	/**
	 * getting the commands from MyView
	 * @return the commands of HashMap
	 */
	HashMap<String, Command> getCommands();
	/**
	 * display maze with the name mazeName
	 * @param mazeName the name of the maze we want to display
	 */
	void display(String[] mazeName);
	
	/**
	 * display cross section by axis (x,y,z), index and maze name
	 * @param args array of strings: the axis, the index and the maze name
	 */
	void displayCrossSectionBy(String[] args);
	/**
	 * display the maze size
	 * @param args the name of the maze
	 */
	void mazeSize(String[] args);
	/**
	 * display the maze size in a file
	 * @param args the name of the file
	 */
	void fileSize(String[] args);
	/**
	 * display the solution of a maze
	 * @param args the name of the maze
	 */
	void displaySolution(String[] args);
}
