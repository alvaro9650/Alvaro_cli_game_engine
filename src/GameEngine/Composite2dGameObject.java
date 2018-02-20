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

    public Composite2dGameObject(Field field) {
        super(field);
        size = new TwoDimensionsSize(1, 1);
    }

}
