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
 * Object to use in the game
 *
 * @author alumno1718_2
 */
public class Gameobject implements Closeable {

    public Coordinate location;
    public Integer height;
    public Integer array_position;
    public Speed speed;
    public Field playing_field;
    public Rectangular_area posible_location_area;
    public Rectangular_area respawn_area;
    public Log_level log_level;
    public char character;
    public Out_of_bounds_move_type out_of_bounds_move_type;
    public Physical_state_type physical_state_type;
    public Move_type move_type;
    public String object_type;
    public Collision_type receiving_collision;
    public Collision_type giving_collision;
    public Integer move_direction_vertical;
    public Integer move_direction_horizontal;

    /**
     * Creates a basic game object , should be overriden
     *
     * @param field The field where the object is located
     */
    public Gameobject(Field field) {
        this.character = '|';
        this.height = 0;
        this.object_type = "Default";
        this.speed = new Speed(0, 0);
        this.move_type = Move_type.None;
        this.playing_field = field;
        this.posible_location_area = new Rectangular_area(field.x_size - 1, 0, field.y_size - 1, 0);
        this.respawn_area = new Rectangular_area(field.x_size - 1, 0, field.y_size - 1, 0);
        this.physical_state_type = Physical_state_type.Ghost;
        this.out_of_bounds_move_type = Out_of_bounds_move_type.Circular_universe;
        this.log_level = Log_level.None;
        this.location = new Coordinate(this.posible_location_area.min_coord.x, this.posible_location_area.min_coord.y);
    }

    /**
     * Creates a respawn point to use when it's going to be respawned
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public void RespawnPoint(Integer x, Integer y) {
        this.respawn_area = new Rectangular_area(x, x, y, y);
    }

    /**
     * Creates a respawn area to use when it's going to be respawned
     *
     * @param max_x Maximum x location of the area
     * @param min_x Minumum x location of the area
     * @param max_y Maximum y location of the area
     * @param min_y Minumum y location of the area
     */
    public void RespawnArea(Integer max_x, Integer min_x, Integer max_y, Integer min_y) {
        this.respawn_area = new Rectangular_area(max_x, min_x, max_y, min_y);
    }

