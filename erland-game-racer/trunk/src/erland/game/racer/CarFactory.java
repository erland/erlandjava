/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: Jan 3, 2003
 * Time: 6:40:58 PM
 * To change this template use Options | File Templates.
 */
package erland.game.racer;

import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;
import erland.util.StringUtil;

/**
 * This is a factory that creates car objects
 */
public class CarFactory {
    /**
     * Creates a server car object
     * @param environment The game environment to place the car in
     * @param cont The block container to place the car in
     * @param color The color for the car
     * @param type The type of the car, less than 0 will be cars with equal capabilities,
     * 0 or greater will be cars with different capabilities. Higher number gets faster cars
     * @return The newly created car object
     */
    public static Car createServerCar(GameEnvironmentInterface environment, BlockContainerInterface cont, int color,int type) {
        Car car = new Car();
        car.init(environment);
        car.setContainer(cont);
        car.setImage("car"+(((color+1)%4)+1)+".gif");
        if(type>=0) {
        //cars[i].setCapabilities(2.0+i/10.0,0.015,0.05,2.0,0.01,2.0+i/10);
            car.setCapabilities(2.0+type/10.0,0.015,0.05,2.5,0.01,0.35+type/10);
        }else {
            car.setCapabilities(2.5,0.015,0.05,2.5,0.01,0.35);
        }
        return car;
    }

    /**
     * Creates a client car object
     * @param environment The game environment to place the car in
     * @param cont The block container to place the car in
     * @param carString The text string with data for all cars, observe that this string
     *                  may contain information about several cars. The beginIndex parameter controls
     *                  where data for this car begins.
     * @param beginIndex The start position in the text string for this car
     * @param endIndex The end position in the text string for this car
     * @return The newly created car object
     */
    public static CarClientInterface createClientCar(GameEnvironmentInterface environment, BlockContainerInterface cont, String carString, int beginIndex, int endIndex) {
        CarClient car = new CarClient();
        car.init(environment);
        car.setContainer(cont);
        int x = (int)StringUtil.getNumber(carString,beginIndex,beginIndex+3);
        int y = (int)StringUtil.getNumber(carString,beginIndex+3,beginIndex+6);
        int angle = (int)StringUtil.getNumber(carString,beginIndex+6,beginIndex+9);
        String image = carString.substring(beginIndex+9,endIndex).trim();
        car.setServerPos(x,y);
        car.setClientPos(x,y);
        car.setServerCarAngle(angle);
        car.setClientCarAngle(angle);
        car.setServerMovingAngle(angle);
        car.setClientMovingAngle(angle);
        car.setImage(image);
        return car;
    }
}
