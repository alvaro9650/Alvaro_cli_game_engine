/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Example_game;

import Game_engine.Coordinate;
import Game_engine.Field;
import Game_engine.GameObject;
import Game_engine.ImpossibleLocationAddException;
import alvaro_tools.MathCustomFuncs;
import Game_engine.OutOfBoundsMoveType;
import Game_engine.Speed;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ball for the object example game
 *
 * @author alumno1718_2
 */
public class Ball extends GameObject {

    /**
     * Constructor for Ball
     *
     * @param field Field where the ball is located
     */
    public Ball(Field field) {
        super(field);
        this.objecttype = "Ball";
        this.character = 'O';
        this.height = 1;
        this.outofboundsmovetype = OutOfBoundsMoveType.Bounceable;
        this.speed = new Speed(MathCustomFuncs.random(6, 16).intValue(), MathCustomFuncs.random(6, 16).intValue());
        set_rand_coord:
        do {
            this.location = new Coordinate(MathCustomFuncs.random(0, playingfield.sizex - 1).intValue(), MathCustomFuncs.random(0, playingfield.sizey - 1).intValue());
            try {
                this.playingfield.addGameObject(this);
            } catch (ImpossibleLocationAddException ex) {
                Logger.getLogger(Ball.class.getName()).log(Level.SEVERE, null, ex);
                continue set_rand_coord;
            }
            break;
        } while (true);
    }
}