    /**
     * Logs the data in the object acording to Log_level
     */
    public void log() {
        StringBuilder object_log = new StringBuilder();
        object_log.append("Object: ");
        object_log.append(this.hashCode());
        switch (this.log_level) {
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
                object_log.append("\nmax_x_location = ");
                object_log.append(this.posible_location_area.max_coord.x);
                object_log.append("\nmin_x_location = ");
                object_log.append(this.posible_location_area.min_coord.x);
                object_log.append("\nmax_y_location = ");
                object_log.append(this.posible_location_area.max_coord.y);
                object_log.append("\nmin_y_location = ");
                object_log.append(this.posible_location_area.min_coord.y);
                object_log.append("\nmax_x_respawn_area = ");
                object_log.append(this.respawn_area.max_coord.x);
                object_log.append("\nmin_x_respawn_area = ");
                object_log.append(this.respawn_area.min_coord.x);
                object_log.append("\nmax_y_respawn_area = ");
                object_log.append(this.respawn_area.max_coord.y);
                object_log.append("\nmin_y_respawn_area = ");
                object_log.append(this.respawn_area.min_coord.y);
                object_log.append("\nout_of_bounds_move_type = ");
                object_log.append(this.out_of_bounds_move_type);
            case Collision_related:
                object_log.append("\ncollision_type = ");
                object_log.append(this.physical_state_type);
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

    /**
     * Teleports an object to a location
     *
     * @param coord The coordinate you want to move this object to
     * @throws ImpossibleLocationRemoveException
     * @throws ImpossibleLocationAddException
     */
    public void move_to(Coordinate coord) throws ImpossibleLocationRemoveException, ImpossibleLocationAddException {
        this.playing_field.DeleteGame_object(this);
        this.location.x = coord.x;
        this.location.y = coord.y;
        this.playing_field.AddGame_object(this);
    }

    /**
     * Checks if location can be updated
     *
     * @return Returns an array contaning if x and y can be updated
     */
    public Boolean CanUpdateLocation() {
        switch (this.out_of_bounds_move_type) {
            case Bounceable:
                return CanUpdatebounceableLocation();
            case Respawnable:
                return CanUpdaterespawnableLocation();
            case Impossible:
            //return CanUpdateimpossibleLocation();
            case Destroyable:
            //return CanUpdatedestroyableLocation();
            case Possible:
            //return CanUpdatepossibleLocation();
            case Farest:
            //return CanUpdatefarestLocation();
            case Circular_universe:
            //return CanUpdatecircularuniverseLocation();
        }
        return false;
    }

    /**
     * Updates location in the object and the field
     */
    public void UpdateLocation() {
        if (this.speed.x != 0 || this.speed.y != 0) {
            switch (this.out_of_bounds_move_type) {
                case Bounceable:
                    UpdatebounceableLocation();
                    break;
                case Respawnable:
                    UpdaterespawnableLocation();
                    break;
                case Impossible:
                    //UpdateimpossibleLocation();
                    break;
                case Destroyable:
                    //try {
                    //UpdatedestroyableLocation();
                    //} catch (IOException ex) {
                    //    Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
                    //}
                    break;
                case Possible:
                    //UpdatepossibleLocation();
                    break;
                case Farest:
                    //UpdatefarestLocation();
                    break;
                case Circular_universe:
                    //UpdatecircularuniverseLocation();
                    break;
            }
        }
    }
    
    /**
     * Checks if it will bounce when it moves
     *
     * @return A boolean containing the result
     */
    public Boolean WillBounceAgainstBounds() {
        Coordinate destiny_location = new Coordinate(this.location.x, this.location.y);
        switch (this.move_type) {
            case Teleport:
                Rectangular_area possible_bounce_area = new Rectangular_area(this.playing_field.x_size - 1, 0, this.playing_field.y_size - 1, 0).CommonArea(this.posible_location_area);
                Gameobject collisionreceiver;
                while ((collisionreceiver = this.playing_field.collidesWith(this)) != null) {
                    this.playing_field.processCollision(this, collisionreceiver);
                }
                if ((destiny_location.x += this.speed.x) > possible_bounce_area.max_coord.x || destiny_location.x < possible_bounce_area.min_coord.x || (destiny_location.y += this.speed.y) > possible_bounce_area.max_coord.y || destiny_location.y < possible_bounce_area.min_coord.y) {
                    return true;
                }
                return false;
            case None:
                return false;
            default:
                Speed moving_speed = new Speed(new Float(Math.signum(this.speed.x)).intValue(), new Float(Math.signum(this.speed.x)).intValue());
                Speed remaining_speed = new Speed(this.speed.x, this.speed.y);
                switch (this.move_type) {
                    case Horizontal_first:
                        for (; remaining_speed.x != 0; destiny_location.x += moving_speed.x, remaining_speed.x -= moving_speed.x) {
                            if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {

                            } else {
                                return true;
                            }
                        }
                        for (; remaining_speed.y != 0; destiny_location.y += moving_speed.y, remaining_speed.y -= moving_speed.y) {
                            if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {

                            } else {
                                return true;
                            }
                        }
                        return false;
                    case Vertical_first:
                        for (; remaining_speed.y != 0; destiny_location.y += moving_speed.y, remaining_speed.y -= moving_speed.y) {
                            if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {

                            } else {
                                return true;
                            }
                        }
                        for (; remaining_speed.x != 0; destiny_location.x += moving_speed.x, remaining_speed.x -= moving_speed.x) {
                            if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                            } else {
                                return true;
                            }
                        }
                        return false;
                    case Diagonal:
                        if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.x) >= Math.abs(this.speed.y)) {
                            Integer times = Math.abs(this.speed.x / this.speed.y);
                            for (; remaining_speed.y != 0; destiny_location.y += moving_speed.y) {
                                if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                    remaining_speed.y -= moving_speed.y;
                                } else {
                                    return true;
                                }
                                for (Integer i = 0; i < times; i++, destiny_location.x += moving_speed.x) {
                                    if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                        remaining_speed.x -= moving_speed.x;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                            for (; remaining_speed.x != 0; destiny_location.x += moving_speed.x) {
                                if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                    remaining_speed.x -= moving_speed.x;
                                } else {
                                    return true;
                                }
                            }
                            return false;
                        } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y) >= Math.abs(this.speed.x)) {
                            Integer times = Math.abs(this.speed.y / this.speed.x);
                            for (; remaining_speed.x != 0; destiny_location.x += moving_speed.x) {
                                if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                    remaining_speed.x -= moving_speed.x;
                                } else {
                                    return true;
                                }
                                for (Integer i = 0; i < times; i++, destiny_location.x += moving_speed.y) {
                                    if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                        remaining_speed.y -= moving_speed.y;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                            for (; remaining_speed.y != 0; destiny_location.y += moving_speed.y) {
                                if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                    remaining_speed.y -= moving_speed.y;
                                } else {
                                    return true;
                                }
                            }
                            return false;
                        } else {
                            do {
                                destiny_location.x += moving_speed.x;
                                destiny_location.y += moving_speed.y;
                                remaining_speed.x -= moving_speed.x;
                                remaining_speed.y -= moving_speed.y;
                                if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {

                                } else {
                                    return true;
                                }
                            } while (remaining_speed.x != 0 || remaining_speed.x != 0);
                            return false;
                        }
                }
        }
        return false;
    }

    /**
     * Checks if and UpdatebounceableLocation can be done
     *
     * @return A boolean containing the result
     */
    public Boolean CanUpdatebounceableLocation() {
        Coordinate destiny_location = new Coordinate(this.location.x, this.location.y);
        switch (this.move_type) {
            case Teleport:
                Integer bouncing_space,
                 teorical_destiny;
                Rectangular_area possible_bounce_area = new Rectangular_area(this.playing_field.x_size - 1, 0, this.playing_field.y_size - 1, 0).CommonArea(this.posible_location_area);
                destiny_location.x = (((this.speed.x = ((teorical_destiny = destiny_location.x + this.speed.x) / (bouncing_space = possible_bounce_area.max_coord.x - possible_bounce_area.min_coord.x) % 2 != 0) ? -this.speed.x : this.speed.x) > 0) ? possible_bounce_area.min_coord.x : possible_bounce_area.max_coord.x) + teorical_destiny % bouncing_space * new Float(Math.signum(this.speed.x)).intValue();
                destiny_location.y = (((this.speed.y = ((teorical_destiny = destiny_location.y + this.speed.y) / (bouncing_space = possible_bounce_area.max_coord.y - possible_bounce_area.min_coord.y) % 2 != 0) ? -this.speed.y : this.speed.y) > 0) ? possible_bounce_area.min_coord.y : possible_bounce_area.max_coord.y) + teorical_destiny % bouncing_space * new Float(Math.signum(this.speed.y)).intValue();
                return this.playing_field.CanRelocateGame_object(this, destiny_location);
            case None:
                return this.playing_field.CanRelocateGame_object(this, destiny_location);
            default:
                Speed moving_speed = new Speed(new Float(Math.signum(this.speed.x)).intValue(), new Float(Math.signum(this.speed.x)).intValue());
                Speed remaining_speed = new Speed(this.speed.x, this.speed.y);
                switch (this.move_type) {
                    case Horizontal_first:
                        for (; remaining_speed.x != 0; destiny_location.x += moving_speed.x, remaining_speed.x -= moving_speed.x) {
                            if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {

                            } else {
                                moving_speed.x *= -1;
                                remaining_speed.x *= -1;
                                destiny_location.x += moving_speed.x;
                            }
                        }
                        for (; remaining_speed.y != 0; destiny_location.y += moving_speed.y, remaining_speed.y -= moving_speed.y) {
                            if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {

                            } else {
                                moving_speed.y *= -1;
                                remaining_speed.y *= -1;
                                destiny_location.y += moving_speed.y;
                            }
                        }
                        return this.playing_field.CanRelocateGame_object(this, destiny_location);
                    case Vertical_first:
                        for (; remaining_speed.y != 0; destiny_location.y += moving_speed.y, remaining_speed.y -= moving_speed.y) {
                            if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {

                            } else {
                                moving_speed.y *= -1;
                                remaining_speed.y *= -1;
                                destiny_location.y += moving_speed.y;
                            }
                        }
                        for (; remaining_speed.x != 0; destiny_location.x += moving_speed.x, remaining_speed.x -= moving_speed.x) {
                            if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                            } else {
                                moving_speed.x *= -1;
                                remaining_speed.x *= -1;
                                destiny_location.x += moving_speed.x;
                            }
                        }
                        return this.playing_field.CanRelocateGame_object(this, destiny_location);
                    case Diagonal:
                        if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.x) >= Math.abs(this.speed.y)) {
                            Integer times = Math.abs(this.speed.x / this.speed.y);
                            for (; remaining_speed.y != 0; destiny_location.y += moving_speed.y) {
                                if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                    remaining_speed.y -= moving_speed.y;
                                } else {
                                    moving_speed.y *= -1;
                                    remaining_speed.y *= -1;
                                    destiny_location.y += moving_speed.y;
                                    remaining_speed.y -= moving_speed.y;
                                }
                                for (Integer i = 0; i < times; i++, destiny_location.x += moving_speed.x) {
                                    if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                        remaining_speed.x -= moving_speed.x;
                                    } else {
                                        moving_speed.x *= -1;
                                        remaining_speed.x *= -1;
                                        destiny_location.x += moving_speed.x;
                                        remaining_speed.x -= moving_speed.x;
                                    }
                                }
                            }
                            for (; remaining_speed.x != 0; destiny_location.x += moving_speed.x) {
                                if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                    remaining_speed.x -= moving_speed.x;
                                } else {
                                    moving_speed.x *= -1;
                                    remaining_speed.x *= -1;
                                    destiny_location.x += moving_speed.x;
                                    remaining_speed.x -= moving_speed.x;
                                }
                            }
                            return this.playing_field.CanRelocateGame_object(this, destiny_location);
                        } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y) >= Math.abs(this.speed.x)) {
                            Integer times = Math.abs(this.speed.y / this.speed.x);
                            for (; remaining_speed.x != 0; destiny_location.x += moving_speed.x) {
                                if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                    remaining_speed.x -= moving_speed.x;
                                } else {
                                    moving_speed.x *= -1;
                                    remaining_speed.x *= -1;
                                    destiny_location.x += moving_speed.x;
                                    remaining_speed.x -= moving_speed.x;
                                }
                                for (Integer i = 0; i < times; i++, destiny_location.x += moving_speed.y) {
                                    if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                        remaining_speed.y -= moving_speed.y;
                                    } else {
                                        moving_speed.y *= -1;
                                        remaining_speed.y *= -1;
                                        destiny_location.y += moving_speed.y;
                                        remaining_speed.y -= moving_speed.y;
                                    }
                                }
                            }
                            for (; remaining_speed.y != 0; destiny_location.y += moving_speed.y) {
                                if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
                                    remaining_speed.y -= moving_speed.y;
                                } else {
                                    moving_speed.y *= -1;
                                    remaining_speed.y *= -1;
                                    destiny_location.y += moving_speed.y;
                                    remaining_speed.y -= moving_speed.y;
                                }
                            }
                            return this.playing_field.CanRelocateGame_object(this, destiny_location);
                        } else {
                            do {
                                destiny_location.x += moving_speed.x;
                                destiny_location.y += moving_speed.y;
                                remaining_speed.x -= moving_speed.x;
                                remaining_speed.y -= moving_speed.y;
                                if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {

                                } else {
                                    moving_speed.x *= -1;
                                    remaining_speed.x *= -1;
                                    moving_speed.y *= -1;
                                    remaining_speed.y *= -1;
                                    destiny_location.x += moving_speed.x;
                                    destiny_location.y += moving_speed.y;
                                }
                            } while (remaining_speed.x != 0 || remaining_speed.x != 0);
                            return this.playing_field.CanRelocateGame_object(this, destiny_location);
                        }
                }
        }
        return false;
    }

    public void UpdatebounceableLocation() {
        Coordinate original_location = this.location;
        if (CanUpdatebounceableLocation()) {
            try {
                this.playing_field.DeleteGame_object(this);
            } catch (ImpossibleRelocationException ex) {
                Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
            }
            switch (this.move_type) {
                case Teleport:
                    Integer bouncing_space,
                     teorical_destiny;
                    Rectangular_area possible_bounce_area = new Rectangular_area(this.playing_field.x_size - 1, 0, this.playing_field.y_size - 1, 0).CommonArea(this.posible_location_area);
                    this.location.x = (((this.speed.x = ((teorical_destiny = this.location.x + this.speed.x) / (bouncing_space = possible_bounce_area.max_coord.x - possible_bounce_area.min_coord.x) % 2 != 0) ? -this.speed.x : this.speed.x) > 0) ? possible_bounce_area.min_coord.x : possible_bounce_area.max_coord.x) + teorical_destiny % bouncing_space * new Float(Math.signum(this.speed.x)).intValue();
                    this.location.y = (((this.speed.y = ((teorical_destiny = this.location.y + this.speed.y) / (bouncing_space = possible_bounce_area.max_coord.y - possible_bounce_area.min_coord.y) % 2 != 0) ? -this.speed.y : this.speed.y) > 0) ? possible_bounce_area.min_coord.y : possible_bounce_area.max_coord.y) + teorical_destiny % bouncing_space * new Float(Math.signum(this.speed.y)).intValue();
                    try {
                        this.playing_field.AddGame_object(this);
                    } catch (ImpossibleRelocationException ex) {
                        this.location = original_location;
                        Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case None:
                    try {
                        this.playing_field.AddGame_object(this);
                    } catch (ImpossibleRelocationException ex) {
                        this.location = original_location;
                        Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    Speed moving_speed = new Speed(new Float(Math.signum(this.speed.x)).intValue(), new Float(Math.signum(this.speed.x)).intValue());
                    Speed remaining_speed = new Speed(this.speed.x, this.speed.y);
                    switch (this.move_type) {
                        case Horizontal_first:
                            for (; remaining_speed.x != 0; this.location.x += moving_speed.x, remaining_speed.x -= moving_speed.x) {
                                if (this.playing_field.CanRelocateGame_object(this, this.location)) {

                                } else {
                                    moving_speed.x *= -1;
                                    remaining_speed.x *= -1;
                                    this.location.x += moving_speed.x;
                                }
                            }
                            for (; remaining_speed.y != 0; this.location.y += moving_speed.y, remaining_speed.y -= moving_speed.y) {
                                if (this.playing_field.CanRelocateGame_object(this, this.location)) {

                                } else {
                                    moving_speed.y *= -1;
                                    remaining_speed.y *= -1;
                                    this.location.y += moving_speed.y;
                                }
                            }
                            try {
                                this.playing_field.AddGame_object(this);
                            } catch (ImpossibleRelocationException ex) {
                                this.location = original_location;
                                Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        case Vertical_first:
                            for (; remaining_speed.y != 0; this.location.y += moving_speed.y, remaining_speed.y -= moving_speed.y) {
                                if (this.playing_field.CanRelocateGame_object(this, this.location)) {

                                } else {
                                    moving_speed.y *= -1;
                                    remaining_speed.y *= -1;
                                    this.location.y += moving_speed.y;
                                }
                            }
                            for (; remaining_speed.x != 0; this.location.x += moving_speed.x, remaining_speed.x -= moving_speed.x) {
                                if (this.playing_field.CanRelocateGame_object(this, this.location)) {
                                } else {
                                    moving_speed.x *= -1;
                                    remaining_speed.x *= -1;
                                    this.location.x += moving_speed.x;
                                }
                            }
                            try {
                                this.playing_field.AddGame_object(this);
                            } catch (ImpossibleRelocationException ex) {
                                this.location = original_location;
                                Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        case Diagonal:
                            if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.x) >= Math.abs(this.speed.y)) {
                                Integer times = Math.abs(this.speed.x / this.speed.y);
                                for (; remaining_speed.y != 0; this.location.y += moving_speed.y) {
                                    if (this.playing_field.CanRelocateGame_object(this, this.location)) {
                                        remaining_speed.y -= moving_speed.y;
                                    } else {
                                        moving_speed.y *= -1;
                                        remaining_speed.y *= -1;
                                        this.location.y += moving_speed.y;
                                        remaining_speed.y -= moving_speed.y;
                                    }
                                    for (Integer i = 0; i < times; i++, this.location.x += moving_speed.x) {
                                        if (this.playing_field.CanRelocateGame_object(this, this.location)) {
                                            remaining_speed.x -= moving_speed.x;
                                        } else {
                                            moving_speed.x *= -1;
                                            remaining_speed.x *= -1;
                                            this.location.x += moving_speed.x;
                                            remaining_speed.x -= moving_speed.x;
                                        }
                                    }
                                }
                                for (; remaining_speed.x != 0; this.location.x += moving_speed.x) {
                                    if (this.playing_field.CanRelocateGame_object(this, this.location)) {
                                        remaining_speed.x -= moving_speed.x;
                                    } else {
                                        moving_speed.x *= -1;
                                        remaining_speed.x *= -1;
                                        this.location.x += moving_speed.x;
                                        remaining_speed.x -= moving_speed.x;
                                    }
                                }
                                try {
                                    this.playing_field.AddGame_object(this);
                                } catch (ImpossibleRelocationException ex) {
                                    this.location = original_location;
                                    Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y) >= Math.abs(this.speed.x)) {
                                Integer times = Math.abs(this.speed.y / this.speed.x);
                                for (; remaining_speed.x != 0; this.location.x += moving_speed.x) {
                                    if (this.playing_field.CanRelocateGame_object(this, this.location)) {
                                        remaining_speed.x -= moving_speed.x;
                                    } else {
                                        moving_speed.x *= -1;
                                        remaining_speed.x *= -1;
                                        this.location.x += moving_speed.x;
                                        remaining_speed.x -= moving_speed.x;
                                    }
                                    for (Integer i = 0; i < times; i++, this.location.x += moving_speed.y) {
                                        if (this.playing_field.CanRelocateGame_object(this, this.location)) {
                                            remaining_speed.y -= moving_speed.y;
                                        } else {
                                            moving_speed.y *= -1;
                                            remaining_speed.y *= -1;
                                            this.location.y += moving_speed.y;
                                            remaining_speed.y -= moving_speed.y;
                                        }
                                    }
                                }
                                for (; remaining_speed.y != 0; this.location.y += moving_speed.y) {
                                    if (this.playing_field.CanRelocateGame_object(this, this.location)) {
                                        remaining_speed.y -= moving_speed.y;
                                    } else {
                                        moving_speed.y *= -1;
                                        remaining_speed.y *= -1;
                                        this.location.y += moving_speed.y;
                                        remaining_speed.y -= moving_speed.y;
                                    }
                                }
                                try {
                                    this.playing_field.AddGame_object(this);
                                } catch (ImpossibleRelocationException ex) {
                                    this.location = original_location;
                                    Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                do {
                                    this.location.x += moving_speed.x;
                                    this.location.y += moving_speed.y;
                                    remaining_speed.x -= moving_speed.x;
                                    remaining_speed.y -= moving_speed.y;
                                    if (this.playing_field.CanRelocateGame_object(this, this.location)) {

                                    } else {
                                        moving_speed.x *= -1;
                                        remaining_speed.x *= -1;
                                        moving_speed.y *= -1;
                                        remaining_speed.y *= -1;
                                        this.location.x += moving_speed.x;
                                        this.location.y += moving_speed.y;
                                    }
                                } while (remaining_speed.x != 0 || remaining_speed.x != 0);
                                try {
                                    this.playing_field.AddGame_object(this);
                                } catch (ImpossibleRelocationException ex) {
                                    this.location = original_location;
                                    Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                    }
            }
        }
    }

    /**
     * Respawns the object
     */
    public void Respawn() {
        Coordinate original_location = this.location;
        try {
            this.playing_field.DeleteGame_object(this);
        } catch (ImpossibleLocationRemoveException ex) {
            Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
        }
        Rectangular_area area = this.respawn_area.CommonArea(this.posible_location_area).CommonArea(new Rectangular_area(0, this.playing_field.x_size - 1, 0, this.playing_field.y_size - 1));
        do {
        } while (!this.playing_field.CanRelocateGame_object(this, this.location = new Coordinate(Mathcustomfuncs.random(area.min_coord.x, area.max_coord.x).intValue(), Mathcustomfuncs.random(area.min_coord.y, area.max_coord.y).intValue())));
        try {
            this.playing_field.AddGame_object(this);
        } catch (ImpossibleRelocationException ex) {
            this.location = original_location;
            Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Checks if it will respawn when it moves
     *
     * @return A boolean containing the result
     */
    public Boolean WillRespawn() {
        Coordinate destiny_location = new Coordinate(this.location.x, this.location.y);
        switch (this.move_type) {
            case None:
                return false;
            default:
                Rectangular_area possible_area = new Rectangular_area(this.playing_field.x_size - 1, 0, this.playing_field.y_size - 1, 0).CommonArea(this.posible_location_area);
                if ((destiny_location.x += this.speed.x) > possible_area.max_coord.x || destiny_location.x < possible_area.min_coord.x || (destiny_location.y += this.speed.y) > possible_area.max_coord.y || destiny_location.y < possible_area.min_coord.y) {
                    return true;
                }
                return false;
        }
    }

    /**
     * Checks if and UpdaterespawnableLocation can be done
     *
     * @return A boolean containing the result
     */
    public Boolean CanUpdaterespawnableLocation() {
        if (this.WillRespawn()) {
            Rectangular_area area = this.respawn_area.CommonArea(this.posible_location_area).CommonArea(new Rectangular_area(0, this.playing_field.x_size - 1, 0, this.playing_field.y_size - 1));
            Boolean can_relocate = false;
            for (Integer i = 0; i < 200 || can_relocate; i++) {
                can_relocate = this.playing_field.CanRelocateGame_object(this, new Coordinate(Mathcustomfuncs.random(area.min_coord.x, area.max_coord.x).intValue(), Mathcustomfuncs.random(area.min_coord.y, area.max_coord.y).intValue()));
            }
            if (can_relocate) {
                return true;
            }
        }
        return false;
    }

    public void UpdaterespawnableLocation() {
        Coordinate original_location = this.location;
        try {
            this.playing_field.DeleteGame_object(this);
        } catch (ImpossibleLocationRemoveException ex) {
            Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.WillRespawn()) {
            Rectangular_area area = this.respawn_area.CommonArea(this.posible_location_area).CommonArea(new Rectangular_area(0, this.playing_field.x_size - 1, 0, this.playing_field.y_size - 1));
            do {
            } while (!this.playing_field.CanRelocateGame_object(this, this.location = new Coordinate(Mathcustomfuncs.random(area.min_coord.x, area.max_coord.x).intValue(), Mathcustomfuncs.random(area.min_coord.y, area.max_coord.y).intValue())));
        }
        try {
            this.playing_field.AddGame_object(this);
        } catch (ImpossibleRelocationException ex) {
            this.location = original_location;
            Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * public Boolean CanUpdateimpossibleLocation() { Coordinate
     * destiny_location = new Coordinate(this.location.x, this.location.y);
     * Rectangular_area possible_area = new
     * Rectangular_area(this.playing_field.x_size - 1, 0,
     * this.playing_field.y_size - 1, 0).CommonArea(this.posible_location_area);
     * switch (this.move_type) { case Teleport: if ((destiny_location.x =
     * destiny_location.x + this.speed.x) > possible_area.max_coord.x) {
     * destiny_location.x = possible_area.max_coord.x; } else if
     * (destiny_location.x < possible_area.min_coord.x) {
     * destiny_location.x = possible_area.min_coord.x;
     * }
     * if ((destiny_location.y = destiny_location.y + this.speed.y) >
     * possible_area.max_coord.y) { destiny_location.y =
     * possible_area.max_coord.y; } else if (destiny_location.y < possible_area.min_coord.y) {
     * destiny_location.y = possible_area.min_coord.y;
     * }
     * return this.playing_field.CanRelocateGame_object(this, destiny_location);
     * case None:
     * return this.playing_field.CanRelocateGame_object(this, destiny_location);
     * default:
     * Speed moving_speed = new Speed(new Float(Math.signum(this.speed.x)).intValue(), new Float(Math.signum(this.speed.x)).intValue());
     * Speed remaining_speed = new Speed(this.speed.x, this.speed.y);
     * Boolean can_relocate = true;
     * switch (this.move_type) {
     * case Horizontal_first:
     * for (; remaining_speed.x != 0 && (can_relocate = this.playing_field.CanRelocateGame_object(this, destiny_location)); destiny_location.x += moving_speed.x, remaining_speed.x -= moving_speed.x) {
     * }
     * destiny_location.x = (can_relocate) ? destiny_location.x : (this.speed.x
     * > 0) ? possible_area.max_coord.x : possible_area.min_coord.x; for (;
     * remaining_speed.y != 0 && (can_relocate =
     * this.playing_field.CanRelocateGame_object(this, destiny_location));
     * destiny_location.y += moving_speed.y, remaining_speed.y -=
     * moving_speed.y) { } destiny_location.y = (can_relocate) ?
     * destiny_location.y : (this.speed.y > 0) ? possible_area.max_coord.y :
     * possible_area.min_coord.y; return
     * this.playing_field.CanRelocateGame_object(this, destiny_location); case
     * Vertical_first: for (; remaining_speed.y != 0 && (can_relocate =
     * this.playing_field.CanRelocateGame_object(this, destiny_location));
     * destiny_location.y += moving_speed.y, remaining_speed.y -=
     * moving_speed.y) { } destiny_location.y = (can_relocate) ?
     * destiny_location.y : (this.speed.y > 0) ? possible_area.max_coord.y :
     * possible_area.min_coord.y; for (; remaining_speed.x != 0 && (can_relocate
     * = this.playing_field.CanRelocateGame_object(this, destiny_location));
     * destiny_location.x += moving_speed.x, remaining_speed.x -=
     * moving_speed.x) { } destiny_location.x = (can_relocate) ?
     * destiny_location.x : (this.speed.x > 0) ? possible_area.max_coord.x :
     * possible_area.min_coord.x; return
     * this.playing_field.CanRelocateGame_object(this, destiny_location); case
     * Diagonal: if (this.speed.x != 0 && this.speed.y != 0 &&
     * Math.abs(this.speed.x) >= Math.abs(this.speed.y)) { Integer times =
     * Math.abs(this.speed.x / this.speed.y); for (; remaining_speed.y != 0;
     * destiny_location.y += moving_speed.y) { if
     * (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
     * remaining_speed.y -= moving_speed.y; } else { moving_speed.y *= -1;
     * remaining_speed.y *= -1; destiny_location.y += moving_speed.y;
     * remaining_speed.y -= moving_speed.y; } for (Integer i = 0; i < times; i++, destiny_location.x += moving_speed.x) {
     * if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
     * remaining_speed.x -= moving_speed.x;
     * } else {
     * moving_speed.x *= -1;
     * remaining_speed.x *= -1;
     * destiny_location.x += moving_speed.x;
     * remaining_speed.x -= moving_speed.x;
     * }
     * }
     * }
     * for (; remaining_speed.x != 0; destiny_location.x += moving_speed.x) {
     * if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
     * remaining_speed.x -= moving_speed.x;
     * } else {
     * moving_speed.x *= -1;
     * remaining_speed.x *= -1;
     * destiny_location.x += moving_speed.x;
     * remaining_speed.x -= moving_speed.x;
     * }
     * }
     * return this.playing_field.CanRelocateGame_object(this, destiny_location);
     * } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y)
     * >= Math.abs(this.speed.x)) { Integer times = Math.abs(this.speed.y /
     * this.speed.x); for (; remaining_speed.x != 0; destiny_location.x +=
     * moving_speed.x) { if (this.playing_field.CanRelocateGame_object(this,
     * destiny_location)) { remaining_speed.x -= moving_speed.x; } else {
     * moving_speed.x *= -1; remaining_speed.x *= -1; destiny_location.x +=
     * moving_speed.x; remaining_speed.x -= moving_speed.x; } for (Integer i =
     * 0; i < times; i++, destiny_location.x += moving_speed.y) {
     * if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
     * remaining_speed.y -= moving_speed.y;
     * } else {
     * moving_speed.y *= -1;
     * remaining_speed.y *= -1;
     * destiny_location.y += moving_speed.y;
     * remaining_speed.y -= moving_speed.y;
     * }
     * }
     * }
     * for (; remaining_speed.y != 0; destiny_location.y += moving_speed.y) {
     * if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
     * remaining_speed.y -= moving_speed.y;
     * } else {
     * moving_speed.y *= -1;
     * remaining_speed.y *= -1;
     * destiny_location.y += moving_speed.y;
     * remaining_speed.y -= moving_speed.y;
     * }
     * }
     * return this.playing_field.CanRelocateGame_object(this, destiny_location);
     * } else {
     * do {
     * destiny_location.x += moving_speed.x;
     * destiny_location.y += moving_speed.y;
     * remaining_speed.x -= moving_speed.x;
     * remaining_speed.y -= moving_speed.y;
     * if (this.playing_field.CanRelocateGame_object(this, destiny_location)) {
     *
     * } else {
     * moving_speed.x *= -1;
     * remaining_speed.x *= -1;
     * moving_speed.y *= -1;
     * remaining_speed.y *= -1;
     * destiny_location.x += moving_speed.x;
     * destiny_location.y += moving_speed.y;
     * }
     * } while (remaining_speed.x != 0 || remaining_speed.x != 0);
     * return this.playing_field.CanRelocateGame_object(this, destiny_location);
     * }
     * }
     * }
     * return false;
     * }
     *
     * public void UpdateimpossibleLocation() {
     * UpdateimpossiblexLocation();
     * UpdateimpossibleyLocation();
     * }
     *
     * public void UpdateimpossiblexLocation() {
     * this.location.x += (this.location.x + this.speed.x >
     * this.posible_location_area.max_coord.x || this.location.x + this.speed.x < this.posible_location_area.min_coord.x) ? 0 : this.speed.x;
     * }
     *
     * public void UpdateimpossibleyLocation() {
     * this.location.y += (this.location.y + this.speed.y >
     * this.posible_location_area.max_coord.y || this.location.y + this.speed.y
     * < this.posible_location_area.min_coord.y) ? 0 : this.speed.y; }
     *
     * /**
     * Checks if and UpdatedestroyableLocation can be done
     *
     * @return A boolean containing the result public Boolean
     * CanUpdatedestroyableLocation() { Coordinate destiny_location = new
     * Coordinate(this.location.x + this.speed.x, this.location.y +
     * this.speed.y); Speed moving_speed; switch (this.move_type) { case
     * Teleport: return this.playing_field.CanRelocateGame_object(this,
     * destiny_location); case Horizontal_first: moving_speed = new
     * Speed((destiny_location.x > this.location.x) ? 1 : -1,
     * (destiny_location.y > this.location.y) ? 1 : -1); for (;
     * !Objects.equals(this.location.x, destiny_location.x); this.location.x +=
     * moving_speed.x) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } } for (;
     * !Objects.equals(this.location.y, destiny_location.y); this.location.y +=
     * moving_speed.y) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } } return true; case Vertical_first:
     * moving_speed = new Speed((destiny_location.x > this.location.x) ? 1 : -1,
     * (destiny_location.y > this.location.y) ? 1 : -1); for (;
     * !Objects.equals(this.location.y, destiny_location.y); this.location.y +=
     * moving_speed.y) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } } for (;
     * !Objects.equals(this.location.x, destiny_location.x); this.location.x +=
     * moving_speed.x) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } } return true; case Diagonal: if
     * (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.x) >=
     * Math.abs(this.speed.y)) { Integer times = Math.abs(this.speed.x /
     * this.speed.y); Integer x_direction = (this.speed.x < 0) ? -1 : 1; Integer
     * y_direction = (this.speed.y < 0) ? -1 : 1; for (;
     * !Objects.equals(this.location.y, destiny_location.y); this.location.y +=
     * y_direction) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } for (Integer i = 0; i < times; i++, this.location.x += x_direction) {
     * if (!this.playing_field.CanRelocateGame_object(this, this.location)) {
     * return false;
     * }
     * }
     * }
     * for (; !Objects.equals(this.location.x, destiny_location.x); this.location.x += x_direction) {
     * if (!this.playing_field.CanRelocateGame_object(this, this.location)) {
     * return false;
     * }
     * }
     * } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y)
     * >= Math.abs(this.speed.x)) { Integer times = Math.abs(this.speed.y /
     * this.speed.x); Integer y_direction = (this.speed.y < 0) ? -1 : 1; Integer
     * x_direction = (this.speed.x < 0) ? -1 : 1; for (;
     * !Objects.equals(this.location.x, destiny_location.x); this.location.x +=
     * x_direction) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } for (Integer i = 0; i < times; i++,
     * this.location.x += y_direction) { if
     * (!this.playing_field.CanRelocateGame_object(this, this.location)) {
     * return false; } } } for (; !Objects.equals(this.location.y,
     * destiny_location.y); this.location.y += y_direction) { if
     * (!this.playing_field.CanRelocateGame_object(this, this.location)) {
     * return false; } } } else { Integer x_direction = (this.speed.x < 0) ? -1
     * : 1; for (Integer i = 0; i < this.speed.x; i++, this.location.x +=
     * x_direction) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } } Integer y_direction = (this.speed.y <
     * 0) ? -1 : 1; for (Integer i = 0; i < this.speed.y; i++, this.location.y += y_direction) {
     * if (!this.playing_field.CanRelocateGame_object(this, this.location)) {
     * return false;
     * }
     * }
     * }
     * case None:
     * return false;
     * }
     * return false;
     * }
     *
     * public void UpdatedestroyableLocation() throws IOException {
     * UpdatedestroyablexLocation();
     * UpdatedestroyableyLocation();
     * }
     *
     * public Boolean CanUpdatedestroyablexLocation() {
     * Integer resultcoord;
     * return !((resultcoord = this.location.x + this.speed.x) >
     * this.posible_location_area.max_coord.x || resultcoord < this.posible_location_area.min_coord.x);
     * }
     *
     * public void UpdatedestroyablexLocation() throws IOException {
     * if (this.location.x + this.speed.x >
     * this.posible_location_area.max_coord.x || this.location.x + this.speed.x < this.posible_location_area.min_coord.x) {
     * this.close();
     * } else {
     * this.location.x += this.speed.x;
     * }
     * }
     *
     * public Boolean CanUpdatedestroyableyLocation() {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + this.speed.y) >
     * this.posible_location_area.max_coord.y || resultcoord < this.posible_location_area.min_coord.y);
     * }
     *
     * public void UpdatedestroyableyLocation() throws IOException {
     * if (this.location.y + this.speed.y >
     * this.posible_location_area.max_coord.y || this.location.y + this.speed.y
     * < this.posible_location_area.min_coord.y) { this.close(); } else {
     * this.location.y += this.speed.y; } }
     *
     * /**
     * Checks if and UpdatepossibleLocation can be done
     *
     * @return A boolean containing the result public Boolean
     * CanUpdatepossibleLocation() { return true; }
     *
     * public void UpdatepossibleLocation() { UpdatepossiblexLocation();
     * UpdatepossibleyLocation(); }
     *
     * public void UpdatepossiblexLocation() { this.location.x += this.speed.x;
     * }
     *
     * public void UpdatepossibleyLocation() { this.location.y += this.speed.y;
     * }
     *
     * /**
     * Checks if it will be at farest location when it moves
     *
     * @return A boolean containing the result public Boolean WillFarest() {
     * Coordinate resultcoord = new Coordinate(this.location.x + this.speed.x,
     * this.location.y + this.speed.y); return resultcoord.y >
     * this.posible_location_area.max_coord.y || resultcoord.y <
     * this.posible_location_area.min_coord.y || resultcoord.y < 0 || resultcoord.y
     * >= this.playing_field.y_size || resultcoord.x >
     * this.posible_location_area.max_coord.x || resultcoord.x <
     * this.posible_location_area.min_coord.x || resultcoord.x < 0 || resultcoord.x
     * >= this.playing_field.x_size; }
     *
     * /**
     * Checks if and UpdaterespawneableLocation can be done
     *
     * @return A boolean containing the result public Boolean
     * CanUpdatefarestLocation() { Coordinate destiny_location = new
     * Coordinate(this.location.x + this.speed.x, this.location.y +
     * this.speed.y); Speed moving_speed; switch (this.move_type) { case
     * Teleport: return this.playing_field.CanRelocateGame_object(this,
     * destiny_location); case Horizontal_first: moving_speed = new
     * Speed((destiny_location.x > this.location.x) ? 1 : -1,
     * (destiny_location.y > this.location.y) ? 1 : -1); for (;
     * !Objects.equals(this.location.x, destiny_location.x); this.location.x +=
     * moving_speed.x) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } } for (;
     * !Objects.equals(this.location.y, destiny_location.y); this.location.y +=
     * moving_speed.y) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } } return true; case Vertical_first:
     * moving_speed = new Speed((destiny_location.x > this.location.x) ? 1 : -1,
     * (destiny_location.y > this.location.y) ? 1 : -1); for (;
     * !Objects.equals(this.location.y, destiny_location.y); this.location.y +=
     * moving_speed.y) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } } for (;
     * !Objects.equals(this.location.x, destiny_location.x); this.location.x +=
     * moving_speed.x) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } } return true; case Diagonal: if
     * (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.x) >=
     * Math.abs(this.speed.y)) { Integer times = Math.abs(this.speed.x /
     * this.speed.y); Integer x_direction = (this.speed.x < 0) ? -1 : 1; Integer
     * y_direction = (this.speed.y < 0) ? -1 : 1; for (;
     * !Objects.equals(this.location.y, destiny_location.y); this.location.y +=
     * y_direction) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } for (Integer i = 0; i < times; i++, this.location.x += x_direction) {
     * if (!this.playing_field.CanRelocateGame_object(this, this.location)) {
     * return false;
     * }
     * }
     * }
     * for (; !Objects.equals(this.location.x, destiny_location.x); this.location.x += x_direction) {
     * if (!this.playing_field.CanRelocateGame_object(this, this.location)) {
     * return false;
     * }
     * }
     * } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y)
     * >= Math.abs(this.speed.x)) { Integer times = Math.abs(this.speed.y /
     * this.speed.x); Integer y_direction = (this.speed.y < 0) ? -1 : 1; Integer
     * x_direction = (this.speed.x < 0) ? -1 : 1; for (;
     * !Objects.equals(this.location.x, destiny_location.x); this.location.x +=
     * x_direction) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } for (Integer i = 0; i < times; i++,
     * this.location.x += y_direction) { if
     * (!this.playing_field.CanRelocateGame_object(this, this.location)) {
     * return false; } } } for (; !Objects.equals(this.location.y,
     * destiny_location.y); this.location.y += y_direction) { if
     * (!this.playing_field.CanRelocateGame_object(this, this.location)) {
     * return false; } } } else { Integer x_direction = (this.speed.x < 0) ? -1
     * : 1; for (Integer i = 0; i < this.speed.x; i++, this.location.x +=
     * x_direction) { if (!this.playing_field.CanRelocateGame_object(this,
     * this.location)) { return false; } } Integer y_direction = (this.speed.y <
     * 0) ? -1 : 1; for (Integer i = 0; i < this.speed.y; i++, this.location.y += y_direction) {
     * if (!this.playing_field.CanRelocateGame_object(this, this.location)) {
     * return false;
     * }
     * }
     * }
     * case None:
     * return false;
     * }
     * return false;
     * }
     *
     * public void UpdatefarestLocation() {
     * UpdatefarestxLocation();
     * UpdatefarestyLocation();
     * }
     *
     * public void UpdatefarestxLocation() {
     * if (this.location.x + this.speed.x >
     * this.posible_location_area.max_coord.x) { this.location.x =
     * this.posible_location_area.max_coord.x; } else if (this.location.x +
     * this.speed.x < this.posible_location_area.min_coord.x) {
     * this.location.x = this.posible_location_area.min_coord.x;
     * } else {
     * this.location.x += this.speed.x;
     * }
     * }
     *
     * public void UpdatefarestyLocation() {
     * if (this.location.y + this.speed.y >
     * this.posible_location_area.max_coord.y) { this.location.y =
     * this.posible_location_area.max_coord.y; } else if (this.location.y +
     * this.speed.y < this.posible_location_area.min_coord.y) { this.location.y
     * = this.posible_location_area.min_coord.y; } else { this.location.y +=
     * this.speed.y; } }
     *
     * /**
     * Checks if it will make use of the circular universe when it moves
     *
     * @return A boolean containing the result public Boolean
     * WillCircularUniverse() { Coordinate resultcoord = new
     * Coordinate(this.location.x + this.speed.x, this.location.y +
     * this.speed.y); return resultcoord.y >
     * this.posible_location_area.max_coord.y || resultcoord.y <
     * this.posible_location_area.min_coord.y || resultcoord.y < 0 || resultcoord.y
     * >= this.playing_field.y_size || resultcoord.x >
     * this.posible_location_area.max_coord.x || resultcoord.x <
     * this.posible_location_area.min_coord.x || resultcoord.x < 0 || resultcoord.x
     * >= this.playing_field.x_size; }
     *
     * /**
     * Checks if and UpdatecircularuniverseLocation can be done
     *
     * @return A boolean containing the result public Boolean
     * CanUpdatecircularuniverseLocation() { return true; }
     *
     * public void UpdatecircularuniverseLocation() {
     * UpdatecircularuniversexLocation(); UpdatecircularuniverseyLocation(); }
     *
     * public void UpdatecircularuniversexLocation() { if (this.location.x +
     * this.speed.x > this.posible_location_area.max_coord.x) { this.location.x
     * = this.posible_location_area.min_coord.x + (this.speed.x -
     * (this.posible_location_area.max_coord.x - this.location.x)); } else if
     * (this.location.x + this.speed.x < this.posible_location_area.min_coord.x) {
     * this.location.x = this.posible_location_area.max_coord.x + (this.speed.x + this.location.x);
     * } else {
     * this.location.x += this.speed.x;
     * }
     * }
     *
     * public void UpdatecircularuniverseyLocation() {
     * if (this.location.y + this.speed.y >
     * this.posible_location_area.max_coord.y) { this.location.y =
     * this.posible_location_area.min_coord.y + (this.speed.y -
     * (this.posible_location_area.max_coord.y - this.location.y)); } else if
     * (this.location.x + this.speed.x < this.posible_location_area.min_coord.x) {
 this.location.y = this.posible_location_area.max_coord.y + (this.speed.y + this.location.y);
 } else {
 this.location.y += this.speed.y;
 }
 }

 public Boolean[] CanUpdateLocation(Integer x, Integer y) {
 switch (this.out_of_bounds_move_type) {
 case Bounceable:
 return CanUpdatecircularuniverseLocation(x, y);
 case Respawnable:
 return CanUpdaterespawnableLocation(x, y);
 case Impossible:
 return CanUpdateimpossibleLocation(x, y);
 case Destroyable:
 return CanUpdatedestroyableLocation(x, y);
 case Possible:
 return CanUpdatepossibleLocation(x, y);
 case Farest:
 return CanUpdatefarestLocation(x, y);
 case Circular_universe:
 return CanUpdatecircularuniverseLocation(x, y);
 }
 Boolean[] no_out_of_bounds_move_type = {false, false};
 return no_out_of_bounds_move_type;
 }

 public void UpdateLocation(Integer x, Integer y) {
 if (x != 0 || y != 0) {
 switch (this.out_of_bounds_move_type) {
 case Bounceable:
 UpdatebounceableLocation(x, y);
 break;
 case Respawnable:
 UpdaterespawnableLocation(x, y);
 break;
 case Impossible:
 UpdateimpossibleLocation(x, y);
 break;
 case Destroyable:
 try {
 UpdatedestroyableLocation(x, y);
 } catch (IOException ex) {
 Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
 }
 break;
 case Possible:
 UpdatepossibleLocation(x, y);
 break;
 case Farest:
 UpdatefarestLocation(x, y);
 break;
 case Circular_universe:
 UpdatecircularuniverseLocation(x, y);
 break;
 }
 }
 }

 public Boolean[] CanUpdatebounceableLocation(Integer x, Integer y) {
 Boolean[] result = {CanUpdatebounceablexLocation(x, y), CanUpdatebounceableyLocation(x, y)};
 return result;
 }

 public void UpdatebounceableLocation(Integer x, Integer y) {
 UpdatebounceablexLocation(x, y);
 UpdatebounceableyLocation(x, y);
 }

 public Boolean CanUpdatebounceablexLocation(Integer x, Integer y) {
 Integer resultcoord;
 return !((resultcoord = this.location.x + x) >
     * this.posible_location_area.max_coord.x || resultcoord < this.posible_location_area.min_coord.x);
     * }
     *
     * public void UpdatebounceablexLocation(Integer x, Integer y) {
     * if (this.location.x + x > this.posible_location_area.max_coord.x) {
     * this.location.x -= (x - (this.posible_location_area.max_coord.x -
     * this.location.x)); } else if (this.location.x + x < this.posible_location_area.min_coord.y) {
     * this.location.x -= (x + this.location.x);
     * } else {
     * this.location.x += x;
     * }
     * }
     *
     * public Boolean CanUpdatebounceableyLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + y) >
     * this.posible_location_area.max_coord.y || resultcoord < this.posible_location_area.min_coord.y);
     * }
     *
     * public void UpdatebounceableyLocation(Integer x, Integer y) {
     * if (this.location.y + y > this.posible_location_area.max_coord.y) {
     * this.location.y -= (y - (this.posible_location_area.max_coord.y -
     * this.location.y)); } else if (this.location.y + y < this.posible_location_area.min_coord.y) {
     * this.location.y -= (y + this.location.y);
     * } else {
     * this.location.y += y;
     * }
     * }
     *
     * public Boolean[] CanUpdaterespawnableLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdaterespawnablexLocation(x, y), CanUpdaterespawnableyLocation(x, y)};
     * return result;
     * }
     *
     * public void UpdaterespawnableLocation(Integer x, Integer y) {
     * UpdaterespawnablexLocation(x, y);
     * UpdaterespawnableyLocation(x, y);
     * }
     *
     * public Boolean CanUpdaterespawnablexLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.x + x) >
     * this.posible_location_area.max_coord.x || resultcoord < this.posible_location_area.min_coord.x);
     * }
     *
     * public void UpdaterespawnablexLocation(Integer x, Integer y) {
     * if (this.location.x + x > this.posible_location_area.max_coord.x ||
     * this.location.x + x < this.posible_location_area.min_coord.x) {
     * this.location.x = Mathcustomfuncs.random(this.respawn_area.min_coord.x, this.respawn_area.max_coord.x).intValue();
     * } else {
     * this.location.x += x;
     * }
     * }
     *
     * public Boolean CanUpdaterespawnableyLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + y) >
     * this.posible_location_area.max_coord.y || resultcoord < this.posible_location_area.min_coord.y);
     * }
     *
     * public void UpdaterespawnableyLocation(Integer x, Integer y) {
     * if (this.location.y + y > this.posible_location_area.max_coord.y ||
     * this.location.y + y < this.posible_location_area.min_coord.y) {
     * this.location.y = Mathcustomfuncs.random(this.respawn_area.min_coord.y, this.respawn_area.max_coord.y).intValue();
     * } else {
     * this.location.y += y;
     * }
     * }
     *
     * public Boolean[] CanUpdateimpossibleLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdateimpossiblexLocation(x, y), CanUpdateimpossibleyLocation(x, y)};
     * return result;
     * }
     *
     * public void UpdateimpossibleLocation(Integer x, Integer y) {
     * UpdateimpossiblexLocation(x, y);
     * UpdateimpossibleyLocation(x, y);
     * }
     *
     * public Boolean CanUpdateimpossiblexLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.x + x) >
     * this.posible_location_area.max_coord.x || resultcoord < this.posible_location_area.min_coord.x);
     * }
     *
     * public void UpdateimpossiblexLocation(Integer x, Integer y) {
     * this.location.x += (this.location.x + x >
     * this.posible_location_area.max_coord.x || this.location.x + x < this.posible_location_area.min_coord.x) ? 0 : x;
     * }
     *
     * public Boolean CanUpdateimpossibleyLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + y) >
     * this.posible_location_area.max_coord.y || resultcoord < this.posible_location_area.min_coord.y);
     * }
     *
     * public void UpdateimpossibleyLocation(Integer x, Integer y) {
     * this.location.y += (this.location.y + y >
     * this.posible_location_area.max_coord.y || this.location.y + y < this.posible_location_area.min_coord.y) ? 0 : y;
     * }
     *
     * public Boolean[] CanUpdatedestroyableLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdatedestroyablexLocation(x, y), CanUpdatedestroyableyLocation(x, y)};
     * return result;
     * }
     *
     * public void UpdatedestroyableLocation(Integer x, Integer y) throws IOException {
     * UpdatedestroyablexLocation(x, y);
     * UpdatedestroyableyLocation(x, y);
     * }
     *
     * public Boolean CanUpdatedestroyablexLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.x + x) >
     * this.posible_location_area.max_coord.x || resultcoord < this.posible_location_area.min_coord.x);
     * }
     *
     * public void UpdatedestroyablexLocation(Integer x, Integer y) throws IOException {
     * if (this.location.x + x > this.posible_location_area.max_coord.x ||
     * this.location.x + x < this.posible_location_area.min_coord.x) {
     * this.close();
     * } else {
     * this.location.x += x;
     * }
     * }
     *
     * public Boolean CanUpdatedestroyableyLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + y) >
     * this.posible_location_area.max_coord.y || resultcoord < this.posible_location_area.min_coord.y);
     * }
     *
     * public void UpdatedestroyableyLocation(Integer x, Integer y) throws IOException {
     * if (this.location.y + y > this.posible_location_area.max_coord.y ||
     * this.location.y + y < this.posible_location_area.min_coord.y) {
     * this.close();
     * } else {
     * this.location.y += y;
     * }
     * }
     *
     * public Boolean[] CanUpdatepossibleLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdatepossiblexLocation(x, y), CanUpdatepossibleyLocation(x, y)};
     * return result;
     * }
     *
     * public void UpdatepossibleLocation(Integer x, Integer y) {
     * UpdatepossiblexLocation(x, y);
     * UpdatepossibleyLocation(x, y);
     * }
     *
     * public Boolean CanUpdatepossiblexLocation(Integer x, Integer y) {
     * return true;
     * }
     *
     * public void UpdatepossiblexLocation(Integer x, Integer y) {
     * this.location.x += x;
     * }
     *
     * public Boolean CanUpdatepossibleyLocation(Integer x, Integer y) {
     * return true;
     * }
     *
     * public void UpdatepossibleyLocation(Integer x, Integer y) {
     * this.location.y += y;
     * }
     *
     * public Boolean[] CanUpdatefarestLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdatefarestxLocation(x, y), CanUpdatefarestyLocation(x, y)};
     * return result;
     * }
     *
     * public void UpdatefarestLocation(Integer x, Integer y) {
     * UpdatefarestxLocation(x, y);
     * UpdatefarestyLocation(x, y);
     * }
     *
     * public Boolean CanUpdatefarestxLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.x + x) >
     * this.posible_location_area.max_coord.x || resultcoord < this.posible_location_area.min_coord.x);
     * }
     *
     * public void UpdatefarestxLocation(Integer x, Integer y) {
     * if (this.location.x + x > this.posible_location_area.max_coord.x) {
     * this.location.x = this.posible_location_area.max_coord.x; } else if
     * (this.location.x + x < this.posible_location_area.min_coord.x) {
     * this.location.x = this.posible_location_area.min_coord.x;
     * } else {
     * this.location.x += x;
     * }
     * }
     *
     * public Boolean CanUpdatefarestyLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + y) >
     * this.posible_location_area.max_coord.y || resultcoord < this.posible_location_area.min_coord.y);
     * }
     *
     * public void UpdatefarestyLocation(Integer x, Integer y) {
     * if (this.location.y + y > this.posible_location_area.max_coord.y) {
     * this.location.y = this.posible_location_area.max_coord.y; } else if
     * (this.location.y + y < this.posible_location_area.min_coord.y) {
     * this.location.y = this.posible_location_area.min_coord.y;
     * } else {
     * this.location.y += y;
     * }
     * }
     *
     * public Boolean[] CanUpdatecircularuniverseLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdatecircularuniversexLocation(x, y), CanUpdatecircularuniverseyLocation(x, y)};
     * return result;
     * }
     *
     * public void UpdatecircularuniverseLocation(Integer x, Integer y) {
     * UpdatecircularuniversexLocation(x, y);
     * UpdatecircularuniverseyLocation(x, y);
     * }
     *
     * public Boolean CanUpdatecircularuniversexLocation(Integer x, Integer y) {
     * return true;
     * }
     *
     * public void UpdatecircularuniversexLocation(Integer x, Integer y) {
     * if (this.location.x + x > this.posible_location_area.max_coord.x) {
     * this.location.x = this.posible_location_area.min_coord.x + (x -
     * (this.posible_location_area.max_coord.x - this.location.x)); } else if
     * (this.location.x + x < this.posible_location_area.min_coord.x) {
     * this.location.x = this.posible_location_area.max_coord.x + (x + this.location.x);
     * } else {
     * this.location.x += x;
     * }
     * }
     *
     * public Boolean CanUpdatecircularuniverseyLocation(Integer x, Integer y) {
     * return true;
     * }
     *
     * public void UpdatecircularuniverseyLocation(Integer x, Integer y) {
     * if (this.location.y + y > this.posible_location_area.max_coord.y) {
     * this.location.y = this.posible_location_area.min_coord.y + (y -
     * (this.posible_location_area.max_coord.y - this.location.y)); } else if
     * (this.location.x + x < this.posible_location_area.min_coord.x) {
     * this.location.y = this.posible_location_area.max_coord.y + (y +
     * this.location.y); } else { this.location.y += y; } }
     *
     */
    @Override
    public void close() throws IOException {
        try {
            this.playing_field.DeleteGame_object(this);
        } catch (ImpossibleLocationRemoveException ex) {
            Logger.getLogger(Gameobject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
