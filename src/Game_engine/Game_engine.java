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

    /**
     * It's used to process a collision between 2 objects
     *
     * @param receiving_collision_object Object which this object collides to
     * @param giving_collision_object
     */
    public void processCollision(Game_object giving_collision_object, Game_object receiving_collision_object) {
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
                    case Destroyable: {
                        try {
                            giving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
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
                    case Destroyable: {
                        try {
                            giving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
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
                    case Destroyable: {
                        try {
                            giving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
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
                    case Destroyable: {
                        try {
                            giving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
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
                    case Destroyable: {
                        try {
                            giving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
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
                                Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
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
                                Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Imparable: {
                        try {
                            receiving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Unmoveable: {
                        try {
                            receiving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Respawnable: {
                        try {
                            receiving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    giving_collision_object.Respawn();
                    break;
                    case Destroyable: {
                        try {
                            giving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                     {
                        try {
                            receiving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
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
                                Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
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
                    case Destroyable: {
                        try {
                            receiving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Game_object.class.getName()).log(Level.SEVERE, null, ex);
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
