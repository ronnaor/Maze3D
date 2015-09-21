package algorithms.demo;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Maze3dByteArr;
import algorithms.mazeGenarators.Maze3dGenerator;
import algorithms.mazeGenarators.Position;
import algorithms.mazeGenarators.MyMaze3dGenerator;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattenDistance;
import algorithms.search.MazeSearchable;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * class Demo
 *
 */
public class Demo {
	/**
	 * method that prints a maze and solutions
	 */
	public void run()
	{
		Maze3dGenerator mg = new MyMaze3dGenerator();
		// generate  3d maze
		Maze3d maze=mg.generate(9,8,7);
		//print the maze by levels (y axis)
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
		// using task to solve the maze with bfs and A*
		/*Task<Position> task = new MazeSearcherTask(new MazeSearchable(maze,1));
		System.out.println("Solution with BFS:");
		task.doTask(new BFS<Position>());
		System.out.println();
		System.out.println("Solution with A* air distance heuristic:");
		task.doTask(new AStar<Position>(new MazeAirDistance()));
		System.out.println();
		System.out.println("Solution with A* manhatten distance heuristic:");
		task.doTask(new AStar<Position>(new MazeManhattenDistance()));*/
		
		////part 3
		System.out.println("------------part3-------------");
		
		try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream("1.maz"));
			out.write(maze.toByteArray());
			out.flush();
			out.close();
			InputStream in=new MyDecompressorInputStream(new FileInputStream("1.maz"));
			byte b[]=new byte[maze.toByteArray().length];
			in.read(b);
			in.close();
			Maze3d loaded=new Maze3dByteArr(b);
			System.out.println(loaded.equals(maze));
			
			for(int k=0;k<loaded.getMaze()[0].length;k++)
			{
				int[][] maze2dy=loaded.getCrossSectionByY(k);
				
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
			System.out.println(loaded.getStartPosition());
			// prints the maze exit position
			System.out.println("The goal point of the maze:");
			System.out.println(loaded.getGoalPosition());
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	public static void main(String[] args) {
		Demo d = new Demo();
		d.run();
	}
}
