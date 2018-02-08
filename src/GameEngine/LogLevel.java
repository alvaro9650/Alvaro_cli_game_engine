/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

/**
 * Log levels
 *
 * @author alvaro9650
 */
public enum LogLevel {
    Verbose(1), DrawRelated(2), MoveRelated(3), OutOfBoundsRelated(4), CollisionRelated(5), PositionRelated(6), None(7);
    private final int log_level;

    /**
     * Constructor for Log_level
     *
     * @param log_level int that identifies Log_level
     */
    private LogLevel(int log_level) {
        this.log_level = log_level;
    }

    /**
     * Getter for LogLevel
     *
     * @return Returns int that identifies LogLevel
     */
    public int getLogLevel() {
        return this.log_level;
    }
}
