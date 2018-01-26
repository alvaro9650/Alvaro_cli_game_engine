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
public enum MoveType {
    Teleport(1), HorizontalFirst(2), VerticalFirst(3), Diagonal(4), None(5);
    private final int movetype;

    /**
     * Constructor for MoveType
     *
     * @param movetype An int that identifies the move type
     */
    private MoveType(int movetype) {
        this.movetype = movetype;
    }

    /**
     * Getter for MoveType
     *
     * @return Returns an int that identifies the move type
     */
    public int getMoveType() {
        return this.movetype;
    }
}
