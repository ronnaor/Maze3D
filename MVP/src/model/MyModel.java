package model;

import java.beans.XMLDecoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Maze3dByteArr;
import algorithms.mazeGenarators.MyMaze3dGenerator;
import algorithms.mazeGenarators.Position;
import algorithms.mazeGenarators.SimpleMaze3dGenerator;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattenDistance;
import algorithms.search.MazeSearchable;
import algorithms.search.Solution;
import db.DBObject;
import db.SaveToDB;
import db.SimpelingMaze;
import db.Solutions;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Presenter;
import presenter.Properties;
/**
 * class MyModel that extends Observable and implements Model will be defining the data to be displayed
 *
 */
public class MyModel extends Observable implements Model {

	private Presenter presenter;
	private HashMap<String, Maze3d> mazes;
	private HashMap<String, Solution<Position>> solutions;
	ExecutorService exe;
	String generateAlg;
	String solveAlg;
	String viewStyle ;
	
	/**
	 * Ctor of MyModel
	 */
	public MyModel() {
		this.mazes = new HashMap<String, Maze3d>();
		this.solutions = new HashMap<String, Solution<Position>>();
		try {// get properties
			XMLDecoder xml=new XMLDecoder(new FileInputStream("prop.xml"));
			Properties properties=(Properties)xml.readObject();
			this.exe = Executors.newFixedThreadPool(properties.getNumThreads());
			this.generateAlg = properties.getGenerateAlgorithm();
			this.solveAlg = properties.getSolveAlgorithm();
			this.viewStyle = properties.getViewStyle();
			xml.close();
		} catch (FileNotFoundException e1) {

			this.exe = Executors.newFixedThreadPool(13);
			this.generateAlg = "my";
			this.solveAlg = "bfs";
			this.viewStyle = "GUI";
		}
		try {
			//startDB();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
			
	
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
	/**
	 * get the Generate Algorithm we will use
	 * @return String the Algorithm
	 */
	public String getGenerateAlg() {
		return generateAlg;
	}
	/**
	 * set the Generate Algorithm we will use
	 * @param generateAlg String the Algorithm
	 */
	public void setGenerateAlg(String generateAlg) {
		this.generateAlg = generateAlg;
	}
	/**
	 * get the solve Algorithm we will use
	 * @return String the Algorithm
	 */
	public String getSolveAlg() {
		return solveAlg;
	}
	/**
	 * set the solve Algorithm we will use
	 * @param solveAlg String the Algorithm
	 */
	public void setSolveAlg(String solveAlg) {
		this.solveAlg = solveAlg;
	}
	
	
	
	
	public ExecutorService getExe() {
		return exe;
	}
	public void setExe(ExecutorService exe) {
		this.exe = exe;
	}
	public String getViewStyle() {
		return viewStyle;
	}
	public void setViewStyle(String viewStyle) {
		this.viewStyle = viewStyle;
	}
	@Override
	public void getDir(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		String[] str;
		//checking that we got enough data
		if (args.length<2)
		{
			str = new String[2];
			str[0] = "error";
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
				str[0] = "error";
				str[1] = "path does not exist";
				setChanged();
				notifyObservers(str);
			}	
		}	
		
	}

	@Override
	public void generate3DMaze(String[] args) {
		String[] str = new String[2];
		//checking if we have all the data we need
		Integer x,y,z;
		if (args.length < 5) 
		{
			str[0] = "error";
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
		}	
		//Checking if the the 3 variables are int type (for the maze size)
		else if (((x=Presenter.tryParseInt(args[2]))==null)|| ((y=Presenter.tryParseInt(args[3]))==null)||
				((z=Presenter.tryParseInt(args[4]))==null))
		{
			str[0] = "error";
			str[1] = "data input does not match the command requiremnts";
			setChanged();
			notifyObservers(str);
		}
		//checking that there is no other maze with that name
		else if (mazes.containsKey(args[1]))
		{
			str[0] = "error";
			str[1] = "maze already exists";
			setChanged();
			notifyObservers(str);
		}
		else
		{
			Future<String> future=exe.submit(new Callable<String>() 
					//open new thread
					{
						@Override
						public String call() throws Exception {
							//check if the generate algorithm is valid else using generateAlg
							if ((args.length>5) && (args[5].equalsIgnoreCase("my")))
							{
								Maze3d maze = new MyMaze3dGenerator().generate((int)x, (int)y, (int)z);
								//creating the maze and adding it to the hashmap
								if (maze != null)
								{
									mazes.put(args[1], maze);
									return "maze "+args[1]+" is ready";
								}
								else
								{
									return "data input does not match the command requiremnts";
								}
							}
							if ((args.length>5) && (args[5].equalsIgnoreCase("simple")))
							{
								Maze3d maze = new SimpleMaze3dGenerator().generate((int)x, (int)y, (int)z);
								//creating the maze and adding it to the hashmap
								if (maze != null)
								{
									mazes.put(args[1], maze);
									return "maze "+args[1]+" is ready";
								}
								else
								{
									return "data input does not match the command requiremnts";
								}
							}
							if (generateAlg.equalsIgnoreCase("simple"))
							{
								Maze3d maze = new SimpleMaze3dGenerator().generate((int)x, (int)y, (int)z);
								//creating the maze and adding it to the hashmap
								if (maze != null)
								{
									mazes.put(args[1], maze);
									return "maze "+args[1]+" is ready";
								}
								else
								{
									return "data input does not match the command requiremnts";
								}
							}
							if (generateAlg.equalsIgnoreCase("my"))
							{
								Maze3d maze = new MyMaze3dGenerator().generate((int)x, (int)y, (int)z);
								//creating the maze and adding it to the hashmap
								if (maze != null)
								{
									mazes.put(args[1], maze);
									return "maze "+args[1]+" is ready";
								}
								else
								{
									return "data input does not match the command requiremnts";
								}
							}
							return "data input does not match the command requiremnts";
							

						}});
							exe.execute(new Runnable() {
								
								@Override
								public void run() {
										try {
											String s = future.get();
											if (s.equals("maze "+args[1]+" is ready"))
											{
												str[0] = "printUpdate";
												str[1] = "maze "+args[1]+" is ready";
												setChanged();
												notifyObservers(str);
												return;
											}
											else
											{
												str[0] = "error";
												str[1] = "data input does not match the command requiremnts";
												setChanged();
												notifyObservers(str);
												return;
											}
												
										} catch (InterruptedException | ExecutionException e) {
											str[0] = "error";
											str[1] = e.getMessage();
											setChanged();
											notifyObservers(str);
											return;
										}		
								}
							});	
		
		}
			
	
	}

	@Override
	public Maze3d getMaze(String[] args) {
		String[] str = new String[2];
		if (args.length<2) 
		{
			str[0] = "error";
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
			str[0] = "error";
			str[1] = "maze does not exist";
			setChanged();
			notifyObservers(str);
			return null;
		}
	}

	@Override
	public int[][] getCrossSectionBy(String[] args) {
		String[] str = new String[2];
		Integer y;
		//checking if we have all the data we need
		if (args.length < 4) 
		{
			str[0] = "error";
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
					str[0] = "error";
					str[1] = "values is not as expected";
					setChanged();
					notifyObservers(str);
					return null;
				}
			}
			catch (Exception e)
			{
				str[0] = "error";
				str[1] = "values is not as expected";
				setChanged();
				notifyObservers(str);
				return null;
			}
		}
		else
		{
			str[0] = "error";
			str[1] = "values is not as expected";
			setChanged();
			notifyObservers(str);
			return null;
		}
	}

	@Override
	public void saveMaze(String[] args) {
		String[] str = new String[2];
		//checking if we have all the data we need
		if (args.length < 3) 
		{
			str[0] = "error";
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
			str[0] = "printUpdate";
			str[1] = "Maze "+ args[1]+" was saved";
			setChanged();
			notifyObservers(str);
			}
			catch (Exception e) {
				str[0] = "error";
				str[1] = "Couldn't save the maze - fatal error";
				setChanged();
				notifyObservers(str);
			}
		}
		else
		{
			str[0] = "error";
			str[1] = "Maze does not exist";
			setChanged();
			notifyObservers(str);
		}
		
	}

	@Override
	public void loadMaze(String[] args) {
		String[] str = new String[2];
		//checking if we have all the data we need
		if (args.length < 3) 
		{
			str[0] = "error";
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
				str[0] = "printUpdate";
				str[1] = "Maze was loaded succesfully";
				setChanged();
				notifyObservers(str);
			}
			catch (Exception e) {
				str[0] = "error";
				str[1] = "Error in loading file";
				setChanged();
				notifyObservers(str);
			}
		}
		else
		{
			str[0] = "error";
			str[1] = "Maze already exist";
			setChanged();
			notifyObservers(str);
		}
		
	}

	@Override
	public int getMazeSize(String[] args) {
		String[] str = new String[2];
		//checking if we have all the data we need
		if (args.length<2) 
		{
			str[0] = "error";
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
			str[0] = "error";
			str[1] = "no such maze";
			setChanged();
			notifyObservers(str);
			return 0;
		}
	}

	@Override
	public long getFileSize(String[] args) {
		String[] str = new String[2];
		if (args.length < 2) 
		{
			str[0] = "error";
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
				str[0] = "error";
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
		String[] str = new String[2];
		//checking if we have all the data we need
		if (args.length < 2) 
		{
			str[0] = "error";
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
		}
		else if(solutions.containsKey(args[1]))
		{
			str[0] = "printUpdate";
			str[1] = "solution for "+ args[1]+ " is ready";
			setChanged();
			notifyObservers(str);
		}
		else if (!mazes.containsKey(args[1]))
		{
			str[0] = "error";
			str[1] = "no such maze exist";
			setChanged();
			notifyObservers(str);
		}
		else
		{
			Future<String> future=exe.submit(new Callable<String>() 
			{
	
				@Override
				public String call() throws Exception {
					//check if the generate algorithm is valid else using solveAlg
					if ((args.length>2) && (args[2].equalsIgnoreCase("BFS")))
					{
						Solution<Position> s=  new BFS<Position>().search(new MazeSearchable(mazes.get(args[1]),1)); //get Solution of the maze
						if (s!=null)
						{
							solutions.put(args[1], s);
							try {
								DBObject obj = new DBObject(args[1],new SimpelingMaze(mazes.get(args[1])) ,new Solutions(s));
								save2DB(obj);
							} catch (Exception e) {
								String[] err = new String[2];
								err[0] = "error";
								err[1] = "no DB conection";
								setChanged();
								notifyObservers(err);
							}
							return "solution for "+ args[1]+ " is ready";
							
						}
						else
						{
							return "no solution was found";
						}		
					}
					if ((args.length>2) && (args[2].equalsIgnoreCase("A* manhatten")))
					{
						Solution<Position> s=  new AStar<Position>(new MazeManhattenDistance()).search(new MazeSearchable(mazes.get(args[1]),1)); //get Solution of the maze
						if (s!=null)
						{
							solutions.put(args[1], s);
							try {
								DBObject obj = new DBObject(args[1],new SimpelingMaze(mazes.get(args[1])) ,new Solutions(s));
								save2DB(obj);
							} catch (Exception e) {
								String[] err = new String[2];
								err[0] = "error";
								err[1] = "no DB conection";
								setChanged();
								notifyObservers(err);
							}
							return "solution for "+ args[1]+ " is ready";
						}
						else
						{
							return "no solution was found";
						}
					}
					if ((args.length>2) && (args[2].equalsIgnoreCase("A* air")))
					{
						Solution<Position> s=  new AStar<Position>(new MazeAirDistance()).search(new MazeSearchable(mazes.get(args[1]),1)); //get Solution of the maze
						if (s!=null)
						{
							solutions.put(args[1], s);
							try {
								DBObject obj = new DBObject(args[1],new SimpelingMaze(mazes.get(args[1])) ,new Solutions(s));
								save2DB(obj);
							} catch (Exception e) {
								String[] err = new String[2];
								err[0] = "error";
								err[1] = "no DB conection";
								setChanged();
								notifyObservers(err);
							}
							return "solution for "+ args[1]+ " is ready";
						}
						else
						{
							return "no solution was found";
						}
					}
					if(solveAlg.equalsIgnoreCase("BFS"))
					{
						Solution<Position> s=  new BFS<Position>().search(new MazeSearchable(mazes.get(args[1]),1)); //get Solution of the maze
						if (s!=null)
						{
							solutions.put(args[1], s);
		
							try {
								SimpelingMaze d =new SimpelingMaze(mazes.get(args[1]));
								Solutions b =new Solutions(s);
								DBObject obj = new DBObject(args[1],d ,b);
								save2DB(obj);
							} catch (Exception e) {
								System.out.println(e.getStackTrace());
								String[] err = new String[2];
								err[0] = "error";
								err[1] = "no DB conection";
								setChanged();
								notifyObservers(err);
							}
							return "solution for "+ args[1]+ " is ready";
						}
						else
						{
							return "no solution was found";
						}
						
					}
					if(solveAlg.equalsIgnoreCase("A* manhatten"))					
					{
						Solution<Position> s=  new AStar<Position>(new MazeManhattenDistance()).search(new MazeSearchable(mazes.get(args[1]),1)); //get Solution of the maze
						if (s!=null)
						{
							solutions.put(args[1], s);
							try {
								DBObject obj = new DBObject(args[1],new SimpelingMaze(mazes.get(args[1])) ,new Solutions(s));
								save2DB(obj);
							} catch (Exception e) {
								String[] err = new String[2];
								err[0] = "error";
								err[1] = "no DB conection";
								setChanged();
								notifyObservers(err);
							}
							return "solution for "+ args[1]+ " is ready";
						}
						else
						{
							return "no solution was found";
						}
					}
					if(solveAlg.equalsIgnoreCase("A* air"))
					{
						Solution<Position> s=  new AStar<Position>(new MazeAirDistance()).search(new MazeSearchable(mazes.get(args[1]),1)); //get Solution of the maze
						if (s!=null)
						{
							solutions.put(args[1], s);
							try {
								DBObject obj = new DBObject(args[1],new SimpelingMaze(mazes.get(args[1])) ,new Solutions(s));
								save2DB(obj);
							} catch (Exception e) {
								String[] err = new String[2];
								err[0] = "error";
								err[1] = "no DB conection";
								setChanged();
								notifyObservers(err);
							}
							return "solution for "+ args[1]+ " is ready";
						}
						else
						{
							return "no solution was found";
						}
					}
					return null;
				}});
		//open new thread
				exe.execute(new Runnable() {
					
					@Override
					public void run() {
						
						try {
							String s = future.get();
							if (s.equals("solution for "+ args[1]+ " is ready"))
							{
								str[0] = "printUpdate";
								str[1] = "solution for "+ args[1]+ " is ready";
								setChanged();
								notifyObservers(str);
							}
							else
							{
								str[0] = "error";
								str[1] = "no solution was found";
								setChanged();
								notifyObservers(str);							
							}
								
						} catch (InterruptedException | ExecutionException e) {
							str[0] = "error";
							str[1] = e.getMessage();
							setChanged();
							notifyObservers(str);
						}		
						
					}
				});}		
		
	}

	@Override
	public Solution<Position> getSoultion(String[] args) {
		String[] str = new String[2];
		//checking if we have all the data we need
		if (args.length<2) 
		{
			str[0] = "error";
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
			str[0] = "error";
			str[1] = "solution wasn't created";
			setChanged();
			notifyObservers(str);
			return null;
		}
	}

	@Override
	public void changeProperties(String[] args) {
		String path;
		
		String[] str = new String[2];
		//checking if we have all the data we need
		if (args.length<2) 
		{
			str[0] = "error";
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
			return;
		} 
		
		if(args[1]==null)
		{
			path = "./prop.xml";
		}
		
		else
		{
			path = args[1];
		}

		try
		{
			File file=new File(path);
			if(!file.exists())
			{
				str[0] = "error";
				str[1] = "File  not exists";
				setChanged();
				notifyObservers(str);
				return;
			}
			XMLDecoder xml= new XMLDecoder(new FileInputStream(path));
			Properties properties=(Properties)xml.readObject();
			this.exe = Executors.newFixedThreadPool(properties.getNumThreads());
			this.generateAlg = properties.getGenerateAlgorithm();
			this.solveAlg = properties.getSolveAlgorithm();
			this.viewStyle = properties.getViewStyle();
			xml.close();
			
			str[0] = "properties change";
			str[1] = this.viewStyle;
			setChanged();
			notifyObservers(str);
			
		} 
		catch (Exception e) 
		{
			str[0] = "error";
			str[1] = "File load error";
			setChanged();
			notifyObservers(str);
			return;
		}
		
		
	}
	
	@Override
	public void exit(String[] args) {
		String[] str = new String[2];
		exe.shutdown();
		try 
		{
			while(!(exe.awaitTermination(2, TimeUnit.SECONDS)));
		} 
		catch (InterruptedException e)
		{
			str[0] = "error";
			str[1] = e.getMessage();
			setChanged();
			notifyObservers(str);
		}
		str[0] = "printUpdate";
		str[1] = "Goodbye and good Java to you";
		setChanged();
		notifyObservers(str);
		
	}

	
	@SuppressWarnings("null")
	@Override
	public HashMap<String, HashMap<Maze3d, Solution<Position>>> getMazesNSolutions() {
		HashMap<Maze3d, Solution<Position>> combine = null;
		HashMap<String, HashMap<Maze3d, Solution<Position>>> obj = null;
		Set<String> namesMazes = getMazes().keySet();
		Set<String> namesSoultions = getSolutions().keySet();
		//merging mazes and solutions hashmaps by the same name
		for (String nameMaze : namesMazes)
		{
			for(String nameSoultion : namesSoultions)
			{
				if(nameMaze.equals(nameSoultion))
				{				
					combine.put(getMazes().get(nameMaze), getSolutions().get(nameSoultion));				
					obj.put(nameSoultion, combine);
				}
			}
		}
		return obj;
	}
	@Override
	public void save2DB(DBObject obj) {
		org.hibernate.SessionFactory sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory(); 
		 Session session = sessionFactory.openSession();
		 SaveToDB manager = new SaveToDB(session);
	
		 manager.saveObj(obj);
		 session.flush();
		 session.close();
	}
	@Override
	public void startDB() {
		try {
			
			//creating db
			Connection conn = null;
			   Statement stmt = null;
			   try{
			      //STEP 2: Register JDBC driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //STEP 3: Open a connection
			      conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/", "root", "Aa123456!");

			      //STEP 4: Execute a query
			      stmt = conn.createStatement();
			      //creating the DB
			      String sql = "CREATE DATABASE IF NOT EXISTS `DB`;";
			      stmt.executeUpdate(sql);
			      try{
				         if(stmt!=null)
				            stmt.close();
				      }catch(SQLException se2){
				      }
				      try{
				         if(conn!=null)
				            conn.close();
				      }catch(SQLException se){
				    	  String[] err = new String[2];
							err[0] = "error";
							err[1] = "couldn't close db";
							setChanged();
							notifyObservers(err);
				      }//end finally try
			      //Creating the father table
			      conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/DB", "root", "Aa123456!");
			      stmt = conn.createStatement();
			     /* sql = "CREATE TABLE IF NOT EXISTS `All` " +
		                   "(`MAZE_ID` int(255) not NULL AUTO_INCREMENT," +
		                   " `NAME` VARCHAR(255),"+
		                   " PRIMARY KEY (`MAZE_ID`) USING BTREE"+
						") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			      stmt.executeUpdate(sql);			      
			      //creating the childs tables
			      //MAZE TABLE
			      sql = "CREATE TABLE IF NOT EXISTS `mazes` " +
		                   "(`MAZE_ID` int(255) not NULL AUTO_INCREMENT," +
		                   " `SIMPLEMAZE` BLOB,"+
		                   " PRIMARY KEY (`MAZE_ID`) USING BTREE,"+
		                   " CONSTRAINT `FK_MAZE_ID` FOREIGN KEY (`MAZE_ID`) REFERENCES `All` (`MAZE_ID`)"+
						") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			      stmt.executeUpdate(sql);
			      //SOLUTION TaBLE
			      sql = "CREATE TABLE IF NOT EXISTS `Solutions` " +
		                   "(`MAZE_ID` int(255) not NULL AUTO_INCREMENT, " +
		                   " `NUMMOVES` int(255),"+			                   
		                   " PRIMARY KEY (`MAZE_ID`) USING BTREE,"+
		                   " CONSTRAINT `FK_SOLUTION_ID` FOREIGN KEY (`MAZE_ID`) REFERENCES `All` (`MAZE_ID`)"+
						") ENGINE=InnoDB DEFAULT CHARSET=utf8;";			
			      stmt.executeUpdate(sql);
			      //POSITION TABLE
			      sql = "CREATE TABLE IF NOT EXISTS `Positions` " +
		                   "(`posID` int(255) not NULL AUTO_INCREMENT, " +
		                   " `X` int(255),"+
		                   " `Y` int(255),"+			                   
		                   " `Z` int(255),"+			                   
		                   " PRIMARY KEY (`posID`) USING BTREE,"+
		                   "KEY `FK_MAZE_TRANSACTION_MAZE_ID` (`MAZE_ID`),"+
		                   "CONSTRAINT `FK_MAZE_TRANSACTION_MAZE_ID` FOREIGN KEY (`MAZE_ID`) "+ 
		                   "REFERENCES `Solutions` (`MAZE_ID`) ON DELETE CASCADE ON UPDATE CASCADE"+
						") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			      stmt.executeUpdate(sql);
			      */
			   }catch(Exception se1){
				   String[] err = new String[2];
					err[0] = "error";
					err[1] = "couldn't create db";
					setChanged();
					notifyObservers(err);
			   }finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			    	  String[] err = new String[2];
						err[0] = "error";
						err[1] = "couldn't close db";
						setChanged();
						notifyObservers(err);
			      }//end finally try
			   }//end try
			   
			 //creating session
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); 
			Session session = sessionFactory.openSession();

			
			Query query = session.createQuery("from DBObject");

			@SuppressWarnings("unchecked")
			List <DBObject>list = query.list();
			Iterator<DBObject> it=list.iterator();

			DBObject object;
			
			while (it.hasNext()){
				object=it.next();
				this.mazes.put(object.getName(), object.getFixMaze());
				this.solutions.put(object.getName(), object.getFixSolution());			
			}
			session.close();

		} catch (Exception e) {
			String[] err = new String[2];
			err[0] = "error";
			err[1] = "error";
			System.out.println(e.getStackTrace());
			e.printStackTrace();
			setChanged();
			notifyObservers(err);
		}
	}
	

}



