/*
 * @(#)CrackOut.java 1.0 01/07/16
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_2\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */
package erland.game.crackout;
import erland.util.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.math.*;

/**
 * Main Application/Applet object
 */
public class CrackOut extends Applet 
	implements Runnable
{
	/** Indicates if it runs as an application or applet, true indicates applet */
	protected static boolean inApplet =true;
	/** The main Frame window if it runs as an application */
	protected static Frame myFrame;
	/** Main thread that updates the objects position and redraws the screen */
	protected Thread animator;
	/** Image object needed for the double buffering mechanism */
	protected Image imag;
	/** Graphics object needed for the double buffering mechanism */
	protected Graphics offScreen;
	/** Image handler object */
	protected ImageHandlerInterface images;
	/** Parameter storage object */
	protected ParameterValueStorageInterface cookies;
	/** Main game object */
	protected CrackOutMain main;
	/** Main editor objec */
	protected CrackOutEditor editor;
	/** Editor and Game buttons */
	protected Button buttons[];
	/** Indicates if cheatmode is active or not */
	protected boolean bCheatMode;
	/** Counter that is increased every time the correct letter in the cheat word is entered on the keyboard */
	protected int cheatCounter;
	/** Object that handles when one of the buttons are pressed */
	protected ActionHandler actionHandler;

	/**
	 * Takes care of all keyboard events
	 */
	class Keyboard extends KeyAdapter {
		/**
		 * Called when a key is pressed down
		 * @param e KeyEvent event
		 */
		public void keyPressed(KeyEvent e) {
			if(main!=null) {
				if(e.getKeyCode()==e.VK_LEFT) {
					main.moveLeft();
				}else if(e.getKeyCode()==e.VK_RIGHT) {
					main.moveRight();
				}else if(e.getKeyCode()==e.VK_SPACE) {
					main.hitSpace();
				}else if(bCheatMode && e.getKeyCode()==e.VK_F1) {
					main.increaseStartLevel();
				}else if(bCheatMode && e.getKeyCode()==e.VK_F2) {
					main.SafetyWall();
				}else if(bCheatMode && e.getKeyCode()==e.VK_F3) {
					main.IncreaseBallSpeed();
				}else if(bCheatMode && e.getKeyCode()==e.VK_F4) {
					main.DecreaseBallSpeed();
				}else if(bCheatMode && e.getKeyCode()==e.VK_F5) {
					main.IncreaseBatSpeed();
				}else if(bCheatMode && e.getKeyCode()==e.VK_F6) {
					main.DecreaseBatSpeed();
				}else if(bCheatMode && e.getKeyCode()==e.VK_F7) {
					main.NewBall();
				}else if(bCheatMode && e.getKeyCode()==e.VK_F8) {
					main.DoubleBat();
				}else if(bCheatMode && e.getKeyCode()==e.VK_F9) {
					main.LargeBat();
				}else if(bCheatMode && e.getKeyCode()==e.VK_F10) {
					main.SmallBat();
				}else if(cheatCounter==0 && e.getKeyCode()==e.VK_E) {
					cheatCounter++;
				}else if(cheatCounter==1 && e.getKeyCode()==e.VK_R) {
					cheatCounter++;
				}else if(cheatCounter==2 && e.getKeyCode()==e.VK_L) {
					cheatCounter++;
				}else if(cheatCounter==3 && e.getKeyCode()==e.VK_A) {
					cheatCounter++;
				}else if(cheatCounter==4 && e.getKeyCode()==e.VK_N) {
					cheatCounter++;
				}else if(cheatCounter==5 && e.getKeyCode()==e.VK_D) {
					cheatCounter++;
					bCheatMode=true;
					main.setCheatMode(true);
				}
			}
		}
		/**
		 * Called when a key is released
		 * @param e KeyEvent event
		 */
		public void keyReleased(KeyEvent e) {
			if(main!=null) {
				if(e.getKeyCode()==e.VK_LEFT) {
					main.stopMoveLeft();	
				}else if(e.getKeyCode()==e.VK_RIGHT) {
					main.stopMoveRight();	
				}
			}
		}
	}
	
	/**
	 * Handles all the mouse events
	 */
	class Mouse extends MouseAdapter {
		/**
		 * Called when the mouse button is pressed down
		 * @param e MouseEvent event
		 */
		public void mousePressed(MouseEvent e)
		{
			if(editor!=null) {
				editor.handleMousePressed(e.getX(),e.getY());
			}else if(main!=null) {
				main.handleMousePressed(e.getX(),e.getY());
			}
		}
		/**
		 * Called when the mouse button is released
		 * @param e MouseEvent event
		 */
		public void mouseReleased(MouseEvent e)
		{
			if(editor!=null) {
				editor.handleMouseReleased(e.getX(),e.getY());
			}else if(main!=null) {
				main.handleMouseReleased(e.getX(),e.getY());
			}
		}
		/**
		 * Called when the mouse button is clicked (pressed + released)
		 * @param e MouseEvent event
		 */
		public void mouseClicked(MouseEvent e)
		{
			if(editor!=null) {
				editor.handleMouseClicked(e.getX(),e.getY());
			}else if(main!=null) {
				main.handleMouseClicked(e.getX(),e.getY());
			}
		}
	}
	
	/**
	 * Handles all clicks on the buttons
	 */
	class ActionHandler implements ActionListener {
		/** Referens to the container of the buttons */
		Container c;
		/** 
		 * Creates a new object 
		 * @param c The container the buttons resides in
		 */
		public ActionHandler(Container c)
		{
			this.c = c;
		}
		/**
		 * Performs the correct action when a button has been pressed
		 * @param e ActionEvent with information about which button that has been pressed
		 */
		public void actionPerformed(ActionEvent e) {
			if(main==null && editor==null) {
				if(e.getSource()==buttons[0]) {
					buttons[0].setVisible(false);
					buttons[1].setVisible(false);
					main = new CrackOutMain(c,images,cookies,10,10,15);
					requestFocus();
					editor = null;
				}else if(e.getSource()==buttons[1]) {
					buttons[0].setVisible(false);
					buttons[1].setVisible(false);
					editor = new CrackOutEditor(c,images, cookies,10,10,15);
					requestFocus();
					main = null;
				}
			}
		}
	}

	/**
	 * Initialize the application/applet 
	 */
	public void init() {
		this.setLayout(null);
		bCheatMode=false;
		cheatCounter=0;
		addKeyListener(new Keyboard());
		addMouseListener(new Mouse());
		this.setBackground(Color.black);
		String mayScript = null;
		if(inApplet) {
			this.images = new ImageHandlerForApplet(this,"images/crackout");
			mayScript = this.getParameter("MAYSCRIPT");
		}else {
			this.images = new ImageHandlerForApplication(myFrame,"images/crackout");
		}
		if(mayScript!=null) {
			this.cookies = new CookieHandler(this);
		}else {
			if(!inApplet) {
				this.cookies = new ParameterStorageGroup(new FileStorage("data.xml"),"crackout","level");
			}
		}
		//buttons = new ImageObject[2];
		//buttons[0] = new ImageObject(images.getImage(images.BUTTON_GAME),0,0,100,100,73,25);
		//buttons[1] = new ImageObject(images.getImage(images.BUTTON_EDITOR),0,0,100,140,73,25);
		buttons = new Button[2];
		buttons[0] = new Button("Game");
		buttons[1] = new Button("Editor");
		buttons[0].setBounds(100,100,73,25);
		buttons[1].setBounds(100,140,73,25);
		this.add(buttons[0]);
		this.add(buttons[1]);
		actionHandler= new ActionHandler(this);
		buttons[0].addActionListener(actionHandler);
		buttons[1].addActionListener(actionHandler);
		if(cookies==null) {
			buttons[0].setVisible(false);
			buttons[1].setVisible(false);
			main = new CrackOutMain(this,images,cookies,10,10,15);
			editor = null;
		}else {
			main = null;
			editor = null;
		}
	}

	/**
	 * Draw the screen
	 * @param g Graphics object to draw on
	 */
	public void paint(Graphics g) {
		int width = getSize().width;
		int height = getSize().height;
		if(imag == null) {
			imag = createImage(width,height);
			offScreen = imag.getGraphics();
			offScreen.setColor(getBackground());
		}
		offScreen.clearRect(0,0,width,height);
		offScreen.setColor(Color.blue);
		if(main!=null) {
			main.draw(offScreen);
		}else if(editor != null) {
			editor.draw(offScreen);
		}else {
			/*
			for(int i=0;i<buttons.length;i++) {
				buttons[i].draw(offScreen);
			}
			*/			
		}
		g.drawImage(imag,0,0,null);
	}

	/**
	 * start the application/applet
	 */
	public void start()
	{
		this.requestFocus();
		if(animator==null) {
			animator= new Thread(this);
			animator.start();
		}
	}
	
	/**
	 * stop the application/applet
	 */
	public void stop()
	{
		if((animator != null) && (animator.isAlive())) {
			animator = null;
		}
	}
	
	/**
	 * Main loop for the main thread 
	 * Makes sure all objects are updated and draw at the correct time
	 */
	public void run()
	{
		long time = System.currentTimeMillis();
		final int frameDelay=20;
		int setSleepTime=0;
		while(animator != null) {
			animator.setPriority(Thread.MAX_PRIORITY);
			try {
				time+=frameDelay;
				animator.sleep(Math.max(0,time-System.currentTimeMillis()));
				if(main!=null) {
					main.update();
					if(main.isExit()) {
						buttons[0].setVisible(true);
						buttons[1].setVisible(true);
						this.requestFocus();
						cheatCounter=0;
						bCheatMode=false;
						main=null;
					}
				}else if(editor!=null) {
					if(editor.isExit()) {
						buttons[0].setVisible(true);
						buttons[1].setVisible(true);
						this.requestFocus();
						cheatCounter=0;
						bCheatMode=false;
						editor=null;
					}
				}
				repaint();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Redraws the screen
	 * @param g Graphics object to draw on
	 */
	public void update(Graphics g)
	{
		paint(g);
	}
	
	/**
	 * Main method if the game is executed as an application
	 * @param args Command line arguments (None exists at the moment)
	 */
	public static void main(String args[]){
		/*set a boolean flag to show if you are in an applet or not */
		inApplet=false;
		
		/*Create a Frame to place our application in. */
		/*You can change the string value to show your desired label*/
		/*for the frame */
		myFrame = new Frame ("CrackOut");
		myFrame.setBackground(Color.black);
		
		/*Create a instance. */
		CrackOut myApp = new CrackOut();         /*Add the current application to the Frame */
		myFrame.add ("Center",myApp);
		myFrame.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e)
				{
					myFrame.dispose();
					System.exit(1);
				}
			}
		);
		
		/*Resize the Frame to the desired size, and make it visible */
		myFrame.setSize(700,500);
		myFrame.show();
		
		/*Run the methods the browser normally would */
		myApp.init();
		myApp.start();
	
	}
}
