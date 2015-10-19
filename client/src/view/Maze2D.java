package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenarators.Position;
/**
 * class that extends MazeDisplayer and will show the maze as 2d
 *
 */
public class Maze2D extends MazeDisplayer {

	int characterX;
	int characterY;
	int characterZ;

	Image startIm; 
	Image finishIm;
	Image characterIm;
	Image wallIm;
	/**
	 * ctor for maze 2d
	 * @param parent holds the controls
	 * @param style define the style
	 * @param start define the starting point
	 */
	public Maze2D(Composite parent, int style, Position start) {
		super(parent, style);
		this.characterX= start.getX();
		this.characterY = start.getY();
		this.characterZ = start.getZ();

		startIm = new Image(getDisplay(), "./resources/images/start.jpg"); //get image of start position
		finishIm = new Image(getDisplay(), "./resources/images/finish.jpg"); //get image of goal position
		characterIm = new Image(getDisplay(), "./resources/images/character.jpg"); //get image of character
		wallIm = new Image(getDisplay(), "./resources/images/wall.jpg"); //get image of the walls
		
		final Color white=new Color(null, 255, 255, 255);
		setBackground(white);
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				
				if(maze!=null)
				{


					e.gc.setForeground(new Color(null, 0, 0, 0));
					e.gc.setBackground(new Color(null, 35,	50	,120));

					int[][][] mazeData = maze.getMaze();

					int width = getSize().x;
					int depth = getSize().y;

					int w = width / mazeData[0][0].length;// the width of a cell
					int h = depth / mazeData[0].length; // the height of a cell

					// for calculating the size of the maze floor
					int lengthWidth = mazeData[0][0].length;// z axis length
					int lengthDepth = mazeData[0].length;// y axis length

					for (int i = 0; i < lengthDepth; i++) {
						for (int j = 0; j < lengthWidth; j++) {
					
							int pixelX = w * j;
							int pixelY = h * i;
							if (mazeData[characterX][i][j] != 0)
							{
								e.gc.drawImage(wallIm, 0, 0, wallIm.getBounds().width,wallIm.getBounds().height,pixelX,pixelY ,w ,h);	//draw walls
							}

						}
					}

					if(characterX==maze.getStartPosition().getX())
					{
						e.gc.drawImage(startIm, 0, 0, startIm.getBounds().width,startIm.getBounds().height,maze.getStartPosition().getZ()*w,maze.getStartPosition().getY()*h ,w ,h);	//draw start position					
						e.gc.setBackground(new Color(null,0,0,0));
					}
					
					if(characterX==maze.getGoalPosition().getX())
					{
						e.gc.drawImage(finishIm, 0, 0, finishIm.getBounds().width,finishIm.getBounds().height,maze.getGoalPosition().getZ()*w,maze.getGoalPosition().getY()*h ,w ,h);	//draw goal position						
						e.gc.setBackground(new Color(null,0,0,0));
					}
					
					
					e.gc.drawImage(characterIm, 0, 0, characterIm.getBounds().width,characterIm.getBounds().height,characterZ*w,characterY*h ,w ,h); //draw character in his current position
					
				}
				  
				
			}
		});
	}
	/**
	 * move the character to his new position
	 * @param x define x position
	 * @param y define y position
	 * @param z define z position
	 */
	private void moveCharacter(int x,int y, int z){
		
			getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					redraw();
				}
			});
		}
	
	

	@Override
	public void moveUp() {
		
		setCharacterPosition(characterX, characterY-1, characterZ);
	}

	@Override
	public void moveDown() {
		setCharacterPosition(characterX, characterY+1, characterZ);
	}

	@Override
	public void moveLeft() {
		setCharacterPosition(characterX, characterY, characterZ-1);
	}

	@Override
	public void moveRight() {
		setCharacterPosition(characterX, characterY, characterZ+1);
	}
	
	@Override
	public void movePageUp() {
		setCharacterPosition(characterX+1, characterY, characterZ);
		
	}
	@Override
	public void movePageDown() {
		setCharacterPosition(characterX-1, characterY, characterZ);
		
	}

	@Override
	public void moveStart() {
		setCharacterPosition(maze.getStartPosition().getX(), maze.getStartPosition().getY(), maze.getStartPosition().getZ());
		
	}
	
	@Override
	public void move(Position p) {
		
		setCharacterPosition(p.getX(),p.getY(),p.getZ());
		
	}

	
	@Override
	public void setCharacterPosition(int x, int y, int z) {
		characterX=x;
		characterY=y;
		characterZ=z;
		moveCharacter(x,y,z);
	}

	
	

	
	
}
