package view;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;


public class MazeWindow extends BasicWindow{

	Timer timer;
	TimerTask task;
	Maze3d maze;
	int[][] mazeData;
	Position now;
	Label up;
	Label down;
	Button menu;
	Button start;
	Button reset;
	Button hint;
	Button solve;
	Button save;
	MazeDisplayer mazed;
	KeyListener arrowKeyListener;
	
	public MazeWindow(String title, int width, int height,HashMap<String, Listener> listeners ,Maze3d maze,KeyListener arrowKeyListener) {
		super(title, width, height, listeners);
		this.maze = maze;
		if (maze != null)
		{
			int z = this.maze.getStartPosition().getZ();
			this.mazeData = maze.getCrossSectionByZ(z);
			this.now = maze.getStartPosition();
		}
		this.arrowKeyListener=arrowKeyListener;
	}

	
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		
		this.start=new Button(shell, SWT.PUSH);
		start.setText("Start");
		start.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		this.mazed=new Maze3D(shell, SWT.BORDER, maze.getStartPosition());
		mazed.setMazeData(mazeData);
		mazed.setStart(maze.getStartPosition());
		mazed.setExit(maze.getGoalPosition());
		mazed.setMaze(maze);
		mazed.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,10));
		mazed.addKeyListener(arrowKeyListener);
		
		this.reset=new Button(shell, SWT.PUSH);
		reset.setText("reset");
		reset.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		reset.setEnabled(false);
		
		this.hint=new Button(shell, SWT.PUSH);
		hint.setText("hint");
		hint.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		hint.setEnabled(false);
		
		this.solve=new Button(shell, SWT.PUSH);
		solve.setText("solve");
		solve.setLayoutData(new GridData(SWT.FILL,SWT.FILL, false, false, 1, 1));
		solve.setEnabled(false);
		
		this.save=new Button(shell, SWT.PUSH);
		save.setText("save game");
		save.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		save.setEnabled(false);
		
		this.menu=new Button(shell, SWT.PUSH);
		menu.setText("stop play");
		menu.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		menu.setEnabled(false);
		
		this.up = new Label(shell, SWT.ARROW_UP);
		
		this.down = new Label(shell, SWT.ARROW_UP);
		
		
		
		
		start.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				timer=new Timer();
				task=new TimerTask() {
					@Override
					public void run() {
						display.syncExec(new Runnable() {
							@Override
							public void run() {
								//mazed.setCharacterPosition(maze.getStartPosition().getX(),maze.getStartPosition().getY(),maze.getStartPosition().getZ());
							}
						});
					}
				};				
				timer.scheduleAtFixedRate(task, 0, 100);				
				start.setEnabled(false);
				reset.setEnabled(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		reset.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				task.cancel();
				timer.cancel();
				start.setEnabled(true);
				reset.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
	}

	@Override
	void close() {
		shell.dispose();
		
	}

	public int[][] getMazeData() {
		return mazeData;
	}

	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
	}



	public Maze3d getMaze() {
		return maze;
	}



	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}



	public Position getNow() {
		return now;
	}



	public void setNow(Position now) {
		this.now = now;
	}



	public void move(String direction, boolean upPossiblle, boolean downPossiblle) {
		
			
			/*display.syncExec(new Runnable() {

				@Override
				public void run() {
					
						down.setEnabled(downPossiblle);
						up.setEnabled(upPossiblle);
				}
			});*/

			
			walk(mazed, direction);
			
		}
		
	


	private void walk(MazeDisplayer maze , String dir){
		
		if(dir.equals("UP"))
			maze.moveUp();
		if(dir.equals("DOWN"))
			maze.moveDown();
		if(dir.equals("RIGHT"))
			maze.moveRight();
		if(dir.equals("LEFT"))
			maze.moveLeft();
		if(dir.equals("PAGE_DOWN"))
			maze.movePageDown();
		if(dir.equals("PAGE_UP"))
			maze.movePageUp();;
		
		maze.redraw();
	}

}
