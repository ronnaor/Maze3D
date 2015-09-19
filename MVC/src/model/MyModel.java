package model;

import java.util.HashMap;

import algorithms.mazeGenarators.Maze3d;
import controller.Command;
/**
 *class MyModel that implements from Model 
 *
 */
public class MyModel implements Model {
	private HashMap<String, Maze3d> mazes;
	private HashMap<String, Command<Model>> commands;
	
	public MyModel() {
		mazes = new HashMap<String, Maze3d>();
		commands = new HashMap<String, Command<Model>>();
	}
	
	@Override
	public void setCommands(HashMap<String, Command<Model>> commands) {
		// TODO Auto-generated method stub

	}

	@Override
	public void performCommand(String str) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generate3DMaze(String str, int x, int y, int z) {
		// TODO Auto-generated method stub

	}

}
