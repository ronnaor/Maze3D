package view;

import java.util.HashMap;

import org.eclipse.swt.SWT;
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
	
	public ViewGUI() {
		this.listeners=new HashMap<String,Listener>();
		initListeners();
		this.startWindow =  new StartWindow("maze window", 300, 500,listeners);
		this.mazeWindow = new MazeWindow("maze window", 300, 500,listeners, maze);
		this.randMazeName = "randMaze";
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
					startWindow.close();
					args[0] = "generate 3d maze";
					args[1] = randMazeName;
					for (int i=2; i<5;i++)
					{
						args[i] = "10";
					}
					setChanged();
					notifyObservers(args);
					
					while (maze==null)
					{
						args[0] = "display";
						setChanged();
						notifyObservers(args);
					}
						
					mazeWindow =  new MazeWindow("maze window", 300, 500,listeners, maze);
					mazeWindow.run();
				}
				else if (args[0].equals("load maze"))
				{
					startWindow.close();
					args[0] = "display";
					setChanged();
					notifyObservers(args);
					if (maze != null)
					{
						mazeWindow =  new MazeWindow("maze window", 300, 500,listeners, maze);
						mazeWindow.run();
					}
					else
					{
						startWindow =  new StartWindow("maze window", 300, 500,listeners);
						startWindow.errMessageBox("This maze does not exists");
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
		
		listeners.put("exit",new Listener() 
		{
			
			@Override
			public void handleEvent(Event arg0) {
				args = new String[] {"exit"};
				setChanged();
				notifyObservers(args);
				if (!startWindow.shell.isDisposed())
				{
					startWindow.close();
				}
				if (!mazeWindow.shell.isDisposed())
				{
					mazeWindow.close();
				}
				
			}
		}); 
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
	public void printDir(String[] arr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMaze(Maze3d maze) {
		this.maze = maze;
	}

	@Override
	public void displayCrossSectionBy(int[][] arr, String axis, String index) {
		MazeWindow mW = new MazeWindow("maze", 500, 500, listeners, maze);
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
