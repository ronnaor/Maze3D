package model;

import java.util.Observable;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
/**
 * class MyModel that extends Observable and implements Model will be defining the data to be displayed
 *
 */
public class MyModel extends Observable implements Model {

	@Override
	public void getDir(String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generate3DMaze(String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Maze3d getMaze(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[][] getCrossSectionBy(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveMaze(String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadMaze(String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMazeSize(String[] args) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getFileSize(String[] args) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void solve(String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Solution<Position> getSoultion(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exit(String[] args) {
		// TODO Auto-generated method stub
		
	}

	
}
