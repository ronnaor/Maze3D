package view;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class StartWindow extends BasicWindow {

	private String[] commands;
	private String[] args; 
	
	public StartWindow(String title, int width, int height, HashMap<String, Listener> listeners) {
		super(title, width, height, listeners);
		//setting the print for the menu
		commands =new String[] {"open properties","generate 3d maze","Play","load maze"};
		args = new String[6];
	}

	@Override
	void initWidgets() 
	{
		shell.setLayout(new GridLayout(1,false));
		
		//combo of the commands to choose from
		Label commandlabel = new Label(shell, SWT.NULL);
		commandlabel.setText("Choose Command:");
		Combo combo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		combo.setItems(commands);
		combo.pack();
	    
		//text fields to insert the data for the command
	     ArrayList<Text> argsList = new ArrayList<Text>();
	    
	     Label l1 =new Label(shell, SWT.NULL);
	     l1.setText("Insert maze name if necessary:");
	     Text first = new Text(shell, SWT.SINGLE | SWT.BORDER);
	     first.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
	     first.setText("");
	     argsList.add(first);
	     l1.pack();
	     first.pack();
	     
	     Label l2 =new Label(shell, SWT.NULL);
	     l2.setText("Insert x size if necessary::");
	     Text seconed = new Text(shell, SWT.SINGLE | SWT.BORDER);
	     seconed.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
	     seconed.setText("");
	     argsList.add(seconed);
	     l2.pack();
	     seconed.pack();
	     
	     Label l3 =new Label(shell, SWT.NULL);
	     l3.setText("Insert y size if necessary::");
	     Text third = new Text(shell, SWT.SINGLE | SWT.BORDER);
	     third.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
	     third.setText("");
	     argsList.add(third);
	     l3.pack();
	     third.pack();
	     
	     Label l4 =new Label(shell, SWT.NULL);
	     l4.setText("Insert z size if necessary::");
	     Text fourth = new Text(shell, SWT.SINGLE | SWT.BORDER);
	     fourth.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
	     fourth.setText("");
	     argsList.add(fourth);
	     l4.pack();
	     fourth.pack();
	     
	     Label l5 =new Label(shell, SWT.NULL);
	     l5.setText("Insert type of generate maze if necessary:");
	     Text fifth = new Text(shell, SWT.SINGLE | SWT.BORDER);
	     fifth.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
	     fifth.setText("");
	     argsList.add(fifth);
	     l5.pack();
	     fifth.pack();
		  
	     //Button ok to continue 
	     Button ok =new Button(shell, SWT.PUSH);
	     ok.setText("ok");
	     ok.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
	     ok.pack();
	     ok.addSelectionListener(new SelectionListener() {	
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					try {
			
						args[0]= combo.getText();
						int cnt = 1;
						for (Text t: argsList)
						{
							args[cnt] = t.getText();
							cnt++;
						}
						
					} catch (Exception e) {
						errMessageBox(e);
					}
					
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	     ok.addListener(SWT.Selection,listeners.get("ok"));
	     
	   //Button exit to end the program 
	     Button exit=new Button(shell,SWT.PUSH);
	     exit.setText("exit");
	     exit.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
	     exit.pack();
	   
	     exit.addListener(SWT.Selection,listeners.get("exit"));
			shell.pack();
}

	@Override
	public void close() 
	{
		shell.dispose();
	
	}
	public void errMessageBox(Exception e)
	{
		MessageBox messageBox = new MessageBox(shell,  SWT.ICON_ERROR| SWT.OK);
		messageBox.setMessage(e.getMessage());
		messageBox.setText("Error");
		messageBox.open();
	}
	
	public void errMessageBox(String error)
	{
		MessageBox messageBox = new MessageBox(shell,SWT.ICON_ERROR| SWT.OK);
		messageBox.setMessage(error);
		messageBox.setText("Error");
		messageBox.open();
	}
	
	public void updateMessageBox(String str)
	{
		display.syncExec(new Runnable(){
			@Override
			public void run() {
				MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION| SWT.OK);
				messageBox.setMessage(str);
				messageBox.setText("Update");
				messageBox.open();
			}
				});
	}
	
	public String[] getArgs() {
		return args;
	}
	
	public void setArgs(String[] args) {
		this.args = args;
	}
	
	
	}
