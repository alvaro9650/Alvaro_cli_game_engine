/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import GameEngine.Coordinate;
import GameEngine.Field;
import GameEngine.GameObject;
import GameEngine.ImpossibleLocationAddException;
import GameEngine.ImpossibleLocationRemoveException;
import GameEngine.MoveType;
import GameEngine.ObjectCollidesException;
import GameEngine.OutOfBoundsException;
import GameEngine.OutOfBoundsMoveType;
import GameEngine.PhysicalStateType;
import alvaro_tools.MathCustomFuncs;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The apple the snake has to eat to grow
 *
 * @author alvaro9650
 */
public class Apple extends GameObject {

    public Apple(Field field) {
        super(field);
        this.movetype = MoveType.Teleport;
        this.height = 1;
        this.character = 'O';
        this.objecttype = "Apple";
        this.physicalstatetype = PhysicalStateType.Solid;
        this.outofboundsmovetype = OutOfBoundsMoveType.Respawnable;
        do {
            this.location = new Coordinate(MathCustomFuncs.random(0, playingfield.size.x - 1).intValue(), MathCustomFuncs.random(0, playingfield.size.y - 1).intValue());
            try {
                this.playingfield.addGameObject(this);
                break;
            } catch (ImpossibleLocationAddException ex) {
                Logger.getLogger(Apple.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ObjectCollidesException ex) {
                System.out.println("apple collides");
            } catch (OutOfBoundsException ex) {
                System.out.println("apple out of bounds");
            } catch (ImpossibleLocationRemoveException ex) {
                System.out.println("imposible apple remove");
            }
        } while (true);
    }
    /**
     * Function to add custom actions when collided against a determined object
     * or object type
     *
     * @param collisiongiver The object that gives the collision
     * @author alvaro9650
     */
    @Override
    public void receiveCollision(GameObject collisiongiver){
        super.receiveCollision(collisiongiver);
        switch (collisiongiver.objectidentifier) {    
            default:
                break;
        }
        switch (collisiongiver.objecttype) {
            case "SnakePart":
                this.respawn();
                break;
            default:
                break;
        }
    }
}
