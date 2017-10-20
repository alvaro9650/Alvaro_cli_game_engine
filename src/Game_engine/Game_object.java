/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno1718_2
 */
public class Game_object implements Closeable {

    Coordinate location;
    Speed speed;
    Field playing_field;
    Integer max_x_location;
    Integer min_x_location;
    Integer max_y_location;
    Integer min_y_location;
    Integer max_x_respawn_area;
    Integer min_x_respawn_area;
    Integer max_y_respawn_area;
    Integer min_y_respawn_area;
    Integer height;
    Log_level log_level;
    char character;
    Out_of_bounds_move_type out_of_bounds_move_type;
    Collision_type collision_type;
    Move_type move_type;
    String object_type;
    Game_object(Field field) {
        this.character = '|';
        this.height = 0;
        this.object_type = "Default";
        this.speed = new Speed(0,0);
        this.move_type = Move_type.None;
        this.playing_field = field;
        this.max_x_location = field.x_size-1;
        this.min_x_location = 0;
        this.max_y_location = field.y_size-1;
        this.min_y_location = 0;
        this.max_x_respawn_area = field.x_size-1;
        this.min_x_respawn_area = 0;
        this.max_y_respawn_area = field.y_size-1;
        this.min_y_respawn_area = 0;
        this.collision_type = Collision_type.Ghost;
        this.out_of_bounds_move_type = Out_of_bounds_move_type.Circular_universe;
        this.log_level = Log_level.None;
        this.location = new Coordinate(this.min_x_location,this.min_y_location);
    }

