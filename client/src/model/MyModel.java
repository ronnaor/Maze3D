package model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Maze3dByteArr;
import algorithms.mazeGenarators.MyMaze3dGenerator;
import algorithms.mazeGenarators.Position;
import algorithms.mazeGenarators.SimpleMaze3dGenerator;
import algorithms.search.Solution;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.ClientProperties;
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
	private ExecutorService exe;
	private String generateAlg;
	private String solveAlg;
	private String viewStyle ;
	private Socket theServer;
	private String serverIP;
	private int port;

	
	/**
	 * Ctor of MyModel
	 */
	public MyModel() {
		
		this.mazes = new HashMap<String, Maze3d>();
		this.solutions = new HashMap<String, Solution<Position>>();
		loadFile();
		try {// get properties
			XMLDecoder xml=new XMLDecoder(new FileInputStream("./resources/prop.xml"));
			Properties properties=(Properties)xml.readObject();
			this.exe = Executors.newFixedThreadPool(properties.getNumThreads());
			this.generateAlg = properties.getGenerateAlgorithm();
			this.solveAlg = properties.getSolveAlgorithm();
			this.viewStyle = properties.getViewStyle();
			xml.close();
		} catch (FileNotFoundException e1) {

			this.exe = Executors.newFixedThreadPool(13);
			this.generateAlg = "my";
			this.solveAlg = "A* manhatten";
			this.viewStyle = "GUI";
		}			
		//get client properties
		File serverProp=new File("./resources/propClient.xml"); // check if client Properties xml file exists
		if(!serverProp.exists()) //if the file does not exists
		{
			try { //create the xml file
				XMLEncoder xmlClient = new XMLEncoder(new FileOutputStream("./resources/propClient.xml"));
				xmlClient.writeObject(new ClientProperties("localhost", 1234));
				xmlClient.close();
			} catch (FileNotFoundException e) {
				setChanged();
				notifyObservers(new String[] {"error", e.getMessage()});
			}
			
		}
		
		try {// get the client properties from the xml file
			XMLDecoder xmlClient=new XMLDecoder(new FileInputStream("./resources/propClient.xml"));
			ClientProperties properties=(ClientProperties)xmlClient.readObject();
			this.port = properties.getPortServer();
			this.serverIP = properties.getIpServer();
			xmlClient.close();
		} catch (Exception e) {
			setChanged();
			notifyObservers(new String[] {"error", e.getMessage()});
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
												str[0] = "generated";
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
						Solution<Position> s= getSolitionFromServer(mazes.get(args[1]), "BFS", args[1]);  //get Solution of the maze from server
								
						if (s!=null)
						{
							solutions.put(args[1], s);
							return "solution for "+ args[1]+ " is ready";
							
						}
						else
						{
							return "no solution was found";
						}		
					}
					if ((args.length>2) && (args[2].equalsIgnoreCase("A* manhatten")))
					{
						Solution<Position> s= getSolitionFromServer(mazes.get(args[1]), "A* manhatten", args[1]);  //get Solution of the maze from server 
						if (s!=null)
						{
							solutions.put(args[1], s);
							return "solution for "+ args[1]+ " is ready";
						}
						else
						{
							return "no solution was found";
						}
					}
					if ((args.length>2) && (args[2].equalsIgnoreCase("A* air")))
					{
						Solution<Position> s= getSolitionFromServer(mazes.get(args[1]), "A* air", args[1]);  //get Solution of the maze from server
						if (s!=null)
						{
							solutions.put(args[1], s);
							return "solution for "+ args[1]+ " is ready";
						}
						else
						{
							return "no solution was found";
						}
					}
					if(solveAlg.equalsIgnoreCase("BFS"))
					{
						Solution<Position> s= getSolitionFromServer(mazes.get(args[1]), "BFS", args[1]);  //get Solution of the maze from server
						if (s!=null)
						{
							solutions.put(args[1], s);
		
							return "solution for "+ args[1]+ " is ready";
						}
						else
						{
							return "no solution was found";
						}
						
					}
					if(solveAlg.equalsIgnoreCase("A* manhatten"))					
					{
						Solution<Position> s= getSolitionFromServer(mazes.get(args[1]), "A* manhatten", args[1]);  //get Solution of the maze from server 
						if (s!=null)
						{
							solutions.put(args[1], s);
							return "solution for "+ args[1]+ " is ready";
						}
						else
						{
							return "no solution was found";
						}
					}
					if(solveAlg.equalsIgnoreCase("A* air"))
					{
						Solution<Position> s=  getSolitionFromServer(mazes.get(args[1]), "A* air", args[1]);  //get Solution of the maze from server
						
						if (s!=null)
						{
							solutions.put(args[1], s);
							return "solution for "+ args[1]+ " is ready";
						}
						else
						{
							return "no solution was found";
						}
					}
					return "no solution was found";
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
			path = "./resources/prop.xml";
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
	public Maze3d play(String[] args) {
		String[] str = new String[2];
		//checking if we have all the data we need
		@SuppressWarnings("unused")
		Integer x,y,z;
		if (args.length < 5 && !mazes.containsKey(args[1]) ) 
		{
			str[0] = "error";
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
			return null;
		}	
		//checking that there is no other maze with that name
		else if (mazes.containsKey(args[1]))
		{
			str[0] = "printUpdate";
			str[1] = "this maze already exists the game will start";
			setChanged();
			notifyObservers(str);
			return mazes.get(args[1]);
		}
		//Checking if the the 3 variables are int type (for the maze size)
		else if (((x=Presenter.tryParseInt(args[2]))==null)|| ((y=Presenter.tryParseInt(args[3]))==null)||
				((z=Presenter.tryParseInt(args[4]))==null))
		{
			str[0] = "error";
			str[1] = "data input does not match the command requiremnts";
			setChanged();
			notifyObservers(str);
			return null;
		}
		else
		{
			generate3DMaze(args);
			while (!mazes.containsKey(args[1])){}
			return mazes.get(args[1]);
		}
		
		
	}
	@Override
	public Solution<Position> sol(String[] args) {
		
		String[] str = new String[2];
		//checking if we have all the data we need
		if (args.length < 5 ) 
		{
			str[0] = "error";
			str[1] = "not enough data";
			setChanged();
			notifyObservers(str);
			return null;
		}	
		else
		{
			Maze3d tempMaze = new MyMaze3dGenerator(mazes.get(args[1]));  
			args[1]= args[1]+"mid";
			int x=Presenter.tryParseInt(args[2]);
			int y=Presenter.tryParseInt(args[3]);
			int z=Presenter.tryParseInt(args[4]);
			tempMaze.setStartPosition(new Position(x,y,z));// set the position as the start position of this maze
			mazes.put(args[1], tempMaze);
			solve(args);
			while (!solutions.containsKey(args[1])){}
			return solutions.get(args[1]);
		}	
					
	}
	
	@Override
	public void removeMidMaze(String[] args) {
		args[1]= args[1];
		if (solutions.containsKey(args[1]))
		{
			solutions.remove(args[1]);
		}
		if (mazes.containsKey(args[1]))
		{
			mazes.remove(args[1]);
		}
		
	}
	@Override
	public void saveFile() {
		String[] str = new String[2];
		try {
			
			File mazesFile = new File("./resources/Solutions.zip");
			FileWriter fw = new FileWriter(mazesFile,false);
			BufferedWriter bw = new BufferedWriter(fw);
			for (String mazeName : this.solutions.keySet()) 
			{
				//Overriding the data in the file because we already loaded it into the memory
				
				bw.write(mazeName);	
				//adding special characters in order to identify objects
				bw.write("@@@");
				bw.flush();
				
				byte[] byteMaze = new byte[this.mazes.get(mazeName).toByteArray().length];
				byteMaze= this.mazes.get(mazeName).toByteArray();	
				for (byte b : byteMaze)
				{
					bw.write((int)b);
					bw.flush();
				}
				
				bw.write("@@@");
				for (Position p :this.solutions.get(mazeName).getPath())
				{
					bw.write(p.getX()+"#"+p.getY()+"#"+p.getZ()+"#");
					bw.flush();
				}
				bw.write("\n");
				bw.flush();
				
			}
			bw.close();
			
		} catch (FileNotFoundException e) {
			str[0] = "error";
			str[1] = "File wasn't created";
			setChanged();
			notifyObservers(str);
		} catch (IOException e) {
			str[0] = "error";
			str[1] = "File wasn't created";
			setChanged();
			notifyObservers(str);
		}
		
	}
	@Override
	public void loadFile() {
		
		 try{
			 File mazesFile = new File("./resources/Solutions.zip");
			 if (!mazesFile.exists())
			 {
				 return;
			 }
			 else
			 { 
				 //opening the file for reading
			    FileInputStream fstream = new FileInputStream("./resources/Solutions.zip");
			    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			    String strLine;
			    //getting the data line by line which represents one maze per line
			    while ((strLine = br.readLine()) != null)   {
			    	String[] line = strLine.split("@@@");
			    	//getting the name
			      String name = line[0];
			      //Getting the maze
			      char[] tempMaze = new char[line[1].toCharArray().length];
			      byte[] bMaze = new byte[line[1].toCharArray().length];
			      tempMaze = line[1].toCharArray();
			      int cnt = 0;
			      for (char c :tempMaze)
			      {
			    	  bMaze[cnt]=(byte)c;
			    	  cnt++;
			      }
			      this.mazes.put(name, new Maze3dByteArr(bMaze));
			      //getting the solution
			      String[] posArr = line[2].split("#");
			      ArrayList<Position> tempList=new ArrayList<Position>();
			      for (int i =0; i<posArr.length;i+=3)
			      {
			    	  int tempX = Integer.parseInt(posArr[i]);
			    	  int tempY = Integer.parseInt(posArr[i+1]);
			    	  int tempZ = Integer.parseInt(posArr[i+2]);
			    	  tempList.add(new Position(tempX,tempY,tempZ));
			      }
			      this.solutions.put(name, new Solution<Position>(tempList));
			     
			    }
			    
			    //Close the input stream
			    br.close();
			 }
			  } catch (Exception e) {
			      e.printStackTrace();
			    }
			  
	}
	
	/**
	 * asking the server for solution to a maze
	 * @param maze the maze we want solution for
	 * @param solveAlg the search algorithm to work with 
	 * @return solution Solution<Position> of the maze
	 */
	private Solution<Position>getSolitionFromServer(Maze3d maze, String solveAlg, String mazeName)
	{
		
		try {
			//start new socket to connect to server
			theServer = new Socket(this.serverIP, this.port);
			
			//get the socket output and input streams
			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
			BufferedReader inFromServer=new BufferedReader(new InputStreamReader(theServer.getInputStream()));

			outToServer.println("Need Solution\n");
			outToServer.flush();
			
			//get answer from server and continue
			inFromServer.readLine();
			
			//create an array list of the solve algorithm and the maze to solve and send it to the server
			ArrayList<Object> mazeAndAlg=new ArrayList<Object>();
			mazeAndAlg.add(mazeName);
			mazeAndAlg.add(solveAlg);
			mazeAndAlg.add(maze.toByteArray());
			ObjectOutputStream problemToServer=new ObjectOutputStream(theServer.getOutputStream());
			problemToServer.writeObject(mazeAndAlg);
			problemToServer.flush();
			
			//get the solution from the server
			ObjectInputStream solutionFromServer=new ObjectInputStream(theServer.getInputStream());
			@SuppressWarnings("unchecked")
			Solution<Position> sol=(Solution<Position>)solutionFromServer.readObject();

			problemToServer.close();
			solutionFromServer.close();
			outToServer.close();
			inFromServer.close();
			
			theServer.close();
			
			return sol;
		}
		catch (Exception e) 
		{
			try {
				theServer.close();
			} catch (Exception e1) {
				return null;
			}
			return null;
		}
		

		
	}
	

}



