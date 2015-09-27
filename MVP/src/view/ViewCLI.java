package view;

import java.io.File;
import java.io.PrintWriter;
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
	private Presenter presenter;
	private PrintWriter out;
	
	@Override
	public void printOutput(String string) {
		out.println(string);
		out.flush();		
	}

	@Override
	public void printDir(String[] path) {
		//checking that we got enough data
				if (path.length<1)
				{
					printOutput("not enough data");
				}
				else
				{
					File file; 
					try
					{
					file= new File(path[0]);
						for (String s : file.list())
						{
							printOutput(s);
						}
					}
					catch (Exception e) {
						printOutput("file does not exist");
					}	
				}		
	}
	@Override
	public void display(Maze3d maze) 
	{
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
	public void displayCrossSectionBy(int[][] arr, String axis, String index )
	{
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
	public void mazeSize(int maze,String args) {
		printOutput("The maze "+args +" size is:"+maze);		
	}
	
	@Override
	public void fileSize(String[] args) {
		if (args.length < 1) 
		{
			printOutput("not enough data");
		}
		else
		{
			printOutput("The maze in the file size is: "+(new File(args[0]+".maz").length()));	
		}
			
	}
	
	@Override
	public void displaySolution(Solution<Position> solve) {
		for (Position s: solve.getPath())
		{
			printOutput(s.toString());
		}
	}
	/**
	 * get the view presenter
	 * @return presenter
	 */
	public Presenter getpresenter() {
		return presenter;
	}
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