    public void RespawnPoint(Integer x, Integer y) {
        this.max_x_respawn_area = x;
        this.min_x_respawn_area = x;
        this.max_y_respawn_area = y;
        this.min_y_respawn_area = y;
    }
    public void RespawnArea(Integer min_x, Integer max_x, Integer min_y, Integer max_y) {
        this.min_x_respawn_area = min_x;
        this.max_x_respawn_area = max_x;
        this.min_y_respawn_area = min_y;
        this.max_y_respawn_area = max_y;
    }
    public void log() {
        StringBuilder object_log = new StringBuilder();
        object_log.append("Object: ");
        object_log.append(this.hashCode());
        switch (this.log_level){
                case Verbose:
                    object_log.append("\nlog_level = verbose");
                case Draw_related:
                    object_log.append("\ncharacter = ");
                    object_log.append(this.character);
                case Move_related:
                    object_log.append("\nmove_type = ");
                    object_log.append(this.move_type);
                case Out_of_bounds_related:
                    object_log.append("\nplaying_field x = ");
                    object_log.append(this.playing_field.x_size);
                    object_log.append("\nplaying_field y = ");
                    object_log.append(this.playing_field.y_size);
                    object_log.append("\nspeed x = ");
                    object_log.append(this.speed.x);
                    object_log.append("\nspeed y = ");
                    object_log.append(this.speed.y);
                    object_log.append("\nmin_x_location = ");
                    object_log.append(this.min_x_location);
                    object_log.append("\nmax_x_location = ");
                    object_log.append(this.max_x_location);
                    object_log.append("\nmin_y_location = ");
                    object_log.append(this.min_y_location);
                    object_log.append("\nmax_y_location = ");
                    object_log.append(this.max_y_location);
                    object_log.append("\nmin_x_respawn_area = ");
                    object_log.append(this.min_x_respawn_area);
                    object_log.append("\nmax_x_respawn_area = ");
                    object_log.append(this.max_x_respawn_area);
                    object_log.append("\nmin_y_respawn_area = ");
                    object_log.append(this.min_y_respawn_area);
                    object_log.append("\nmax_y_respawn_area = ");
                    object_log.append(this.max_y_respawn_area);
                    object_log.append("\nout_of_bounds_move_type = ");
                    object_log.append(this.out_of_bounds_move_type);
                case Collision_related:
                    object_log.append("\ncollision_type = ");
                    object_log.append(this.collision_type);
                case Position_related:
                    object_log.append("\ncoordinate x = ");
                    object_log.append(location.x.toString());
                    object_log.append("\ncoordinate y = ");
                    object_log.append(location.y.toString());
                    break;
                case None:
                    object_log.delete(0, object_log.length());
        }
        System.out.println(object_log.toString());
    }
    public Boolean[] CanUpdateLocation() {
        switch (this.out_of_bounds_move_type) {
            case Bounceable:
                return CanUpdatecircularuniverseLocation();
            case Respawnable:
                return CanUpdaterespawnableLocation();
            case Impossible:
                return CanUpdateimpossibleLocation();
            case Destroyable:
                return CanUpdatedestroyableLocation();
            case Possible:
                return CanUpdatepossibleLocation();
            case Farest:
                return CanUpdatefarestLocation();
            case Circular_universe:
                return CanUpdatecircularuniverseLocation();
        }
        Boolean[] no_out_of_bounds_move_type = {false,false};
        return no_out_of_bounds_move_type;
    }
    public void UpdateLocation() {
        if ( this.speed.x!=0 || this.speed.y!=0 ){
            this.playing_field.DeleteGame_object(this);
            switch (this.out_of_bounds_move_type) {
                case Bounceable:
                    UpdatebounceableLocation();
                    break;
                case Respawnable:
                    UpdaterespawnableLocation();
                    break;
                case Impossible:
                    UpdateimpossibleLocation();
                    break;
                case Destroyable:
                    try {
                        UpdatedestroyableLocation();
                    } catch (IOException ex) {
                        Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case Possible:
                    UpdatepossibleLocation();
                    break;
                case Farest:
                    UpdatefarestLocation();
                    break;
                case Circular_universe:
                    UpdatecircularuniverseLocation();
                    break;
            }
            this.playing_field.AddGame_object(this);
        }
    }
    public Boolean[] CanUpdatebounceableLocation() {
        Boolean[] result = {CanUpdatebounceablexLocation(),CanUpdatebounceableyLocation()};
        return result;
    }
    public void UpdatebounceableLocation() {
        UpdatebounceablexLocation();
        UpdatebounceableyLocation();
    }
    public Boolean CanUpdatebounceablexLocation() {
        Integer resultcoord;
        return !((resultcoord = this.location.x + this.speed.x) > this.max_x_location || resultcoord < this.min_x_location);
    }
    public void UpdatebounceablexLocation() {
        if (this.location.x + this.speed.x > this.max_x_location) {
            this.location.x -= (this.speed.x - (this.max_x_location - this.location.x));
            this.speed.x = -this.speed.x;
        } else if (this.location.x + this.speed.x < this.min_y_location) {
            this.location.x -= (this.speed.x + this.location.x);
            this.speed.x = -this.speed.x;
        } else {
            this.location.x += this.speed.x;
        }
    }
    public Boolean CanUpdatebounceableyLocation() {
        Integer resultcoord;
        return !((resultcoord = this.location.y + this.speed.y) > this.max_y_location || resultcoord < this.min_y_location);
    }
    public void UpdatebounceableyLocation() {
        if (this.location.y + this.speed.y > this.max_y_location) {
            this.location.y -= (this.speed.y - (this.max_y_location - this.location.y));
            this.speed.y = -this.speed.y;
        } else if (this.location.y + this.speed.y < this.min_y_location) {
            this.location.y -= (this.speed.y + this.location.y);
            this.speed.y = -this.speed.y;
        } else {
            this.location.y += this.speed.y;
        }
    }
    public Boolean[] CanUpdaterespawnableLocation() {
        Boolean[] result = {CanUpdaterespawnablexLocation(),CanUpdaterespawnableyLocation()};
        return result;
    }
    public void UpdaterespawnableLocation() {
        UpdaterespawnablexLocation();
        UpdaterespawnableyLocation();
    }
    public Boolean CanUpdaterespawnablexLocation() {
        Integer resultcoord;
        return !((resultcoord = this.location.x + this.speed.x) > this.max_x_location || resultcoord < this.min_x_location);
    }
    public void UpdaterespawnablexLocation() {
        if (this.location.x + this.speed.x > this.max_x_location || this.location.x + this.speed.x < this.min_x_location) {
            this.location.x = Mathcustomfuncs.random(this.min_x_respawn_area, this.max_x_respawn_area).intValue();
        } else {
            this.location.x += this.speed.x;
        }
    }
    public Boolean CanUpdaterespawnableyLocation() {
        Integer resultcoord;
        return !((resultcoord = this.location.y + this.speed.y) > this.max_y_location || resultcoord < this.min_y_location);
    }
    public void UpdaterespawnableyLocation() {
        if (this.location.y + this.speed.y > this.max_y_location || this.location.y + this.speed.y < this.min_y_location) {
            this.location.y = Mathcustomfuncs.random(this.min_y_respawn_area, this.max_y_respawn_area).intValue();
        } else {
            this.location.y += this.speed.y;
        }
    }
    public Boolean[] CanUpdateimpossibleLocation() {
        Boolean[] result = {CanUpdateimpossiblexLocation(),CanUpdateimpossibleyLocation()};
        return result;
    }
    public void UpdateimpossibleLocation() {
        UpdateimpossiblexLocation();
        UpdateimpossibleyLocation();
    }
    public Boolean CanUpdateimpossiblexLocation() {
        Integer resultcoord;
        return !((resultcoord = this.location.x + this.speed.x) > this.max_x_location || resultcoord < this.min_x_location);
    }
    public void UpdateimpossiblexLocation() {
        this.location.x += (this.location.x + this.speed.x > this.max_x_location || this.location.x + this.speed.x < this.min_x_location) ? 0 : this.speed.x;
    }
    public Boolean CanUpdateimpossibleyLocation() {
        Integer resultcoord;
        return !((resultcoord = this.location.y + this.speed.y) > this.max_y_location || resultcoord < this.min_y_location);
    }
    public void UpdateimpossibleyLocation() {
        this.location.y += (this.location.y + this.speed.y > this.max_y_location || this.location.y + this.speed.y < this.min_y_location) ? 0 : this.speed.y;
    }
    public Boolean[] CanUpdatedestroyableLocation() {
        Boolean[] result = {CanUpdatedestroyablexLocation(),CanUpdatedestroyableyLocation()};
        return result;
    }
    public void UpdatedestroyableLocation() throws IOException {
        UpdatedestroyablexLocation();
        UpdatedestroyableyLocation();
    }
    public Boolean CanUpdatedestroyablexLocation() {
        Integer resultcoord;
        return !((resultcoord = this.location.x + this.speed.x) > this.max_x_location || resultcoord < this.min_x_location);
    }
    public void UpdatedestroyablexLocation() throws IOException {
        if (this.location.x + this.speed.x > this.max_x_location || this.location.x + this.speed.x < this.min_x_location) {
            this.close();
        } else {
            this.location.x += this.speed.x;
        }
    }
    public Boolean CanUpdatedestroyableyLocation() {
        Integer resultcoord;
        return !((resultcoord = this.location.y + this.speed.y) > this.max_y_location || resultcoord < this.min_y_location);
    }
    public void UpdatedestroyableyLocation() throws IOException {
        if (this.location.y + this.speed.y > this.max_y_location || this.location.y + this.speed.y < this.min_y_location) {
            this.close();
        } else {
            this.location.y += this.speed.y;
        }
    }
    public Boolean[] CanUpdatepossibleLocation() {
        Boolean[] result = {CanUpdatepossiblexLocation(),CanUpdatepossibleyLocation()};
        return result;
    }
    public void UpdatepossibleLocation() {
        UpdatepossiblexLocation();
        UpdatepossibleyLocation();
    }
    public Boolean CanUpdatepossiblexLocation() {
        return true;
    }
    public void UpdatepossiblexLocation() {
        this.location.x += this.speed.x;
    }
    public Boolean CanUpdatepossibleyLocation() {
        return true;
    }
    public void UpdatepossibleyLocation() {
        this.location.y += this.speed.y;
    }
    public Boolean[] CanUpdatefarestLocation() {
        Boolean[] result = {CanUpdatefarestxLocation(),CanUpdatefarestyLocation()};
        return result;
    }
    public void UpdatefarestLocation() {
        UpdatefarestxLocation();
        UpdatefarestyLocation();
    }
    public Boolean CanUpdatefarestxLocation() {
        Integer resultcoord;
        return !((resultcoord = this.location.x + this.speed.x) > this.max_x_location || resultcoord < this.min_x_location);
    }
    public void UpdatefarestxLocation() {
        if (this.location.x + this.speed.x > this.max_x_location) {
            this.location.x = this.max_x_location;
        } else if(this.location.x + this.speed.x < this.min_x_location){
            this.location.x = this.min_x_location;
        } else {
            this.location.x += this.speed.x;
        }
    }
    public Boolean CanUpdatefarestyLocation() {
        Integer resultcoord;
        return !((resultcoord = this.location.y + this.speed.y) > this.max_y_location || resultcoord < this.min_y_location);
    }
    public void UpdatefarestyLocation() {
        if (this.location.y + this.speed.y > this.max_y_location) {
            this.location.y = this.max_y_location;
        } else if(this.location.y + this.speed.y < this.min_y_location){
            this.location.y = this.min_y_location;
        } else {
            this.location.y += this.speed.y;
        }
    }
    public Boolean[] CanUpdatecircularuniverseLocation() {
        Boolean[] result = {CanUpdatecircularuniversexLocation(),CanUpdatecircularuniverseyLocation()};
        return result;
    }
    public void UpdatecircularuniverseLocation() {
        UpdatecircularuniversexLocation();
        UpdatecircularuniverseyLocation();
    }
    public Boolean CanUpdatecircularuniversexLocation() {
        return true;
    }
    public void UpdatecircularuniversexLocation() {
        if (this.location.x + this.speed.x > this.max_x_location) {
            this.location.x = this.min_x_location + (this.speed.x - (this.max_x_location - this.location.x));
        } else if (this.location.x + this.speed.x < this.min_x_location) {
            this.location.x = this.max_x_location + (this.speed.x + this.location.x);
        } else {
            this.location.x += this.speed.x;
        }
    }
    public Boolean CanUpdatecircularuniverseyLocation() {
        return true;
    }
    public void UpdatecircularuniverseyLocation() {
        if (this.location.y + this.speed.y > this.max_y_location) {
            this.location.y = this.min_y_location + (this.speed.y - (this.max_y_location - this.location.y));
        } else if (this.location.x + this.speed.x < this.min_x_location) {
            this.location.y = this.max_y_location + (this.speed.y + this.location.y);
        } else {
            this.location.y += this.speed.y;
        }
    }
    public Boolean[] CanUpdateLocation(Integer x,Integer y) {
        switch (this.out_of_bounds_move_type) {
            case Bounceable:
                return CanUpdatecircularuniverseLocation(x,y);
            case Respawnable:
                return CanUpdaterespawnableLocation(x,y);
            case Impossible:
                return CanUpdateimpossibleLocation(x,y);
            case Destroyable:
                return CanUpdatedestroyableLocation(x,y);
            case Possible:
                return CanUpdatepossibleLocation(x,y);
            case Farest:
                return CanUpdatefarestLocation(x,y);
            case Circular_universe:
                return CanUpdatecircularuniverseLocation(x,y);
        }
        Boolean[] no_out_of_bounds_move_type = {false,false};
        return no_out_of_bounds_move_type;
    }
    public void UpdateLocation(Integer x,Integer y) {
        if ( x!=0 || y!=0 ){
            this.playing_field.DeleteGame_object(this);
            switch (this.out_of_bounds_move_type) {
                case Bounceable:
                    UpdatebounceableLocation(x,y);
                    break;
                case Respawnable:
                    UpdaterespawnableLocation(x,y);
                    break;
                case Impossible:
                    UpdateimpossibleLocation(x,y);
                    break;
                case Destroyable:
                    try {
                        UpdatedestroyableLocation(x,y);
                    } catch (IOException ex) {
                        Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case Possible:
                    UpdatepossibleLocation(x,y);
                    break;
                case Farest:
                    UpdatefarestLocation(x,y);
                    break;
                case Circular_universe:
                    UpdatecircularuniverseLocation(x,y);
                    break;
            }
            this.playing_field.AddGame_object(this);
        }
    }
    public Boolean[] CanUpdatebounceableLocation(Integer x,Integer y) {
        Boolean[] result = {CanUpdatebounceablexLocation(x,y),CanUpdatebounceableyLocation(x,y)};
        return result;
    }
    public void UpdatebounceableLocation(Integer x,Integer y) {
        UpdatebounceablexLocation(x,y);
        UpdatebounceableyLocation(x,y);
    }
    public Boolean CanUpdatebounceablexLocation(Integer x,Integer y) {
        Integer resultcoord;
        return !((resultcoord = this.location.x + x) > this.max_x_location || resultcoord < this.min_x_location);
    }
    public void UpdatebounceablexLocation(Integer x,Integer y) {
        if (this.location.x + x > this.max_x_location) {
            this.location.x -= (x - (this.max_x_location - this.location.x));
        } else if (this.location.x + x < this.min_y_location) {
            this.location.x -= (x + this.location.x);
        } else {
            this.location.x += x;
        }
    }
    public Boolean CanUpdatebounceableyLocation(Integer x,Integer y) {
        Integer resultcoord;
        return !((resultcoord = this.location.y + y) > this.max_y_location || resultcoord < this.min_y_location);
    }
    public void UpdatebounceableyLocation(Integer x,Integer y) {
        if (this.location.y + y > this.max_y_location) {
            this.location.y -= (y - (this.max_y_location - this.location.y));
        } else if (this.location.y + y < this.min_y_location) {
            this.location.y -= (y + this.location.y);
        } else {
            this.location.y += y;
        }
    }
    public Boolean[] CanUpdaterespawnableLocation(Integer x,Integer y) {
        Boolean[] result = {CanUpdaterespawnablexLocation(x,y),CanUpdaterespawnableyLocation(x,y)};
        return result;
    }
    public void UpdaterespawnableLocation(Integer x,Integer y) {
        UpdaterespawnablexLocation(x,y);
        UpdaterespawnableyLocation(x,y);
    }
    public Boolean CanUpdaterespawnablexLocation(Integer x,Integer y) {
        Integer resultcoord;
        return !((resultcoord = this.location.x + x) > this.max_x_location || resultcoord < this.min_x_location);
    }
    public void UpdaterespawnablexLocation(Integer x,Integer y) {
        if (this.location.x + x > this.max_x_location || this.location.x + x < this.min_x_location) {
            this.location.x = Mathcustomfuncs.random(this.min_x_respawn_area, this.max_x_respawn_area).intValue();
        } else {
            this.location.x += x;
        }
    }
    public Boolean CanUpdaterespawnableyLocation(Integer x,Integer y) {
        Integer resultcoord;
        return !((resultcoord = this.location.y + y) > this.max_y_location || resultcoord < this.min_y_location);
    }
    public void UpdaterespawnableyLocation(Integer x,Integer y) {
        if (this.location.y + y > this.max_y_location || this.location.y + y < this.min_y_location) {
            this.location.y = Mathcustomfuncs.random(this.min_y_respawn_area, this.max_y_respawn_area).intValue();
        } else {
            this.location.y += y;
        }
    }
    public Boolean[] CanUpdateimpossibleLocation(Integer x,Integer y) {
        Boolean[] result = {CanUpdateimpossiblexLocation(x,y),CanUpdateimpossibleyLocation(x,y)};
        return result;
    }
    public void UpdateimpossibleLocation(Integer x,Integer y) {
        UpdateimpossiblexLocation(x,y);
        UpdateimpossibleyLocation(x,y);
    }
    public Boolean CanUpdateimpossiblexLocation(Integer x,Integer y) {
        Integer resultcoord;
        return !((resultcoord = this.location.x + x) > this.max_x_location || resultcoord < this.min_x_location);
    }
    public void UpdateimpossiblexLocation(Integer x,Integer y) {
        this.location.x += (this.location.x + x > this.max_x_location || this.location.x + x < this.min_x_location) ? 0 : x;
    }
    public Boolean CanUpdateimpossibleyLocation(Integer x,Integer y) {
        Integer resultcoord;
        return !((resultcoord = this.location.y + y) > this.max_y_location || resultcoord < this.min_y_location);
    }
    public void UpdateimpossibleyLocation(Integer x,Integer y) {
        this.location.y += (this.location.y + y > this.max_y_location || this.location.y + y < this.min_y_location) ? 0 : y;
    }
    public Boolean[] CanUpdatedestroyableLocation(Integer x,Integer y) {
        Boolean[] result = {CanUpdatedestroyablexLocation(x,y),CanUpdatedestroyableyLocation(x,y)};
        return result;
    }
    public void UpdatedestroyableLocation(Integer x,Integer y) throws IOException {
        UpdatedestroyablexLocation(x,y);
        UpdatedestroyableyLocation(x,y);
    }
    public Boolean CanUpdatedestroyablexLocation(Integer x,Integer y) {
        Integer resultcoord;
        return !((resultcoord = this.location.x + x) > this.max_x_location || resultcoord < this.min_x_location);
    }
    public void UpdatedestroyablexLocation(Integer x,Integer y) throws IOException {
        if (this.location.x + x > this.max_x_location || this.location.x + x < this.min_x_location) {
            this.close();
        } else {
            this.location.x += x;
        }
    }
    public Boolean CanUpdatedestroyableyLocation(Integer x,Integer y) {
        Integer resultcoord;
        return !((resultcoord = this.location.y + y) > this.max_y_location || resultcoord < this.min_y_location);
    }
    public void UpdatedestroyableyLocation(Integer x,Integer y) throws IOException {
        if (this.location.y + y > this.max_y_location || this.location.y + y < this.min_y_location) {
            this.close();
        } else {
            this.location.y += y;
        }
    }
    public Boolean[] CanUpdatepossibleLocation(Integer x,Integer y) {
        Boolean[] result = {CanUpdatepossiblexLocation(x,y),CanUpdatepossibleyLocation(x,y)};
        return result;
    }
    public void UpdatepossibleLocation(Integer x,Integer y) {
        UpdatepossiblexLocation(x,y);
        UpdatepossibleyLocation(x,y);
    }
    public Boolean CanUpdatepossiblexLocation(Integer x,Integer y) {
        return true;
    }
    public void UpdatepossiblexLocation(Integer x,Integer y) {
        this.location.x += x;
    }
    public Boolean CanUpdatepossibleyLocation(Integer x,Integer y) {
        return true;
    }
    public void UpdatepossibleyLocation(Integer x,Integer y) {
        this.location.y += y;
    }
    public Boolean[] CanUpdatefarestLocation(Integer x,Integer y) {
        Boolean[] result = {CanUpdatefarestxLocation(x,y),CanUpdatefarestyLocation(x,y)};
        return result;
    }
    public void UpdatefarestLocation(Integer x,Integer y) {
        UpdatefarestxLocation(x,y);
        UpdatefarestyLocation(x,y);
    }
    public Boolean CanUpdatefarestxLocation(Integer x,Integer y) {
        Integer resultcoord;
        return !((resultcoord = this.location.x + x) > this.max_x_location || resultcoord < this.min_x_location);
    }
    public void UpdatefarestxLocation(Integer x,Integer y) {
        if (this.location.x + x > this.max_x_location) {
            this.location.x = this.max_x_location;
        } else if(this.location.x + x < this.min_x_location){
            this.location.x = this.min_x_location;
        } else {
            this.location.x += x;
        }
    }
    public Boolean CanUpdatefarestyLocation(Integer x,Integer y) {
        Integer resultcoord;
        return !((resultcoord = this.location.y + y) > this.max_y_location || resultcoord < this.min_y_location);
    }
    public void UpdatefarestyLocation(Integer x,Integer y) {
        if (this.location.y + y > this.max_y_location) {
            this.location.y = this.max_y_location;
        } else if(this.location.y + y < this.min_y_location){
            this.location.y = this.min_y_location;
        } else {
            this.location.y += y;
        }
    }
    public Boolean[] CanUpdatecircularuniverseLocation(Integer x,Integer y) {
        Boolean[] result = {CanUpdatecircularuniversexLocation(x,y),CanUpdatecircularuniverseyLocation(x,y)};
        return result;
    }
    public void UpdatecircularuniverseLocation(Integer x,Integer y) {
        UpdatecircularuniversexLocation(x,y);
        UpdatecircularuniverseyLocation(x,y);
    }
    public Boolean CanUpdatecircularuniversexLocation(Integer x,Integer y) {
        return true;
    }
    public void UpdatecircularuniversexLocation(Integer x,Integer y) {
        if (this.location.x + x > this.max_x_location) {
            this.location.x = this.min_x_location + (x - (this.max_x_location - this.location.x));
        } else if (this.location.x + x < this.min_x_location) {
            this.location.x = this.max_x_location + (x + this.location.x);
        } else {
            this.location.x += x;
        }
    }
    public Boolean CanUpdatecircularuniverseyLocation(Integer x,Integer y) {
        return true;
    }
    public void UpdatecircularuniverseyLocation(Integer x,Integer y) {
        if (this.location.y + y > this.max_y_location) {
            this.location.y = this.min_y_location + (y - (this.max_y_location - this.location.y));
        } else if (this.location.x + x < this.min_x_location) {
            this.location.y = this.max_y_location + (y + this.location.y);
        } else {
            this.location.y += y;
        }
    }
    @Override
    public void close() throws IOException {
        this.playing_field.DeleteGame_object(this);
    }
}
