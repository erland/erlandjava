package erland.game;

import erland.util.*;
import erland.util.Timer;
import erland.game.FullScreenHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

public abstract class GamePanelHandler implements TimerListenerInterface, GamePanelHandlerInterface, GameEnvironmentInterface, ActionListener {
    protected FullScreenHandler screen;
    protected DisplayMode display[];
    protected boolean bQuit;
    protected boolean bReadyToDraw = true;
    protected int currentGame;
    protected Vector games;
    protected JButton buttons[];
    protected Cursor cursor;
    protected String waitMessage;

    public void actionPerformed(ActionEvent e) {
        if(currentGame==0) {
            if(e.getSource()==buttons[0]) {
                bQuit = true;
            }else {
                for(int i=1;i<buttons.length;i++) {
                    if(e.getSource()==buttons[i]) {
                        for(int j=0;j<buttons.length;j++) {
                            buttons[j].setVisible(false);
                        }
                        waitMessage = ((GamePanelData)games.get(i-1)).getDescription();
                        ((GamePanelData)games.get(i-1)).getPanel().init(this);
                        waitMessage = null;
                        currentGame=i;
                    }
                }
            }
        }
    }

    public void addPanel(String name, String description, GamePanel panel)
    {
        GamePanelData game = new GamePanelData(name,description,panel);
        games.add(game);
    }

    public abstract DisplayMode[] getDisplayModes();

    public abstract void initGames(GamePanelHandlerInterface handler);

    public int getFrameDelay()
    {
        return 15;
    }

    public void init()
    {
        bQuit = false;
        screen = new FullScreenHandler(getDisplayModes(),true);
        screen.getFrame().requestFocus();

        games = new Vector(0);
        initGames(this);
        if(games.size()>1) {
            currentGame = 0;
            buttons = new JButton[games.size()+1];
            buttons[0] = new JButton("Exit");
            buttons[0].setBounds(10,10,100,30);
            buttons[0].addActionListener(this);
            screen.add(buttons[0]);
            for(int i=1;i<=games.size();i++) {
                buttons[i] = new JButton(((GamePanelData)games.get(i-1)).getName());
                buttons[i].setBounds(10,10+i*40,100,30);
                buttons[i].addActionListener(this);
                screen.add(buttons[i]);
            }
        }else {
            currentGame=1;
            ((GamePanelData)games.get(currentGame-1)).getPanel().init(this);
            buttons = null;
        }
        cursor = screen.getFrame().getCursor();
    }

    public void tick()
    {
        bReadyToDraw = true;
    }

    public void run()
    {
        init();
        Timer timer = new Timer(getFrameDelay(), this);
        timer.start();
        try {
            while(!bQuit) {
                if(currentGame>0) {
                    ((GamePanelData)games.get(currentGame-1)).getPanel().update();
                }
                while(!bReadyToDraw) {
                    Thread.yield();
                }
                bReadyToDraw = false;
                if(currentGame>0) {
                    if(!timer.isRunning()) {
                        timer.start();
                    }
                    if(!((GamePanelData)games.get(currentGame-1)).getPanel().isExit()) {

                        ((GamePanelData)games.get(currentGame-1)).getPanel().draw();

                    }else {
                        ((GamePanelData)games.get(currentGame-1)).getPanel().exit();
                        if(buttons!=null) {
                            for(int i=0;i<buttons.length;i++) {
                                buttons[i].setVisible(true);
                            }
                            currentGame=0;
                            screen.getFrame().setCursor(cursor);
                        }else {
                            bQuit = true;
                        }
                    }
                }
                if(currentGame==0) {
                    Graphics g = screen.getCurrentGraphics();
                    g.clearRect(0,0,screen.getWidth(),screen.getHeight());
                    screen.getFrame().paint(g);
                    if(waitMessage!=null) {
                        g.drawString(waitMessage,250,200);
                        g.drawString("Please wait...",250,240);
                    }
                }
                screen.swapScreens();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        screen.dispose();
        timer.stop();
    }

    public ScreenHandlerInterface getScreenHandler() {
        return screen;
    }

    public ImageCreatorInterface getImageCreator() {
        return screen;
    }

    class GamePanelData {
        protected String name;
        protected String description;
        protected GamePanel panel;
        public GamePanelData(String name, String description, GamePanel panel) {
            this.name = name;
            this.description = description;
            this.panel = panel;
        }
        public String getName() {
            return name;
        }
        public String getDescription() {
            return description;
        }
        public GamePanel getPanel() {
            return panel;
        }
    }
}
