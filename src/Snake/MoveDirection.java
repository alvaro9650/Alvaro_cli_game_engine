/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

/**
 * Move directions
 *
 * @author alvaro9650
 */
public enum MoveDirection {
    UP("UP"), DOWN("DOWN"), RIGHT("RIGHT"), LEFT("RIGHT");
    private final String movedirection;

    /**
     * Constructor for MoveDirection
     *
     * @param movedirection An int that identifies the move direction
     * @author alvaro9650
     */
    private MoveDirection(String movedirection) {
        this.movedirection = movedirection;
    }

    /**
     * Getter for MoveDirection
     *
     * @return Returns an int that identifies the move direction
     * @author alvaro9650
     */
    public String getMoveType() {
        return this.movedirection;
    }
}
