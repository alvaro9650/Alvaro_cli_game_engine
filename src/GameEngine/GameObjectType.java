/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

/**
 * Game object types
 *
 * @author alvaro9650
 */
public enum GameObjectType {
    Simple(1), Composite2dGameObjectType(2), Composite3dGameObjectType(3);
    private final int gameobjecttype;

    /**
     * Constructor for GameObjectType
     *
     * @param gameobjecttype An int that identifies the move type
     * @author alvaro9650
     */
    private GameObjectType(int gameobjecttype) {
        this.gameobjecttype = gameobjecttype;
    }

    /**
     * Getter for GameObjectType
     *
     * @return Returns an int that identifies the move type
     * @author alvaro9650
     */
    public int getGameObjectType() {
        return this.gameobjecttype;
    }
}
