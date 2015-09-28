package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;


import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
import presenter.Presenter;

/**
 *  class MyView extends Observable implements View, 
 * displays data and user commands to the presenter to act upon that data.
 *
 */
public class ViewCLI extends Observable implements View {
	
	private BufferedReader in;
	private PrintWriter out; 
	private Presenter presenter;
	
	/**
	 * Ctor
	 * @param in BufferedReader
	 * @param out PrintWriter
	 */
	public ViewCLI(BufferedReader in,PrintWriter out) {
		this.in = in;
		this.out = out;
	} 

	/**
	 * get the view presenter
	 * @return presenter
	 */
	public Presenter getpresenter() {
		return presenter;
	}
	/**
	 * set the view presenter
	 * @return presenter
	 */
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	public void start()
	{		 
		new Thread(new Runnable() {
			@Override
			public void run() {	
				String[] args; 
				String str;
				try {
					//printing the first user request menu
					printOutput("Please enter the command you want to perform:");
					
					//continuing until getting exit command
					while(!(str = in.readLine()).equals("exit"))
					{
						ArrayList<String> list = new ArrayList<String>();
						list.add(str);
						printOutput("Please enter the next value for the command, when finished enter Stop");
						String answer = in.readLine();
						if(!answer.equalsIgnoreCase("Stop"))
						{
							list.add(answer);
						}
						while (!answer.equalsIgnoreCase("Stop"))
						{
							printOutput("Please enter the next value for the command, when finished enter Stop");
							if (!(answer =in.readLine()).equalsIgnoreCase("stop")) 
							{
								list.add(answer);
							}
						}
						int cnt = 0;
						args = new String[list.size()];
						for (String s : list)
						{
							args[cnt]=s;
							cnt++;
						}
						//send the command and values to the presenter
						notifyObservers(args);					
						//printing the first user request menu
						printOutput("\nPlease enter the command you want to perform:");
					}
					//send command exit to presenter
					args = new String[1];
					args[0] = "exit";
					notifyObservers(args);	
				} catch (IOException e) {
					printOutput(e.getMessage());
				}
					}}
			).start();
	}
	
	@Override
	public void printOutput(String string) {
		out.println(string);
		out.flush();	
	}

	@Override
	public void printDir(String[] arr) {
		out.println("The files and directories in the path are: ");
		for (String s : arr)
		{
			out.println(s);
		}
		out.flush();
	}

	@Override
	public void displayMaze(Maze3d maze) {
		if (maze== null) 
		{
			printOutput("this maze does not exist");
		} 
		else 
		{
			for(int k=0;k<maze.getMaze()[0].length;k++)
			{
				int[][] maze2dy=maze.getCrossSectionByY(k);
				
				printOutput("maze in level " +k + ":");
				printOutput("{");
				for(int i=0;i<maze2dy.length;i++)
				{
					System.out.print("{");
					for(int j=0;j<maze2dy[i].length;j++)
					{
						System.out.print(maze2dy[i][j]);
						if (j != maze2dy[i].length-1)
						{
							System.out.print(", ");
						}
					}
					printOutput("},");
				}
				printOutput("}");
			}
			printOutput("");
			// prints the maze entrance
			printOutput("The entrance point of the maze:");
			printOutput(maze.getStartPosition().toString());
			// prints the maze exit position
			printOutput("The goal point of the maze:");
			printOutput(maze.getGoalPosition().toString());
			printOutput("");
		}
		
	}

	@Override
	public void displayCrossSectionBy(int[][] arr, String axis, String index) {
		printOutput("maze in section " +axis + ":");
		printOutput("in level " +index + ":");
		printOutput("{");
		for(int i=0;i<arr.length;i++)
		{
			System.out.print("{");
			for(int j=0;j<arr[i].length;j++)
			{
				System.out.print(arr[i][j]);
				if (j != arr[i].length-1)
				{
					System.out.print(", ");
				}
			}
			printOutput("},");
		}
		printOutput("}");
		
	}

	@Override
	public void displayMazeSize(int size, String name) {
		printOutput("The maze "+name +" size is:"+size);	
		
	}

	@Override
	public void displayFileSize(long size, String name) {
		printOutput("The file "+name +" size is:"+size);
		
	}

	@Override
	public void displaySolution(Solution<Position> sol) {
		for (Position s: sol.getPath())
		{
			printOutput(s.toString());
		}
		
	}

	

}
