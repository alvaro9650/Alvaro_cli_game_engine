/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

/**
 *
 * @author alvaro9650
 */
public class GameEngine {

    public GameEngine() {
    }

    /**
     * Draws a frame that fields
     *
     * @param field field to draw
     * @author alvaro9650
     */
    public static void DrawFrame(Field field) {
        StringBuilder frame = new StringBuilder();
        char drawcharacter = '|';
        Integer characterheight = 0;
        for (int y = 0; y < field.gameobjects[0].length; y++) {
            for (GameObject[][] x : field.gameobjects) {
                for (GameObject gameobject : x[y]) {
                    if (gameobject != null && gameobject.height != null && gameobject.height > characterheight && gameobject.height > 0) {
                        characterheight = gameobject.height;
                        drawcharacter = gameobject.character;
                    }
                }
                frame.append(drawcharacter);
                drawcharacter = '|';
                characterheight = 0;
            }
            frame.append("\n");
        }
        System.out.println(frame.toString());
    }

    /**
     * Draws a frame of each of those fields
     *
     * @param fields fields to draw
     * @author alvaro9650
     */
    public static void drawFrame(Field[] fields) {
        Integer characterheight = 0;
        StringBuilder frame = new StringBuilder();
        for (Field field : fields) {
            char drawcharacter = '|';
            for (int y = 0; y < field.gameobjects[0].length; y++) {
                for (GameObject[][] x : field.gameobjects) {
                    for (GameObject gameobject : x[y]) {
                        if (gameobject != null && gameobject.height != null && gameobject.height > characterheight && gameobject.height > 0) {
                            characterheight = gameobject.height;
                            drawcharacter = gameobject.character;
                        }
                    }
                    frame.append(drawcharacter);
                    drawcharacter = '|';
                    characterheight = 0;
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
     * @author alvaro9650
     */
    public static void updateLocations(Field field) {
        for (GameObject[][] x : field.gameobjects) {
            for (GameObject[] y : x) {
                for (GameObject gameobject : y) {
                    if (gameobject != null) {
                        gameobject.updateLocation();
                    }
                }
            }
        }
    }

    /**
     * Updates the location of the objects in that composite object
     *
     * @param compositeobject the composite object that has to update locations
     * @author alvaro9650
     */
    public static void updateLocations(Composite2dGameObject compositeobject) {
        for (Composite2dGameObjectComponent[][] x : compositeobject.componentobjects) {
            for (Composite2dGameObjectComponent[] y : x) {
                for (Composite2dGameObjectComponent composite2dgameobjectcomponent : y) {
                    if (composite2dgameobjectcomponent != null) {
                        composite2dgameobjectcomponent.updateLocation();
                    }
                }
            }
        }
    }

    /**
     * Update the locations of the objects in those fields
     *
     * @param fields fields that will update location
     * @author alvaro9650
     */
    public static void updateLocations(Field[] fields) {
        for (Field field : fields) {
            GameEngine.updateLocations(field);
        }
    }

    /**
     * Returns which character has to be drawn in that position in that field
     *
     * @param field
     * @param x x coordinate
     * @param y y coordinate
     * @return Character to be drawn at that location
     * @author alvaro9650
     */
    public static Character toDrawAt(Field field, Integer x, Integer y) {
        Character drawcharacter = null;
        Integer characterheight = 0;
        for (GameObject gameobject : field.gameobjects[x][y]) {
            if (gameobject != null && gameobject.height != null && gameobject.height > characterheight && gameobject.height > 0) {
                characterheight = gameobject.height;
                drawcharacter = gameobject.character;
            }
        }
        return drawcharacter;
    }

}
