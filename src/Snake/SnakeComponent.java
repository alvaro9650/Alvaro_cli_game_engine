/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import GameEngine.Composite2dGameObjectComponent;
import GameEngine.GameObject;
import GameEngine.MoveType;

/**
 *
 * @author alvaro9650
 */
public class SnakeComponent extends Composite2dGameObjectComponent {

    public SnakePartType part;

    public SnakeComponent(Snake parent) {
        super(parent);
        this.character = '8';
        this.movetype = MoveType.Teleport;
        this.height = 1;
        this.objecttype = "SnakePart";
    }
    /**
     * Function to add custom actions when colliding against a determined object
     * or object type
     *
     * @param collisionreceiver The object that receives the collision
     * @author alvaro9650
     */
    @Override
    public void giveCollision(GameObject collisionreceiver) {
        switch (collisionreceiver.objectidentifier) {
            default:
                break;
        }
        switch (collisionreceiver.objecttype) {
            case "Apple":
                ((Snake)parent).eatApple();
                break;
            default:
                break;
        }
    }
}
