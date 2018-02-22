/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

import java.util.Objects;

/**
 *
 * @author alvaro9650
 */
public class Composite2dGameObject extends CompositeGameObject {

    public TwoDimensionsSize size;
    public GameObject[][][] componentobjects;

    /**
     * Costructor for Composite2dGameObject
     *
     * @param field The field where the object is located
     * @param x X size of the object
     * @param y Y size of the object
     * @param maxobjectspercoord Maximum number of objects per object coord
     * @author alvaro9650
     */
    public Composite2dGameObject(Field field, Integer x, Integer y, Integer maxobjectspercoord) {
        super(field);
        this.type = GameObjectType.Composite2dGameObjectType;
        this.size = new TwoDimensionsSize(x, y);
        this.componentobjects = new GameObject[x][y][maxobjectspercoord];
    }

    /**
     * Add an object to a location in the object
     *
     * @param gameobject The object you want to add
     * @throws GameEngine.ImpossibleLocationAddException
     * @throws GameEngine.ObjectCollidesException
     * @throws GameEngine.OutOfBoundsException
     * @throws GameEngine.ImpossibleLocationRemoveException
     * @author alvaro9650
     */
    public void addGameObject(GameObject gameobject) throws ImpossibleLocationAddException, ObjectCollidesException, OutOfBoundsException, ImpossibleLocationRemoveException {
        GameObject collider;
        if (gameobject.located) {
            this.deleteGameObject(gameobject);
        }
        if (!(new RectangularArea(this.size.x - 1, 0, this.size.y - 1, 0).getCommonArea(gameobject.posiblelocationarea).isInside(gameobject.location))) {
            throw new OutOfBoundsException();
        } else if ((collider = this.collidesWith(gameobject)) != null) {
            switch (collider.physicalstatetype) {
                case Solid:
                case SolidWithHoles:
                    switch (gameobject.physicalstatetype) {
                        case SolidWithHoles:
                        case Solid:
                            throw new ObjectCollidesException();
                    }
                case Liquid:
                    switch (gameobject.physicalstatetype) {
                        case Solid:
                            throw new ObjectCollidesException();
                    }
                case Gas:
                    switch (gameobject.physicalstatetype) {
                        case Solid:
                        case Liquid:
                            throw new ObjectCollidesException();
                    }
            }
        }
        Integer i = 0;
        while (i < this.componentobjects[gameobject.location.x][gameobject.location.y].length && this.componentobjects[gameobject.location.x][gameobject.location.y][i] != null) {
            i++;
        }
        if (i < this.componentobjects[gameobject.location.x][gameobject.location.y].length) {
            this.componentobjects[gameobject.location.x][gameobject.location.y][i] = gameobject;
            gameobject.arrayposition = i;
        } else {
            throw new ImpossibleLocationAddException("No space available");
        }
        gameobject.located = true;
    }

    /**
     * Remove an object from a location in the object
     *
     * @param gameobject The object you want to delete
     * @throws GameEngine.ImpossibleLocationRemoveException
     * @author alvaro9650
     */
    public void deleteGameObject(GameObject gameobject) throws ImpossibleLocationRemoveException {
        if (gameobject.arrayposition < this.componentobjects[gameobject.location.x][gameobject.location.y].length && gameobject.hashCode() == this.componentobjects[gameobject.location.x][gameobject.location.y][gameobject.arrayposition].hashCode()) {
            this.componentobjects[gameobject.location.x][gameobject.location.y][gameobject.arrayposition] = null;
        } else {
            throw new ImpossibleLocationRemoveException("Object is not in the object stated location");
        }
        gameobject.located = false;
    }

    /**
     * Checks if an object collides with something in that location
     *
     * @param objectlookingforcollider The object that you want to know if
     * collides with something
     * @return The object to collide with or null if it doesn't collide with
     * anything
     * @author alvaro9650
     */
    public GameObject collidesWith(GameObject objectlookingforcollider) {
        for (GameObject object : this.componentobjects[objectlookingforcollider.location.x][objectlookingforcollider.location.y]) {
            if (object != null && Objects.equals(object.height, objectlookingforcollider.height) && !Objects.equals(object, objectlookingforcollider)) {
                return object;
            }
        }
        return null;
    }
}
