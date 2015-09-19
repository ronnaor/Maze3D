package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
/**
 * class MyView that implements from View
 *
 */
public class MyView implements View {

	private HashMap<String, Command<View>> commands;
	private CLI cli;
	
	/**
	 * CTOR of MyView
	 * @param in the input stream object
	 * @param out the output stream object
	 */
	public MyView(BufferedReader in,PrintWriter out) {
		cli.setIn(in);
		cli.setOut(out);
	}
	void start()
	{
		
	}
	/**
	 * settings the commands from the new map
	 */
	@Override
	public void setCommands(HashMap<String, Command<View>> map) {
		this.commands = map;
	}

	@Override
	public void printDir(String str) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCommandFromCLI(String str) {
		// TODO Auto-generated method stub

	}

	@Override
	public void performCommand(String str) {
		// TODO Auto-generated method stub

	}
	/**
	 * getting the cli object
	 * @return the cli object
	 */
	public CLI getCli() {
		return cli;
	}
	/**
	 * setting the cli object
	 * @param cli the in and out object in cli class
	 */
	public void setCli(CLI cli) {
		this.cli = cli;
	}
	/**
	 * getting the commands from MyView
	 * @return the commands of HashMap
	 */
	public HashMap<String, Command<View>> getCommands() {
		return commands;
	}

}
