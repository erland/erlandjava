/*
 * @(#)Pipes.java 1.0 01/08/03
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_4\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */
package erland.game.pipes;
import erland.util.*;
import erland.game.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.math.*;

/**
 * Main applet/application class
 */
public class Pipes extends Applet 
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
	protected PipesMain main;
	/** This is the class that implements the options screen */
	protected PipesOptions options;
	/** Indicates if cheatmode is active or not */
	protected boolean bCheatMode;
	/** Counter that counts the number of characters entered in the secret cheat word */
	protected int cheatCounter;
	/** Image handler object */
	protected ImageHandlerInterface images;
	/** Object that handles when one of the buttons are pressed */
	protected ActionHandler actionHandler;
	/** Option and Game buttons */
	protected Button buttons[];

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
					// TODO: main.someMethod();
				}else if(e.getKeyCode()==e.VK_RIGHT) {
					// TODO: main.someMethod();
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
					// TODO: main.someMethod();
				}else if(e.getKeyCode()==e.VK_RIGHT) {
					// TODO: main.someMethod();
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
			}else if(options!=null && (e.getModifiers() & e.BUTTON1_MASK)!=0) {
				options.handleLeftMousePressed(e.getX(),e.getY());
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
			}else if(options!=null && (e.getModifiers() & e.BUTTON1_MASK)!=0) {
				options.handleLeftMouseReleased(e.getX(),e.getY());
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
			}else if(options!=null && (e.getModifiers() & e.BUTTON1_MASK)!=0) {
				options.handleLeftMouseClicked(e.getX(),e.getY());
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
			if(main==null && options==null) {
				if(e.getSource()==buttons[0]) {
					for (int i=0; i<buttons.length; i++) {
						buttons[i].setVisible(false);
				    }
					main = new PipesMain(c,cookies,images,10,10,LevelFactory.GameType.UntilWaterStopped);
					requestFocus();
					options = null;
				}else if(e.getSource()==buttons[1]) {
					for (int i=0; i<buttons.length; i++) {
						buttons[i].setVisible(false);
				    }
					main = new PipesMain(c,cookies,images,10,10,LevelFactory.GameType.UntilPoolsFilled);
					requestFocus();
					options = null;
				}else if(cookies!=null && e.getSource()==buttons[2]) {
					for (int i=0; i<buttons.length; i++) {
						buttons[i].setVisible(false);
				    }
					options = new PipesOptions(c,cookies,images,10,10);
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
		//Log.setLog("<log><logitem1>erland.game.pipes.PipeBlock</logitem1><logitem2>erland.util.ParameterStorageString</logitem2></log>");
		this.setLayout(null);
		bCheatMode=false;
		cheatCounter=0;
		addKeyListener(new Keyboard());
		addMouseListener(new Mouse());
		addMouseMotionListener(new MouseDrag());
		this.setBackground(Color.black);
		String mayScript = null;
		if(inApplet) {
			images = new ImageHandlerForApplet(this,"images/pipes");
			mayScript = this.getParameter("MAYSCRIPT");
			if(mayScript!=null) {
				this.cookies = new CookieHandler(this);
			}
		}else {
			images = new ImageHandlerForApplication(myFrame,"images/pipes");
			this.cookies = new ParameterStorage("pipes.xml","pipes");
		}

		int nButtons=3;
		if(cookies==null) {
			nButtons=2;
		}
		buttons = new Button[nButtons];
		buttons[0] = new Button("Game 1");
		buttons[1] = new Button("Game 2");
		buttons[0].setBounds(100,100,73,25);
		buttons[1].setBounds(100,140,73,25);
		this.add(buttons[0]);
		this.add(buttons[1]);
		actionHandler= new ActionHandler(this);
		buttons[0].addActionListener(actionHandler);
		buttons[1].addActionListener(actionHandler);
		if(cookies!=null) {
			buttons[2] = new Button("Options");
			buttons[2].setBounds(100,180,73,25);
			this.add(buttons[2]);
			buttons[2].addActionListener(actionHandler);
		}
		main = null;
		options = null;
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
		offScreen.setColor(Color.blue);
		if(main!=null) {
			offScreen.clearRect(320,0,width-320,height);
			main.draw(offScreen);
		}else if(options != null) {
			offScreen.clearRect(0,0,width,height);
			options.draw(offScreen);
		}else {
			offScreen.clearRect(0,0,width,height);
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
			//animator.setPriority(Thread.MAX_PRIORITY);
			try {
				time+=frameDelay;
				animator.sleep(Math.max(0,time-System.currentTimeMillis()));
				if(main!=null) {
					main.update();
					if(main.isExit()) {
						for (int i=0; i<buttons.length; i++) {
							buttons[i].setVisible(true);
					    }
						this.requestFocus();
						cheatCounter=0;
						bCheatMode=false;
						main=null;
					}
				}else if(options!=null) {
					if(options.isExit()) {
						for (int i=0; i<buttons.length; i++) {
							buttons[i].setVisible(true);
					    }
						this.requestFocus();
						cheatCounter=0;
						bCheatMode=false;
						options=null;
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
		myFrame = new Frame ("Pipes");
        myFrame.setBackground(Color.black);

		/*Create a instance. */
		Pipes myApp = new Pipes();         /*Add the current application to the Frame */
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
