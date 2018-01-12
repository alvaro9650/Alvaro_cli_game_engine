/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 * Extra math funcs
 *
 * @author alumno1718_2
 */
public class Mathcustomfuncs {

    /**
     * Calculates a pseudo-random number between the max and the min
     *
     * @param min Minumum number
     * @param max Maximum number
     * @return Returns a random number between the max and the min
     */
    public static Double random(int min, int max) {
        return Math.random() * (max + 1 - min) + min;
    }
}
