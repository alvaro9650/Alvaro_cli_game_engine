/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

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
