/*
 * @(#)BoulderDash.java 1.0 01/08/08
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_4\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */
package erland.game.boulderdash;
import erland.util.*;
import erland.game.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.math.*;

/**
 * Main applet/application class
 */
public class BoulderDash extends Applet 
	implements Runnable
{
	/** Indicates if the game runs as an applet or application, true indicates applet */
	protected static boolean inApplet =true;
	/** The main Frame window if the game runs as an application */
	protected static Frame myFrame;
	/** Main thread that updates the game logic and redraws the screen */
	protected Thread animator;
	/** Image object used for the double buffering mechanism */
	protected Image imag;
	/** Graphics object used for the double buffering mechanism */
	protected Graphics offScreen;
	/** Parameter storage object which is used to access stored game data */
	protected ParameterValueStorageInterface cookies;
	/** This is the main class that implements the game */
	protected BoulderDashMain main;
	/** This is the main class that implements the level editor */
	protected BoulderDashEditor editor;
	/** Editor and Game buttons */
	protected Button buttons[];
	/** Indicates if cheatmode is active or not */
	protected boolean bCheatMode;
	/** Counter that counts the number of characters entered in the secret cheat word */
	protected int cheatCounter;
	/** Image handler object */
	protected ImageHandlerInterface images;
	/** Handler for the button actions */
	protected ActionHandler actionHandler;

	/**
	 * Class that catch all supported keyboard commands
	 * and propagate them further to the main game class
	 */
	class Keyboard extends KeyAdapter {
		/**
		 * Called when a key is pressed down
		 * @param e Information about the key pressed
		 */
		public void keyPressed(KeyEvent e) {
			if(main!=null) {
				if(e.getKeyCode()==e.VK_LEFT) {
					main.moveLeft();
				}else if(e.getKeyCode()==e.VK_RIGHT) {
					main.moveRight();
				}else if(e.getKeyCode()==e.VK_UP) {
					main.moveUp();
				}else if(e.getKeyCode()==e.VK_DOWN) {
					main.moveDown();
				}else if(e.getKeyCode()==e.VK_SPACE) {
					main.hitSpace();
				}else if(bCheatMode && e.getKeyCode()==e.VK_F1) {
					// TODO: main.someCheatMethod();
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
		 * @param e Information about the key released
		 */
		public void keyReleased(KeyEvent e) {
			if(main!=null) {
				if(e.getKeyCode()==e.VK_LEFT) {
					main.stopMoveLeft();
				}else if(e.getKeyCode()==e.VK_RIGHT) {
					main.stopMoveRight();
				}else if(e.getKeyCode()==e.VK_UP) {
					main.stopMoveUp();
				}else if(e.getKeyCode()==e.VK_DOWN) {
					main.stopMoveDown();
				}
			}
		}
	}

	/**
	 * Class that catch all supported mouse commands
	 * and propagate them further to the main game class
	 */
	class Mouse extends MouseAdapter {
		/**
		 * Called when a mouse button is pressed down
		 * @param e Information about which mouse button that was pressed down
		 */
		public void mousePressed(MouseEvent e)
		{
			if(main!=null && (e.getModifiers() & e.BUTTON1_MASK)!=0) {
				main.handleLeftMousePressed(e.getX(),e.getY());
			}else if(editor!=null && (e.getModifiers() & e.BUTTON1_MASK)!=0) {
				editor.handleLeftMousePressed(e.getX(),e.getY());
			} 
		}
		/**
		 * Called when a mouse button is released
		 * @param e Information about which mouse button that was released
		 */
		public void mouseReleased(MouseEvent e)
		{
			if(main!=null && (e.getModifiers() & e.BUTTON1_MASK)!=0) {
				main.handleLeftMouseReleased(e.getX(),e.getY());
			}else if(editor!=null && (e.getModifiers() & e.BUTTON1_MASK)!=0) {
				editor.handleLeftMouseReleased(e.getX(),e.getY());
			}
		}
		/**
		 * Called when a mouse button is clicked (pressed + released)
		 * @param e Information about which mouse button that was clicked
		 */
		public void mouseClicked(MouseEvent e)
		{
			if(main!=null && (e.getModifiers() & e.BUTTON1_MASK)!=0) {
				main.handleLeftMouseClicked(e.getX(),e.getY());
			}else if(editor!=null && (e.getModifiers() & e.BUTTON1_MASK)!=0) {
				editor.handleLeftMouseClicked(e.getX(),e.getY());
			}
		}
	}

	/**
	 * Class that catch all supported mouse commands
	 * and propagate them further to the main game class
	 */
	class MouseDrag extends MouseMotionAdapter {
		/**
		 * Called when the mouse is dragged with a button pressed down
		 * @param e Information about which mouse button that was pressed down
		 */
		public void mouseDragged(MouseEvent e)
		{
			if(main!=null && (e.getModifiers() & e.BUTTON1_MASK)!=0) {
				main.handleLeftMouseDragged(e.getX(),e.getY());
			}else if(editor!=null && (e.getModifiers() & e.BUTTON1_MASK)!=0) {
				editor.handleLeftMouseDragged(e.getX(),e.getY());
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
					main = new BoulderDashMain(c,cookies,images,10,10);
					requestFocus();
					editor = null;
				}else if(e.getSource()==buttons[1]) {
					buttons[0].setVisible(false);
					buttons[1].setVisible(false);
					editor = new BoulderDashEditor(c,cookies,images, 10,10);
					requestFocus();
					main = null;
				}
			}
		}
	}

	/**
	 * Initializes a new game
	 */
	public void init() {
		//Log.setLog("<log><logitem1>erland.game.boulderdash.LevelFactory</logitem1><logitem2>erland.game.boulderdash.Level</logitem2></log>");
		this.setLayout(null);
		bCheatMode=false;
		cheatCounter=0;
		addKeyListener(new Keyboard());
		addMouseListener(new Mouse());
		addMouseMotionListener(new MouseDrag());
		this.setBackground(Color.black);
		String mayScript = null;
		if(inApplet) {
			mayScript = this.getParameter("MAYSCRIPT");
			this.images = new ImageHandlerForApplet(this,"images/boulderdash");
			if(mayScript!=null) {
				this.cookies = new CookieHandler(this);
			}
		}else {
			this.cookies = new ParameterStorageGroup(new FileStorage("boulderdash.xml"),"boulderdash","level");
			images = new ImageHandlerForApplication(this,"images/boulderdash");
		}

		editor = null;
		main = null;


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

		if(this.cookies == null) {
			buttons[0].setVisible(false);
			buttons[1].setVisible(false);
			main = new BoulderDashMain(this,cookies,images,10,10);
		}
	}

	/**
	 * Called whenever the screen needs to be redrawn.
	 * Redraws all the graphics on the screen
	 * @param g Grahics object to draw on
	 */
	public void paint(Graphics g) {
		int width = getSize().width;
		int height = getSize().height;
		if(imag == null) {
			imag = createImage(width,height);
			offScreen = imag.getGraphics();
			offScreen.setColor(getBackground());
		}
		if(main!=null) {
			offScreen.clearRect(420,10,width-420,height/2+40);
			offScreen.setColor(Color.blue);
			main.draw(offScreen);
		}else if(editor != null) {
			offScreen.clearRect(0,0,width,height);
			offScreen.setColor(Color.blue);
			editor.draw(offScreen);
		}else {
			offScreen.clearRect(0,0,width,height);
			offScreen.setColor(Color.blue);
		}
		g.drawImage(imag,0,0,null);
	}

	/**
	 * Start the main thread
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
	 * Stop the main thread
	 */
	public void stop()
	{
		if((animator != null) && (animator.isAlive())) {
			animator = null;
		}
	}

	/**
	 * The main thread will be in this method all the time, so this is
	 * the method that makes sure the game logic is updated and that the screen is redrawn 
	 * with regual intervals
	 */
	public void run()
	{
		long time = System.currentTimeMillis();
		final int frameDelay=19;
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
	 * Redraws the screen, it just calls {@link #paint(Graphics)}
	 */
	public void update(Graphics g)
	{
		paint(g);
	}
	
	/**
	 * Main method that is the starting point if the game is run
	 * as an application.
	 */
	public static void main(String args[]){
		/*set a boolean flag to show if you are in an applet or not */
		inApplet=false;
		
		/*Create a Frame to place our application in. */
		/*You can change the string value to show your desired label*/
		/*for the frame */
		myFrame = new Frame ("BoulderDash");
		myFrame.setBackground(Color.black);
		
		/*Create a instance. */
		BoulderDash myApp = new BoulderDash();         /*Add the current application to the Frame */
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
