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

import erland.util.ImageHandlerInterface;
import erland.util.ImageCreatorInterface;
import erland.util.ParameterValueStorageExInterface;

/**
 * An adapter class to simplify implementation of new {@link GameEnvironmentInterface} classes
 * @author Erland Isaksson
 */
public class GameEnvironmentCustomizable implements GameEnvironmentInterface {
    /** The custom game environment to use */
    private GameEnvironmentInterface environment;
    /** The custom image handler to use */
    private ImageHandlerInterface imageHandler;
    /** The custom image creator to use */
    private ImageCreatorInterface imageCreator;
    /** The custom screen handler to use */
    private ScreenHandlerInterface screenHandler;
    /** The custom storage to use */
    private ParameterValueStorageExInterface storage;
    /** The custom highscore object to use */
    private HighScoreInterface highScore;
    /** The custom highscore list object to use */
    private HighScoreListInterface highScoreList;

    /**
     * Creates a new instance using the specified game environment
     * @param environment Game environment object
     */
    public GameEnvironmentCustomizable(GameEnvironmentInterface environment) {
        this.environment = environment;
    }
    /**
     * Set a custom image handler
     * @param imageHandler The custom image handler
     */
    public void setImageHandler(ImageHandlerInterface imageHandler) {
        this.imageHandler = imageHandler;
    }

    /**
     * Set a custom image creator
     * @param imageCreator The custom image creator
     */
    public void setImageCreator(ImageCreatorInterface imageCreator) {
        this.imageCreator = imageCreator;
    }

    /**
     * Set a custom screen handler
     * @param screenHandler The custom screen handler
     */
    public void setScreenHandler(ScreenHandlerInterface screenHandler) {
        this.screenHandler = screenHandler;
    }
    /**
     * Set a custom storage object
     * @param storage The custom storage object
     */
    public void setStorage(ParameterValueStorageExInterface storage) {
        this.storage = storage;
    }
    /**
     * Set a custom highscore object
     * @param highScore The custom highscore object
     */
    public void setHighScore(HighScoreInterface highScore) {
        this.highScore = highScore;
    }

    /**
     * Set a custom highscore list object
     * @param highScoreList The custom highscore list object
     */
    public void setHighScoreList(HighScoreListInterface highScoreList) {
        this.highScoreList = highScoreList;
    }

    public ImageHandlerInterface getImageHandler() {
        if(imageHandler!=null) {
            return imageHandler;
        }else {
            return environment.getImageHandler();
        }
    }

    public ImageCreatorInterface getImageCreator() {
        if(imageCreator!=null) {
            return imageCreator;
        }else {
            return environment.getImageCreator();
        }
    }

    public ScreenHandlerInterface getScreenHandler() {
        if(screenHandler!=null) {
            return screenHandler;
        }else {
            return environment.getScreenHandler();
        }
    }

    public ParameterValueStorageExInterface getStorage() {
        if(storage!=null) {
            return storage;
        }else {
            return environment.getStorage();
        }
    }

    public HighScoreInterface getHighScore() {
        if(highScore!=null) {
            return highScore;
        }else {
            return environment.getHighScore();
        }
    }

    public HighScoreListInterface getHighScoreList() {
        if(highScoreList!=null) {
            return highScoreList;
        }else {
            return environment.getHighScoreList();
        }
    }
}
