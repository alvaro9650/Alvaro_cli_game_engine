/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno1718_2
 */
public class Game_engine {

    public Game_engine() {
    }

    /**
     * Draws a frame that fields
     *
     * @param field field to draw
     */
    public void DrawFrame(Field field) {
        StringBuilder frame = new StringBuilder();
        char draw_character = '|';
        Integer character_height = 0;
        for (int y = 0; y < field.game_objects[0].length; y++) {
            for (Game_object[][] x : field.game_objects) {
                for (Game_object game_object : x[y]) {
                    if (game_object != null && game_object.height != null && game_object.height > character_height && game_object.height > 0) {
                        character_height = game_object.height;
                        draw_character = game_object.character;
                    }
                }
                frame.append(draw_character);
                draw_character = '|';
                character_height = 0;
            }
            frame.append("\n");
        }
        System.out.println(frame.toString());
    }

    /**
     * Draws a frame of each of those fields
     *
     * @param fields fields to draw
     */
    public void DrawFrame(Field[] fields) {
        Integer character_height = 0;
        StringBuilder frame = new StringBuilder();
        for (Field field : fields) {
            char draw_character = '|';
            for (int y = 0; y < field.game_objects[0].length; y++) {
                for (Game_object[][] x : field.game_objects) {
                    for (Game_object game_object : x[y]) {
                        if (game_object != null && game_object.height != null && game_object.height > character_height && game_object.height > 0) {
                            character_height = game_object.height;
                            draw_character = game_object.character;
                        }
                    }
                    frame.append(draw_character);
                    draw_character = '|';
                    character_height = 0;
                }
                frame.append("\n");
            }
            System.out.println(frame.toString());
            frame.delete(0, frame.length());
        }
    }

    /**
     * Updates the location of the objects in that field
     *
     * @param field the field that has to update locations
     */
    public void UpdateLocations(Field field) {
        for (Game_object[][] x : field.game_objects) {
            for (Game_object[] y : x) {
                for (Game_object game_object : y) {
                    if (game_object != null) {
                        game_object.UpdateLocation();
                    }
                }
            }
        }
    }

    /**
     * Update the locations of the objects in those fields
     *
     * @param fields fields that will update location
     */
    public void UpdateLocations(Field[] fields) {
        for (Field field : fields) {
            this.UpdateLocations(field);
        }
    }

    /**
     * Returns which character has to be drawn in that position in that field
     *
     * @param field
     * @param x x coordinate
     * @param y y coordinate
     * @return Character to be drawn at that location
     */
    public char ToDrawAt(Field field, Integer x, Integer y) {
        char draw_character = ' ';
        Integer character_height = 0;
        for (Game_object game_object : field.game_objects[x][y]) {
            if (game_object != null && game_object.height != null && game_object.height > character_height && game_object.height > 0) {
                character_height = game_object.height;
                draw_character = game_object.character;
            }
        }
        return draw_character;
    }


}
