/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import GameEngine.Composite2dGameObject;
import GameEngine.Composite2dGameObjectComponent;
import GameEngine.Coordinate;
import GameEngine.Field;
import GameEngine.ImpossibleLocationAddException;
import GameEngine.ImpossibleLocationRemoveException;
import GameEngine.ObjectCollidesException;
import GameEngine.OutOfBoundsException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvaro9650
 */
public class Snake extends Composite2dGameObject {

    public SnakeComponent head;
    public SnakeComponent[] body;
    public SnakeComponent tail;

    public Snake(Field field) {
        super(field, field.size.x, field.size.y, 2);
        this.objectidentifier="Snake";
        this.location = new Coordinate(0, 0);
        this.head = new SnakeComponent(this);
        this.head.part = SnakePartType.HEAD;
        this.head.location = new Coordinate(5, 5);
        this.tail = new SnakeComponent(this);
        this.tail.part = SnakePartType.HEAD;
        this.tail.location = new Coordinate(4, 5);
        try {
            this.addGameObject(head);
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
            this.addGameObject(tail);
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
     */
    public void move(MoveDirection movedirection) {
        switch (movedirection) {
            case UP:
                GameEngine.GameEngine.updateLocations(this);
            case DOWN:
                GameEngine.GameEngine.updateLocations(this);
            case LEFT:
                GameEngine.GameEngine.updateLocations(this);
            case RIGHT:
                GameEngine.GameEngine.updateLocations(this);
        }
    }
}
