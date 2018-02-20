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
public class Composite2dGameObjectComponent extends CompositeGameObjectComponent {

    public Composite2dGameObject parent;

    /**
     * Constructor for Composite2dGameObjectComponent
     *
     * @param parent The parent object
     */
    public Composite2dGameObjectComponent(Composite2dGameObject parent) {
        super(parent);
        this.posiblelocationarea = new RectangularArea(new Coordinate(parent.size.x, parent.size.y), new Coordinate(0, 0));
        this.parent = parent;
    }

}
