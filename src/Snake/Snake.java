/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import GameEngine.Composite2dGameObject;
import GameEngine.Field;

/**
 *
 * @author alvaro9650
 */
public class Snake extends Composite2dGameObject {

    public Snake(Field field) {
        super(field, field.size.x, field.size.y, 2);
    }
    public void move(MoveDirection movedirection){
        switch(movedirection){
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