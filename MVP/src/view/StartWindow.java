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

public class StartWindow extends MazeWindow {

	private String[] commands;
	private String[] args; 
	
	public StartWindow(String title, int width, int height, HashMap<String, Listener> listeners) {
		super(title, width, height, listeners);
		//setting the print for the menu
		commands =new String[] {"Play","open properties","generate 3d maze","load maze","exit"};
	}

	@Override
	void initWidgets() 
	{
		shell.setLayout(new GridLayout(2,false));
		
		
		Label commandlabel = new Label(shell, SWT.NULL);
		commandlabel.setText("Choose Command:");
		Combo combo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		combo.setItems(commands);
		combo.pack();
	    /*Group info = new Group(shell, SWT.NULL);
	     info.setText("Info");
	     
	     GridLayout gridLayout2 = new GridLayout();
	     gridLayout2.numColumns = 2;
	     info.setLayout(gridLayout);
	     GridData gridData2 = new GridData(SWT.FILL, SWT.UP, false, false, 1, 1);
	     gridData2.horizontalSpan = 5;
	     info.setLayoutData(gridData);*/
	     
	     ArrayList<Text> labelList = new ArrayList<Text>();
	    
	    
	     Label l1 =new Label(shell, SWT.NULL);
	     l1.setText("Insert first argument:");
	     Text first = new Text(shell, SWT.SINGLE | SWT.BORDER);
	     first.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
	     labelList.add(first);
	    l1.pack();
	     
	     Label l2 =new Label(shell, SWT.NULL);
	     l2.setText("Insert seconed argument:");
	     Text seconed = new Text(shell, SWT.SINGLE | SWT.BORDER);
	     seconed.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
	     labelList.add(seconed);
	     l2.pack();
		  
	     Label l3 =new Label(shell, SWT.NULL);
	     l3.setText("Insert third argument:");
	     Text third = new Text(shell, SWT.SINGLE | SWT.BORDER);
	     third.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
	     labelList.add(third);
	     l3.pack();
		  
	     Label l4 =new Label(shell, SWT.NULL);
	     l4.setText("Insert fourth argument:");
	     Text fourth = new Text(shell, SWT.SINGLE | SWT.BORDER);
	     fourth.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
	     labelList.add(fourth);
	     l4.pack();
		  
	     Label l5 =new Label(shell, SWT.NULL);
	     l5.setText("Insert fifth argument:");
	     Text fifth = new Text(shell, SWT.SINGLE | SWT.BORDER);
	     fifth.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
	     labelList.add(fifth);
	     l5.pack();
		  
	     Button ok =new Button(shell, SWT.PUSH);
	     ok.setText("ok");
	     ok.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
	     ok.pack();
	     ok.addSelectionListener(new SelectionListener() {	
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					args[0]= combo.getText();
					int cnt = 1;
					for (Text t: labelList)
					{
						args[cnt] = t.getText(); 
					}
					close();
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
}


public void showMessageBox(Exception e)
{
	MessageBox messageBox = new MessageBox(shell,  SWT.ICON_ERROR| SWT.OK);
	messageBox.setMessage(e.getMessage());
	messageBox.setText("Error");
	messageBox.open();
}

public String[] getArgs() {
	return args;
}

public void setArgs(String[] args) {
	this.args = args;
}


}
