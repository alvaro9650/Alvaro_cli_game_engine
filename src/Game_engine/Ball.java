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
public class Ball extends Game_object {

    Ball(Field field) {
        super(field);
        this.object_type = "Ball";
        this.character = 'O';
        this.height = 1;
        this.out_of_bounds_move_type = Out_of_bounds_move_type.Bounceable;
        this.location = new Coordinate(Mathcustomfuncs.random(0, playing_field.x_size - 1).intValue(), Mathcustomfuncs.random(0, playing_field.y_size - 1).intValue());
        this.speed = new Speed(Mathcustomfuncs.random(6, 16).intValue(), Mathcustomfuncs.random(6, 16).intValue());
        this.playing_field.AddGame_object(this);
    }
}
