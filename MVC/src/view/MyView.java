package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
import controller.Command;
import controller.Controller;
import controller.MyController;
/**
 * class MyView that implements from View
 *
 */
public class MyView implements View {
	private MyController controller;
	private HashMap<String, Command> commands;
	private CLI cli;
	
	/**
	 * CTOR of MyView
	 * @param in the input stream object
	 * @param out the output stream object
	 */
	public MyView(BufferedReader in,PrintWriter out) {
		 this.cli = new CLI(in,out,this);
	}
	@Override
	public void start()
	{
		cli.start();
	}
	/**
	 * settings the commands from the new map
	 *  @param map HashMap of commands
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}
	@Override
	public HashMap<String, Command> controllerCommands() {
		return controller.getCommands();
	}
	@Override
	public void printDir(String[] path) {
		if (path.length<1)
		{
			cli.printOutput("not enough data");
		}
		else
		{
			File file; 
			try
			{
			file= new File(path[0]);
				for (String s : file.list())
				{
					cli.printOutput(s);
				}
			}
			catch (Exception e) {
				cli.printOutput("file does not exist");
			}	
		}
		
	}

	@Override
	public void display(Maze3d maze) 
	{
		if (maze== null) 
		{
			cli.printOutput("this maze does not exist");
		} 
		else 
		{
			for(int k=0;k<maze.getMaze()[0].length;k++)
			{
				int[][] maze2dy=maze.getCrossSectionByY(k);
				
				cli.printOutput("maze in level " +k + ":");
				cli.printOutput("{");
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
					cli.printOutput("},");
				}
				cli.printOutput("}");
			}
			cli.printOutput("");
			// prints the maze entrance
			cli.printOutput("The entrance point of the maze:");
			cli.printOutput(maze.getStartPosition().toString());
			// prints the maze exit position
			cli.printOutput("The goal point of the maze:");
			cli.printOutput(maze.getGoalPosition().toString());
			cli.printOutput("");
		}
		
	}
	
	@Override
	public void displayCrossSectionBy(int[][] arr, String axis, String index )
	{
		cli.printOutput("maze in section " +axis + ":");
		cli.printOutput("in level " +index + ":");
		cli.printOutput("{");
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
			cli.printOutput("},");
		}
		cli.printOutput("}");	
	}
	
	@Override
	public void mazeSize(int maze,String args) {
		cli.printOutput("The maze "+args +" size is:"+maze);		
	}
	
	@Override
	public void fileSize(String[] args) {
		if (args.length < 1) 
		{
			cli.printOutput("not enough data");
		}
		else
		{
			cli.printOutput("The maze in the file size is: "+(new File(args[0]+".maz").length()));	
		}
			
	}
	
	@Override
	public void displaySolution(Solution<Position> solve) {
		for (Position s: solve.getPath())
		{
			cli.printOutput(s.toString());
		}
	}
	/**
	 * getting the cli object
	 * @return the cli object
	 */
	public CLI getCli() {
		return cli;
	}
	/**
	 * setting the cli object
	 * @param cli the in and out object in cli class
	 */
	public void setCli(CLI cli) {
		this.cli = cli;
	}
	/**
	 * getting the commands from MyView
	 * @return the commands of HashMap
	 */
	@Override
	public HashMap<String, Command> getCommands() {
		return commands;
	}
	/**
	 * get the view controller
	 * @return controller
	 */
	public Controller getController() {
		return controller;
	}
	/**
	 * set controller to the view
	 * @param controller
	 */
	@Override
	public void setController(MyController controller) {
		this.controller = controller;
	}
	@Override
	public void printOutput(String str) {
		cli.printOutput(str);
		
	}

}
