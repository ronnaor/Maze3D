package view;

import java.util.Observable;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;

/**
 *  class MyView extends Observable implements View, 
 * displays data and user commands to the presenter to act upon that data.
 *
 */
public class ViewCLI extends Observable implements View {

	@Override
	public void printOutput(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printDir(String[] arr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMaze(Maze3d maze) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayCrossSectionBy(int[][] arr, String string, String string2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMazeSize(int size, String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayFileSize(long l, String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolution(Solution<Position> sol) {
		// TODO Auto-generated method stub
		
	}

	

}
