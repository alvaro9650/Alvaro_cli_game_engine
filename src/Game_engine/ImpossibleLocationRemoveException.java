/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 *
 * @author alumno1718_2
 */
public class ImpossibleLocationRemoveException extends ImpossibleRelocationException {

    public ImpossibleLocationRemoveException() {
        super();
    }

    public ImpossibleLocationRemoveException(String exception_text) {
        super("Impossible to remove object from that location:" + exception_text);
    }
}
