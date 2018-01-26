/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 * The camera has a location and a resolution and it's located in a field , it
 * can draw frames
 *
 * @author alumno1718_2
 */
public class Camera extends GameObject {

    Resolution resolution;
    String frame;

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
     */
    public Camera(Field field, Integer locationx, Integer locationy, Integer resolutionx, Integer resolutiony) {
        super(field);
        this.location.x = locationx;
        this.location.y = locationy;
        this.resolution = new Resolution(resolutionx, resolutiony);
    }

    /**
     * Updates the frame of this camera
     */
    public void updateFrame() {
        StringBuilder framecreator = new StringBuilder();
        for (int y = this.location.y - this.resolution.y / 2; y < this.location.y + this.resolution.y - this.resolution.y / 2; y++) {
            for (int x = this.location.x - this.resolution.x / 2; y < this.location.x + this.location.x - this.location.x / 2; x++) {
                framecreator.append((y >= 0 && x >= 0 && x < this.playing_field.sizex && y < this.playing_field.sizey) ? new GameEngine().ToDrawAt(this.playing_field, x, y) : ' ');
            }
            framecreator.append("\n");
        }
        this.frame = framecreator.toString();
    }

    /**
     * Draws the frame of this camera
     */
    public void drawFrame() {
        System.out.println(this.frame);
    }
}
