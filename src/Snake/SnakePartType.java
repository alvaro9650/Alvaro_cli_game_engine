/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

/**
 * Snake part types
 *
 * @author alvaro9650
 */
public enum SnakePartType {
    HEAD("HEAD"), BODY("BODY"), TAIL("TAIL");
    private final String snakeparttype;

    /**
     * Constructor for SnakePartType
     *
     * @param snakeparttype An int that identifies the part of the snake body
     * @author alvaro9650
     */
    private SnakePartType(String snakeparttype) {
        this.snakeparttype = snakeparttype;
    }

    /**
     * Getter for SnakePartType
     *
     * @return Returns an int that identifies the part of the snake body
     * @author alvaro9650
     */
    public String getSnakePartType() {
        return this.snakeparttype;
    }
}
