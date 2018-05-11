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

    /**
     * Constructor for the apple
     *
     * @param field The field where the apple will be located
     */
    public Apple(Field field) {
        //Makes a call to do everything the superior constructor does
        super(field);
        //Select a type of movement
        this.movetype = MoveType.Teleport;
        //Set the object height
        this.height = 1;
        //Assign a character to use when drawing the object
        this.character = 'O';
        //Sets the object type
        this.objecttype = "Apple";
        //Select a physical state type to use in collisions
        this.physicalstatetype = PhysicalStateType.Solid;
        //Select a type of movement to use when the object goes out of bounds
        this.outofboundsmovetype = OutOfBoundsMoveType.Respawnable;
        //Try to add the apple to the field until the apple is located
        do {
            //Set a random location for the apple
            this.location = new Coordinate(MathCustomFuncs.random(0, playingfield.size.x - 1).intValue(), MathCustomFuncs.random(0, playingfield.size.y - 1).intValue());
            //Try to add the apple to the field in the location created before
            try {
                //Add the apple to the field
                this.playingfield.addGameObject(this);
            } catch (ImpossibleLocationAddException ex) {
                //Log message to use if adding gives ImpossibleLocationAddException
                Logger.getLogger(Apple.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ObjectCollidesException ex) {
                //Log message to use if adding gives ObjectCollidesException
                System.out.println("apple collides");
            } catch (OutOfBoundsException ex) {
                //Log message to use if adding gives OutOfBoundsException
                System.out.println("apple out of bounds");
            } catch (ImpossibleLocationRemoveException ex) {
                //Log message to use if adding gives ImpossibleLocationRemoveException
                System.out.println("imposible apple remove");
            }
        } while (!this.located);
    }

    /**
     * Function to add custom actions when collided against a determined object
     * or object type
     *
     * @param collisiongiver The object that provokes the collision
     * @author alvaro9650
     */
    @Override
    public void receiveCollision(GameObject collisiongiver) {
        super.receiveCollision(collisiongiver);
        //Checks the object identifier of the object that provoked the collision
        switch (collisiongiver.objectidentifier) {
            default:
                break;
        }
        //Checks the object type of the object that provoked the collision
        switch (collisiongiver.objecttype) {
            case "SnakePart":
                //Respawn the apple
                this.respawn();
                break;
            default:
                break;
        }
    }
}
