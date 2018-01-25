/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 * Move types to use when it's going out of bounds
 *
 * @author alumno1718_2
 */
public enum OutOfBoundsMoveType {
    Bounceable(1), Respawnable(2), Impossible(3), Destroyable(4), Possible(5), Farest(6), Circular_universe(7);
    private final int out_of_bounds_move_type;

    /**
     * Constructor for Out_of_bounds_move_type
     *
     * @param out_of_bounds_move_type An int that identifies the type
     */
    private OutOfBoundsMoveType(int out_of_bounds_move_type) {
        this.out_of_bounds_move_type = out_of_bounds_move_type;
    }

    /**
     * Getter for OutOfBoundsMoveType
     *
     * @return Returns an int that identifies the type
     */
    public int getout_of_bounds_move_type() {
        return this.out_of_bounds_move_type;
    }
}
