package model;

import java.util.HashMap;

import controller.Command;

/**
 * interface of model
 *
 */
public interface Model {
	/**
	 * settings the commands
	 * @param commands the mapping of the commands 
	 */
	void setCommands(HashMap<String,Command<Model>> commands);
	/**
	 * Performing the command
	 * @param str which command to perform
	 */
	void performCommand(String str);
	/**
	 * Generating maze
	 * @param str the name of the maze
	 * @param x setting the maze axis
	 * @param y setting the maze axis
	 * @param z setting the maze axis
	 */
	void generate3DMaze(String str, int x, int y,int z);
}
