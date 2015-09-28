package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
	/**
	 * get hash map of the solutions of the mazes
	 * @return HashMap of solutions
	 */
	public HashMap<String, Solution<Position>> getSolutions() {
		return solutions;
	}
	/**
	 * set hash map of  solutions in the model
	 * @param solutions HashMap of solutions
	 */
	public void setSolutions(HashMap<String, Solution<Position>> solutions) {
		this.solutions = solutions;
	}
	
	@Override
	public void getDir(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		String[] str;
		//checking that we got enough data
		if (args.length<2)
		{
			str = new String[2];
			str[0] = "printUpdate";
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
		}
		else
		{
			File file; 
			try
			{
			file= new File(args[1]);
				for (String s : file.list())
				{
					list.add(s);
				}
				str = new String[list.size()+1];
				str[0] = "printDir";
				int cnt = 1;
				for (String s : list)
				{
					str[cnt] = s;
					cnt++;
				}
				setChanged();
				notifyObservers(str);
			}
			catch (Exception e) {
				str = new String[2];
				str[0] = "printUpdate";
				str[1] = "path does not exist";
				setChanged();
				notifyObservers(str);
			}	
		}	
		
	}

	@Override
	public void generate3DMaze(String[] args) {
		//open new thread
				exe.execute(new Runnable() {
					
					@Override
					public void run() {
						
					String[] str = new String[2];
					str[0] = "printUpdate";
					//checking if we have all the data we need
					Integer x,y,z;
					if (args.length < 5) 
					{
						str[1] = "not enough data";
						setChanged();
						notifyObservers(str);
					}		
					//Checking if the the 3 other variables are int type
					else if (((x=Presenter.tryParseInt(args[2]))!=null)&& ((y=Presenter.tryParseInt(args[3]))!=null)&&
						((z=Presenter.tryParseInt(args[4]))!=null))
					{
					//creating the maze and adding it to the hashmap
						Maze3d maze = new MyMaze3dGenerator().generate((int)x, (int)y, (int)z);
						if (maze != null)
						{
							mazes.put(args[1], maze);
							str[1] = "maze "+args[1]+" is ready";
							setChanged();
							notifyObservers(str);
						}
						else
						{
							str[1] = "data input does not match the command requiremnts";
							setChanged();
							notifyObservers(str);
						}
					}
					else
					{
						str[1] = "data input does not match the command requiremnts";
						setChanged();
						notifyObservers(str);
					}
						}
				});	
		
	}

	@Override
	public Maze3d getMaze(String[] args) {
		String[] str = new String[2];
		str[0] = "printUpdate";
		if (args.length<2) 
		{
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
			return null;
		} 
		else if (mazes.containsKey(args[1]))
		{
			Maze3d maze;
			maze= mazes.get(args[1]);
			return maze;
		}
		else
		{
			str[1] = "maze does not exist";
			setChanged();
			notifyObservers(str);
			return null;
		}
	}

	@Override
	public int[][] getCrossSectionBy(String[] args) {
		String[] str = new String[2];
		str[0] = "printUpdate";
		Integer y;
		//checking if we have all the data we need
		if (args.length < 4) 
		{
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
			return null;
		} 
		else if ((mazes.containsKey(args[3]))&&((y=Presenter.tryParseInt(args[2]))!=null))
		{
			Maze3d maze;
			maze= mazes.get(args[3]);
			int x=(int)y;
			try
			{
				if (args[1].equalsIgnoreCase("X"))
				{
					return maze.getCrossSectionByX(x);
				}
				else if (args[1].equalsIgnoreCase("Y"))
				{
					return maze.getCrossSectionByY(x);
				}
				else if (args[1].equalsIgnoreCase("Z"))
				{
					return maze.getCrossSectionByZ(x);
				}
				else
				{
					str[1] = "values is not as expected";
					setChanged();
					notifyObservers(str);
					return null;
				}
			}
			catch (Exception e)
			{
				str[1] = "values is not as expected";
				setChanged();
				notifyObservers(str);
				return null;
			}
		}
		else
		{
			str[1] = "values is not as expected";
			setChanged();
			notifyObservers(str);
			return null;
		}
	}

	@Override
	public void saveMaze(String[] args) {
		String[] str = new String[2];
		str[0] = "printUpdate";
		//checking if we have all the data we need
		if (args.length < 3) 
		{
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
		}
		else if(mazes.containsKey(args[1]))
		{
			try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(args[2]+".maz"));
			out.write(mazes.get(args[1]).toByteArray());			
			out.flush();
			out.close();
			str[1] = "Maze "+ args[1]+" was saved";
			setChanged();
			notifyObservers(str);
			}
			catch (Exception e) {
				str[1] = "Couldn't save the maze - fatal error";
				setChanged();
				notifyObservers(str);
			}
		}
		else
		{
			str[1] = "Maze does not exist";
			setChanged();
			notifyObservers(str);
		}
		
	}

	@Override
	public void loadMaze(String[] args) {
		String[] str = new String[2];
		str[0] = "printUpdate";
		//checking if we have all the data we need
		if (args.length < 3) 
		{
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
		}
		else if(!mazes.containsKey(args[2]))
		{
			try {
				InputStream in=new MyDecompressorInputStream(new FileInputStream(args[1]+".maz"));
				byte b[]=new byte[(int)(new File(args[1]+".maz")).length()];
				in.read(b);
				in.close();
				Maze3d loaded=new Maze3dByteArr(b);
				mazes.put(args[2], loaded);
				str[1] = "Maze was loaded succesfully";
				setChanged();
				notifyObservers(str);
			}
			catch (Exception e) {
				str[1] = "Error in loading file";
				setChanged();
				notifyObservers(str);
			}
		}
		else
		{
			str[1] = "Maze already exist";
			setChanged();
			notifyObservers(str);
		}
		
	}

	@Override
	public int getMazeSize(String[] args) {
		String[] str = new String[2];
		str[0] = "printUpdate";
		//checking if we have all the data we need
		if (args.length<2) 
		{
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
			return 0;
		} 
		else if (mazes.containsKey(args[1]))
		{
			Maze3d maze;
			maze= mazes.get(args[1]);
			int size = maze.getMaze().length*maze.getMaze()[0].length*maze.getMaze()[0][0].length;
			return size;
		}	
		else
		{
			str[1] = "no such maze";
			setChanged();
			notifyObservers(str);
			return 0;
		}
	}

	@Override
	public long getFileSize(String[] args) {
		String[] str = new String[2];
		str[0] = "printUpdate";
		if (args.length < 2) 
		{
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
			return 0;
		}
		else 
		{
			long l = new File(args[1]+".maz").length();
			if (l==0)
			{
				str[1] = "no such File";
				setChanged();
				notifyObservers(str);
				return 0;
			}
			else
			{
				return l;
			}
		}
	}

	@Override
	public void solve(String[] args) {
		//open new thread
				exe.execute(new Runnable() {
					
					@Override
					public void run() {
						String[] str = new String[2];
						str[0] = "printUpdate";
						//checking if we have all the data we need
						if (args.length < 3) 
						{
							str[1] = "not enough data";
							setChanged();
							notifyObservers(str);
						}
						else if(mazes.containsKey(args[1]))
						{
							//Checking which algorithm was chosen and if a solution was already existing for the maze, it overwrites it.
							if(args[2].equalsIgnoreCase("BFS"))
							{
								Solution<Position> s=  new BFS<Position>().search(new MazeSearchable(mazes.get(args[1]),1)); //get Solution of the maze
								solutions.put(args[1], s);
								str[1] = "solution for "+ args[1]+ " is ready";
								setChanged();
								notifyObservers(str);
							}
							else if(args[2].equalsIgnoreCase("A* manhatten"))
								
							{
								Solution<Position> s=  new AStar<Position>(new MazeManhattenDistance()).search(new MazeSearchable(mazes.get(args[1]),1)); //get Solution of the maze
								solutions.put(args[1], s);
								str[1] = "solution for "+ args[1]+ " is ready";
								setChanged();
								notifyObservers(str);
							}
							else if(args[2].equalsIgnoreCase("A* air"))
							{
								Solution<Position> s=  new AStar<Position>(new MazeAirDistance()).search(new MazeSearchable(mazes.get(args[1]),1)); //get Solution of the maze
								solutions.put(args[1], s);
								str[1] = "solution for "+ args[1]+ " is ready";
								setChanged();
								notifyObservers(str);
							}
							else
							{
								str[1] = "no algorithm was chosen";
								setChanged();
								notifyObservers(str);
							}
						}
						else
						{
							str[1] = "no such maze exist";
							setChanged();
							notifyObservers(str);
						}
						
					}
				});		
		
	}

	@Override
	public Solution<Position> getSoultion(String[] args) {
		String[] str = new String[2];
		str[0] = "printUpdate";
		//checking if we have all the data we need
		if (args.length<2) 
		{
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
			return null;
		} 
		else if (solutions.containsKey(args[1]))
		{
			Solution<Position> solve = solutions.get(args[1]);
			return solve;
		}
		else
		{
			str[1] = "solution wasn't created";
			setChanged();
			notifyObservers(str);
			return null;
		}
	}

	@Override
	public void exit(String[] args) {
		String[] str = new String[2];
		str[0] = "printUpdate";
		exe.shutdown();
		try 
		{
			while(!(exe.awaitTermination(2, TimeUnit.SECONDS)));
		} 
		catch (InterruptedException e)
		{
			str[1] = e.getMessage();
			setChanged();
			notifyObservers(str);
		}
		str[1] = "Goodbye and good Java to you";
		setChanged();
		notifyObservers(str);
		
	}

	
}
