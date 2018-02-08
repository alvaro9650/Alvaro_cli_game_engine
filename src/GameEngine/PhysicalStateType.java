/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

/**
 * Physical state types
 *
 * @author alvaro9650
 */
public enum PhysicalStateType {
    Ghost(1), SolidWithHoles(2), Liquid(3), Gas(4), Solid(5);
    private final int physicalstatetype;

    /**
     * Constructor for Physical_state_type
     *
     * @param physical_state_type An int that identifies Physical_state_type
     * @author alvaro9650
     */
    private PhysicalStateType(int physicalstatetype) {
        this.physicalstatetype = physicalstatetype;
    }

    /**
     * Getter for PhysicalStateType
     *
     * @return Return an int that represents PhysicalStateType
     * @author alvaro9650
     */
    public int getPysicalStateType() {
        return this.physicalstatetype;
    }
}
