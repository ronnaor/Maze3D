package model;

import java.util.HashMap;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
import controller.Command;
import controller.MyController;

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
	 * Generating maze
	 * @param args array of strings, the name of the maze, and the x,y,z sizes
	 */
	void generate3DMaze(String[] args);
	/**
	 * set a controller to the model
	 * @param controller
	 */
	void setController(MyController controller);
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
	 * @return 
	 */
	void solve(String[] args);
	/**
	 * exit from the run, close all open files and threads
	 * @param args  
	 */
	void exit(String[] args);
	/**
	 * getting the maze
	 * @param mazeName the maze we want
	 * @return the maze
	 */
	Maze3d getMaze(String mazeName);
}
