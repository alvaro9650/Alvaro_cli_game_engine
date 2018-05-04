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
public class RectangularArea {

    // Maximum coordinate of the rectangular area
    Coordinate maxcoord;
    // Minimum coordinate of the rectangular area
    Coordinate mincoord;

    /**
     * Rectangular_area constructor , creates the object and fix wrong order
     *
     * @param maxx Maximum x coordinate
     * @param maxy Maximum y coordinate
     * @param minx Minimum x coordinate
     * @param miny Minimum y coordinate
     * @author alvaro9650
     */
    public RectangularArea(Integer maxx, Integer minx, Integer maxy, Integer miny) {
        // Check the max and the min are not inversed
        if (maxx >= minx && maxy >= miny) {
            // Create the maximum coordinate and asign it to maxcoord
            this.maxcoord = new Coordinate(maxx, maxy);
            // Create the minimum coordinate and asign it to mincoord
            this.mincoord = new Coordinate(minx, miny);
        } else if (maxx < minx && maxy >= miny) {
            // Create the maximum coordinate and asign it to maxcoord
            this.maxcoord = new Coordinate(minx, maxy);
            // Create the minimum coordinate and asign it to mincoord
            this.mincoord = new Coordinate(maxx, miny);
        } else if (maxx >= minx && maxy < miny) {
            // Create the maximum coordinate and asign it to maxcoord
            this.maxcoord = new Coordinate(maxx, miny);
            // Create the minimum coordinate and asign it to mincoord
            this.mincoord = new Coordinate(minx, maxy);
        } else if (maxx < minx && maxy < miny) {
            // Create the maximum coordinate and asign it to maxcoord
            this.maxcoord = new Coordinate(minx, miny);
            // Create the minimum coordinate and asign it to mincoord
            this.mincoord = new Coordinate(maxx, maxy);
        }
    }

    /**
     * Rectangular_area constructor , creates the object and checks everything
     * is correct
     *
     * @param maxcoord Maximum coordinate
     * @param mincoord Minimum coordinate
     * @author alvaro9650
     */
    public RectangularArea(Coordinate maxcoord, Coordinate mincoord) {
        // Check the max and the min are not inversed
        if (maxcoord.x >= mincoord.x && maxcoord.y >= mincoord.y) {
            // Create the maximum coordinate and asign it to maxcoord
            this.maxcoord = new Coordinate(maxcoord.x, maxcoord.y);
            // Create the minimum coordinate and asign it to mincoord
            this.mincoord = new Coordinate(mincoord.x, mincoord.y);
        } else if (maxcoord.x < mincoord.x && maxcoord.y >= mincoord.y) {
            // Create the maximum coordinate and asign it to maxcoord
            this.maxcoord = new Coordinate(mincoord.x, maxcoord.y);
            // Create the minimum coordinate and asign it to mincoord
            this.mincoord = new Coordinate(maxcoord.x, mincoord.y);
        } else if (maxcoord.x >= mincoord.x && maxcoord.y < mincoord.y) {
            // Create the maximum coordinate and asign it to maxcoord
            this.maxcoord = new Coordinate(maxcoord.x, mincoord.y);
            // Create the minimum coordinate and asign it to mincoord
            this.mincoord = new Coordinate(mincoord.x, maxcoord.y);
        } else if (maxcoord.x < mincoord.x && maxcoord.y < mincoord.y) {
            // Create the maximum coordinate and asign it to maxcoord
            this.maxcoord = new Coordinate(mincoord.x, mincoord.y);
            // Create the minimum coordinate and asign it to mincoord
            this.mincoord = new Coordinate(maxcoord.x, maxcoord.y);
        }
    }

    /**
     * Gets the area in common of this area and another one
     *
     * @param extarea Other area
     * @return The area that those 2 areas have in common
     * @author alvaro9650
     */
    public RectangularArea getCommonArea(RectangularArea extarea) {
        // Create coordinate to save the common minimum
        Coordinate commonmin = new Coordinate(0, 0);
        // Create coordinate to save the common maximum
        Coordinate commonmax = new Coordinate(0, 0);
        if (this.mincoord.x >= extarea.mincoord.x) {
            // Assign the common minimum x
            commonmin.x = this.mincoord.x;
        } else {
            // Assign the common minimum x
            commonmin.x = extarea.mincoord.x;
        }
        if (this.mincoord.y >= extarea.mincoord.y) {
            // Assign the common minimum y
            commonmin.y = this.mincoord.y;
        } else {
            // Assign the common minimum y
            commonmin.y = extarea.mincoord.y;
        }
        if (this.maxcoord.x <= extarea.maxcoord.x) {
            // Assign the common maximum x
            commonmax.x = this.maxcoord.x;
        } else {
            // Assign the common maximum x
            commonmax.x = extarea.maxcoord.x;
        }
        if (this.maxcoord.y <= extarea.maxcoord.y) {
            // Assign the common maximum y
            commonmax.y = this.maxcoord.y;
        } else {
            // Assign the common maximum y
            commonmax.y = extarea.maxcoord.y;
        }
        // Create a RectangularArea with the common values and return it
        return new RectangularArea(commonmax, commonmin);
    }

    /**
     * Checks if a coordinate is inside the area
     *
     * @param coord the coordinate you want to check if it's in the area
     * @return true if the coordinate is in the area
     * @author alvaro9650
     */
    public Boolean isInside(Coordinate coord) {
        return coord.x <= this.maxcoord.x && coord.y <= this.maxcoord.y && coord.x >= this.mincoord.x && coord.y >= this.mincoord.y;
    }

    /**
     * Returns a string containing the rectangular area
     *
     * @author alvaro9650
     * @return The string containing the rectangular area
     */
    @Override
    public String toString() {
        return new StringBuilder("RectangularArea maxcoord = , mincoord = ").insert(35, this.mincoord.toString()).insert(22, this.maxcoord.toString()).toString();
    }
}
