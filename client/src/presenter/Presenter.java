package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
import model.Model;
import view.View;

/**
 * class Presenter that implements Observer will coordinate between a model and a view
 *
 */
public class Presenter implements Observer {

	private Model model;
	private View view;
	private HashMap<String, Command<Void>> viewCommands;
	private HashMap<String, Command<Void>> modelCommands;

	/**
	 * Ctor for Presenter
	 * @param model the model the Presenter will work with
	 * @param view the view the Presenter will work with
	 */
	public Presenter(Model model,  View view) {
		this.model = model;
		this.view = view;
		this.viewCommands = new HashMap<String, Command<Void>>();
		this.modelCommands = new HashMap<String, Command<Void>>(); 
		initViewCommands();
		initModelCommands();
	}
	/**
	 * initiate the model commands hashmap
	 */
	private void initModelCommands() {
		modelCommands.put("printDir", new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				view.printDir(args);
				return null;
			}
		});
		modelCommands.put("properties change", new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				view.changeProp(args);
				return null;
			}
		});
		modelCommands.put("generated", new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				view.mazegenerated(args[1]);
				return null;
			}
		});
		modelCommands.put("printUpdate", new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				view.printOutput(args[1]);
				return null;
			}
		});
		modelCommands.put("error", new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				view.error(args[1]);
				return null;
			}
		});
	}
	/**
	 * initiate the view commands hashmap
	 */
	private void initViewCommands() {
		viewCommands.put("dir", new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				model.getDir(args);
				return null;
			}		
		});
		viewCommands.put("generate 3d maze", new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				model.generate3DMaze(args);
				return null;
			}		
		});
		viewCommands.put("display",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				Maze3d maze = model.getMaze(args);
				if (maze !=null)
				{
					view.displayMaze(maze);
				}
				return null;
			}		
		});
		viewCommands.put("display cross section by",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				int[][] arr = model.getCrossSectionBy(args);
				if (arr!=null)
				{
					view.displayCrossSectionBy(arr, args[2], args[3]);
				}
				return null;
			}		
		});
		viewCommands.put("save maze",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				model.saveMaze(args);
				return null;
			}		
		});
		viewCommands.put("load maze",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				model.loadMaze(args);
				return null;
			}		
		});
		viewCommands.put("maze size",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				int size= model.getMazeSize(args);
				if (size!=0)
				{
					view.displayMazeSize(size,args[1]);
				}
				return null;
			}		
		});
		viewCommands.put("file size",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				long l = model.getFileSize(args);
				if (l!=0)
				{
					view.displayFileSize(l, args[1]);
				}
				return null;
			}		
		})
		;viewCommands.put("solve",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				model.solve(args);
				Solution<Position> solFile = model.sol(args);
				if (solFile !=null)
				{
					model.saveFile();
				}
				return null;
			}		
		})
		;viewCommands.put("display solution",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				Solution<Position> sol= model.getSoultion(args);
				if (sol != null)
				{
					view.displaySolution(sol);
				}
				return null;
			}		
		});
		viewCommands.put("open properties",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				model.changeProperties(args);
				return null;
			}		
		});
		viewCommands.put("Play",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				Maze3d mazep = model.play(args);
				if (mazep !=null)
				{
					view.displayMaze(mazep);
				}
				else 
				{
					view.playError();
				}
				return null;
			}		
		});
		viewCommands.put("sol",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				Solution<Position> solution = model.sol(args);
				if (solution !=null)
				{
					view.displaySolution(solution);
					model.removeMidMaze(args);
				}
				else 
				{
					view.solError();
					model.removeMidMaze(args);
				}
				return null;
			}		
		});
		viewCommands.put("open properties",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				model.changeProperties(args);
				return null;
			}		
		});
		viewCommands.put("exit",new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				model.exit(args);
				return null;
			}		
		});
		
		
	}
	/**
	 * get the model the presenter work with
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}
	/**
	 * set the model the presenter work with
	 * @param model
	 */
	public void setModel(Model model) {
		this.model = model;
	}
	/**
	 * get the view the presenter work with
	 * @return the view
	 */
	public View getView() {
		return view;
	}
	/**
	 * set the view the presenter work with
	 * @param view
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * @Override for Observer method update
	 */
	@Override
	public void update(Observable obs, Object obj) {
		String[] args = (String[])obj;
		if (obs == model)
		{
			if (modelCommands.containsKey(args[0]))
			{
				modelCommands.get(args[0]).doCommand(args);
			}
			else
			{
				view.error("no such command");
			}
		}
		if (obs == view)
		{
			if (viewCommands.containsKey(args[0]))
			{
				viewCommands.get(args[0]).doCommand(args);
			}
			else
			{
				view.error("no such command");
			}
		}

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
	 * getting the commands of the view
	 * @return hashmap with key values as string names and vaules as the commands
	 */
	public HashMap<String, Command<Void>> getViewCommands() {
		return viewCommands;
	}
	/**
	 * settings the hashmap of the view commands
	 * @param viewCommands hashmap consisting key values as string names and vaules as the commands 
	 */
	public void setViewCommands(HashMap<String, Command<Void>> viewCommands) {
		this.viewCommands = viewCommands;
	}
	/**
	 * getting the commands of the model
	 * @return hashmap with key values as string names and vaules as the commands
	 */
	public HashMap<String, Command<Void>> getModelCommands() {
		return modelCommands;
	}
	/**
	 * * settings the hashmap of the model commands
	 * @param modelCommands hashmap consisting key values as string names and vaules as the commands
	 */
	public void setModelCommands(HashMap<String, Command<Void>> modelCommands) {
		this.modelCommands = modelCommands;
	}
	
}
