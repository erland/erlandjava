package erland.game.crackout;
import erland.util.*;
import erland.game.*;
import java.awt.*;
import java.awt.event.*;

class CrackOutEditor
	implements ActionListener
{
	int offsetX;
	int offsetY;
	int squareSize;
	final int sizeX=20;
	final int sizeY=20;
	Block blocks[];
	Block selectBlocks[];
	Block preview;
	boolean bEnd;
	int blinkCounter;
	final int BLINK_SPEED=20;
	boolean bStarted;
	int level;
	int color;
	final int MAX_LEVEL=9;
	boolean bExit;
	int selectedBlock = 0;

	ImageHandlerInterface images;
	ParameterValueStorageInterface cookies;
	LevelFactory levelFactory;
	LevelFactory levelFactorySelect;
	ImageObject buttons[];
	Button realButtons[];
	String msg;
	BlockContainerData mainCont;
	BlockContainerData selectCont;
	BlockContainerData previewCont;
	Container container;
	
	public CrackOutEditor(Container container, ImageHandlerInterface images, ParameterValueStorageInterface cookies, int offsetX, int offsetY, int squareSize)
	{
		this.container = container;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.squareSize = squareSize;
		this.images = images;
		this.cookies = cookies;
		this.mainCont = new BlockContainerData(offsetX+1, offsetY+1, sizeX, sizeY, squareSize);
		this.selectCont = new BlockContainerData(offsetX+sizeX*squareSize+20, offsetY+150, 4, 10, squareSize);
		this.previewCont = new BlockContainerData(offsetX+squareSize*sizeX+20+60,offsetY+90,1,1,squareSize);

		levelFactory = new LevelFactory(images,cookies, mainCont);
		levelFactorySelect = new LevelFactory(images,cookies, selectCont);
		init();
		level=0;
		color=0;
		increaseLevel();
		bExit = false;
	}
	
	void init()
	{
		bEnd = false;
		blinkCounter = 0;
		bStarted=false;
		level=0;
		buttons = new ImageObject[4];
		int rightColumnX = sizeX*squareSize+20;
		int rightColumnY = 0;
		rightColumnX+=60;
		buttons[0] = new ImageObject(images.getImage(images.BUTTON_ARROWUP),offsetX,offsetY,rightColumnX,rightColumnY,14,14,true,Color.lightGray);
		rightColumnY+=17;
		buttons[1] = new ImageObject(images.getImage(images.BUTTON_ARROWDOWN),offsetX,offsetY,rightColumnX,rightColumnY,14,14,true,Color.lightGray);
		rightColumnY+=22;
		buttons[2] = new ImageObject(images.getImage(images.BUTTON_ARROWUP),offsetX,offsetY,rightColumnX,rightColumnY,14,14,true,Color.lightGray);
		rightColumnY+=17;
		buttons[3] = new ImageObject(images.getImage(images.BUTTON_ARROWDOWN),offsetX,offsetY,rightColumnX,rightColumnY,14,14,true,Color.lightGray);

		realButtons = new Button[7];
		rightColumnX = sizeX*squareSize+160;
		rightColumnY = offsetX+140;
		realButtons[0] = new Button("Clear This");
		realButtons[0].setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[4] = new ImageObject(images.getImage(images.BUTTON_CLEARTHIS),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;
		realButtons[1] = new Button("Delete");
		realButtons[1].setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[5] = new ImageObject(images.getImage(images.BUTTON_DELETE),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;
		realButtons[2] = new Button("New");
		realButtons[2].setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[6] = new ImageObject(images.getImage(images.BUTTON_NEW),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnX = sizeX*squareSize+240;
		rightColumnY = offsetX+140;
		realButtons[3] = new Button("Get Default");
		realButtons[3].setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[7] = new ImageObject(images.getImage(images.BUTTON_GETDEFAULT),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;
		realButtons[4] = new Button("Save This");
		realButtons[4].setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[8] = new ImageObject(images.getImage(images.BUTTON_SAVETHIS),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;
		realButtons[5] = new Button("Save All");
		realButtons[5].setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[9] = new ImageObject(images.getImage(images.BUTTON_SAVEALL),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;
		realButtons[6] = new Button("Exit");
		realButtons[6].setBounds(offsetX + rightColumnX,offsetY + rightColumnY,73,25);
		//buttons[10] = new ImageObject(images.getImage(images.BUTTON_EXIT),offsetX, offsetY,rightColumnX,rightColumnY,73,25);
		rightColumnY+=30;
		for(int i=0;i<realButtons.length;i++) {
			realButtons[i].addActionListener(this);
			container.add(realButtons[i]);
		}
				
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
	}
	void increaseLevel()
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
	void decreaseLevel()
	{
		levelFactory.storeLevel(level,blocks);
		level--;
		if(level<1) {
			level=1;
		}
		blocks = levelFactory.getLevel(level);
	}
	
	void increaseColor()
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
	void decreaseColor()
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

	Color getColor(int color)
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
	public void draw(Graphics g)
	{
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
	}
	
	boolean checkCollision(CollisionRect rc, int x, int y)
	{
		if(rc.left()<=x && rc.right()>=x) {			
			if(rc.top()<=y && rc.bottom()>=y) {
				return true;
			}
		}
		return false;
	}
	void handleMousePressed(int x, int y)
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
	void handleMouseReleased(int x, int y)
	{
		// Check buttons
		for(int i=0;i<buttons.length;i++) 
		{
			buttons[i].setRaised(true);
		}
	}
	void handleMouseClicked(int x, int y)
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
	void clearLevel()
	{
		blocks = null;
		blocks = new Block[0];
	}

	void deleteLevel()
	{
		levelFactory.deleteLevel(level);
		if(level>levelFactory.getLastLevel()) {
			level = levelFactory.getLastLevel();			
		}
		blocks = levelFactory.getLevel(level);
	}

	void newLevel()
	{
		levelFactory.newLevel(level);
		increaseLevel();
	}

	void getDefaultLevel()
	{
		blocks = levelFactory.getDefaultLevel(level);
	}

	void saveThisLevel()
	{
		levelFactory.storeLevel(level,blocks);
		levelFactory.saveLevel(level);
	}

	void saveAllLevels()
	{
		levelFactory.storeLevel(level,blocks);
		levelFactory.saveAll();
	}
	void exitEditor()
	{
		for(int i=0;i<realButtons.length;i++) {
			container.remove(realButtons[i]);
		}
		bExit=true;
	}
	boolean isExit()
	{
		return bExit;
	}
	public void actionPerformed(ActionEvent e)
	{
		for(int i=0;i<realButtons.length;i++) {
			if(e.getSource()==realButtons[i]) {
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
