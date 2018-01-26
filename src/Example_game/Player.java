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
import Game_engine.ImpossibleLocationRemoveException;
import Game_engine.LogLevel;
import alvaro_tools.MathCustomFuncs;
import Game_engine.OutOfBoundsMoveType;
import Game_engine.Speed;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A player for the example game
 *
 * @author alumno1718_2
 */
public class Player extends GameObject {

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
        this.out_of_bounds_move_type = OutOfBoundsMoveType.Bounceable;
        this.move_points = 10;
        this.points = 0;
        this.log_level = LogLevel.Verbose;
        do {
            this.location = new Coordinate(MathCustomFuncs.random(0, playing_field.sizex - 1).intValue(), MathCustomFuncs.random(0, playing_field.sizey - 1).intValue());
            try {
                this.playing_field.addGameObject(this);
            } catch (ImpossibleLocationAddException ex) {
                Logger.getLogger(Ball.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            }
            break;
        } while (true);
    }

    /**
     * Moves the player and calculates move points
     *
     * @param location Location you want to move to
     * @throws Game_engine.ImpossibleLocationRemoveException
     * @throws Game_engine.ImpossibleLocationAddException
     */
    @Override
    public void move_to(Coordinate location) throws ImpossibleLocationRemoveException, ImpossibleLocationAddException {
        this.move_points += (location.x < 0) ? location.x : -location.x;
        this.move_points += (location.y < 0) ? location.y : -location.y;
        if (this.move_points < 0) {
            System.out.println("You want to move too fast so you wont move and you wont accumulate move points");
        } else {
            super.move_to(location);
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
