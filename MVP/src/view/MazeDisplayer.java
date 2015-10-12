package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;



// this is (1) the common type, and (2) a type of widget
// (1) we can switch among different MazeDisplayers
// (2) other programmers can use it naturally
public abstract class MazeDisplayer extends Canvas{
	
	int[][] mazeData ;

	
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}

	
	public int[][] getMaze() {
		return mazeData;
	}


	public void setMaze(int[][] maze) {
		this.mazeData = maze;
	}


	public abstract  void setCharacterPosition(int row,int col);

	public abstract void moveUp();

	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();

}