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

import erland.network.NetworkConnectionInterface;
import erland.game.GameEnvironmentInterface;
import erland.game.GamePlayerInterface;

/**
 * Implementation of control of the user car
 */
public class RacerPlayer implements GamePlayerInterface, CarControlManagerInterface {
    /** Connection to client if networked game */
    private NetworkConnectionInterface connection;
    /** User car object */
    private Car car;
    /** Indicates if the user is turning right */
    private boolean bTurnRight;
    /** Indicates if the user is turning left */
    private boolean bTurnLeft;
    /** Indicates if the user is accellerating */
    private boolean bAccellerating;
    /** Indicates if the user is braking */
    private boolean bBraking;

    /**
     * Create player object
     * @param environment Game environment
     * @param playerNo Player number (if networked game)
     */
    public RacerPlayer(GameEnvironmentInterface environment, int playerNo) {
        this.car = CarFactory.createServerCar(environment,null,playerNo,-1);
    }

    public void setConnection(NetworkConnectionInterface connection) {
        this.connection = connection;
    }

    /**
     * Get car object
     * @return Car object
     */
    public Car getCar() {
        return car;
    }
    public NetworkConnectionInterface getConnection() {
        return connection;
    }
    /** Start turn right */
    public void startTurnRight() {
        bTurnRight = true;
    }
    /** Start turn left */
    public void startTurnLeft() {
        bTurnLeft = true;
    }
    /** Start accellerating */
    public void startAccellerating() {
        bAccellerating = true;
    }
    /** Start braking */
    public void startBraking() {
        bBraking = true;
    }
    /** Stop turn right */
    public void stopTurnRight() {
        bTurnRight = false;
    }
    /** Stop turn left*/
    public void stopTurnLeft() {
        bTurnLeft = false;
    }
    /** Stop accellerating */
    public void stopAccellerating() {
        bAccellerating = false;
    }
    /** Stop braking */
    public void stopBraking() {
        bBraking = false;
    }

    public void controlCars() {
        if(bTurnLeft) {
            car.turnLeft();
        }else if(bTurnRight) {
            car.turnRight();
        }
        if(bAccellerating) {
            car.accelerating();
        }else if(bBraking) {
            car.braking();
        }else {
            car.slowdown();
        }
    }

    /**
     * Update player
     */
    public void update() {
        car.serverUpdate();
    }
    /**
     * Check if player has completed game
     * @return true if game is completed
     */
    public boolean isCompleted() {
        return false;
    }

    /**
     * Check if game is over for player
     * @return true if game is over for player
     */
    public boolean isGameOver() {
        return false;
    }

    public void setFrictionObjectContainer(FrictionObjectContainerInterface frictionContainer) {
        // Do nothing, the user takes care of changing course when there is something in the way
    }
}
