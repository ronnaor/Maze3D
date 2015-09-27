package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Observable;
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
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Presenter;
/**
 * class MyModel that extends Observable and implements Model will be defining the data to be displayed
 *
 */
public class MyModel extends Observable implements Model {
	private Presenter presenter;
	private HashMap<String, Maze3d> mazes;
	private HashMap<String, Solution<Position>> solutions;
	ExecutorService exe;
	/**
	 * Ctor of MyModel
	 */
	public MyModel() {
		this.mazes = new HashMap<String, Maze3d>();
		this.solutions = new HashMap<String, Solution<Position>>();
		exe = Executors.newFixedThreadPool(13);
	}
	/**
	 * get the model presenter
	 * @return presenter
	 */
	public Presenter getpresenter() {
		return presenter;
	}

	/**
	 * set a presenter to the model
	 * @param presenter
	 */
	@Override
	public void setpresenter(Presenter presenter) {
		this.presenter = presenter;
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
				presenter.outPut("not enough data");
			}		
			//Checking if the the 3 other variables are int type
			else if (((x=Presenter.tryParseInt(args[1]))!=null)&& ((y=Presenter.tryParseInt(args[2]))!=null)&&
				((z=Presenter.tryParseInt(args[3]))!=null))
			{
				//creating the maze and adding it to the hashmap
				Maze3d maze = new MyMaze3dGenerator().generate((int)x, (int)y, (int)z);
				if (maze != null)
				{
					mazes.put(args[0], maze);
					presenter.outPut("maze "+args[0]+" is ready");
				}
				else
				{
					presenter.outPut("data input does not match the command requiremnts");
				}
			}
			else
			{
				presenter.outPut("data input does not match the command requiremnts");
			}
				}
		});	
	}
	
	@Override
	public void saveMaze(String[] args) {
		//checking if we have all the data we need
		if (args.length < 2) 
		{
			presenter.outPut("not enough data");
		}
		else if(mazes.containsKey(args[0]))
		{
			try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(args[1]+".maz"));
			out.write(mazes.get(args[0]).toByteArray());			
			out.flush();
			out.close();
			presenter.outPut("Maze "+ args[0]+" was saved");
			}
			catch (Exception e) {
				presenter.outPut("Couldn't save the maze - fatal error");
			}
		}
		else
		{
			presenter.outPut("Maze does not exist");
		}
	}
	
	@Override
	public void loadMaze(String[] args) {
		//checking if we have all the data we need
		if (args.length < 2) 
		{
			presenter.outPut("not enough data");
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
				presenter.outPut("Maze was loaded succesfully");
			}
			catch (Exception e) {
				presenter.outPut("Error in loading file");
			}
		}
		else
		{
			presenter.outPut("Maze already exist");
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
					presenter.outPut("not enough data");
				}
				else if(mazes.containsKey(args[0]))
				{
					//Checking which algorithm was chosen and if a solution was already existing for the maze, it overwrites it.
					if(args[1].equalsIgnoreCase("BFS"))
					{
						Solution<Position> s=  new BFS<Position>().search(new MazeSearchable(mazes.get(args[0]),1)); //get Solution of the maze
						solutions.put(args[0], s);
						presenter.outPut("solution for "+ args[0]+ " is ready");
					}
					else if(args[1].equalsIgnoreCase("A* manhatten"))
						
					{
						Solution<Position> s=  new AStar<Position>(new MazeManhattenDistance()).search(new MazeSearchable(mazes.get(args[0]),1)); //get Solution of the maze
						solutions.put(args[0], s);
						presenter.outPut("solution for "+ args[0]+ " is ready");
					}
					else if(args[1].equalsIgnoreCase("A* air"))
					{
						Solution<Position> s=  new AStar<Position>(new MazeAirDistance()).search(new MazeSearchable(mazes.get(args[0]),1)); //get Solution of the maze
						solutions.put(args[0], s);
						presenter.outPut("solution for "+ args[0]+ " is ready");
					}
					else
					{
						presenter.outPut("no algorithm was chosen");
					}
				}
				else
				{
					presenter.outPut("no such maze exist");
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
			presenter.outPut(e.getMessage());
		}
		presenter.outPut("Goodbye and good Java to you");
	}
	@Override
	public Maze3d getMaze(String[] args) {
		if (args.length<1) 
		{
			presenter.outPut("not enough data");
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
			presenter.outPut("maze does not exist");
			return null;
		}
	}
	@Override
	public int[][] getCrossSectionBy(String[] args) {
		Integer y;
		//checking if we have all the data we need
		if (args.length < 3) 
		{
			presenter.outPut("not enough data");
			return null;
		} 
		else if ((mazes.containsKey(args[2]))&&((y=Presenter.tryParseInt(args[1]))!=null))
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
					presenter.outPut("values is not as expected");
					return null;
				}
			}
			catch (Exception e)
			{
				presenter.outPut("values is not as expected");
				return null;
			}
		}
		else
		{
			presenter.outPut("values is not as expected");
			return null;
		}
		
	}
	@Override
	public int mazeSize(String[] args) {
		//checking if we have all the data we need
		if (args.length<1) 
		{
			presenter.outPut("not enough data");
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
			presenter.outPut("no such maze");
			return 0;
		}
	}
	@Override
	public Solution<Position> getSoultion(String[] args) {
		//checking if we have all the data we need
		if (args.length<1) 
		{
			presenter.outPut("not enough data");
			return null;
		} 
		else if (solutions.containsKey(args[0]))
		{
			Solution<Position> solve = solutions.get(args[0]);
			return solve;
		}
		else
		{
			presenter.outPut("solution wasn't created");
			return null;
		}
	}
}
