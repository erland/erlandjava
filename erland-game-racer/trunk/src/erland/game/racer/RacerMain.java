package erland.game.racer;

import erland.game.*;
import erland.util.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.util.Vector;

public class RacerMain implements GamePanel,FrictionObjectContainerInterface {
    protected BlockContainerData cont;
    protected int speed = 3;
    protected boolean bUp = false;
    protected boolean bDown = false;
    protected boolean bRight = false;
    protected boolean bLeft = false;
    protected Car car;
    protected Car cars[];
    protected boolean bQuit;
    protected KeyListener keyListener;
    protected FpsCounter fps;
    protected Block[][] blocks;
    protected BlockMap map;
    protected GameEnvironmentInterface environment;
    protected CollisionManager collisionManager;
    protected FrictionManager frictionManager;
    protected FrictionObjectInterface[] frictionObjects;

    public boolean isExit()
    {
        return bQuit;
    }

    public void exit()
    {
        environment.getScreenHandler().getFrame().removeKeyListener(keyListener);
    }
    public void init(GameEnvironmentInterface environment)
    {
        this.environment = environment;
        bQuit = false;

        keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    bQuit = true;
                }else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    bLeft = true;
                }else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    bRight = true;
                }else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    bUp = true;
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    bDown = true;
                }
            }
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    bLeft = false;
                }else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    bRight = false;
                }else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    bUp = false;
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    bDown = false;
                }
            }

        };
        environment.getScreenHandler().setCursor(null);
        environment.getScreenHandler().getFrame().requestFocus();
        environment.getScreenHandler().getFrame().addKeyListener(keyListener);

        fps = new FpsCounter(50);
        cont = new BlockContainerData(20, 20, 30,30, 32, 15,12);
        map = new BlockMap();

        blocks = new Block[cont.getSizeX()][cont.getSizeY()];


        LevelFactoryInterface levelFactoryGameIcon = new RacerLevelFactoryGameIcon(new BlockClone());
        LevelManager iconManager = new LevelManager();
        iconManager.init(environment,levelFactoryGameIcon,"mapicons","mapicon");
        iconManager.setContainer(cont);

        LevelFactoryInterface levelFactoryBlocks = new RacerLevelFactoryGroup(iconManager,new BlockClone(),3,3);
        LevelManager blockManager = new LevelManager();
        blockManager.init(environment,levelFactoryBlocks,"mapblocks","mapblock");
        blockManager.setContainer(cont);

        LevelFactoryInterface levelFactoryGroup = new RacerLevelFactoryGroup(blockManager,new BlockClone(),cont.getScrollingSizeX(),cont.getScrollingSizeY());
        LevelManager levelManager = new LevelManager();
        levelManager.init(environment,levelFactoryGroup,"levels","level");
        levelManager.setContainer(cont);
        MapEditorBlockInterface mapBlocks[][] = levelManager.getLevel(1);
        for(int x=0;x<mapBlocks.length;x++) {
            for(int y=0;y<mapBlocks[x].length;y++) {
                if(x>=0 && x<blocks.length && y>=0 && y<blocks[0].length) {
                    blocks[x][y]=(Block)mapBlocks[x][y];
                }
            }
        }


        map.setContainer(cont);
        map.setMapData(blocks);

        collisionManager = new CollisionManager();
        frictionManager = new FrictionManager(this);
        car = new Car();
        car.init(environment);
        car.setContainer(cont);
        car.setImage("car.gif");
        car.setCapabilities(2.5,0.015,0.05,2,0.01,1.75);
        car.setPos(100,100);
        car.setAngle(0);
        collisionManager.addObject(car);
        frictionManager.addObject(car);

        cars = new Car[2];
        for(int i=0;i<cars.length;i++) {
            cars[i] = new Car();
            cars[i].init(environment);
            cars[i].setContainer(cont);
            cars[i].setImage("car.gif");
            cars[i].setCapabilities(1.5,0.015,0.05,0.75,0.01,0.75);
            cars[i].setPos(200+i*80,100+i*10);
            cars[i].setAngle(i*5);
            collisionManager.addObject(cars[i]);
            frictionManager.addObject(cars[i]);
        }
        frictionObjects = new FrictionObjectInterface[16*9];
        environment.getScreenHandler().getFrame().setBackground(Color.black);

        //getFrictionObjects(car);
        //bQuit = true;
    }

    public void update() {
        for(int i=0;i<cars.length;i++) {
            cars[i].accelerating();
            cars[i].turnRight();
            cars[i].update();
        }
        if(bLeft) {
            car.turnLeft();
        }else if(bRight) {
            car.turnRight();
        }
        if(bUp) {
            car.accelerating();
        }else if(bDown) {
            car.breaking();
        }else {
            car.slowdown();
        }
        car.update();

        frictionManager.handleFrictions();
        collisionManager.handleCollisions();
        // Scroll map
        int scrollBorderLeft = 200;
        int scrollBorderRight = 200;
        int scrollBorderUp = 200;
        int scrollBorderDown = 200;
        if(car.getPosX()<(cont.getScrollingOffsetX()+scrollBorderLeft)) {
            cont.setScrollingOffsetX(car.getPosX()-scrollBorderLeft);
        }
        if(car.getPosX()>(cont.getScrollingOffsetX()+cont.getDrawingSizeX()-scrollBorderRight)) {
            cont.setScrollingOffsetX(car.getPosX()-cont.getDrawingSizeX()+scrollBorderRight);
        }
        if(car.getPosY()<(cont.getScrollingOffsetY()+scrollBorderUp)) {
            cont.setScrollingOffsetY(car.getPosY()-scrollBorderUp);
        }
        if(car.getPosY()>(cont.getScrollingOffsetY()+cont.getDrawingSizeY()-scrollBorderDown)) {
            cont.setScrollingOffsetY(car.getPosY()-cont.getDrawingSizeY()+scrollBorderDown);
        }

        // Make sure we don't scroll the map to much
        if(cont.getScrollingOffsetX()<0) {
            cont.setScrollingOffsetX(0);
        }
        if(cont.getScrollingOffsetX()>cont.getScrollingSizeX()-cont.getDrawingSizeX()) {
            cont.setScrollingOffsetX(cont.getScrollingSizeX()-cont.getDrawingSizeX());
        }
        if(cont.getScrollingOffsetY()<0) {
            cont.setScrollingOffsetY(0);
        }
        if(cont.getScrollingOffsetY()>cont.getScrollingSizeY()-cont.getDrawingSizeY()) {
            cont.setScrollingOffsetY(cont.getScrollingSizeY()-cont.getDrawingSizeY());
        }

    }

    public void draw()
    {
        Graphics g = environment.getScreenHandler().getCurrentGraphics();
        g.clearRect(0,0,environment.getScreenHandler().getWidth(),environment.getScreenHandler().getHeight());
        fps.update();
        fps.draw(g,Color.red, 10,10);
        g.setColor(Color.white);
        g.drawString(String.valueOf(car.friction),10,30);
        map.draw(g,0);
        car.draw(g,0);
        for(int i=0;i<cars.length;i++) {
            cars[i].draw(g,0);
        }
        /*
        g.setColor(Color.white);
        g.drawRect(cont.getPixelDrawingPositionX((int)car.getBounds().getX()),
        cont.getPixelDrawingPositionY((int)car.getBounds().getY()),
        (int)car.getBounds().getWidth(),
        (int)car.getBounds().getHeight());
        */
        /*
        FrictionObjectInterface[] frictions = getFrictionObjects(car);
        for(int i=0;i<frictions.length && frictions[i]!=null;i++) {
            if(frictions[i].getFriction()>0) {
                g.setColor(Color.gray);
            }else {
                g.setColor(Color.white);
            }
            g.drawRect(cont.getPixelDrawingPositionX((int)frictions[i].getBounds().getX()),
            cont.getPixelDrawingPositionY((int)frictions[i].getBounds().getY()),
            (int)frictions[i].getBounds().getWidth(),
            (int)frictions[i].getBounds().getHeight());
        }
        */
    }

    public FrictionObjectInterface[] getFrictionObjects(FrictionSensitiveInterface obj) {
        if(obj instanceof Car) {
            Car c = (Car)obj;
            int objX = c.getPosX();
            int objY = c.getPosY();
            int blockX = objX/cont.getSquareSize();
            int blockY = objY/cont.getSquareSize();
            int i=0;
            for(int x=blockX-1;x<=blockX+1;x++) {
                for(int y=blockY-1;y<=blockY+1;y++) {
                    if(x>=0 && x<blocks.length && y>=0 && y<blocks[0].length) {
                        //Log.println(this,"Get friction for: "+x+","+y+","+objX+","+objY);
                        FrictionObjectInterface[] frictions = blocks[x][y].getFrictionObjects();
                        for(int j=0;j<frictions.length;j++) {
                            frictionObjects[i++] = frictions[j];
                            //Log.println(this,"getFrictionObjects: "+String.valueOf(frictions[j].getFriction())+","+((FrictionBlock)frictions[j]).offsetX+","+((FrictionBlock)frictions[j]).offsetY+","+((FrictionBlock)frictions[j]).x+","+((FrictionBlock)frictions[j]).y+","+((FrictionBlock)frictions[j]).squareSize);
                            //Log.println(this,"getFrictionObjects2: "+String.valueOf(frictions[j].getFriction())+","+frictions[j].getBounds().getX()+","+frictions[j].getBounds().getY()+","+frictions[j].getBounds().getWidth()+","+frictions[j].getBounds().getHeight());
                        }
                    }
                }
            }
            for(;i<16*9;i++) {
                frictionObjects[i]=null;
            }
        }
        return frictionObjects;
    }
}
