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
public class Composite2dGameObject extends GameObject {

    public TwoDimensionsSize size;
    public GameObject[][][] objectcomponents;

    /**
     * Costructor for Composite2dGameObject
     *
     * @param field The field where the object is located
     * @param x X size of the object
     * @param y Y size of the object
     * @param maxobjectspercoord Maximum number of objects per object coord
     * @author alvaro9650
     */
    public Composite2dGameObject(Field field, Integer x, Integer y, Integer maxobjectspercoord) {
        super(field);
        size = new TwoDimensionsSize(x, y);
        objectcomponents = new GameObject[x][y][maxobjectspercoord];
    }

}
