package erland.game.tetris;

import erland.game.GameEnvironmentInterface;
import erland.game.FpsCounter;
import erland.game.GamePanelInterface;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

public class TetrisMain implements GamePanelInterface {
    private GameEnvironmentInterface environment;
    private boolean bQuit;
    private boolean cheatMode;
    private KeyListener keyListener;
    private FpsCounter fps;
    /** Container representing the game logo */
    protected BlockContainerData titleContainer;
    /** Container representing the next block */
    protected BlockContainerData previewContainer;

    /** Size of the squares */
    protected int squareSize;

    /** Horizontal drawing offset */
    protected int offsetX;
    /** Vertical drawing offset */
    protected int offsetY;
    /** Number of pixels between squares */
    protected int spaceBetweenSquares;
    /** Counter to handle blinking of text when game is completed or GAME OVER has occurred */
    protected int endBlink;
    /** Speed of blinking text */
    protected final int END_BLINK_SPEED=10;
    /** Object representing the tetris data model */
    private TetrisModelInterface model;

    public TetrisMain(TetrisModelInterface model) {
        this.model = model;
    }
    public boolean isExit() {
        return bQuit;
    }

    public void setCheatmode(boolean enable) {
        cheatMode = enable;
    }

    public void exit() {
        environment.getScreenHandler().getContainer().removeKeyListener(keyListener);
    }

