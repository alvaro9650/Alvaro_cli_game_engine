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
public class CompositeGameObjectComponent extends GameObject {

    public CompositeGameObject parent;

    /**
     * Constructor for CompositeGameObjectComponent
     *
     * @param parent The parent object
     */
    public CompositeGameObjectComponent(CompositeGameObject parent) {
        super(parent.playingfield);
        this.parent = parent;
    }
}
