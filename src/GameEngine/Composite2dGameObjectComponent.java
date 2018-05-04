/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvaro9650
 */
public class Composite2dGameObjectComponent extends CompositeGameObjectComponent {

    public Composite2dGameObject parent;

    /**
     * Constructor for Composite2dGameObjectComponent
     *
     * @param parent The parent object
     */
    public Composite2dGameObjectComponent(Composite2dGameObject parent) {
        super(parent);
        this.posiblelocationarea = new RectangularArea(new Coordinate(parent.size.x, parent.size.y), new Coordinate(0, 0));
        this.parent = parent;
        this.type = GameObjectType.Composite2dGameObjectComponentType;
    }

    /**
     * Process object move when it goes out of bounds
     *
     * @param previouslocation The previous location of the object
     * @author alvaro9650
     */
    @Override
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
                RectangularArea possiblearea = new RectangularArea(this.parent.size.x - 1, 0, this.parent.size.y - 1, 0).getCommonArea(this.posiblelocationarea);
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
                            this.location.x = this.location.x - possiblearea.maxcoord.x + possiblearea.mincoord.x - 1;
                        }
                        if (this.location.y < possiblearea.mincoord.y) {
                            this.location.y = possiblearea.maxcoord.y - (possiblearea.mincoord.y - this.location.y);
                        } else if (this.location.y > possiblearea.maxcoord.y) {
                            this.location.y = this.location.y - possiblearea.maxcoord.y + possiblearea.mincoord.y - 1;
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
            this.parent.addComposite2dGameObjectComponent(this);
        } catch (ImpossibleLocationAddException ex) {
            this.location = previouslocation;
            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectCollidesException ex) {
            this.playingfield.processCollision(this, this.playingfield.collidesWith(this));
        } catch (OutOfBoundsException ex) {
            this.processOutOfBounds(this.location);
        } catch (ImpossibleLocationRemoveException ex) {
            System.out.println("imposible remove");
        }
    }

    /**
     * Updates location in the Composite2dGameObjectComponent and the parent
     *
     * @author alvaro9650
     */
    @Override
    public void updateLocation() {
        Coordinate originallocation = new Coordinate(this.location);
        this.movingspeed = new Speed(new Float(Math.signum(this.speed.x)).intValue(), new Float(Math.signum(this.speed.x)).intValue());
        this.remainingspeed = new Speed(this.speed.x, this.speed.y);
        try {
            this.parent.deleteGameObject(this);
        } catch (ImpossibleLocationRemoveException ex) {
            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch (this.movetype) {
            case None:
                try {
                    this.parent.addComposite2dGameObjectComponent(this);
                } catch (ImpossibleLocationAddException | OutOfBoundsException ex) {
                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ObjectCollidesException ex) {
                    this.parent.processCollision(this, this.parent.collidesWith(this));
                } catch (ImpossibleLocationRemoveException ex) {
                    System.out.println("imposible remove");
                }
                break;
            case Teleport:
                this.location.x += this.speed.x;
                this.location.y += this.speed.y;
                try {
                    this.parent.addComposite2dGameObjectComponent(this);
                } catch (ImpossibleLocationAddException ex) {
                    this.location = originallocation;
                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ObjectCollidesException ex) {
                    this.parent.processCollision(this, this.parent.collidesWith(this));
                } catch (OutOfBoundsException ex) {
                    this.processOutOfBounds(originallocation);
                } catch (ImpossibleLocationRemoveException ex) {
                    System.out.println("imposible remove");
                }
                break;
            case HorizontalFirst:
                this.movedirection.x = 1;
                for (; this.remainingspeed.x != 0; this.location.x += this.movingspeed.x, this.remainingspeed.x -= this.movingspeed.x) {
                    try {
                        this.parent.addComposite2dGameObjectComponent(this);
                    } catch (ImpossibleLocationAddException ex) {
                        this.movingspeed.y *= -1;
                        this.remainingspeed.y *= -1;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ObjectCollidesException ex) {
                        this.parent.processCollision(this, this.parent.collidesWith(this));
                    } catch (OutOfBoundsException ex) {
                        this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                    } catch (ImpossibleLocationRemoveException ex) {
                        System.out.println("imposible remove");
                    }
                }
                this.movedirection.x = 0;
                this.movedirection.y = 1;
                for (; this.remainingspeed.y != 0; this.location.y += this.movingspeed.y, this.remainingspeed.y -= this.movingspeed.y) {
                    try {
                        this.parent.addComposite2dGameObjectComponent(this);
                    } catch (ImpossibleLocationAddException ex) {
                        this.movingspeed.y *= -1;
                        this.remainingspeed.y *= -1;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ObjectCollidesException ex) {
                        this.parent.processCollision(this, this.parent.collidesWith(this));
                    } catch (OutOfBoundsException ex) {
                        this.processOutOfBounds(new Coordinate(this.location.x, this.location.y -= this.movingspeed.y));
                    } catch (ImpossibleLocationRemoveException ex) {
                        System.out.println("imposible remove");
                    }
                }
                this.movedirection.y = 0;
            case VerticalFirst:
                this.movedirection.y = 1;
                for (; this.remainingspeed.y != 0; this.location.y += this.movingspeed.y, this.remainingspeed.y -= this.movingspeed.y) {
                    try {
                        this.parent.addComposite2dGameObjectComponent(this);
                    } catch (ImpossibleLocationAddException ex) {
                        this.movingspeed.y *= -1;
                        this.remainingspeed.y *= -1;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ObjectCollidesException ex) {
                        this.parent.processCollision(this, this.parent.collidesWith(this));
                    } catch (OutOfBoundsException ex) {
                        this.processOutOfBounds(new Coordinate(this.location.x, this.location.y -= this.movingspeed.y));
                    } catch (ImpossibleLocationRemoveException ex) {
                        System.out.println("imposible remove");
                    }
                }
                this.movedirection.y = 0;
                this.movedirection.x = 1;
                for (; this.remainingspeed.x != 0; this.location.x += this.movingspeed.x, this.remainingspeed.x -= this.movingspeed.x) {
                    try {
                        this.parent.addComposite2dGameObjectComponent(this);
                    } catch (ImpossibleLocationAddException ex) {
                        this.movingspeed.y *= -1;
                        this.remainingspeed.y *= -1;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ObjectCollidesException ex) {
                        this.parent.processCollision(this, this.parent.collidesWith(this));
                    } catch (OutOfBoundsException ex) {
                        this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                    } catch (ImpossibleLocationRemoveException ex) {
                        System.out.println("imposible remove");
                    }
                }
                this.movedirection.x = 0;
                try {
                    this.parent.addComposite2dGameObjectComponent(this);
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
                            this.parent.addComposite2dGameObjectComponent(this);
                        } catch (ImpossibleLocationAddException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ObjectCollidesException ex) {
                            this.parent.processCollision(this, this.parent.collidesWith(this));
                        } catch (OutOfBoundsException ex) {
                            this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                        } catch (ImpossibleLocationRemoveException ex) {
                            System.out.println("imposible remove");
                        }
                        this.movedirection.y = 0;
                        this.movedirection.x = this.movingspeed.x;
                        for (Integer i = 0; i < times; i++, this.location.x += this.movingspeed.x, this.remainingspeed.x -= this.movingspeed.x) {
                            try {
                                this.parent.addComposite2dGameObjectComponent(this);
                            } catch (ImpossibleLocationAddException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ObjectCollidesException ex) {
                                this.parent.processCollision(this, this.parent.collidesWith(this));
                            } catch (OutOfBoundsException ex) {
                                this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                            } catch (ImpossibleLocationRemoveException ex) {
                                System.out.println("imposible remove");
                            }
                        }
                        this.movedirection.x = 0;
                    }
                    this.movedirection.y = 0;
                    for (; this.remainingspeed.x != 0; this.location.x += this.movingspeed.x, this.remainingspeed.x -= this.movingspeed.x) {
                        this.movedirection.x = this.movingspeed.y;
                        try {
                            this.parent.addComposite2dGameObjectComponent(this);
                        } catch (ImpossibleLocationAddException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ObjectCollidesException ex) {
                            this.parent.processCollision(this, this.parent.collidesWith(this));
                        } catch (OutOfBoundsException ex) {
                            this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                        } catch (ImpossibleLocationRemoveException ex) {
                            System.out.println("imposible remove");
                        }
                    }
                    this.movedirection.x = 0;
                    try {
                        this.parent.addComposite2dGameObjectComponent(this);
                    } catch (ImpossibleRelocationException ex) {
                        this.location = originallocation;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (this.speed.x != 0 && this.speed.y != 0 && this.movingspeed.y >= this.movingspeed.x) {
                    Integer times = Math.abs(this.speed.y / this.speed.x);
                    for (; this.remainingspeed.x != 0; this.location.x += this.movingspeed.x, this.remainingspeed.x -= this.movingspeed.x) {
                        this.movedirection.x = this.movingspeed.x;
                        try {
                            this.parent.addComposite2dGameObjectComponent(this);
                        } catch (ImpossibleLocationAddException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ObjectCollidesException ex) {
                            this.parent.processCollision(this, this.parent.collidesWith(this));
                        } catch (OutOfBoundsException ex) {
                            this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                        } catch (ImpossibleLocationRemoveException ex) {
                            System.out.println("imposible remove");
                        }
                        this.movedirection.x = 0;
                        for (Integer i = 0; i < times; i++, this.location.y += this.movingspeed.y, this.remainingspeed.y -= this.movingspeed.y) {
                            this.movedirection.y = this.movingspeed.y;
                            try {
                                this.parent.addComposite2dGameObjectComponent(this);
                            } catch (ImpossibleLocationAddException ex) {
                                Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ObjectCollidesException ex) {
                                this.parent.processCollision(this, this.parent.collidesWith(this));
                            } catch (OutOfBoundsException ex) {
                                this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                            } catch (ImpossibleLocationRemoveException ex) {
                                System.out.println("imposible remove");
                            }
                        }
                        this.movedirection.y = 0;
                    }
                    this.movedirection.x = 0;
                    for (; this.remainingspeed.y != 0; this.location.y += this.movingspeed.y, this.remainingspeed.y -= this.movingspeed.y) {
                        this.movedirection.y = this.movingspeed.y;
                        try {
                            this.parent.addComposite2dGameObjectComponent(this);
                        } catch (ImpossibleLocationAddException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ObjectCollidesException ex) {
                            this.parent.processCollision(this, this.parent.collidesWith(this));
                        } catch (OutOfBoundsException ex) {
                            this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                        } catch (ImpossibleLocationRemoveException ex) {
                            System.out.println("imposible remove");
                        }
                    }
                    this.movedirection.y = 0;
                    try {
                        this.parent.addComposite2dGameObjectComponent(this);
                    } catch (ImpossibleRelocationException ex) {
                        this.location = originallocation;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    for (; this.remainingspeed.y != 0 || this.remainingspeed.x != 0; this.location.x += this.movingspeed.x, this.location.y += this.movingspeed.y, this.remainingspeed.x -= this.movingspeed.x, this.remainingspeed.y -= this.movingspeed.y) {
                        this.movedirection.x = this.movingspeed.x;
                        this.movedirection.y = this.movingspeed.y;
                        try {
                            this.parent.addComposite2dGameObjectComponent(this);
                        } catch (ImpossibleLocationAddException ex) {
                            Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ObjectCollidesException ex) {
                            this.parent.processCollision(this, this.parent.collidesWith(this));
                        } catch (OutOfBoundsException ex) {
                            this.processOutOfBounds(new Coordinate(this.location.x -= this.movingspeed.x, this.location.y));
                        } catch (ImpossibleLocationRemoveException ex) {
                            System.out.println("imposible remove");
                        }
                    }
                    this.movedirection.x = 0;
                    this.movedirection.y = 0;
                    try {
                        this.parent.addComposite2dGameObjectComponent(this);
                    } catch (ImpossibleRelocationException ex) {
                        this.location = originallocation;
                        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }
    }
}
