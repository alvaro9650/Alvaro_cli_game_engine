/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import GameEngine.Camera;
import GameEngine.Field;
import GameEngine.GameEngine;

/**
 *
 * @author alvaro9650
 */
public class SnakeGame {

    /**
     * @param args the command line arguments
     * @author alvaro9650
     */
    public static void main(String[] args) {
        GameEngine gameengine = new GameEngine();
        Field field = new Field(79, 20, 50);
        Camera cam = new Camera(field, 0, 0, 79, 20);
        cam.blank = '|';
        Snake snake = new Snake(field);
        game:
        while (true) {
            cam.updateFrame();
            cam.drawFrame();
            snake.move(MoveDirection.UP);
        }
    }
}
