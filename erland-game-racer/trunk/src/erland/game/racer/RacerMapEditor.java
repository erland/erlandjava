package erland.game.racer;

import erland.game.*;
import erland.util.*;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;

public class RacerMapEditor extends MapEditor {
    protected BlockContainerData cont;
    protected BlockContainerData contSelect;
    protected MapEditorBlockInterface[][] mapBlocks;
    protected MapEditorBlockInterface[][] selectBlocks;
    protected SpinnerNumberModel levelNoSpinner;
    protected int levelNo = 1;
    protected LevelManager levelManager;
    protected Image mapBlockImage;
    protected Image mapIconImage;


    public void init(GameEnvironmentInterface environment)
    {
        super.init(environment);
    }
    protected String getLevelFileLabel()
    {
        return "levels";
    }

    protected String getLevelFileGroupLabel()
    {
        return "level";
    }

    protected int getSizeX()
    {
        return 10;
    }
    protected int getSizeY()
    {
        return 10;
    }
    protected void initFinish()
    {
        levelManager = new LevelManager();
        mapBlockImage = environment.getImageCreator().createImage(256,256);
        LevelFactoryInterface levelFactoryLevel = new RacerLevelFactorySimple(new BlockTypeClone(getSelectBlocks()),cont.getSizeX(),cont.getSizeY());
        levelManager.init(environment,levelFactoryLevel,getLevelFileLabel(),getLevelFileGroupLabel());
        levelManager.setContainer(cont);

        // Create icon image with block bitmaps
        LevelManager iconLevelManager = new LevelManager();
        LevelFactoryInterface levelFactoryIcon = new RacerLevelFactoryGameIcon(new BlockClone());
        iconLevelManager.init(environment,levelFactoryIcon,"mapicons","mapicon");
        iconLevelManager.setContainer(new BlockContainerData(0,0,1,1,32));
        int i=0;
        boolean bLast = false;
        mapIconImage = environment.getImageCreator().createImage(256,256);
        Image imgTemp = environment.getImageCreator().createImage(cont.getSquareSize(),cont.getSquareSize());
        imgTemp.getGraphics().setColor(Color.black);
        mapIconImage.getGraphics().setColor(Color.black);
        mapIconImage.getGraphics().fillRect(-1,-1,mapIconImage.getWidth(null)+2,mapIconImage.getHeight(null)+2);
        while(!bLast) {
            MapEditorBlockInterface[][] blocks = iconLevelManager.getLevel(i+1);
            if(blocks!=null) {
                if(blocks[0][0]!=null) {
                    blocks[0][0].setPos(0,0);
                    imgTemp.getGraphics().fillRect(-1,-1,imgTemp.getWidth(null)+2,imgTemp.getHeight(null)+2);
                    drawBlock(imgTemp.getGraphics(),blocks[0][0]);
                    int posX = (i%8)*32;
                    int posY = (i/8)*32;
                    mapIconImage.getGraphics().drawImage(imgTemp,posX,posY,posX+cont.getSquareSize(),posY+cont.getSquareSize(),0,0,32,32,null);
                }
                i++;
            }else {
                bLast = true;
            }
        }

        BlockGroup[][] icons = new BlockGroup[i][1];
        for(int j=0;j<i;j++) {
            icons[j][0] = new BlockGroup();
            icons[j][0].init(environment);
            icons[j][0].setImage(mapIconImage,j);
            icons[j][0].setBlockType(j+1);
        }
        for(;i<8*8;i++) {
            int posX = (i%8)*32;
            int posY = (i/8)*32;
            mapIconImage.getGraphics().fillRect(posX,posY,32,32);
        }

        // Create zoomed out blocks
        LevelManager blockLevelManager = new LevelManager();
        LevelFactoryInterface levelFactoryBlock = new RacerLevelFactorySimple(new BlockTypeClone(icons),3,3);
        blockLevelManager.init(environment,levelFactoryBlock,"mapblocks","mapblock");
        blockLevelManager.setContainer(new BlockContainerData(0,0,1,1,32));
        i=0;
        bLast = false;
        imgTemp = environment.getImageCreator().createImage(cont.getSquareSize(),cont.getSquareSize());
        imgTemp.getGraphics().setColor(Color.black);
        mapBlockImage.getGraphics().setColor(Color.black);
        mapBlockImage.getGraphics().fillRect(-1,-1,mapBlockImage.getWidth(null)+2,mapBlockImage.getHeight(null)+2);
        while(!bLast) {
            MapEditorBlockInterface[][] blocks = blockLevelManager.getLevel(i+1);
            if(blocks!=null) {
                final int blockSize = 11;
                for(int x=0;x<blocks.length;x++) {
                    for(int y=0;y<blocks[0].length;y++) {
                        if(blocks[x][y]!=null) {
                            blocks[x][y].setPos(0,0);
                            imgTemp.getGraphics().fillRect(-1,-1,imgTemp.getWidth(null)+2,imgTemp.getHeight(null)+2);
                            drawBlock(imgTemp.getGraphics(),blocks[x][y]);
                            int posX = (i%8)*32+x*blockSize;
                            int posY = (i/8)*32+y*blockSize;
                            mapBlockImage.getGraphics().drawImage(imgTemp,posX,posY,posX+blockSize,posY+blockSize,0,0,32,32,null);

                        }
                    }
                }
                i++;
            }else {
                bLast = true;
            }
        }
        for(;i<8*8;i++) {
            int posX = (i%8)*32;
            int posY = (i/8)*32;
            mapBlockImage.getGraphics().fillRect(posX,posY,32,32);
        }

    }
    protected BlockContainerInterface getMapContainer(int offsetX, int offsetY)
    {
        if(cont==null) {
            cont = new BlockContainerData(offsetX+10,offsetY+10,getSizeX(),getSizeY(),32);
        }
        return cont;
    }

