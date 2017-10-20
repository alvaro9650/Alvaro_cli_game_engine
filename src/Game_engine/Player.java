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
public class Player extends Game_object{
    Integer move_points;
    Integer points;
    Player(char character,Field field){
        super(field);
        this.speed=new Speed(0,0);
        this.height = 2;
        this.character = character;
        this.out_of_bounds_move_type = Out_of_bounds_move_type.Bounceable;
        this.location = new Coordinate(Mathcustomfuncs.random(0,playing_field.x_size-1).intValue(), Mathcustomfuncs.random(0,playing_field.y_size-1).intValue());
        this.move_points = 10;
        this.points = 0;
        this.log_level=Log_level.Verbose;
        this.playing_field.AddGame_object(this);
    }
    public void move(Integer x , Integer y){
        this.move_points += (x<0) ? x : -x;
        this.move_points += (y<0) ? y : -y;
        if(this.move_points<0){
            System.out.println("You want to move too fast so you wont move and you wont accumulate move points");
        }else{
            UpdateLocation(x,y);
            this.move_points+=10;
        }
    }
    public void log(){
        super.log();
        StringBuilder object_log = new StringBuilder();
        switch (this.log_level){
                case Verbose:
                    object_log.append("\nmove_points = ");
                    object_log.append(this.move_points);
                    object_log.append("\npoints = ");
                    object_log.append(this.points);
                case Draw_related:
                case Move_related:
                case Out_of_bounds_related:
                case Collision_related:
                case Position_related:
                case None:
        }
    }
}
