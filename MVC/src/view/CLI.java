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
	private MyView myView;


	public CLI(BufferedReader in,PrintWriter out,MyView myView) {
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
			while((str = in.readLine()).equals("exit"))
				{
				Boolean bool = true;
				//bulding set of all the strings of the keys in the hashmaps	
				Set<String> keys = myView.getCommands().keySet();
					for (String key : keys)
					{
						//comparing the key string to the string from the input stream
						if (key.equals(str))
						{
							myView.getCommands().get(str).doCommand(null);
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
				}
			myView.getCommands().get(str).doCommand(null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	

}
