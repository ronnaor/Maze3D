package controller;

import java.util.HashMap;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
import model.Model;
import view.View;

/**
 * abstract class MyController that implements from Controller
 *
 */
public class MyController implements Controller {
	protected  Model model;
	protected View view;
	protected HashMap<String, Command> commands;
	/**
	 * Ctor of MyController
	 * @param model getting model object
	 * @param view getting view object
	 */
	public MyController(Model model, View view) 
	{
		this.model= model;
		this.view = view;
		commands = new HashMap<String, Command>();
		initCommands();
	}
	/**
	 * get the model of the controller
	 * @return model
	 */
	public Model getModel() {
		return model;
	}
	/**
	 * set the model of the Controller
	 * @param model the model we will use in the controller
	 */
	public void setModel(Model model) {
		this.model = model;
	}
	/**
	 * get the view of the controller
	 * @return view
	 */
	public View getView() {
		return view;
	}
	/**
	 * set the view of the Controller
	 * @param view the view we will use in the controller
	 */
	public void setView(View view) {
		this.view = view;
	}
	/**
	 * get HashMap of the commands in the controller
	 * @return HashMap of commands
	 */
	public HashMap<String, Command> getCommands() {
		return commands;
	}
	/**
	 * set HashMap of the commands in the controller
	 * @param commands HashMap of commands 
	 */
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}
	/**
	 * Override of Controller method initCommands that set the commands we will use in the controller
	 */
	@Override
	public void initCommands() {
		commands.put("dir", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				view.printDir(args);
				
			}
		});

		commands.put("generate 3d maze", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				model.generate3DMaze(args);
				
			}
		});
		commands.put("display", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				Maze3d maze = model.getMaze(args);
				if (maze !=null)
				{
					view.display(maze);	
				}
			}
		});
		commands.put("display cross section by", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				int[][] arr = model.getCrossSectionBy(args);
				if (arr!=null)
				{
					view.displayCrossSectionBy(arr, args[0], args[1]);
				}	
			}
		});
		commands.put("save maze", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				model.saveMaze(args);	
			}
		});
		commands.put("load maze", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				model.loadMaze(args);
				
			}
		});
		commands.put("maze size", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				int maze= model.mazeSize(args);
				if (maze!=0)
				{
					view.mazeSize(maze,args[0]);
				}
			}
		});
		commands.put("file size", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				view.fileSize(args);
				
			}
		});
		commands.put("solve", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				model.solve(args);
				
			}
		});
		commands.put("display solution", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				Solution<Position> solve= model.getSoultion(args);
				if (solve != null)
				{
					view.displaySolution(solve);
				}
			}
		});
		commands.put("exit", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				model.exit(args);
				
			}
		});
		
	}
	/**
	 * casting and checking from string to integer
	 * @param str the variable we want to change
	 * @return The integer we changed the string to
	 */
	public static Integer tryParseInt(String str) {
		   try {
		      return Integer.parseInt(str);
		   } catch (NumberFormatException ex) {
		      return null;
		   }
		}
	/**
	 * sending the view what to print
	 * @param string the output we want
	 */
	public void outPut(String string) 
	{
		view.printOutput(string);
	}
	
}
