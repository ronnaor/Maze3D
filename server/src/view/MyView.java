package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;

import presenter.Presenter;

public class MyView extends Observable implements View {

	private BufferedReader in;
	private PrintWriter out; 
	private Presenter presenter;
	private ArrayList<String> commands;
	
	/**
	 * Ctor that build the command line ui
	 * @param in BufferedReader the input stream
	 * @param out PrintWriter the output stream
	 */
	public MyView(BufferedReader in,PrintWriter out) {
		this.in = in;
		this.out = out;
		this.commands = new ArrayList<>();
		//setting the print for the menu
		this.commands.add("open server");
		this.commands.add("close server");
		
		
	} 

	/**
	 * get the view presenter
	 * @return presenter
	 */
	public Presenter getpresenter() {
		return presenter;
	}
	/**
	 * set the view presenter
	 * @return presenter
	 */
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {	
				String[] args; 
				String str;
				try {
					//printing the first user request menu
					printOutput("Please enter the command you want to perform:");
					printCommands(commands);
					
					//continuing until getting exit command
					while(!(str = in.readLine()).equals("close server"))
					{
						
						args = new String[1];
						args[0]= str;
						//send the command and values to the presenter
						setChanged();
						notifyObservers(args);					
						//printing the first user request menu
						printOutput("\nPlease enter the command you want to perform:");
						printCommands(commands);
					}
					//send command exit to presenter
					args = new String[1];
					args[0] = "close server";
					setChanged();
					notifyObservers(args);	
				} catch (IOException e) {
					displayError(e.getMessage());
				}
					}}
			).start();
		
	}

	
	@Override
	public void displayError(String err) {
		out.println(err);
		out.flush();	
		
	}

	@Override
	public void printOutput(String str) {
		out.println(str);
		out.flush();	
		
	}
	
	/**
	 * printing the commands that should be shown to the user
	 * @param commands - list that holds the names of the commands
	 */
		public void printCommands(ArrayList<String> commands)
		{
			printOutput("");
			for (String command : commands)
			{
			printOutput(command);
			}
			printOutput("");
		}

}
