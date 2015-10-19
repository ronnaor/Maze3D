package boot;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import view.ViewGUI;
import view.CommonView;
import view.ViewCLI;

public class Run {

	public static void main(String[] args) {
		CommonView view;
		if (args != null && args.length>1 && args[1].equalsIgnoreCase("CLI"))
		{
			view= new ViewCLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		}
		else if (args != null && args.length>1 && args[1].equalsIgnoreCase("GUI"))
		{
			view = new ViewGUI();
		}
		else
		{
			try {
				XMLDecoder xml=new XMLDecoder(new FileInputStream("./resources/prop.xml"));
				Properties properties=(Properties)xml.readObject();
				switch(properties.getViewStyle()){
				case "CLI":
					view= new ViewCLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
					break;
				case "GUI":
					view = new ViewGUI();
					break;
				default:
					System.out.println("no view type in the file, will use GUI as view");
					view = new ViewGUI();
					break;
				}
				xml.close();
			} catch (FileNotFoundException e1) {
				System.out.println("no view type in the file, will use GUI as view");
				view = new ViewGUI();
			}
		}
		
		if (view!= null)
		{
			MyModel model = new MyModel();
			Presenter presenter = new Presenter(model, view);
			model.addObserver(presenter);
			view.addObserver(presenter);
			view.start();
		}
		
	}

}
