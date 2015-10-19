package presenter;

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
	
	/**
	 * Ctor for Presenter
	 * @param model the model the Presenter will work with
	 * @param view the view the Presenter will work with
	 */
	public Presenter(Model model,  View view) {
		this.model = model;
		this.view = view;
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
			switch(args[0])
			{
			case "open server": 
				
				break;
			case "close server": 
				
				break;
			default:
				view.displayError("no such command");
				break;
					
			}
			
		}
		if (obs == view)
		{
			switch(args[0])
			{
			case "": 
				
				break;
			default:
				view.displayError("no such command");
				break;
					
			}

		}
	}
	

}
