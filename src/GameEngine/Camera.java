/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

import alvaro_tools.ClassesTools;

/**
 * The camera has a location and a resolution and it's located in a field , it
 * can draw frames
 *
 * @author alvaro9650
 */
public class Camera extends GameObject {

    public Resolution resolution;
    public String frame;
    public char blank;

    /**
     * Creates a camera
     *
     * @param field The field that will be drawedby the camera
     * @param locationx The horizontal position where the camera is going to be
     * located
     * @param locationy The vertical position where the camera is going to be
     * located
     * @param resolutionx Horizontal resolution of the camera
     * @param resolutiony Vertical resolution of the camera
     * @author alvaro9650
     */
    public Camera(Field field, Integer locationx, Integer locationy, Integer resolutionx, Integer resolutiony) {
        super(field);
        this.location.x = locationx;
        this.location.y = locationy;
        this.resolution = new Resolution(resolutionx, resolutiony);
        this.blank = ' ';
    }

    /**
     * Updates the frame of this camera
     *
     * @author alvaro9650
     */
    public void updateFrame() {
        StringBuilder framecreator = new StringBuilder();
        for (int y = this.location.y; y < this.location.y + this.resolution.y; y++) {
            for (int x = this.location.x; x < this.location.x + this.resolution.x; x++) {
                framecreator.append((y >= 0 && x >= 0 && x < this.playingfield.size.x && y < this.playingfield.size.y) ? ClassesTools.NVL(GameEngine.toDrawAt(this.playingfield, x, y),this.blank) : this.blank);
            }
            framecreator.append("\n");
        }
        this.frame = framecreator.toString();
    }

    /**
     * Draws the frame of this camera
     *
     * @author alvaro9650
     */
    public void drawFrame() {
        System.out.println(this.frame);
    }

    /**
     * Returns a string containing the camera information
     *
     * @author alvaro9650
     * @return The string containing the camera information
     */
    @Override
    public String toString() {
        return new StringBuilder("Camera resolution = , frame = ").insert(30, this.frame).insert(20, this.resolution.toString()).toString();
    }
}
