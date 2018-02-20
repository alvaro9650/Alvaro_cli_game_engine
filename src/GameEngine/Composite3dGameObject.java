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
public class Composite3dGameObject extends GameObject {

    public ThreeDimensionsSize size;
    public GameObject[][][][] objectcomponents;

    /**
     * Costructor for Composite3dGameObject
     *
     * @param field The field where the object is located
     * @param x X size of the object
     * @param y Y size of the object
     * @param z Z size of the object
     * @param maxobjectspercoord Maximum number of objects per object coord
     * @author alvaro9650
     */
    public Composite3dGameObject(Field field, Integer x, Integer y, Integer z, Integer maxobjectspercoord) {
        super(field);
        size = new ThreeDimensionsSize(x, y, z);
        objectcomponents = new GameObject[x][y][z][maxobjectspercoord];
    }
}
