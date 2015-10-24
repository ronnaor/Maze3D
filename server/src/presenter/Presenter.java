package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

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

		modelCommands.put("update", new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				view.printOutput(args[1]);
				return null;
			}		
		});
		modelCommands.put("error", new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				view.displayError(args[1]);
				return null;
			}		
		});
	}
	/**
	 * initiate the view commands hashmap
	 */
	private void initViewCommands() {
		viewCommands.put("open server", new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				model.openServer();
				return null;
			}		
		});
		viewCommands.put("close server", new Command<Void>() {
			
			@Override
			public Void doCommand(String[] args) {
				model.closeServer();
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
				view.displayError("no such command");
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
				view.displayError("no such command");
			}
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
