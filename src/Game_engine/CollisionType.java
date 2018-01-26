/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 * Collision aggainst other objects
 *
 * @author alumno1718_2
 */
public enum CollisionType {
    Ghost(1), Bounce(2), Worm_hole(3), Imparable(4), Unmoveable(5), Respawnable(6), Destroyable(7), Farest(8);
    private final int collision_type;

    /**
     * Constructor for Collision_type
     *
     * @param Collision_type An int that identifies Collision_type
     */
    private CollisionType(int collision_type) {
        this.collision_type = collision_type;
    }

    /**
     * Getter for Pysical_state_type
     *
     * @return Return an int that represents Pysical_state_type
     */
    public int getCollision_type() {
        return this.collision_type;
    }
}