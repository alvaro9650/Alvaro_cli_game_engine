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

    public TwoDimensionsSize size;
    public GameObject[][][] gameobjects;

    /**
     * Costructor for field
     *
     * @param x X size of the field
     * @param y Y size of the field
     * @param maxobjectspercoord Maximum number of objects per coord
     */
    public Field(Integer x, Integer y, Integer maxobjectspercoord) {
        this.size = new TwoDimensionsSize(x, y);
        this.gameobjects = new GameObject[this.size.x][this.size.y][maxobjectspercoord];
    }

    /**
     * Remove an object from a location in the field
     *
     * @param gameobject The object you want to delete
     * @throws Game_engine.ImpossibleLocationRemoveException
     */
    public void deleteGameObject(GameObject gameobject) throws ImpossibleLocationRemoveException {
        if (gameobject.arrayposition < this.gameobjects[gameobject.location.x][gameobject.location.y].length && gameobject.hashCode() == this.gameobjects[gameobject.location.x][gameobject.location.y][gameobject.arrayposition].hashCode()) {
            this.gameobjects[gameobject.location.x][gameobject.location.y][gameobject.arrayposition] = null;
        } else {
            throw new ImpossibleLocationRemoveException("Object is not in the object stated location");
        }
    }

    /**
     * Add an object to a location in the field
     *
     * @param gameobject The object you want to add
     * @throws Game_engine.ImpossibleLocationAddException
     * @throws Game_engine.ObjectCollidesException
     * @throws Game_engine.OutOfBoundsException
     */
    public void addGameObject(GameObject gameobject) throws ImpossibleLocationAddException, ObjectCollidesException, OutOfBoundsException {
        GameObject collider;
        if ((collider = this.collidesWith(gameobject)) != null) {
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
        } else if (new RectangularArea(this.size.x - 1, 0, this.size.y - 1, 0).getCommonArea(gameobject.posiblelocationarea).isInside(gameobject.location)) {
            throw new OutOfBoundsException();
        }
        Integer i = 0;
        while (i < this.gameobjects[gameobject.location.x][gameobject.location.y].length && this.gameobjects[gameobject.location.x][gameobject.location.y][i] != null) {
            i++;
        }
        if (i < this.gameobjects[gameobject.location.x][gameobject.location.y].length) {
            this.gameobjects[gameobject.location.x][gameobject.location.y][i] = gameobject;
            gameobject.arrayposition = i;
        } else {
            throw new ImpossibleLocationAddException("No space available");
        }
    }

    /**
     * Checks if coordinate has space available
     *
     * @param coordinate The coordinate you want to know if has space
     * @return true if there is space to put an object in that Coordinate false
     * if not
     */
    public Boolean checkSpaceAvailable(Coordinate coordinate) {
        for (GameObject object : this.gameobjects[coordinate.x][coordinate.y]) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tells if the object can be relocated there
     *
     * @param gameobject Object you want to know if can be relocated there
     * @param coordinate Cordinate you want to know if can be relocated
     * @return Returns a boolean with true or false depending on if it's
     * possible to relocate the object there
     */
    public Boolean canRelocateGameObject(GameObject gameobject, Coordinate coordinate) {
        RectangularArea possiblemovearea;
        if (!checkSpaceAvailable(coordinate) || coordinate.x > (possiblemovearea = new RectangularArea(this.size.x - 1, 0, this.size.y - 1, 0).getCommonArea(gameobject.posiblelocationarea)).maxcoord.x || coordinate.x < possiblemovearea.mincoord.x || coordinate.y > possiblemovearea.maxcoord.y || coordinate.y < possiblemovearea.mincoord.y) {
            return false;
        }
        for (GameObject object : this.gameobjects[coordinate.x][coordinate.y]) {
            if (object != null) {
                switch (object.physicalstatetype) {
                    case Solid:
                    case SolidWithHoles:
                        switch (gameobject.physicalstatetype) {
                            case SolidWithHoles:
                            case Solid:
                                return false;
                        }
                    case Liquid:
                        switch (gameobject.physicalstatetype) {
                            case Solid:
                                return false;
                        }
                    case Gas:
                        switch (gameobject.physicalstatetype) {
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
        for (GameObject object : this.gameobjects[objectlookingforcollider.location.x][objectlookingforcollider.location.y]) {
            if (object != null && Objects.equals(object.height, objectlookingforcollider.height)&& !Objects.equals(object, objectlookingforcollider)) {
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
        switch (receiving_collision_object.receivingcollision) {
            case Ghost:
                switch (giving_collision_object.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        break;
                    case WormHole:
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
                switch (giving_collision_object.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        giving_collision_object.speed.x /= -2;
                        receiving_collision_object.speed.x -= giving_collision_object.speed.x;
                        giving_collision_object.speed.y /= -2;
                        receiving_collision_object.speed.x -= giving_collision_object.speed.y;
                        break;
                    case WormHole:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
                        break;
                    case Imparable:
                        if (giving_collision_object.movedirection.x != 0) {
                            if (giving_collision_object.movedirection.x == Math.signum(receiving_collision_object.speed.x)) {
                                receiving_collision_object.speed.x = giving_collision_object.speed.x;
                            } else {
                                receiving_collision_object.speed.x /= -2;
                                receiving_collision_object.speed.x -= giving_collision_object.speed.x;
                            }
                        }
                        if (giving_collision_object.movedirection.y != 0) {
                            if (giving_collision_object.movedirection.y == Math.signum(receiving_collision_object.speed.y)) {
                                receiving_collision_object.speed.y = giving_collision_object.speed.y;
                            } else {
                                receiving_collision_object.speed.y /= -2;
                                receiving_collision_object.speed.y -= giving_collision_object.speed.y;
                            }
                        }
                        break;
                    case Unmoveable:
                        if (giving_collision_object.movedirection.x != 0) {
                            if (giving_collision_object.movedirection.x == Math.signum(receiving_collision_object.speed.x)) {
                                receiving_collision_object.speed.x = giving_collision_object.speed.x;
                            } else {
                                receiving_collision_object.speed.x /= -2;
                                receiving_collision_object.speed.x -= giving_collision_object.speed.x;
                            }
                        }
                        if (giving_collision_object.movedirection.y != 0) {
                            if (giving_collision_object.movedirection.y == Math.signum(receiving_collision_object.speed.y)) {
                                receiving_collision_object.speed.y = giving_collision_object.speed.y;
                            } else {
                                receiving_collision_object.speed.y /= -2;
                                receiving_collision_object.speed.y -= giving_collision_object.speed.y;
                            }
                        }
                        break;
                    case Respawnable:
                        giving_collision_object.respawn();
                        break;
                    case Destroyable: {
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
            case WormHole:
                switch (giving_collision_object.givingcollision) {
                    case Ghost:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
                        break;
                    case Bounce:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
                        break;
                    case WormHole:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
                        break;
                    case Imparable:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
                        break;
                    case Unmoveable:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
                        break;
                    case Respawnable:
                        giving_collision_object.respawn();
                        break;
                    case Destroyable: {
                        try {
                            giving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Farest:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
                        break;
                }
            case Imparable:
                switch (giving_collision_object.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        giving_collision_object.speed.x = -giving_collision_object.speed.x;
                        giving_collision_object.speed.y = -giving_collision_object.speed.y;
                        break;
                    case WormHole:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
                        break;
                    case Imparable:
                        break;
                    case Unmoveable:
                        break;
                    case Respawnable:
                        giving_collision_object.respawn();
                        break;
                    case Destroyable: {
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
                switch (giving_collision_object.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        giving_collision_object.speed.x = -giving_collision_object.speed.x;
                        giving_collision_object.speed.y = -giving_collision_object.speed.y;
                        break;
                    case WormHole:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
                        break;
                    case Imparable:
                        break;
                    case Unmoveable:
                        break;
                    case Respawnable:
                        giving_collision_object.respawn();
                        break;
                    case Destroyable: {
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
                switch (giving_collision_object.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        giving_collision_object.speed.x /= -2;
                        giving_collision_object.speed.y /= -2;
                        receiving_collision_object.respawn();
                        break;
                    case WormHole:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
                        receiving_collision_object.respawn();
                        break;
                    case Imparable:
                        receiving_collision_object.respawn();
                        break;
                    case Unmoveable:
                        receiving_collision_object.respawn();
                        break;
                    case Respawnable:
                        receiving_collision_object.respawn();
                        giving_collision_object.respawn();
                        break;
                    case Destroyable: {
                        try {
                            giving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    receiving_collision_object.respawn();
                    break;
                    case Farest:
                        giving_collision_object.speed.x = 0;
                        giving_collision_object.speed.y = 0;
                        receiving_collision_object.respawn();
                        break;
                }
            case Destroyable:
                switch (giving_collision_object.givingcollision) {
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
                    case WormHole:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
                         {
                            try {
                                receiving_collision_object.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Imparable: {
                        try {
                            receiving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Unmoveable: {
                        try {
                            receiving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Respawnable: {
                        try {
                            receiving_collision_object.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    giving_collision_object.respawn();
                    break;
                    case Destroyable: {
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
                switch (giving_collision_object.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        receiving_collision_object.location.x += new Float(Math.signum(receiving_collision_object.speed.x)).intValue();
                        receiving_collision_object.location.y += new Float(Math.signum(receiving_collision_object.speed.y)).intValue();
                        break;
                    case WormHole:
                        giving_collision_object.location.x += giving_collision_object.movedirection.x;
                        giving_collision_object.location.y += giving_collision_object.movedirection.y;
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
                        giving_collision_object.respawn();
                        break;
                    case Destroyable: {
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
