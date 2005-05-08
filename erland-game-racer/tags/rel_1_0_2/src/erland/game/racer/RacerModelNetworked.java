package erland.game.racer;
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

import erland.game.*;
import erland.network.NetworkConnectionInterface;
import erland.network.NetworkClientListenerInterface;
import erland.network.NetworkClient;
import erland.util.*;

/**
 * Implementation of a networked version of the {@link RacerModelInterface}
 */
public class RacerModelNetworked implements RacerModelInterface, NetworkClientListenerInterface {
    /** Opponent cars */
    private CarClientInterface[] opponentCars;
    /** The user car */
    private CarClientInterface myCar;
    /** Game environment */
    private GameEnvironmentInterface environment;
    /** Block container for the game area */
    private BlockContainerInterface cont;
    /** Network connection to server */
    private NetworkConnectionInterface connection;
    /** Block map */
    private BlockMapDrawInterface map;
    /** Indicates if game is started */
    private boolean bStarted;
    /** Indicates if game is initialized */
    private boolean bInitialized;
    /** Hostname of the server */
    private String hostname;
    /** User name to use when logging in to server */
    private String username;
    /** Password to use when logging in to server */
    private String password;
    /** Initicates if an update has been received from server since last client update */
    private boolean bUpdateFromServer;
    /** Indicates if the user is turning left */
    private boolean bTurnLeft;
    /** Indicates if the user is turning right */
    private boolean bTurnRight;
    /** Indicates if the user is accellerating */
    private boolean bAccellerating;
    /** Indicates if the user is braking */
    private boolean bBraking;
    /** Number of human controlled cars */
    private int noOfHumanCars;
    /** FPS counter */
    private FpsCounter fps;

