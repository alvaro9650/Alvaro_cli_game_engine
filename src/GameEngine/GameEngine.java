/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

import java.util.ArrayList;

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
        ArrayList<GameObject> movedobjects = new ArrayList<GameObject>();
        for (GameObject[][] x : field.gameobjects) {
            for (GameObject[] y : x) {
                for (GameObject gameobject : y) {
                    if (gameobject != null && !movedobjects.contains(gameobject)) {
                        gameobject.updateLocation();
                        movedobjects.add(gameobject);
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
        ArrayList<Composite2dGameObjectComponent> movedobjects = new ArrayList<Composite2dGameObjectComponent>();
        for (Composite2dGameObjectComponent[][] x : compositeobject.componentobjects) {
            for (Composite2dGameObjectComponent[] y : x) {
                for (Composite2dGameObjectComponent composite2dgameobjectcomponent : y) {
                    if (composite2dgameobjectcomponent != null && !movedobjects.contains(composite2dgameobjectcomponent)) {
                        composite2dgameobjectcomponent.updateLocation();
                        movedobjects.add(composite2dgameobjectcomponent);
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
                switch (gameobject.type) {
                    case Simple:
                        characterheight = gameobject.height;
                        drawcharacter = gameobject.character;
                    case Composite2dGameObjectType:
                        Character charatcom;
                        if (null != (charatcom = GameEngine.toDrawAt((Composite2dGameObject) gameobject, x - gameobject.location.x, y - gameobject.location.y))) {
                            characterheight = gameobject.height;
                            drawcharacter = charatcom;
                        }
                    case Composite3dGameObjectType:
                }
            }
        }
        return drawcharacter;
    }

    /**
     * Returns which character has to be drawn in that position in that
     * Composite2dGameObject
     *
     * @param object
     * @param x x coordinate
     * @param y y coordinate
     * @return Character to be drawn at that location
     * @author alvaro9650
     */
    public static Character toDrawAt(Composite2dGameObject object, Integer x, Integer y) {
        Character drawcharacter = null;
        Integer characterheight = 0;
        for (GameObject component : object.componentobjects[x][y]) {
            if (component != null && component.height != null && component.height > characterheight && component.height > 0) {
                switch (component.type) {
                    case Composite2dGameObjectComponentType:
                    case Composite3dGameObjectComponentType:
                    case Simple:
                        characterheight = component.height;
                        drawcharacter = component.character;
                        break;
                    case Composite2dGameObjectType:
                        GameEngine.toDrawAt((Composite2dGameObject) component, x - component.location.x, y - component.location.y);
                        break;
                    case Composite3dGameObjectType:
                }
            }
        }
        return drawcharacter;
    }
}
