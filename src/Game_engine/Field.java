/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

/**
 * A field that has a x size y size and can contain game objects
 *
 * @author alumno1718_2
 */
public class Field {

    public Integer x_size;
    public Integer y_size;
    public Game_object[][][] game_objects;

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
        this.game_objects = new Game_object[this.x_size][this.y_size][max_objects_per_coord];
    }

    /**
     * Add an object to a location in the field
     *
     * @param game_object The object you want to delete
     * @throws Game_engine.ImpossibleLocationRemoveException
     */
    public void DeleteGame_object(Game_object game_object) throws ImpossibleLocationRemoveException {
        int o_num = 0;
        do {
        } while (o_num++ < this.game_objects[game_object.location.x][game_object.location.y].length && !(this.game_objects[game_object.location.x][game_object.location.y][o_num] != null && this.game_objects[game_object.location.x][game_object.location.y][o_num].hashCode() == game_object.hashCode()));
        if (o_num < this.game_objects[game_object.location.x][game_object.location.y].length) {
            this.game_objects[game_object.location.x][game_object.location.y][o_num] = null;
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
    public void AddGame_object(Game_object game_object) throws ImpossibleLocationAddException {
        int o_num = 0;
        do {
        } while (o_num < this.game_objects[game_object.location.x][game_object.location.y].length && this.game_objects[game_object.location.x][game_object.location.y][o_num++] != null);
        if (o_num < this.game_objects[game_object.location.x][game_object.location.y].length) {
            this.game_objects[game_object.location.x][game_object.location.y][o_num] = game_object;
        } else {
            throw new ImpossibleLocationAddException("No space available");
        }
    }

    /**
     * Tells if the object can be relocated there
     *
     * @param game_object Object you want to know if can be relocated there
     * @param coordinate Cordinate you want to know if can be relocated
     * @return Returns a boolean with true or false depending on if it's
     * possible to relocate the object there
     */
    public Boolean CanRelocateGame_object(Game_object game_object, Coordinate coordinate) {
        Boolean need_space = true;
        for (Game_object object : this.game_objects[coordinate.x][coordinate.y]) {
            if (object == null) {
                need_space = false;
                break;
            }
        }
        Rectangular_area possible_move_area = new Rectangular_area(this.x_size - 1, 0, this.y_size - 1, 0).CommonArea(game_object.posible_location_area);
        if (coordinate.x > possible_move_area.max_coord.x || coordinate.x < possible_move_area.min_coord.x || coordinate.y > possible_move_area.max_coord.y || coordinate.y < possible_move_area.min_coord.y || need_space) {
            return false;
        }
        for (Game_object object : this.game_objects[coordinate.x][coordinate.y]) {
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
        return true;
    }
}
