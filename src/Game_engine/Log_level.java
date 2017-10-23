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
public enum Log_level {
    Verbose(1), Draw_related(2), Move_related(3), Out_of_bounds_related(4), Collision_related(5), Position_related(6), None(7);
    private final int log_level;

    private Log_level(int log_level) {
        this.log_level = log_level;
    }

    public int getLog_level() {
        return this.log_level;
    }
}
