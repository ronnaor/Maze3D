package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import controller.Controller;
/**
 * class MyView that implements from View
 *
 */
public class MyView implements View {
	private Controller controller;
	private HashMap<String, Command> commands;
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
	 *  @param map HashMap of commands
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}

	@Override
	public void printDir(String[] path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCommandFromCLI(String str) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(String[] mazeName) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void displayCrossSectionBy(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mazeSize(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void fileSize(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void displaySolution(String[] args) {
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
	@Override
	public HashMap<String, Command> getCommands() {
		return commands;
	}
	/**
	 * get the view controller
	 * @return controller
	 */
	public Controller getController() {
		return controller;
	}
	/**
	 * set controller to the view
	 * @param controller
	 */
	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}
	

}
