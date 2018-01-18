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
import Game_engine.Log_level;
import Game_engine.Mathcustomfuncs;
import Game_engine.Out_of_bounds_move_type;
import Game_engine.Speed;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A player for the example game
 *
 * @author alumno1718_2
 */
public class Player extends Game_object {

    Integer move_points;
    Integer points;

    /**
     * Creates a player for the example game
     *
     * @param character the character that represents the player
     * @param field The field where the player is located
     */
    public Player(char character, Field field) {
        super(field);
        this.object_type = "Player";
        this.speed = new Speed(0, 0);
        this.height = 2;
        this.character = character;
        this.out_of_bounds_move_type = Out_of_bounds_move_type.Bounceable;
        this.move_points = 10;
        this.points = 0;
        this.log_level = Log_level.Verbose;
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

    /**
     * Moves the player and calculates move points
     *
     * @param x How much to move in x direction
     * @param y How much to move in y direction
     */
    public void move(Integer x, Integer y) {
        this.move_points += (x < 0) ? x : -x;
        this.move_points += (y < 0) ? y : -y;
        if (this.move_points < 0) {
            System.out.println("You want to move too fast so you wont move and you wont accumulate move points");
        } else {
            UpdateLocation(x, y);
            this.move_points += 10;
        }
    }

    /**
     * Logs the object data
     */
    @Override
    public void log() {
        super.log();
        StringBuilder object_log = new StringBuilder();
        switch (this.log_level) {
            case Verbose:
                object_log.append("\nmove_points = ");
                object_log.append(this.move_points);
                object_log.append("\npoints = ");
                object_log.append(this.points);
            case Draw_related:
            case Move_related:
            case Out_of_bounds_related:
            case Collision_related:
            case Position_related:
            case None:
        }
    }
}