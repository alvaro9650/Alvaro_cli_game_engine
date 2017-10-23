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
public enum Move_type {
    Teleport(1), Up_first(2), Down_first(3), Diagonal(4), None(5);
    private final int move_type;

    private Move_type(int move_type) {
        this.move_type = move_type;
    }

    public int getMove_type() {
        return this.move_type;
    }
}
