/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

import alvaro_tools.MathCustomFuncs;
import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Object to use in the game
 *
 * @author alumno1718_2
 */
public class GameObject implements Closeable {

    public Coordinate location;
    public Integer height;
    public Integer arrayposition;
    public Speed speed;
    public Field playingfield;
    public RectangularArea posiblelocationarea;
    public RectangularArea respawnarea;
    public LogLevel loglevel;
    public char character;
    public OutOfBoundsMoveType outofboundsmovetype;
    public PhysicalStateType physicalstatetype;
    public MoveType movetype;
    public String objecttype;
    public CollisionType receivingcollision;
    public CollisionType givingcollision;
    public Integer movedirectionvertical;
    public Integer movedirectionhorizontal;

    /**
     * Creates a basic game object , should be overriden
     *
     * @param field The field where the object is located
     */
    public GameObject(Field field) {
        this.character = '|';
        this.height = 0;
        this.objecttype = "Default";
        this.speed = new Speed(0, 0);
        this.movetype = MoveType.None;
        this.playingfield = field;
        this.posiblelocationarea = new RectangularArea(field.sizex - 1, 0, field.sizey - 1, 0);
        this.respawnarea = new RectangularArea(field.sizex - 1, 0, field.sizey - 1, 0);
        this.physicalstatetype = PhysicalStateType.Ghost;
        this.outofboundsmovetype = OutOfBoundsMoveType.Circular_universe;
        this.loglevel = LogLevel.None;
        this.location = new Coordinate(this.posiblelocationarea.mincoord.x, this.posiblelocationarea.mincoord.y);
    }

    /**
     * Creates a respawn point to use when it's going to be respawned
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setRespawnPoint(Integer x, Integer y) {
        this.respawnarea = new RectangularArea(x, x, y, y);
    }

    /**
     * Creates a respawn area to use when it's going to be respawned
     *
     * @param maxx Maximum x location of the area
     * @param minx Minumum x location of the area
     * @param maxy Maximum y location of the area
     * @param miny Minumum y location of the area
     */
    public void setRespawnArea(Integer maxx, Integer minx, Integer maxy, Integer miny) {
        this.respawnarea = new RectangularArea(maxx, minx, maxy, miny);
    }

    /**
     * Logs the data in the object acording to LogLevel
     */
    public void log() {
        StringBuilder objectlog = new StringBuilder();
        objectlog.append("Object: ");
        objectlog.append(this.hashCode());
        switch (this.loglevel) {
            case Verbose:
                objectlog.append("\nlog_level = verbose");
            case Draw_related:
                objectlog.append("\ncharacter = ");
                objectlog.append(this.character);
            case Move_related:
                objectlog.append("\nmove_type = ");
                objectlog.append(this.movetype);
            case Out_of_bounds_related:
                objectlog.append("\nplaying_field x = ");
                objectlog.append(this.playingfield.sizex);
                objectlog.append("\nplaying_field y = ");
                objectlog.append(this.playingfield.sizey);
                objectlog.append("\nspeed x = ");
                objectlog.append(this.speed.x);
                objectlog.append("\nspeed y = ");
                objectlog.append(this.speed.y);
                objectlog.append("\nmax_x_location = ");
                objectlog.append(this.posiblelocationarea.maxcoord.x);
                objectlog.append("\nmin_x_location = ");
                objectlog.append(this.posiblelocationarea.mincoord.x);
                objectlog.append("\nmax_y_location = ");
                objectlog.append(this.posiblelocationarea.maxcoord.y);
                objectlog.append("\nmin_y_location = ");
                objectlog.append(this.posiblelocationarea.mincoord.y);
                objectlog.append("\nmax_x_respawn_area = ");
                objectlog.append(this.respawnarea.maxcoord.x);
                objectlog.append("\nmin_x_respawn_area = ");
                objectlog.append(this.respawnarea.mincoord.x);
                objectlog.append("\nmax_y_respawn_area = ");
                objectlog.append(this.respawnarea.maxcoord.y);
                objectlog.append("\nmin_y_respawn_area = ");
                objectlog.append(this.respawnarea.mincoord.y);
                objectlog.append("\nout_of_bounds_move_type = ");
                objectlog.append(this.outofboundsmovetype);
            case Collision_related:
                objectlog.append("\ncollision_type = ");
                objectlog.append(this.physicalstatetype);
            case Position_related:
                objectlog.append("\ncoordinate x = ");
                objectlog.append(location.x.toString());
                objectlog.append("\ncoordinate y = ");
                objectlog.append(location.y.toString());
                break;
            case None:
                objectlog.delete(0, objectlog.length());
        }
        System.out.println(objectlog.toString());
    }

    /**
     * Teleports an object to a location
     *
     * @param coord The coordinate you want to move this object to
     * @throws ImpossibleLocationRemoveException
     * @throws ImpossibleLocationAddException
     */
    public void moveTo(Coordinate coord) throws ImpossibleLocationRemoveException, ImpossibleLocationAddException {
        this.playingfield.deleteGameObject(this);
        this.location.x = coord.x;
        this.location.y = coord.y;
        this.playingfield.addGameObject(this);
    }

    /**
     * Checks if location can be updated
     *
     * @return Returns an array contaning if x and y can be updated
     */
    public Boolean canUpdateLocation() {
        switch (this.outofboundsmovetype) {
            case Bounceable:
                return canUpdateBounceableLocation();
            case Respawnable:
                return canUpdateRespawnableLocation();
            case Impossible:
            //return CanUpdateimpossibleLocation();
            case Destroyable:
            //return CanUpdatedestroyableLocation();
            case Possible:
            //return CanUpdatepossibleLocation();
            case Farest:
            //return CanUpdatefarestLocation();
            case Circular_universe:
            //return CanUpdatecircularuniverseLocation();
        }
        return false;
    }

    /**
     * Updates location in the object and the field
     */
    public void updateLocation() {
        if (this.speed.x != 0 || this.speed.y != 0) {
            switch (this.outofboundsmovetype) {
                case Bounceable:
                    updateBounceableLocation();
                    break;
                case Respawnable:
                    updateRespawnableLocation();
                    break;
                case Impossible:
                    //UpdateimpossibleLocation();
                    break;
                case Destroyable:
                    //try {
                    //UpdatedestroyableLocation();
                    //} catch (IOException ex) {
                    //    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    //}
                    break;
                case Possible:
                    //UpdatepossibleLocation();
                    break;
                case Farest:
                    //UpdatefarestLocation();
                    break;
                case Circular_universe:
                    //UpdatecircularuniverseLocation();
                    break;
            }
        }
    }

