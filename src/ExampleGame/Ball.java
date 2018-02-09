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
}