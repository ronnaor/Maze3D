package model;

import java.util.HashMap;

import controller.Command;
import controller.Controller;

/**
 * interface of model
 *
 */
public interface Model {
	/**
	 * settings the commands
	 * @param commands the mapping of the commands 
	 */
	void setCommands(HashMap<String,Command> commands);
	/**
	 * Performing the command
	 * @param str which command to perform
	 */
	void performCommand(String str);
	/**
	 * Generating maze
	 * @param args array of strings, the name of the maze, and the x,y,z sizes
	 */
	void generate3DMaze(String[] args);
	/**
	 * set a controller to the model
	 * @param controller
	 */
	void setController(Controller controller);
	/**
	 * save maze to a file
	 * @param args array of strings: the name of the maze we save, and the name of the file we save to
	 */
	void saveMaze(String[] args);
	/**
	 * load maze from a file 
	 * @param args array of strings: the name of the file we load from, and the name of the maze 
	 */
	void loadMaze(String[] args);
	/**
	 * solve the maze
	 * @param args array of strings: the name of the maze, and the name of the algorithm we solve with 
	 */
	void solve(String[] args);
	/**
	 * exit from the run, close all open files and threads
	 * @param args  
	 */
	void exit(String[] args);
}
