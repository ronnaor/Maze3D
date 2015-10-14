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
	
	public ViewGUI() {
		this.listeners=new HashMap<String,Listener>();
		initListeners();
		this.startWindow =  new StartWindow("menu", 300, 500,listeners);
		this.mazeWindow = new MazeWindow("game", 300, 500,listeners, maze,arrowKeyListener, "");
		
	}
	
	public void initListeners()
	{

		listeners.put("ok",new Listener() 
		{
			
			@Override
			public void handleEvent(Event arg0) {
				args = startWindow.getArgs();
				if(args[0].equals("open properties"))
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
				else if (args[0].equals("Play"))
				{
					setChanged();
					notifyObservers(args);
					while(maze==null){}
					if (maze!= null)
					{
						pos = maze.getStartPosition();
						mazeWindow =  new MazeWindow("game", 300, 500,listeners, maze,arrowKeyListener, args[1]);
						mazeWindow.run();
					}
					
				}
				else if (args[0].equals("load maze"))
				{
	
					args[0] = "display";
					setChanged();
					notifyObservers(args);
					if (maze != null)
					{
						pos = maze.getStartPosition();
						mazeWindow =  new MazeWindow("game", 300, 500,listeners, maze,arrowKeyListener, args[1]);
						mazeWindow.run();
					}
				}
				else
				{
					setChanged();
					notifyObservers(args);
				}
				
				
			}
		}); 
		
		listeners.put("exit",new Listener() 
		{
			
			@Override
			public void handleEvent(Event arg0) {
				args = new String[] {"exit"};
				setChanged();
				notifyObservers(args);
				if (!startWindow.shell.isDisposed())
				{
					startWindow.setDisplayDisposed(true);
					startWindow.close();
				}
				if (!mazeWindow.shell.isDisposed())
				{
					mazeWindow.setDisplayDisposed(true);
					mazeWindow.close();
				}
				
			}
		}); 
		
		listeners.put("start",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				pos = maze.getStartPosition();
				mazeWindow.move("START", makeUpPossiblle(),makeDownPossiblle());
			}
		});
		listeners.put("reset",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				pos = maze.getStartPosition();
				mazeWindow.move("START", makeUpPossiblle(),makeDownPossiblle());
			}
		});
		
		listeners.put("hint",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				pos = maze.getStartPosition();
				mazeWindow.move("START", makeUpPossiblle(),makeDownPossiblle());
			}
		});
		
		listeners.put("sol",new Listener() {
			
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
				while (solution == null){}
				for (Position p: solution.getPath())
				{
					pos = p;
					mazeWindow.move(p, makeUpPossiblle(),makeDownPossiblle());
				}
			
			}
		});
		
		listeners.put("menu",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				
				if (!mazeWindow.shell.isDisposed())
				{
					mazeWindow.close();
				}
				
				
			}
		});
		
		arrowKeyListener=new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg) {
				
				int x=pos.getX();
				int y=pos.getY();
				int z=pos.getZ();
				pos = new Position(x, y, z);
				int[][][] array=maze.getMaze();
				if(arg.keyCode==SWT.ARROW_RIGHT)
				{
					
					if((z<array[0][0].length-1) && (array[x][y][z+1]==0))
					{
						pos = new Position(x, y, z+1);
						mazeWindow.move("RIGHT", makeUpPossiblle(),makeDownPossiblle());
					}
				}
				else if(arg.keyCode==SWT.ARROW_LEFT)
				{
					if((z>0)&&(array[x][y][z-1]==0))
					{
						pos = new Position(x, y, z-1);
						mazeWindow.move("LEFT", makeUpPossiblle(),makeDownPossiblle());
					}
				}
				else if(arg.keyCode==SWT.ARROW_UP)
				{
					if((y>0)&&(array[x][y-1][z]==0))
					{
						pos = new Position(x, y-1, z);
						mazeWindow.move("UP", makeUpPossiblle(),makeDownPossiblle());
					}

				}
				else if(arg.keyCode==SWT.ARROW_DOWN)
				{
					if((y<array[0].length-1)&&(array[x][y+1][z]==0))
					{
						pos = new Position(x, y+1, z);
						mazeWindow.move("DOWN", makeUpPossiblle(),makeDownPossiblle());
					}
				}
				else if(arg.keyCode==SWT.PAGE_DOWN)
				{
					if((x>0)&&(array[x-1][y][z]==0))
					{
						pos = new Position(x-1, y, z);
						mazeWindow.move("PAGE_DOWN", makeUpPossiblle(),makeDownPossiblle());
					}
				}
				else if(arg.keyCode==SWT.PAGE_UP)
				{
					if((x<array.length-1)&&(array[x+1][y][z]==0))
					{
						pos = new Position(x+1, y, z);
						mazeWindow.move("PAGE_UP", makeUpPossiblle(),makeDownPossiblle());
					}
				}
				
			}

			
		};
	}
	
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
	public void printOutput(String string) {
		if (!startWindow.shell.isDisposed())
		{
			startWindow.updateMessageBox(string);
		}
		

	}

	@Override
	public void printDir(String[] arr) {}

	@Override
	public void displayMaze(Maze3d maze) {
		this.maze = maze;
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
	}

	@Override
	public void error(String string) {
		if (!startWindow.shell.isDisposed())
		{
			startWindow.errMessageBox(string);
		}

	}

	@Override
	public void changeProp(String[] args) {
		if (args[1].equalsIgnoreCase("CLI"))
		{
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
