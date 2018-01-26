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
    private final int collisiontype;

    /**
     * Constructor for CollisionType
     *
     * @param CollisionType An int that identifies CollisionType
     */
    private CollisionType(int collisiontype) {
        this.collisiontype = collisiontype;
    }

    /**
     * Getter for CollisionType
     *
     * @return Return an int that represents CollisionType
     */
    public int getCollisionType() {
        return this.collisiontype;
    }
}
