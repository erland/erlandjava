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
	/** Indicates if cheatmode is active or not */
	protected boolean bCheatMode;
	/** Counter that counts the number of characters entered in the secret cheat word */
	protected int cheatCounter;
	/** Image handler object */
	protected ImageHandlerInterface images;

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
			}
		}
	}

	/**
	 * Initializes a new game
	 */
	public void init() {
		//Log.setLog("<log><logitem1>erland.game.boulderdash.BoulderDashMain</logitem1></log>");
		this.setLayout(null);
		bCheatMode=false;
		cheatCounter=0;
		addKeyListener(new Keyboard());
		addMouseListener(new Mouse());
		this.setBackground(Color.black);
		String mayScript = null;
		if(inApplet) {
			mayScript = this.getParameter("MAYSCRIPT");
			this.images = new ImageHandlerForApplet(this,"images/boulderdash");
			if(mayScript!=null) {
				this.cookies = new CookieHandler(this);
			}
		}else {
			this.cookies = new ParameterStorage("boulderdash.xml","boulderdash");
			images = new ImageHandlerForApplication(this,"images/boulderdash");
		}

		main = new BoulderDashMain(this,cookies,images,10,10);
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
		offScreen.clearRect(0,0,width,height);
		offScreen.setColor(Color.blue);
		if(main!=null) {
			main.draw(offScreen);
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
		final int frameDelay=20;
		int setSleepTime=0;
		while(animator != null) {
			animator.setPriority(Thread.MAX_PRIORITY);
			try {
				time+=frameDelay;
				animator.sleep(Math.max(0,time-System.currentTimeMillis()));
				if(main!=null) {
					main.update();
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
