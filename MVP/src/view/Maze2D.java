package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenarators.Position;

public class Maze2D extends MazeDisplayer {

	int characterX;
	int characterY;
	int characterZ;

	
	public Maze2D(Composite parent, int style, Position start) {
		super(parent, style);
		this.characterX= start.getX();
		this.characterY = start.getY();
		this.characterZ = start.getZ();

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
								e.gc.fillRectangle(pixelX, pixelY, w, h);
							}

						}
					}

					e.gc.setBackground(new Color(null,130,220,0));
					e.gc.fillOval(characterZ*w, characterY*h, w, h);							
					e.gc.setBackground(new Color(null,130,220,0));
					e.gc.setBackground(new Color(null,0,0,0));
				}
				  
				
			}
		});
	}
	
	private void moveCharacter(int x,int y, int z){
		
			getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					redraw();
				}
			});
		}
	
	
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveUp()
	 */
	@Override
	public void moveUp() {
		
		setCharacterPosition(characterX, characterY-1, characterZ);
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveDown()
	 */
	@Override
	public void moveDown() {
		setCharacterPosition(characterX, characterY+1, characterZ);
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveLeft()
	 */
	@Override
	public void moveLeft() {
		setCharacterPosition(characterX, characterY, characterZ-1);
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveRight()
	 */
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
