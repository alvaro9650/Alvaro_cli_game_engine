/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import GameEngine.Composite2dGameObject;
import GameEngine.Coordinate;
import GameEngine.Field;
import GameEngine.ImpossibleLocationAddException;
import GameEngine.ImpossibleLocationRemoveException;
import GameEngine.ObjectCollidesException;
import GameEngine.OutOfBoundsException;
import GameEngine.Speed;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvaro9650
 */
public class Snake extends Composite2dGameObject {

    public SnakeComponent head;
    public ArrayList<SnakeComponent> body;
    public SnakeComponent tail;
    public Integer unusedfood;

    public Snake(Field field) {
        super(field, field.size.x, field.size.y, 2);
        this.objecttype = "Snake";
        this.height = 1;
        this.location = new Coordinate(0, 0);
        this.head = new SnakeComponent(this);
        this.head.part = SnakePartType.HEAD;
        this.head.location = new Coordinate(5, 5);
        this.tail = new SnakeComponent(this);
        this.tail.part = SnakePartType.TAIL;
        this.tail.location = new Coordinate(4, 5);
        this.body = new ArrayList<>();
        try {
            this.addComposite2dGameObjectComponent(this.head);
        } catch (ImpossibleLocationAddException ex) {
            Logger.getLogger(SnakeComponent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectCollidesException ex) {
            System.out.println("it collides");
        } catch (OutOfBoundsException ex) {
            System.out.println("it's out of bounds");
        } catch (ImpossibleLocationRemoveException ex) {
            System.out.println("imposible remove");
        }
        try {
            this.addComposite2dGameObjectComponent(this.tail);
        } catch (ImpossibleLocationAddException ex) {
            Logger.getLogger(SnakeComponent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectCollidesException ex) {
            System.out.println("it collides");
        } catch (OutOfBoundsException ex) {
            System.out.println("it's out of bounds");
        } catch (ImpossibleLocationRemoveException ex) {
            System.out.println("imposible remove");
        }
        try {
            this.playingfield.addGameObject(this);
        } catch (ImpossibleLocationAddException ex) {
            Logger.getLogger(Snake.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectCollidesException ex) {
            System.out.println("Snake collide");
        } catch (OutOfBoundsException ex) {
            System.out.println("Snake out of bounds");
        } catch (ImpossibleLocationRemoveException ex) {
            System.out.println("imposible snake remove");
        }
    }

    /**
     * Method for moving the snake in a direction
     *
     * @param movedirection The direction the snake moves
     * @throws Snake.GameOverException If the snake collides itself
     */
    public void move(MoveDirection movedirection) throws GameOverException {
        Coordinate newheadcoord;
        Coordinate lastpartlocation;
        Speed movespeed;
        switch (movedirection) {
            case UP:
                movespeed = new Speed(0, -1);
                break;
            case DOWN:
                movespeed = new Speed(0, 1);
                break;
            case LEFT:
                movespeed = new Speed(-1, 0);
                break;
            case RIGHT:
                movespeed = new Speed(1, 0);
                break;
            default:
                movespeed = new Speed(0, 0);
                break;
        }
        if (this.tail.location.equals(newheadcoord = new Coordinate(this.head.location.x + movespeed.x, this.head.location.y + movespeed.y))) {
            throw new GameOverException("Snake collides itself");
        } else {
            for (SnakeComponent part : this.body) {
                if (part.location.equals(newheadcoord)) {
                    throw new GameOverException("Snake collides itself");
                }
            }
        }
        this.head.speed = new Speed(movespeed);
        lastpartlocation = this.head.location;
        for (SnakeComponent part : this.body) {
            part.speed = new Speed(lastpartlocation.x - part.location.x, lastpartlocation.y - part.location.y);
            lastpartlocation = part.location;
        }
        this.tail.speed = new Speed(lastpartlocation.x - this.tail.location.x, lastpartlocation.y - tail.location.y);
        GameEngine.GameEngine.updateLocations(this);
        this.head.speed = new Speed(0, 0);
        this.body.forEach((part) -> {
            part.speed = new Speed(0, 0);
        });
        if (this.unusedfood > 0) {
            this.unusedfood--;
            this.body.add(tail);
            this.tail = new SnakeComponent(this);
            this.tail.part = SnakePartType.TAIL;
            this.tail.location = new Coordinate(this.body.get(this.body.size() - 1).location.x - this.body.get(this.body.size() - 1).speed.x, this.body.get(this.body.size() - 1).location.y - this.body.get(this.body.size() - 1).speed.y);
            this.body.get(this.body.size() - 1).part = SnakePartType.BODY;
            this.body.get(this.body.size() - 1).speed.stop();
        }
        this.tail.speed = new Speed(0, 0);
    }

    /**
     * Does the necesary functions to perform when the snake eats an apple
     */
    public void eatApple() {
        this.unusedfood++;
    }
}
