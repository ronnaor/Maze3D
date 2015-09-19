package controller;

import java.util.Observable;

import model.Model;
import view.View;

/**
 * class MyController that implements from Controller
 *
 */
public class MyController implements Controller {
	private  Model model;
	private View view;
	/**
	 * Ctor of MyController
	 * @param model getting model object
	 * @param view getting view object
	 */
	public MyController(Model model, View view) 
	{
		this.model= model;
		this.view = view;
	}
	/**
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj)
	{
		
	}
}
