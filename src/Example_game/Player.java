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
import Game_engine.ObjectCollidesException;
import Game_engine.OutOfBoundsException;
import alvaro_tools.MathCustomFuncs;
import Game_engine.OutOfBoundsMoveType;
import Game_engine.Speed;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A player for the example game
 *
 * @author alvaro9650
 */
public class Player extends GameObject {

    Integer move_points;
    Integer points;

    /**
     * Creates a player for the example game
     *
     * @param character the character that represents the player
     * @param field The field where the player is located
     * @author alvaro9650
     */
    public Player(char character, Field field) {
        super(field);
        this.objecttype = "Player";
        this.speed = new Speed(0, 0);
        this.height = 2;
        this.character = character;
        this.outofboundsmovetype = OutOfBoundsMoveType.Bounceable;
        this.move_points = 10;
        this.points = 0;
        this.loglevel = LogLevel.Verbose;
        do {
            this.location = new Coordinate(MathCustomFuncs.random(0, playingfield.size.x - 1).intValue(), MathCustomFuncs.random(0, playingfield.size.y - 1).intValue());
            try {
                this.playingfield.addGameObject(this);
            } catch (ImpossibleLocationAddException ex) {
                Logger.getLogger(Ball.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            } catch (ObjectCollidesException ex) {
                System.out.println("player collide");
            } catch (OutOfBoundsException ex) {
                System.out.println("player out of bounds");
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
     * @author alvaro9650
     */
    @Override
    public void moveTo(Coordinate location) throws ImpossibleLocationRemoveException, ImpossibleLocationAddException {
        this.move_points += (location.x < 0) ? location.x : -location.x;
        this.move_points += (location.y < 0) ? location.y : -location.y;
        if (this.move_points < 0) {
            System.out.println("You want to move too fast so you wont move and you wont accumulate move points");
        } else {
            try {
                super.moveTo(location);
            } catch (ObjectCollidesException ex) {
                System.out.println("ball collide");
            } catch (OutOfBoundsException ex) {
                System.out.println("ball out of bounds");
            }
            this.move_points += 10;
        }
    }

    /**
     * Logs the object data
     *
     * @author alvaro9650
     */
    @Override
    public void log() {
        super.log();
        StringBuilder object_log = new StringBuilder();
        switch (this.loglevel) {
            case Verbose:
                object_log.append("\nmove_points = ");
                object_log.append(this.move_points);
                object_log.append("\npoints = ");
                object_log.append(this.points);
            case DrawRelated:
            case MoveRelated:
            case OutOfBoundsRelated:
            case CollisionRelated:
            case PositionRelated:
            case None:
        }
    }
}
