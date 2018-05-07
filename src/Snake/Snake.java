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
        this.unusedfood = 0;
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
        switch (movedirection) {
            case UP:
                this.head.speed.set(0, -1);
                break;
            case DOWN:
                this.head.speed.set(0, 1);
                break;
            case LEFT:
                this.head.speed.set(-1, 0);
                break;
            case RIGHT:
                this.head.speed.set(1, 0);
                break;
            default:
                this.head.speed.set(0, 0);
                break;
        }
        Coordinate newheadcoord;
        if (this.tail.location.equals(newheadcoord = new Coordinate(this.head.location.x + this.head.speed.x, this.head.location.y + this.head.speed.y))) {
            throw new GameOverException("Snake collides itself");
        } else {
            for (SnakeComponent part : this.body) {
                if (part.location.equals(newheadcoord)) {
                    throw new GameOverException("Snake collides itself");
                }
            }
        }
        Coordinate lastpartlocation;
        lastpartlocation = this.head.location;
        for (SnakeComponent part : this.body) {
            part.speed.set(lastpartlocation.x - part.location.x, lastpartlocation.y - part.location.y);
            lastpartlocation = part.location;
        }
        this.tail.speed.set(lastpartlocation.x - this.tail.location.x, lastpartlocation.y - this.tail.location.y);
        GameEngine.GameEngine.updateLocations(this);
        this.head.speed.stop();
        this.body.forEach((part) -> {
            part.speed.stop();
        });
        if (this.unusedfood > 0) {
            this.unusedfood--;
            this.body.add(this.tail);
            this.tail = new SnakeComponent(this);
            this.tail.part = SnakePartType.TAIL;
            this.tail.location = new Coordinate(this.body.get(this.body.size() - 1).location.x - this.body.get(this.body.size() - 1).speed.x, this.body.get(this.body.size() - 1).location.y - this.body.get(this.body.size() - 1).speed.y);
            this.body.get(this.body.size() - 1).part = SnakePartType.BODY;
            this.body.get(this.body.size() - 1).speed.stop();
        }
        this.tail.speed.stop();
    }

    /**
     * Does the necesary functions to perform when the snake eats an apple
     */
    public void eatApple() {
        this.unusedfood++;
    }
}
