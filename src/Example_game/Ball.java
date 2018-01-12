/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Example_game;

import Game_engine.Coordinate;
import Game_engine.Field;
import Game_engine.Game_object;
import Game_engine.ImpossibleLocationAddException;
import Game_engine.Mathcustomfuncs;
import Game_engine.Out_of_bounds_move_type;
import Game_engine.Speed;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ball for the object example game
 *
 * @author alumno1718_2
 */
public class Ball extends Game_object {

    /**
     * Constructor for Ball
     *
     * @param field Field where the ball is located
     */
    public Ball(Field field) {
        super(field);
        this.object_type = "Ball";
        this.character = 'O';
        this.height = 1;
        this.out_of_bounds_move_type = Out_of_bounds_move_type.Bounceable;
        this.speed = new Speed(Mathcustomfuncs.random(6, 16).intValue(), Mathcustomfuncs.random(6, 16).intValue());
        set_rand_coord:
        do {
            this.location = new Coordinate(Mathcustomfuncs.random(0, playing_field.x_size - 1).intValue(), Mathcustomfuncs.random(0, playing_field.y_size - 1).intValue());
            try {
                this.playing_field.AddGame_object(this);
            } catch (ImpossibleLocationAddException ex) {
                Logger.getLogger(Ball.class.getName()).log(Level.SEVERE, null, ex);
                continue set_rand_coord;
            }
            break;
        } while (true);
    }
}
