/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

/**
 *
 * @author alvaro9650
 */
public class OutOfBoundsException extends ImpossibleRelocationException {

    public OutOfBoundsException() {
        super();
    }

    public OutOfBoundsException(String exceptiontext) {
        super(exceptiontext);
    }
}