    public void init(GameEnvironmentInterface environment) {
        this.environment = environment;
        bQuit = false;
        cheatMode = false;

        keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    bQuit = true;
                }else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    model.moveLeft();
                }else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    model.moveRight();
                }else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    model.rotate();
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    model.moveDown();
                }else if(e.getKeyCode()==e.VK_SPACE) {
				    newGame();
			    }
            }
        };
        environment.getScreenHandler().getContainer().setCursor(null);
        environment.getScreenHandler().getContainer().requestFocus();
        environment.getScreenHandler().getContainer().addKeyListener(keyListener);
        environment.getScreenHandler().getContainer().setBackground(Color.black);
        fps = new FpsCounter(60);

        previewContainer = new BlockContainerData(4,2);
        titleContainer = new BlockContainerData(34,5);
        titleContainer.clear();
        makeTitle(titleContainer);
        this.offsetX = 20;
        this.offsetY = 20;
        this.squareSize = 13;
        this.spaceBetweenSquares = 1;
        init();
        model.init(environment);
    }

    /**
     * Initialize a new game
     */
    protected void init()
    {
        endBlink=0;
    }

    /**
     * Start a new game, this will only succeed if there is not a game already running
     */
    public void newGame()
    {
        if(model.isEnd() || !model.isStarted()) {
            model.startGame();
        }
    }

    /**
     * Draw the tetris logo in the specied BlockMatrix
     * @param m The BlockMatrix to draw the logo in
     */
    protected void makeTitle(BlockMatrix m) {
        //XXXXX-XXXXX-XXXXX-XXXX----X----XXXX
        //--X---X-------X---X---X---X---X----
        //--X---XXXX----X---XXXX----X----XXX-
        //--X---X-------X---X--X----X-------X
        //--X---XXXXX---X---X---X---X---XXXX-

        // "T"
        Color c = Color.red;
        m.setUsed(0,0,c);
        m.setUsed(1,0,c);
        m.setUsed(2,0,c);
        m.setUsed(3,0,c);
        m.setUsed(4,0,c);
        m.setUsed(2,1,c);
        m.setUsed(2,2,c);
        m.setUsed(2,3,c);
        m.setUsed(2,4,c);
        // "E"
        m.setUsed(6,0,c);
        m.setUsed(7,0,c);
        m.setUsed(8,0,c);
        m.setUsed(9,0,c);
        m.setUsed(10,0,c);
        m.setUsed(6,1,c);
        m.setUsed(6,2,c);
        m.setUsed(7,2,c);
        m.setUsed(8,2,c);
        m.setUsed(9,2,c);
        m.setUsed(6,3,c);
        m.setUsed(6,4,c);
        m.setUsed(7,4,c);
        m.setUsed(8,4,c);
        m.setUsed(9,4,c);
        m.setUsed(10,4,c);
        // "T"
        m.setUsed(12,0,c);
        m.setUsed(13,0,c);
        m.setUsed(14,0,c);
        m.setUsed(15,0,c);
        m.setUsed(16,0,c);
        m.setUsed(14,1,c);
        m.setUsed(14,2,c);
        m.setUsed(14,3,c);
        m.setUsed(14,4,c);
        // "R"
        m.setUsed(18,0,c);
        m.setUsed(18,1,c);
        m.setUsed(18,2,c);
        m.setUsed(18,3,c);
        m.setUsed(18,4,c);
        m.setUsed(19,0,c);
        m.setUsed(20,0,c);
        m.setUsed(21,0,c);
        m.setUsed(22,1,c);
        m.setUsed(19,2,c);
        m.setUsed(20,2,c);
        m.setUsed(21,2,c);
        m.setUsed(21,3,c);
        m.setUsed(22,4,c);
        // "I"
        m.setUsed(25,0,c);
        m.setUsed(25,1,c);
        m.setUsed(25,2,c);
        m.setUsed(25,3,c);
        m.setUsed(25,4,c);
        // "S"
        m.setUsed(30,0,c);
        m.setUsed(31,0,c);
        m.setUsed(32,0,c);
        m.setUsed(33,0,c);
        m.setUsed(29,1,c);
        m.setUsed(30,2,c);
        m.setUsed(31,2,c);
        m.setUsed(32,2,c);
        m.setUsed(33,3,c);
        m.setUsed(29,4,c);
        m.setUsed(30,4,c);
        m.setUsed(31,4,c);
        m.setUsed(32,4,c);
    }

    public void update() {
        fps.update();
        model.update();
    }

    public void draw() {
        Graphics g = environment.getScreenHandler().getCurrentGraphics();
        g.setColor(Color.black);
        g.clearRect(0,0,640,480);
        if(cheatMode) {
            fps.draw(g,Color.white,10,10);
        }
        BlockContainerData mainContainer1 = model.getMainContainer();
        int rightColumnX = offsetX + mainContainer1.getWidth()*(squareSize+spaceBetweenSquares)+10;
        int rightColumnX2 = 640-(offsetX + mainContainer1.getWidth()*(squareSize+spaceBetweenSquares) + 60);
        int rightColumnY = offsetY;
        drawContainer(g,mainContainer1,offsetX,offsetY, squareSize,spaceBetweenSquares,1,Color.blue,Color.darkGray);
        if(model.isMultiplayer()) {
            if(model.isOpponentConnected()) {
                drawContainer(g,model.getOpponentMainContainer(),640-(mainContainer1.getWidth()*(squareSize+spaceBetweenSquares)+offsetX),offsetY, squareSize,spaceBetweenSquares,1,Color.blue,Color.darkGray);
            }else {
                drawContainer(g,model.getOpponentMainContainer(),640-(mainContainer1.getWidth()*(squareSize+spaceBetweenSquares)+offsetX),offsetY, squareSize,spaceBetweenSquares,1,Color.darkGray,Color.darkGray);
            }
        }
        updatePreviewContainer();
        drawContainer(g,previewContainer,offsetX + mainContainer1.getWidth()*(squareSize+spaceBetweenSquares)+10,rightColumnY, squareSize, spaceBetweenSquares,1,Color.blue,Color.darkGray);
        drawContainer(g,titleContainer,rightColumnX,
                        offsetY + mainContainer1.getHeight()*(squareSize+spaceBetweenSquares)-titleContainer.getHeight()*(squareSize/2+spaceBetweenSquares)-20,squareSize/2,spaceBetweenSquares,0,Color.blue,Color.black);
        g.drawString("by Erland Isaksson",rightColumnX+147,offsetY + mainContainer1.getHeight()*(squareSize+spaceBetweenSquares));
        rightColumnY += offsetY+previewContainer.getHeight()*(squareSize+spaceBetweenSquares)+20;
        g.setColor(Color.white);
        g.drawString("LEVEL: "+model.getLevel(),rightColumnX, rightColumnY);
        if(model.isMultiplayer()) {
            g.drawString("LEVEL: "+model.getOpponentLevel(),rightColumnX2, rightColumnY);
        }
        rightColumnY+=20;
        g.drawString("HIGHSCORE:",rightColumnX+100, rightColumnY);
        g.drawString(model.getHighScore(),rightColumnX+120,rightColumnY+20);
        g.drawString("SCORE:",rightColumnX,rightColumnY);
        if(model.isMultiplayer()) {
            g.drawString("SCORE:",rightColumnX2,rightColumnY);
        }
        rightColumnY+=20;
        g.drawString(model.getScore(),rightColumnX,rightColumnY);
        if(model.isMultiplayer()) {
            g.drawString(model.getOpponentScore(),rightColumnX2,rightColumnY);
        }
        rightColumnY+=20;
        if(model.isEnd()) {
            if(endBlink<END_BLINK_SPEED) {
                if(!model.isCompleted()) {
                    g.drawString("GAME OVER", rightColumnX,rightColumnY);
                    rightColumnY+=20;
                    if(!model.isStarted()) {
                        rightColumnY+=20;
                        if(!model.isMultiplayer() || model.isOpponentConnected()) {
                            g.drawString("Press space for new game", rightColumnX, rightColumnY);
                        }else {
                            g.drawString("Press space or wait for opponent", rightColumnX, rightColumnY);
                        }
                        rightColumnY+=20;
                    }
                }else {
                    g.drawString("* CONGRATULATIONS *", rightColumnX, rightColumnY);
                    rightColumnY+=20;
                    g.drawString(" You have finished ", rightColumnX, rightColumnY);
                    rightColumnY+=20;
                    g.drawString("     the game      ", rightColumnX, rightColumnY);
                    rightColumnY+=20;
                    if(!model.isStarted()) {
                        rightColumnY+=20;
                        if(!model.isMultiplayer() || model.isOpponentConnected()) {
                            g.drawString("Press space for new game", rightColumnX, rightColumnY);
                        }else {
                            g.drawString("Press space or wait for opponent", rightColumnX, rightColumnY);
                        }
                        rightColumnY+=20;
                    }

                }
                endBlink++;
            }else {
                if(endBlink<(2*END_BLINK_SPEED)) {
                    endBlink++;
                }else {
                    endBlink=0;
                }
            }
        }else if(!model.isStarted()) {
            if(endBlink<END_BLINK_SPEED) {
                if(!model.isMultiplayer() || model.isOpponentConnected()) {
                    g.drawString("Press space for new game",rightColumnX, rightColumnY);
                }else {
                    g.drawString("Press space or wait for opponent",rightColumnX, rightColumnY);
                }
                rightColumnY+=20;
                endBlink++;
            }else {
                if(endBlink<(2*END_BLINK_SPEED)) {
                    endBlink++;
                }else {
                    endBlink=0;
                }
            }
        }
    }

    /**
     * Draw all grapics in a specific BlockMatrix
     * @param g The Graphics object to draw on
     * @param m The BlockMatix object to draw
     * @param offsetX The horisontal drawing offset
     * @param offsetY The vertical drawing offset
     * @param squareSize The size of each square in pixels
     * @param spaceBetweenSquares Number of pixels between each square
     * @param borderSize The size in pixels of the border around the drawn area
     * @param borderColor The color of the border
     * @param gridColor The color of the grid
     */
    protected void drawContainer(Graphics g, BlockMatrix m, int offsetX, int offsetY, int squareSize, int spaceBetweenSquares, int borderSize, Color borderColor, Color gridColor)
    {
        // Draw block container
        if(borderSize>=0) {
            g.setColor(borderColor);
            g.fillRect(offsetX, offsetY, borderSize, (squareSize+spaceBetweenSquares)*m.getHeight()-spaceBetweenSquares+borderSize);
            g.fillRect(offsetX, offsetY, (squareSize+spaceBetweenSquares)*m.getWidth()-spaceBetweenSquares+borderSize, borderSize);
            g.fillRect(offsetX+borderSize+(squareSize+spaceBetweenSquares)*m.getWidth()-spaceBetweenSquares, offsetY,borderSize,(squareSize+spaceBetweenSquares)*m.getHeight()-spaceBetweenSquares+borderSize*2);
            g.fillRect(offsetX, offsetY+borderSize+(squareSize+spaceBetweenSquares)*m.getHeight()-spaceBetweenSquares,(squareSize+spaceBetweenSquares)*m.getWidth()-spaceBetweenSquares+borderSize*2,borderSize);
        }
        if(spaceBetweenSquares>0) {
            g.setColor(gridColor);
            for(int x=1;x<m.getWidth();x++) {
                g.fillRect(offsetX + borderSize + x*(squareSize+spaceBetweenSquares)-spaceBetweenSquares,
                            offsetY + borderSize,
                            spaceBetweenSquares,
                            m.getHeight()*(squareSize+spaceBetweenSquares)-spaceBetweenSquares);
            }
            for(int y=1;y<m.getHeight();y++) {
                g.fillRect(offsetX + borderSize,
                            offsetY + borderSize + y*(squareSize+spaceBetweenSquares)-spaceBetweenSquares,
                            m.getWidth()*(squareSize+spaceBetweenSquares)-spaceBetweenSquares,
                            spaceBetweenSquares);
            }
        }
        for(int x=0;x<m.getWidth();x++) {
            for(int y=0;y<m.getHeight();y++) {
                if(m.isUsed(x,y)) {
                    g.setColor(m.getColor(x,y));
                    g.fillRect(offsetX +borderSize+ x*(squareSize+spaceBetweenSquares),offsetY +borderSize+ y*(squareSize+spaceBetweenSquares),squareSize, squareSize);
                }
            }
        }
    }
    protected void updatePreviewContainer() {
        previewContainer.clear();
        Block b = model.getNextBlock();
        if(b!=null) {
            b.init(1,0,0);
            b.moveDown(previewContainer);
        }
    }
}
