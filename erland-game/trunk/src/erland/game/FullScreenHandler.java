package erland.game;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.MemoryImageSource;
import erland.util.ImageCreatorInterface;

import javax.swing.*;

/**
 * Object that represents a full screen window
 * @author Erland Isaksson
 */
public class FullScreenHandler implements ScreenHandlerInterface, ImageCreatorInterface {
   private BufferStrategy bufferStrategy;
   private Graphics lastGraphics;
   private GraphicsDevice device;
   private JFrame mainFrame;
   private int screenTopLeftX;
   private int screenTopLeftY;
   private int screenWidth;
   private int screenHeight;
   private DisplayMode[] allowedDisplayModes;
   private boolean bQuit;

    /**
     * Creates new FullScreenHandler
     * @param displayModes List of displaymodes that is allowed
     * @param fullScreen True if exclusive full screen is desired
     */
    public FullScreenHandler (DisplayMode[] displayModes, boolean fullScreen) {
        init(displayModes,fullScreen,false);
    }

    /**
     * Creates new FullScreenHandler
     * @param displayModes List of displaymodes that is allowed
     * @param fullScreen True if exclusive full screen is desired
     * @param windowed Indicates if windowed mode should be choosen if not fullscreen
     */
    public FullScreenHandler (DisplayMode[] displayModes, boolean fullScreen, boolean windowed) {
        init(displayModes,fullScreen,windowed);
    }

