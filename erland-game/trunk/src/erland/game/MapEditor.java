package erland.game;

import erland.game.GamePanel;
import erland.game.BlockContainerInterface;
import erland.game.ScreenHandlerInterface;
import erland.util.ImageHandlerInterface;
import erland.util.ImageCreatorInterface;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

public abstract class MapEditor implements GamePanel {
    protected boolean bQuit;
    protected MouseListener mouseListener;
    protected BlockContainerInterface cont;
    protected BlockContainerInterface contSelect;
    protected MapEditorBlockInterface selectBlocks[][];
    protected MapEditorBlockInterface mapBlocks[][];
    protected MapEditorBlockInterface selectedBlock;
    protected JPanel buttonPanel;
    protected ActionListener actionListener;
    protected MouseMotionListener mouseMotionListener;
    protected GameEnvironmentInterface environment;

    public boolean isExit() {
        return bQuit;
    }

    public void exit() {
        environment.getScreenHandler().getFrame().removeMouseListener(mouseListener);
        environment.getScreenHandler().getFrame().removeMouseMotionListener(mouseMotionListener);
        if(buttonPanel!=null) {
            environment.getScreenHandler().remove(buttonPanel);
        }
    }

    protected abstract BlockContainerInterface getMapContainer(int offsetX, int offsetY);

    protected abstract BlockContainerInterface getSelectContainer(int offsetX, int offsetY);

    protected abstract MapEditorBlockInterface[][] getSelectBlocks();

    protected abstract MapEditorBlockInterface[][] getMapBlocks();


