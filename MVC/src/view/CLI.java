package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

/**
 * class CLI that extends Thread
 *
 */
public class CLI extends Thread {
	private BufferedReader in;
	private PrintWriter out; 
	private View myView;

	/**
	 * Ctor
	 * @param in BufferedReader
	 * @param out PrintWriter
	 * @param myView View
	 */
	public CLI(BufferedReader in,PrintWriter out,View myView) {
		this.myView = myView;
		this.in = in;
		this.out = out;
	} 
	
	public void start()
	{		 
		new Thread(new Runnable() {
			@Override
			public void run() {	
				String str;
				try {
					//continuing until getting exit command
					printOutput("Please enter your choice");
					while(!(str = in.readLine()).equals("exit"))						
						{
						Boolean bool = true;
						//building set of all the strings of the keys in the hashmaps	
						Set<String> keys = myView.controllerCommands().keySet();
							for (String key : keys)
							{
								//comparing the key string to the string from the input stream
								if (key.equals(str))
								{
									ArrayList<String> list = new ArrayList<String>();	
									printOutput("Please enter the value for the command, when finished enter Stop");
									String answer = in.readLine();
									if(!answer.equalsIgnoreCase("Stop"))
										{
										list.add(answer);
										}
									while (!answer.equalsIgnoreCase("Stop"))
									{
										printOutput("Please enter the value for the command, when finished enter Stop");
										if (!(answer =in.readLine()).equalsIgnoreCase("stop")) 
										{
											list.add(answer);
										}
									}
									int cnt = 0;
									String[] strings = new String[list.size()];
									for (String s : list)
									{
										strings[cnt]=s;
										cnt++;
									}
									myView.controllerCommands().get(str).doCommand(strings);
									bool = false;
									//breaking the for loop because there could be only one command with this name
									break;
								}								
							}						
							if (bool)
							{
								out.println("Command not found");
								out.flush();
							}
							printOutput("Please enter your choice");
						}
					myView.controllerCommands().get(str).doCommand(null);	
				} catch (IOException e) {
					e.printStackTrace();
				}
					}}
			).start();
	}
	public BufferedReader getIn() {
		return in;
	}
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	public PrintWriter getOut() {
		return out;
	}
	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public void printOutput(String str) {
		out.println(str);
		out.flush();
	}
	

}