    protected void init(DisplayMode[] displayModes, boolean fullScreen, boolean windowed) {
        try {
            bQuit = false;
            allowedDisplayModes = displayModes;
            GraphicsEnvironment env = GraphicsEnvironment.
                getLocalGraphicsEnvironment();
            device = env.getDefaultScreenDevice();
            GraphicsConfiguration gc = device.getDefaultConfiguration();
            mainFrame = new JFrame(gc);
            mainFrame.getContentPane().setLayout(null);
            mainFrame.getContentPane().setIgnoreRepaint(true);
            mainFrame.getRootPane().setIgnoreRepaint(true);
            mainFrame.getLayeredPane().setIgnoreRepaint(true);
            mainFrame.setIgnoreRepaint(true);
            if (fullScreen) {
                mainFrame.setUndecorated(true);
                device.setFullScreenWindow(mainFrame);
                if (device.isDisplayChangeSupported()) {
                    chooseBestDisplayMode(device);
                }
            }  else {
                DisplayMode dm;
                if(windowed) {
                    dm = getBestDisplayMode(device);
                    mainFrame.setUndecorated(false);
                    screenWidth = dm.getWidth();
                    screenHeight = dm.getHeight();
                    mainFrame.setSize(new Dimension(screenWidth,screenHeight));
                    mainFrame.show();
                    Insets ins = mainFrame.getInsets();
                    screenTopLeftX = ins.left;
                    screenTopLeftY = ins.top;
                }else {
                    dm = device.getDisplayMode();
                    mainFrame.setUndecorated(true);
                    screenWidth = dm.getWidth();
                    screenHeight = dm.getHeight();
                    mainFrame.setSize(new Dimension(screenWidth,screenHeight));
                    Insets ins = mainFrame.getInsets();
                    screenTopLeftX = ins.left;
                    screenTopLeftY = ins.top;
                    screenWidth -= screenTopLeftX;
                    screenHeight -= screenTopLeftY;
                    mainFrame.show();
                }
            }
            mainFrame.addWindowListener( new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                    bQuit = true;
                }
            });
            mainFrame.createBufferStrategy(2);
            bufferStrategy = mainFrame.getBufferStrategy();                                          
        } catch (Exception e){
            e.printStackTrace();
            device.setFullScreenWindow(null);
            bQuit = true;
        }
    }

    /**
     * Disable automatic repaint of a component
     * @param c The component to disable repaint on
     */
    private void setRepaintOff(Component c){
        c.setIgnoreRepaint(true);
        if (c instanceof java.awt.Container) {
            Component[] children = ((Container)c).getComponents();
            for(int i=0;i<children.length;i++) {
                setRepaintOff(children[i]);
            }
        }
    }
    
    public void add(Component c) {
        setRepaintOff(c);
        mainFrame.getContentPane().add(c);
    }

    public void remove(Component c)
    {
        mainFrame.getContentPane().remove(c);
    }

    public void paintComponents(Graphics g) {
        mainFrame.getContentPane().paintComponents(g);
    }

    /**
     * Gets the best display mode by lookup available display modes
     * and choose among the allowed display modes reported in the
     * consturctor
     * @param device The GraphicsDevice to use
     * @return The choosen DisplayMode
     */
    private DisplayMode getBestDisplayMode(GraphicsDevice device) {
        for (int x = 0; x < allowedDisplayModes.length; x++) {
            DisplayMode[] modes = device.getDisplayModes();
            for (int i = 0; i < modes.length; i++) {
                if (modes[i].getWidth() == allowedDisplayModes[x].getWidth()
                   && modes[i].getHeight() == allowedDisplayModes[x].getHeight()
                   && modes[i].getBitDepth() == allowedDisplayModes[x].getBitDepth()
                   ) {
                    return allowedDisplayModes[x];
                }
            }
        }
        return null;
    }

    /**
     * Choose best available display mode of the allowed
     * @param device The GraphicsDevice to use
     */
    private void chooseBestDisplayMode(GraphicsDevice device) {
        DisplayMode best = getBestDisplayMode(device);
        if (best != null) {
            device.setDisplayMode(best);
        }
        screenWidth = best.getWidth();
        screenHeight = best.getHeight();
    }

    public Graphics getCurrentGraphics(){
        lastGraphics = bufferStrategy.getDrawGraphics();
        lastGraphics.setClip(0, 0, screenWidth, screenHeight);
        lastGraphics.translate(screenTopLeftX, screenTopLeftY);
        return lastGraphics;
    }

    public boolean swapScreens(){
        boolean done = false;
        if (!bufferStrategy.contentsLost()){
            bufferStrategy.show();
            done = true;
        } else {
            System.out.println("Buffer lost");
            bQuit = true;
        }
        lastGraphics.dispose();
        return done;
    }

    public Container getContainer(){
        return mainFrame;
    }

    public int getWidth() {
        return mainFrame.getSize().width;
    }

    public int getHeight() {
        return mainFrame.getSize().height;
    }

    public void dispose() {
        device.setFullScreenWindow(null);
    }

     public Image createVolatileImage() {
        // try ot get a volatile image the size of the screen
        return device.getDefaultConfiguration().createCompatibleVolatileImage(getWidth(),
            getHeight());
    }

    public Image createImage() {
        // Creates a new Image with the size of the screen
        return mainFrame.createImage(getWidth(),getHeight());
    }

    public void setCursor(Cursor cursor)
    {
        if(cursor == null) {
            setCursor(Toolkit.getDefaultToolkit().createCustomCursor(mainFrame.createImage(new MemoryImageSource(256, 256, new int[1], 0, 256)),new Point(0, 0), "empty"));
        }else {
            mainFrame.setCursor(cursor);
        }
    }

    public Image createVolatileImage(int width, int height) {
        return device.getDefaultConfiguration().createCompatibleVolatileImage(width,height);
    }

    public Image createImage(int width, int height) {
        return mainFrame.createImage(width,height);
    }

    public Image createCompatibleImage(int width, int height,int transparency)
    {
        return device.getDefaultConfiguration().createCompatibleImage(width,height,transparency);
    }

    public Image createVolatileImage(Image image) {
        Image img = createVolatileImage(image.getWidth(mainFrame),image.getHeight(mainFrame));
        img.getGraphics().drawImage(image,0,0,mainFrame);
        return img;
    }

    public int getScreenX(int x) {
        return x-screenTopLeftX;
    }

    public int getScreenY(int y) {
        return y-screenTopLeftY;
    }

    public boolean isExit() {
        return bQuit;
    }
}



