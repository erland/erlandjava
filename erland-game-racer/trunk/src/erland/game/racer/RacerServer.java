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

import erland.util.*;
import erland.game.*;

/**
 * Main class for the server of the networked version of the game
 */
public class RacerServer extends GameServer implements GameEnvironmentInterface {
    /** Storage object */
    private ParameterValueStorageExInterface storage;
    /** Car control manager for computer controlled cars */
    private CarControlManager computerCarControlManager;
    /** Racer model */
    private RacerModel model;
    /** Computer cars */
    private Car[] computerCars;
    /** String buffer for sending updates to client */
    private StringBuffer carUpdateSB;
    /** Counter that is used if client shouldn't be updated every frame */
    private int updateCounter;
    /** Number of frames between each client update */
    private int updateInterval;

    /**
     * Starts the server
     * @param args noOfFramesBetweenClientUpdates
     */
    public static void main(String[] args) {
        RacerServer me;
        if(args.length>0) {
            me = new RacerServer(Integer.valueOf(args[0]).intValue());
        }else {
            me = new RacerServer(5);
        }
        me.run(1123);
    }
    /**
     * Creates racer server object
     * @param updateInterval Number of frames between each client update
     */
    public RacerServer(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    protected int getMaxPlayers() {
        return 4;
    }

    protected GamePlayerInterface createPlayer() {
        return new RacerPlayer(this,getNoOfPlayers());
    }

    protected void disconnected(GamePlayerInterface player) {
        if(player!=null) {
            sendCarData();
        }
    }

    /**
     * Sends map to the specified player
     * @param me The player to send map to
     */
    public void sendMap(GamePlayerInterface me) {
        String mapString = getStorage().toString();
        me.getConnection().write(mapString+"MAP");
    }

    /**
     * Sends all cars to all clients
     */
    public void sendCarData() {
        StringBuffer sb=new StringBuffer(7+25*(getNoOfPlayers()+computerCars.length));
        GamePlayerInterface[] players = getPlayers();
        for (int i = 0; i < players.length; i++) {
            RacerPlayer p = (RacerPlayer) players[i];
            p.getCar().setId(i);
            sb.append(p.getCar().getId());
            StringUtil.addFixLength(sb,p.getCar().getPosX(),3);
            StringUtil.addFixLength(sb,p.getCar().getPosY(),3);
            StringUtil.addFixLength(sb,(int)p.getCar().getCarAngle(),3);
            StringUtil.addFixLength(sb,p.getCar().getImage(),15,true);
        }

        for(int i=0, j=getNoOfPlayers();i<computerCars.length;i++,j++) {
            computerCars[i].setId(j);
            sb.append(computerCars[i].getId());
            StringUtil.addFixLength(sb,computerCars[i].getPosX(),3);
            StringUtil.addFixLength(sb,computerCars[i].getPosY(),3);
            StringUtil.addFixLength(sb,(int)computerCars[i].getCarAngle(),3);
            StringUtil.addFixLength(sb,computerCars[i].getImage(),15,true);
        }
        sb.append("CARDATA");

        for (int i = 0; i < players.length; i++) {
            RacerPlayer p = (RacerPlayer) players[i];
            p.getConnection().write(String.valueOf(players.length+computerCars.length)+String.valueOf(players.length)+String.valueOf(p.getCar().getId())+sb.toString());
        }
    }
    /**
     * Get string with updated car positions that can be sent to clients
     * @return
     */
    public String getCarUpdateString() {
        if(carUpdateSB==null) {
            carUpdateSB=new StringBuffer(10+10*(getNoOfPlayers()+computerCars.length));
        }else {
            carUpdateSB.setLength(0);
        }
        GamePlayerInterface[] players = getPlayers();
        for (int i = 0; i < players.length; i++) {
            RacerPlayer p = (RacerPlayer) players[i];
            carUpdateSB.append(p.getCar().getId());
            StringUtil.addFixLength(carUpdateSB,p.getCar().getPosX(),3);
            StringUtil.addFixLength(carUpdateSB,p.getCar().getPosY(),3);
            StringUtil.addFixLength(carUpdateSB,(int)p.getCar().getCarAngle(),3);
            StringUtil.addFixLength(carUpdateSB,(int)p.getCar().getMovingAngle(),3);
            StringUtil.addFixLength(carUpdateSB,(int)p.getCar().getSpeed()*100,3);
        }
        for(int i=0;i<computerCars.length;i++) {
            carUpdateSB.append(computerCars[i].getId());
            StringUtil.addFixLength(carUpdateSB,computerCars[i].getPosX(),3);
            StringUtil.addFixLength(carUpdateSB,computerCars[i].getPosY(),3);
            StringUtil.addFixLength(carUpdateSB,(int)computerCars[i].getCarAngle(),3);
            StringUtil.addFixLength(carUpdateSB,(int)computerCars[i].getMovingAngle(),3);
            StringUtil.addFixLength(carUpdateSB,(int)computerCars[i].getSpeed()*100,3);
        }
        carUpdateSB.append("CARSUPDATE");
        return carUpdateSB.toString();
    }

    protected void initPlayer(GamePlayerInterface player) {
        initCars();
        sendMap(player);
        sendCarData();
    }

    protected void command(GamePlayerInterface player, String message) {
        if(message.equals("STARTTURNRIGHT")) {
            ((RacerPlayer)player).startTurnRight();
        }else if(message.equals("STARTTURNLEFT")) {
            ((RacerPlayer)player).startTurnLeft();
        }else if(message.equals("STARTACCELLERATING")) {
            ((RacerPlayer)player).startAccellerating();
        }else if(message.equals("STARTBRAKING")) {
            ((RacerPlayer)player).startBraking();
        }else if(message.equals("STOPTURNRIGHT")) {
            ((RacerPlayer)player).stopTurnRight();
        }else if(message.equals("STOPTURNLEFT")) {
            ((RacerPlayer)player).stopTurnLeft();
        }else if(message.equals("STOPACCELLERATING")) {
            ((RacerPlayer)player).stopAccellerating();
        }else if(message.equals("STOPBRAKING")) {
            ((RacerPlayer)player).stopBraking();
        }
    }


    /**
     * Creates computer controlled cars
     * @param noOfCars Number of computer controlled cars to create
     * @return The newly created cars
     */
    private Car[] initComputerCars(int noOfCars) {
        Car[] computerCars = new Car[noOfCars];
        for(int i=0;i<computerCars.length;i++) {
            computerCars[i] = CarFactory.createServerCar(this,null,i+getNoOfPlayers(),i);
            computerCars[i].setId(i);
        }
        return computerCars;
    }


    public boolean initGame() {
        model = new RacerModel();
        BlockContainerInterface cont = new BlockContainerData(20, 20, 30,30, 32, 15,12);
        model.init(this,cont);
        return true;
    }

    /**
     * Initialize cars
     */
    public void initCars() {
        computerCars = initComputerCars(4-getNoOfPlayers());
        Car cars[] = new Car[getNoOfPlayers()+computerCars.length];
        computerCarControlManager = new CarControlManager();
        CarScanDataContainerStandard computerCarScanData = new CarScanDataContainerStandard();

        GamePlayerInterface[] players = getPlayers();
        for (int i = 0; i < players.length; i++) {
            RacerPlayer p = (RacerPlayer) players[i];
            cars[i] = p.getCar();
            computerCarControlManager.addCollisionCar(cars[i]);
            p.setFrictionObjectContainer(model);
        }

        for(int i= getNoOfPlayers(),j=0;i<cars.length;i++,j++) {
            cars[i] = computerCars[j];
            computerCarControlManager.addCar(computerCars[j], computerCarScanData);
            computerCarControlManager.addCollisionCar(cars[i]);
            computerCarControlManager.setFrictionObjectContainer(model);
        }
        model.initCars(cars);
    }


    protected void updatePlayer(GamePlayerInterface player) {
        RacerPlayer p = (RacerPlayer) player;
        p.controlCars();
    }

    protected void updateGame() {
        computerCarControlManager.controlCars();

        model.update();

        updateCounter++;
        if(updateCounter>=updateInterval) {
            updateCounter=0;
            writeMessage(getCarUpdateString());
        }
    }

    public ParameterValueStorageExInterface getStorage() {
        if(storage==null) {
            JarFileStorage jarfile = new JarFileStorage("racer.xml");
            FileStorage file = new FileStorage("racer.xml");
            storage = new ParameterStorageString(file,jarfile,"racer");
        }
        return storage;
    }

    public ImageHandlerInterface getImageHandler() {
        return null;
    }

    public ImageCreatorInterface getImageCreator() {
        return null;
    }

    public ScreenHandlerInterface getScreenHandler() {
        return null;
    }
}

