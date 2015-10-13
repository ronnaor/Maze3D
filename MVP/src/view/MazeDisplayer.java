package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;



// this is (1) the common type, and (2) a type of widget
// (1) we can switch among different MazeDisplayers
// (2) other programmers can use it naturally
public abstract class MazeDisplayer extends Canvas{
	
	Maze3d maze;
	int[][] mazeData ;
	Position start ;
	Position exit ;
	
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}

	

	public Maze3d getMaze() {
		return maze;
	}

	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	public int[][] getMazeData() {
		return mazeData;
	}


	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
	}


	public Position getStart() {
		return start;
	}


	public void setStart(Position start) {
		this.start = start;
	}


	public Position getExit() {
		return exit;
	}


	public void setExit(Position exit) {
		this.exit = exit;
	}


	public abstract  void setCharacterPosition(int x,int y, int z);

	public abstract void moveUp();

	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();
	
	public  abstract void movePageUp();

	public  abstract void movePageDown();

}