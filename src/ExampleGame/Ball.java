/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExampleGame;

import GameEngine.Coordinate;
import GameEngine.Field;
import GameEngine.GameObject;
import GameEngine.ImpossibleLocationAddException;
import GameEngine.MoveType;
import GameEngine.ObjectCollidesException;
import GameEngine.OutOfBoundsException;
import alvaro_tools.MathCustomFuncs;
import GameEngine.OutOfBoundsMoveType;
import GameEngine.PhysicalStateType;
import GameEngine.Speed;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ball for the object example game
 *
 * @author alvaro9650
 */
public class Ball extends GameObject {

    /**
     * Constructor for Ball
     *
     * @param field Field where the ball is located
     * @author alvaro9650
     */
    public Ball(Field field) {
        super(field);
        this.objecttype = "Ball";
        this.character = 'O';
        this.height = 1;
        this.physicalstatetype=PhysicalStateType.Solid;
        this.outofboundsmovetype = OutOfBoundsMoveType.Bounceable;
        this.speed = new Speed(MathCustomFuncs.random(6, 16).intValue(), MathCustomFuncs.random(6, 16).intValue());
        this.movetype = MoveType.Teleport;
        do {
            this.location = new Coordinate(MathCustomFuncs.random(0, playingfield.size.x - 1).intValue(), MathCustomFuncs.random(0, playingfield.size.y - 1).intValue());
            try {
                this.playingfield.addGameObject(this);
            } catch (ImpossibleLocationAddException ex) {
                Logger.getLogger(Ball.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            } catch (ObjectCollidesException ex) {
                System.out.println("ball collide");
            } catch (OutOfBoundsException ex) {
                System.out.println("ball out of bounds");
            }
            break;
        } while (true);
    }

    /**
     * Returns a string containing the ball information
     *
     * @author alvaro9650
     * @return The string containing the ball information
     */
    @Override
    public String toString() {
        return new StringBuilder("Ball location = , height = , arrayposition = , speed = , playingfield = , posiblelocationarea = , respawnarea = , loglevel = , character = , outofboundsmovetype = , physicalstatetype = , movetype = , objecttype = , objectidentifier = , receivingcollision = , givingcollision = , movedirection = , movingspeed = , remainingspeed = ").insert(330, this.remainingspeed.toString()).insert(311, this.movingspeed.toString()).insert(295, this.movedirection.toString()).insert(277, this.givingcollision).insert(257, this.receivingcollision).insert(234, this.objectidentifier).insert(213, this.objecttype).insert(198, this.movetype).insert(185, this.physicalstatetype).insert(163, this.outofboundsmovetype).insert(139, this.character).insert(125, this.loglevel).insert(112, this.respawnarea.toString()).insert(96, this.posiblelocationarea.toString()).insert(72, this.playingfield.toString()).insert(55, this.speed.toString()).insert(45, this.arrayposition).insert(27, this.height).insert(16, this.location.toString()).toString();
    }
}
