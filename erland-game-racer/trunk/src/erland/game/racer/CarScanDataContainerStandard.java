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

/**
 * Implements standard scan data for computer controlled cars
 */
public class CarScanDataContainerStandard implements CarScanDataContainerInterface {
    /**
     * Scan data that should be used
     */
    private final CarScanData scanData[] = new CarScanData[] {
        new CarScanData(   0.0, 1.0,  CarScanData.NONE,  CarScanData.ACCELERATE,  CarScanData.CHOOSEONEIFSMALLEST, new CarScanData[] {
            new CarScanData( -15.0, 1.0,   CarScanData.LEFT, CarScanData.NONE,  CarScanData.NONE, null, new CarScanData[] {
                new CarScanData( -15.0, 0.75,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null),
                new CarScanData( -15.0, 0.5,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
            }),
            new CarScanData(  15.0, 1.0,  CarScanData.RIGHT, CarScanData.NONE,  CarScanData.NONE, null, new CarScanData[] {
                new CarScanData(  15.0, 0.75, CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null),
                new CarScanData(  15.0, 0.5, CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
            }),
            new CarScanData( -15.0, 0.5,  CarScanData.LEFT, CarScanData.NONE,  CarScanData.NONE, null, new CarScanData[] {
                new CarScanData(  -15.0, 0.25, CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
            }),
            new CarScanData(  15.0, 0.5, CarScanData.RIGHT, CarScanData.NONE,  CarScanData.NONE, null, new CarScanData[] {
                new CarScanData(   15.0, 0.25, CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
            }),
        }, new CarScanData[] {
            new CarScanData(   0.0, 0.75,  CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData(   0.0, 0.5,  CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData(   0.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData(  15.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData( -15.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData( -30.0, 1.0,  CarScanData.LEFT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData( -30.0, 0.5,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData( -30.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData(  30.0, 1.0, CarScanData.RIGHT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData(  30.0, 0.5,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData(  30.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData( -45.0, 1.0,  CarScanData.LEFT,      CarScanData.BREAK,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData( -45.0, 0.5,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData(  45.0, 1.0, CarScanData.RIGHT,      CarScanData.BREAK,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData(  45.0, 0.5,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData( -15.0, 0.75,  CarScanData.LEFT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData( -15.0, 0.5, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData( -15.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData(  15.0, 0.75, CarScanData.RIGHT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData(  15.0, 0.5, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData(  15.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData( -30.0, 0.5,  CarScanData.LEFT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData( -30.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData(  30.0, 0.5, CarScanData.RIGHT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData(  30.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData( -135.0, 1.0,  CarScanData.LEFT,     CarScanData.BREAK,  CarScanData.NONE, null, null),
        new CarScanData(  135.0, 1.0, CarScanData.RIGHT,     CarScanData.BREAK,  CarScanData.NONE, null, null)
    };

    public CarScanDataInterface[] getScanData() {
        return scanData;
    }
}
