/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

/**
 *
 * @author alvaro9650
 */
public class GameOverException extends Exception {

    public GameOverException() {
        super();
    }

    public GameOverException(String exceptiontext) {
        super(exceptiontext);
    }
}
