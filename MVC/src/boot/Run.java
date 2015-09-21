package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import view.MyView;
import view.View;
import model.Model;
import model.MyModel;
import controller.Controller;
import controller.MyController;

public class Run {
public static void main(String[] args) {
	Model model=new MyModel();
	View view=new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
	Controller controller=new MyController(model,view);
	model.setController((MyController) controller);
	view.setController((MyController) controller);
	view.start();
}
}