    protected MapEditorBlockInterface prepareNewBlock(MapEditorBlockInterface oldBlock,MapEditorBlockInterface newBlock)
    {
        return newBlock;
    }
    public void init(GameEnvironmentInterface environment) {
        bQuit = false;
        this.environment = environment;

        mouseListener = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1) {
                    leftMousePressed(e);
                }
            }
        };
        mouseMotionListener = new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if ((e.getModifiers() & e.BUTTON1_MASK)!=0) {
                    leftMouseDragged(e);
                }
            }
        };
        environment.getScreenHandler().getFrame().addMouseListener(mouseListener);
        environment.getScreenHandler().getFrame().addMouseMotionListener(mouseMotionListener);
        buttonPanel = initButtonPanel();
        if(buttonPanel!=null) {
            initDefaultButtons(buttonPanel);
            initButtons(buttonPanel);
            environment.getScreenHandler().add(buttonPanel);
        }

        contSelect = getSelectContainer(0,0);
        cont = getMapContainer(0,0);
        initFinish();
        updateBlocks();

        environment.getScreenHandler().getFrame().setBackground(Color.black);
    }

    public void leftMouseDragged(MouseEvent e)
    {
        Rectangle rc = new Rectangle(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
        if(rc.contains(e.getX(),e.getY())) {
            int posX = (int)(e.getX()-rc.getMinX()+cont.getScrollingOffsetX())/cont.getSquareSize();
            int posY = (int)(e.getY()-rc.getMinY()+cont.getScrollingOffsetY())/cont.getSquareSize();
            if(selectedBlock!=null) {
                if(posX>=0 && posX<mapBlocks.length && posY>=0 && posY<mapBlocks[posX].length) {
                    mapBlocks[posX][posY] = prepareNewBlock(mapBlocks[posX][posY],cloneBlock(selectedBlock,cont,posX,posY));
                }
            }else {
                if(isEmptyAllowed()) {
                    if(posX>=0 && posX<mapBlocks.length && posY>=0 && posY<mapBlocks[posX].length) {
                        mapBlocks[posX][posY] = null;
                    }
                }
            }
        }
    }
    public void leftMousePressed(MouseEvent e)
    {
        Rectangle rc = new Rectangle(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
        Rectangle rcSelect = new Rectangle(contSelect.getOffsetX(),contSelect.getOffsetY(),contSelect.getDrawingSizeX(),contSelect.getDrawingSizeY());
        if(rc.contains(e.getX(),e.getY())) {
            leftMouseDragged(e);
        }else if(rcSelect.contains(e.getX(),e.getY())) {
            int posX = (int)(e.getX()-rcSelect.getMinX()+contSelect.getScrollingOffsetX())/contSelect.getSquareSize();
            int posY = (int)(e.getY()-rcSelect.getMinY()+contSelect.getScrollingOffsetY())/contSelect.getSquareSize();
            if(selectBlocks[posX][posY]!=null) {
                if(posX>=0 && posX<selectBlocks.length && posY>=0 && posY<selectBlocks[posX].length) {
                    selectedBlock = selectBlocks[posX][posY];
                }
            }else {
                selectedBlock = null;
            }
        }
    }

    protected void updateBlocks()
    {
        selectBlocks = getSelectBlocks();
        mapBlocks = getMapBlocks();
    }
    public void update() {
    }

    protected abstract void drawBlock(Graphics g, MapEditorBlockInterface block);

    protected abstract MapEditorBlockInterface cloneBlock(MapEditorBlockInterface block,BlockContainerInterface cont, int x, int y);

    protected abstract boolean isEmptyAllowed();

    protected void initButtons(JPanel panel)
    {
    }
    protected void initFinish()
    {
    }

    protected void saveButton(MapEditorBlockInterface[][] blocks)
    {
    }
    protected void loadButton()
    {
    }
    protected void exitButton()
    {
        bQuit = true;
    }
    final static int BUTTON_EXIT=1;
    final static int BUTTON_SAVE=2;
    final static int BUTTON_LOAD=3;
    protected int getDefaultButtons()
    {
        return BUTTON_EXIT|BUTTON_SAVE|BUTTON_LOAD;
    }

    protected void initDefaultButtons(JPanel panel)
    {
        int defaultbuttons = getDefaultButtons();
        if((defaultbuttons & BUTTON_EXIT)!=0) {
            JButton b = new JButton("Exit");
            panel.add(b);
            b.addActionListener(new  ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    exitButton();
                }
            });
        }
        if((defaultbuttons & BUTTON_SAVE)!=0) {
            JButton b = new JButton("Save");
            panel.add(b);
            b.addActionListener(new  ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveButton(mapBlocks);
                }
            });
        }
        if((defaultbuttons & BUTTON_LOAD)!=0) {
            JButton b = new JButton("Load");
            panel.add(b);
            b.addActionListener(new  ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadButton();
                }
            });
        }
    }
    protected JPanel initButtonPanel()
    {
        JPanel panel = new JPanel();
        panel.setSize(environment.getScreenHandler().getWidth()/5, environment.getScreenHandler().getHeight());
        panel.setLocation(environment.getScreenHandler().getWidth()*4/5,0);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    public void draw() {
        Graphics g = environment.getScreenHandler().getCurrentGraphics();
        g.clearRect(0,0,environment.getScreenHandler().getWidth(),environment.getScreenHandler().getHeight());
        g.setColor(Color.red);
        g.drawRect(cont.getDrawingPositionX(0)-1,cont.getDrawingPositionY(0)-1,cont.getDrawingSizeX()+1,cont.getDrawingSizeY()+1);
        g.drawRect(contSelect.getDrawingPositionX(0)-1,contSelect.getDrawingPositionY(0)-1,contSelect.getDrawingSizeX()+1,contSelect.getDrawingSizeY()+1);
        for(int x=0;x<mapBlocks.length;x++) {
            for(int y=0;y<mapBlocks[x].length;y++) {
                if(cont.getVisible(x,y)) {
                    if(mapBlocks[x][y]!=null) {
                        drawBlock(g,mapBlocks[x][y]);
                    }
                }
            }
        }
        for(int x=0;x<selectBlocks.length;x++) {
            for(int y=0;y<selectBlocks[x].length;y++) {
                if(contSelect.getVisible(x,y)) {
                    if(selectBlocks[x][y]!=null) {
                        drawBlock(g,selectBlocks[x][y]);
                    }
                }
            }
        }
        if(selectedBlock!=null) {
            g.setColor(Color.white);
            g.drawRect(contSelect.getDrawingPositionX(selectedBlock.getPosX()),contSelect.getDrawingPositionY(selectedBlock.getPosY()),contSelect.getSquareSize(),contSelect.getSquareSize());
        }

        environment.getScreenHandler().paintComponents(g);
    }
}
