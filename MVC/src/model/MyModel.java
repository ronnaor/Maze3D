package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
	ExecutorService exe;
	/**
	 * Ctor of MyModel
	 */
	public MyModel() {
		this.mazes = new HashMap<String, Maze3d>();
		this.commands = new HashMap<String, Command>();
		this.solutions = new HashMap<String, Solution<Position>>();
		exe = Executors.newFixedThreadPool(13);
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
		//open new thread
		exe.execute(new Runnable() {
			
			@Override
			public void run() {
				//checking if we have all the data we need
			Integer x,y,z;
			if (args.length < 4) 
			{
				controller.outPut("not enough data");
			}		
			//cheking if the the 3 other variabels are int type
			else if (((x=MyController.tryParseInt(args[1]))!=null)&& ((y=MyController.tryParseInt(args[2]))!=null)&&
				((z=MyController.tryParseInt(args[3]))!=null))
			{
				//creating the maze and adding it to the hashmap
				Maze3d maze = new MyMaze3dGenerator().generate((int)x, (int)y, (int)z);
				if (maze != null)
				{
					mazes.put(args[0], maze);
					controller.outPut("maze "+args[1]+" is ready");
				}
				else
				{
					controller.outPut("data input does not match the command requiremnts");
				}
			}
			else
			{
				controller.outPut("data input does not match the command requiremnts");
			}
				}
		});	
	}
	
	@Override
	public void saveMaze(String[] args) {
		//checking if we have all the data we need
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
			controller.outPut("Maze "+ args[1]+" was saved");
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
		//checking if we have all the data we need
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
				controller.outPut("Maze was loaded succesfully");
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
		//open new thread
		exe.execute(new Runnable() {
			
			@Override
			public void run() {
				//checking if we have all the data we need
				if (args.length < 2) 
				{
					controller.outPut("not enough data");
				}
				else if(mazes.containsKey(args[0]))
				{
					//Checking which algorithm was chosen and if a solution was already existing for the maze, it overwrites it.
					if(args[1].equalsIgnoreCase("BFS"))
					{
						Solution<Position> s=  new BFS<Position>().search(new MazeSearchable(mazes.get(args[0]),1)); //get Solution of the maze
						solutions.put(args[0], s);
						controller.outPut("solution for "+ args[0]+ " is ready");
					}
					else if(args[1].equalsIgnoreCase("A* manhatten"))
						
					{
						Solution<Position> s=  new AStar<Position>(new MazeManhattenDistance()).search(new MazeSearchable(mazes.get(args[0]),1)); //get Solution of the maze
						solutions.put(args[0], s);
						controller.outPut("solution for "+ args[0]+ " is ready");
					}
					else if(args[1].equalsIgnoreCase("A* air"))
					{
						Solution<Position> s=  new AStar<Position>(new MazeAirDistance()).search(new MazeSearchable(mazes.get(args[0]),1)); //get Solution of the maze
						solutions.put(args[0], s);
						controller.outPut("solution for "+ args[0]+ " is ready");
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
		});		
	}
	
	@Override
	public void exit(String[] args) {
		exe.shutdown();
		try 
		{
			while(!(exe.awaitTermination(2, TimeUnit.SECONDS)));
		} 
		catch (InterruptedException e)
		{
			controller.outPut(e.getMessage());
		}
		controller.outPut("Goodbye and good Java to you");
	}
	@Override
	public Maze3d getMaze(String[] args) {
		if (args.length<1) 
		{
			controller.outPut("not enough data");
			return null;
		} 
		else if (mazes.containsKey(args[0]))
		{
			Maze3d maze;
			maze= mazes.get(args[0]);
			return maze;
		}
		else
		{
			controller.outPut("maze does not exist");
			return null;
		}
	}
	@Override
	public int[][] getCrossSectionBy(String[] args) {
		Integer y;
		//checking if we have all the data we need
		if (args.length < 3) 
		{
			controller.outPut("not enough data");
			return null;
		} 
		else if ((mazes.containsKey(args[2]))&&((y=MyController.tryParseInt(args[1]))!=null))
		{
			Maze3d maze;
			maze= mazes.get(args[2]);
			int x=(int)y;
			try
			{
				if (args[0].equalsIgnoreCase("X"))
				{
					return maze.getCrossSectionByX(x);
				}
				else if (args[0].equalsIgnoreCase("Y"))
				{
					return maze.getCrossSectionByY(x);
				}
				else if (args[0].equalsIgnoreCase("Z"))
				{
					return maze.getCrossSectionByZ(x);
				}
				else
				{
					controller.outPut("values is not as expected");
					return null;
				}
			}
			catch (Exception e)
			{
				controller.outPut("values is not as expected");
				return null;
			}
		}
		else
		{
			controller.outPut("values is not as expected");
			return null;
		}
		
	}
	@Override
	public int mazeSize(String[] args) {
		//checking if we have all the data we need
		if (args.length<1) 
		{
			controller.outPut("not enough data");
			return 0;
		} 
		else if (mazes.containsKey(args[0]))
		{
			Maze3d maze;
			maze= mazes.get(args[0]);
			int size = maze.getMaze().length*maze.getMaze()[0].length*maze.getMaze()[0][0].length;
			return size;
		}	
		else
		{
			controller.outPut("no such maze");
			return 0;
		}
	}
	@Override
	public Solution<Position> getSoultion(String[] args) {
		//checking if we have all the data we need
		if (args.length<1) 
		{
			controller.outPut("not enough data");
			return null;
		} 
		else if (solutions.containsKey(args[0]))
		{
			Solution<Position> solve = solutions.get(args[0]);
			return solve;
		}
		else
		{
			controller.outPut("solution wasn't created");
			return null;
		}
	}
}
