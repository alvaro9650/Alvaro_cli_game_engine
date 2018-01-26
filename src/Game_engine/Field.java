/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A field that has a x size y size and can contain game objects
 *
 * @author alumno1718_2
 */
public class Field {

    public Integer x_size;
    public Integer y_size;
    public GameObject[][][] game_objects;

    /**
     * Costructor for field
     *
     * @param x X size of the field
     * @param y Y size of the field
     * @param max_objects_per_coord Maximum number of objects per coord
     */
    public Field(Integer x, Integer y, Integer max_objects_per_coord) {
        this.x_size = x;
        this.y_size = y;
        this.game_objects = new GameObject[this.x_size][this.y_size][max_objects_per_coord];
    }

    /**
     * Remove an object from a location in the field
     *
     * @param game_object The object you want to delete
     * @throws Game_engine.ImpossibleLocationRemoveException
     */
    public void DeleteGame_object(GameObject game_object) throws ImpossibleLocationRemoveException {
        if (game_object.array_position < this.game_objects[game_object.location.x][game_object.location.y].length && game_object.hashCode() == this.game_objects[game_object.location.x][game_object.location.y][game_object.array_position].hashCode()) {
            this.game_objects[game_object.location.x][game_object.location.y][game_object.array_position] = null;
        } else {
            throw new ImpossibleLocationRemoveException("Object is not in the object stated location");
        }
    }

    /**
     * Add an object to a location in the field
     *
     * @param game_object The object you want to add
     * @throws Game_engine.ImpossibleLocationAddException
     */
    public void AddGame_object(GameObject game_object) throws ImpossibleLocationAddException {
        int o_num = 0;
        do {
        } while (o_num++ < this.game_objects[game_object.location.x][game_object.location.y].length && this.game_objects[game_object.location.x][game_object.location.y][o_num] != null);
        if (o_num < this.game_objects[game_object.location.x][game_object.location.y].length) {
            this.game_objects[game_object.location.x][game_object.location.y][o_num] = game_object;
            game_object.array_position = o_num;
        } else {
            throw new ImpossibleLocationAddException("No space available");
        }
    }

