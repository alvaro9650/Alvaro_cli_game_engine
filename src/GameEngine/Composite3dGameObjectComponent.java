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
public class Composite3dGameObjectComponent extends CompositeGameObjectComponent {

    public Composite3dGameObject parent;

    /**
     * Constructor for Composite3dGameObjectComponent
     *
     * @param parent The parent object
     */
    public Composite3dGameObjectComponent(Composite3dGameObject parent) {
        super(parent);
        this.parent = parent;
    }

}
