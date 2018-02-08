/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 * Move types to use when it's going out of bounds
 *
 * @author alvaro9650
 */
public enum OutOfBoundsMoveType {
    Bounceable(1), Respawnable(2), Impossible(3), Destroyable(4), Possible(5), Farest(6), CircularUniverse(7);
    private final int outofboundsmovetype;

    /**
     * Constructor for OutOfBoundsMoveType
     *
     * @param outofboundsmovetype An int that identifies the type
     * @author alvaro9650
     */
    private OutOfBoundsMoveType(int outofboundsmovetype) {
        this.outofboundsmovetype = outofboundsmovetype;
    }

    /**
     * Getter for OutOfBoundsMoveType
     *
     * @return Returns an int that identifies the type
     * @author alvaro9650
     */
    public int getOutOfBoundsMoveType() {
        return this.outofboundsmovetype;
    }
}