    protected int getSelectSizeX()
    {
        return 4;
    }
    protected int getSelectSizeY()
    {
        return 10;
    }
    protected BlockContainerInterface getSelectContainer(int offsetX, int offsetY)
    {
        if(contSelect==null) {
            contSelect = new BlockContainerData(offsetX+10+33*10,offsetY+10,getSelectSizeX(),getSelectSizeY(),32);
        }
        return contSelect;
    }
    protected MapEditorBlockInterface getSelectBlock(int blockNo)
    {
        BlockGroup b = new BlockGroup();
        b.init(environment);
        b.setImage(mapBlockImage,blockNo);
        b.setBlockType(blockNo+1);
        return b;
    }

    protected MapEditorBlockInterface[][] getSelectBlocks()
    {
        if(selectBlocks==null) {
            selectBlocks = new MapEditorBlockInterface[contSelect.getSizeX()][contSelect.getSizeY()];
            int i=0;
            for(int x=0;x<selectBlocks.length;x++) {
                for(int y=0;y<selectBlocks[0].length-1;y++) {
                    selectBlocks[x][y] = getSelectBlock(i);
                    if(selectBlocks[x][y]!=null) {
                        selectBlocks[x][y].setContainer(contSelect);
                        selectBlocks[x][y].setPos(x,y);
                    }
                    i++;
                }
            }
        }
        return selectBlocks;
    }

    protected MapEditorBlockInterface[][] getMapBlocks()
    {
        if(mapBlocks==null) {
            mapBlocks = new MapEditorBlockInterface[cont.getSizeX()][cont.getSizeX()];
            mapBlocks = levelManager.getLevel(getLevelNo());
            if(mapBlocks==null) {
                mapBlocks = new MapEditorBlockInterface[cont.getSizeX()][cont.getSizeY()];
            }
        }
        return mapBlocks;
    }


    protected void drawBlock(Graphics g, MapEditorBlockInterface block)
    {
        ((Block)block).draw(g,0);
    }

    protected MapEditorBlockInterface cloneBlock(MapEditorBlockInterface block,BlockContainerInterface cont, int x, int y)
    {
        Block b = (Block)block;
        Block newBlock = null;
        try {
            newBlock = (Block)b.clone();
            newBlock.setPos(x,y);
            newBlock.setContainer(cont);
        } catch (CloneNotSupportedException e) {
        }
        return newBlock;
    }

    protected boolean isEmptyAllowed()
    {
        return true;
    }

    protected String getLevelLabelText()
    {
        return "Level:";
    }
    protected int getMaxLevel()
    {
        return 10;
    }
    protected void initButtons(JPanel panel)
    {
        JPanel p = new JPanel(new FlowLayout());
        JTextField label = new JTextField(getLevelLabelText());
        label.setEditable(false);
        p.add(label);
        levelNoSpinner = new SpinnerNumberModel(levelNo,1,getMaxLevel(),1);
        levelNoSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                loadButton();
            }
        });
        JSpinner track = new JSpinner(levelNoSpinner);
        ((JFormattedTextField)(((JSpinner.DefaultEditor)(track.getEditor())).getTextField())).setEditable(false);
        p.add(track);
        panel.add(p);
    }
    protected int getLevelNo()
    {
        int no =0;
        try {
            no = levelNoSpinner.getNumber().intValue();
        } catch (NumberFormatException e) {
        }
        return no;
    }
    protected void saveButton(MapEditorBlockInterface[][] blocks)
    {
        levelManager.setLevel(getLevelNo(),blocks);
    }

    protected void loadButton()
    {
        mapBlocks = levelManager.getLevel(getLevelNo());
        updateBlocks();
    }
}
