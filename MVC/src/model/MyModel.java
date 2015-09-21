package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;



import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Maze3dByteArr;
import algorithms.mazeGenarators.MyMaze3dGenerator;
import algorithms.mazeGenarators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattenDistance;
import algorithms.search.MazeSearchable;
import algorithms.search.Solution;
import controller.Command;
import controller.Controller;
import controller.MyController;
/**
 *class MyModel that implements from Model 
 *
 */
public class MyModel implements Model {
	private MyController controller;
	private HashMap<String, Maze3d> mazes;
	private HashMap<String, Command> commands;
	private HashMap<String, Solution<Position>> solutions;
	
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
	public void setController(MyController controller) {
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
		this.commands =commands;
	}

	@Override
	public void generate3DMaze(String[] args) {
		//checking if we have all the data we need
		if (args.length < 4) 
		{
			controller.outPut("not enough data");
		}
		Integer x,y,z;
		//cheking if the the 3 other variabels are int type
		if (((x=MyController.tryParseInt(args[1]))!=null)&& ((y=MyController.tryParseInt(args[2]))!=null)&&
			((z=MyController.tryParseInt(args[2]))!=null))
		{
			//creating the maze and adding it to the hasmap
			Maze3d maze = new MyMaze3dGenerator().generate((int)x, (int)y, (int)z);
			mazes.put(args[0], maze);
			controller.outPut("maze "+args[0]+"is ready");
		}
	}
	
	@Override
	public void saveMaze(String[] args) {
		if (args.length < 2) 
		{
			controller.outPut("not enough data");
		}
		else if(mazes.containsKey(args[0]))
		{
			try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(args[1]+".maz"));
			out.write(mazes.get(args[0]).toByteArray());
			out.flush();
			out.close();
			}
			catch (Exception e) {
				controller.outPut("Couldn't save the maze - fatal error");
			}
		}
		else
		{
			controller.outPut("Maze does not exist");
		}
	}
	
	@Override
	public void loadMaze(String[] args) {
		if (args.length < 2) 
		{
			controller.outPut("not enough data");
		}
		else if(!mazes.containsKey(args[1]))
		{
			try {
				InputStream in=new MyDecompressorInputStream(new FileInputStream(args[0]+".maz"));
				byte b[]=new byte[(int)(new File(args[0]+".maz")).length()];
				in.read(b);
				in.close();
				Maze3d loaded=new Maze3dByteArr(b);
				mazes.put(args[1], loaded);
			}
			catch (Exception e) {
				controller.outPut("Error in loading file");
			}
		}
		else
		{
			controller.outPut("Maze already exist");
		}
		
	}
	
	@Override
	public void solve(String[] args)
	{
		if (args.length < 2) 
		{
			controller.outPut("not enough data");
		}
		else if(mazes.containsKey(args[0]))
		{
			//Checking which algorithm was chosen and if a solution was already existing for the maze, it overwrites it.
			if(args[1].equals("BFS"))
			{
				Solution<Position> s=  new BFS<Position>().search(new MazeSearchable(mazes.get(args[0]),1)); //get Solution of the maze
				solutions.put(args[0], s);
			}
			else if(args[1].equals("A* mnhtn"))
			{
				Solution<Position> s=  new AStar<Position>(new MazeManhattenDistance()).search(new MazeSearchable(mazes.get(args[0]),1)); //get Solution of the maze
				solutions.put(args[0], s);
			}
			else if(args[1].equals("A* air"))
			{
				Solution<Position> s=  new AStar<Position>(new MazeAirDistance()).search(new MazeSearchable(mazes.get(args[0]),1)); //get Solution of the maze
				solutions.put(args[0], s);
			}
			else
			{
				controller.outPut("no algorithm was chosen");
			}
		}
		else
		{
			controller.outPut("no such maze exist");
		}
	}
	
	@Override
	public void exit(String[] args) {
		
	}
	@Override
	public Maze3d getMaze(String mazeName) {
		Maze3d maze = mazes.get(mazeName);
		return maze;
	}
}
