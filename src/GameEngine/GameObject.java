/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

import alvaro_tools.LoggingTools;
import alvaro_tools.MathCustomFuncs;
import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Object to use in the game
 *
 * @author alvaro9650
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
    public String objectidentifier;
    public CollisionType receivingcollision;
    public CollisionType givingcollision;
    public Speed movedirection;
    public Speed movingspeed;
    public Speed remainingspeed;

    /**
     * Creates a basic game object , should be overriden
     *
     * @param field The field where the object is located
     * @author alvaro9650
     */
    public GameObject(Field field) {
        this.character = '|';
        this.height = 0;
        this.objecttype = "Default";
        this.speed = new Speed(0, 0);
        this.movedirection = new Speed(0, 0);
        this.movetype = MoveType.None;
        this.playingfield = field;
        this.givingcollision=CollisionType.Ghost;
        this.receivingcollision=CollisionType.Ghost;
        this.posiblelocationarea = new RectangularArea(field.size.x - 1, 0, field.size.y - 1, 0);
        this.respawnarea = new RectangularArea(field.size.x - 1, 0, field.size.y - 1, 0);
        this.physicalstatetype = PhysicalStateType.Ghost;
        this.outofboundsmovetype = OutOfBoundsMoveType.CircularUniverse;
        this.loglevel = LogLevel.None;
        this.location = new Coordinate(this.posiblelocationarea.mincoord.x, this.posiblelocationarea.mincoord.y);
        this.objectidentifier = Integer.toString(this.hashCode());
    }

    /**
     * Creates a respawn point to use when it's going to be respawned
     *
     * @param x x coordinate
     * @param y y coordinate
     * @author alvaro9650
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
     * @author alvaro9650
     */
    public void setRespawnArea(Integer maxx, Integer minx, Integer maxy, Integer miny) {
        this.respawnarea = new RectangularArea(maxx, minx, maxy, miny);
    }

    /**
     * Logs the data in the object acording to LogLevel
     *
     * @author alvaro9650
     */
    public void log() {
        StringBuilder objectlog = new StringBuilder();
        objectlog.append("Object: ");
        objectlog.append(this.hashCode());
        switch (this.loglevel) {
            case Verbose:
                objectlog.append("\nlog_level = verbose");
            case DrawRelated:
                objectlog.append("\ncharacter = ");
                objectlog.append(this.character);
            case MoveRelated:
                objectlog.append("\nmove_type = ");
                objectlog.append(this.movetype);
            case OutOfBoundsRelated:
                objectlog.append("\nplaying_field x = ");
                objectlog.append(this.playingfield.size.x);
                objectlog.append("\nplaying_field y = ");
                objectlog.append(this.playingfield.size.y);
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
            case CollisionRelated:
                objectlog.append("\ncollision_type = ");
                objectlog.append(this.physicalstatetype);
            case PositionRelated:
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
     * Function to add custom actions when colliding against a determined object
     * or object type
     *
     * @param collisionreceiver The object that receives the collision
     * @author alvaro9650
     */
    public void giveCollision(GameObject collisionreceiver) {
        switch (collisionreceiver.objectidentifier) {
            default:
                break;
        }
        switch (collisionreceiver.objecttype) {
            default:
                break;
        }
    }

    /**
     * Function to add custom actions when collided against a determined object
     * or object type
     *
     * @param collisiongiver The object that gives the collision
     * @author alvaro9650
     */
    public void receiveCollision(GameObject collisiongiver) {
        switch (collisiongiver.objectidentifier) {
            default:
                break;
        }
        switch (collisiongiver.objecttype) {
            default:
                break;
        }
    }

    /**
     * Teleports an object to a location
     *
     * @param coord The coordinate you want to move this object to
     * @throws ImpossibleLocationRemoveException
     * @throws ImpossibleLocationAddException
     * @throws GameEngine.ObjectCollidesException
     * @throws GameEngine.OutOfBoundsException
     * @author alvaro9650
     */
    public void moveTo(Coordinate coord) throws ImpossibleLocationRemoveException, ImpossibleLocationAddException, ObjectCollidesException, OutOfBoundsException {
        this.playingfield.deleteGameObject(this);
        this.location.x = coord.x;
        this.location.y = coord.y;
        this.playingfield.addGameObject(this);
    }

    /**
     * Process object move when it goes out of bounds
     *
     * @param previouslocation The previous location of the object
     * @author alvaro9650
     */
    public void processOutOfBounds(Coordinate previouslocation) {
        switch (this.outofboundsmovetype) {
            case Impossible:
                this.location = previouslocation;
                break;
            case Respawnable:
                this.respawn();
                break;
            case Destroyable:
                try {
                    this.close();
                } catch (IOException ex) {
                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case Possible:
                break;
            default:
                RectangularArea possiblearea = new RectangularArea(this.playingfield.size.x - 1, 0, this.playingfield.size.y - 1, 0).getCommonArea(this.posiblelocationarea);
                switch (this.outofboundsmovetype) {
                    case Farest:
                        if (this.location.x < possiblearea.mincoord.x) {
                            this.location.x = possiblearea.mincoord.x;
                        } else if (this.location.x > possiblearea.maxcoord.x) {
                            this.location.x = possiblearea.maxcoord.x;
                        }
                        if (this.location.y < possiblearea.mincoord.y) {
                            this.location.y = possiblearea.mincoord.y;
                        } else if (this.location.y > possiblearea.maxcoord.y) {
                            this.location.y = possiblearea.maxcoord.y;
                        }
                        break;
                    case CircularUniverse:
                        if (this.location.x < possiblearea.mincoord.x) {
                            this.location.x = possiblearea.maxcoord.x - (possiblearea.mincoord.x - this.location.x);
                        } else if (this.location.x > possiblearea.maxcoord.x) {
                            this.location.x = this.location.x - possiblearea.maxcoord.x + possiblearea.mincoord.x;
                        }
                        if (this.location.y < possiblearea.mincoord.y) {
                            this.location.y = possiblearea.maxcoord.y - (possiblearea.mincoord.y - this.location.y);
                        } else if (this.location.x > possiblearea.maxcoord.y) {
                            this.location.y = this.location.y - possiblearea.maxcoord.y + possiblearea.mincoord.y;
                        }
                        break;
                    case Bounceable:
                        Integer bouncingspace,
                         teoricaldestiny;
                        Boolean maxbounce;
                        this.location.x = (((maxbounce = ((teoricaldestiny = previouslocation.x + this.speed.x) / (bouncingspace = possiblearea.maxcoord.x - possiblearea.mincoord.x) != 0 || teoricaldestiny < possiblearea.mincoord.x) && this.speed.x > 0) ? possiblearea.maxcoord.x : possiblearea.mincoord.x) + Math.abs(teoricaldestiny % bouncingspace) * (maxbounce ? -1 : 1));
                        this.movingspeed.x = new Float(Math.signum(this.speed.x = (teoricaldestiny / bouncingspace % 2 != 0 || teoricaldestiny < possiblearea.mincoord.x ? -this.speed.x : this.speed.x))).intValue();
                        this.location.y = (((maxbounce = ((teoricaldestiny = previouslocation.y + this.speed.y) / (bouncingspace = possiblearea.maxcoord.y - possiblearea.mincoord.y) != 0 || teoricaldestiny < possiblearea.mincoord.y) && this.speed.y > 0) ? possiblearea.maxcoord.y : possiblearea.mincoord.y) + Math.abs(teoricaldestiny % bouncingspace) * (maxbounce ? -1 : 1));
                        this.movingspeed.y = new Float(Math.signum(this.speed.y = (teoricaldestiny / bouncingspace % 2 != 0 || teoricaldestiny < possiblearea.mincoord.y ? -this.speed.y : this.speed.y))).intValue();
                        break;
                }
        }
        try {
            this.playingfield.addGameObject(this);
        } catch (ImpossibleLocationAddException ex) {
            this.location = previouslocation;
            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectCollidesException ex) {
            this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
        } catch (OutOfBoundsException ex) {
            this.processOutOfBounds(this.location);
        }
    }

    /**
     * Stops the object
     *
     * @author alvaro9650
     */
    public void stop() {
        this.speed.stop();
        this.movedirection.stop();
        this.movingspeed.stop();
        this.remainingspeed.stop();
    }

    /**
     * Updates location in the object and the field
     *
     * @author alvaro9650
     */
    public void updateLocation() {
        Coordinate originallocation = new Coordinate(this.location);
        this.movingspeed = new Speed(new Float(Math.signum(this.speed.x)).intValue(), new Float(Math.signum(this.speed.x)).intValue());
        this.remainingspeed = new Speed(this.speed.x, this.speed.y);
        try {
            this.playingfield.deleteGameObject(this);
        } catch (ImpossibleLocationRemoveException ex) {
            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch (this.movetype) {
            case None:
                try {
                    this.playingfield.addGameObject(this);
                } catch (ImpossibleLocationAddException | OutOfBoundsException ex) {
                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ObjectCollidesException ex) {
                    this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                }
                break;
            case Teleport:
                this.location.x += this.speed.x;
                this.location.y += this.speed.y;
                try {
                    this.playingfield.addGameObject(this);
                } catch (ImpossibleLocationAddException ex) {
                    this.location = originallocation;
                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ObjectCollidesException ex) {
                    this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                } catch (OutOfBoundsException ex) {
                    this.processOutOfBounds(originallocation);
                }
                break;
            case HorizontalFirst:
                this.movedirection.x = 1;
                for (; this.remainingspeed.x != 0; this.location.x += this.movingspeed.x, this.remainingspeed.x -= this.movingspeed.x) {
                    try {
                        this.playingfield.addGameObject(this);
                    } catch (ImpossibleLocationAddException ex) {
                        this.movingspeed.y *= -1;
                        this.remainingspeed.y *= -1;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ObjectCollidesException ex) {
                        this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                    } catch (OutOfBoundsException ex) {
                        this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                    }
                }
                this.movedirection.x = 0;
                this.movedirection.y = 1;
                for (; this.remainingspeed.y != 0; this.location.y += this.movingspeed.y, this.remainingspeed.y -= this.movingspeed.y) {
                    try {
                        this.playingfield.addGameObject(this);
                    } catch (ImpossibleLocationAddException ex) {
                        this.movingspeed.y *= -1;
                        this.remainingspeed.y *= -1;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ObjectCollidesException ex) {
                        this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                    } catch (OutOfBoundsException ex) {
                        this.processOutOfBounds(new Coordinate(this.location.x, this.location.y -= this.movingspeed.y));
                    }
                }
                this.movedirection.y = 0;
            case VerticalFirst:
                this.movedirection.y = 1;
                for (; this.remainingspeed.y != 0; this.location.y += this.movingspeed.y, this.remainingspeed.y -= this.movingspeed.y) {
                    try {
                        this.playingfield.addGameObject(this);
                    } catch (ImpossibleLocationAddException ex) {
                        this.movingspeed.y *= -1;
                        this.remainingspeed.y *= -1;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ObjectCollidesException ex) {
                        this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                    } catch (OutOfBoundsException ex) {
                        this.processOutOfBounds(new Coordinate(this.location.x, this.location.y -= this.movingspeed.y));
                    }
                }
                this.movedirection.y = 0;
                this.movedirection.x = 1;
                for (; this.remainingspeed.x != 0; this.location.x += this.movingspeed.x, this.remainingspeed.x -= this.movingspeed.x) {
                    try {
                        this.playingfield.addGameObject(this);
                    } catch (ImpossibleLocationAddException ex) {
                        this.movingspeed.y *= -1;
                        this.remainingspeed.y *= -1;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ObjectCollidesException ex) {
                        this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                    } catch (OutOfBoundsException ex) {
                        this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                    }
                }
                this.movedirection.x = 0;
                try {
                    this.playingfield.addGameObject(this);
                } catch (ImpossibleRelocationException ex) {
                    this.location = originallocation;
                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            case Diagonal:
                if (this.speed.x != 0 && this.speed.y != 0 && this.movingspeed.x >= this.movingspeed.y) {
                    Integer times = Math.abs(this.speed.x / this.speed.y);
                    for (; this.remainingspeed.y != 0; this.location.y += this.movingspeed.y, this.remainingspeed.y -= this.movingspeed.y) {
                        this.movedirection.y = this.movingspeed.y;
                        try {
                            this.playingfield.addGameObject(this);
                        } catch (ImpossibleLocationAddException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ObjectCollidesException ex) {
                            this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                        } catch (OutOfBoundsException ex) {
                            this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                        }
                        this.movedirection.y = 0;
                        this.movedirection.x = this.movingspeed.x;
                        for (Integer i = 0; i < times; i++, this.location.x += this.movingspeed.x, this.remainingspeed.x -= this.movingspeed.x) {
                            try {
                                this.playingfield.addGameObject(this);
                            } catch (ImpossibleLocationAddException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ObjectCollidesException ex) {
                                this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                            } catch (OutOfBoundsException ex) {
                                this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                            }
                        }
                        this.movedirection.x = 0;
                    }
                    this.movedirection.y = 0;
                    for (; this.remainingspeed.x != 0; this.location.x += this.movingspeed.x, this.remainingspeed.x -= this.movingspeed.x) {
                        this.movedirection.x = this.movingspeed.y;
                        try {
                            this.playingfield.addGameObject(this);
                        } catch (ImpossibleLocationAddException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ObjectCollidesException ex) {
                            this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                        } catch (OutOfBoundsException ex) {
                            this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                        }
                    }
                    this.movedirection.x = 0;
                    try {
                        this.playingfield.addGameObject(this);
                    } catch (ImpossibleRelocationException ex) {
                        this.location = originallocation;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (this.speed.x != 0 && this.speed.y != 0 && this.movingspeed.y >= this.movingspeed.x) {
                    Integer times = Math.abs(this.speed.y / this.speed.x);
                    for (; this.remainingspeed.x != 0; this.location.x += this.movingspeed.x, this.remainingspeed.x -= this.movingspeed.x) {
                        this.movedirection.x = this.movingspeed.x;
                        try {
                            this.playingfield.addGameObject(this);
                        } catch (ImpossibleLocationAddException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ObjectCollidesException ex) {
                            this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                        } catch (OutOfBoundsException ex) {
                            this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                        }
                        this.movedirection.x = 0;
                        for (Integer i = 0; i < times; i++, this.location.y += this.movingspeed.y, this.remainingspeed.y -= this.movingspeed.y) {
                            this.movedirection.y = this.movingspeed.y;
                            try {
                                this.playingfield.addGameObject(this);
                            } catch (ImpossibleLocationAddException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ObjectCollidesException ex) {
                                this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                            } catch (OutOfBoundsException ex) {
                                this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                            }
                        }
                        this.movedirection.y = 0;
                    }
                    this.movedirection.x = 0;
                    for (; this.remainingspeed.y != 0; this.location.y += this.movingspeed.y, this.remainingspeed.y -= this.movingspeed.y) {
                        this.movedirection.y = this.movingspeed.y;
                        try {
                            this.playingfield.addGameObject(this);
                        } catch (ImpossibleLocationAddException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ObjectCollidesException ex) {
                            this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                        } catch (OutOfBoundsException ex) {
                            this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                        }
                    }
                    this.movedirection.y = 0;
                    try {
                        this.playingfield.addGameObject(this);
                    } catch (ImpossibleRelocationException ex) {
                        this.location = originallocation;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    for (; this.remainingspeed.y != 0 || this.remainingspeed.x != 0; this.location.x += this.movingspeed.x, this.location.y += this.movingspeed.y, this.remainingspeed.x -= this.movingspeed.x, this.remainingspeed.y -= this.movingspeed.y) {
                        this.movedirection.x = this.movingspeed.x;
                        this.movedirection.y = this.movingspeed.y;
                        try {
                            this.playingfield.addGameObject(this);
                        } catch (ImpossibleLocationAddException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ObjectCollidesException ex) {
                            this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
                        } catch (OutOfBoundsException ex) {
                            this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                        }
                    }
                    this.movedirection.x = 0;
                    this.movedirection.y = 0;
                    try {
                        this.playingfield.addGameObject(this);
                    } catch (ImpossibleRelocationException ex) {
                        this.location = originallocation;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }
    }

    /**
     * Checks if it will bounce when it moves
     *
     * @return A boolean containing the result
     * @author alvaro9650
     */
    public Boolean willBounceAgainstBounds() {
        Coordinate destinylocation = new Coordinate(this.location.x, this.location.y);
        switch (this.movetype) {
            case Teleport:
                RectangularArea possiblebouncearea = new RectangularArea(this.playingfield.size.x - 1, 0, this.playingfield.size.y - 1, 0).getCommonArea(this.posiblelocationarea);
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
                    case HorizontalFirst:
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
                    case VerticalFirst:
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
     * Respawns the object
     *
     * @author alvaro9650
     */
    public void respawn() {
        Coordinate originallocation = this.location;
        try {
            this.playingfield.deleteGameObject(this);
        } catch (ImpossibleLocationRemoveException ex) {
            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        RectangularArea area = this.respawnarea.getCommonArea(this.posiblelocationarea).getCommonArea(new RectangularArea(0, this.playingfield.size.x - 1, 0, this.playingfield.size.y - 1));
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
     * @author alvaro9650
     */
    public Boolean willRespawn() {
        Coordinate destinylocation = new Coordinate(this.location.x, this.location.y);
        switch (this.movetype) {
            case None:
                return false;
            default:
                RectangularArea possiblearea = new RectangularArea(this.playingfield.size.x - 1, 0, this.playingfield.size.y - 1, 0).getCommonArea(this.posiblelocationarea);
                if ((destinylocation.x += this.speed.x) > possiblearea.maxcoord.x || destinylocation.x < possiblearea.mincoord.x || (destinylocation.y += this.speed.y) > possiblearea.maxcoord.y || destinylocation.y < possiblearea.mincoord.y) {
                    return true;
                }
                return false;
        }
    }

    /**
     * Returns a string containing the GameObject information
     *
     * @author alvaro9650
     * @return The string containing the GameObject information
     */
    @Override
    public String toString() {
        return new StringBuilder("GameObject location = , height = , arrayposition = , speed = , playingfield = , posiblelocationarea = , respawnarea = , loglevel = , character = , outofboundsmovetype = , physicalstatetype = , movetype = , objecttype = , objectidentifier = , receivingcollision = , givingcollision = , movedirection = , movingspeed = , remainingspeed = ").insert(336, this.remainingspeed.toString()).insert(317, this.movingspeed.toString()).insert(301, this.movedirection.toString()).insert(283, this.givingcollision).insert(263, this.receivingcollision).insert(240, this.objectidentifier).insert(219, this.objecttype).insert(204, this.movetype).insert(191, this.physicalstatetype).insert(169, this.outofboundsmovetype).insert(145, this.character).insert(131, this.loglevel).insert(118, this.respawnarea.toString()).insert(102, this.posiblelocationarea.toString()).insert(78, this.playingfield.toString()).insert(61, this.speed.toString()).insert(51, this.arrayposition).insert(33, this.height).insert(22, this.location.toString()).toString();
    }

    @Override
    public void close() throws IOException {
        try {
            this.playingfield.deleteGameObject(this);
        } catch (ImpossibleLocationRemoveException ex) {
            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
