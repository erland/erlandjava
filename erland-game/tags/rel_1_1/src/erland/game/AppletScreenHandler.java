package erland.game;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import java.awt.*;
import java.applet.Applet;

import erland.util.ImageCreatorInterface;


/**
 * Object that represents a full screen window
 * @author Erland Isaksson
 */
public class AppletScreenHandler implements ScreenHandlerInterface, ImageCreatorInterface {
   private Graphics lastGraphics;
   private int screenTopLeftX;
   private int screenTopLeftY;
   private int screenWidth;
   private int screenHeight;
   private DisplayMode[] allowedDisplayModes;
   private Applet applet;
    /** Image object for the double buffering mechanism */
    protected Image imag;
    /** Graphics object for the double buffering mechanism */
    protected Graphics offScreen;

    /**
     * Creates new FullScreenHandler
     * @param displayModes List of displaymodes that is allowed
     */
    public AppletScreenHandler (Applet applet,DisplayMode[] displayModes) {
        init(applet,displayModes);
    }


    protected void init(Applet applet, DisplayMode[] displayModes) {
        this.applet = applet;
        allowedDisplayModes = displayModes;
        DisplayMode dm = getBestDisplayMode();
        screenWidth = dm.getWidth();
        screenHeight = dm.getHeight();
        applet.setSize(new Dimension(screenWidth,screenHeight));
        Insets ins = applet.getInsets();
        screenTopLeftX = ins.left;
        screenTopLeftY = ins.top;
    }


    public void add(Component c) {
        applet.add(c);
    }

    public void remove(Component c)
    {
        applet.remove(c);
    }

    public void paintComponents(Graphics g) {
        applet.paintComponents(g);
    }

    /**
     * Gets the best display mode by lookup available display modes
     * and choose among the allowed display modes reported in the
     * consturctor
     * @return The choosen DisplayMode
     */
    private DisplayMode getBestDisplayMode() {
        if(allowedDisplayModes.length>0) {
            return allowedDisplayModes[0];
        }else {
            return null;
        }
    }


    private void createOffScreen() {
        if(imag == null) {
            imag = createImage(screenWidth,screenHeight);
            offScreen = imag.getGraphics();
            offScreen.setColor(applet.getBackground());
        }
    }
    public Graphics getCurrentGraphics(){
        createOffScreen();
        lastGraphics = offScreen;
        lastGraphics.setClip(0, 0, screenWidth, screenHeight);
        lastGraphics.translate(screenTopLeftX, screenTopLeftY);
        return lastGraphics;
    }

    public boolean swapScreens(){
        createOffScreen();
        applet.getGraphics().drawImage(imag,0,0,null);
        return true;
    }

    public Container getContainer(){
        return applet;
    }

    public int getWidth() {
        return applet.getSize().width;
    }

    public int getHeight() {
        return applet.getSize().height;
    }

    public void dispose() {
        //TODO: Anything ???
    }

     public Image createVolatileImage() {
        // try ot get a volatile image the size of the screen
        return createImage();
    }

    public Image createImage() {
        // Creates a new Image with the size of the screen
        return applet.createImage(getWidth(),getHeight());
    }

    public void setCursor(Cursor cursor)
    {
        if(cursor != null) {
            applet.setCursor(cursor);
        }
    }

    public Image createVolatileImage(int width, int height) {
        return createImage(width,height);
    }

    public Image createImage(int width, int height) {
        return applet.createImage(width,height);
    }

    public Image createCompatibleImage(int width, int height,int transparency)
    {
        return createImage(width,height);
    }

    public Image createVolatileImage(Image image) {
        Image img = createImage(image.getWidth(applet),image.getHeight(applet));
        img.getGraphics().drawImage(image,0,0,applet);
        return img;
    }

    public int getScreenX(int x) {
        return x-screenTopLeftX;
    }

    public int getScreenY(int y) {
        return y-screenTopLeftY;
    }

    public boolean isExit() {
        return false;
    }
}



