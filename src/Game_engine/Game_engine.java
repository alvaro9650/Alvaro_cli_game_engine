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
public class Game_engine {
    Game_engine(){
    }
    public void DeprecatedDrawFrame(Field field, Ball[] balls, Player[] players) {
        for (int y = 0; y <= field.y_size; y++) {
            next_coordinate:
            for (int x = 0; x <= field.x_size; x++) {
                for (Player player : players) {
                    if (x == player.location.x && y == player.location.y) {
                        System.out.append(player.character);
                        continue next_coordinate;
                    }
                }
                for (Ball ball : balls) {
                    if (x == ball.location.x && y == ball.location.y) {
                        System.out.append('O');
                        continue next_coordinate;
                    }
                }
                System.out.append('|');
            }
            System.out.append("\n");
        }
        System.out.append("\n");
    }
    public void DrawFrame(Field field){
        StringBuilder frame = new StringBuilder();
        char draw_character = '|';
        Integer character_height = 0;
        for(int y = 0;y<field.game_objects[0].length;y++){
            for (Game_object[][] x : field.game_objects) {
                for (Game_object game_object : x[y]) {
                    if ( game_object!=null&&game_object.height!=null&&game_object.height>character_height && game_object.height>0 ) {
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
    public void DrawFrame(Field[] fields){
        Integer character_height = 0;
        StringBuilder frame = new StringBuilder();
        for ( Field field : fields ){
            char draw_character = '|';
            for(int y = 0;y<field.game_objects[0].length;y++){
                for (Game_object[][] x : field.game_objects) {
                    for (Game_object game_object : x[y]) {
                        if ( game_object!=null&&game_object.height!=null&&game_object.height>character_height && game_object.height>0 ) {
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
    public void DeprecatedUpdateLocations(Field field, Ball[] balls, Player[] players) {
        for (int y = 0; y <= field.y_size; y++) {
            next_coordinate:
            for (int x = 0; x <= field.x_size; x++) {
                for (Player player : players) {
                    if (x == player.location.x && y == player.location.y) {
                        System.out.append(player.character);
                        continue next_coordinate;
                    }
                }
                for (Ball ball : balls) {
                    if (x == ball.location.x && y == ball.location.y) {
                        System.out.append('O');
                        continue next_coordinate;
                    }
                }
                System.out.append('|');
            }
            System.out.append("\n");
        }
        System.out.append("\n");
    }
    public void UpdateLocations(Field field) {
        for ( Game_object[][] x : field.game_objects ){
            for ( Game_object[] y : x ){
                for ( Game_object game_object : y ) {
                    if ( game_object!=null ) {
                        game_object.UpdateLocation();
                    }
                }
            }
        }
    }
    public void UpdateLocations(Field[] fields) {
        for ( Field field : fields ){
            this.UpdateLocations(field);
        }
    }
    public char ToDrawAt(Field field,Integer x,Integer y){
        char draw_character = ' ';
        Integer character_height = 0;
        for (Game_object game_object : field.game_objects[x][y]) {
            if ( game_object!=null&&game_object.height!=null&&game_object.height>character_height && game_object.height>0 ) {
                character_height = game_object.height;
                draw_character = game_object.character;
            }
        }
        return draw_character;
    }
}