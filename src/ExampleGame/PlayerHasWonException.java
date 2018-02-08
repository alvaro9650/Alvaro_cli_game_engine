/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExampleGame;

/**
 *
 * @author alvaro9650
 */
public class PlayerHasWonException extends Exception {

    public PlayerHasWonException() {
        super();
    }

    public PlayerHasWonException(String winner) {
        super("The player" + winner + "has won the match");
    }
}
