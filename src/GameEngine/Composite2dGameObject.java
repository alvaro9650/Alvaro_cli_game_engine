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
        GameObject collider;
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
}
