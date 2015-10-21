package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Maze3dByteArr;
import algorithms.mazeGenarators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattenDistance;
import algorithms.search.MazeSearchable;
import algorithms.search.Solution;

/**
 * 
 *class MyClientHandler implements ClinetHandler and get a soulotion for a maze to the client
 *
 */
public class MyClientHandler implements ClinetHandler{

	private HashMap<String, Maze3d> mazes;
	private HashMap<String, Solution<Position>> solutions;
	
	public MyClientHandler() {
		this.mazes = new HashMap<String, Maze3d>();
		this.solutions = new HashMap<String, Solution<Position>>();
		loadFile();
		
	}
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter out=new PrintWriter(outToClient);
		
		@SuppressWarnings("unused")
		String line;
		try{
			if((line=in.readLine()).equals("Need Solution")){
				out.println("received the request");
				out.flush();
				
				ObjectInputStream problemFromClientt=new ObjectInputStream(inFromClient);
				@SuppressWarnings("unchecked")
				ArrayList<Object> mazeAndAlg=(ArrayList<Object>)problemFromClientt.readObject();
				String mazeName=(String)mazeAndAlg.get(0);
				String solveAlg=(String)mazeAndAlg.get(1);
				Maze3d maze=new Maze3dByteArr((byte[])mazeAndAlg.get(2));
				
				Solution<Position> s =null;
				if(solutions.containsKey(mazeName))
				{
					s = solutions.get(mazeName);
				}
				else if(solveAlg.equalsIgnoreCase("BFS"))
				{
					s = new BFS<Position>().search(new MazeSearchable(maze,1)); //get Solution of the maze
					solutions.put(mazeName, s);
				}
				else if(solveAlg.equalsIgnoreCase("A* manhatten"))					
				{
					s = new AStar<Position>(new MazeManhattenDistance()).search(new MazeSearchable(maze,1)); //get Solution of the maze
					solutions.put(mazeName, s);
				}
				else if(solveAlg.equalsIgnoreCase("A* air"))
				{
					s =  new AStar<Position>(new MazeAirDistance()).search(new MazeSearchable(maze,1)); //get Solution of the maze
					solutions.put(mazeName, s);
				}
				
				//return the solution to the client
				ObjectOutputStream solutionToClient=new ObjectOutputStream(outToClient);
				solutionToClient.writeObject(s);
				solutionToClient.flush();

				problemFromClientt.close();
				solutionToClient.close();
			}
			in.close();
			out.close();
			
		}catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		catch (ClassNotFoundException e) 
		{
			System.out.println(e.getMessage());
		} 
		
		
	}
	
	/**
	 * load the Solutions file if exists and put the mazes and solutions into the hashmaps
	 */
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
	

}
