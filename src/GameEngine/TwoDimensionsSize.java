/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

/**
 *
 * @author alvaro9650
 */
public class TwoDimensionsSize {

    public Integer x;
    public Integer y;

    /**
     * Creates an object to save a 2D size
     *
     * @param x X size
     * @param y Y size
     * @author alvaro9650
     */
    public TwoDimensionsSize(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a string containing the 2d information
     *
     * @author alvaro9650
     * @return The string containing the 2d information
     */
    @Override
    public String toString() {
        return new StringBuilder("TwoDimensionsSize x = , y = ").insert(28, this.y).insert(22, this.x).toString();
    }
}
