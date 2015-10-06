package view;

import java.util.HashMap;

import org.eclipse.swt.widgets.Listener;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;

public class ViewGUI extends CommonView {

	MazeWindow window;
	HashMap<String, Listener> listeners;
	
	public ViewGUI() {
		this.window =  new StartWindow("maze window", 300, 500,listeners);
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
		
	}

	@Override
	public void displayCrossSectionBy(int[][] arr, String axis, String index) {
		MazeWindow mW = new MazeWindow("maze", 500, 500, listeners);
		mW.setMazeData(arr);
		mW.run();

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
