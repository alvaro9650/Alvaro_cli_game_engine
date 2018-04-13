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
public class ThreeDimensionsSize extends TwoDimensionsSize {

    public Integer z;

    /**
     * Creates an object to save a 3D size
     *
     * @param x X size
     * @param y Y size
     * @param z Z size
     * @author alvaro9650
     */
    public ThreeDimensionsSize(Integer x, Integer y, Integer z) {
        super(x, y);
        this.z = z;
    }

    /**
     * Returns a string containing the 3d information
     *
     * @author alvaro9650
     * @return The string containing the 3d information
     */
    @Override
    public String toString() {
        return new StringBuilder("ThreeDimensionsSize x = , y = , z = ").insert(36, this.z).insert(30, this.y).insert(24, this.x).toString();
    }
}
