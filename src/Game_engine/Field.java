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

    Integer x_size;
    Integer y_size;
    Game_object[][][] game_objects;

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
     */
    public void DeleteGame_object(Game_object game_object) {
        for (int o_num = 0; o_num < this.game_objects[game_object.location.x][game_object.location.y].length; o_num++) {
            if (this.game_objects[game_object.location.x][game_object.location.y][o_num] != null && this.game_objects[game_object.location.x][game_object.location.y][o_num].hashCode() == game_object.hashCode()) {
                this.game_objects[game_object.location.x][game_object.location.y][o_num] = null;
                break;
            }
        }
    }

    /**
     * Add an object to a location in the field
     *
     * @param game_object The object you want to add
     */
    public void AddGame_object(Game_object game_object) {
        for (int o_num = 0; o_num < this.game_objects[game_object.location.x][game_object.location.y].length; o_num++) {
            if (this.game_objects[game_object.location.x][game_object.location.y][o_num] == null) {
                this.game_objects[game_object.location.x][game_object.location.y][o_num] = game_object;
                break;
            }
        }
    }
}
