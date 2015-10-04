package view;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;

public class ViewGUI extends CommonView {

	MazeWindow window;
	
	public ViewGUI() {
		window =  new MazeWindow("maze window", 300, 500);
	}
	
	@Override
	public void start()
	{	
		window.run();
	}
	
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
	public void displayCrossSectionBy(int[][] arr, String axis, String index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMazeSize(int size, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayFileSize(long size, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displaySolution(Solution<Position> sol) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String string) {
		// TODO Auto-generated method stub

	}

}
