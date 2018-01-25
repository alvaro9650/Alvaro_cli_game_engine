/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 * Physical state types
 *
 * @author alumno1718_2
 */
public enum PhysicalStateType {
    Ghost(1), Solid_with_holes(2), Liquid(3), Gas(4), Solid(5);
    private final int physical_state_type;

    /**
     * Constructor for Physical_state_type
     *
     * @param physical_state_type An int that identifies Physical_state_type
     */
    private PhysicalStateType(int physical_state_type) {
        this.physical_state_type = physical_state_type;
    }

    /**
     * Getter for PhysicalStateType
     *
     * @return Return an int that represents PhysicalStateType
     */
    public int getPysical_state_type() {
        return this.physical_state_type;
    }
}
