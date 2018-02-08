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
public class ImpossibleLocationAddException extends ImpossibleRelocationException {

    public ImpossibleLocationAddException() {
        super();
    }

    public ImpossibleLocationAddException(String exception_text) {
        super("Impossible to add object to that location:" + exception_text);
    }
}