    /**
     * Checks if it will bounce when it moves
     *
     * @return A boolean containing the result
     */
    public Boolean willBounceAgainstBounds() {
        Coordinate destinylocation = new Coordinate(this.location.x, this.location.y);
        switch (this.movetype) {
            case Teleport:
                RectangularArea possiblebouncearea = new RectangularArea(this.playingfield.sizex - 1, 0, this.playingfield.sizey - 1, 0).getCommonArea(this.posiblelocationarea);
                GameObject collisionreceiver;
                while ((collisionreceiver = this.playingfield.collidesWith(this)) != null) {
                    this.playingfield.processCollision(this, collisionreceiver);
                }
                if ((destinylocation.x += this.speed.x) > possiblebouncearea.maxcoord.x || destinylocation.x < possiblebouncearea.mincoord.x || (destinylocation.y += this.speed.y) > possiblebouncearea.maxcoord.y || destinylocation.y < possiblebouncearea.mincoord.y) {
                    return true;
                }
                return false;
            case None:
                return false;
            default:
                Speed movingspeed = new Speed(new Float(Math.signum(this.speed.x)).intValue(), new Float(Math.signum(this.speed.x)).intValue());
                Speed remainingspeed = new Speed(this.speed.x, this.speed.y);
                switch (this.movetype) {
                    case Horizontal_first:
                        for (; remainingspeed.x != 0; destinylocation.x += movingspeed.x, remainingspeed.x -= movingspeed.x) {
                            if (this.playingfield.canRelocateGameObject(this, destinylocation)) {

                            } else {
                                return true;
                            }
                        }
                        for (; remainingspeed.y != 0; destinylocation.y += movingspeed.y, remainingspeed.y -= movingspeed.y) {
                            if (this.playingfield.canRelocateGameObject(this, destinylocation)) {

                            } else {
                                return true;
                            }
                        }
                        return false;
                    case Vertical_first:
                        for (; remainingspeed.y != 0; destinylocation.y += movingspeed.y, remainingspeed.y -= movingspeed.y) {
                            if (this.playingfield.canRelocateGameObject(this, destinylocation)) {

                            } else {
                                return true;
                            }
                        }
                        for (; remainingspeed.x != 0; destinylocation.x += movingspeed.x, remainingspeed.x -= movingspeed.x) {
                            if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                            } else {
                                return true;
                            }
                        }
                        return false;
                    case Diagonal:
                        if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.x) >= Math.abs(this.speed.y)) {
                            Integer times = Math.abs(this.speed.x / this.speed.y);
                            for (; remainingspeed.y != 0; destinylocation.y += movingspeed.y) {
                                if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                    remainingspeed.y -= movingspeed.y;
                                } else {
                                    return true;
                                }
                                for (Integer i = 0; i < times; i++, destinylocation.x += movingspeed.x) {
                                    if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                        remainingspeed.x -= movingspeed.x;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                            for (; remainingspeed.x != 0; destinylocation.x += movingspeed.x) {
                                if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                    remainingspeed.x -= movingspeed.x;
                                } else {
                                    return true;
                                }
                            }
                            return false;
                        } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y) >= Math.abs(this.speed.x)) {
                            Integer times = Math.abs(this.speed.y / this.speed.x);
                            for (; remainingspeed.x != 0; destinylocation.x += movingspeed.x) {
                                if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                    remainingspeed.x -= movingspeed.x;
                                } else {
                                    return true;
                                }
                                for (Integer i = 0; i < times; i++, destinylocation.x += movingspeed.y) {
                                    if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                        remainingspeed.y -= movingspeed.y;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                            for (; remainingspeed.y != 0; destinylocation.y += movingspeed.y) {
                                if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                    remainingspeed.y -= movingspeed.y;
                                } else {
                                    return true;
                                }
                            }
                            return false;
                        } else {
                            do {
                                destinylocation.x += movingspeed.x;
                                destinylocation.y += movingspeed.y;
                                remainingspeed.x -= movingspeed.x;
                                remainingspeed.y -= movingspeed.y;
                                if (this.playingfield.canRelocateGameObject(this, destinylocation)) {

                                } else {
                                    return true;
                                }
                            } while (remainingspeed.x != 0 || remainingspeed.x != 0);
                            return false;
                        }
                }
        }
        return false;
    }

    /**
     * Checks if and updateBounceableLocation can be done
     *
     * @return A boolean containing the result
     */
    public Boolean canUpdateBounceableLocation() {
        Coordinate destinylocation = new Coordinate(this.location.x, this.location.y);
        switch (this.movetype) {
            case Teleport:
                Integer bouncingspace,
                 teoricaldestiny;
                RectangularArea possiblebouncearea = new RectangularArea(this.playingfield.sizex - 1, 0, this.playingfield.sizey - 1, 0).getCommonArea(this.posiblelocationarea);
                destinylocation.x = (((this.speed.x = ((teoricaldestiny = destinylocation.x + this.speed.x) / (bouncingspace = possiblebouncearea.maxcoord.x - possiblebouncearea.mincoord.x) % 2 != 0) ? -this.speed.x : this.speed.x) > 0) ? possiblebouncearea.mincoord.x : possiblebouncearea.maxcoord.x) + teoricaldestiny % bouncingspace * new Float(Math.signum(this.speed.x)).intValue();
                destinylocation.y = (((this.speed.y = ((teoricaldestiny = destinylocation.y + this.speed.y) / (bouncingspace = possiblebouncearea.maxcoord.y - possiblebouncearea.mincoord.y) % 2 != 0) ? -this.speed.y : this.speed.y) > 0) ? possiblebouncearea.mincoord.y : possiblebouncearea.maxcoord.y) + teoricaldestiny % bouncingspace * new Float(Math.signum(this.speed.y)).intValue();
                return this.playingfield.canRelocateGameObject(this, destinylocation);
            case None:
                return this.playingfield.canRelocateGameObject(this, destinylocation);
            default:
                Speed movingspeed = new Speed(new Float(Math.signum(this.speed.x)).intValue(), new Float(Math.signum(this.speed.x)).intValue());
                Speed remainingspeed = new Speed(this.speed.x, this.speed.y);
                switch (this.movetype) {
                    case Horizontal_first:
                        for (; remainingspeed.x != 0; destinylocation.x += movingspeed.x, remainingspeed.x -= movingspeed.x) {
                            if (this.playingfield.canRelocateGameObject(this, destinylocation)) {

                            } else {
                                movingspeed.x *= -1;
                                remainingspeed.x *= -1;
                                destinylocation.x += movingspeed.x;
                            }
                        }
                        for (; remainingspeed.y != 0; destinylocation.y += movingspeed.y, remainingspeed.y -= movingspeed.y) {
                            if (this.playingfield.canRelocateGameObject(this, destinylocation)) {

                            } else {
                                movingspeed.y *= -1;
                                remainingspeed.y *= -1;
                                destinylocation.y += movingspeed.y;
                            }
                        }
                        return this.playingfield.canRelocateGameObject(this, destinylocation);
                    case Vertical_first:
                        for (; remainingspeed.y != 0; destinylocation.y += movingspeed.y, remainingspeed.y -= movingspeed.y) {
                            if (this.playingfield.canRelocateGameObject(this, destinylocation)) {

                            } else {
                                movingspeed.y *= -1;
                                remainingspeed.y *= -1;
                                destinylocation.y += movingspeed.y;
                            }
                        }
                        for (; remainingspeed.x != 0; destinylocation.x += movingspeed.x, remainingspeed.x -= movingspeed.x) {
                            if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                            } else {
                                movingspeed.x *= -1;
                                remainingspeed.x *= -1;
                                destinylocation.x += movingspeed.x;
                            }
                        }
                        return this.playingfield.canRelocateGameObject(this, destinylocation);
                    case Diagonal:
                        if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.x) >= Math.abs(this.speed.y)) {
                            Integer times = Math.abs(this.speed.x / this.speed.y);
                            for (; remainingspeed.y != 0; destinylocation.y += movingspeed.y) {
                                if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                    remainingspeed.y -= movingspeed.y;
                                } else {
                                    movingspeed.y *= -1;
                                    remainingspeed.y *= -1;
                                    destinylocation.y += movingspeed.y;
                                    remainingspeed.y -= movingspeed.y;
                                }
                                for (Integer i = 0; i < times; i++, destinylocation.x += movingspeed.x) {
                                    if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                        remainingspeed.x -= movingspeed.x;
                                    } else {
                                        movingspeed.x *= -1;
                                        remainingspeed.x *= -1;
                                        destinylocation.x += movingspeed.x;
                                        remainingspeed.x -= movingspeed.x;
                                    }
                                }
                            }
                            for (; remainingspeed.x != 0; destinylocation.x += movingspeed.x) {
                                if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                    remainingspeed.x -= movingspeed.x;
                                } else {
                                    movingspeed.x *= -1;
                                    remainingspeed.x *= -1;
                                    destinylocation.x += movingspeed.x;
                                    remainingspeed.x -= movingspeed.x;
                                }
                            }
                            return this.playingfield.canRelocateGameObject(this, destinylocation);
                        } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y) >= Math.abs(this.speed.x)) {
                            Integer times = Math.abs(this.speed.y / this.speed.x);
                            for (; remainingspeed.x != 0; destinylocation.x += movingspeed.x) {
                                if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                    remainingspeed.x -= movingspeed.x;
                                } else {
                                    movingspeed.x *= -1;
                                    remainingspeed.x *= -1;
                                    destinylocation.x += movingspeed.x;
                                    remainingspeed.x -= movingspeed.x;
                                }
                                for (Integer i = 0; i < times; i++, destinylocation.x += movingspeed.y) {
                                    if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                        remainingspeed.y -= movingspeed.y;
                                    } else {
                                        movingspeed.y *= -1;
                                        remainingspeed.y *= -1;
                                        destinylocation.y += movingspeed.y;
                                        remainingspeed.y -= movingspeed.y;
                                    }
                                }
                            }
                            for (; remainingspeed.y != 0; destinylocation.y += movingspeed.y) {
                                if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
                                    remainingspeed.y -= movingspeed.y;
                                } else {
                                    movingspeed.y *= -1;
                                    remainingspeed.y *= -1;
                                    destinylocation.y += movingspeed.y;
                                    remainingspeed.y -= movingspeed.y;
                                }
                            }
                            return this.playingfield.canRelocateGameObject(this, destinylocation);
                        } else {
                            do {
                                destinylocation.x += movingspeed.x;
                                destinylocation.y += movingspeed.y;
                                remainingspeed.x -= movingspeed.x;
                                remainingspeed.y -= movingspeed.y;
                                if (this.playingfield.canRelocateGameObject(this, destinylocation)) {

                                } else {
                                    movingspeed.x *= -1;
                                    remainingspeed.x *= -1;
                                    movingspeed.y *= -1;
                                    remainingspeed.y *= -1;
                                    destinylocation.x += movingspeed.x;
                                    destinylocation.y += movingspeed.y;
                                }
                            } while (remainingspeed.x != 0 || remainingspeed.x != 0);
                            return this.playingfield.canRelocateGameObject(this, destinylocation);
                        }
                }
        }
        return false;
    }

    public void updateBounceableLocation() {
        Coordinate originallocation = this.location;
        if (canUpdateBounceableLocation()) {
            try {
                this.playingfield.deleteGameObject(this);
            } catch (ImpossibleRelocationException ex) {
                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
            }
            switch (this.movetype) {
                case Teleport:
                    Integer bouncingspace,
                     teoricaldestiny;
                    RectangularArea possiblebouncearea = new RectangularArea(this.playingfield.sizex - 1, 0, this.playingfield.sizey - 1, 0).getCommonArea(this.posiblelocationarea);
                    this.location.x = (((this.speed.x = ((teoricaldestiny = this.location.x + this.speed.x) / (bouncingspace = possiblebouncearea.maxcoord.x - possiblebouncearea.mincoord.x) % 2 != 0) ? -this.speed.x : this.speed.x) > 0) ? possiblebouncearea.mincoord.x : possiblebouncearea.maxcoord.x) + teoricaldestiny % bouncingspace * new Float(Math.signum(this.speed.x)).intValue();
                    this.location.y = (((this.speed.y = ((teoricaldestiny = this.location.y + this.speed.y) / (bouncingspace = possiblebouncearea.maxcoord.y - possiblebouncearea.mincoord.y) % 2 != 0) ? -this.speed.y : this.speed.y) > 0) ? possiblebouncearea.mincoord.y : possiblebouncearea.maxcoord.y) + teoricaldestiny % bouncingspace * new Float(Math.signum(this.speed.y)).intValue();
                    try {
                        this.playingfield.addGameObject(this);
                    } catch (ImpossibleRelocationException ex) {
                        this.location = originallocation;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case None:
                    try {
                        this.playingfield.addGameObject(this);
                    } catch (ImpossibleRelocationException ex) {
                        this.location = originallocation;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    Speed movingspeed = new Speed(new Float(Math.signum(this.speed.x)).intValue(), new Float(Math.signum(this.speed.x)).intValue());
                    Speed remainingspeed = new Speed(this.speed.x, this.speed.y);
                    switch (this.movetype) {
                        case Horizontal_first:
                            for (; remainingspeed.x != 0; this.location.x += movingspeed.x, remainingspeed.x -= movingspeed.x) {
                                if (this.playingfield.canRelocateGameObject(this, this.location)) {

                                } else {
                                    movingspeed.x *= -1;
                                    remainingspeed.x *= -1;
                                    this.location.x += movingspeed.x;
                                }
                            }
                            for (; remainingspeed.y != 0; this.location.y += movingspeed.y, remainingspeed.y -= movingspeed.y) {
                                if (this.playingfield.canRelocateGameObject(this, this.location)) {

                                } else {
                                    movingspeed.y *= -1;
                                    remainingspeed.y *= -1;
                                    this.location.y += movingspeed.y;
                                }
                            }
                            try {
                                this.playingfield.addGameObject(this);
                            } catch (ImpossibleRelocationException ex) {
                                this.location = originallocation;
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        case Vertical_first:
                            for (; remainingspeed.y != 0; this.location.y += movingspeed.y, remainingspeed.y -= movingspeed.y) {
                                if (this.playingfield.canRelocateGameObject(this, this.location)) {

                                } else {
                                    movingspeed.y *= -1;
                                    remainingspeed.y *= -1;
                                    this.location.y += movingspeed.y;
                                }
                            }
                            for (; remainingspeed.x != 0; this.location.x += movingspeed.x, remainingspeed.x -= movingspeed.x) {
                                if (this.playingfield.canRelocateGameObject(this, this.location)) {
                                } else {
                                    movingspeed.x *= -1;
                                    remainingspeed.x *= -1;
                                    this.location.x += movingspeed.x;
                                }
                            }
                            try {
                                this.playingfield.addGameObject(this);
                            } catch (ImpossibleRelocationException ex) {
                                this.location = originallocation;
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        case Diagonal:
                            if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.x) >= Math.abs(this.speed.y)) {
                                Integer times = Math.abs(this.speed.x / this.speed.y);
                                for (; remainingspeed.y != 0; this.location.y += movingspeed.y) {
                                    if (this.playingfield.canRelocateGameObject(this, this.location)) {
                                        remainingspeed.y -= movingspeed.y;
                                    } else {
                                        movingspeed.y *= -1;
                                        remainingspeed.y *= -1;
                                        this.location.y += movingspeed.y;
                                        remainingspeed.y -= movingspeed.y;
                                    }
                                    for (Integer i = 0; i < times; i++, this.location.x += movingspeed.x) {
                                        if (this.playingfield.canRelocateGameObject(this, this.location)) {
                                            remainingspeed.x -= movingspeed.x;
                                        } else {
                                            movingspeed.x *= -1;
                                            remainingspeed.x *= -1;
                                            this.location.x += movingspeed.x;
                                            remainingspeed.x -= movingspeed.x;
                                        }
                                    }
                                }
                                for (; remainingspeed.x != 0; this.location.x += movingspeed.x) {
                                    if (this.playingfield.canRelocateGameObject(this, this.location)) {
                                        remainingspeed.x -= movingspeed.x;
                                    } else {
                                        movingspeed.x *= -1;
                                        remainingspeed.x *= -1;
                                        this.location.x += movingspeed.x;
                                        remainingspeed.x -= movingspeed.x;
                                    }
                                }
                                try {
                                    this.playingfield.addGameObject(this);
                                } catch (ImpossibleRelocationException ex) {
                                    this.location = originallocation;
                                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y) >= Math.abs(this.speed.x)) {
                                Integer times = Math.abs(this.speed.y / this.speed.x);
                                for (; remainingspeed.x != 0; this.location.x += movingspeed.x) {
                                    if (this.playingfield.canRelocateGameObject(this, this.location)) {
                                        remainingspeed.x -= movingspeed.x;
                                    } else {
                                        movingspeed.x *= -1;
                                        remainingspeed.x *= -1;
                                        this.location.x += movingspeed.x;
                                        remainingspeed.x -= movingspeed.x;
                                    }
                                    for (Integer i = 0; i < times; i++, this.location.x += movingspeed.y) {
                                        if (this.playingfield.canRelocateGameObject(this, this.location)) {
                                            remainingspeed.y -= movingspeed.y;
                                        } else {
                                            movingspeed.y *= -1;
                                            remainingspeed.y *= -1;
                                            this.location.y += movingspeed.y;
                                            remainingspeed.y -= movingspeed.y;
                                        }
                                    }
                                }
                                for (; remainingspeed.y != 0; this.location.y += movingspeed.y) {
                                    if (this.playingfield.canRelocateGameObject(this, this.location)) {
                                        remainingspeed.y -= movingspeed.y;
                                    } else {
                                        movingspeed.y *= -1;
                                        remainingspeed.y *= -1;
                                        this.location.y += movingspeed.y;
                                        remainingspeed.y -= movingspeed.y;
                                    }
                                }
                                try {
                                    this.playingfield.addGameObject(this);
                                } catch (ImpossibleRelocationException ex) {
                                    this.location = originallocation;
                                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                do {
                                    this.location.x += movingspeed.x;
                                    this.location.y += movingspeed.y;
                                    remainingspeed.x -= movingspeed.x;
                                    remainingspeed.y -= movingspeed.y;
                                    if (this.playingfield.canRelocateGameObject(this, this.location)) {

                                    } else {
                                        movingspeed.x *= -1;
                                        remainingspeed.x *= -1;
                                        movingspeed.y *= -1;
                                        remainingspeed.y *= -1;
                                        this.location.x += movingspeed.x;
                                        this.location.y += movingspeed.y;
                                    }
                                } while (remainingspeed.x != 0 || remainingspeed.x != 0);
                                try {
                                    this.playingfield.addGameObject(this);
                                } catch (ImpossibleRelocationException ex) {
                                    this.location = originallocation;
                                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                    }
            }
        }
    }

    /**
     * Respawns the object
     */
    public void respawn() {
        Coordinate originallocation = this.location;
        try {
            this.playingfield.deleteGameObject(this);
        } catch (ImpossibleLocationRemoveException ex) {
            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        RectangularArea area = this.respawnarea.getCommonArea(this.posiblelocationarea).getCommonArea(new RectangularArea(0, this.playingfield.sizex - 1, 0, this.playingfield.sizey - 1));
        do {
        } while (!this.playingfield.canRelocateGameObject(this, this.location = new Coordinate(MathCustomFuncs.random(area.mincoord.x, area.maxcoord.x).intValue(), MathCustomFuncs.random(area.mincoord.y, area.maxcoord.y).intValue())));
        try {
            this.playingfield.addGameObject(this);
        } catch (ImpossibleRelocationException ex) {
            this.location = originallocation;
            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Checks if it will respawn when it moves
     *
     * @return A boolean containing the result
     */
    public Boolean willRespawn() {
        Coordinate destinylocation = new Coordinate(this.location.x, this.location.y);
        switch (this.movetype) {
            case None:
                return false;
            default:
                RectangularArea possiblearea = new RectangularArea(this.playingfield.sizex - 1, 0, this.playingfield.sizey - 1, 0).getCommonArea(this.posiblelocationarea);
                if ((destinylocation.x += this.speed.x) > possiblearea.maxcoord.x || destinylocation.x < possiblearea.mincoord.x || (destinylocation.y += this.speed.y) > possiblearea.maxcoord.y || destinylocation.y < possiblearea.mincoord.y) {
                    return true;
                }
                return false;
        }
    }

    /**
     * Checks if and updateRespawnableLocation can be done
     *
     * @return A boolean containing the result
     */
    public Boolean canUpdateRespawnableLocation() {
        if (this.willRespawn()) {
            RectangularArea area = this.respawnarea.getCommonArea(this.posiblelocationarea).getCommonArea(new RectangularArea(0, this.playingfield.sizex - 1, 0, this.playingfield.sizey - 1));
            Boolean canrelocate = false;
            for (Integer i = 0; i < 200 || canrelocate; i++) {
                canrelocate = this.playingfield.canRelocateGameObject(this, new Coordinate(MathCustomFuncs.random(area.mincoord.x, area.maxcoord.x).intValue(), MathCustomFuncs.random(area.mincoord.y, area.maxcoord.y).intValue()));
            }
            if (canrelocate) {
                return true;
            }
        }
        return false;
    }

    public void updateRespawnableLocation() {
        Coordinate originallocation = this.location;
        try {
            this.playingfield.deleteGameObject(this);
        } catch (ImpossibleLocationRemoveException ex) {
            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.willRespawn()) {
            RectangularArea area = this.respawnarea.getCommonArea(this.posiblelocationarea).getCommonArea(new RectangularArea(0, this.playingfield.sizex - 1, 0, this.playingfield.sizey - 1));
            do {
            } while (!this.playingfield.canRelocateGameObject(this, this.location = new Coordinate(MathCustomFuncs.random(area.mincoord.x, area.maxcoord.x).intValue(), MathCustomFuncs.random(area.mincoord.y, area.maxcoord.y).intValue())));
        }
        try {
            this.playingfield.addGameObject(this);
        } catch (ImpossibleRelocationException ex) {
            this.location = originallocation;
            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * public Boolean CanUpdateimpossibleLocation() { Coordinate destinylocation
 = new Coordinate(this.location.x, this.location.y); Rectangular_area
 possiblearea = new Rectangular_area(this.playingfield.sizex - 1, 0,
 this.playingfield.sizey - 1, 0).getCommonArea(this.posiblelocationarea);
 switch (this.movetype) { case Teleport: if ((destinylocation.x =
 destinylocation.x + this.speed.x) > possiblearea.maxcoord.x) {
 destinylocation.x = possiblearea.maxcoord.x; } else if
 (destinylocation.x < possiblearea.mincoord.x) {
 destinylocation.x = possiblearea.mincoord.x;
 }
 if ((destinylocation.y = destinylocation.y + this.speed.y) >
 possiblearea.maxcoord.y) { destinylocation.y = possiblearea.maxcoord.y;
 } else if (destinylocation.y < possiblearea.mincoord.y) {
 destinylocation.y = possiblearea.mincoord.y;
 }
 return this.playingfield.canRelocateGameObject(this, destinylocation);
 case None:
 return this.playingfield.canRelocateGameObject(this, destinylocation);
 default:
 Speed movingspeed = new Speed(new Float(Math.signum(this.speed.x)).intValue(), new Float(Math.signum(this.speed.x)).intValue());
 Speed remainingspeed = new Speed(this.speed.x, this.speed.y);
 Boolean canrelocate = true;
 switch (this.movetype) {
 case Horizontal_first:
 for (; remainingspeed.x != 0 && (canrelocate = this.playingfield.canRelocateGameObject(this, destinylocation)); destinylocation.x += movingspeed.x, remainingspeed.x -= movingspeed.x) {
     * }
     * destinylocation.x = (canrelocate) ? destinylocation.x : (this.speed.x
     * > 0) ? possiblearea.maxcoord.x : possiblearea.mincoord.x; for (;
 remainingspeed.y != 0 && (canrelocate =
     * this.playingfield.canRelocateGameObject(this, destinylocation));
     * destinylocation.y += movingspeed.y, remainingspeed.y -= movingspeed.y) {
     * } destinylocation.y = (canrelocate) ? destinylocation.y : (this.speed.y >
 0) ? possiblearea.maxcoord.y : possiblearea.mincoord.y; return
 this.playingfield.canRelocateGameObject(this, destinylocation); case
 Vertical_first: for (; remainingspeed.y != 0 && (canrelocate =
     * this.playingfield.canRelocateGameObject(this, destinylocation));
     * destinylocation.y += movingspeed.y, remainingspeed.y -= movingspeed.y) {
     * } destinylocation.y = (canrelocate) ? destinylocation.y : (this.speed.y >
 0) ? possiblearea.maxcoord.y : possiblearea.mincoord.y; for (;
 remainingspeed.x != 0 && (canrelocate =
     * this.playingfield.canRelocateGameObject(this, destinylocation));
     * destinylocation.x += movingspeed.x, remainingspeed.x -= movingspeed.x) {
     * } destinylocation.x = (canrelocate) ? destinylocation.x : (this.speed.x >
 0) ? possiblearea.maxcoord.x : possiblearea.mincoord.x; return
 this.playingfield.canRelocateGameObject(this, destinylocation); case
 Diagonal: if (this.speed.x != 0 && this.speed.y != 0 &&
     * Math.abs(this.speed.x) >= Math.abs(this.speed.y)) { Integer times =
     * Math.abs(this.speed.x / this.speed.y); for (; remainingspeed.y != 0;
     * destinylocation.y += movingspeed.y) { if
     * (this.playingfield.canRelocateGameObject(this, destinylocation)) {
     * remainingspeed.y -= movingspeed.y; } else { movingspeed.y *= -1;
     * remainingspeed.y *= -1; destinylocation.y += movingspeed.y;
     * remainingspeed.y -= movingspeed.y; } for (Integer i = 0; i < times; i++, destinylocation.x += movingspeed.x) {
     * if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
     * remainingspeed.x -= movingspeed.x;
     * } else {
     * movingspeed.x *= -1;
     * remainingspeed.x *= -1;
     * destinylocation.x += movingspeed.x;
     * remainingspeed.x -= movingspeed.x;
     * }
     * }
     * }
     * for (; remainingspeed.x != 0; destinylocation.x += movingspeed.x) {
     * if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
     * remainingspeed.x -= movingspeed.x;
     * } else {
     * movingspeed.x *= -1;
     * remainingspeed.x *= -1;
     * destinylocation.x += movingspeed.x;
     * remainingspeed.x -= movingspeed.x;
     * }
     * }
     * return this.playingfield.canRelocateGameObject(this, destinylocation);
     * } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y)
     * >= Math.abs(this.speed.x)) { Integer times = Math.abs(this.speed.y /
     * this.speed.x); for (; remainingspeed.x != 0; destinylocation.x +=
     * movingspeed.x) { if (this.playingfield.canRelocateGameObject(this,
     * destinylocation)) { remainingspeed.x -= movingspeed.x; } else {
     * movingspeed.x *= -1; remainingspeed.x *= -1; destinylocation.x +=
     * movingspeed.x; remainingspeed.x -= movingspeed.x; } for (Integer i = 0; i < times; i++, destinylocation.x += movingspeed.y) {
     * if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
     * remainingspeed.y -= movingspeed.y;
     * } else {
     * movingspeed.y *= -1;
     * remainingspeed.y *= -1;
     * destinylocation.y += movingspeed.y;
     * remainingspeed.y -= movingspeed.y;
     * }
     * }
     * }
     * for (; remainingspeed.y != 0; destinylocation.y += movingspeed.y) {
     * if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
     * remainingspeed.y -= movingspeed.y;
     * } else {
     * movingspeed.y *= -1;
     * remainingspeed.y *= -1;
     * destinylocation.y += movingspeed.y;
     * remainingspeed.y -= movingspeed.y;
     * }
     * }
     * return this.playingfield.canRelocateGameObject(this, destinylocation);
     * } else {
     * do {
     * destinylocation.x += movingspeed.x;
     * destinylocation.y += movingspeed.y;
     * remainingspeed.x -= movingspeed.x;
     * remainingspeed.y -= movingspeed.y;
     * if (this.playingfield.canRelocateGameObject(this, destinylocation)) {
     *
     * } else {
     * movingspeed.x *= -1;
     * remainingspeed.x *= -1;
     * movingspeed.y *= -1;
     * remainingspeed.y *= -1;
     * destinylocation.x += movingspeed.x;
     * destinylocation.y += movingspeed.y;
     * }
     * } while (remainingspeed.x != 0 || remainingspeed.x != 0);
     * return this.playingfield.canRelocateGameObject(this, destinylocation);
     * }
     * }
     * }
     * return false;
     * }
     *
     * public void UpdateimpossibleLocation() {
     * UpdateimpossiblexLocation();
     * UpdateimpossibleyLocation();
     * }
     *
     * public void UpdateimpossiblexLocation() {
     * this.location.x += (this.location.x + this.speed.x >
     * this.posiblelocationarea.maxcoord.x || this.location.x + this.speed.x < this.posiblelocationarea.mincoord.x) ? 0 : this.speed.x;
     * }
     *
     * public void UpdateimpossibleyLocation() {
     * this.location.y += (this.location.y + this.speed.y >
     * this.posiblelocationarea.maxcoord.y || this.location.y + this.speed.y <
     * this.posiblelocationarea.mincoord.y) ? 0 : this.speed.y; }
     *
     * /**
     * Checks if and UpdatedestroyableLocation can be done
     *
     * @return A boolean containing the result public Boolean
     * CanUpdatedestroyableLocation() { Coordinate destinylocation = new
     * Coordinate(this.location.x + this.speed.x, this.location.y +
     * this.speed.y); Speed movingspeed; switch (this.movetype) { case Teleport:
     * return this.playingfield.canRelocateGameObject(this, destinylocation);
     * case Horizontal_first: movingspeed = new Speed((destinylocation.x >
     * this.location.x) ? 1 : -1, (destinylocation.y > this.location.y) ? 1 :
     * -1); for (; !Objects.equals(this.location.x, destinylocation.x);
     * this.location.x += movingspeed.x) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } for (; !Objects.equals(this.location.y, destinylocation.y);
     * this.location.y += movingspeed.y) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } return true; case Vertical_first: movingspeed = new
     * Speed((destinylocation.x > this.location.x) ? 1 : -1, (destinylocation.y
     * > this.location.y) ? 1 : -1); for (; !Objects.equals(this.location.y,
     * destinylocation.y); this.location.y += movingspeed.y) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } for (; !Objects.equals(this.location.x, destinylocation.x);
     * this.location.x += movingspeed.x) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } return true; case Diagonal: if (this.speed.x != 0 &&
     * this.speed.y != 0 && Math.abs(this.speed.x) >= Math.abs(this.speed.y)) {
     * Integer times = Math.abs(this.speed.x / this.speed.y); Integer
     * x_direction = (this.speed.x < 0) ? -1 : 1; Integer y_direction =
     * (this.speed.y < 0) ? -1 : 1; for (; !Objects.equals(this.location.y,
     * destinylocation.y); this.location.y += y_direction) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } for (Integer i = 0; i < times; i++, this.location.x += x_direction) {
     * if (!this.playingfield.canRelocateGameObject(this, this.location)) {
     * return false;
     * }
     * }
     * }
     * for (; !Objects.equals(this.location.x, destinylocation.x); this.location.x += x_direction) {
     * if (!this.playingfield.canRelocateGameObject(this, this.location)) {
     * return false;
     * }
     * }
     * } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y)
     * >= Math.abs(this.speed.x)) { Integer times = Math.abs(this.speed.y /
     * this.speed.x); Integer y_direction = (this.speed.y < 0) ? -1 : 1; Integer
     * x_direction = (this.speed.x < 0) ? -1 : 1; for (;
     * !Objects.equals(this.location.x, destinylocation.x); this.location.x +=
     * x_direction) { if (!this.playingfield.canRelocateGameObject(this,
     * this.location)) { return false; } for (Integer i = 0; i < times; i++,
     * this.location.x += y_direction) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } } for (; !Objects.equals(this.location.y, destinylocation.y);
     * this.location.y += y_direction) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } } else { Integer x_direction = (this.speed.x < 0) ? -1 : 1;
     * for (Integer i = 0; i < this.speed.x; i++, this.location.x +=
     * x_direction) { if (!this.playingfield.canRelocateGameObject(this,
     * this.location)) { return false; } } Integer y_direction = (this.speed.y <
     * 0) ? -1 : 1; for (Integer i = 0; i < this.speed.y; i++, this.location.y += y_direction) {
     * if (!this.playingfield.canRelocateGameObject(this, this.location)) {
     * return false;
     * }
     * }
     * }
     * case None:
     * return false;
     * }
     * return false;
     * }
     *
     * public void UpdatedestroyableLocation() throws IOException {
     * UpdatedestroyablexLocation();
     * UpdatedestroyableyLocation();
     * }
     *
     * public Boolean CanUpdatedestroyablexLocation() {
     * Integer resultcoord;
     * return !((resultcoord = this.location.x + this.speed.x) >
     * this.posiblelocationarea.maxcoord.x || resultcoord < this.posiblelocationarea.mincoord.x);
     * }
     *
     * public void UpdatedestroyablexLocation() throws IOException {
     * if (this.location.x + this.speed.x >
     * this.posiblelocationarea.maxcoord.x || this.location.x + this.speed.x < this.posiblelocationarea.mincoord.x) {
     * this.close();
     * } else {
     * this.location.x += this.speed.x;
     * }
     * }
     *
     * public Boolean CanUpdatedestroyableyLocation() {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + this.speed.y) >
     * this.posiblelocationarea.maxcoord.y || resultcoord < this.posiblelocationarea.mincoord.y);
     * }
     *
     * public void UpdatedestroyableyLocation() throws IOException {
     * if (this.location.y + this.speed.y >
     * this.posiblelocationarea.maxcoord.y || this.location.y + this.speed.y <
     * this.posiblelocationarea.mincoord.y) { this.close(); } else {
     * this.location.y += this.speed.y; } }
     *
     * /**
     * Checks if and UpdatepossibleLocation can be done
     *
     * @return A boolean containing the result public Boolean
     * CanUpdatepossibleLocation() { return true; }
     *
     * public void UpdatepossibleLocation() { UpdatepossiblexLocation();
     * UpdatepossibleyLocation(); }
     *
     * public void UpdatepossiblexLocation() { this.location.x += this.speed.x;
     * }
     *
     * public void UpdatepossibleyLocation() { this.location.y += this.speed.y;
     * }
     *
     * /**
     * Checks if it will be at farest location when it moves
     *
     * @return A boolean containing the result public Boolean WillFarest() {
     * Coordinate resultcoord = new Coordinate(this.location.x + this.speed.x,
     * this.location.y + this.speed.y); return resultcoord.y >
     * this.posiblelocationarea.maxcoord.y || resultcoord.y <
     * this.posiblelocationarea.mincoord.y || resultcoord.y < 0 || resultcoord.y
     * >= this.playingfield.sizey || resultcoord.x >
     * this.posiblelocationarea.maxcoord.x || resultcoord.x <
     * this.posiblelocationarea.mincoord.x || resultcoord.x < 0 || resultcoord.x
     * >= this.playingfield.sizex; }
     *
     * /**
     * Checks if and UpdaterespawneableLocation can be done
     *
     * @return A boolean containing the result public Boolean
     * CanUpdatefarestLocation() { Coordinate destinylocation = new
     * Coordinate(this.location.x + this.speed.x, this.location.y +
     * this.speed.y); Speed movingspeed; switch (this.movetype) { case Teleport:
     * return this.playingfield.canRelocateGameObject(this, destinylocation);
     * case Horizontal_first: movingspeed = new Speed((destinylocation.x >
     * this.location.x) ? 1 : -1, (destinylocation.y > this.location.y) ? 1 :
     * -1); for (; !Objects.equals(this.location.x, destinylocation.x);
     * this.location.x += movingspeed.x) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } for (; !Objects.equals(this.location.y, destinylocation.y);
     * this.location.y += movingspeed.y) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } return true; case Vertical_first: movingspeed = new
     * Speed((destinylocation.x > this.location.x) ? 1 : -1, (destinylocation.y
     * > this.location.y) ? 1 : -1); for (; !Objects.equals(this.location.y,
     * destinylocation.y); this.location.y += movingspeed.y) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } for (; !Objects.equals(this.location.x, destinylocation.x);
     * this.location.x += movingspeed.x) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } return true; case Diagonal: if (this.speed.x != 0 &&
     * this.speed.y != 0 && Math.abs(this.speed.x) >= Math.abs(this.speed.y)) {
     * Integer times = Math.abs(this.speed.x / this.speed.y); Integer
     * x_direction = (this.speed.x < 0) ? -1 : 1; Integer y_direction =
     * (this.speed.y < 0) ? -1 : 1; for (; !Objects.equals(this.location.y,
     * destinylocation.y); this.location.y += y_direction) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } for (Integer i = 0; i < times; i++, this.location.x += x_direction) {
     * if (!this.playingfield.canRelocateGameObject(this, this.location)) {
     * return false;
     * }
     * }
     * }
     * for (; !Objects.equals(this.location.x, destinylocation.x); this.location.x += x_direction) {
     * if (!this.playingfield.canRelocateGameObject(this, this.location)) {
     * return false;
     * }
     * }
     * } else if (this.speed.x != 0 && this.speed.y != 0 && Math.abs(this.speed.y)
     * >= Math.abs(this.speed.x)) { Integer times = Math.abs(this.speed.y /
     * this.speed.x); Integer y_direction = (this.speed.y < 0) ? -1 : 1; Integer
     * x_direction = (this.speed.x < 0) ? -1 : 1; for (;
     * !Objects.equals(this.location.x, destinylocation.x); this.location.x +=
     * x_direction) { if (!this.playingfield.canRelocateGameObject(this,
     * this.location)) { return false; } for (Integer i = 0; i < times; i++,
     * this.location.x += y_direction) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } } for (; !Objects.equals(this.location.y, destinylocation.y);
     * this.location.y += y_direction) { if
     * (!this.playingfield.canRelocateGameObject(this, this.location)) { return
     * false; } } } else { Integer x_direction = (this.speed.x < 0) ? -1 : 1;
     * for (Integer i = 0; i < this.speed.x; i++, this.location.x +=
     * x_direction) { if (!this.playingfield.canRelocateGameObject(this,
     * this.location)) { return false; } } Integer y_direction = (this.speed.y <
     * 0) ? -1 : 1; for (Integer i = 0; i < this.speed.y; i++, this.location.y += y_direction) {
     * if (!this.playingfield.canRelocateGameObject(this, this.location)) {
     * return false;
     * }
     * }
     * }
     * case None:
     * return false;
     * }
     * return false;
     * }
     *
     * public void UpdatefarestLocation() {
     * UpdatefarestxLocation();
     * UpdatefarestyLocation();
     * }
     *
     * public void UpdatefarestxLocation() {
     * if (this.location.x + this.speed.x >
     * this.posiblelocationarea.maxcoord.x) { this.location.x =
     * this.posiblelocationarea.maxcoord.x; } else if (this.location.x +
     * this.speed.x < this.posiblelocationarea.mincoord.x) {
     * this.location.x = this.posiblelocationarea.mincoord.x;
     * } else {
     * this.location.x += this.speed.x;
     * }
     * }
     *
     * public void UpdatefarestyLocation() {
     * if (this.location.y + this.speed.y >
     * this.posiblelocationarea.maxcoord.y) { this.location.y =
     * this.posiblelocationarea.maxcoord.y; } else if (this.location.y +
     * this.speed.y < this.posiblelocationarea.mincoord.y) { this.location.y =
     * this.posiblelocationarea.mincoord.y; } else { this.location.y +=
     * this.speed.y; } }
     *
     * /**
     * Checks if it will make use of the circular universe when it moves
     *
     * @return A boolean containing the result public Boolean
     * WillCircularUniverse() { Coordinate resultcoord = new
     * Coordinate(this.location.x + this.speed.x, this.location.y +
     * this.speed.y); return resultcoord.y > this.posiblelocationarea.maxcoord.y
     * || resultcoord.y < this.posiblelocationarea.mincoord.y || resultcoord.y < 0 || resultcoord.y
     * >= this.playingfield.sizey || resultcoord.x >
     * this.posiblelocationarea.maxcoord.x || resultcoord.x <
     * this.posiblelocationarea.mincoord.x || resultcoord.x < 0 || resultcoord.x
     * >= this.playingfield.sizex; }
     *
     * /**
     * Checks if and UpdatecircularuniverseLocation can be done
     *
     * @return A boolean containing the result public Boolean
     * CanUpdatecircularuniverseLocation() { return true; }
     *
     * public void UpdatecircularuniverseLocation() {
     * UpdatecircularuniversexLocation(); UpdatecircularuniverseyLocation(); }
     *
     * public void UpdatecircularuniversexLocation() { if (this.location.x +
     * this.speed.x > this.posiblelocationarea.maxcoord.x) { this.location.x =
     * this.posiblelocationarea.mincoord.x + (this.speed.x -
     * (this.posiblelocationarea.maxcoord.x - this.location.x)); } else if
     * (this.location.x + this.speed.x < this.posiblelocationarea.mincoord.x) {
     * this.location.x = this.posiblelocationarea.maxcoord.x + (this.speed.x + this.location.x);
     * } else {
     * this.location.x += this.speed.x;
     * }
     * }
     *
     * public void UpdatecircularuniverseyLocation() {
     * if (this.location.y + this.speed.y >
     * this.posiblelocationarea.maxcoord.y) { this.location.y =
     * this.posiblelocationarea.mincoord.y + (this.speed.y -
     * (this.posiblelocationarea.maxcoord.y - this.location.y)); } else if
     * (this.location.x + this.speed.x < this.posiblelocationarea.mincoord.x) {
     * this.location.y = this.posiblelocationarea.maxcoord.y + (this.speed.y + this.location.y);
     * } else {
     * this.location.y += this.speed.y;
     * }
     * }
     * public Boolean[] canUpdateLocation(Integer x, Integer y) {
     * switch (this.outofboundsmovetype) {
     * case Bounceable:
     * return CanUpdatecircularuniverseLocation(x, y);
     * case Respawnable:
     * return canUpdateRespawnableLocation(x, y);
     * case Impossible:
     * return CanUpdateimpossibleLocation(x, y);
     * case Destroyable:
     * return CanUpdatedestroyableLocation(x, y);
     * case Possible:
     * return CanUpdatepossibleLocation(x, y);
     * case Farest:
     * return CanUpdatefarestLocation(x, y);
     * case Circular_universe:
     * return CanUpdatecircularuniverseLocation(x, y);
     * }
     * Boolean[] no_out_of_bounds_move_type = {false, false};
     * return no_out_of_bounds_move_type;
     * }
     * public void updateLocation(Integer x, Integer y) {
     * if (x != 0 || y != 0) {
     * switch (this.outofboundsmovetype) {
     * case Bounceable:
     * updateBounceableLocation(x, y);
     * break;
     * case Respawnable:
     * updateRespawnableLocation(x, y);
     * break;
     * case Impossible:
     * UpdateimpossibleLocation(x, y);
     * break;
     * case Destroyable:
     * try {
     * UpdatedestroyableLocation(x, y);
     * } catch (IOException ex) {
     * Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
     * }
     * break;
     * case Possible:
     * UpdatepossibleLocation(x, y);
     * break;
     * case Farest:
     * UpdatefarestLocation(x, y);
     * break;
     * case Circular_universe:
     * UpdatecircularuniverseLocation(x, y);
     * break;
     * }
     * }
     * }
     * public Boolean[] canUpdateBounceableLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdatebounceablexLocation(x, y), CanUpdatebounceableyLocation(x, y)};
     * return result;
     * }
     * public void updateBounceableLocation(Integer x, Integer y) {
     * UpdatebounceablexLocation(x, y);
     * UpdatebounceableyLocation(x, y);
     * }
     * public Boolean CanUpdatebounceablexLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.x + x) >
     * this.posiblelocationarea.maxcoord.x || resultcoord < this.posiblelocationarea.mincoord.x);
     * }
     *
     * public void UpdatebounceablexLocation(Integer x, Integer y) {
     * if (this.location.x + x > this.posiblelocationarea.maxcoord.x) {
     * this.location.x -= (x - (this.posiblelocationarea.maxcoord.x -
     * this.location.x)); } else if (this.location.x + x < this.posiblelocationarea.mincoord.y) {
     * this.location.x -= (x + this.location.x);
     * } else {
     * this.location.x += x;
     * }
     * }
     *
     * public Boolean CanUpdatebounceableyLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + y) >
     * this.posiblelocationarea.maxcoord.y || resultcoord < this.posiblelocationarea.mincoord.y);
     * }
     *
     * public void UpdatebounceableyLocation(Integer x, Integer y) {
     * if (this.location.y + y > this.posiblelocationarea.maxcoord.y) {
     * this.location.y -= (y - (this.posiblelocationarea.maxcoord.y -
     * this.location.y)); } else if (this.location.y + y < this.posiblelocationarea.mincoord.y) {
     * this.location.y -= (y + this.location.y);
     * } else {
     * this.location.y += y;
     * }
     * }
     *
     * public Boolean[] canUpdateRespawnableLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdaterespawnablexLocation(x, y), CanUpdaterespawnableyLocation(x, y)};
     * return result;
     * }
     *
     * public void updateRespawnableLocation(Integer x, Integer y) {
     * UpdaterespawnablexLocation(x, y);
     * UpdaterespawnableyLocation(x, y);
     * }
     *
     * public Boolean CanUpdaterespawnablexLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.x + x) >
     * this.posiblelocationarea.maxcoord.x || resultcoord < this.posiblelocationarea.mincoord.x);
     * }
     *
     * public void UpdaterespawnablexLocation(Integer x, Integer y) {
     * if (this.location.x + x > this.posiblelocationarea.maxcoord.x ||
     * this.location.x + x < this.posiblelocationarea.mincoord.x) {
     * this.location.x = MathCustomFuncs.random(this.respawnarea.mincoord.x, this.respawnarea.maxcoord.x).intValue();
     * } else {
     * this.location.x += x;
     * }
     * }
     *
     * public Boolean CanUpdaterespawnableyLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + y) >
     * this.posiblelocationarea.maxcoord.y || resultcoord < this.posiblelocationarea.mincoord.y);
     * }
     *
     * public void UpdaterespawnableyLocation(Integer x, Integer y) {
     * if (this.location.y + y > this.posiblelocationarea.maxcoord.y ||
     * this.location.y + y < this.posiblelocationarea.mincoord.y) {
     * this.location.y = MathCustomFuncs.random(this.respawnarea.mincoord.y, this.respawnarea.maxcoord.y).intValue();
     * } else {
     * this.location.y += y;
     * }
     * }
     *
     * public Boolean[] CanUpdateimpossibleLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdateimpossiblexLocation(x, y), CanUpdateimpossibleyLocation(x, y)};
     * return result;
     * }
     *
     * public void UpdateimpossibleLocation(Integer x, Integer y) {
     * UpdateimpossiblexLocation(x, y);
     * UpdateimpossibleyLocation(x, y);
     * }
     *
     * public Boolean CanUpdateimpossiblexLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.x + x) >
     * this.posiblelocationarea.maxcoord.x || resultcoord < this.posiblelocationarea.mincoord.x);
     * }
     *
     * public void UpdateimpossiblexLocation(Integer x, Integer y) {
     * this.location.x += (this.location.x + x >
     * this.posiblelocationarea.maxcoord.x || this.location.x + x < this.posiblelocationarea.mincoord.x) ? 0 : x;
     * }
     *
     * public Boolean CanUpdateimpossibleyLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + y) >
     * this.posiblelocationarea.maxcoord.y || resultcoord < this.posiblelocationarea.mincoord.y);
     * }
     *
     * public void UpdateimpossibleyLocation(Integer x, Integer y) {
     * this.location.y += (this.location.y + y >
     * this.posiblelocationarea.maxcoord.y || this.location.y + y < this.posiblelocationarea.mincoord.y) ? 0 : y;
     * }
     *
     * public Boolean[] CanUpdatedestroyableLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdatedestroyablexLocation(x, y), CanUpdatedestroyableyLocation(x, y)};
     * return result;
     * }
     *
     * public void UpdatedestroyableLocation(Integer x, Integer y) throws IOException {
     * UpdatedestroyablexLocation(x, y);
     * UpdatedestroyableyLocation(x, y);
     * }
     *
     * public Boolean CanUpdatedestroyablexLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.x + x) >
     * this.posiblelocationarea.maxcoord.x || resultcoord < this.posiblelocationarea.mincoord.x);
     * }
     *
     * public void UpdatedestroyablexLocation(Integer x, Integer y) throws IOException {
     * if (this.location.x + x > this.posiblelocationarea.maxcoord.x ||
     * this.location.x + x < this.posiblelocationarea.mincoord.x) {
     * this.close();
     * } else {
     * this.location.x += x;
     * }
     * }
     *
     * public Boolean CanUpdatedestroyableyLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + y) >
     * this.posiblelocationarea.maxcoord.y || resultcoord < this.posiblelocationarea.mincoord.y);
     * }
     *
     * public void UpdatedestroyableyLocation(Integer x, Integer y) throws IOException {
     * if (this.location.y + y > this.posiblelocationarea.maxcoord.y ||
     * this.location.y + y < this.posiblelocationarea.mincoord.y) {
     * this.close();
     * } else {
     * this.location.y += y;
     * }
     * }
     *
     * public Boolean[] CanUpdatepossibleLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdatepossiblexLocation(x, y), CanUpdatepossibleyLocation(x, y)};
     * return result;
     * }
     *
     * public void UpdatepossibleLocation(Integer x, Integer y) {
     * UpdatepossiblexLocation(x, y);
     * UpdatepossibleyLocation(x, y);
     * }
     *
     * public Boolean CanUpdatepossiblexLocation(Integer x, Integer y) {
     * return true;
     * }
     *
     * public void UpdatepossiblexLocation(Integer x, Integer y) {
     * this.location.x += x;
     * }
     *
     * public Boolean CanUpdatepossibleyLocation(Integer x, Integer y) {
     * return true;
     * }
     *
     * public void UpdatepossibleyLocation(Integer x, Integer y) {
     * this.location.y += y;
     * }
     *
     * public Boolean[] CanUpdatefarestLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdatefarestxLocation(x, y), CanUpdatefarestyLocation(x, y)};
     * return result;
     * }
     *
     * public void UpdatefarestLocation(Integer x, Integer y) {
     * UpdatefarestxLocation(x, y);
     * UpdatefarestyLocation(x, y);
     * }
     *
     * public Boolean CanUpdatefarestxLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.x + x) >
     * this.posiblelocationarea.maxcoord.x || resultcoord < this.posiblelocationarea.mincoord.x);
     * }
     *
     * public void UpdatefarestxLocation(Integer x, Integer y) {
     * if (this.location.x + x > this.posiblelocationarea.maxcoord.x) {
     * this.location.x = this.posiblelocationarea.maxcoord.x; } else if
     * (this.location.x + x < this.posiblelocationarea.mincoord.x) {
     * this.location.x = this.posiblelocationarea.mincoord.x;
     * } else {
     * this.location.x += x;
     * }
     * }
     *
     * public Boolean CanUpdatefarestyLocation(Integer x, Integer y) {
     * Integer resultcoord;
     * return !((resultcoord = this.location.y + y) >
     * this.posiblelocationarea.maxcoord.y || resultcoord < this.posiblelocationarea.mincoord.y);
     * }
     *
     * public void UpdatefarestyLocation(Integer x, Integer y) {
     * if (this.location.y + y > this.posiblelocationarea.maxcoord.y) {
     * this.location.y = this.posiblelocationarea.maxcoord.y; } else if
     * (this.location.y + y < this.posiblelocationarea.mincoord.y) {
     * this.location.y = this.posiblelocationarea.mincoord.y;
     * } else {
     * this.location.y += y;
     * }
     * }
     *
     * public Boolean[] CanUpdatecircularuniverseLocation(Integer x, Integer y) {
     * Boolean[] result = {CanUpdatecircularuniversexLocation(x, y), CanUpdatecircularuniverseyLocation(x, y)};
     * return result;
     * }
     *
     * public void UpdatecircularuniverseLocation(Integer x, Integer y) {
     * UpdatecircularuniversexLocation(x, y);
     * UpdatecircularuniverseyLocation(x, y);
     * }
     *
     * public Boolean CanUpdatecircularuniversexLocation(Integer x, Integer y) {
     * return true;
     * }
     *
     * public void UpdatecircularuniversexLocation(Integer x, Integer y) {
     * if (this.location.x + x > this.posiblelocationarea.maxcoord.x) {
     * this.location.x = this.posiblelocationarea.mincoord.x + (x -
     * (this.posiblelocationarea.maxcoord.x - this.location.x)); } else if
     * (this.location.x + x < this.posiblelocationarea.mincoord.x) {
     * this.location.x = this.posiblelocationarea.maxcoord.x + (x + this.location.x);
     * } else {
     * this.location.x += x;
     * }
     * }
     *
     * public Boolean CanUpdatecircularuniverseyLocation(Integer x, Integer y) {
     * return true;
     * }
     *
     * public void UpdatecircularuniverseyLocation(Integer x, Integer y) {
     * if (this.location.y + y > this.posiblelocationarea.maxcoord.y) {
     * this.location.y = this.posiblelocationarea.mincoord.y + (y -
     * (this.posiblelocationarea.maxcoord.y - this.location.y)); } else if
     * (this.location.x + x < this.posiblelocationarea.mincoord.x) {
     * this.location.y = this.posiblelocationarea.maxcoord.y + (y +
     * this.location.y); } else { this.location.y += y; } }
     *
     */
    @Override
    public void close() throws IOException {
        try {
            this.playingfield.deleteGameObject(this);
        } catch (ImpossibleLocationRemoveException ex) {
            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
