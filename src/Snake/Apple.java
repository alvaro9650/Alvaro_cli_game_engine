/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import GameEngine.Field;
import GameEngine.GameObject;
import GameEngine.MoveType;

/**
 * The apple the snake has to eat to grow
 *
 * @author alvaro9650
 */
public class Apple extends GameObject {

    public Apple(Field field) {
        super(field);
        this.movetype = MoveType.Teleport;
        this.height = 1;
        this.character = 'O';
        this.objecttype = "Apple";
    }

}
