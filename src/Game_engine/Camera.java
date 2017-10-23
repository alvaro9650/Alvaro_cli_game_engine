/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 *
 * @author alumno1718_2
 */
public class Camera extends Game_object{
    Integer x_resolution;
    Integer y_resolution;
    public Camera(Field field,Integer x,Integer y) {
        super(field);
        this.location.x = x;
        this.location.y = y;
    }
    public void DrawFrame(){
        StringBuilder frame = new StringBuilder();
        for(int y = this.location.y-y_resolution/2;y<this.location.y+y_resolution-y_resolution/2;y++){
            for(int x = this.location.x-x_resolution/2;y<this.location.x+x_resolution-x_resolution/2;x++) {
                frame.append((y>=0&&x>=0&&x<this.playing_field.x_size&&y<this.playing_field.y_size) ? new Game_engine(this.playing_field).ToDrawAt(this.playing_field,x,y) : ' ');
            }
            frame.append("\n");
        }
        System.out.println(frame.toString());
    }
}
