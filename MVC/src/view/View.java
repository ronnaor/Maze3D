package view;

import java.util.HashMap;

import controller.Command;
/**
 * interface view
 *
 */
public interface View {
	/**
	 * setting the commands
	 * @param map gets map of commands
	 */
	void setCommands(HashMap<String,Command<View>> map);
	/**
	 * printing the directory path and files
	 * @param str path of directory
	 */
	void printDir(String str);
	/**
	 * getting the name of the command
	 * @param str the name of the command
	 */
	void getCommandFromCLI(String str);
	/**
	 * performing the command given
	 * @param str the name of the command
	 */
	void performCommand(String str);
}
