package model;

import java.util.HashMap;

import algorithms.mazeGenarators.Maze3d;
import controller.Command;
import controller.Controller;
/**
 *class MyModel that implements from Model 
 *
 */
public class MyModel implements Model {
	private Controller controller;
	private HashMap<String, Maze3d> mazes;
	private HashMap<String, Command> commands;
	
	/**
	 * Ctor of MyModel
	 */
	public MyModel() {
		mazes = new HashMap<String, Maze3d>();
		commands = new HashMap<String, Command>();
	}
	/**
	 * get the model controller
	 * @return controller
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * set a controller to the model
	 * @param controller
	 */
	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}
	/**
	 * get hash map of the mazes in the model
	 * @return HashMap of mazes
	 */
	public HashMap<String, Maze3d> getMazes() {
		return mazes;
	}
	/**
	 * set hash map of  mazes in the model
	 * @param mazes HashMap of mazes
	 */
	public void setMazes(HashMap<String, Maze3d> mazes) {
		this.mazes = mazes;
	}
	/**
	 * get hash map of the commands in the model
	 * @return HashMap of commands
	 */
	public HashMap<String, Command> getCommands() {
		return commands;
	}
	
	/**
	 * set hash map of  mazes in the model
	 * @param commands HashMap of commands
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		// TODO Auto-generated method stub

	}

	@Override
	public void performCommand(String str) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generate3DMaze(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void saveMaze(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void loadMaze(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void solve(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void exit(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
