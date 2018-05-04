/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

import java.util.Objects;

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

    /**
     * Sets the x speed and y speed to 0
     *
     * @author alvaro9650
     */
    public void stop() {
        this.x = 0;
        this.y = 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.x);
        hash = 23 * hash + Objects.hashCode(this.y);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null||getClass() != obj.getClass()) {
            return false;
        }
        final Speed other = (Speed) obj;
        return !(!Objects.equals(this.x, other.x)||!Objects.equals(this.y, other.y));
    }

    /**
     * Returns a string containing the speed
     *
     * @author alvaro9650
     * @return The string containing the speed
     */
    @Override
    public String toString() {
        return new StringBuilder("Speed x = , y = ").insert(16, this.y).insert(10, this.x).toString();
    }
}
