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
public class Rectangular_area {

    Coordinate max_coord;
    Coordinate min_coord;

    /**
     * Rectangular_area constructor , creates the object and fix wrong order
     *
     * @param max_x Maximum x coordinate
     * @param max_y Maximum y coordinate
     * @param min_x Minimum x coordinate
     * @param min_y Minimum y coordinate
     */
    public Rectangular_area(Integer max_x, Integer min_x, Integer max_y, Integer min_y) {
        if (max_x >= min_x && max_y >= min_y) {
            this.max_coord = new Coordinate(max_x, max_y);
            this.min_coord = new Coordinate(min_x, min_y);
        } else if (max_x < min_x && max_y >= min_y) {
            this.max_coord = new Coordinate(min_x, max_y);
            this.min_coord = new Coordinate(max_x, min_y);
        } else if (max_x >= min_x && max_y < min_y) {
            this.max_coord = new Coordinate(max_x, min_y);
            this.min_coord = new Coordinate(min_x, max_y);
        } else if (max_x < min_x && max_y < min_y) {
            this.max_coord = new Coordinate(min_x, min_y);
            this.min_coord = new Coordinate(max_x, max_y);
        }

    }

    /**
     * Rectangular_area constructor , creates the object and checks everything
     * is correct
     *
     * @param max_coord Maximum coordinate
     * @param min_coord Minimum coordinate
     */
    public Rectangular_area(Coordinate max_coord, Coordinate min_coord) {
        if (max_coord.x >= min_coord.x && max_coord.y >= min_coord.y) {
            this.max_coord = new Coordinate(max_coord.x, max_coord.y);
            this.min_coord = new Coordinate(min_coord.x, min_coord.y);
        } else if (max_coord.x < min_coord.x && max_coord.y >= min_coord.y) {
            this.max_coord = new Coordinate(min_coord.x, max_coord.y);
            this.min_coord = new Coordinate(max_coord.x, min_coord.y);
        } else if (max_coord.x >= min_coord.x && max_coord.y < min_coord.y) {
            this.max_coord = new Coordinate(max_coord.x, min_coord.y);
            this.min_coord = new Coordinate(min_coord.x, max_coord.y);
        } else if (max_coord.x < min_coord.x && max_coord.y < min_coord.y) {
            this.max_coord = new Coordinate(min_coord.x, min_coord.y);
            this.min_coord = new Coordinate(max_coord.x, max_coord.y);
        }
    }

    /**
     * Gets the area in common of this area and another one
     *
     * @param ext_area Other area
     * @return The area that those 2 areas have in common
     */
    public Rectangular_area CommonArea(Rectangular_area ext_area) {
        Coordinate commonmin = new Coordinate(0, 0);
        Coordinate commonmax = new Coordinate(0, 0);
        if (this.min_coord.x <= ext_area.min_coord.x) {
            commonmin.x = this.min_coord.x;
        } else {
            commonmin.x = ext_area.min_coord.x;
        }
        if (this.min_coord.y <= ext_area.min_coord.y) {
            commonmin.y = this.min_coord.y;
        } else {
            commonmin.y = ext_area.min_coord.y;
        }
        if (this.max_coord.x >= ext_area.max_coord.x) {
            commonmax.x = this.max_coord.x;
        } else {
            commonmax.x = ext_area.max_coord.x;
        }
        if (this.max_coord.y >= ext_area.max_coord.y) {
            commonmax.y = this.max_coord.y;
        } else {
            commonmax.y = ext_area.max_coord.y;
        }
        return new Rectangular_area(commonmax, commonmin);
    }
}
