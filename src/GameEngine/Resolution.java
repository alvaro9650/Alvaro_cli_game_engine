/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

/**
 * A resolution has a horizontal resolution and vertical resolution
 *
 * @author alvaro9650
 */
public class Resolution {

    Integer x;
    Integer y;

    /**
     * Creates a resolution
     *
     * @param x Horizontal resolution
     * @param y Vertical resolution
     * @author alvaro9650
     */
    public Resolution(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a string containing the resolution
     *
     * @author alvaro9650
     * @return The string containing the resolution
     */
    @Override
    public String toString() {
        return new StringBuilder("Resolution x = , y = ").insert(21, this.y).insert(15, this.x).toString();
    }
}
