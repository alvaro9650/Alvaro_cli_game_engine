/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 *
 * @author alumno1718_2
 */
public class RectangularArea {

    Coordinate maxcoord;
    Coordinate mincoord;

    /**
     * Rectangular_area constructor , creates the object and fix wrong order
     *
     * @param maxx Maximum x coordinate
     * @param maxy Maximum y coordinate
     * @param minx Minimum x coordinate
     * @param miny Minimum y coordinate
     */
    public RectangularArea(Integer maxx, Integer minx, Integer maxy, Integer miny) {
        if (maxx >= minx && maxy >= miny) {
            this.maxcoord = new Coordinate(maxx, maxy);
            this.mincoord = new Coordinate(minx, miny);
        } else if (maxx < minx && maxy >= miny) {
            this.maxcoord = new Coordinate(minx, maxy);
            this.mincoord = new Coordinate(maxx, miny);
        } else if (maxx >= minx && maxy < miny) {
            this.maxcoord = new Coordinate(maxx, miny);
            this.mincoord = new Coordinate(minx, maxy);
        } else if (maxx < minx && maxy < miny) {
            this.maxcoord = new Coordinate(minx, miny);
            this.mincoord = new Coordinate(maxx, maxy);
        }

    }

    /**
     * Rectangular_area constructor , creates the object and checks everything
     * is correct
     *
     * @param maxcoord Maximum coordinate
     * @param mincoord Minimum coordinate
     */
    public RectangularArea(Coordinate maxcoord, Coordinate mincoord) {
        if (maxcoord.x >= mincoord.x && maxcoord.y >= mincoord.y) {
            this.maxcoord = new Coordinate(maxcoord.x, maxcoord.y);
            this.mincoord = new Coordinate(mincoord.x, mincoord.y);
        } else if (maxcoord.x < mincoord.x && maxcoord.y >= mincoord.y) {
            this.maxcoord = new Coordinate(mincoord.x, maxcoord.y);
            this.mincoord = new Coordinate(maxcoord.x, mincoord.y);
        } else if (maxcoord.x >= mincoord.x && maxcoord.y < mincoord.y) {
            this.maxcoord = new Coordinate(maxcoord.x, mincoord.y);
            this.mincoord = new Coordinate(mincoord.x, maxcoord.y);
        } else if (maxcoord.x < mincoord.x && maxcoord.y < mincoord.y) {
            this.maxcoord = new Coordinate(mincoord.x, mincoord.y);
            this.mincoord = new Coordinate(maxcoord.x, maxcoord.y);
        }
    }

    /**
     * Gets the area in common of this area and another one
     *
     * @param extarea Other area
     * @return The area that those 2 areas have in common
     */
    public RectangularArea getCommonArea(RectangularArea extarea) {
        Coordinate commonmin = new Coordinate(0, 0);
        Coordinate commonmax = new Coordinate(0, 0);
        if (this.mincoord.x <= extarea.mincoord.x) {
            commonmin.x = this.mincoord.x;
        } else {
            commonmin.x = extarea.mincoord.x;
        }
        if (this.mincoord.y <= extarea.mincoord.y) {
            commonmin.y = this.mincoord.y;
        } else {
            commonmin.y = extarea.mincoord.y;
        }
        if (this.maxcoord.x >= extarea.maxcoord.x) {
            commonmax.x = this.maxcoord.x;
        } else {
            commonmax.x = extarea.maxcoord.x;
        }
        if (this.maxcoord.y >= extarea.maxcoord.y) {
            commonmax.y = this.maxcoord.y;
        } else {
            commonmax.y = extarea.maxcoord.y;
        }
        return new RectangularArea(commonmax, commonmin);
    }
}