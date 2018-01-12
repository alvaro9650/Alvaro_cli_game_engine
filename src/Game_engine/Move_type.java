/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 * Move types
 *
 * @author alumno1718_2
 */
public enum Move_type {
    Teleport(1), Horizontal_first(2), Vertical_first(3), Diagonal(4), None(5);
    private final int move_type;

    /**
     * Constructor for Move_type
     *
     * @param move_type An int that identifies the move type
     */
    private Move_type(int move_type) {
        this.move_type = move_type;
    }

    /**
     * Getter for Move_type
     *
     * @return Returns an int that identifies the move type
     */
    public int getMove_type() {
        return this.move_type;
    }
}