    /**
     * Create networked model object
     * @param hostname Hostname to server
     * @param username User name to use when logging in
     * @param password Password to use when logging in
     */
    RacerModelNetworked(String hostname, String username, String password) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        fps = new FpsCounter(60);
    }

    public void startTurnLeft() {
        if(!bTurnLeft) {
            connection.write("STARTTURNLEFT");
            bTurnLeft = true;
        }
    }

    public void startTurnRight() {
        if(!bTurnRight) {
            connection.write("STARTTURNRIGHT");
            bTurnRight = true;
        }
    }

    public void startAccellerating() {
        if(!bStarted) {
            start();
        }
        if(!bAccellerating) {
            connection.write("STARTACCELLERATING");
            bAccellerating = true;
        }
    }

    public void startBraking() {
        if(!bBraking) {
            connection.write("STARTBRAKING");
            bBraking = true;
        }
    }

    public void stopTurnLeft() {
        connection.write("STOPTURNLEFT");
        bTurnLeft = false;
    }

    public void stopTurnRight() {
        connection.write("STOPTURNRIGHT");
        bTurnRight = false;
    }

    public void stopAccellerating() {
        connection.write("STOPACCELLERATING");
        bAccellerating = false;
    }

    public void stopBraking() {
        connection.write("STOPBRAKING");
        bBraking = false;
    }

    public void update() {
        if(isStarted()) {
            if(!bUpdateFromServer) {
                for(int i=0;i<opponentCars.length;i++) {
                    opponentCars[i].clientUpdate();
                }
                myCar.clientUpdate();
                //System.out.println("CLIENTUPDATE "+myCar.getPosX()+","+myCar.getPosY());
            }else {
                bUpdateFromServer=false;
                for(int i=0;i<opponentCars.length;i++) {
                    opponentCars[i].serverUpdate();
                }
                myCar.serverUpdate();
            }
        }
    }

    public void init(GameEnvironmentInterface environment, BlockContainerInterface cont) {
        this.environment = environment;
        this.cont = cont;
        myCar = null;
        opponentCars = null;
        noOfHumanCars = 0;
        this.connection = NetworkClient.connect(hostname,1123,5,username,password,this);
        connection.write("INIT");
    }

    public void start() {
        connection.write("START");
    }

    public CarDrawInterface[] getOpponentCars() {
        return opponentCars;
    }

    public CarDrawInterface getMyCar() {
        return myCar;
    }

    public BlockMapDrawInterface getMap() {
        return map;
    }

    public boolean isEnd() {
        return false;
    }

    public boolean isGameOver() {
        return false;
    }

    public boolean isCompleted() {
        return false;
    }

    public boolean isStarted() {
        return bStarted;
    }
    public boolean isInitialized() {
        return bInitialized;
    }

    public void connected(NetworkConnectionInterface connection) {
        System.out.println("RacerModelNetworked.connected");
    }

    public void disconnected(NetworkConnectionInterface connection) {
        System.out.println("RacerModelNetworked.disconnected");
    }

    private void addCar(CarClientInterface car) {
        for(int i=0;i<opponentCars.length;i++) {
            if(opponentCars[i]==null) {
                opponentCars[i]=car;
                return;
            }
        }
    }

    private int findCar(int carId) {
        if(opponentCars!=null) {
            for(int i=0;i<opponentCars.length;i++) {
                if(opponentCars[i]!=null && opponentCars[i].getId()==carId) {
                    return i;
                }
            }
        }
        return -1;
    }
    public void message(NetworkConnectionInterface connection, String message) {
        //System.out.println("Got: "+message);
        if(message.endsWith("MAP")) {
            bInitialized = false;
            StringStorage stringStorage = new StringStorage(message.substring(0,message.length()-3));
            ParameterValueStorageExInterface storage = new ParameterStorageString(stringStorage,null,"racer");
            GameEnvironmentCustomizable env = new GameEnvironmentCustomizable(environment);
            env.setStorage(storage);
            map = BlockMapFactory.create(env,cont,1);
        }else if(message.endsWith("CARDATA")) {
            bInitialized = false;
            int myCarId = message.charAt(2)-'0';
            int noOfCars = message.charAt(0)-'0';
            noOfHumanCars = message.charAt(1)-'0';
            opponentCars = new CarClientInterface[noOfCars-1];
            int lastChar = message.length()-9;
            for(int i=3;i<lastChar;i+=25) {
                int carId = message.charAt(i)-'0';
                if(carId==myCarId) {
                    myCar = CarFactory.createClientCar(environment,cont,message,i+1,i+25);
                    myCar.setId(carId);
                }else {
                    CarClientInterface car = CarFactory.createClientCar(environment,cont,message,i+1,i+25);
                    car.setId(carId);
                    addCar(car);
                }
            }
            bInitialized = true;
        }else if(message.endsWith("CARSUPDATE")) {
            fps.update();
            int lastChar = message.length()-10;
            for(int i=0;i<lastChar;i+=16) {
                int carId = message.charAt(i)-'0';
                int x = (int)StringUtil.getNumber(message,i+1,i+4);
                int y = (int)StringUtil.getNumber(message,i+4,i+7);
                int carAngle = (int)StringUtil.getNumber(message,i+7,i+10);
                int movingAngle = (int)StringUtil.getNumber(message,i+10,i+13);
                double speed = StringUtil.getNumber(message,i+13,i+16)/100.0;
                if(myCar!=null && myCar.getId()==carId) {
                    myCar.setServerPos(x,y);
                    myCar.setServerCarAngle(carAngle);
                    myCar.setServerMovingAngle(carAngle);
                    myCar.setServerSpeed(speed);
                }else {
                    int pos = findCar(carId);
                    if(pos>=0) {
                        opponentCars[pos].setServerPos(x,y);
                        opponentCars[pos].setServerCarAngle(carAngle);
                        opponentCars[pos].setServerMovingAngle(movingAngle);
                        opponentCars[pos].setServerSpeed(speed);
                    }
                }
            }
            //System.out.println("SERVERUPDATE "+myCar.getPosX()+","+myCar.getPosY());
            bUpdateFromServer=true;
        }else if(message.equals("STARTED")) {
            bStarted = true;
        }
    }

    public int getNoOfHumanCars() {
        return noOfHumanCars;
    }

    public boolean isMultiplayer() {
        return true;
    }

    public String getInfoString() {
        return fps.getFps();
    }

    public void setCheatModeParameter(String parameter, String value) {
        if(parameter.substring(0,5).equals("MYCAR")) {
            myCar.setCheatParameter(parameter.substring(5),value);
        }else if(parameter.substring(0,7).equals("ALLCARS")) {
            myCar.setCheatParameter(parameter.substring(7),value);
            for (int i = 0; i < opponentCars.length; i++) {
                CarClientInterface car = opponentCars[i];
                car.setCheatParameter(parameter.substring(7),value);
            }
        }
    }
}
