/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

/**
 * An object to save x direction speed and y direction speed
 *
 * @author alvaro9650
 */
public class Speed {

    public Integer x;
    public Integer y;

    /**
     * Creates an object to save speed
     *
     * @param x x speed
     * @param y y speed
     * @author alvaro9650
     */
    public Speed(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates an object to save speed
     *
     * @param speed the new speed
     * @author alvaro9650
     */
    public Speed(Speed speed) {
        this.x = speed.x;
        this.y = speed.y;
    }
}
