/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

/**
 * Coordinate in 2d
 *
 * @author alvaro9650
 */
public class Coordinate {

    public Integer x;
    public Integer y;

    /**
     * Constructor for coordinate
     *
     * @param x Coordinate in x dimension
     * @param y Coordinate in y dimension
     * @author alvaro9650
     */
    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate coord) {
        this.x = coord.x;
        this.y = coord.y;
    }
}
