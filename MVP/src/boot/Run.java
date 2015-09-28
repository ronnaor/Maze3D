package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import view.ViewCLI;

public class Run {

	public static void main(String[] args) {
		
		ViewCLI view = new ViewCLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		MyModel model = new MyModel();
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
		view.start();
	}

}
