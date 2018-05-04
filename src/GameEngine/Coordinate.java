/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.x);
        hash = 97 * hash + Objects.hashCode(this.y);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Coordinate other = (Coordinate) obj;
        return !(!Objects.equals(this.x, other.x) || !Objects.equals(this.y, other.y));
    }

    public Coordinate(Coordinate coord) {
        this.x = coord.x;
        this.y = coord.y;
    }

    /**
     * Returns a string containing the coordinates
     *
     * @author alvaro9650
     * @return The string containing the coordinates
     */
    @Override
    public String toString() {
        return new StringBuilder("Coordinate x = , y = ").insert(21, this.y).insert(15, this.x).toString();
    }

}
