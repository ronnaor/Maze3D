package view;

import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
/**
 *  class ViewGUI extends Observable implements View, 
 * displays data and user commands to the presenter to act upon that data.
 *
 */
public class ViewGUI extends CommonView {

	StartWindow startWindow;
	MazeWindow mazeWindow;
	HashMap<String, Listener> listeners;
	String[] args;
	String randMazeName;
	Maze3d maze;
	Position pos;
	KeyListener arrowKeyListener;
	Solution<Position> solution;
	boolean mazeNull ;
	boolean solNull ;
	/**
	 * ctor that constructs the gui using listeners, startWindow and mazeWindow
	 */
	public ViewGUI() {
		this.listeners=new HashMap<String,Listener>();
		initListeners();
		this.startWindow =  new StartWindow("menu", 300, 500,listeners);
		this.mazeWindow = new MazeWindow("game", 300, 500,listeners, maze,arrowKeyListener, "");
		this.mazeNull=true;
		this.solNull=true;
		
	}
	/**
	 * initiliazing the listeners
	 */
	public void initListeners()
	{

		listeners.put("ok",new Listener() 
		{
			
			@Override
			public void handleEvent(Event arg0) {
				args = startWindow.getArgs();
				if(args[0].equals("open properties")) // open file dialog and select file 
				{
					FileDialog file=new FileDialog(startWindow.getShell(),SWT.OPEN);
					file.setText("Open");
					String[] filter = { "*.xml" };
					file.setFilterExtensions(filter);
					String path = file.open();
					args[1] = path;
					setChanged();
					notifyObservers(args); 
				}
				//generate new maze
				else if (args[0].equals("generate 3d maze"))
				{
					setChanged();
					notifyObservers(args);
				}
				else if (args[0].equals("Play")) // generate new maze and start playing it (if already exists play the old maze
				{
					if (!startWindow.shell.isDisposed())
					{
						startWindow.close(); //close startWindow
					}
					setChanged();
					notifyObservers(args);
					while (mazeNull){}
					if (maze != null)
					{
						mazeNull=true;
						pos = maze.getStartPosition();
						//open the game window
						mazeWindow =  new MazeWindow("game", 300, 500,listeners, maze,arrowKeyListener, args[1]);
						mazeWindow.run();
					}
					else
					{
						mazeNull=true;
						//open the startWindow
						startWindow =  new StartWindow("menu", 300, 500,listeners);
						startWindow.run();
					}
					
				}
				else if (args[0].equals("load maze")) //load a maze that is already exists
				{
					if (!startWindow.shell.isDisposed())
					{
						startWindow.close(); //close startWindow
					}
					args[0] = "display";
					setChanged();
					notifyObservers(args);
					if (maze != null)
					{
						mazeNull=true;
						pos = maze.getStartPosition();
						//open the game window
						mazeWindow =  new MazeWindow("game", 300, 500,listeners, maze,arrowKeyListener, args[1]);
						mazeWindow.run();
					}
					else
					{
						mazeNull=true;
						//open the startWindow
						startWindow =  new StartWindow("menu", 300, 500,listeners);
						startWindow.run();
					}
				}
				else
				{
					setChanged();
					notifyObservers(args);
				}
				
				
			}
		}); 
		
		listeners.put("exit",new Listener() //exit the program
		{
			
			@Override
			public void handleEvent(Event arg0) {
				args = new String[] {"exit"};
				setChanged();
				notifyObservers(args); //close all threads working from Model
				if (!startWindow.shell.isDisposed())
				{
					startWindow.setDisplayDisposed(true);
					startWindow.close(); //close startWindow
				}
				if (!mazeWindow.shell.isDisposed())
				{
					mazeWindow.setDisplayDisposed(true);
					mazeWindow.close(); //close mazeWindow
				}
				
			}
		}); 
		
		listeners.put("start",new Listener() { //start play the maze
			
			@Override
			public void handleEvent(Event arg0) {
				pos = maze.getStartPosition();
				mazeWindow.move("START", makeUpPossiblle(),makeDownPossiblle());
			}
		});
		listeners.put("reset",new Listener() { //reset the game to the start
			
			@Override
			public void handleEvent(Event arg0) {
				pos = maze.getStartPosition();
				mazeWindow.move("START", makeUpPossiblle(),makeDownPossiblle());
			}
		});
		
		listeners.put("hint",new Listener() { //move one step in the correct way to the finish
			
			@Override
			public void handleEvent(Event arg0) {
				String[] str = new String[5];
				str[0] = "sol";
				str[1] = mazeWindow.getMazeName();
				str[2] = ((Integer)pos.getX()).toString();
				str[3] = ((Integer)pos.getY()).toString();
				str[4] = ((Integer)pos.getZ()).toString();
				setChanged();
				notifyObservers(str);
				while (solNull){}
				if (solution != null)
				{
					solNull=true;
					Position [] positions = new Position[solution.getPath().size()];
					int cnt = solution.getPath().size()-1;
					for (Position p: solution.getPath())
					{
						positions[cnt] = p;
						cnt--;
					}
					Thread thread=new Thread(new Runnable() {
						
						@Override
						public void run() 
						{
							
							try {
								Thread.sleep(800);
								pos = positions[1];
								mazeWindow.move(pos, makeUpPossiblle(),makeDownPossiblle());
								if (pos.getX()==maze.getGoalPosition().getX() && pos.getY()==maze.getGoalPosition().getY() && pos.getZ()==maze.getGoalPosition().getZ())
								{
									mazeWindow.solved();
								}
								
							} catch (Exception e) {
								if (!mazeWindow.shell.isDisposed())
								{
									mazeWindow.errMessageBox(e);
								}
								
							}
						
							
						}
					});
					thread.start();
				}
				
				else
				{
					solNull=true;
					error("problem with geting hint try again");
				}
				
			
			}
		});
		
		listeners.put("sol",new Listener() { //solve the game
			
			@Override
			public void handleEvent(Event arg0) {
				String[] str = new String[5];
				str[0] = "sol";
				str[1] = mazeWindow.getMazeName();
				str[2] = ((Integer)pos.getX()).toString();
				str[3] = ((Integer)pos.getY()).toString();
				str[4] = ((Integer)pos.getZ()).toString();
				setChanged();
				notifyObservers(str);
				while (solNull){}
				if (solution != null)
				{
					solNull=true;
					Position [] positions = new Position[solution.getPath().size()];
					int cnt = solution.getPath().size()-1;
					for (Position p: solution.getPath())
					{
						positions[cnt] = p;
						cnt--;
					}
					Thread thread=new Thread(new Runnable() {
						
						@Override
						public void run() 
						{
							for (Position p: positions)
							{
								try {
									Thread.sleep(800);
									pos = p;
									mazeWindow.move(p, makeUpPossiblle(),makeDownPossiblle());
									if (pos.getX()==maze.getGoalPosition().getX() && pos.getY()==maze.getGoalPosition().getY() && pos.getZ()==maze.getGoalPosition().getZ())
									{
										mazeWindow.solved();
									}
									
									
								} catch (Exception e) {
									if (!mazeWindow.shell.isDisposed())
									{
										mazeWindow.errMessageBox(e);
									}
								}
								
							}
							
						}
					});
					thread.start();
				}
				else
				{
					solNull=true;
					error("problem with geting hint try again");
				}
			
			}
		});
		
		listeners.put("menu",new Listener() { //return to startWindow
			
			@Override
			public void handleEvent(Event arg0) {
				maze = null;
				if (!mazeWindow.shell.isDisposed())
				{
					mazeWindow.close();
				}
				startWindow =  new StartWindow("menu", 300, 500,listeners);
				startWindow.run();
				
				
			}
		});
		
		arrowKeyListener=new KeyListener() { //add keys listeners
			
			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg) {
				
				if (!mazeWindow.isSolving()) //if the game did not start or ended the listener do nothing 
				{
					return;
				}
				
				int x=pos.getX();
				int y=pos.getY();
				int z=pos.getZ();
				pos = new Position(x, y, z);
				int[][][] array=maze.getMaze();
				if(arg.keyCode==SWT.ARROW_RIGHT)
				{
					
					if((z<array[0][0].length-1) && (array[x][y][z+1]==0)) // try moving one step right
					{
						pos = new Position(x, y, z+1);
						mazeWindow.move("RIGHT", makeUpPossiblle(),makeDownPossiblle());
						if (pos.getX()==maze.getGoalPosition().getX() && pos.getY()==maze.getGoalPosition().getY() && pos.getZ()==maze.getGoalPosition().getZ())
						{
							mazeWindow.solved();
						}
					}
				}
				else if(arg.keyCode==SWT.ARROW_LEFT) // try moving one step left
				{
					if((z>0)&&(array[x][y][z-1]==0))
					{
						pos = new Position(x, y, z-1);
						mazeWindow.move("LEFT", makeUpPossiblle(),makeDownPossiblle());
						if (pos.getX()==maze.getGoalPosition().getX() && pos.getY()==maze.getGoalPosition().getY() && pos.getZ()==maze.getGoalPosition().getZ())
						{
							mazeWindow.solved();
						}
					}
				}
				else if(arg.keyCode==SWT.ARROW_UP) // try moving one step up
				{
					if((y>0)&&(array[x][y-1][z]==0))
					{
						pos = new Position(x, y-1, z);
						mazeWindow.move("UP", makeUpPossiblle(),makeDownPossiblle());
						if (pos.getX()==maze.getGoalPosition().getX() && pos.getY()==maze.getGoalPosition().getY() && pos.getZ()==maze.getGoalPosition().getZ())
						{
							mazeWindow.solved();
						}
					}

				}
				else if(arg.keyCode==SWT.ARROW_DOWN) // try moving one step down
				{
					if((y<array[0].length-1)&&(array[x][y+1][z]==0))
					{
						pos = new Position(x, y+1, z);
						mazeWindow.move("DOWN", makeUpPossiblle(),makeDownPossiblle());
						if (pos.getX()==maze.getGoalPosition().getX() && pos.getY()==maze.getGoalPosition().getY() && pos.getZ()==maze.getGoalPosition().getZ())
						{
							mazeWindow.solved();
						}
					}
				}
				else if(arg.keyCode==SWT.PAGE_DOWN) // try moving one step floor down
				{
					if((x>0)&&(array[x-1][y][z]==0))
					{
						pos = new Position(x-1, y, z);
						mazeWindow.move("PAGE_DOWN", makeUpPossiblle(),makeDownPossiblle());
						if (pos.getX()==maze.getGoalPosition().getX() && pos.getY()==maze.getGoalPosition().getY() && pos.getZ()==maze.getGoalPosition().getZ())
						{
							mazeWindow.solved();
						}
					}
				}
				else if(arg.keyCode==SWT.PAGE_UP) // try moving one step floor up
				{
					if((x<array.length-1)&&(array[x+1][y][z]==0))
					{
						pos = new Position(x+1, y, z);
						mazeWindow.move("PAGE_UP", makeUpPossiblle(),makeDownPossiblle());
						if (pos.getX()==maze.getGoalPosition().getX() && pos.getY()==maze.getGoalPosition().getY() && pos.getZ()==maze.getGoalPosition().getZ())
						{
							mazeWindow.solved();
						}
					}
				}
				
			}

			
		};
	}
	/**
	 * Checking if page up could be possible
	 * @return true if yes
	 */
	public boolean makeDownPossiblle() {
		if(maze!=null)
		{
			int[][][] data=maze.getMaze();
			
			if((pos.getX()>0)&&(data[pos.getX()-1][pos.getY()][pos.getZ()]==0))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	/**
	 * Checking if page down could be possible
	 * @return true if yes
	 */
	public boolean makeUpPossiblle() {
		if(maze!=null)
		{
			int[][][] data=maze.getMaze();
			
			if((pos.getX()<data.length-1)&&(data[pos.getX()+1][pos.getY()][pos.getZ()]==0))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	

	@Override
	public void start()
	{	
		startWindow.run();
	}
	
	@Override
	public void printOutput(String string) {}

	@Override
	public void mazegenerated(String string) {
		if (!mazeWindow.shell.isDisposed())
		{
			mazeWindow.updateMessageBox(string);
		}
		else if (!startWindow.shell.isDisposed())
		{
			startWindow.updateMessageBox(string);
		}
		
	}
	@Override
	public void printDir(String[] arr) {}

	@Override
	public void displayMaze(Maze3d maze) {
		this.maze = maze;
		this.mazeNull = false;
		
		
	}

	@Override
	public void displayCrossSectionBy(int[][] arr, String axis, String index) {
		MazeWindow mW = new MazeWindow("maze", 500, 500, listeners, maze,arrowKeyListener, args[1]);
		mW.run();

	}

	@Override
	public void displayMazeSize(int size, String name) {}

	@Override
	public void displayFileSize(long size, String name) {}

	@Override
	public void displaySolution(Solution<Position> sol) {
		this.solution =sol;
		this.solNull = false;
	}

	@Override
	public void error(String string) {
		if (!mazeWindow.shell.isDisposed())
		{
			mazeWindow.errMessageBox(string);
		}
		else if (!startWindow.shell.isDisposed())
		{
			startWindow.errMessageBox(string);
		}

	}

	@Override
	public void changeProp(String[] args) {
		if (args[1].equalsIgnoreCase("CLI"))
		{
			String[] exit = new String[] {"exit"};
			setChanged();
			notifyObservers(exit); //close all threads working from Model
			if (!startWindow.shell.isDisposed())
			{
				startWindow.close();
			}
			if (!mazeWindow.shell.isDisposed())
			{
				mazeWindow.close();
			}
			boot.Run.main(args);
		}
		else
		{
			startWindow.updateMessageBox("Properties file loaded");
		}
		
	}

	
	
	

}
