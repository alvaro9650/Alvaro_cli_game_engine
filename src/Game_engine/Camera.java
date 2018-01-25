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
     * @param x_location The horizontal position where the camera is going to be
     * located
     * @param y_location The vertical position where the camera is going to be
     * located
     * @param x_resolution Horizontal resolution of the camera
     * @param y_resolution Vertical resolution of the camera
     */
    public Camera(Field field, Integer x_location, Integer y_location, Integer x_resolution, Integer y_resolution) {
        super(field);
        this.location.x = x_location;
        this.location.y = y_location;
        this.resolution = new Resolution(x_resolution, y_resolution);
    }

    /**
     * Updates the frame of this camera
     */
    public void UpdateFrame() {
        StringBuilder frame_creator = new StringBuilder();
        for (int y = this.location.y - this.resolution.y / 2; y < this.location.y + this.resolution.y - this.resolution.y / 2; y++) {
            for (int x = this.location.x - this.resolution.x / 2; y < this.location.x + this.location.x - this.location.x / 2; x++) {
                frame_creator.append((y >= 0 && x >= 0 && x < this.playing_field.x_size && y < this.playing_field.y_size) ? new Game_engine().ToDrawAt(this.playing_field, x, y) : ' ');
            }
            frame_creator.append("\n");
        }
        this.frame = frame_creator.toString();
    }

    /**
     * Draws the frame of this camera
     */
    public void DrawFrame() {
        System.out.println(this.frame);
    }
}
