package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenarators.Maze3d;
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
		cli.setIn(in);
		cli.setOut(out);
	}
	void start()
	{
		
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
	public void printDir(String[] path) {
		if (path== null) 
		{
			cli.printOutput("not enough data");
		} 
		File file = new File(path[0]);
		for (String s : file.list())
		{
			cli.printOutput(s);
		}
	}

	@Override
	public void display(String[] mazeName) 
	{
		if (mazeName== null) 
		{
			cli.printOutput("not enough data");
		} 
		Maze3d maze;
		if ((maze= controller.getMaze(mazeName[0]))!= null)
		{
			for(int k=0;k<maze.getMaze()[0].length;k++)
			{
				int[][] maze2dy=maze.getCrossSectionByY(k);
				
				System.out.println("maze in level " +k + ":");
				System.out.println("{");
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
					System.out.println("},");
				}
				System.out.println("}");
			}
			System.out.println();
			// prints the maze entrance
			System.out.println("The entrance point of the maze:");
			System.out.println(maze.getStartPosition());
			// prints the maze exit position
			System.out.println("The goal point of the maze:");
			System.out.println(maze.getGoalPosition());
			System.out.println();
		}
		else
		{
			cli.printOutput("no such maze");
		}
	}
	
	@Override
	public void displayCrossSectionBy(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mazeSize(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void fileSize(String[] args) {
		if (args.length < 1) 
		{
			cli.printOutput("not enough data");
		}
		cli.printOutput("The maze in the file size is: "+(new File(args[0]+".maz").length()));		
	}
	
	@Override
	public void displaySolution(String[] args) {
		// TODO Auto-generated method stub
		
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
