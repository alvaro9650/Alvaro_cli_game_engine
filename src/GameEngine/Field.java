/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A field that has a x size y size and can contain game objects
 *
 * @author alvaro9650
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
     * @author alvaro9650
     */
    public Field(Integer x, Integer y, Integer maxobjectspercoord) {
        this.size = new TwoDimensionsSize(x, y);
        this.gameobjects = new GameObject[this.size.x][this.size.y][maxobjectspercoord];
    }

    /**
     * Remove an object from a location in the field
     *
     * @param gameobject The object you want to delete
     * @throws GameEngine.ImpossibleLocationRemoveException
     * @author alvaro9650
     */
    public void deleteGameObject(GameObject gameobject) throws ImpossibleLocationRemoveException {
        if (gameobject.arrayposition < this.gameobjects[gameobject.location.x][gameobject.location.y].length && gameobject.hashCode() == this.gameobjects[gameobject.location.x][gameobject.location.y][gameobject.arrayposition].hashCode()) {
            this.gameobjects[gameobject.location.x][gameobject.location.y][gameobject.arrayposition] = null;
        } else {
            throw new ImpossibleLocationRemoveException("Object is not in the object stated location");
        }
        gameobject.located = false;
    }

    /**
     * Add an object to a location in the field
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
        TwoDimensionsSize objectsize;
        switch (gameobject.type) {
            case Simple: {
                objectsize = new TwoDimensionsSize(1, 1);
                break;
            }
            case Composite2dGameObjectType: {
                objectsize = ((Composite2dGameObject) gameobject).size;
                break;
            }
            case Composite3dGameObjectType: {
                objectsize = ((Composite3dGameObject) gameobject).size;
                break;
            }
            default:
                objectsize = new TwoDimensionsSize(0, 0);
                break;
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
        Integer[][] spaceavailable = new Integer[objectsize.x][objectsize.y];
        for (Integer xoffset = 0; xoffset < objectsize.x; xoffset++) {
            for (Integer yoffset = 0; yoffset < objectsize.y; yoffset++) {
                for (spaceavailable[xoffset][yoffset] = 0; spaceavailable[xoffset][yoffset] < this.gameobjects[gameobject.location.x + xoffset][gameobject.location.y + yoffset].length && this.gameobjects[gameobject.location.x + xoffset][gameobject.location.y + yoffset][spaceavailable[xoffset][yoffset]] != null; spaceavailable[xoffset][yoffset]++) {
                }
                if (spaceavailable[xoffset][yoffset] >= this.gameobjects[gameobject.location.x + xoffset][gameobject.location.y + yoffset].length) {
                    throw new ImpossibleLocationAddException("No space available");
                }
            }
        }
        switch (gameobject.type) {
            case Simple: {
                gameobject.arrayposition=spaceavailable[0][0];
                break;
            }
            case Composite2dGameObjectType: {
                ((Composite2dGameObject)gameobject).arrayposition = spaceavailable;
                break;
            }
        }
        for (Integer xoffset = 0; xoffset < objectsize.x; xoffset++) {
            for (Integer yoffset = 0; yoffset < objectsize.y; yoffset++) {
                this.gameobjects[gameobject.location.x + xoffset][gameobject.location.y + yoffset][gameobject.arrayposition = spaceavailable[xoffset][yoffset]] = gameobject;
            }
        }
        gameobject.located = true;
    }

    /**
     * Add an object to a location in the field without checking if it collides
     * bewcause collision is suposed to already been processed
     *
     * @param gameobject The object you want to add
     * @throws GameEngine.ImpossibleLocationAddException
     * @throws GameEngine.OutOfBoundsException
     * @author alvaro9650
     * @throws GameEngine.ImpossibleLocationRemoveException
     */
    public void addCollidedGameObject(GameObject gameobject) throws ImpossibleLocationAddException, OutOfBoundsException, ImpossibleLocationRemoveException {
        if (gameobject.located) {
            this.deleteGameObject(gameobject);
        }
        if (!(new RectangularArea(this.size.x - 1, 0, this.size.y - 1, 0).getCommonArea(gameobject.posiblelocationarea).isInside(gameobject.location))) {
            throw new OutOfBoundsException();
        }
        Integer i = 0;
        while (i < this.gameobjects[gameobject.location.x][gameobject.location.y].length && this.gameobjects[gameobject.location.x][gameobject.location.y][i] != null) {
            i++;
        }
        if (i < this.gameobjects[gameobject.location.x][gameobject.location.y].length) {
            this.gameobjects[gameobject.location.x][gameobject.location.y][gameobject.arrayposition = i] = gameobject;
        } else {
            throw new ImpossibleLocationAddException("No space available");
        }
        gameobject.located = true;
    }

    /**
     * Checks if coordinate has space available
     *
     * @param coordinate The coordinate you want to know if has space
     * @param gameobject The object you want to check space for
     * @return true if there is space to put an object in that Coordinate false
     * if not
     * @author alvaro9650
     */
    public Boolean checkSpaceAvailable(Coordinate coordinate, GameObject gameobject) {
        TwoDimensionsSize objectsize;
        switch (gameobject.type) {
            case Simple: {
                objectsize = new TwoDimensionsSize(1, 1);
                break;
            }
            case Composite2dGameObjectType: {
                objectsize = ((Composite2dGameObject) gameobject).size;
                break;
            }
            case Composite3dGameObjectType: {
                objectsize = ((Composite3dGameObject) gameobject).size;
                break;
            }
            default:
                objectsize = new TwoDimensionsSize(0, 0);
                break;
        }
        for (Integer xoffset = 0; xoffset < objectsize.x; xoffset++) {
            coord:
            for (Integer yoffset = 0; yoffset < objectsize.x; yoffset++) {
                for (GameObject object : this.gameobjects[coordinate.x + xoffset][coordinate.y + yoffset]) {
                    if (object == null) {
                        continue coord;
                    }
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Tells if the object can be relocated there
     *
     * @param gameobject Object you want to know if can be relocated there
     * @param coordinate Cordinate you want to know if can be relocated
     * @return Returns a boolean with true or false depending on if it's
     * possible to relocate the object there
     * @author alvaro9650
     */
    public Boolean canRelocateGameObject(GameObject gameobject, Coordinate coordinate) {
        TwoDimensionsSize objectsize;
        switch (gameobject.type) {
            case Simple: {
                objectsize = new TwoDimensionsSize(1, 1);
                break;
            }
            case Composite2dGameObjectType: {
                objectsize = ((Composite2dGameObject) gameobject).size;
                break;
            }
            case Composite3dGameObjectType: {
                objectsize = ((Composite3dGameObject) gameobject).size;
                break;
            }
            default:
                objectsize = new TwoDimensionsSize(0, 0);
                break;
        }
        return new RectangularArea(this.size.x - 1, 0, this.size.y - 1, 0).getCommonArea(gameobject.posiblelocationarea).isInside(new Coordinate(coordinate.x + objectsize.x, coordinate.y + objectsize.y)) || !checkSpaceAvailable(coordinate, gameobject);
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
        for (GameObject object : this.gameobjects[objectlookingforcollider.location.x][objectlookingforcollider.location.y]) {
            if (object != null && Objects.equals(object.height, objectlookingforcollider.height) && !Objects.equals(object, objectlookingforcollider)) {
                return object;
            }
        }
        return null;
    }

    /**
     * It's used to process a collision between 2 objects
     *
     * @param receivingcollisionobject Object which this object collides to
     * @param givingcollisionobject
     * @author alvaro9650
     */
    public void processCollision(GameObject givingcollisionobject, GameObject receivingcollisionobject) {
        givingcollisionobject.giveCollision(receivingcollisionobject);
        receivingcollisionobject.receiveCollision(givingcollisionobject);
        switch (receivingcollisionobject.receivingcollision) {
            case Ghost:
                switch (givingcollisionobject.givingcollision) {
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
                switch (givingcollisionobject.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        givingcollisionobject.speed.x /= -2;
                        receivingcollisionobject.speed.x -= givingcollisionobject.speed.x;
                        givingcollisionobject.speed.y /= -2;
                        receivingcollisionobject.speed.x -= givingcollisionobject.speed.y;
                        break;
                    case WormHole:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                        break;
                    case Imparable:
                        if (givingcollisionobject.movedirection.x != 0) {
                            if (givingcollisionobject.movedirection.x == Math.signum(receivingcollisionobject.speed.x)) {
                                receivingcollisionobject.speed.x = givingcollisionobject.speed.x;
                            } else {
                                receivingcollisionobject.speed.x /= -2;
                                receivingcollisionobject.speed.x -= givingcollisionobject.speed.x;
                            }
                        }
                        if (givingcollisionobject.movedirection.y != 0) {
                            if (givingcollisionobject.movedirection.y == Math.signum(receivingcollisionobject.speed.y)) {
                                receivingcollisionobject.speed.y = givingcollisionobject.speed.y;
                            } else {
                                receivingcollisionobject.speed.y /= -2;
                                receivingcollisionobject.speed.y -= givingcollisionobject.speed.y;
                            }
                        }
                        break;
                    case Unmoveable:
                        if (givingcollisionobject.movedirection.x != 0) {
                            if (givingcollisionobject.movedirection.x == Math.signum(receivingcollisionobject.speed.x)) {
                                receivingcollisionobject.speed.x = givingcollisionobject.speed.x;
                            } else {
                                receivingcollisionobject.speed.x /= -2;
                                receivingcollisionobject.speed.x -= givingcollisionobject.speed.x;
                            }
                        }
                        if (givingcollisionobject.movedirection.y != 0) {
                            if (givingcollisionobject.movedirection.y == Math.signum(receivingcollisionobject.speed.y)) {
                                receivingcollisionobject.speed.y = givingcollisionobject.speed.y;
                            } else {
                                receivingcollisionobject.speed.y /= -2;
                                receivingcollisionobject.speed.y -= givingcollisionobject.speed.y;
                            }
                        }
                        break;
                    case Respawnable:
                        givingcollisionobject.respawn();
                        break;
                    case Destroyable: {
                        try {
                            givingcollisionobject.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Farest:
                        givingcollisionobject.stop();
                        break;
                }
            case WormHole:
                switch (givingcollisionobject.givingcollision) {
                    case Ghost:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                        break;
                    case Bounce:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                        break;
                    case WormHole:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                        break;
                    case Imparable:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                        break;
                    case Unmoveable:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                        break;
                    case Respawnable:
                        givingcollisionobject.respawn();
                        break;
                    case Destroyable: {
                        try {
                            givingcollisionobject.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Farest:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                        break;
                }
            case Imparable:
                switch (givingcollisionobject.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        givingcollisionobject.speed.x = -givingcollisionobject.speed.x;
                        givingcollisionobject.speed.y = -givingcollisionobject.speed.y;
                        break;
                    case WormHole:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                        break;
                    case Imparable:
                        break;
                    case Unmoveable:
                        break;
                    case Respawnable:
                        givingcollisionobject.respawn();
                        break;
                    case Destroyable: {
                        try {
                            givingcollisionobject.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Farest:
                        givingcollisionobject.stop();
                        break;
                }
                break;
            case Unmoveable:
                switch (givingcollisionobject.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        givingcollisionobject.speed.x = -givingcollisionobject.speed.x;
                        givingcollisionobject.speed.y = -givingcollisionobject.speed.y;
                        break;
                    case WormHole:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                        break;
                    case Imparable:
                        break;
                    case Unmoveable:
                        break;
                    case Respawnable:
                        givingcollisionobject.respawn();
                        break;
                    case Destroyable: {
                        try {
                            givingcollisionobject.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Farest:
                        givingcollisionobject.stop();
                        break;
                }
                break;
            case Respawnable:
                switch (givingcollisionobject.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        givingcollisionobject.speed.x /= -2;
                        givingcollisionobject.speed.y /= -2;
                        receivingcollisionobject.respawn();
                        break;
                    case WormHole:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                        receivingcollisionobject.respawn();
                        break;
                    case Imparable:
                        receivingcollisionobject.respawn();
                        break;
                    case Unmoveable:
                        receivingcollisionobject.respawn();
                        break;
                    case Respawnable:
                        receivingcollisionobject.respawn();
                        givingcollisionobject.respawn();
                        break;
                    case Destroyable: {
                        try {
                            givingcollisionobject.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    receivingcollisionobject.respawn();
                    break;
                    case Farest:
                        givingcollisionobject.stop();
                        receivingcollisionobject.respawn();
                        break;
                }
            case Destroyable:
                switch (givingcollisionobject.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        givingcollisionobject.speed.x /= -2;
                        givingcollisionobject.speed.y /= -2;
                         {
                            try {
                                receivingcollisionobject.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case WormHole:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                         {
                            try {
                                receivingcollisionobject.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case Imparable: {
                        try {
                            receivingcollisionobject.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Unmoveable: {
                        try {
                            receivingcollisionobject.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Respawnable: {
                        try {
                            receivingcollisionobject.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    givingcollisionobject.respawn();
                    break;
                    case Destroyable: {
                        try {
                            givingcollisionobject.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                     {
                        try {
                            receivingcollisionobject.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Farest:
                        givingcollisionobject.stop();
                         {
                            try {
                                receivingcollisionobject.close();
                            } catch (IOException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                }
            case Farest:
                switch (givingcollisionobject.givingcollision) {
                    case Ghost:
                        break;
                    case Bounce:
                        receivingcollisionobject.location.x += new Float(Math.signum(receivingcollisionobject.speed.x)).intValue();
                        receivingcollisionobject.location.y += new Float(Math.signum(receivingcollisionobject.speed.y)).intValue();
                        break;
                    case WormHole:
                        givingcollisionobject.location.x += givingcollisionobject.movedirection.x;
                        givingcollisionobject.location.y += givingcollisionobject.movedirection.y;
                        break;
                    case Imparable:
                        receivingcollisionobject.location.x += new Float(Math.signum(receivingcollisionobject.speed.x)).intValue();
                        receivingcollisionobject.location.y += new Float(Math.signum(receivingcollisionobject.speed.y)).intValue();
                        break;
                    case Unmoveable:
                        receivingcollisionobject.location.x += new Float(Math.signum(receivingcollisionobject.speed.x)).intValue();
                        receivingcollisionobject.location.y += new Float(Math.signum(receivingcollisionobject.speed.y)).intValue();
                        break;
                    case Respawnable:
                        givingcollisionobject.respawn();
                        break;
                    case Destroyable: {
                        try {
                            receivingcollisionobject.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case Farest:
                        givingcollisionobject.stop();
                        break;
                }
        }
        try {
            this.addCollidedGameObject(givingcollisionobject);
            this.addCollidedGameObject(receivingcollisionobject);
        } catch (ImpossibleLocationAddException ex) {
            System.out.println("Imposible location add");
        } catch (OutOfBoundsException ex) {
            givingcollisionobject.processOutOfBounds(givingcollisionobject.location);
        } catch (ImpossibleLocationRemoveException ex) {
            System.out.println("Imposible location remove");
        }
        try {
            this.addCollidedGameObject(receivingcollisionobject);
        } catch (ImpossibleLocationAddException ex) {
            System.out.println("Imposible location add");
        } catch (OutOfBoundsException ex) {
            receivingcollisionobject.processOutOfBounds(receivingcollisionobject.location);
        } catch (ImpossibleLocationRemoveException ex) {
            System.out.println("Imposible location remove");
        }
    }

    /**
     * Returns a string containing the field information
     *
     * @author alvaro9650
     * @return The string containing the field information
     */
    @Override
    public String toString() {
        return new StringBuilder("Field resolution = ").insert(19, this.size.toString()).toString();
    }
}
