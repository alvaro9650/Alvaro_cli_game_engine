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
public enum Out_of_bounds_move_type {
    Bounceable(1),Respawnable(2),Impossible(3),Destroyable(4),Possible(5),Farest(6),Circular_universe(7);             
    private final int out_of_bounds_move_type;
    private Out_of_bounds_move_type(int out_of_bounds_move_type) {
        this.out_of_bounds_move_type = out_of_bounds_move_type;
    }
    public int getout_of_bounds_move_type(){
        return this.out_of_bounds_move_type;
    }
}
