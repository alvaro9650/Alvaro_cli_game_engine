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
 *
 * @author alvaro9650
 */
public class Composite2dGameObject extends CompositeGameObject {

    // The size of the Composite2dGameObject
    public TwoDimensionsSize size;
    // The objects the Composite2dGameObject contains
    public Composite2dGameObjectComponent[][][] componentobjects;

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
        this.componentobjects = new Composite2dGameObjectComponent[x][y][maxobjectspercoord];
    }

    /**
     * Add an object to a location in the object
     *
     * @param composite2dgameobjectcomponent The object you want to add
     * @throws GameEngine.ImpossibleLocationAddException
     * @throws GameEngine.ObjectCollidesException
     * @throws GameEngine.OutOfBoundsException
     * @throws GameEngine.ImpossibleLocationRemoveException
     * @author alvaro9650
     */
    public void addComposite2dGameObjectComponent(Composite2dGameObjectComponent composite2dgameobjectcomponent) throws ImpossibleLocationAddException, ObjectCollidesException, OutOfBoundsException, ImpossibleLocationRemoveException {
        Composite2dGameObjectComponent collider;
        if (composite2dgameobjectcomponent.located) {
            this.deleteGameObject(composite2dgameobjectcomponent);
        }
        if (!(new RectangularArea(this.size.x - 1, 0, this.size.y - 1, 0).getCommonArea(composite2dgameobjectcomponent.posiblelocationarea).isInside(composite2dgameobjectcomponent.location))) {
            throw new OutOfBoundsException();
        } else if ((collider = this.collidesWith(composite2dgameobjectcomponent)) != null) {
            switch (collider.physicalstatetype) {
                case Solid:
                case SolidWithHoles:
                    switch (composite2dgameobjectcomponent.physicalstatetype) {
                        case SolidWithHoles:
                        case Solid:
                            throw new ObjectCollidesException();
                    }
                case Liquid:
                    switch (composite2dgameobjectcomponent.physicalstatetype) {
                        case Solid:
                            throw new ObjectCollidesException();
                    }
                case Gas:
                    switch (composite2dgameobjectcomponent.physicalstatetype) {
                        case Solid:
                        case Liquid:
                            throw new ObjectCollidesException();
                    }
            }
        }
        Integer i = 0;
        while (i < this.componentobjects[composite2dgameobjectcomponent.location.x][composite2dgameobjectcomponent.location.y].length && this.componentobjects[composite2dgameobjectcomponent.location.x][composite2dgameobjectcomponent.location.y][i] != null) {
            i++;
        }
        if (i < this.componentobjects[composite2dgameobjectcomponent.location.x][composite2dgameobjectcomponent.location.y].length) {
            this.componentobjects[composite2dgameobjectcomponent.location.x][composite2dgameobjectcomponent.location.y][i] = composite2dgameobjectcomponent;
            composite2dgameobjectcomponent.arrayposition = i;
        } else {
            throw new ImpossibleLocationAddException("No space available");
        }
        composite2dgameobjectcomponent.located = true;
    }

    /**
     * Remove an object from a location in the object
     *
     * @param composite2dgameobjectcomponent The object you want to delete
     * @throws GameEngine.ImpossibleLocationRemoveException
     * @author alvaro9650
     */
    public void deleteGameObject(Composite2dGameObjectComponent composite2dgameobjectcomponent) throws ImpossibleLocationRemoveException {
        if (composite2dgameobjectcomponent.arrayposition < this.componentobjects[composite2dgameobjectcomponent.location.x][composite2dgameobjectcomponent.location.y].length && composite2dgameobjectcomponent.hashCode() == this.componentobjects[composite2dgameobjectcomponent.location.x][composite2dgameobjectcomponent.location.y][composite2dgameobjectcomponent.arrayposition].hashCode()) {
            this.componentobjects[composite2dgameobjectcomponent.location.x][composite2dgameobjectcomponent.location.y][composite2dgameobjectcomponent.arrayposition] = null;
        } else {
            throw new ImpossibleLocationRemoveException("Object is not in the object stated location");
        }
        composite2dgameobjectcomponent.located = false;
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
    public Composite2dGameObjectComponent collidesWith(GameObject objectlookingforcollider) {
        for (Composite2dGameObjectComponent object : this.componentobjects[objectlookingforcollider.location.x][objectlookingforcollider.location.y]) {
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
    public void processCollision(Composite2dGameObjectComponent givingcollisionobject, Composite2dGameObjectComponent receivingcollisionobject) {
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
            this.addCollidedComposite2dGameObjectComponent(givingcollisionobject);
            this.addCollidedComposite2dGameObjectComponent(receivingcollisionobject);
        } catch (ImpossibleLocationAddException ex) {
            System.out.println("Imposible location add");
        } catch (OutOfBoundsException ex) {
            givingcollisionobject.processOutOfBounds(givingcollisionobject.location);
        } catch (ImpossibleLocationRemoveException ex) {
            System.out.println("Imposible location remove");
        }
        try {
            this.addCollidedComposite2dGameObjectComponent(receivingcollisionobject);
        } catch (ImpossibleLocationAddException ex) {
            System.out.println("Imposible location add");
        } catch (OutOfBoundsException ex) {
            receivingcollisionobject.processOutOfBounds(receivingcollisionobject.location);
        } catch (ImpossibleLocationRemoveException ex) {
            System.out.println("Imposible location remove");
        }
    }
    /**
     * Add an object to a location in the Composite2dGameObject without checking
     * if it collides because collision is suposed to already been processed
     *
     * @param composite2dgameobjectcomponent The Composite2dGameObjectComponent
     * you want to add
     * @throws GameEngine.ImpossibleLocationAddException
     * @throws GameEngine.OutOfBoundsException
     * @throws GameEngine.ImpossibleLocationRemoveException
     * @author alvaro9650
     */
    public void addCollidedComposite2dGameObjectComponent(Composite2dGameObjectComponent composite2dgameobjectcomponent) throws ImpossibleLocationAddException, OutOfBoundsException, ImpossibleLocationRemoveException {
        if (composite2dgameobjectcomponent.located) {
            this.deleteGameObject(composite2dgameobjectcomponent);
        }
        if (!(new RectangularArea(this.size.x - 1, 0, this.size.y - 1, 0).getCommonArea(composite2dgameobjectcomponent.posiblelocationarea).isInside(composite2dgameobjectcomponent.location))) {
            throw new OutOfBoundsException();
        }
        Integer i = 0;
        while (i < this.componentobjects[composite2dgameobjectcomponent.location.x][composite2dgameobjectcomponent.location.y].length && this.componentobjects[composite2dgameobjectcomponent.location.x][composite2dgameobjectcomponent.location.y][i] != null) {
            i++;
        }
        if (i < this.componentobjects[composite2dgameobjectcomponent.location.x][composite2dgameobjectcomponent.location.y].length) {
            this.componentobjects[composite2dgameobjectcomponent.location.x][composite2dgameobjectcomponent.location.y][composite2dgameobjectcomponent.arrayposition = i] = composite2dgameobjectcomponent;
        } else {
            throw new ImpossibleLocationAddException("No space available");
        }
        composite2dgameobjectcomponent.located = true;
    }
}
