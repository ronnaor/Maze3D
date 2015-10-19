package view;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
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
	Label up;
	Label down;
	Button menu;
	Button start;
	Button reset;
	Button hint;
	Button sol;
	Button save;
	Button exit;
	MazeDisplayer mazed;
	KeyListener arrowKeyListener;
	Image upIm;
	Image downIm;
	String mazeName;
	boolean solving;
	
	/**
	 * constructor for mazeWindow
	 * @param title the name of the window
	 * @param width start width of window
	 * @param height start height of window
	 * @param listeners HashMap of listeners for the widgets in the window
	 * @param maze the maze we will work with
	 * @param arrowKeyListener KeyListener 
	 * @param mazeName the name of the maze
	 */
	public MazeWindow(String title, int width, int height,HashMap<String, Listener> listeners ,Maze3d maze,KeyListener arrowKeyListener, String mazeName) {
		super(title, width, height, listeners);
		this.maze = maze;
		this.arrowKeyListener=arrowKeyListener;
		this.mazeName = mazeName;
		Image temp = new Image(display, "./resources/images/up.jpg");
		this.upIm = new Image(display, temp.getImageData().scaledTo(60,50));
		temp = new Image(display, "./resources/images/down.jpg");
		this.downIm = new Image(display, temp.getImageData().scaledTo(60,50));
		this.solving = false;
	}

	
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		//start button
		start=new Button(shell, SWT.PUSH);
		start.setText("Start");
		start.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		//Maze2D the maze
		mazed=new Maze2D(shell, SWT.BORDER, maze.getStartPosition());
		mazed.setMaze(maze);
		mazed.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,10));
		mazed.addKeyListener(arrowKeyListener);
		
		//reset button
		reset=new Button(shell, SWT.PUSH);
		reset.setText("reset");
		reset.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		reset.setEnabled(false);
		
		//hint button
		hint=new Button(shell, SWT.PUSH);
		hint.setText("hint");
		hint.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		hint.setEnabled(false);
		hint.addListener(SWT.Selection,listeners.get("hint")); //add listener from listeners
		
		// solve button
		sol=new Button(shell, SWT.PUSH);
		sol.setText("solve");
		sol.setLayoutData(new GridData(SWT.FILL,SWT.FILL, false, false, 1, 1));
		sol.setEnabled(false);
		sol.addListener(SWT.Selection,listeners.get("sol")); //add listener from listeners
		
		//stop play and go back to menu button
		menu=new Button(shell, SWT.PUSH);
		menu.setText("stop play");
		menu.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		menu.addListener(SWT.Selection,listeners.get("menu")); //add listener from listeners
		
		//exit button
		exit =new Button(shell,SWT.PUSH);
	    exit.setText("exit");
	    exit.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
	    exit.addListener(SWT.Selection,listeners.get("exit")); //add listener from listeners
		
	    //image of up that is enabled when can move up a floor
		up = new Label(shell, SWT.BORDER);
		up.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));;
		up.setImage(upIm);
		up.setVisible(false);
		
		//image of down that is enabled when can move down a floor
		down = new Label(shell, SWT.BORDER);
		down.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));;
		down.setImage(downIm);
		down.setVisible(false);
		 
		//start-button listeners
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
								
							}
						});
					}
				};				
				timer.scheduleAtFixedRate(task, 0, 100);				
				start.setEnabled(false);
				reset.setEnabled(true);
				hint.setEnabled(true);
				sol.setEnabled(true);
				solving = true;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		start.addListener(SWT.Selection,listeners.get("start"));
		
		//reset-button listeners
		reset.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				timer.cancel();
				task.cancel();
				timer=new Timer();
				task=new TimerTask() {
					@Override
					public void run() {
						display.syncExec(new Runnable() {
							@Override
							public void run() {
								
							}
						});
					}
				};				
				timer.scheduleAtFixedRate(task, 0, 100);				
			
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		reset.addListener(SWT.Selection,listeners.get("reset"));
		
		//x-button listener
		shell.addListener(SWT.Close, listeners.get("exit"));
		
		
	}

	@Override
	void close() {
		
		if (timer!=null)
		{
			timer.cancel();
		}
		if (task!=null)
		{
			task.cancel();
		}
		this.shell.dispose();
		
	}

	//getters and setters
	/**
	 * get maze
	 * @return MAze3d
	 */
	public Maze3d getMaze() {
		return maze;
	}

	/**
	 * set maze
	 * @param maze Maze3d
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	/**
	 * get maze name
	 * @return String maze name
	 */
	public String getMazeName() {
		return mazeName;
	}
	
	/**
	 * set mazeName
	 * @param mazeName String the name
	 */
	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}


	/**
	 * move the character to the new position
	 * @param direction String the direction to move the character
	 * @param upPossiblle bool if can move floor up from new position
	 * @param downPossiblle bool if can move floor down from new position
	 */
	public void move(String direction, boolean upPossiblle, boolean downPossiblle) {
		
			
			display.syncExec(new Runnable() {

				@Override
				public void run() {
					
						down.setVisible(downPossiblle);
						up.setVisible(upPossiblle);
				}
			});
		
			if(direction.equals("UP"))
				mazed.moveUp();
			if(direction.equals("DOWN"))
				mazed.moveDown();
			if(direction.equals("RIGHT"))
				mazed.moveRight();
			if(direction.equals("LEFT"))
				mazed.moveLeft();
			if(direction.equals("PAGE_DOWN"))
				mazed.movePageDown();
			if(direction.equals("PAGE_UP"))
				mazed.movePageUp();
			if(direction.equals("START"))
				mazed.moveStart();
			
			
		}

	/**
	 * move the character to the new position
	 * @param p the new position
	 * @param upPossiblle bool if can move floor up from new position
	 * @param downPossiblle bool if can move floor down from new position
	 */
	public void move(Position p, boolean upPossiblle, boolean downPossiblle) {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				
					down.setVisible(downPossiblle);
					up.setVisible(upPossiblle);
			}
		});
		
		mazed.move(p);
		
	}

	/**
	 * what happens when the maze is solved
	 */
	public void solved() {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				
				start.setEnabled(true);
				reset.setEnabled(false);
				hint.setEnabled(false);
				sol.setEnabled(false);
				down.setVisible(false);
				up.setVisible(false);
				solving = false;
			}
		});
		
		if (timer!=null)
		{
			timer.cancel();
		}
		if (task!=null)
		{
			task.cancel();
		}
		updateMessageBox("maze solved");
	}

	/**
	 * check if we are in the middle of solving
	 * @return boolean
	 */
	public boolean isSolving() {
		return solving;
	}

	/**
	 * set solving
	 * @param solving boolean
	 */
	public void setSolving(boolean solving) {
		this.solving = solving;
	}
		
	


}
