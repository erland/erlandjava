/*
 * @(#)Tetris.java 1.0 01/07/12
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_2\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */

package erland.game.tetris;
import erland.util.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class Tetris extends Applet 
	implements Runnable 
{
	static boolean inApplet =true;
	static Frame myFrame;
	Thread animator;
	BlockContainer container;
	Image imag;
	Graphics offScreen;
	ParameterValueStorageInterface cookies;

	class Keyboard extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==e.VK_LEFT) {
				container.moveLeft();
			}else if(e.getKeyCode()==e.VK_RIGHT) {
				container.moveRight();
			}else if(e.getKeyCode()==e.VK_UP) {
				container.rotateRight();
			}else if(e.getKeyCode()==e.VK_DOWN) {
				while(container.moveDown()) {
					repaint();
				}
			}else if(e.getKeyCode()==e.VK_SPACE) {
				container.newGame();
			}
		}
	}

	public void init() {
		if(inApplet) {
			String mayScript = this.getParameter("MAYSCRIPT");
			if(mayScript!=null) {
				cookies = new CookieHandler(this);
			}else {
				cookies = null;
			}
		}else {
			this.cookies = new ParameterStorage("data.xml","tetris");
		}
		container = new BlockContainer(cookies,20,20,10,30,13,1);
		addKeyListener(new Keyboard());
		this.setBackground(Color.black);
	}

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
		if(container!=null) {
			container.draw(offScreen);
		}else {
			System.out.println("Ojsan, nu är det fel");
		}
		g.drawImage(imag,0,0,null);
	}
	
	public void start()
	{
		this.requestFocus();
		if(animator==null) {
			animator= new Thread(this);
			animator.start();
		}
	}
	
	public void stop()
	{
		if((animator != null) && (animator.isAlive())) {
			animator = null;
		}
	}
	
	public void run()
	{
		while(animator != null) {
			try {
				animator.sleep(50);
				container.update();
				repaint();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void update(Graphics g)
	{
		paint(g);
	}
	
	public static void main(String args[]){
		/*set a boolean flag to show if you are in an applet or not */
		inApplet=false;
		
		/*Create a Frame to place our application in. */
		/*You can change the string value to show your desired label*/
		/*for the frame */
		myFrame = new Frame ("Tetris");
		
		/*Create a instance. */
		Tetris myApp = new Tetris();         /*Add the current application to the Frame */
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
		myFrame.setSize(450,490);
		
		/*Run the methods the browser normally would */
		myApp.init();
		myFrame.show();
		myApp.start();
	
	}
}
