package erland.game.crackout;
import erland.game.*;
import erland.game.component.EButton;

import java.awt.*;
import java.awt.event.*;

/**
 * Main object for the level editor
 */
class CrackOutEditor
	implements GamePanelInterface, ActionListener
{
	/** Horisontal drawing offset */
	protected int offsetX;
	/** Vertical drawing offset */
	protected int offsetY;
	/** Size of the squares the blocks consists of (Number of pixels) */
	protected int squareSize;
	/** Width of the main level editor area (Number of squares) */
	protected final int sizeX=20;
	/** Height of the main level editor area (Number of squares) */
	protected final int sizeY=20;
	/** Array with all the blocks in the main level editor area */
	protected Block blocks[];
	/** Array with all the blocks in the block select area */
	protected Block selectBlocks[];
	/** Block that is previewed/selected */
	protected Block preview;
	/** The level currently edited */
	protected int level;
	/** The color of the currently selected block */
	protected int color;
	/** Indicates if the Exit button has been clicked */
	protected boolean bExit;
	/** The position in {@link #selectBlocks} array of the currently selected block */
	protected int selectedBlock = 0;
	/** Level factory for the main level editor area */
	protected LevelFactory levelFactory;
	/** Level factory for the select block area */
	protected LevelFactory levelFactorySelect;
	/** Up/Down arrow buttons */
	protected ImageObject buttons[];
	/** 
	 * Array with all the buttons
	 * @see #buttons
	 */
	protected EButton realButtons[];
	/** The description of the currently selected block */
	protected String msg;
	/** Block container for the main level area */
	protected BlockContainerData mainCont;
	/** Block container for the block select area */
	protected BlockContainerData selectCont;
	/** Block container for the preview area */
	protected BlockContainerData previewCont;
    protected GameEnvironmentInterface environment;
    protected MouseListener mouseListener;
	/**
	 * Creates a new level editor
	 * @param offsetX Horisontal drawing offset
	 * @param offsetY Vertical drawing offset
	 * @param squareSize The size of the squares that all blocks are build of
	 */
	public CrackOutEditor(int offsetX, int offsetY, int squareSize)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.squareSize = squareSize;
	}

    public void init(GameEnvironmentInterface environ) {
        this.environment = environ;
		this.mainCont = new BlockContainerData(offsetX+1, offsetY+1, sizeX, sizeY, squareSize);
		this.selectCont = new BlockContainerData(offsetX+sizeX*squareSize+20, offsetY+150, 4, 10, squareSize);
		this.previewCont = new BlockContainerData(offsetX+squareSize*sizeX+20+60,offsetY+90,1,1,squareSize);

		levelFactory = new LevelFactory(environment, mainCont);
		levelFactorySelect = new LevelFactory(environment, selectCont);
		init();
		level=0;
		color=0;
		increaseLevel();
		bExit = false;

        mouseListener = new MouseAdapter()  {
            public void mousePressed(MouseEvent e)
            {
                handleMousePressed(environment.getScreenHandler().getScreenX(e.getX()),
                        environment.getScreenHandler().getScreenY(e.getY()));
            }
            public void mouseReleased(MouseEvent e)
            {
                handleMouseReleased(environment.getScreenHandler().getScreenX(e.getX()),
                        environment.getScreenHandler().getScreenY(e.getY()));
            }
            public void mouseClicked(MouseEvent e)
            {
                handleMouseClicked(environment.getScreenHandler().getScreenX(e.getX()),
                        environment.getScreenHandler().getScreenY(e.getY()));
            }
        };
        environment.getScreenHandler().getContainer().requestFocus();
        environment.getScreenHandler().getContainer().addMouseListener(mouseListener);
        environment.getScreenHandler().getContainer().setBackground(Color.black);
    }

    public void update() {
        // Do nothing
    }

    /**
	 * Initialize object
	 */
	protected void init()
	{
		level=0;
		buttons = new ImageObject[4];
		int rightColumnX = sizeX*squareSize+20;
		int rightColumnY = 0;
		rightColumnX+=60;
		buttons[0] = new ImageObject(environment.getImageHandler().getImage("button_arrowup.gif"),offsetX,offsetY,rightColumnX,rightColumnY,14,14,true,Color.lightGray);
		rightColumnY+=17;
		buttons[1] = new ImageObject(environment.getImageHandler().getImage("button_arrowdown.gif"),offsetX,offsetY,rightColumnX,rightColumnY,14,14,true,Color.lightGray);
		rightColumnY+=22;
		buttons[2] = new ImageObject(environment.getImageHandler().getImage("button_arrowup.gif"),offsetX,offsetY,rightColumnX,rightColumnY,14,14,true,Color.lightGray);
		rightColumnY+=17;
		buttons[3] = new ImageObject(environment.getImageHandler().getImage("button_arrowdown.gif"),offsetX,offsetY,rightColumnX,rightColumnY,14,14,true,Color.lightGray);

		realButtons = new EButton[7];
		rightColumnX = sizeX*squareSize+160;
		rightColumnY = offsetX+140;
		realButtons[0] = EButton.create("Clear This");
		realButtons[0].getComponent().setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[4] = new ImageObject(images.getImage(images.BUTTON_CLEARTHIS),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;
		realButtons[1] = EButton.create("Delete");
		realButtons[1].getComponent().setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[5] = new ImageObject(images.getImage(images.BUTTON_DELETE),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;
		realButtons[2] = EButton.create("New");
		realButtons[2].getComponent().setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[6] = new ImageObject(images.getImage(images.BUTTON_NEW),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnX = sizeX*squareSize+240;
		rightColumnY = offsetX+140;
		realButtons[3] = EButton.create("Get Default");
		realButtons[3].getComponent().setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[7] = new ImageObject(images.getImage(images.BUTTON_GETDEFAULT),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;
		realButtons[4] = EButton.create("Save This");
		realButtons[4].getComponent().setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[8] = new ImageObject(images.getImage(images.BUTTON_SAVETHIS),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;
		realButtons[5] = EButton.create("Save All");
		realButtons[5].getComponent().setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[9] = new ImageObject(images.getImage(images.BUTTON_SAVEALL),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;
		realButtons[6] = EButton.create("Exit");
		realButtons[6].getComponent().setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[10] = new ImageObject(images.getImage(images.BUTTON_EXIT),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;

		selectBlocks = levelFactorySelect.getFullBlockList();
		for(int i=0;i<selectBlocks.length;i++) {
			selectBlocks[i].setColor(getColor(color));
		}
		try {
			preview = (Block)(selectBlocks[0].clone());	
			preview.setPosition(0,0);
			preview.setContainer(previewCont);
			preview.setColor(getColor(color));
			selectedBlock=0;
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
        for(int i=0;i<realButtons.length;i++) {
            realButtons[i].addActionListener(this);
            environment.getScreenHandler().add(realButtons[i].getComponent());
        }
	}

	/**
	 * Step to the next level
	 */
	protected void increaseLevel()
	{
		if(level>0) {
			levelFactory.storeLevel(level,blocks);
		}
		level++;
		if(level>levelFactory.getLastLevel()) {
			level = levelFactory.getLastLevel();
		}
		blocks = levelFactory.getLevel(level);
	}
	/**
	 * Step to the previous level
	 */
	protected void decreaseLevel()
	{
		levelFactory.storeLevel(level,blocks);
		level--;
		if(level<1) {
			level=1;
		}
		blocks = levelFactory.getLevel(level);
	}
	
	/**
	 * Change the selected block color to the next color
	 */
	protected void increaseColor()
	{
		color++;
		if(color>4) {
			color=0;
		}
		if(preview!=null) {
			preview.setColor(getColor(color));
		}
		for(int i=0;i<selectBlocks.length;i++) {
			selectBlocks[i].setColor(getColor(color));
		}
	}
	/**
	 * Change the selected block color to the previous color
	 */
	protected void decreaseColor()
	{
		color--;
		if(color<0) {
			color=4;
		}
		if(preview!=null) {
			preview.setColor(getColor(color));
		}
		for(int i=0;i<selectBlocks.length;i++) {
			selectBlocks[i].setColor(getColor(color));
		}
	}
	
	/**
	 * Get the color for the specified index
	 * @param color The index of the color to get
	 * @return The Color for the specified index, Color.gray if not found
	 */
	protected Color getColor(int color)
	{
		switch(color) {
			case 0:
				return Color.red;
			case 1:
				return Color.white;
			case 2:
				return Color.blue;
			case 3:
				return Color.yellow;
			case 4:
				return Color.green;
			default:
				return Color.gray;
		}
	}
	/**
	 * Draw all the level editor grapics
	 */
	public void draw()
	{
        Graphics g = environment.getScreenHandler().getCurrentGraphics();
        g.clearRect(0,0,environment.getScreenHandler().getWidth(),environment.getScreenHandler().getHeight());
		g.setColor(Color.blue);
		g.fillRect(offsetX, offsetY, 1,sizeY*squareSize);
		g.fillRect(offsetX, offsetY, sizeX*squareSize,1);
		g.fillRect(offsetX+sizeX*squareSize,offsetY,1,sizeY*squareSize);
		for(int x=0;x<sizeX;x+=2) {
			g.fillRect(offsetX+x*squareSize,offsetY, 1,sizeX*squareSize);
		}
		for(int y=0;y<sizeY;y++) {
			g.fillRect(offsetX,offsetY+y*squareSize, sizeY*squareSize,1);
		}
		for(int i=0;i<blocks.length;i++) {
			blocks[i].draw(g);
		}
		for(int i=0;i<selectBlocks.length;i++) {
			selectBlocks[i].draw(g);
		}
		int rightColumnX = offsetX+sizeX*squareSize+20;
		int rightColumnY = offsetY+20;
		g.setColor(Color.white);
		g.drawString("Level: "+String.valueOf(level),rightColumnX, rightColumnY);
		rightColumnY+=40;
		g.setColor(Color.white);
		g.drawString("Color: ",rightColumnX, rightColumnY);
		g.setColor(getColor(color));
		g.fillRect(rightColumnX+40,rightColumnY-10,10,10);
		rightColumnY+=40;
		g.setColor(Color.white);
		g.drawString("Preview: ", rightColumnX, rightColumnY);
		if(preview!=null) {
			preview.draw(g);
			g.setColor(Color.white);
			g.drawString(preview.getDescription(),rightColumnX,rightColumnY+20);
			g.drawRect(selectCont.getOffsetX()+selectBlocks[selectedBlock].left(),
						selectCont.getOffsetY()+selectBlocks[selectedBlock].top(),
						selectBlocks[selectedBlock].right()-selectBlocks[selectedBlock].left()-1,
						selectBlocks[selectedBlock].bottom()-selectBlocks[selectedBlock].top()-1);
		}
		rightColumnY+=40;
		g.setColor(Color.white);
		g.drawString("Blocks:", rightColumnX,rightColumnY);
	
		
		// Draw buttons
		for(int i=0;i<buttons.length;i++) {
			buttons[i].draw(g);
		}
		g.setColor(Color.white);
		if(msg!=null) {
			g.drawString(msg,rightColumnX,rightColumnY);
		}
		g.setColor(Color.red);
		g.drawString("by Erland Isaksson",rightColumnX,offsetY+sizeY*squareSize);
        environment.getScreenHandler().paintComponents(g);
	}
	
	/**
	 * Checks if the specified coordinate is inside the specified rectangle
	 * @param rc Rectangle to check if the coordinate is within
	 * @param x x coordinate to check
	 * @param y y coordinate to check
	 * @return true/false (Inside/Not inside)
	 */
	protected boolean checkCollision(CollisionRect rc, int x, int y)
	{
		if(rc.left()<=x && rc.right()>=x) {			
			if(rc.top()<=y && rc.bottom()>=y) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Handles mousePressed events. This lower the clicked button and raises all the other
	 * @param x X position of mouse pointer
	 * @param y Y position of mouse pointer
	 */
	public void handleMousePressed(int x, int y)
	{
		// Check buttons
		for(int i=0;i<buttons.length;i++) 
		{
			buttons[i].setRaised(true);
			if(checkCollision(buttons[i],x-offsetX,y-offsetY)) {
				buttons[i].setRaised(false);
			}
		}
	}
	/**
	 * Handles mouseReleased events. This raises all the buttons
	 * @param x X position of mouse pointer
	 * @param y Y position of mouse pointer
	 */
	public void handleMouseReleased(int x, int y)
	{
		// Check buttons
		for(int i=0;i<buttons.length;i++) 
		{
			buttons[i].setRaised(true);
		}
	}
	/**
	 * Handles mouseClicked events. 
	 * This peforms the correct action for the button clicked if a up/down arrow button was clicked
	 * This selects a new block if the block select area was clicked
	 * This paste the currently selected block if the main level editor area was clicked
	 * @param x X position of mouse pointer
	 * @param y Y position of mouse pointer
	 */
	public void handleMouseClicked(int x, int y)
	{
		// Check buttons
		for(int i=0;i<buttons.length;i++) 
		{
			if(checkCollision(buttons[i],x-offsetX,y-offsetY)) {
				switch(i) {
					case 0:
						increaseLevel();
						break;
					case 1:
						decreaseLevel();
						break;
					case 2:
						increaseColor();
						break;
					case 3:
						decreaseColor();
						break;
					default:
						break;
				}
			}
		}
		
		// Check block selection
		for(int i=0;i<selectBlocks.length;i++) {
			if(checkCollision(selectBlocks[i],x-selectBlocks[i].getContainer().getOffsetX(),y-selectBlocks[i].getContainer().getOffsetY())) {
				try {
					selectedBlock=i;
					preview = (Block)(selectBlocks[i].clone());	
					preview.setPosition(0,0);
					preview.setContainer(previewCont);
					preview.setColor(getColor(color));
				}catch(CloneNotSupportedException e) {
					preview = null;
				}
			}
		}
		
		if(preview!=null) {
			// Check paste area
			if(x>=(offsetX+1) && x<=(offsetX+1+squareSize*sizeX)) {
				if(y>=(offsetY+1) && y<=(offsetY+1+squareSize*sizeY)) {
					boolean exists=false;
					int existsIndex=0;
					for(int i=0;i<blocks.length;i++) {
						if(checkCollision(blocks[i],x-blocks[i].getContainer().getOffsetX(),y-blocks[i].getContainer().getOffsetY())) {
							exists=true;
							existsIndex=i;
						}
					}
					if(!exists) {
						Block tmp[] = new Block[blocks.length+1];
						for(int i=0;i<blocks.length;i++) {
							tmp[i] = blocks[i];
						}
						try {
							tmp[blocks.length] = (Block)(preview.clone());
							tmp[blocks.length].setContainer(mainCont);
							int posX = (x-(offsetX+1))/squareSize;
							posX/=2;
							posX*=2;
							tmp[blocks.length].setPosition(posX,(y-(offsetY+1))/squareSize);
							blocks = tmp;
						}catch(CloneNotSupportedException e) {
							// Do nothing;
						}
					}else {
						Block tmp[] = new Block[blocks.length-1];
						for(int i=0, j=0;i<blocks.length;i++) {
							if(i!=existsIndex) {
								tmp[j++]=blocks[i];
							}
						}
						blocks = tmp;
					}
				}
			}
		}
	}
	/**
	 * Remove all blocks from the main level editor area
	 */
	protected void clearLevel()
	{
		blocks = null;
		blocks = new Block[0];
	}

	/**
	 * Deletes the currently selected level
	 */
	protected void deleteLevel()
	{
		levelFactory.deleteLevel(level);
		if(level>levelFactory.getLastLevel()) {
			level = levelFactory.getLastLevel();			
		}
		blocks = levelFactory.getLevel(level);
	}
	/**
	 * Inserts a new level after the currently active level
	 */
	protected void newLevel()
	{
		levelFactory.newLevel(level);
		increaseLevel();
	}
	
	/**
	 * Get the hardcoded level data for the currently selected level
	 */
	protected void getDefaultLevel()
	{
		blocks = levelFactory.getDefaultLevel(level);
	}
	
	/**
	 * Save the currently active level to disk
	 */
	protected void saveThisLevel()
	{
		levelFactory.storeLevel(level,blocks);
		levelFactory.saveLevel(level);
	}

	/**
	 * Save all levels to disk
	 */
	protected void saveAllLevels()
	{
		levelFactory.storeLevel(level,blocks);
		levelFactory.saveAll();
	}
	
	/**
	 * Exit the level editor
	 */
	protected void exitEditor()
	{
		bExit=true;
	}

    public void exit() {
        for(int i=0;i<realButtons.length;i++) {
            environment.getScreenHandler().remove(realButtons[i].getComponent());
        }
        environment.getScreenHandler().getContainer().removeMouseListener(mouseListener);
    }
	/**
	 * Checks if the Exit button has been pressed
	 * @return true/false (Exit pressed/Exit not pressed)
	 */
	public boolean isExit()
	{
		return bExit;
	}

    public void setCheatmode(boolean enable) {
    }

    /**
	 * Performs the correct action if any of the normal buttons was pressed
	 * @param e ActionEvent with information about which button that was pressed
	 */
	public void actionPerformed(ActionEvent e)
	{
		for(int i=0;i<realButtons.length;i++) {
			if(e.getSource()==realButtons[i].getComponent()) {
				switch(i) {
					case 0:
						clearLevel();
						break;
					case 1:
						deleteLevel();
						break;
					case 2:
						newLevel();
						break;
					case 3:
						getDefaultLevel();
						break;
					case 4:
						saveThisLevel();
						break;
					case 5:
						saveAllLevels();
						break;
					case 6:
						exitEditor();
						break;
					default:
						break;
				}
				break;
			}
		}
	}
}
