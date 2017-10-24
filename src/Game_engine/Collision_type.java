/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 * Collision types
 *
 * @author alumno1718_2
 */
public enum Collision_type {
    Ghost(1), Solid_with_holes(2), Liquid(3), Gas(4), Solid(5);
    private final int Collision_type;

    /**
     * Constructor for Collision_type
     *
     * @param Collision_type An int that identifies Collision_type
     */
    private Collision_type(int Collision_type) {
        this.Collision_type = Collision_type;
    }

    /**
     * Getter for Collision_type
     *
     * @return Return an int that represents Collision_type
     */
    public int getCollision_type() {
        return this.Collision_type;
    }
}
