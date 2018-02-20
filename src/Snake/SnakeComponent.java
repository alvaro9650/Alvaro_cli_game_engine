/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import GameEngine.Composite2dGameObjectComponent;
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
public class SnakeComponent extends Composite2dGameObjectComponent {

    public SnakeComponent(Snake parent) {
        super(parent);
        try {
            parent.addGameObject(this);
        } catch (ImpossibleLocationAddException ex) {
            Logger.getLogger(Composite2dGameObjectComponent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectCollidesException ex) {
            System.out.println("ball collide");
        } catch (OutOfBoundsException ex) {
            System.out.println("ball out of bounds");
        } catch (ImpossibleLocationRemoveException ex) {
            System.out.println("imposible ball remove");
        }
    }
}
