package view;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;

/**
 * 
 * interface View
 *
 */
public interface View {
	
	void printOutput(String string);

	void printDir(String[] arr);

	void displayMaze(Maze3d maze);

	void displayCrossSectionBy(int[][] arr, String string, String string2);

	void displayMazeSize(int size, String string);

	void displayFileSize(long l, String string);

	void displaySolution(Solution<Position> sol);

	
	
}