    /**
     * Checks if coordinate has space
     *
     * @param coordinate The coordinate you want to know if has space
     * @return true if there is space to put an object in that Coordinate false
     * if not
     */
    public Boolean CoordinateHasSpace(Coordinate coordinate) {
        for (GameObject object : this.game_objects[coordinate.x][coordinate.y]) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tells if the object can be relocated there
     *
     * @param game_object Object you want to know if can be relocated there
     * @param coordinate Cordinate you want to know if can be relocated
     * @return Returns a boolean with true or false depending on if it's
     * possible to relocate the object there
     */
    public Boolean CanRelocateGame_object(GameObject game_object, Coordinate coordinate) {
        RectangularArea possible_move_area;
        if (!CoordinateHasSpace(coordinate) || coordinate.x > (possible_move_area = new RectangularArea(this.x_size - 1, 0, this.y_size - 1, 0).getCommonArea(game_object.posible_location_area)).maxcoord.x || coordinate.x < possible_move_area.mincoord.x || coordinate.y > possible_move_area.maxcoord.y || coordinate.y < possible_move_area.mincoord.y) {
            return false;
        }
        for (GameObject object : this.game_objects[coordinate.x][coordinate.y]) {
            if (object != null) {
                switch (object.physical_state_type) {
                    case Solid:
                    case Solid_with_holes:
                        switch (game_object.physical_state_type) {
                            case Solid_with_holes:
                            case Solid:
                                return false;
                        }
                    case Liquid:
                        switch (game_object.physical_state_type) {
                            case Solid:
                                return false;
                        }
                    case Gas:
                        switch (game_object.physical_state_type) {
                            case Solid:
                            case Liquid:
                                return false;
                        }
                }
            }
        }
        return true;
    }

    /**
     * Checks if an object collides with something in that location
     *
     * @param objectlookingforcollider The object that you want to know if
     * collides with something
     * @return The object to collide with or null if it doesn't collide with
     * anything
     */
    public GameObject collidesWith(GameObject objectlookingforcollider) {
        for (GameObject object : this.game_objects[objectlookingforcollider.location.x][objectlookingforcollider.location.y]) {
            if (object != null && Objects.equals(object.height, objectlookingforcollider.height)) {
                return object;
            }
        }
        return null;
    }

    /**
     * It's used to process a collision between 2 objects
     *
     * @param receiving_collision_object Object which this object collides to
     * @param giving_collision_object
     */
    public void processCollision(GameObject giving_collision_object, GameObject receiving_collision_object) {
        switch (receiving_collision_object.receiving_collision) {
            case Ghost:
                switch (giving_collision_object.giving_collision) {
                    case Ghost:
                        break;
                    case Bounce:
                        break;
                    case Worm_hole:
                        break;
                    case Imparable:
                        break;
                    case Unmoveable:
                        break;
                    case Respawnable:
                        break;
                    case Destroyable:
                        break;
                    case Farest:
                        break;
                }
                break;
            case Bounce:
                switch (giving_collision_object.giving_collision) {
                    case Ghost:
                        break;
                    case Bounce:
                        giving_collision_object.speed.x /= -2;
                        receiving_collision_object.speed.x -= giving_collision_object.speed.x;
                        giving_collision_object.speed.y /= -2;
                        receiving_collision_object.speed.x -= giving_collision_object.speed.y;
                        break;
                    case Worm_hole:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        break;
                    case Imparable:
                        if (giving_collision_object.move_direction_horizontal != 0) {
                            if (giving_collision_object.move_direction_horizontal == Math.signum(receiving_collision_object.speed.x)) {
                                receiving_collision_object.speed.x = giving_collision_object.speed.x;
                            } else {
                                receiving_collision_object.speed.x /= -2;
                                receiving_collision_object.speed.x -= giving_collision_object.speed.x;
                            }
                        }
                        if (giving_collision_object.move_direction_vertical != 0) {
                            if (giving_collision_object.move_direction_vertical == Math.signum(receiving_collision_object.speed.y)) {
                                receiving_collision_object.speed.y = giving_collision_object.speed.y;
                            } else {
                                receiving_collision_object.speed.y /= -2;
                                receiving_collision_object.speed.y -= giving_collision_object.speed.y;
                            }
                        }
                        break;
                    case Unmoveable:
                        if (giving_collision_object.move_direction_horizontal != 0) {
                            if (giving_collision_object.move_direction_horizontal == Math.signum(receiving_collision_object.speed.x)) {
                                receiving_collision_object.speed.x = giving_collision_object.speed.x;
                            } else {
                                receiving_collision_object.speed.x /= -2;
                                receiving_collision_object.speed.x -= giving_collision_object.speed.x;
                            }
                        }
                        if (giving_collision_object.move_direction_vertical != 0) {
                            if (giving_collision_object.move_direction_vertical == Math.signum(receiving_collision_object.speed.y)) {
                                receiving_collision_object.speed.y = giving_collision_object.speed.y;
                            } else {
                                receiving_collision_object.speed.y /= -2;
                                receiving_collision_object.speed.y -= giving_collision_object.speed.y;
                            }
                        }
                        break;
                    case Respawnable:
                        giving_collision_object.Respawn();
                        break;
                    case Destroyable:
                        {
                            try {
                                giving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Farest:
                        giving_collision_object.speed.x = 0;
                        giving_collision_object.speed.y = 0;
                        break;
                }
            case Worm_hole:
                switch (giving_collision_object.giving_collision) {
                    case Ghost:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        break;
                    case Bounce:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        break;
                    case Worm_hole:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        break;
                    case Imparable:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        break;
                    case Unmoveable:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        break;
                    case Respawnable:
                        giving_collision_object.Respawn();
                        break;
                    case Destroyable:
                        {
                            try {
                                giving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Farest:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        break;
                }
            case Imparable:
                switch (giving_collision_object.giving_collision) {
                    case Ghost:
                        break;
                    case Bounce:
                        giving_collision_object.speed.x = -giving_collision_object.speed.x;
                        giving_collision_object.speed.y = -giving_collision_object.speed.y;
                        break;
                    case Worm_hole:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        break;
                    case Imparable:
                        break;
                    case Unmoveable:
                        break;
                    case Respawnable:
                        giving_collision_object.Respawn();
                        break;
                    case Destroyable:
                        {
                            try {
                                giving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Farest:
                        giving_collision_object.speed.x = 0;
                        giving_collision_object.speed.x = 0;
                        break;
                }
                break;
            case Unmoveable:
                switch (giving_collision_object.giving_collision) {
                    case Ghost:
                        break;
                    case Bounce:
                        giving_collision_object.speed.x = -giving_collision_object.speed.x;
                        giving_collision_object.speed.y = -giving_collision_object.speed.y;
                        break;
                    case Worm_hole:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        break;
                    case Imparable:
                        break;
                    case Unmoveable:
                        break;
                    case Respawnable:
                        giving_collision_object.Respawn();
                        break;
                    case Destroyable:
                        {
                            try {
                                giving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Farest:
                        giving_collision_object.speed.x = 0;
                        giving_collision_object.speed.x = 0;
                        break;
                }
                break;
            case Respawnable:
                switch (giving_collision_object.giving_collision) {
                    case Ghost:
                        break;
                    case Bounce:
                        giving_collision_object.speed.x /= -2;
                        giving_collision_object.speed.y /= -2;
                        receiving_collision_object.Respawn();
                        break;
                    case Worm_hole:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        receiving_collision_object.Respawn();
                        break;
                    case Imparable:
                        receiving_collision_object.Respawn();
                        break;
                    case Unmoveable:
                        receiving_collision_object.Respawn();
                        break;
                    case Respawnable:
                        receiving_collision_object.Respawn();
                        giving_collision_object.Respawn();
                        break;
                    case Destroyable:
                        {
                            try {
                                giving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        receiving_collision_object.Respawn();
                        break;
                    case Farest:
                        giving_collision_object.speed.x = 0;
                        giving_collision_object.speed.y = 0;
                        receiving_collision_object.Respawn();
                        break;
                }
            case Destroyable:
                switch (giving_collision_object.giving_collision) {
                    case Ghost:
                        break;
                    case Bounce:
                        giving_collision_object.speed.x /= -2;
                        giving_collision_object.speed.y /= -2;
                        {
                            try {
                                receiving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Worm_hole:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        {
                            try {
                                receiving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Imparable:
                        {
                            try {
                                receiving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Unmoveable:
                        {
                            try {
                                receiving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Respawnable:
                        {
                            try {
                                receiving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        giving_collision_object.Respawn();
                        break;
                    case Destroyable:
                        {
                            try {
                                giving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        {
                            try {
                                receiving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Farest:
                        giving_collision_object.speed.x = 0;
                        giving_collision_object.speed.y = 0;
                        {
                            try {
                                receiving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                }
            case Farest:
                switch (giving_collision_object.giving_collision) {
                    case Ghost:
                        break;
                    case Bounce:
                        receiving_collision_object.location.x += new Float(Math.signum(receiving_collision_object.speed.x)).intValue();
                        receiving_collision_object.location.y += new Float(Math.signum(receiving_collision_object.speed.y)).intValue();
                        break;
                    case Worm_hole:
                        giving_collision_object.location.x += giving_collision_object.move_direction_horizontal;
                        giving_collision_object.location.y += giving_collision_object.move_direction_vertical;
                        break;
                    case Imparable:
                        receiving_collision_object.location.x += new Float(Math.signum(receiving_collision_object.speed.x)).intValue();
                        receiving_collision_object.location.y += new Float(Math.signum(receiving_collision_object.speed.y)).intValue();
                        break;
                    case Unmoveable:
                        receiving_collision_object.location.x += new Float(Math.signum(receiving_collision_object.speed.x)).intValue();
                        receiving_collision_object.location.y += new Float(Math.signum(receiving_collision_object.speed.y)).intValue();
                        break;
                    case Respawnable:
                        giving_collision_object.Respawn();
                        break;
                    case Destroyable:
                        {
                            try {
                                receiving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Farest:
                        giving_collision_object.speed.x = 0;
                        giving_collision_object.speed.y = 0;
                        break;
                }
        }
    }
}
