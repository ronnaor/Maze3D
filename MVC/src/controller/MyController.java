package controller;

import java.util.HashMap;

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
				view.display(args);
				
			}
		});
		commands.put("display cross section by", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				view.display(args);
				
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
				view.mazeSize(args);
				
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
				view.displaySolution(args);
				
			}
		});
		commands.put("exit", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				model.exit(args);
				
			}
		});
		
	}

}
